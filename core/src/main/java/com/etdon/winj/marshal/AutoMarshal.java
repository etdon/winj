package com.etdon.winj.marshal;

import com.etdon.commons.tuple.KeyValuePair;
import com.etdon.commons.tuple.Pair;
import com.etdon.commons.util.Exceptional;
import com.etdon.commons.util.MapUtils;
import com.etdon.winj.marshal.primitive.*;
import com.etdon.winj.marshal.tchar.CharacterMarshal;
import com.etdon.winj.marshal.tstring.StringMarshal;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Used to marshal untyped inputs automatically based on their class if a corresponding implementation can be found in
 * the internal registry.
 * <p>
 * By default, the internal registry has mappings for all boxed primitive types as well as {@link String}. Additional
 * mappings can be added by accessing the {@link AutoMarshal#REGISTRY} field. Please note that this functionality
 * paired with the capability of overriding existing mappings might change in the future.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public final class AutoMarshal {

    /**
     * Questionable if this should be exposed since {@link MapUtils#newMap(KeyValuePair[])} returns a mutable map. On
     * one hand registering custom types might be desired but being able to override primitive mappings could be a bit
     * much and lead to unexpected behavior.
     * <p>
     * Proper entry management might be desirable, perhaps paired with the prevention of overriding existing keys (or
     * just primitive type mappings).
     */
    public static final Map<Class<?>, Marshal> REGISTRY = MapUtils.newMap(
            Pair.of(Byte.class, ByteMarshal.getInstance()),
            Pair.of(Character.class, CharacterMarshal.getInstance()),
            Pair.of(Short.class, ShortMarshal.getInstance()),
            Pair.of(Integer.class, IntegerMarshal.getInstance()),
            Pair.of(Float.class, FloatMarshal.getInstance()),
            Pair.of(Long.class, LongMarshal.getInstance()),
            Pair.of(Double.class, DoubleMarshal.getInstance()),
            Pair.of(String.class, StringMarshal.getInstance())
    );

    /**
     * Marshals the provided input using a registered processor for the class, if one is present. The provided context
     * might include the marshaling strategy used by the processor. For all primitive types {@link PrimitiveMarshalContext#empty()}
     * can be used if no special context is needed.
     *
     * @param input the untyped input.
     * @param context the context.
     * @return the byte array.
     * @throws RuntimeException if no implementation mapping is found for the input type.
     * @throws IllegalArgumentException if the input or context is invalid.
     */
    public static byte[] marshal(@NotNull final Object input, @NotNull final MarshalContext context) {

        final Marshal marshal = REGISTRY.get(input.getClass());
        if (marshal == null)
            throw Exceptional.of(RuntimeException.class, "Failed to find marshal mapping for type '{}'.", input.getClass().getName());
        if (!marshal.isValid(input))
            throw Exceptional.of(IllegalArgumentException.class, "Input '{}' is not considered valid by its processor '{}'", input, marshal.getClass().getName());
        if (!marshal.isValid(context))
            throw Exceptional.of(IllegalArgumentException.class, "Context '{}' is not considered valid by its processor '{}'", input, marshal.getClass().getName());

        return marshal.marshal(input, context);

    }

    private AutoMarshal() {

        throw new UnsupportedOperationException();

    }

}
