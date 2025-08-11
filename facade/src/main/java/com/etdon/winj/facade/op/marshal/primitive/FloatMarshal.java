package com.etdon.winj.facade.op.marshal.primitive;

import com.etdon.winj.facade.op.marshal.Marshal;
import com.etdon.winj.facade.op.marshal.MarshalContext;
import org.jetbrains.annotations.NotNull;

public final class FloatMarshal extends Marshal<Float, PrimitiveMarshalContext<Float>> {

    private static class FloatMarshalSingleton {

        private static final FloatMarshal INSTANCE = new FloatMarshal();

    }

    private FloatMarshal() {

    }

    @Override
    public byte[] marshal(@NotNull final Float input, @NotNull final PrimitiveMarshalContext<Float> context) {

        if (context.getStrategy().isPresent()) {
            return context.getStrategy().get().marshal(input, context);
        } else {
            final int floatBits = Float.floatToIntBits(input);
            final byte[] buffer = new byte[Float.BYTES];
            for (int i = 0; i < Float.BYTES; i++)
                buffer[i] = (byte) ((floatBits >>> (i * 8)) & 0xFF);
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

    public static FloatMarshal getInstance() {

        return FloatMarshalSingleton.INSTANCE;

    }

}
