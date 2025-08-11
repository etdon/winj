package com.etdon.winj.facade.op.marshal.primitive;

import com.etdon.winj.facade.op.marshal.Marshal;
import com.etdon.winj.facade.op.marshal.MarshalContext;
import org.jetbrains.annotations.NotNull;

public final class ShortMarshal extends Marshal<Short, PrimitiveMarshalContext<Short>> {

    private static class ShortMarshalSingleton {

        private static final ShortMarshal INSTANCE = new ShortMarshal();

    }

    private ShortMarshal() {

    }

    @Override
    public byte[] marshal(@NotNull final Short input, @NotNull final PrimitiveMarshalContext<Short> context) {

        if (context.getStrategy().isPresent()) {
            return context.getStrategy().get().marshal(input, context);
        } else {
            final byte[] buffer = new byte[Short.BYTES];
            for (int i = 0; i < Short.BYTES; i++)
                buffer[i] = (byte) ((input >>> (i * 8)) & 0xFF);
            return buffer;
        }

    }

    @Override
    public boolean isValid(@NotNull final Object input) {

        return input instanceof Short;

    }

    @Override
    public boolean isValid(@NotNull final MarshalContext<?, ?> context) {

        return context instanceof PrimitiveMarshalContext;

    }

    public static ShortMarshal getInstance() {

        return ShortMarshalSingleton.INSTANCE;

    }

}
