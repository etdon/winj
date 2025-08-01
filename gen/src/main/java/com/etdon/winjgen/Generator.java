package com.etdon.winjgen;

import com.etdon.commons.context.MapPlaceholderProcessor;
import com.etdon.commons.util.StringUtils;
import com.etdon.winjgen.component.ClassComponentStack;
import com.etdon.winjgen.component.impl.*;
import com.etdon.winjgen.util.Humanizer;
import com.etdon.winjgen.type.NativeDataTypeMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public final class Generator {

    private static class GeneratorSingleton {

        private static final Generator INSTANCE = new Generator();

    }

    private String nativeFunctionClassTemplate = null;
    private String nativeTypeClassTemplate = null;

    private Generator() {

        this.loadTemplates();

    }

    private void loadTemplates() {

        try (final InputStream inputStream = Objects.requireNonNull(Generator.class.getClassLoader().getResourceAsStream("NativeFunctionClassTemplate"))) {
            final byte[] bytes = inputStream.readAllBytes();
            this.nativeFunctionClassTemplate = new String(bytes);
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }

        try (final InputStream inputStream = Objects.requireNonNull(Generator.class.getClassLoader().getResourceAsStream("NativeTypeClassTemplate"))) {
            final byte[] bytes = inputStream.readAllBytes();
            this.nativeTypeClassTemplate = new String(bytes);
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    @NotNull
    public String generateClass(@NotNull final List<Token> tokens) {

        final List<Token> processedTokens = new ArrayList<>(tokens.size());
        String template = null;

        final NativeDataTypeMapper nativeDataTypeMapper = new NativeDataTypeMapper();
        nativeDataTypeMapper.loadDefaults();

        final ClassComponentStack classComponentStack = new ClassComponentStack();
        final SignatureClassComponent signatureClassComponent = new SignatureClassComponent();
        classComponentStack.set(signatureClassComponent);

        final MapPlaceholderProcessor placeholderProcessor = new MapPlaceholderProcessor();
        for (final Token token : tokens) {
            switch (token.getType()) {
                case KEYWORD -> {
                    if (template == null)
                        template = this.nativeTypeClassTemplate;
                }
                case RETURN_TYPE -> {
                    if (template == null) {
                        template = this.nativeFunctionClassTemplate;
                        signatureClassComponent.addEntry(Objects.requireNonNull(nativeDataTypeMapper.map(Objects.requireNonNull(token.getValue()).toUpperCase())));
                    }
                }
                case FUNCTION_NAME -> {
                    final String value = Objects.requireNonNull(token.getValue());
                    placeholderProcessor.registerPlaceholder("FUNCTION_NAME", value);
                    placeholderProcessor.registerPlaceholder("FORMATTED_FUNCTION_NAME", this.formatName(value));
                }
                case TYPE -> {
                    final String mappedName = nativeDataTypeMapper.map(Objects.requireNonNull(token.getValue()).toUpperCase());
                    signatureClassComponent.addEntry(Objects.requireNonNull(mappedName));
                    final boolean finaleState = !Objects.requireNonNull(processedTokens.get(processedTokens.size() - 2).getValue()).contains("optional");

                    final String javaTypeName = Objects.requireNonNull(nativeDataTypeMapper.mapTypeToJavaType(token.getValue()));
                    final FieldClassComponent fieldClassComponent = classComponentStack.getOrCreate(FieldClassComponent.class);
                    fieldClassComponent.setTypeName(javaTypeName);
                    fieldClassComponent.setFinalState(finaleState);
                    classComponentStack.set(fieldClassComponent);

                    final ConstructorClassComponent constructorClassComponent = classComponentStack.getOrCreate(ConstructorClassComponent.class);
                    constructorClassComponent.setTypeName(javaTypeName);
                    constructorClassComponent.setFinalState(finaleState);
                    classComponentStack.set(constructorClassComponent);

                    final BuilderFieldComponent builderFieldComponent = classComponentStack.getOrCreate(BuilderFieldComponent.class);
                    builderFieldComponent.setNonPrimitiveTypeName(nativeDataTypeMapper.primitiveToObjectType(javaTypeName));
                    builderFieldComponent.setFinalState(finaleState);

                    final BuilderMethodComponent builderMethodComponent = classComponentStack.getOrCreate(BuilderMethodComponent.class);
                    builderMethodComponent.setTypeName(javaTypeName);

                    if (finaleState) {
                        final BuilderBuildComponent builderBuildComponent = classComponentStack.getOrCreate(BuilderBuildComponent.class);
                        builderBuildComponent.setFinalState(true);
                    }
                }
                case PARAMETER_NAME -> {
                    final String nativeName = Objects.requireNonNull(token.getValue());
                    final String humanizedFieldName = Humanizer.humanizeNativeParameterName(nativeName);
                    final FieldClassComponent fieldClassComponent = classComponentStack.getOrCreate(FieldClassComponent.class);
                    fieldClassComponent.setNativeName(nativeName);
                    fieldClassComponent.setFieldName(humanizedFieldName);
                    classComponentStack.flushLatest();

                    final ConstructorClassComponent constructorClassComponent = classComponentStack.getOrCreate(ConstructorClassComponent.class);
                    constructorClassComponent.setFieldName(humanizedFieldName);
                    classComponentStack.flushLatest();

                    final CallerClassComponent callerClassComponent = classComponentStack.getOrCreate(CallerClassComponent.class);
                    callerClassComponent.addEntry(humanizedFieldName);

                    final BuilderFieldComponent builderFieldComponent = classComponentStack.getOrCreate(BuilderFieldComponent.class);
                    builderFieldComponent.setFieldName(humanizedFieldName);
                    classComponentStack.flushLatest();

                    final BuilderMethodComponent builderMethodComponent = classComponentStack.getOrCreate(BuilderMethodComponent.class);
                    builderMethodComponent.setFunctionName(placeholderProcessor.process("${FUNCTION_NAME}"));
                    builderMethodComponent.setFieldName(humanizedFieldName);
                    classComponentStack.flushLatest();

                    if (classComponentStack.onStack(BuilderBuildComponent.class)) {
                        final BuilderBuildComponent builderBuildComponent = classComponentStack.getOrThrow(BuilderBuildComponent.class);
                        builderBuildComponent.setFieldName(humanizedFieldName);
                        classComponentStack.flushLatest();
                    }
                }
            }
            processedTokens.add(token);
        }

        // Flush all remaining components on the stack that haven't been flushed yet, usually just single-instance components.
        classComponentStack.flushAll();

        // Register all the remaining placeholders.
        placeholderProcessor.registerPlaceholder("SIGNATURE", Objects.requireNonNull(this.generatePlaceholder(classComponentStack, SignatureClassComponent.class, 2, false)));
        placeholderProcessor.registerPlaceholder("FIELDS", Objects.requireNonNull(this.generatePlaceholder(classComponentStack, FieldClassComponent.class, 2, false)));
        placeholderProcessor.registerPlaceholder("CONSTRUCTOR", Objects.requireNonNull(this.generatePlaceholder(classComponentStack, ConstructorClassComponent.class, 1, false)));
        placeholderProcessor.registerPlaceholder("CALLER", Objects.requireNonNull(this.generatePlaceholder(classComponentStack, CallerClassComponent.class, 2, false)));
        placeholderProcessor.registerPlaceholder("BUILDER_FIELDS", Objects.requireNonNull(this.generatePlaceholder(classComponentStack, BuilderFieldComponent.class, 1, false)));
        placeholderProcessor.registerPlaceholder("BUILDER_METHODS", Objects.requireNonNull(this.generatePlaceholder(classComponentStack, BuilderMethodComponent.class, 2, false)));
        placeholderProcessor.registerPlaceholder("BUILDER_BUILD", Optional.ofNullable((this.generatePlaceholder(classComponentStack, BuilderBuildComponent.class, 0, true))).orElse(""));

        if (template == null)
            throw new RuntimeException("Failed to detect template.");

        return placeholderProcessor.process(template);

    }

    @Nullable
    private String generatePlaceholder(@NotNull final ClassComponentStack classComponentStack,
                                       @NotNull final Class<? extends ClassComponent> identifier,
                                       final int lineSpacing,
                                       final boolean endSpacing) {

        final Queue<ClassComponent> flushedComponents = classComponentStack.getFlushedComponentsByIdentifier(identifier);
        if (flushedComponents == null) return null;

        final StringBuilder placeholderValue = new StringBuilder();
        for (final ClassComponent flushedComponent : flushedComponents) {
            if (!placeholderValue.isEmpty() && lineSpacing > 0)
                placeholderValue.append(StringUtils.repeat(System.lineSeparator(), lineSpacing));
            placeholderValue.append(flushedComponent.toString());
        }
        if (endSpacing)
            placeholderValue.append(System.lineSeparator());

        return placeholderValue.toString();

    }

    private String formatName(@NotNull final String input) {

        final StringBuilder stringBuilder = new StringBuilder();
        for (final char c : input.toCharArray()) {
            if (Character.isUpperCase(c)) {
                if (!stringBuilder.isEmpty())
                    stringBuilder.append('_');
                stringBuilder.append(c);
            } else {
                stringBuilder.append(Character.toUpperCase(c));
            }
        }

        return stringBuilder.toString();

    }

    public static Generator getInstance() {

        return GeneratorSingleton.INSTANCE;

    }

}
