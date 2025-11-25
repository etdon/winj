package com.etdon.winjgen.type;

import com.etdon.commons.manage.Registry;
import com.etdon.commons.tuple.ImmutablePair;
import com.etdon.commons.tuple.KeyValuePair;
import com.etdon.winj.type.constant.NativeDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.foreign.ValueLayout;
import java.lang.reflect.AccessFlag;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

public final class NativeDataTypeMapper extends Registry<String, ImmutablePair<String, ValueLayout>> {

    public static final Map<String, String> JAVA_MAPPINGS = Map.of(
            "OfBooleanImpl", "boolean",
            "OfCharImpl", "char",
            "OfByteImpl", "byte",
            "OfShortImpl", "short",
            "OfIntImpl", "int",
            "OfFloatImpl", "float",
            "OfLongImpl", "long",
            "OfDoubleImpl", "double",
            "OfAddressImpl", "MemorySegment"
    );

    public static final Map<String, String> PRIMITIVE_TO_NON_PRIMITIVE = Map.of(
            "boolean", "Boolean",
            "char", "Character",
            "byte", "Byte",
            "short", "Short",
            "int", "Integer",
            "float", "Float",
            "long", "Long",
            "double", "Double"
    );

    public void loadDefaults() {

        for (final Field field : NativeDataType.class.getFields()) {
            try {
                if (!field.accessFlags().contains(AccessFlag.PUBLIC) ||
                        !field.accessFlags().contains(AccessFlag.STATIC))
                    continue;
                final String fieldName = field.getName();
                final ValueLayout dataType = (ValueLayout) field.get(null);
                super.register(dataType.name().orElseThrow(), ImmutablePair.of(fieldName, dataType));
            } catch (final Throwable ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    @Nullable
    public String mapTypeToJavaType(@NotNull final String key) {

        final KeyValuePair<String, ValueLayout> value = super.get(key);
        if (value == null) return null;

        return JAVA_MAPPINGS.get(value.getValue().getClass().getSimpleName());

    }

    @Nullable
    public String map(@NotNull final String key) {

        return Optional.ofNullable(super.get(key)).map(KeyValuePair::getKey).orElse(null);

    }

    @NotNull
    public String primitiveToObjectType(@NotNull final String typeName) {

        return Optional.ofNullable(PRIMITIVE_TO_NON_PRIMITIVE.get(typeName)).orElse(typeName);

    }

}
