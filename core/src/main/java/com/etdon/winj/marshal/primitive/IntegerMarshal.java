package com.etdon.winj.marshal.primitive;

import com.etdon.winj.marshal.Marshal;
import com.etdon.winj.marshal.MarshalContext;
import com.etdon.winj.util.ByteBuffer;
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
            final ByteBuffer byteBuffer = ByteBuffer.size(Integer.BYTES);
            byteBuffer.setByteOrder(context.getByteOrder());
            byteBuffer.put(input);
            return byteBuffer.get();
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
