package com.etdon.winj.facade.assembler.operand.encoding;

import com.etdon.winj.facade.assembler.ByteBuffer;
import com.etdon.winj.facade.assembler.Opcode;
import com.etdon.winj.facade.assembler.Opcodes;
import com.etdon.winj.facade.assembler.Prefix;
import com.etdon.winj.facade.assembler.address.RegisterAddressor;
import com.etdon.winj.facade.assembler.operand.Operand;
import com.etdon.winj.facade.assembler.operand.OperandSize;
import com.etdon.winj.facade.assembler.register.Register;

public final class R_RM extends InstructionEncoding {

    private static class R_RMSingleton {

        private static final R_RM INSTANCE = new R_RM();

    }

    private R_RM() {

    }

    @Override
    public byte[] process(final Opcode opcode, final Operand... operands) {

        if (!(operands[0] instanceof Register destination))
            throw new IllegalArgumentException("Expected Register operand, got " + operands[0].getClass().getName());
        if (!(operands[1] instanceof RegisterAddressor source))
            throw new IllegalArgumentException("Expected RegisterAddressor operand, got " + operands[1].getClass().getName());

        final ByteBuffer byteBuffer = ByteBuffer.size(4);
        final boolean wideMode = destination.getSize() == OperandSize.QWORD || source.getSize() == OperandSize.QWORD;
        final boolean sibExtension = source.requiresSIB() && source.getSIB().getIndex().isExtended();
        final byte prefix = Prefix.builder().w(wideMode).r(destination.isExtended()).x(sibExtension).b(source.getRegister().isExtended()).build();
        if (prefix != Opcodes.Prefix.REX)
            byteBuffer.put(prefix);
        byteBuffer.put(opcode.getValues());
        byteBuffer.put(
                Opcodes.ModRM.builder()
                        .mod(source.getMod())
                        .reg(destination.getValue())
                        .rm(source.getRegister().getValue())
                        .build()
        );
        if (source.requiresSIB())
            byteBuffer.put(source.getSIB().toByte());

        return byteBuffer.get();

    }

    public static R_RM getInstance() {

        return R_RMSingleton.INSTANCE;

    }

}
