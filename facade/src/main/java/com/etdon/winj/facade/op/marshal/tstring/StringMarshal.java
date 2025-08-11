package com.etdon.winj.facade.op.marshal.tstring;

import com.etdon.winj.facade.op.marshal.Marshal;
import com.etdon.winj.facade.op.marshal.MarshalContext;
import org.jetbrains.annotations.NotNull;

public final class StringMarshal extends Marshal<String, StringMarshalContext> {

    private static class StringMarshalSingleton {

        private static final StringMarshal INSTANCE = new StringMarshal();

    }

    private StringMarshal() {

    }

    @Override
    public byte[] marshal(@NotNull final String input, @NotNull final StringMarshalContext context) {

        if (context.getStrategy().isPresent()) {
            return context.getStrategy().get().marshal(input, context);
        } else {
            return input.getBytes(context.getCharset());
        }

    }

    @Override
    public boolean isValid(@NotNull final Object input) {

        return input instanceof String;

    }

    @Override
    public boolean isValid(@NotNull final MarshalContext<?, ?> context) {

        return context instanceof StringMarshalContext;

    }

    public static StringMarshal getInstance() {

        return StringMarshalSingleton.INSTANCE;

    }

}
