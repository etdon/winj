package com.etdon.winj.facade.op.marshal.tchar;

import com.etdon.winj.facade.op.marshal.Marshal;
import com.etdon.winj.facade.op.marshal.MarshalContext;
import org.jetbrains.annotations.NotNull;

public final class CharacterMarshal extends Marshal<Character, CharacterMarshalContext> {

    private static class CharMarshalSingleton {

        private static final CharacterMarshal INSTANCE = new CharacterMarshal();

    }

    private CharacterMarshal() {

    }

    @Override
    public byte[] marshal(@NotNull final Character input, @NotNull final CharacterMarshalContext context) {

        if (context.getStrategy().isPresent()) {
            return context.getStrategy().get().marshal(input, context);
        } else {
            return String.valueOf(input).getBytes(context.getCharset());
        }

    }

    @Override
    public boolean isValid(@NotNull final Object input) {

        return input instanceof String;

    }

    @Override
    public boolean isValid(@NotNull final MarshalContext<?, ?> context) {

        return context instanceof CharacterMarshalContext;

    }

    public static CharacterMarshal getInstance() {

        return CharMarshalSingleton.INSTANCE;

    }

}
