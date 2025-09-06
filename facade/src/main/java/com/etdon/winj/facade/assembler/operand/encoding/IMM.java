package com.etdon.winj.facade.assembler.operand.encoding;

import com.etdon.winj.facade.assembler.ByteBuffer;
import com.etdon.winj.facade.assembler.Opcode;
import com.etdon.winj.facade.assembler.Opcodes;
import com.etdon.winj.facade.assembler.Prefix;
import com.etdon.winj.facade.assembler.operand.Operand;
import com.etdon.winj.facade.assembler.operand.OperandSize;
import com.etdon.winj.facade.assembler.operand.impl.Immediate;

public final class IMM extends InstructionEncoding {

    private static class IMMSingleton {

        private static final IMM INSTANCE = new IMM();

    }

    private IMM() {

    }

    @Override
    public byte[] process(final Opcode opcode, final Operand... operands) {

        final Immediate immediate = (Immediate) operands[0];
        final ByteBuffer byteBuffer = ByteBuffer.size(3);
        final boolean wideMode = immediate.getSize() == OperandSize.QWORD;
        final byte prefix = Prefix.builder().w(wideMode).r(false).x(false).b(false).build();
        if (prefix != Opcodes.Prefix.REX)
            byteBuffer.put(prefix);
        byteBuffer.put(opcode.getValues());
        byteBuffer.put(immediate.marshal());

        return byteBuffer.get();

    }

    public static IMM getInstance() {

        return IMMSingleton.INSTANCE;

    }

}
