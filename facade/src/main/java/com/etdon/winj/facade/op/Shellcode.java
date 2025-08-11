package com.etdon.winj.facade.op;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.register.Register;
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

        public Builder mov(@NotNull final Register destination, @NotNull final Register source) {

            Preconditions.checkNotNull(destination);
            Preconditions.checkNotNull(source);
            this.instructions(
                    Opcode.Prefix.of(destination.isExtended(), false, source.isExtended(), true),
                    Opcode.Primary.MOV.RM64_R64,
                    Opcode.ModRM.builder()
                            .mod(Opcode.ModRM.Mod.RD)
                            .reg(source.getValue())
                            .rm(destination.getValue())
                            .build()
            );
            return this;

        }

        public Builder mov(@NotNull final Register destination, final long value) {

            Preconditions.checkNotNull(destination);
            this.instructions(
                    Opcode.Prefix.of(destination.isExtended(), false, false, true),
                    Opcode.Primary.MOV.R64_IMM64 | destination.getValue()
            );
            this.longValue(value);
            return this;

        }

        public Builder push(@NotNull final Register destination) {

            Preconditions.checkNotNull(destination);
            if (destination.isExtended()) {
                this.instructions(
                        Opcode.Prefix.of(true, false, false, false),
                        Opcode.Primary.PUSH_R64 | destination.getValue()
                );
            } else {
                this.instruction(Opcode.Primary.PUSH_R64 | destination.getValue());
            }
            return this;

        }

        public Builder pop(@NotNull final Register source) {

            Preconditions.checkNotNull(source);
            if (source.isExtended()) {
                this.instructions(
                        Opcode.Prefix.of(true, false, false, false),
                        Opcode.Primary.POP_R64 | source.getValue()
                );
            } else {
                this.instruction(Opcode.Primary.POP_R64 | source.getValue());
            }
            return this;

        }

        public Builder call(@NotNull final Register destination) {

            Preconditions.checkNotNull(destination);
            this.instructions(
                    Opcode.Prefix.of(destination.isExtended(), false, false, true),
                    Opcode.Primary.CALL_RM64,
                    Opcode.ModRM.builder()
                            .mod(Opcode.ModRM.Mod.RD)
                            .reg(2)
                            .rm(destination.getValue())
                            .build()
            );
            return this;

        }

        public Builder xor(@NotNull final Register destination, @NotNull final Register source) {

            Preconditions.checkNotNull(destination);
            Preconditions.checkNotNull(source);
            this.instructions(
                    Opcode.Prefix.of(destination.isExtended(), false, source.isExtended(), true),
                    Opcode.Primary.XOR.RM64_R64,
                    Opcode.ModRM.builder()
                            .mod(Opcode.ModRM.Mod.RD)
                            .reg(source.getValue())
                            .rm(destination.getValue())
                            .build()
            );
            return this;

        }

        public Builder inc(@NotNull final Register destination) {

            Preconditions.checkNotNull(destination);
            this.instructions(
                    Opcode.Prefix.of(destination.isExtended(), false, false, true),
                    Opcode.Primary.INC_RM64,
                    Opcode.ModRM.builder()
                            .mod(Opcode.ModRM.Mod.RD)
                            .reg(0)
                            .rm(destination.getValue())
                            .build()
            );
            return this;

        }

        public Builder dec(@NotNull final Register destination) {

            Preconditions.checkNotNull(destination);
            this.instructions(
                    Opcode.Prefix.of(destination.isExtended(), false, false, true),
                    Opcode.Primary.DEC_RM64,
                    Opcode.ModRM.builder()
                            .mod(Opcode.ModRM.Mod.RD)
                            .reg(1)
                            .rm(destination.getValue())
                            .build()
            );
            return this;

        }

        public Builder not(@NotNull final Register destination) {

            Preconditions.checkNotNull(destination);
            this.instructions(
                    Opcode.Prefix.of(destination.isExtended(), false, false, true),
                    Opcode.Primary.NOT_RM64,
                    Opcode.ModRM.builder()
                            .mod(Opcode.ModRM.Mod.RD)
                            .reg(2)
                            .rm(destination.getValue())
                            .build()
            );
            return this;

        }

        public Builder neg(@NotNull final Register destination) {

            Preconditions.checkNotNull(destination);
            this.instructions(
                    Opcode.Prefix.of(destination.isExtended(), false, false, true),
                    Opcode.Primary.NEG_RM64,
                    Opcode.ModRM.builder()
                            .mod(Opcode.ModRM.Mod.RD)
                            .reg(3)
                            .rm(destination.getValue())
                            .build()
            );
            return this;

        }

        public Builder mul(@NotNull final Register source) {

            Preconditions.checkNotNull(source);
            this.instructions(
                    Opcode.Prefix.of(source.isExtended(), false, false, true),
                    Opcode.Primary.MUL_RM64,
                    Opcode.ModRM.builder()
                            .mod(Opcode.ModRM.Mod.RD)
                            .reg(4)
                            .rm(source.getValue())
                            .build()
            );
            return this;

        }

        public Builder imul(@NotNull final Register source) {

            Preconditions.checkNotNull(source);
            this.instructions(
                    Opcode.Prefix.of(source.isExtended(), false, false, true),
                    Opcode.Primary.IMUL_RM64,
                    Opcode.ModRM.builder()
                            .mod(Opcode.ModRM.Mod.RD)
                            .reg(5)
                            .rm(source.getValue())
                            .build()
            );
            return this;

        }

        public Builder div(@NotNull final Register source) {

            Preconditions.checkNotNull(source);
            this.instructions(
                    Opcode.Prefix.of(source.isExtended(), false, false, true),
                    Opcode.Primary.DIV_RM64,
                    Opcode.ModRM.builder()
                            .mod(Opcode.ModRM.Mod.RD)
                            .reg(6)
                            .rm(source.getValue())
                            .build()
            );
            return this;

        }

        public Builder idiv(@NotNull final Register source) {

            Preconditions.checkNotNull(source);
            this.instructions(
                    Opcode.Prefix.of(source.isExtended(), false, false, true),
                    Opcode.Primary.IDIV_RM64,
                    Opcode.ModRM.builder()
                            .mod(Opcode.ModRM.Mod.RD)
                            .reg(7)
                            .rm(source.getValue())
                            .build()
            );
            return this;

        }

        public Builder add(@NotNull final Register destination, final byte value) {

            Preconditions.checkNotNull(destination);
            this.instructions(
                    Opcode.Prefix.of(destination.isExtended(), false, false, true),
                    Opcode.Primary.ADD_RM64_IMM8,
                    Opcode.ModRM.builder()
                            .mod(Opcode.ModRM.Mod.RD)
                            .reg(0)
                            .rm(destination.getValue())
                            .build(),
                    value
            );
            return this;

        }

        public Builder or(@NotNull final Register destination, final byte value) {

            Preconditions.checkNotNull(destination);
            this.instructions(
                    Opcode.Prefix.of(destination.isExtended(), false, false, true),
                    Opcode.Primary.OR_RM64_IMM8,
                    Opcode.ModRM.builder()
                            .mod(Opcode.ModRM.Mod.RD)
                            .reg(1)
                            .rm(destination.getValue())
                            .build(),
                    value
            );
            return this;

        }

        public Builder adc(@NotNull final Register destination, final byte value) {

            Preconditions.checkNotNull(destination);
            this.instructions(
                    Opcode.Prefix.of(destination.isExtended(), false, false, true),
                    Opcode.Primary.ADC_RM64_IMM8,
                    Opcode.ModRM.builder()
                            .mod(Opcode.ModRM.Mod.RD)
                            .reg(2)
                            .rm(destination.getValue())
                            .build(),
                    value
            );
            return this;

        }

        public Builder sbb(@NotNull final Register destination, final byte value) {

            Preconditions.checkNotNull(destination);
            this.instructions(
                    Opcode.Prefix.of(destination.isExtended(), false, false, true),
                    Opcode.Primary.SBB_RM64_IMM8,
                    Opcode.ModRM.builder()
                            .mod(Opcode.ModRM.Mod.RD)
                            .reg(3)
                            .rm(destination.getValue())
                            .build(),
                    value
            );
            return this;

        }

        public Builder and(@NotNull final Register destination, final byte value) {

            Preconditions.checkNotNull(destination);
            this.instructions(
                    Opcode.Prefix.of(destination.isExtended(), false, false, true),
                    Opcode.Primary.AND_RM64_IMM8,
                    Opcode.ModRM.builder()
                            .mod(Opcode.ModRM.Mod.RD)
                            .reg(4)
                            .rm(destination.getValue())
                            .build(),
                    value
            );
            return this;

        }

        public Builder sub(@NotNull final Register destination, final byte value) {

            Preconditions.checkNotNull(destination);
            this.instructions(
                    Opcode.Prefix.of(destination.isExtended(), false, false, true),
                    Opcode.Primary.SUB_RM64_IMM8,
                    Opcode.ModRM.builder()
                            .mod(Opcode.ModRM.Mod.RD)
                            .reg(5)
                            .rm(destination.getValue())
                            .build(),
                    value
            );
            return this;

        }

        public Builder xor(@NotNull final Register destination, final byte value) {

            Preconditions.checkNotNull(destination);
            this.instructions(
                    Opcode.Prefix.of(destination.isExtended(), false, false, true),
                    Opcode.Primary.XOR_RM64_IMM8,
                    Opcode.ModRM.builder()
                            .mod(Opcode.ModRM.Mod.RD)
                            .reg(6)
                            .rm(destination.getValue())
                            .build(),
                    value
            );
            return this;

        }

        public Builder cmp(@NotNull final Register destination, final byte value) {

            Preconditions.checkNotNull(destination);
            this.instructions(
                    Opcode.Prefix.of(destination.isExtended(), false, false, true),
                    Opcode.Primary.CMP_RM64_IMM8,
                    Opcode.ModRM.builder()
                            .mod(Opcode.ModRM.Mod.RD)
                            .reg(7)
                            .rm(destination.getValue())
                            .build(),
                    value
            );
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
