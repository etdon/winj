package com.etdon.winj.marshal;

import com.etdon.commons.tuple.Pair;
import com.etdon.commons.util.Exceptional;
import com.etdon.commons.util.MapUtils;
import com.etdon.winj.marshal.primitive.*;
import com.etdon.winj.marshal.tchar.CharacterMarshal;
import com.etdon.winj.marshal.tstring.StringMarshal;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class AutoMarshal {

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
