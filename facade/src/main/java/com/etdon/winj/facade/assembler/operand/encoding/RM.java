package com.etdon.winj.facade.assembler.operand.encoding;

import com.etdon.winj.util.ByteBuffer;
import com.etdon.winj.facade.assembler.Opcode;
import com.etdon.winj.facade.assembler.Opcodes;
import com.etdon.winj.facade.assembler.Prefix;
import com.etdon.winj.facade.assembler.address.RegisterAddressor;
import com.etdon.winj.facade.assembler.operand.Operand;
import com.etdon.winj.facade.assembler.operand.OperandSize;

public final class RM extends InstructionEncoding {

    private static class RMSingleton {

        private static final RM INSTANCE = new RM();

    }

    private RM() {

    }

    @Override
    public byte[] process(final Opcode opcode, final Operand... operands) {

        if (!(operands[0] instanceof RegisterAddressor rm))
            throw new IllegalArgumentException("Expected RegisterAddressor operand, got " + operands[0].getClass().getName());

        final ByteBuffer byteBuffer = ByteBuffer.size(4);
        final boolean wideMode = rm.getSize() == OperandSize.QWORD;
        final boolean sibExtension = rm.requiresSIB() && rm.getSIB().getIndex().isExtended();
        final byte prefix = Prefix.builder().w(wideMode).r(false).x(sibExtension).b(rm.getRegister().isExtended()).build();
        if (prefix != Opcodes.Prefix.REX)
            byteBuffer.put(prefix);
        byteBuffer.put(opcode.getValues());
        byteBuffer.put(
                Opcodes.ModRM.builder()
                        .mod(rm.getMod())
                        .reg(opcode.getExtension())
                        .rm(rm.getRegister().getValue())
                        .build()
        );
        if (rm.requiresSIB())
            byteBuffer.put(rm.getSIB().toByte());

        return byteBuffer.get();

    }

    public static RM getInstance() {

        return RMSingleton.INSTANCE;

    }

}
