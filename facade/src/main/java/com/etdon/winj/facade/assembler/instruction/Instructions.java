package com.etdon.winj.facade.assembler.instruction;

import com.etdon.winj.facade.assembler.Opcode;
import com.etdon.winj.facade.assembler.address.RegisterAddressor;
import com.etdon.winj.facade.assembler.operand.OperandSize;
import com.etdon.winj.facade.assembler.operand.encoding.*;
import com.etdon.winj.facade.assembler.operand.impl.Immediate;
import com.etdon.winj.facade.assembler.register.Register;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Instructions implements InstructionHolder {

    private static final Logger LOGGER = Logger.getLogger(Instructions.class.getName());
    private static final Map<Class<?>, List<Instruction>> INSTRUCTION_CACHE = new ConcurrentHashMap<>();

    public static final Instruction PUSH_R64 = Instruction.of(Opcode.of(0x50), Signature.of(Constraint.of(Register.class, OperandSize.WORD, OperandSize.DWORD, OperandSize.QWORD)), com.etdon.winj.facade.assembler.operand.encoding.OR.getInstance());
    public static final Instruction POP_R64 = Instruction.of(Opcode.of(0x58), Signature.of(Constraint.of(Register.class, OperandSize.WORD, OperandSize.DWORD, OperandSize.QWORD)), com.etdon.winj.facade.assembler.operand.encoding.OR.getInstance());

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

    public static class JMPC implements InstructionHolder {

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

    public static class ADD implements InstructionHolder {

        public static final Instruction AL_IMM8 = Instruction.of(Opcode.of(0x04), Signature.of(Constraint.of(Immediate.class, OperandSize.BYTE)), IMM.getInstance());
        public static final Instruction AX_IMM16 = Instruction.of(Opcode.of(0x5), Signature.of(Constraint.of(Immediate.class, OperandSize.WORD)), IMM.getInstance());
        public static final Instruction EAX_IMM32 = Instruction.of(Opcode.of(0x5), Signature.of(Constraint.of(Immediate.class, OperandSize.DWORD)), IMM.getInstance());
        public static final Instruction RAX_IMM32 = Instruction.of(Opcode.of(0x5), Signature.of(Constraint.of(Immediate.class, OperandSize.QWORD)), IMM.getInstance());
        public static final Instruction RM8_IMM8 = Instruction.of(Opcode.of(0x80), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE), Constraint.of(Immediate.class, OperandSize.BYTE)), RM_IMM.getInstance());
        public static final Instruction RM16_IMM16 = Instruction.of(Opcode.of(0x81), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD), Constraint.of(Immediate.class, OperandSize.WORD)), RM_IMM.getInstance());
        public static final Instruction RM32_IMM32 = Instruction.of(Opcode.of(0x81), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.DWORD), Constraint.of(Immediate.class, OperandSize.DWORD)), RM_IMM.getInstance());
        public static final Instruction RM64_IMM32 = Instruction.of(Opcode.of(0x81), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD), Constraint.of(Immediate.class, OperandSize.DWORD)), RM_IMM.getInstance());
        public static final Instruction RM64_IMM8 = Instruction.of(Opcode.of(0x83), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD, OperandSize.DWORD, OperandSize.QWORD), Constraint.of(Immediate.class, OperandSize.BYTE)), RM_IMM.getInstance());
        public static final Instruction RM8_R8 = Instruction.of(Opcode.of(0x00), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE), Constraint.of(Register.class, OperandSize.BYTE)), RM_R.getInstance());
        public static final Instruction RM16_R16 = Instruction.of(Opcode.of(0x01), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD), Constraint.of(Register.class, OperandSize.WORD)), RM_R.getInstance());
        public static final Instruction RM32_R32 = Instruction.of(Opcode.of(0x01), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.DWORD), Constraint.of(Register.class, OperandSize.DWORD)), RM_R.getInstance());
        public static final Instruction RM64_R64 = Instruction.of(Opcode.of(0x01), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD), Constraint.of(Register.class, OperandSize.QWORD)), RM_R.getInstance());
        public static final Instruction R8_RM8 = Instruction.of(Opcode.of(0x02), Signature.of(Constraint.of(Register.class, OperandSize.BYTE), Constraint.of(RegisterAddressor.class, OperandSize.BYTE)), R_RM.getInstance());
        public static final Instruction R16_RM16 = Instruction.of(Opcode.of(0x03), Signature.of(Constraint.of(Register.class, OperandSize.WORD), Constraint.of(RegisterAddressor.class, OperandSize.WORD)), R_RM.getInstance());
        public static final Instruction R32_RM32 = Instruction.of(Opcode.of(0x03), Signature.of(Constraint.of(Register.class, OperandSize.DWORD), Constraint.of(RegisterAddressor.class, OperandSize.DWORD)), R_RM.getInstance());
        public static final Instruction R64_RM64 = Instruction.of(Opcode.of(0x03), Signature.of(Constraint.of(Register.class, OperandSize.QWORD), Constraint.of(RegisterAddressor.class, OperandSize.QWORD)), R_RM.getInstance());

    }

    public static final class OR implements InstructionHolder {

        public static final Instruction AL_IMM8 = Instruction.of(Opcode.of(0x0C), Signature.of(Constraint.of(Immediate.class, OperandSize.BYTE)), IMM.getInstance());
        public static final Instruction AX_IMM16 = Instruction.of(Opcode.of(0x0D), Signature.of(Constraint.of(Immediate.class, OperandSize.WORD)), IMM.getInstance());
        public static final Instruction EAX_IMM32 = Instruction.of(Opcode.of(0x0D), Signature.of(Constraint.of(Immediate.class, OperandSize.DWORD)), IMM.getInstance());
        public static final Instruction RAX_IMM32 = Instruction.of(Opcode.of(0x0D), Signature.of(Constraint.of(Immediate.class, OperandSize.QWORD)), IMM.getInstance());
        public static final Instruction RM8_IMM8 = Instruction.of(Opcode.of(0x80, 1), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE), Constraint.of(Immediate.class, OperandSize.BYTE)), RM_IMM.getInstance());
        public static final Instruction RM16_IMM16 = Instruction.of(Opcode.of(0x81, 1), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD), Constraint.of(Immediate.class, OperandSize.WORD)), RM_IMM.getInstance());
        public static final Instruction RM32_IMM32 = Instruction.of(Opcode.of(0x81, 1), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.DWORD), Constraint.of(Immediate.class, OperandSize.DWORD)), RM_IMM.getInstance());
        public static final Instruction RM64_IMM32 = Instruction.of(Opcode.of(0x81, 1), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD), Constraint.of(Immediate.class, OperandSize.DWORD)), RM_IMM.getInstance());
        public static final Instruction RM64_IMM8 = Instruction.of(Opcode.of(0x83, 1), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD, OperandSize.DWORD, OperandSize.QWORD), Constraint.of(Immediate.class, OperandSize.BYTE)), RM_IMM.getInstance());
        public static final Instruction RM8_R8 = Instruction.of(Opcode.of(0x08), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE), Constraint.of(Register.class, OperandSize.BYTE)), RM_R.getInstance());
        public static final Instruction RM16_R16 = Instruction.of(Opcode.of(0x09), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD), Constraint.of(Register.class, OperandSize.WORD)), RM_R.getInstance());
        public static final Instruction RM32_R32 = Instruction.of(Opcode.of(0x09), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.DWORD), Constraint.of(Register.class, OperandSize.DWORD)), RM_R.getInstance());
        public static final Instruction RM64_R64 = Instruction.of(Opcode.of(0x09), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD), Constraint.of(Register.class, OperandSize.QWORD)), RM_R.getInstance());
        public static final Instruction R8_RM8 = Instruction.of(Opcode.of(0x0A), Signature.of(Constraint.of(Register.class, OperandSize.BYTE), Constraint.of(RegisterAddressor.class, OperandSize.BYTE)), R_RM.getInstance());
        public static final Instruction R16_RM16 = Instruction.of(Opcode.of(0x0B), Signature.of(Constraint.of(Register.class, OperandSize.WORD), Constraint.of(RegisterAddressor.class, OperandSize.WORD)), R_RM.getInstance());
        public static final Instruction R32_RM32 = Instruction.of(Opcode.of(0x0B), Signature.of(Constraint.of(Register.class, OperandSize.DWORD), Constraint.of(RegisterAddressor.class, OperandSize.DWORD)), R_RM.getInstance());
        public static final Instruction R64_RM64 = Instruction.of(Opcode.of(0x0B), Signature.of(Constraint.of(Register.class, OperandSize.QWORD), Constraint.of(RegisterAddressor.class, OperandSize.QWORD)), R_RM.getInstance());

    }

    public static final class ADC implements InstructionHolder {

        public static final Instruction AL_IMM8 = Instruction.of(Opcode.of(0x14), Signature.of(Constraint.of(Immediate.class, OperandSize.BYTE)), IMM.getInstance());
        public static final Instruction AX_IMM16 = Instruction.of(Opcode.of(0x15), Signature.of(Constraint.of(Immediate.class, OperandSize.WORD)), IMM.getInstance());
        public static final Instruction EAX_IMM32 = Instruction.of(Opcode.of(0x15), Signature.of(Constraint.of(Immediate.class, OperandSize.DWORD)), IMM.getInstance());
        public static final Instruction RAX_IMM32 = Instruction.of(Opcode.of(0x15), Signature.of(Constraint.of(Immediate.class, OperandSize.QWORD)), IMM.getInstance());
        public static final Instruction RM8_IMM8 = Instruction.of(Opcode.of(0x80, 2), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE), Constraint.of(Immediate.class, OperandSize.BYTE)), RM_IMM.getInstance());
        public static final Instruction RM16_IMM16 = Instruction.of(Opcode.of(0x81, 2), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD), Constraint.of(Immediate.class, OperandSize.WORD)), RM_IMM.getInstance());
        public static final Instruction RM32_IMM32 = Instruction.of(Opcode.of(0x81, 2), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.DWORD), Constraint.of(Immediate.class, OperandSize.DWORD)), RM_IMM.getInstance());
        public static final Instruction RM64_IMM32 = Instruction.of(Opcode.of(0x81, 2), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD), Constraint.of(Immediate.class, OperandSize.DWORD)), RM_IMM.getInstance());
        public static final Instruction RM64_IMM8 = Instruction.of(Opcode.of(0x83, 2), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD, OperandSize.DWORD, OperandSize.QWORD), Constraint.of(Immediate.class, OperandSize.BYTE)), RM_IMM.getInstance());
        public static final Instruction RM8_R8 = Instruction.of(Opcode.of(0x10), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE), Constraint.of(Register.class, OperandSize.BYTE)), RM_R.getInstance());
        public static final Instruction RM16_R16 = Instruction.of(Opcode.of(0x11), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD), Constraint.of(Register.class, OperandSize.WORD)), RM_R.getInstance());
        public static final Instruction RM32_R32 = Instruction.of(Opcode.of(0x11), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.DWORD), Constraint.of(Register.class, OperandSize.DWORD)), RM_R.getInstance());
        public static final Instruction RM64_R64 = Instruction.of(Opcode.of(0x11), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD), Constraint.of(Register.class, OperandSize.QWORD)), RM_R.getInstance());
        public static final Instruction R8_RM8 = Instruction.of(Opcode.of(0x12), Signature.of(Constraint.of(Register.class, OperandSize.BYTE), Constraint.of(RegisterAddressor.class, OperandSize.BYTE)), R_RM.getInstance());
        public static final Instruction R16_RM16 = Instruction.of(Opcode.of(0x13), Signature.of(Constraint.of(Register.class, OperandSize.WORD), Constraint.of(RegisterAddressor.class, OperandSize.WORD)), R_RM.getInstance());
        public static final Instruction R32_RM32 = Instruction.of(Opcode.of(0x13), Signature.of(Constraint.of(Register.class, OperandSize.DWORD), Constraint.of(RegisterAddressor.class, OperandSize.DWORD)), R_RM.getInstance());
        public static final Instruction R64_RM64 = Instruction.of(Opcode.of(0x13), Signature.of(Constraint.of(Register.class, OperandSize.QWORD), Constraint.of(RegisterAddressor.class, OperandSize.QWORD)), R_RM.getInstance());

    }

    public static final class SBB implements InstructionHolder {

        public static final Instruction AL_IMM8 = Instruction.of(Opcode.of(0x1C), Signature.of(Constraint.of(Immediate.class, OperandSize.BYTE)), IMM.getInstance());
        public static final Instruction AX_IMM16 = Instruction.of(Opcode.of(0x1D), Signature.of(Constraint.of(Immediate.class, OperandSize.WORD)), IMM.getInstance());
        public static final Instruction EAX_IMM32 = Instruction.of(Opcode.of(0x1D), Signature.of(Constraint.of(Immediate.class, OperandSize.DWORD)), IMM.getInstance());
        public static final Instruction RAX_IMM32 = Instruction.of(Opcode.of(0x1D), Signature.of(Constraint.of(Immediate.class, OperandSize.QWORD)), IMM.getInstance());
        public static final Instruction RM8_IMM8 = Instruction.of(Opcode.of(0x80, 3), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE), Constraint.of(Immediate.class, OperandSize.BYTE)), RM_IMM.getInstance());
        public static final Instruction RM16_IMM16 = Instruction.of(Opcode.of(0x81, 3), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD), Constraint.of(Immediate.class, OperandSize.WORD)), RM_IMM.getInstance());
        public static final Instruction RM32_IMM32 = Instruction.of(Opcode.of(0x81, 3), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.DWORD), Constraint.of(Immediate.class, OperandSize.DWORD)), RM_IMM.getInstance());
        public static final Instruction RM64_IMM32 = Instruction.of(Opcode.of(0x81, 3), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD), Constraint.of(Immediate.class, OperandSize.DWORD)), RM_IMM.getInstance());
        public static final Instruction RM64_IMM8 = Instruction.of(Opcode.of(0x83, 3), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD, OperandSize.DWORD, OperandSize.QWORD), Constraint.of(Immediate.class, OperandSize.BYTE)), RM_IMM.getInstance());
        public static final Instruction RM8_R8 = Instruction.of(Opcode.of(0x18), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE), Constraint.of(Register.class, OperandSize.BYTE)), RM_R.getInstance());
        public static final Instruction RM16_R16 = Instruction.of(Opcode.of(0x19), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD), Constraint.of(Register.class, OperandSize.WORD)), RM_R.getInstance());
        public static final Instruction RM32_R32 = Instruction.of(Opcode.of(0x19), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.DWORD), Constraint.of(Register.class, OperandSize.DWORD)), RM_R.getInstance());
        public static final Instruction RM64_R64 = Instruction.of(Opcode.of(0x19), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD), Constraint.of(Register.class, OperandSize.QWORD)), RM_R.getInstance());
        public static final Instruction R8_RM8 = Instruction.of(Opcode.of(0x1A), Signature.of(Constraint.of(Register.class, OperandSize.BYTE), Constraint.of(RegisterAddressor.class, OperandSize.BYTE)), R_RM.getInstance());
        public static final Instruction R16_RM16 = Instruction.of(Opcode.of(0x1B), Signature.of(Constraint.of(Register.class, OperandSize.WORD), Constraint.of(RegisterAddressor.class, OperandSize.WORD)), R_RM.getInstance());
        public static final Instruction R32_RM32 = Instruction.of(Opcode.of(0x1B), Signature.of(Constraint.of(Register.class, OperandSize.DWORD), Constraint.of(RegisterAddressor.class, OperandSize.DWORD)), R_RM.getInstance());
        public static final Instruction R64_RM64 = Instruction.of(Opcode.of(0x1B), Signature.of(Constraint.of(Register.class, OperandSize.QWORD), Constraint.of(RegisterAddressor.class, OperandSize.QWORD)), R_RM.getInstance());

    }

    public static final class AND implements InstructionHolder {

        public static final Instruction AL_IMM8 = Instruction.of(Opcode.of(0x24), Signature.of(Constraint.of(Immediate.class, OperandSize.BYTE)), IMM.getInstance());
        public static final Instruction AX_IMM16 = Instruction.of(Opcode.of(0x25), Signature.of(Constraint.of(Immediate.class, OperandSize.WORD)), IMM.getInstance());
        public static final Instruction EAX_IMM32 = Instruction.of(Opcode.of(0x25), Signature.of(Constraint.of(Immediate.class, OperandSize.DWORD)), IMM.getInstance());
        public static final Instruction RAX_IMM32 = Instruction.of(Opcode.of(0x25), Signature.of(Constraint.of(Immediate.class, OperandSize.QWORD)), IMM.getInstance());
        public static final Instruction RM8_IMM8 = Instruction.of(Opcode.of(0x80, 4), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE), Constraint.of(Immediate.class, OperandSize.BYTE)), RM_IMM.getInstance());
        public static final Instruction RM16_IMM16 = Instruction.of(Opcode.of(0x81, 4), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD), Constraint.of(Immediate.class, OperandSize.WORD)), RM_IMM.getInstance());
        public static final Instruction RM32_IMM32 = Instruction.of(Opcode.of(0x81, 4), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.DWORD), Constraint.of(Immediate.class, OperandSize.DWORD)), RM_IMM.getInstance());
        public static final Instruction RM64_IMM32 = Instruction.of(Opcode.of(0x81, 4), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD), Constraint.of(Immediate.class, OperandSize.DWORD)), RM_IMM.getInstance());
        public static final Instruction RM64_IMM8 = Instruction.of(Opcode.of(0x83, 4), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD, OperandSize.DWORD, OperandSize.QWORD), Constraint.of(Immediate.class, OperandSize.BYTE)), RM_IMM.getInstance());
        public static final Instruction RM8_R8 = Instruction.of(Opcode.of(0x20), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE), Constraint.of(Register.class, OperandSize.BYTE)), RM_R.getInstance());
        public static final Instruction RM16_R16 = Instruction.of(Opcode.of(0x21), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD), Constraint.of(Register.class, OperandSize.WORD)), RM_R.getInstance());
        public static final Instruction RM32_R32 = Instruction.of(Opcode.of(0x21), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.DWORD), Constraint.of(Register.class, OperandSize.DWORD)), RM_R.getInstance());
        public static final Instruction RM64_R64 = Instruction.of(Opcode.of(0x21), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD), Constraint.of(Register.class, OperandSize.QWORD)), RM_R.getInstance());
        public static final Instruction R8_RM8 = Instruction.of(Opcode.of(0x22), Signature.of(Constraint.of(Register.class, OperandSize.BYTE), Constraint.of(RegisterAddressor.class, OperandSize.BYTE)), R_RM.getInstance());
        public static final Instruction R16_RM16 = Instruction.of(Opcode.of(0x23), Signature.of(Constraint.of(Register.class, OperandSize.WORD), Constraint.of(RegisterAddressor.class, OperandSize.WORD)), R_RM.getInstance());
        public static final Instruction R32_RM32 = Instruction.of(Opcode.of(0x23), Signature.of(Constraint.of(Register.class, OperandSize.DWORD), Constraint.of(RegisterAddressor.class, OperandSize.DWORD)), R_RM.getInstance());
        public static final Instruction R64_RM64 = Instruction.of(Opcode.of(0x23), Signature.of(Constraint.of(Register.class, OperandSize.QWORD), Constraint.of(RegisterAddressor.class, OperandSize.QWORD)), R_RM.getInstance());

    }

    public static final class SUB implements InstructionHolder {

        public static final Instruction AL_IMM8 = Instruction.of(Opcode.of(0x2C), Signature.of(Constraint.of(Immediate.class, OperandSize.BYTE)), IMM.getInstance());
        public static final Instruction AX_IMM16 = Instruction.of(Opcode.of(0x2D), Signature.of(Constraint.of(Immediate.class, OperandSize.WORD)), IMM.getInstance());
        public static final Instruction EAX_IMM32 = Instruction.of(Opcode.of(0x2D), Signature.of(Constraint.of(Immediate.class, OperandSize.DWORD)), IMM.getInstance());
        public static final Instruction RAX_IMM32 = Instruction.of(Opcode.of(0x2D), Signature.of(Constraint.of(Immediate.class, OperandSize.QWORD)), IMM.getInstance());
        public static final Instruction RM8_IMM8 = Instruction.of(Opcode.of(0x80, 5), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE), Constraint.of(Immediate.class, OperandSize.BYTE)), RM_IMM.getInstance());
        public static final Instruction RM16_IMM16 = Instruction.of(Opcode.of(0x81, 5), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD), Constraint.of(Immediate.class, OperandSize.WORD)), RM_IMM.getInstance());
        public static final Instruction RM32_IMM32 = Instruction.of(Opcode.of(0x81, 5), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.DWORD), Constraint.of(Immediate.class, OperandSize.DWORD)), RM_IMM.getInstance());
        public static final Instruction RM64_IMM32 = Instruction.of(Opcode.of(0x81, 5), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD), Constraint.of(Immediate.class, OperandSize.DWORD)), RM_IMM.getInstance());
        public static final Instruction RM64_IMM8 = Instruction.of(Opcode.of(0x83, 5), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD, OperandSize.DWORD, OperandSize.QWORD), Constraint.of(Immediate.class, OperandSize.BYTE)), RM_IMM.getInstance());
        public static final Instruction RM8_R8 = Instruction.of(Opcode.of(0x28), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE), Constraint.of(Register.class, OperandSize.BYTE)), RM_R.getInstance());
        public static final Instruction RM16_R16 = Instruction.of(Opcode.of(0x29), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD), Constraint.of(Register.class, OperandSize.WORD)), RM_R.getInstance());
        public static final Instruction RM32_R32 = Instruction.of(Opcode.of(0x29), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.DWORD), Constraint.of(Register.class, OperandSize.DWORD)), RM_R.getInstance());
        public static final Instruction RM64_R64 = Instruction.of(Opcode.of(0x29), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD), Constraint.of(Register.class, OperandSize.QWORD)), RM_R.getInstance());
        public static final Instruction R8_RM8 = Instruction.of(Opcode.of(0x2A), Signature.of(Constraint.of(Register.class, OperandSize.BYTE), Constraint.of(RegisterAddressor.class, OperandSize.BYTE)), R_RM.getInstance());
        public static final Instruction R16_RM16 = Instruction.of(Opcode.of(0x2B), Signature.of(Constraint.of(Register.class, OperandSize.WORD), Constraint.of(RegisterAddressor.class, OperandSize.WORD)), R_RM.getInstance());
        public static final Instruction R32_RM32 = Instruction.of(Opcode.of(0x2B), Signature.of(Constraint.of(Register.class, OperandSize.DWORD), Constraint.of(RegisterAddressor.class, OperandSize.DWORD)), R_RM.getInstance());
        public static final Instruction R64_RM64 = Instruction.of(Opcode.of(0x2B), Signature.of(Constraint.of(Register.class, OperandSize.QWORD), Constraint.of(RegisterAddressor.class, OperandSize.QWORD)), R_RM.getInstance());

    }

    public static class XOR implements InstructionHolder {

        public static final Instruction AL_IMM8 = Instruction.of(Opcode.of(0x34), Signature.of(Constraint.of(Immediate.class, OperandSize.BYTE)), IMM.getInstance());
        public static final Instruction AX_IMM16 = Instruction.of(Opcode.of(0x35), Signature.of(Constraint.of(Immediate.class, OperandSize.WORD)), IMM.getInstance());
        public static final Instruction EAX_IMM32 = Instruction.of(Opcode.of(0x35), Signature.of(Constraint.of(Immediate.class, OperandSize.DWORD)), IMM.getInstance());
        public static final Instruction RAX_IMM32 = Instruction.of(Opcode.of(0x35), Signature.of(Constraint.of(Immediate.class, OperandSize.QWORD)), IMM.getInstance());
        public static final Instruction RM8_IMM8 = Instruction.of(Opcode.of(0x80, 6), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE), Constraint.of(Immediate.class, OperandSize.BYTE)), RM_IMM.getInstance());
        public static final Instruction RM16_IMM16 = Instruction.of(Opcode.of(0x81, 6), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD), Constraint.of(Immediate.class, OperandSize.WORD)), RM_IMM.getInstance());
        public static final Instruction RM32_IMM32 = Instruction.of(Opcode.of(0x81, 6), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.DWORD), Constraint.of(Immediate.class, OperandSize.DWORD)), RM_IMM.getInstance());
        public static final Instruction RM64_IMM32 = Instruction.of(Opcode.of(0x81, 6), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD), Constraint.of(Immediate.class, OperandSize.DWORD)), RM_IMM.getInstance());
        public static final Instruction RM64_IMM8 = Instruction.of(Opcode.of(0x83, 6), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD, OperandSize.DWORD, OperandSize.QWORD), Constraint.of(Immediate.class, OperandSize.BYTE)), RM_IMM.getInstance());
        public static final Instruction RM8_R8 = Instruction.of(Opcode.of(0x30), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE), Constraint.of(Register.class, OperandSize.BYTE)), RM_R.getInstance());
        public static final Instruction RM16_R16 = Instruction.of(Opcode.of(0x31), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD), Constraint.of(Register.class, OperandSize.WORD)), RM_R.getInstance());
        public static final Instruction RM32_R32 = Instruction.of(Opcode.of(0x31), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.DWORD), Constraint.of(Register.class, OperandSize.DWORD)), RM_R.getInstance());
        public static final Instruction RM64_R64 = Instruction.of(Opcode.of(0x31), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD), Constraint.of(Register.class, OperandSize.QWORD)), RM_R.getInstance());
        public static final Instruction R8_RM8 = Instruction.of(Opcode.of(0x32), Signature.of(Constraint.of(Register.class, OperandSize.BYTE), Constraint.of(RegisterAddressor.class, OperandSize.BYTE)), R_RM.getInstance());
        public static final Instruction R16_RM16 = Instruction.of(Opcode.of(0x33), Signature.of(Constraint.of(Register.class, OperandSize.WORD), Constraint.of(RegisterAddressor.class, OperandSize.WORD)), R_RM.getInstance());
        public static final Instruction R32_RM32 = Instruction.of(Opcode.of(0x33), Signature.of(Constraint.of(Register.class, OperandSize.DWORD), Constraint.of(RegisterAddressor.class, OperandSize.DWORD)), R_RM.getInstance());
        public static final Instruction R64_RM64 = Instruction.of(Opcode.of(0x33), Signature.of(Constraint.of(Register.class, OperandSize.QWORD), Constraint.of(RegisterAddressor.class, OperandSize.QWORD)), R_RM.getInstance());

    }

    public static final class CMP implements InstructionHolder {

        public static final Instruction AL_IMM8 = Instruction.of(Opcode.of(0x3C), Signature.of(Constraint.of(Immediate.class, OperandSize.BYTE)), IMM.getInstance());
        public static final Instruction AX_IMM16 = Instruction.of(Opcode.of(0x3D), Signature.of(Constraint.of(Immediate.class, OperandSize.WORD)), IMM.getInstance());
        public static final Instruction EAX_IMM32 = Instruction.of(Opcode.of(0x3D), Signature.of(Constraint.of(Immediate.class, OperandSize.DWORD)), IMM.getInstance());
        public static final Instruction RAX_IMM32 = Instruction.of(Opcode.of(0x3D), Signature.of(Constraint.of(Immediate.class, OperandSize.QWORD)), IMM.getInstance());
        public static final Instruction RM8_IMM8 = Instruction.of(Opcode.of(0x80, 7), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE), Constraint.of(Immediate.class, OperandSize.BYTE)), RM_IMM.getInstance());
        public static final Instruction RM16_IMM16 = Instruction.of(Opcode.of(0x81, 7), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD), Constraint.of(Immediate.class, OperandSize.WORD)), RM_IMM.getInstance());
        public static final Instruction RM32_IMM32 = Instruction.of(Opcode.of(0x81, 7), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.DWORD), Constraint.of(Immediate.class, OperandSize.DWORD)), RM_IMM.getInstance());
        public static final Instruction RM64_IMM32 = Instruction.of(Opcode.of(0x81, 7), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD), Constraint.of(Immediate.class, OperandSize.DWORD)), RM_IMM.getInstance());
        public static final Instruction RM64_IMM8 = Instruction.of(Opcode.of(0x83, 7), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD, OperandSize.DWORD, OperandSize.QWORD), Constraint.of(Immediate.class, OperandSize.BYTE)), RM_IMM.getInstance());
        public static final Instruction RM8_R8 = Instruction.of(Opcode.of(0x38), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE), Constraint.of(Register.class, OperandSize.BYTE)), RM_R.getInstance());
        public static final Instruction RM16_R16 = Instruction.of(Opcode.of(0x39), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.WORD), Constraint.of(Register.class, OperandSize.WORD)), RM_R.getInstance());
        public static final Instruction RM32_R32 = Instruction.of(Opcode.of(0x39), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.DWORD), Constraint.of(Register.class, OperandSize.DWORD)), RM_R.getInstance());
        public static final Instruction RM64_R64 = Instruction.of(Opcode.of(0x39), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD), Constraint.of(Register.class, OperandSize.QWORD)), RM_R.getInstance());
        public static final Instruction R8_RM8 = Instruction.of(Opcode.of(0x3A), Signature.of(Constraint.of(Register.class, OperandSize.BYTE), Constraint.of(RegisterAddressor.class, OperandSize.BYTE)), R_RM.getInstance());
        public static final Instruction R16_RM16 = Instruction.of(Opcode.of(0x3B), Signature.of(Constraint.of(Register.class, OperandSize.WORD), Constraint.of(RegisterAddressor.class, OperandSize.WORD)), R_RM.getInstance());
        public static final Instruction R32_RM32 = Instruction.of(Opcode.of(0x3B), Signature.of(Constraint.of(Register.class, OperandSize.DWORD), Constraint.of(RegisterAddressor.class, OperandSize.DWORD)), R_RM.getInstance());
        public static final Instruction R64_RM64 = Instruction.of(Opcode.of(0x3B), Signature.of(Constraint.of(Register.class, OperandSize.QWORD), Constraint.of(RegisterAddressor.class, OperandSize.QWORD)), R_RM.getInstance());

    }

    public static final byte CALLF_M64 = (byte) 0xFF;
    public static final byte JMP_RM32 = (byte) 0xFF;
    public static final byte JMP_RM64 = (byte) 0xFF;
    public static final Instruction TEST_RM8_IMM8 = Instruction.of(Opcode.of(0xF6), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE), Constraint.of(Immediate.class, OperandSize.BYTE)), RM_IMM.getInstance());
    public static final Instruction NOT_RM8 = Instruction.of(Opcode.of(0xF6, 2), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE)), RM.getInstance());
    public static final Instruction NEG_RM8 = Instruction.of(Opcode.of(0xF6, 3), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE)), RM.getInstance());
    public static final Instruction MUL_RM8 = Instruction.of(Opcode.of(0xF6, 4), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE)), RM.getInstance());
    public static final Instruction IMUL_RM8 = Instruction.of(Opcode.of(0xF6, 5), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE)), RM.getInstance());
    public static final Instruction DIV_RM8 = Instruction.of(Opcode.of(0xF6, 6), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE)), RM.getInstance());
    public static final Instruction IDIV_RM8 = Instruction.of(Opcode.of(0xF6, 7), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE)), RM.getInstance());
    public static final Instruction TEST_RM64_IMM32 = Instruction.of(Opcode.of(0xF7), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD), Constraint.of(Immediate.class, OperandSize.DWORD)), RM_IMM.getInstance());
    public static final Instruction NOT_RM64 = Instruction.of(Opcode.of(0xF7, 2), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD)), RM.getInstance());
    public static final Instruction NEG_RM64 = Instruction.of(Opcode.of(0xF7, 3), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD)), RM.getInstance());
    public static final Instruction MUL_RM64 = Instruction.of(Opcode.of(0xF7, 4), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD)), RM.getInstance());
    public static final Instruction IMUL_RM64 = Instruction.of(Opcode.of(0xF7, 5), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD)), RM.getInstance());
    public static final Instruction DIV_RM64 = Instruction.of(Opcode.of(0xF7, 6), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD)), RM.getInstance());
    public static final Instruction IDIV_RM64 = Instruction.of(Opcode.of(0xF7, 7), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD)), RM.getInstance());
    public static final byte CLC = (byte) 0xF8;
    public static final byte STC = (byte) 0xF9;
    public static final byte CLI = (byte) 0xFA;
    public static final byte STI = (byte) 0xFB;
    public static final byte CLD = (byte) 0xFC;
    public static final byte STD = (byte) 0xFD;

    public static final Instruction INC_RM64 = Instruction.of(Opcode.of(0xFF), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD)), RM.getInstance());
    public static final Instruction DEC_RM64 = Instruction.of(Opcode.of(0xFF, 1), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD)), RM.getInstance());
    public static final Instruction CALL_RM64 = Instruction.of(Opcode.of(0xFF, 2), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD)), RM.getInstance());

    public static class MOV implements InstructionHolder {

        public static final Instruction R64_IMM64 = Instruction.of(Opcode.of(0xB8), Signature.of(Constraint.of(Register.class, OperandSize.QWORD), Constraint.of(Immediate.class, OperandSize.QWORD)), OI.getInstance());
        public static final Instruction RM8_R8 = Instruction.of(Opcode.of(0x88), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.BYTE), Constraint.of(Register.class, OperandSize.BYTE)), RM_R.getInstance());
        public static final Instruction RM64_R64 = Instruction.of(Opcode.of(0x89), Signature.of(Constraint.of(RegisterAddressor.class, OperandSize.QWORD), Constraint.of(Register.class, OperandSize.QWORD)), RM_R.getInstance());

    }

    public static final Instruction RETN_IMM16 = Instruction.of(Opcode.of(0xC2), Signature.of(Constraint.of(Immediate.class, OperandSize.WORD)), IMM.getInstance());
    public static final Instruction RETN = Instruction.of(Opcode.of(0xC3), Signature.EMPTY, ZO.getInstance());

    public static final byte INC_RM8 = (byte) 0xFE;
    public static final byte DEC_RM8 = (byte) 0xFE;
    public static final byte CALL_RM32 = (byte) 0xFF;

    public static final Instruction SYSCALL = Instruction.of(Opcode.of(new byte[]{0x0F, 0x05}), Signature.EMPTY, ZO.getInstance());

    /**
     * Resolves and caches the {@link Instruction} values declared within the provided {@link InstructionHolder} class
     * before returning them. If the class has been processed in the past the cached values will be returned.
     *
     * @param clazz The holder class.
     * @return The computed or cached values.
     */
    public static List<Instruction> resolve(@NotNull final Class<? extends InstructionHolder> clazz) {

        return INSTRUCTION_CACHE.computeIfAbsent(clazz, (input) -> {
            try {
                final List<Instruction> instructions = new ArrayList<>();
                final Field[] fields = input.getDeclaredFields();
                for (final Field field : fields) {
                    final Object value = field.get(null);
                    if (!Instruction.class.isAssignableFrom(value.getClass()))
                        continue;
                    instructions.add((Instruction) value);
                }
                return instructions;
            } catch (final ReflectiveOperationException ex) {
                LOGGER.log(Level.SEVERE, "Failed to resolve instruction fields of instruction holder class '{0}': {1}", new Object[]{
                        clazz.getName(), ex.getMessage()
                });
                return null;
            }
        });

    }

    private Instructions() {

        throw new UnsupportedOperationException();

    }

}
