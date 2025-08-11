package com.etdon.winj.facade.op.marshal.primitive;

import com.etdon.winj.facade.op.marshal.Marshal;
import com.etdon.winj.facade.op.marshal.MarshalContext;
import org.jetbrains.annotations.NotNull;

public final class IntegerMarshal extends Marshal<Integer, PrimitiveMarshalContext<Integer>> {

    private static class IntegerMarshalSingleton {

        private static final IntegerMarshal INSTANCE = new IntegerMarshal();

    }

    private IntegerMarshal() {

    }

    @Override
    public byte[] marshal(@NotNull final Integer input, @NotNull final PrimitiveMarshalContext<Integer> context) {

        if (context.getStrategy().isPresent()) {
            return context.getStrategy().get().marshal(input, context);
        } else {
            final byte[] buffer = new byte[Integer.BYTES];
            for (int i = 0; i < Integer.BYTES; i++)
                buffer[i] = (byte) ((input >>> (i * 8)) & 0xFF);
            return buffer;
        }

    }

    @Override
    public boolean isValid(@NotNull final Object input) {

        return input instanceof Integer;

    }

    @Override
    public boolean isValid(@NotNull final MarshalContext<?, ?> context) {

        return context instanceof PrimitiveMarshalContext;

    }

    public static IntegerMarshal getInstance() {

        return IntegerMarshalSingleton.INSTANCE;

    }

}
