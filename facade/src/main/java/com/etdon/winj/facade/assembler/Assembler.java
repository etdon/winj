package com.etdon.winj.facade.assembler;

import com.etdon.commons.util.Exceptional;
import com.etdon.winj.facade.assembler.instruction.Instruction;
import com.etdon.winj.facade.assembler.instruction.Instructions;
import com.etdon.winj.facade.assembler.operand.Operand;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class Assembler {

    public byte[] mov(@NotNull final Operand o1, @NotNull final Operand o2) {

        final List<Instruction> instructions = Instructions.MOV.ENTRIES;
        for (final Instruction instruction : instructions) {
            if (instruction.signature().isSatisfying(o1, o2))
                return instruction.create(o1, o2);
        }

        throw Exceptional.of(IllegalArgumentException.class, "(mov) Failed to find a matching signature for the provided operands: {} {}", o1, o2);

    }

}
