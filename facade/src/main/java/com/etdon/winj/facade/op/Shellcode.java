package com.etdon.winj.facade.op;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.instruction.*;
import com.etdon.winj.facade.op.register.Register64;
import com.etdon.winj.facade.op.register.Register8;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class Shellcode {

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

        public Builder raw(final byte value) {

            this.buffer.add(value);
            return this;

        }

        public Builder raw(final byte[] values) {

            for (final byte b : values)
                this.buffer.add(b);
            return this;

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

        public Builder shortValue(final short value) {

            for (int i = 0; i < Short.BYTES; i++)
                this.buffer.add((byte) ((value >>> (i * 8)) & 0xFF));
            return this;

        }

        public Builder intValue(final int value) {

            for (int i = 0; i < Integer.BYTES; i++)
                this.buffer.add((byte) ((value >>> (i * 8)) & 0xFF));
            return this;

        }


        public Builder longValue(final long value) {

            for (int i = 0; i < Long.BYTES; i++)
                this.buffer.add((byte) ((value >>> (i * 8)) & 0xFF));
            return this;

        }

        public Builder mov(@NotNull final Register64 destination, @NotNull final Register64 source) {

            this.raw(Instruction_MOV_RM64_R64.of(destination, source).build());
            return this;

        }

        public Builder mov(@NotNull final Register64 destination, final long value) {

            this.raw(Instruction_MOV_R64_IMM64.of(destination, value).build());
            return this;

        }

        public Builder push(@NotNull final Register64 destination) {

            this.raw(Instruction_PUSH_R64.of(destination).build());
            return this;

        }

        public Builder pop(@NotNull final Register64 source) {

            this.raw(Instruction_POP_R64.of(source).build());
            return this;

        }

        public Builder call(@NotNull final Register64 destination) {

            this.raw(Instruction_CALL_RM64.of(destination).build());
            return this;

        }

        public Builder xor(@NotNull final Register64 destination, @NotNull final Register64 source) {

            this.raw(Instruction_XOR_RM64_R64.of(destination, source).build());
            return this;

        }

        public Builder inc(@NotNull final Register64 destination) {

            this.raw(Instruction_INC_RM64.of(destination).build());
            return this;

        }

        public Builder dec(@NotNull final Register64 destination) {

            this.raw(Instruction_DEC_RM64.of(destination).build());
            return this;

        }

        public Builder test(@NotNull final Register64 destination, final int value) {

            this.raw(Instruction_TEST_RM64_IMM32.of(destination, value).build());
            return this;

        }

        public Builder test(@NotNull final Register8 destination, final byte value) {

            this.raw(Instruction_TEST_RM8_IMM8.of(destination, value).build());
            return this;

        }

        public Builder not(@NotNull final Register64 destination) {

            this.raw(Instruction_NOT_RM64.of(destination).build());
            return this;

        }

        public Builder not(@NotNull final Register8 destination) {

            this.raw(Instruction_NOT_RM8.of(destination).build());
            return this;

        }

        public Builder neg(@NotNull final Register64 destination) {

            this.raw(Instruction_NEG_RM64.of(destination).build());
            return this;

        }

        public Builder neg(@NotNull final Register8 destination) {

            this.raw(Instruction_NEG_RM8.of(destination).build());
            return this;

        }

        public Builder mul(@NotNull final Register64 source) {

            this.raw(Instruction_MUL_RM64.of(source).build());
            return this;

        }

        public Builder mul(@NotNull final Register8 source) {

            this.raw(Instruction_MUL_RM8.of(source).build());
            return this;

        }

        public Builder imul(@NotNull final Register64 source) {

            this.raw(Instruction_IMUL_RM64.of(source).build());
            return this;

        }

        public Builder imul(@NotNull final Register8 source) {

            this.raw(Instruction_IMUL_RM8.of(source).build());
            return this;

        }

        public Builder div(@NotNull final Register64 source) {

            this.raw(Instruction_DIV_RM64.of(source).build());
            return this;

        }

        public Builder div(@NotNull final Register8 source) {

            this.raw(Instruction_DIV_RM8.of(source).build());
            return this;

        }

        public Builder idiv(@NotNull final Register64 source) {

            this.raw(Instruction_IDIV_RM64.of(source).build());
            return this;

        }

        public Builder idiv(@NotNull final Register8 source) {

            this.raw(Instruction_IDIV_RM8.of(source).build());
            return this;

        }

        public Builder add(@NotNull final Register64 destination, final byte value) {

            this.raw(Instruction_ADD_RM64_IMM8.of(destination, value).build());
            return this;

        }

        public Builder or(@NotNull final Register64 destination, final byte value) {

            this.raw(Instruction_OR_RM64_IMM8.of(destination, value).build());
            return this;

        }

        public Builder adc(@NotNull final Register64 destination, final byte value) {

            this.raw(Instruction_ADC_RM64_IMM8.of(destination, value).build());
            return this;

        }

        public Builder sbb(@NotNull final Register64 destination, final byte value) {

            this.raw(Instruction_SBB_RM64_IMM8.of(destination, value).build());
            return this;

        }

        public Builder and(@NotNull final Register64 destination, final byte value) {

            this.raw(Instruction_AND_RM64_IMM8.of(destination, value).build());
            return this;

        }

        public Builder sub(@NotNull final Register64 destination, final byte value) {

            this.raw(Instruction_SUB_RM64_IMM8.of(destination, value).build());
            return this;

        }

        public Builder xor(@NotNull final Register64 destination, final byte value) {

            this.raw(Instruction_XOR_RM64_IMM8.of(destination, value).build());
            return this;

        }

        public Builder cmp(@NotNull final Register64 destination, final byte value) {

            this.raw(Instruction_CMP_RM64_IMM8.of(destination, value).build());
            return this;

        }

        public Builder retn(final short value) {

            this.raw(Instruction_RETN_IMM16.of(value).build());
            return this;

        }

        public Builder retn() {

            this.raw(Instruction_RETN.of().build());
            return this;

        }

        public Builder syscall() {

            this.raw(Instruction_SYSCALL.of().build());
            return this;

        }

        public Builder address(final long address) {

            for (int i = 0; i < Long.BYTES; i++)
                this.buffer.add((byte) ((address >>> (i * 8)) & 0xFF));
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
