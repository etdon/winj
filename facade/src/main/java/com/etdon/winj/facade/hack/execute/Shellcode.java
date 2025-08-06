package com.etdon.winj.facade.hack.execute;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Shellcode {

    private final List<Byte> buffer;

    private Shellcode(final Builder builder) {

        this.buffer = builder.buffer;

    }

    public byte[] export() {

        final byte[] bytes = new byte[this.buffer.size()];
        for (int i = 0; i < this.buffer.size(); i++)
            bytes[i] = this.buffer.get(i);

        return bytes;

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<Shellcode> {

        private final List<Byte> buffer = new ArrayList<>();

        private Builder() {

        }

        public Builder instruction(@NotNull final Number instruction) {

            Preconditions.checkNotNull(instruction);
            this.buffer.add((byte) instruction.intValue());
            return this;

        }

        public Builder instructions(@NotNull final Number... instructions) {

            Preconditions.checkNotNull(instructions);
            for (final Number instruction : instructions)
                this.buffer.add((byte) instruction.intValue());
            return this;

        }

        public Builder value(@NotNull final Number value) {

            Preconditions.checkNotNull(value);
            this.buffer.add((byte) value.intValue());
            return this;

        }

        public Builder value(@NotNull final Number... values) {

            Preconditions.checkNotNull(values);
            for (final Number value : values)
                this.buffer.add((byte) value.intValue());
            return this;

        }
        
        public Builder address(final long address) {

            for (int i = 0; i < Long.BYTES; i++)
                this.buffer.add((byte) ((address >> (i * 8)) & 0xFF));
            return this;

        }

        public Builder padding(final int count) {

            for (int i = 0; i < count; i++)
                this.buffer.add((byte) 0x00);
            return this;

        }

        public Builder string(@NotNull final String input) {

            final byte[] bytes = input.getBytes();
            for (final byte b : bytes)
                this.buffer.add(b);
            return this;

        }

        public Builder string(@NotNull final String input, @NotNull final Charset charset) {

            final byte[] bytes = input.getBytes(charset);
            for (final byte b : bytes)
                this.buffer.add(b);
            return this;

        }

        public Builder stringXOR(@NotNull final String input, final Number key) {

            final byte[] bytes = input.getBytes();
            for (final byte b : bytes)
                this.buffer.add((byte) (b ^ key.byteValue()));
            return this;

        }

        public Builder stringXOR(@NotNull final String input, @NotNull final Charset charset, final Number key) {

            final byte[] bytes = input.getBytes(charset);
            for (final byte b : bytes)
                this.buffer.add((byte) (b ^ key.byteValue()));
            return this;

        }

        @NotNull
        @Override
        public Shellcode build() {

            return new Shellcode(this);

        }

    }

}
