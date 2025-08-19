package com.etdon.winj.function.generic;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.MemorySegmentable;
import com.etdon.jbinder.function.NativeFunction;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class GenericFunction extends NativeFunction<Object> {

    private final List<Object> parameters;

    private GenericFunction(final Builder builder) {

        super(builder.library, builder.name, builder.functionDescriptor);

        this.parameters = builder.parameters;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invokeWithArguments(this.parameters);

    }

    public List<Object> getParameters() {

        return this.parameters;

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<GenericFunction> {

        private static final Map<Class<?>, ValueLayout> PRIMITIVE_VALUE_LAYOUTS = Map.of(
                Byte.class, ValueLayout.JAVA_BYTE,
                Boolean.class, ValueLayout.JAVA_BOOLEAN,
                Character.class, ValueLayout.JAVA_CHAR,
                Short.class, ValueLayout.JAVA_SHORT,
                Integer.class, ValueLayout.JAVA_INT,
                Long.class, ValueLayout.JAVA_LONG,
                Float.class, ValueLayout.JAVA_FLOAT,
                Double.class, ValueLayout.JAVA_DOUBLE
        );

        private String library;
        private String name;
        private Object returnType;
        private List<Object> parameters = new ArrayList<>();
        private FunctionDescriptor functionDescriptor;

        private Builder() {

        }

        public Builder library(@NotNull final String library) {

            this.library = library;
            return this;

        }

        public Builder name(@NotNull final String name) {

            this.name = name;
            return this;

        }

        public Builder returnType(@NotNull final Object returnType) {

            this.returnType = returnType;
            return this;

        }

        public Builder parameter(@NotNull final Object parameter) {

            this.parameters.add(parameter);
            return this;

        }

        public Builder parameters(@NotNull final Object... parameters) {

            Collections.addAll(this.parameters, parameters);
            return this;

        }

        private FunctionDescriptor generateFunctionDescriptor() {

            final MemoryLayout[] argumentLayouts = new MemoryLayout[this.parameters.size()];
            for (int i = 0; i < this.parameters.size(); i++)
                argumentLayouts[i] = this.findMemoryLayout(this.parameters.get(i));

            if (this.returnType != null) {
                return FunctionDescriptor.of(this.findMemoryLayout(this.returnType), argumentLayouts);
            } else {
                return FunctionDescriptor.ofVoid(argumentLayouts);
            }

        }

        private MemoryLayout findMemoryLayout(@NotNull final Object input) {

            if (input instanceof Class<?>) {
                if (PRIMITIVE_VALUE_LAYOUTS.containsKey(input)) {
                    return PRIMITIVE_VALUE_LAYOUTS.get(input);
                } else {
                    throw new RuntimeException("Failed to resolve class '" + ((Class<?>) input).getName() + "' to value.");
                }
            }

            final Class<?> clazz = input.getClass();
            if (PRIMITIVE_VALUE_LAYOUTS.containsKey(clazz))
                return PRIMITIVE_VALUE_LAYOUTS.get(clazz);

            if (MemorySegmentable.class.isAssignableFrom(clazz)) {
                final MemorySegmentable memorySegmentable = (MemorySegmentable) input;
                return memorySegmentable.getMemoryLayout();
            } else if (MemoryLayout.class.isAssignableFrom(clazz)) {
                return (MemoryLayout) input;
            }

            throw new RuntimeException("Failed to find memory layout for input " + input + " (" + input.getClass().getName() + ")");

        }

        @NotNull
        public GenericFunction build() {

            Preconditions.checkNotNull(this.library);
            Preconditions.checkNotNull(this.name);

            this.functionDescriptor = this.generateFunctionDescriptor();
            for (int i = 0; i < this.parameters.size(); i++) {
                final Object parameter = this.parameters.get(i);
                if (parameter instanceof Class<?>) {
                    if (PRIMITIVE_VALUE_LAYOUTS.containsKey(parameter)) {
                        this.parameters.set(i, 0);
                    } else {
                        this.parameters.set(i, null);
                    }
                } else if (parameter instanceof MemoryLayout) {
                    this.parameters.set(i, null);
                }
            }

            return new GenericFunction(this);

        }

    }

}
