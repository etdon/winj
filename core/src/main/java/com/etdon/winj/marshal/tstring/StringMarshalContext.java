package com.etdon.winj.marshal.tstring;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.winj.marshal.MarshalContext;
import com.etdon.winj.marshal.MarshalStrategy;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;

public final class StringMarshalContext extends MarshalContext<String, StringMarshalContext> {

    private Charset charset;

    private StringMarshalContext() {

        super(null);

    }

    private StringMarshalContext(final Builder builder) {

        super(builder.strategy);

        Conditional.executeIfNotNull(builder.charset, () -> this.charset = builder.charset);

    }

    public Charset getCharset() {

        return this.charset;

    }

    public static StringMarshalContext empty() {

        return new StringMarshalContext();

    }

    public static Builder builder() {

        return new Builder();

    }

    public static class Builder implements FluentBuilder<StringMarshalContext> {

        private MarshalStrategy<String, StringMarshalContext> strategy;
        private Charset charset = Charset.defaultCharset();

        private Builder() {

        }

        public Builder strategy(@NotNull final MarshalStrategy<String, StringMarshalContext> strategy) {

            this.strategy = strategy;
            return this;

        }

        public Builder charset(@NotNull final Charset charset) {

            this.charset = charset;
            return this;

        }

        @NotNull
        @Override
        public StringMarshalContext build() {

            return new StringMarshalContext(this);

        }

    }

}
