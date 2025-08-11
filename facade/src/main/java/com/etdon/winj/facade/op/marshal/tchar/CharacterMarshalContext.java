package com.etdon.winj.facade.op.marshal.tchar;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.winj.facade.op.marshal.MarshalContext;
import com.etdon.winj.facade.op.marshal.MarshalStrategy;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;

public final class CharacterMarshalContext extends MarshalContext<Character, CharacterMarshalContext> {

    private Charset charset;

    private CharacterMarshalContext() {

        super(null);

    }

    private CharacterMarshalContext(final Builder builder) {

        super(builder.strategy);

        Conditional.executeIfNotNull(builder.charset, () -> this.charset = builder.charset);

    }

    public Charset getCharset() {

        return this.charset;

    }

    public static CharacterMarshalContext empty() {

        return new CharacterMarshalContext();

    }

    public static Builder builder() {

        return new Builder();

    }

    public static class Builder implements FluentBuilder<CharacterMarshalContext> {

        private MarshalStrategy<Character, CharacterMarshalContext> strategy;
        private Charset charset = Charset.defaultCharset();

        private Builder() {

        }

        public Builder strategy(@NotNull final MarshalStrategy<Character, CharacterMarshalContext> strategy) {

            this.strategy = strategy;
            return this;

        }

        public Builder charset(@NotNull final Charset charset) {

            this.charset = charset;
            return this;

        }

        @NotNull
        @Override
        public CharacterMarshalContext build() {

            return new CharacterMarshalContext(this);

        }

    }

}
