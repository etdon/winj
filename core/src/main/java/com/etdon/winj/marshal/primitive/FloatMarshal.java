package com.etdon.winj.marshal.primitive;

import com.etdon.winj.marshal.Marshal;
import com.etdon.winj.marshal.MarshalContext;
import com.etdon.winj.util.ByteBuffer;
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
            final ByteBuffer byteBuffer = ByteBuffer.size(Float.BYTES);
            byteBuffer.setByteOrder(context.getByteOrder());
            byteBuffer.put(input);
            return byteBuffer.get();
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
