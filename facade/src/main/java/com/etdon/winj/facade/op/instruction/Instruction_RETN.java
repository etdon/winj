package com.etdon.winj.facade.op.instruction;

import com.etdon.winj.facade.op.Opcode;

public final class Instruction_RETN extends Instruction {

    private Instruction_RETN() {

    }

    @Override
    public byte[] build() {

        return new byte[]{Opcode.Primary.RETN};

    }

    public static Instruction_RETN of() {

        return new Instruction_RETN();

    }

}
