package com.etdon.winj.facade.assembler;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.assembler.register.Register;
import org.jetbrains.annotations.NotNull;

// x86-64
public final class Opcodes {

    public static final class Prefix {

        // Access to new 8-bit registers.
        public static final byte REX = (byte) 0x40;
        // Extension of r/m field, base field, or opcode reg field.
        public static final byte REX_B = (byte) 0x41;
        // Extension of SIB index field.
        public static final byte REX_X = (byte) 0x42;
        // REX.X and REX.B combination.
        public static final byte REX_XB = (byte) 0x43;
        // Extension of ModR/M reg field.
        public static final byte REX_R = (byte) 0x44;
        // REX.R and REX.B combination.
        public static final byte REX_RB = (byte) 0x45;
        // REX.R and REX.X combination.
        public static final byte REX_RX = (byte) 0x46;
        // REX.R, REX.X and REX.B combination.
        public static final byte REX_RXB = (byte) 0x47;
        // 64 bit operand Size.
        public static final byte REX_W = (byte) 0x48;
        // REX.W and REX.B combination.
        public static final byte REX_WB = (byte) 0x49;
        // REX.W and REX.X combination.
        public static final byte REX_WX = (byte) 0x4A;
        // REX.W, REX.X and REX.B combination.
        public static final byte REX_WXB = (byte) 0x4B;
        // REX.W and REX.R combination.
        public static final byte REX_WR = (byte) 0x4C;
        // REX.W, REX.R and REX.B combination.
        public static final byte REX_WRB = (byte) 0x4D;
        // REX.W, REX.R and REX.X combination.
        public static final byte REX_WRX = (byte) 0x4E;
        // REX.W, REX.R, REX.X and REX.B combination.
        public static final byte REX_WRXB = (byte) 0x4F;

        public static byte of(final boolean b, final boolean x, final boolean r, final boolean w) {

            return (byte) (REX | (b ? 0b0001 : 0) | (x ? 0b0010 : 0) | (r ? 0b0100 : 0) | (w ? 0b1000 : 0));

        }

    }

    public static final class Primary {

        public static final class MOV {

            public static final byte R8_RM8 = (byte) 0x8A;
            public static final byte R64_RM64 = (byte) 0x8B;
            public static final byte M16_SREG = (byte) 0x8C;
            public static final byte R64_SREG = (byte) 0x8C;
            public static final byte SREG_RM16 = (byte) 0x8E;
            public static final byte AL_MOFFS8 = (byte) 0xA0;
            public static final byte RAX_MOFFS64 = (byte) 0xA1;
            public static final byte MOFFS8_AL = (byte) 0xA2;
            public static final byte MOFFS64_RAX = (byte) 0xA3;
            public static final byte S_M8_M8 = (byte) 0xA4;
            public static final byte SB_M8_M8 = (byte) 0xA4;
            public static final byte S_M64_M64 = (byte) 0xA5;
            public static final byte SW_M16_M16 = (byte) 0xA5;
            public static final byte SD_M32_M32 = (byte) 0xA5;
            public static final byte SQ_M64_M64 = (byte) 0xA5;
            public static final byte R8_IMM8 = (byte) 0xB0;

        }

        public static final class POP {

            public static final byte RM32 = (byte) 0x8F;
            public static final byte RM64 = (byte) 0x8F;

        }

        public static final byte TEST_RM8_R8 = (byte) 0x84;
        public static final byte TEST_RM64_R64 = (byte) 0x85;
        public static final byte XCHG_R8_RM8 = (byte) 0x86;
        public static final byte XCHG_R64_RM64 = (byte) 0x87;
        public static final byte LEA_R64_M = (byte) 0x8D;
        public static final byte XCHG_R64_RAX = (byte) 0x90;
        public static final byte NOP = (byte) 0x90;
        public static final byte PAUSE = (byte) 0x90;
        public static final byte CBW_AX_AL = (byte) 0x98;
        public static final byte CWDE_EAX_AX = (byte) 0x98;
        public static final byte CDQE_RAX_EAX = (byte) 0x98;
        public static final byte CWD_DX_AX = (byte) 0x99;
        public static final byte CDQ_EDX_EAX = (byte) 0x99;
        public static final byte CQO_RDX_RAX = (byte) 0x99;
        public static final byte FWAIT = (byte) 0x9B;
        public static final byte WAIT = (byte) 0x9B;
        public static final byte PUSHF = (byte) 0x9C;
        public static final byte PUSHFQ = (byte) 0x9C;
        public static final byte POPF = (byte) 0x9D;
        public static final byte POPFQ = (byte) 0x9D;
        public static final byte SAHF_AH = (byte) 0x9E;
        public static final byte LAHF_AH = (byte) 0x9F;

    }

    public static final class ModRM {

        private ModRM() {

        }

        public static Builder builder() {

            return new Builder();

        }

        public static final class Builder implements FluentBuilder<Byte> {

            private byte mod;
            private byte reg;
            private byte rm;

            private Builder() {

            }

            public Builder mod(final int mod) {

                this.mod = (byte) ((mod << 6) & 0b1100_0000);
                return this;

            }

            public Builder reg(final int reg) {

                this.reg = (byte) ((reg << 3) & 0b0011_1000);
                return this;

            }

            public Builder rm(final int rm) {

                this.rm = (byte) (rm & 0b0000_0111);
                return this;

            }

            @NotNull
            @Override
            public Byte build() {

                return (byte) (this.mod | this.reg | this.rm);

            }

        }

        public static final class Mod {

            // Register-indirect no displacement.
            public static final byte RI_D0 = 0b00;
            // Register-indirect 8-bit displacement.
            public static final byte RI_D8 = 0b01;
            // Register-indirect 32-bit displacement.
            public static final byte RI_D32 = 0b10;
            // Register-direct mode
            public static final byte RD = 0b11;

        }

    }

    public static final class SIB {

        private final byte scale;
        private final Register index;
        private final Register base;

        private SIB(final Builder builder) {

            this.scale = builder.scale;
            this.index = builder.index;
            this.base = builder.base;

        }

        public byte getScale() {

            return this.scale;

        }

        @NotNull
        public Register getIndex() {

            return this.index;

        }

        @NotNull
        public Register getBase() {

            return this.base;

        }

        public byte toByte() {

            return (byte) (this.scale |
                    (byte) ((this.index.getValue() << 3) & 0b0011_1000) |
                    (byte) (this.base.getValue() & 0b0000_0111));

        }

        public static Builder builder() {

            return new Builder();

        }

        public static final class Builder implements FluentBuilder<SIB> {

            private byte scale;
            private Register index;
            private Register base;

            private Builder() {

            }

            /**
             * @see ScaleFactor
             */
            public Builder scale(final byte scale) {

                this.scale = (byte) ((scale << 6) & 0b1100_0000);
                return this;

            }

            public Builder index(@NotNull final Register index) {

                this.index = index;
                return this;

            }

            public Builder base(@NotNull final Register base) {

                this.base = base;
                return this;

            }

            @NotNull
            @Override
            public SIB build() {

                Preconditions.checkNotNull(this.index);
                Preconditions.checkNotNull(this.base);
                return new SIB(this);

            }

        }

        public static class ScaleFactor {

            public static final byte SF_0 = 0b00;
            public static final byte SF_2 = 0b01;
            public static final byte SF_4 = 0b10;
            public static final byte SF_8 = 0b11;

        }

    }

    public static final class Group {

        public static final byte G_83 = (byte) 0x83;

    }

    private Opcodes() {

        throw new UnsupportedOperationException();

    }

}
