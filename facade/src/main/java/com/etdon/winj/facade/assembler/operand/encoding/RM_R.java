package com.etdon.winj.facade.assembler.operand.encoding;

import com.etdon.winj.facade.assembler.ByteBuffer;
import com.etdon.winj.facade.assembler.Opcode;
import com.etdon.winj.facade.assembler.Opcodes;
import com.etdon.winj.facade.assembler.Prefix;
import com.etdon.winj.facade.assembler.address.RegisterAddressor;
import com.etdon.winj.facade.assembler.operand.Operand;
import com.etdon.winj.facade.assembler.operand.OperandSize;
import com.etdon.winj.facade.assembler.register.Register;

public final class RM_R extends InstructionEncoding {

    private static class RM_RSingleton {

        private static final RM_R INSTANCE = new RM_R();

    }

    private RM_R() {

    }

    @Override
    public byte[] process(final Opcode opcode, final Operand... operands) {

        if (!(operands[0] instanceof RegisterAddressor destination))
            throw new IllegalArgumentException("Expected RegisterAddressor operand, got " + operands[0].getClass().getName());
        if (!(operands[1] instanceof Register source))
            throw new IllegalArgumentException("Expected Immediate operand, got " + operands[1].getClass().getName());

        final ByteBuffer byteBuffer = ByteBuffer.size(4);
        final boolean wideMode = destination.getSize() == OperandSize.QWORD || source.getSize() == OperandSize.QWORD;
        final boolean sibExtension = destination.requiresSIB() && destination.getSIB().getIndex().isExtended();
        final byte prefix = Prefix.builder().w(wideMode).r(source.isExtended()).x(sibExtension).b(destination.getRegister().isExtended()).build();
        if (prefix != Opcodes.Prefix.REX)
            byteBuffer.put(prefix);
        byteBuffer.put(opcode.getValues());
        byteBuffer.put(
                Opcodes.ModRM.builder()
                        .mod(destination.getMod())
                        .reg(source.getValue())
                        .rm(destination.getRegister().getValue())
                        .build()
        );
        if (destination.requiresSIB())
            byteBuffer.put(destination.getSIB().toByte());

        return byteBuffer.get();

    }

    public static RM_R getInstance() {

        return RM_RSingleton.INSTANCE;

    }

}
