package com.etdon.winj.facade.assembler.operand.encoding;

import com.etdon.winj.facade.assembler.ByteBuffer;
import com.etdon.winj.facade.assembler.Opcode;
import com.etdon.winj.facade.assembler.Opcodes;
import com.etdon.winj.facade.assembler.Prefix;
import com.etdon.winj.facade.assembler.operand.Operand;
import com.etdon.winj.facade.assembler.operand.OperandSize;
import com.etdon.winj.facade.assembler.operand.impl.Immediate;
import com.etdon.winj.facade.assembler.register.Register;

public final class OI extends InstructionEncoding {

    private static class OISingleton {

        private static final OI INSTANCE = new OI();

    }

    private OI() {

    }

    @Override
    public byte[] process(final Opcode opcode, final Operand... operands) {

        final Register register = (Register) operands[0];
        final Immediate immediate = (Immediate) operands[1];
        final ByteBuffer byteBuffer = ByteBuffer.size(10);
        final boolean wideMode = immediate.getSize() == OperandSize.QWORD;
        final byte prefix = Prefix.builder().w(wideMode).r(false).x(false).b(register.isExtended()).build();
        if (prefix != Opcodes.Prefix.REX)
            byteBuffer.put(prefix);
        byteBuffer.put((byte) (opcode.getValues()[0] | register.getValue()));
        byteBuffer.put(immediate.marshal());

        return byteBuffer.get();

    }

    public static OI getInstance() {

        return OISingleton.INSTANCE;

    }

}
