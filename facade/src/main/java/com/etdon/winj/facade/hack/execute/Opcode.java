package com.etdon.winj.facade.hack.execute;

import com.etdon.commons.builder.FluentBuilder;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("SpellCheckingInspection")
public final class Opcode {

    public static final class Prefix {

        public static final byte REX = (byte) 0x40;
        public static final byte REX_B = (byte) 0x41;
        public static final byte REX_X = (byte) 0x42;
        public static final byte REX_XB = (byte) 0x43;
        public static final byte REX_R = (byte) 0x44;
        public static final byte REX_RB = (byte) 0x45;
        public static final byte REX_RX = (byte) 0x46;
        public static final byte REX_RXB = (byte) 0x47;
        public static final byte REX_W = (byte) 0x48;
        public static final byte REX_WB = (byte) 0x49;
        public static final byte REX_WX = (byte) 0x4A;
        public static final byte REX_WXB = (byte) 0x4B;
        public static final byte REX_WR = (byte) 0x4C;
        public static final byte REX_WRB = (byte) 0x4D;
        public static final byte REX_WRX = (byte) 0x4E;
        public static final byte REX_WRXB = (byte) 0x4F;

    }

    public static final class Primary {

        public static final byte RETN_IMM_16 = (byte) 0xC2;
        public static final byte RETN = (byte) 0xC3;
        public static final byte MOV_RM_8 = (byte) 0x88;
        public static final byte MOV_RM_16_32_64 = (byte) 0x89;
        public static final byte MOV_R_8 = (byte) 0x8A;

    }

    public static final class Mode {

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

    }

    public static final class Group {

        public static final byte G_83 = (byte) 0x83;

    }

    private Opcode() {

        throw new UnsupportedOperationException();

    }

}
