package com.etdon.winj.marshal.primitive;

import com.etdon.winj.marshal.Marshal;
import com.etdon.winj.marshal.MarshalContext;
import com.etdon.commons.io.ByteBuffer;
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
            final ByteBuffer byteBuffer = ByteBuffer.size(Short.BYTES);
            byteBuffer.setByteOrder(context.getByteOrder());
            byteBuffer.put(input);
            return byteBuffer.get();
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
