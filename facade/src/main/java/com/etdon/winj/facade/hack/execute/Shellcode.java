package com.etdon.winj.facade.hack.execute;

import com.etdon.commons.builder.FluentBuilder;
import org.jetbrains.annotations.NotNull;

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

        public Builder instruction(final byte instruction) {

            this.buffer.add(instruction);
            return this;

        }

        public Builder instructions(final byte... instructions) {

            for (final byte instruction : instructions)
                this.buffer.add(instruction);
            return this;

        }

        public Builder instruction(final int instruction) {

            this.buffer.add((byte) instruction);
            return this;

        }

        public Builder instructions(final int... instructions) {

            for (final int instruction : instructions)
                this.buffer.add((byte) instruction);
            return this;

        }

        public Builder value(final byte value) {

            this.buffer.add(value);
            return this;

        }

        public Builder value(final short value) {

            if (value > Byte.MAX_VALUE) {
                for (int i = 0; i < Short.BYTES; i++)
                    this.buffer.add((byte) ((value >> (i * 8)) & 0xFF));
            }
            return this;

        }

        public Builder value(final int value) {

            for (int i = 0; i < Integer.BYTES; i++)
                this.buffer.add((byte) ((value >> (i * 8)) & 0xFF));
            return this;

        }

        public Builder value(final long value) {

            for (int i = 0; i < Long.BYTES; i++)
                this.buffer.add((byte) ((value >> (i * 8)) & 0xFF));
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

        @NotNull
        @Override
        public Shellcode build() {

            return new Shellcode(this);

        }

    }

}
