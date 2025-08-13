package com.etdon.winj.facade.op.instruction;

import com.etdon.winj.facade.op.Opcode;

public final class Instruction_SYSCALL extends Instruction {

    private Instruction_SYSCALL() {

    }

    @Override
    public byte[] build() {

        return Opcode.TwoByte.SYSCALL;

    }

    public static Instruction_SYSCALL of() {

        return new Instruction_SYSCALL();

    }

}
