package com.etdon.winj.facade.assembler;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.winj.util.ByteBuffer;
import org.jetbrains.annotations.NotNull;

public final class Opcode {

    private final byte[] values;
    private final byte extension;

    private Opcode(final byte[] values,
                   final byte extension) {

        this.values = values;
        this.extension = extension;

    }

    private Opcode(final Builder builder) {

        this.values = builder.buffer.get();
        this.extension = builder.extension;

    }

    public byte[] getValues() {

        return this.values;

    }

    public byte getExtension() {

        return this.extension;

    }

    public static Opcode of(final Number value) {

        return new Opcode(new byte[]{value.byteValue()}, (byte) 0);

    }

    public static Opcode of(final Number value, final Number extension) {

        return new Opcode(new byte[]{value.byteValue()}, extension.byteValue());

    }

    public static Opcode of(final byte[] bytes) {

        return new Opcode(bytes, (byte) 0);

    }

    public static Opcode of(final byte[] bytes, final Number extension) {

        return new Opcode(bytes, extension.byteValue());

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<Opcode> {

        private final ByteBuffer buffer = ByteBuffer.size(2);
        private byte extension;

        private Builder() {

        }

        public Builder value(final byte value) {

            this.buffer.put(value);
            return this;

        }

        public Builder values(final byte[] values) {

            this.buffer.put(values);
            return this;

        }

        public Builder extension(final byte extension) {

            this.extension = extension;
            return this;

        }

        @NotNull
        @Override
        public Opcode build() {

            return new Opcode(this);

        }

    }

}
