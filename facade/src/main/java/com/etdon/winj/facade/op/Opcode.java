package com.etdon.winj.facade.op;

import com.etdon.commons.builder.FluentBuilder;
import org.jetbrains.annotations.NotNull;

// x86-64
public final class Opcode {

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

            public static final byte RM8_R8 = (byte) 0x88;
            public static final byte RM64_R64 = (byte) 0x89;
            public static final byte R8_RM8 = (byte) 0x8A;
            public static final byte R64_RM64 = (byte) 0x8B;
            public static final byte M16_SREG = (byte) 0x8C;
            public static final byte R64_SREG = (byte) 0x8C;
            public static final byte R8_IMM8 = (byte) 0xB0;
            public static final byte R64_IMM64 = (byte) 0xB8;

        }

        public static final class XOR {

            public static final byte RM8_R8 = (byte) 0x30;
            public static final byte RM64_R64 = (byte) 0x31;
            public static final byte R8_RM8 = (byte) 0x32;
            public static final byte R64_RM64 = (byte) 0x33;
            public static final byte AL_IMM8 = (byte) 0x34;
            public static final byte RAX_IMM32 = (byte) 0x35;

        }

        public static final byte RETN_IMM16 = (byte) 0xC2;
        public static final byte RETN = (byte) 0xC3;

        public static final byte PUSH_R64 = (byte) 0x50;
        public static final byte POP_R64 = (byte) 0x58;

        public static final byte CALL_RM32 = (byte) 0xFF;
        public static final byte CALL_RM64 = (byte) 0xFF;

        public static final byte INC_RM8 = (byte) 0xFE;
        public static final byte DEC_RM8 = (byte) 0xFE;
        public static final byte INC_RM64 = (byte) 0xFF;
        public static final byte DEC_RM64 = (byte) 0xFF;

    }

    public static final class TwoByte {

        public static final byte[] SYSCALL = {0x0F, 0x05};

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

    public static final class Group {

        public static final byte G_83 = (byte) 0x83;

    }

    private Opcode() {

        throw new UnsupportedOperationException();

    }

}
