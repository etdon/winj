package com.etdon.winj.marshal.tstring;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.marshal.MarshalStrategy;
import org.jetbrains.annotations.NotNull;

public final class XorStringMarshalStrategy extends MarshalStrategy<String, StringMarshalContext> {

    private final byte key;

    private XorStringMarshalStrategy(final byte key) {

        this.key = key;

    }

    private XorStringMarshalStrategy(final Builder builder) {

        this.key = builder.key;

    }

    @Override
    public byte[] marshal(@NotNull final String input, @NotNull final StringMarshalContext context) {

        final byte[] inputBytes = input.getBytes(context.getCharset());
        final byte[] output = new byte[inputBytes.length];
        for (int i = 0; i < inputBytes.length; i++)
            output[i] = (byte) (inputBytes[i] ^ this.key);

        return output;

    }

    public byte getKey() {

        return this.key;

    }

    public static XorStringMarshalStrategy withKey(@NotNull final Number key) {

        Preconditions.checkNotNull(key);
        return new XorStringMarshalStrategy(key.byteValue());

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<XorStringMarshalStrategy> {

        private Byte key;

        private Builder() {

        }

        public Builder key(final byte key) {

            this.key = key;
            return this;

        }

        public Builder key(@NotNull final Number key) {

            Preconditions.checkNotNull(key);
            this.key = key.byteValue();
            return this;

        }

        @NotNull
        @Override
        public XorStringMarshalStrategy build() {

            Preconditions.checkNotNull(this.key);
            return new XorStringMarshalStrategy(this);

        }

    }

}
