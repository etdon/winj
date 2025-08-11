package com.etdon.winj.facade.op.marshal.primitive;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.winj.facade.op.marshal.MarshalContext;
import com.etdon.winj.facade.op.marshal.MarshalStrategy;
import org.jetbrains.annotations.NotNull;

public final class PrimitiveMarshalContext<T> extends MarshalContext<T, PrimitiveMarshalContext<T>> {

    private PrimitiveMarshalContext() {

        super(null);

    }

    private PrimitiveMarshalContext(final Builder<T> builder) {

        super(builder.strategy);

    }

    public static <T> PrimitiveMarshalContext<T> empty() {

        return new PrimitiveMarshalContext<>();

    }

    public static <T> Builder<T> builder() {

        return new Builder<>();

    }

    public static final class Builder<T> implements FluentBuilder<PrimitiveMarshalContext<T>> {

        private MarshalStrategy<T, PrimitiveMarshalContext<T>> strategy;

        private Builder() {

        }

        public Builder<T> strategy(@NotNull final MarshalStrategy<T, PrimitiveMarshalContext<T>> strategy) {

            this.strategy = strategy;
            return this;

        }

        @NotNull
        @Override
        public PrimitiveMarshalContext<T> build() {

            return new PrimitiveMarshalContext<>(this);

        }

    }

}
