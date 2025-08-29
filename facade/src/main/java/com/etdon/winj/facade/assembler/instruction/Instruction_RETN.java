package com.etdon.winj.facade.assembler.instruction;

import com.etdon.winj.facade.assembler.Opcodes;

public final class Instruction_RETN extends Instruction {

    private Instruction_RETN() {

    }

    @Override
    public byte[] build() {

        return new byte[]{Opcodes.Primary.RETN};

    }

    public static Instruction_RETN of() {

        return new Instruction_RETN();

    }

}
