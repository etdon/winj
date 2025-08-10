package com.etdon.winj.facade.op.marshal.string;

import com.etdon.winj.facade.op.marshal.Marshal;
import org.jetbrains.annotations.NotNull;

public final class StringMarshal extends Marshal<String, StringMarshalContext> {

    @Override
    public byte[] marshal(@NotNull final String input, @NotNull final StringMarshalContext context) {

        if (context.getStrategy().isPresent()) {
            return context.getStrategy().get().marshal(input, context);
        } else {
            return input.getBytes(context.getCharset());
        }

    }

}
