package com.etdon.winj.facade.assembler.operand.encoding;

import com.etdon.commons.io.ByteBuffer;
import com.etdon.winj.facade.assembler.Opcode;
import com.etdon.winj.facade.assembler.Opcodes;
import com.etdon.winj.facade.assembler.Prefix;
import com.etdon.winj.facade.assembler.address.RegisterAddressor;
import com.etdon.winj.facade.assembler.operand.Operand;
import com.etdon.winj.facade.assembler.operand.OperandSize;
import com.etdon.winj.facade.assembler.operand.impl.Immediate;

public final class RM_IMM extends InstructionEncoding {

    private static class RM_IMMSingleton {

        private static final RM_IMM INSTANCE = new RM_IMM();

    }

    private RM_IMM() {

    }

    @Override
    public byte[] process(final Opcode opcode, final Operand... operands) {

        if (!(operands[0] instanceof RegisterAddressor destination))
            throw new IllegalArgumentException("Expected RegisterAddressor operand, got " + operands[0].getClass().getName());
        if (!(operands[1] instanceof Immediate immediate))
            throw new IllegalArgumentException("Expected Immediate operand, got " + operands[1].getClass().getName());

        final ByteBuffer byteBuffer = ByteBuffer.size(7);
        final boolean wideMode = destination.getSize() == OperandSize.QWORD || immediate.getSize() == OperandSize.QWORD;
        final boolean sibExtension = destination.requiresSIB() && destination.getSIB().getIndex().isExtended();
        final byte prefix = Prefix.builder().w(wideMode).r(false).x(sibExtension).b(destination.getRegister().isExtended()).build();
        if (prefix != Opcodes.Prefix.REX)
            byteBuffer.put(prefix);
        byteBuffer.put(opcode.getValues());
        byteBuffer.put(
                Opcodes.ModRM.builder()
                        .mod(destination.getMod())
                        .reg(opcode.getExtension())
                        .rm(destination.getRegister().getValue())
                        .build()
        );
        if (destination.requiresSIB())
            byteBuffer.put(destination.getSIB().toByte());
        byteBuffer.put(immediate.marshal());

        return byteBuffer.get();

    }

    public static RM_IMM getInstance() {

        return RM_IMMSingleton.INSTANCE;

    }

}
