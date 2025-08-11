package com.etdon.winj.marshal.primitive;

import com.etdon.winj.marshal.Marshal;
import com.etdon.winj.marshal.MarshalContext;
import org.jetbrains.annotations.NotNull;

public final class ByteMarshal extends Marshal<Byte, PrimitiveMarshalContext<Byte>> {

    private static class ByteMarshalSingleton {

        private static final ByteMarshal INSTANCE = new ByteMarshal();

    }

    private ByteMarshal() {

    }

    @Override
    public byte[] marshal(@NotNull final Byte input, @NotNull final PrimitiveMarshalContext<Byte> context) {

        if (context.getStrategy().isPresent()) {
            return context.getStrategy().get().marshal(input, context);
        } else {
            return new byte[]{input};
        }

    }

    @Override
    public boolean isValid(@NotNull final Object input) {

        return input instanceof Byte;

    }

    @Override
    public boolean isValid(@NotNull final MarshalContext<?, ?> context) {

        return context instanceof PrimitiveMarshalContext;

    }

    public static ByteMarshal getInstance() {

        return ByteMarshalSingleton.INSTANCE;

    }

}
