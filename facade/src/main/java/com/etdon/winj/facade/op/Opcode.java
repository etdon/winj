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

        public static final class ADD {

            public static final byte RM8_R8 = (byte) 0x00;
            public static final byte RM64_R64 = (byte) 0x01;
            public static final byte R8_RM8 = (byte) 0x02;
            public static final byte R64_RM64 = (byte) 0x03;
            public static final byte AL_IMM8 = (byte) 0x04;
            public static final byte RAX_IMM32 = (byte) 0x05;
            public static final byte RM8_IMM8 = (byte) 0x80;
            public static final byte RM64_IMM32 = (byte) 0x81;
            /**
             * @see com.etdon.winj.facade.op.instruction.Instruction_ADD_RM64_IMM8
             */
            public static final byte RM64_IMM8 = (byte) 0x83;

        }

        public static final class OR {

            public static final byte RM8_R8 = (byte) 0x08;
            public static final byte RM64_R64 = (byte) 0x09;
            public static final byte R8_RM8 = (byte) 0x0A;
            public static final byte R64_RM64 = (byte) 0x0B;
            public static final byte AL_IMM8 = (byte) 0x0C;
            public static final byte RAX_IMM32 = (byte) 0x0D;
            public static final byte RM8_IMM8 = (byte) 0x80;
            public static final byte RM64_IMM32 = (byte) 0x81;
            /**
             * @see com.etdon.winj.facade.op.instruction.Instruction_OR_RM64_IMM8
             */
            public static final byte RM64_IMM8 = (byte) 0x83;

        }

        public static final class ADC {

            public static final byte RM8_R8 = (byte) 0x10;
            public static final byte RM64_R64 = (byte) 0x11;
            public static final byte R8_RM8 = (byte) 0x12;
            public static final byte R64_RM64 = (byte) 0x13;
            public static final byte AL_IMM8 = (byte) 0x14;
            public static final byte RAX_IMM32 = (byte) 0x15;
            public static final byte RM8_IMM8 = (byte) 0x80;
            public static final byte RM64_IMM32 = (byte) 0x81;
            /**
             * @see com.etdon.winj.facade.op.instruction.Instruction_ADC_RM64_IMM8
             */
            public static final byte RM64_IMM8 = (byte) 0x83;

        }

        public static final class SBB {

            public static final byte RM8_R8 = (byte) 0x18;
            public static final byte RM64_R64 = (byte) 0x19;
            public static final byte R8_RM8 = (byte) 0x1A;
            public static final byte R64_RM64 = (byte) 0x1B;
            public static final byte AL_IMM8 = (byte) 0x1C;
            public static final byte RAX_IMM32 = (byte) 0x1D;
            public static final byte RM8_IMM8 = (byte) 0x80;
            public static final byte RM64_IMM32 = (byte) 0x81;
            /**
             * @see com.etdon.winj.facade.op.instruction.Instruction_SBB_RM64_IMM8
             */
            public static final byte RM64_IMM8 = (byte) 0x83;

        }

        public static final class AND {

            public static final byte RM8_R8 = (byte) 0x20;
            public static final byte RM64_R64 = (byte) 0x21;
            public static final byte R8_RM8 = (byte) 0x22;
            public static final byte R64_RM64 = (byte) 0x23;
            public static final byte AL_IMM8 = (byte) 0x24;
            public static final byte RAX_IMM32 = (byte) 0x25;
            public static final byte RM8_IMM8 = (byte) 0x80;
            public static final byte RM64_IMM32 = (byte) 0x81;
            /**
             * @see com.etdon.winj.facade.op.instruction.Instruction_AND_RM64_IMM8
             */
            public static final byte RM64_IMM8 = (byte) 0x83;

        }

        public static final class SUB {

            public static final byte RM8_R8 = (byte) 0x28;
            public static final byte RM64_R64 = (byte) 0x29;
            public static final byte R8_RM8 = (byte) 0x2A;
            public static final byte R64_RM64 = (byte) 0x2B;
            public static final byte AL_IMM8 = (byte) 0x2C;
            public static final byte RAX_IMM32 = (byte) 0x2D;
            public static final byte RM8_IMM8 = (byte) 0x80;
            public static final byte RM64_IMM32 = (byte) 0x81;
            /**
             * @see com.etdon.winj.facade.op.instruction.Instruction_SUB_RM64_IMM8
             */
            public static final byte RM64_IMM8 = (byte) 0x83;

        }

        public static final class XOR {

            public static final byte RM8_R8 = (byte) 0x30;
            /**
             * @see com.etdon.winj.facade.op.instruction.Instruction_XOR_RM64_R64
             */
            public static final byte RM64_R64 = (byte) 0x31;
            public static final byte R8_RM8 = (byte) 0x32;
            public static final byte R64_RM64 = (byte) 0x33;
            public static final byte AL_IMM8 = (byte) 0x34;
            public static final byte RAX_IMM32 = (byte) 0x35;
            public static final byte RM8_IMM8 = (byte) 0x80;
            public static final byte RM64_IMM32 = (byte) 0x81;
            /**
             * @see com.etdon.winj.facade.op.instruction.Instruction_XOR_RM64_IMM8
             */
            public static final byte RM64_IMM8 = (byte) 0x83;

        }

        public static final class CMP {

            public static final byte RM8_R8 = (byte) 0x38;
            public static final byte RM64_R64 = (byte) 0x39;
            public static final byte R8_RM8 = (byte) 0x3A;
            public static final byte R64_RM64 = (byte) 0x3B;
            public static final byte AL_IMM8 = (byte) 0x3C;
            public static final byte RAX_IMM32 = (byte) 0x3D;
            public static final byte RM8_IMM8 = (byte) 0x80;
            public static final byte RM64_IMM32 = (byte) 0x81;
            /**
             * @see com.etdon.winj.facade.op.instruction.Instruction_CMP_RM64_IMM8
             */
            public static final byte RM64_IMM8 = (byte) 0x83;

        }

        /**
         * @see com.etdon.winj.facade.op.instruction.Instruction_PUSH_R64
         */
        public static final byte PUSH_R64 = (byte) 0x50;
        public static final byte PUSH_IMM32 = (byte) 0x68;
        public static final byte IMUL_R64_RM64_IMM32 = (byte) 0x69;
        public static final byte PUSH_IMM8 = (byte) 0x6A;
        public static final byte IMUL_R64_RM64_IMM8 = (byte) 0x6B;
        public static final byte INS_M8_DX = (byte) 0x6C;
        public static final byte INSB_M8_DX = (byte) 0x6C;
        public static final byte INS_M16_DX = (byte) 0x6D;
        public static final byte INSW_M16_DX = (byte) 0x6D;
        public static final byte INS_M32_DX = (byte) 0x6D;
        public static final byte INSD_M32_DX = (byte) 0x6D;
        public static final byte OUTS_DX_M8 = (byte) 0x6E;
        public static final byte OUTSB_DX_M8 = (byte) 0x6E;
        public static final byte OUTS_DX_M16 = (byte) 0x6F;
        public static final byte OUTSW_DX_M16 = (byte) 0x6F;
        public static final byte OUTS_DX_M32 = (byte) 0x6F;
        public static final byte OUTSD_DX_M32 = (byte) 0x6F;

        public static class JMPC {

            public static final byte JO_REL8 = (byte) 0x70;
            public static final byte JNO_REL8 = (byte) 0x71;
            public static final byte JB_REL8 = (byte) 0x72;
            public static final byte JNAE_REL8 = (byte) 0x72;
            public static final byte JC_REL8 = (byte) 0x72;
            public static final byte JNB_REL8 = (byte) 0x73;
            public static final byte JAE_REL8 = (byte) 0x73;
            public static final byte JNC_REL8 = (byte) 0x73;
            public static final byte JZ_REL8 = (byte) 0x74;
            public static final byte JE_REL8 = (byte) 0x74;
            public static final byte JNZ_REL8 = (byte) 0x75;
            public static final byte JNE_REL8 = (byte) 0x75;
            public static final byte JBE_REL8 = (byte) 0x76;
            public static final byte JNA_REL8 = (byte) 0x76;
            public static final byte JNBE_REL8 = (byte) 0x77;
            public static final byte JA_REL8 = (byte) 0x77;
            public static final byte JS_REL8 = (byte) 0x78;
            public static final byte JNS_REL8 = (byte) 0x79;
            public static final byte JP_REL8 = (byte) 0x7A;
            public static final byte JPE_REL8 = (byte) 0x7A;
            public static final byte JNP_REL8 = (byte) 0x7B;
            public static final byte JPO_REL8 = (byte) 0x7B;
            public static final byte JL_REL8 = (byte) 0x7C;
            public static final byte JNGE_REL8 = (byte) 0x7C;
            public static final byte JNL_REL8 = (byte) 0x7D;
            public static final byte JGE_REL8 = (byte) 0x7D;
            public static final byte JLE_REL8 = (byte) 0x7E;
            public static final byte JNG_REL8 = (byte) 0x7E;
            public static final byte JNLE_REL8 = (byte) 0x7F;
            public static final byte JG_REL8 = (byte) 0x7F;

        }

        public static final class MOV {

            public static final byte RM8_R8 = (byte) 0x88;
            /**
             * @see com.etdon.winj.facade.op.instruction.Instruction_MOV_RM64_R64
             */
            public static final byte RM64_R64 = (byte) 0x89;
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
            /**
             * @see com.etdon.winj.facade.op.instruction.Instruction_MOV_R64_IMM64
             */
            public static final byte R64_IMM64 = (byte) 0xB8;

        }

        public static final class POP {

            /**
             * @see com.etdon.winj.facade.op.instruction.Instruction_POP_R64
             */
            public static final byte R64 = (byte) 0x58;
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
        /**
         * @see com.etdon.winj.facade.op.instruction.Instruction_RETN_IMM16
         */
        public static final byte RETN_IMM16 = (byte) 0xC2;
        /**
         * @see com.etdon.winj.facade.op.instruction.Instruction_RETN
         */
        public static final byte RETN = (byte) 0xC3;
        public static final byte INC_RM8 = (byte) 0xFE;
        public static final byte DEC_RM8 = (byte) 0xFE;
        /**
         * @see com.etdon.winj.facade.op.instruction.Instruction_INC_RM64
         */
        public static final byte INC_RM64 = (byte) 0xFF;
        /**
         * @see com.etdon.winj.facade.op.instruction.Instruction_DEC_RM64
         */
        public static final byte DEC_RM64 = (byte) 0xFF;
        public static final byte CALL_RM32 = (byte) 0xFF;
        /**
         * @see com.etdon.winj.facade.op.instruction.Instruction_CALL_RM64
         */
        public static final byte CALL_RM64 = (byte) 0xFF;
        public static final byte CALLF_M64 = (byte) 0xFF;
        public static final byte JMP_RM32 = (byte) 0xFF;
        public static final byte JMP_RM64 = (byte) 0xFF;
        /**
         * @see com.etdon.winj.facade.op.instruction.Instruction_TEST_RM8_IMM8
         */
        public static final byte TEST_RM8_IMM8 = (byte) 0xF6;
        /**
         * @see com.etdon.winj.facade.op.instruction.Instruction_NOT_RM8
         */
        public static final byte NOT_RM8 = (byte) 0xF6;
        /**
         * @see com.etdon.winj.facade.op.instruction.Instruction_NEG_RM8
         */
        public static final byte NEG_RM8 = (byte) 0xF6;
        /**
         * @see com.etdon.winj.facade.op.instruction.Instruction_MUL_RM8
         */
        public static final byte MUL_RM8 = (byte) 0xF6;
        /**
         * @see com.etdon.winj.facade.op.instruction.Instruction_IMUL_RM8
         */
        public static final byte IMUL_RM8 = (byte) 0xF6;
        /**
         * @see com.etdon.winj.facade.op.instruction.Instruction_DIV_RM8
         */
        public static final byte DIV_RM8 = (byte) 0xF6;
        /**
         * @see com.etdon.winj.facade.op.instruction.Instruction_IDIV_RM8
         */
        public static final byte IDIV_RM8 = (byte) 0xF6;
        /**
         * @see com.etdon.winj.facade.op.instruction.Instruction_TEST_RM64_IMM32
         */
        public static final byte TEST_RM64_IMM32 = (byte) 0xF7;
        /**
         * @see com.etdon.winj.facade.op.instruction.Instruction_NOT_RM64
         */
        public static final byte NOT_RM64 = (byte) 0xF7;
        /**
         * @see com.etdon.winj.facade.op.instruction.Instruction_NEG_RM64
         */
        public static final byte NEG_RM64 = (byte) 0xF7;
        /**
         * @see com.etdon.winj.facade.op.instruction.Instruction_MUL_RM64
         */
        public static final byte MUL_RM64 = (byte) 0xF7;
        /**
         * @see com.etdon.winj.facade.op.instruction.Instruction_IMUL_RM64
         */
        public static final byte IMUL_RM64 = (byte) 0xF7;
        /**
         * @see com.etdon.winj.facade.op.instruction.Instruction_DIV_RM64
         */
        public static final byte DIV_RM64 = (byte) 0xF7;
        /**
         * @see com.etdon.winj.facade.op.instruction.Instruction_IDIV_RM64
         */
        public static final byte IDIV_RM64 = (byte) 0xF7;

    }

    public static final class TwoByte {

        /**
         * @see com.etdon.winj.facade.op.instruction.Instruction_SYSCALL
         */
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

    public static final class SIB {

        private SIB() {

        }

        public static Builder builder() {

            return new Builder();

        }

        public static final class Builder implements FluentBuilder<Byte> {

            private byte scale;
            private byte index;
            private byte base;

            private Builder() {

            }

            public Builder scale(final byte scale) {

                this.scale = (byte) ((scale << 6) & 0b1100_0000);
                return this;

            }

            public Builder index(final byte index) {

                this.index = (byte) ((index << 3) & 0b0011_1000);
                return this;

            }

            public Builder base(final byte base) {

                this.base = (byte) (base & 0b0000_0111);
                return this;

            }

            @NotNull
            @Override
            public Byte build() {

                return (byte) (this.scale | this.index | this.base);

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
