package com.etdon.winj.marshal.primitive;

import com.etdon.winj.marshal.Marshal;
import com.etdon.winj.marshal.MarshalContext;
import org.jetbrains.annotations.NotNull;

public final class LongMarshal extends Marshal<Long, PrimitiveMarshalContext<Long>> {

    private static class LongMarshalSingleton {

        private static final LongMarshal INSTANCE = new LongMarshal();

    }

    private LongMarshal() {

    }

    @Override
    public byte[] marshal(@NotNull final Long input, @NotNull final PrimitiveMarshalContext<Long> context) {

        if (context.getStrategy().isPresent()) {
            return context.getStrategy().get().marshal(input, context);
        } else {
            final byte[] buffer = new byte[Long.BYTES];
            for (int i = 0; i < Long.BYTES; i++)
                buffer[i] = (byte) ((input >>> (i * 8)) & 0xFF);
            return buffer;
        }

    }

    @Override
    public boolean isValid(@NotNull final Object input) {

        return input instanceof Long;

    }

    @Override
    public boolean isValid(@NotNull final MarshalContext<?, ?> context) {

        return context instanceof PrimitiveMarshalContext;

    }

    public static LongMarshal getInstance() {

        return LongMarshalSingleton.INSTANCE;

    }

}
