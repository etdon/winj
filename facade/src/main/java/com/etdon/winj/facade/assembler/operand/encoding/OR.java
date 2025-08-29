package com.etdon.winj.facade.assembler.operand.encoding;

import com.etdon.winj.facade.assembler.ByteBuffer;
import com.etdon.winj.facade.assembler.Opcode;
import com.etdon.winj.facade.assembler.Opcodes;
import com.etdon.winj.facade.assembler.Prefix;
import com.etdon.winj.facade.assembler.operand.Operand;
import com.etdon.winj.facade.assembler.operand.OperandSize;
import com.etdon.winj.facade.assembler.register.Register;

public final class OR extends InstructionEncoding {

    private static class OSingleton {

        private static final OR INSTANCE = new OR();

    }

    private OR() {

    }

    @Override
    public byte[] process(final Opcode opcode, final Operand... operands) {

        final Register register = (Register) operands[0];
        final ByteBuffer byteBuffer = ByteBuffer.size(2);
        final byte prefix = Prefix.builder().w(false).r(false).x(false).b(register.isExtended()).build();
        if (prefix != Opcodes.Prefix.REX)
            byteBuffer.put(prefix);
        byteBuffer.put((byte) (opcode.getValues()[0] | register.getValue()));

        return byteBuffer.get();

    }

    public static OR getInstance() {

        return OSingleton.INSTANCE;

    }

}
