package com.etdon.winj.marshal.primitive;

import com.etdon.winj.marshal.Marshal;
import com.etdon.winj.marshal.MarshalContext;
import org.jetbrains.annotations.NotNull;

public final class DoubleMarshal extends Marshal<Double, PrimitiveMarshalContext<Double>> {

    private static class DoubleMarshalSingleton {

        private static final DoubleMarshal INSTANCE = new DoubleMarshal();

    }

    private DoubleMarshal() {

    }

    @Override
    public byte[] marshal(@NotNull final Double input, @NotNull final PrimitiveMarshalContext<Double> context) {

        if (context.getStrategy().isPresent()) {
            return context.getStrategy().get().marshal(input, context);
        } else {
            final long doubleBits = Double.doubleToLongBits(input);
            final byte[] buffer = new byte[Double.BYTES];
            for (int i = 0; i < Double.BYTES; i++)
                buffer[i] = (byte) ((doubleBits >>> (i * 8)) & 0xFF);
            return buffer;
        }

    }

    @Override
    public boolean isValid(@NotNull final Object input) {

        return input instanceof Double;

    }

    @Override
    public boolean isValid(@NotNull final MarshalContext<?, ?> context) {

        return context instanceof PrimitiveMarshalContext;

    }

    public static DoubleMarshal getInstance() {

        return DoubleMarshalSingleton.INSTANCE;

    }

}
