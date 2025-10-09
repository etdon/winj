package com.etdon.winj.marshal.primitive;

import com.etdon.winj.marshal.Marshal;
import com.etdon.winj.marshal.MarshalContext;
import com.etdon.winj.util.ByteBuffer;
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
            final ByteBuffer byteBuffer = ByteBuffer.size(Long.BYTES);
            byteBuffer.setByteOrder(context.getByteOrder());
            byteBuffer.put(input);
            return byteBuffer.get();
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
