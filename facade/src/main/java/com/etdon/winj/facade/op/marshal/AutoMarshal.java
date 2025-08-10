package com.etdon.winj.facade.op.marshal;

import com.etdon.commons.util.Exceptional;
import com.etdon.winj.facade.op.marshal.string.StringMarshal;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class AutoMarshal {

    public static final Map<Class<?>, Marshal> REGISTRY = Map.of(
            String.class, StringMarshal.getInstance()
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
