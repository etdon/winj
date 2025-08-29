package com.etdon.winj.facade.assembler.operand.encoding;

import com.etdon.winj.facade.assembler.Opcode;
import com.etdon.winj.facade.assembler.operand.Operand;

public final class ZO extends InstructionEncoding {

    private static class ZOSingleton {

        private static final ZO INSTANCE = new ZO();

    }

    private ZO() {

    }

    @Override
    public byte[] process(final Opcode opcode, final Operand... operands) {

        return opcode.getValues();

    }

    public static ZO getInstance() {

        return ZOSingleton.INSTANCE;

    }

}
