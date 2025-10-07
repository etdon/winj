package com.etdon.winj.facade.assembler;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.util.Exceptional;
import com.etdon.winj.facade.assembler.instruction.Instruction;
import com.etdon.winj.facade.assembler.instruction.InstructionHolder;
import com.etdon.winj.facade.assembler.instruction.Instructions;
import com.etdon.winj.facade.assembler.operand.Operand;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class Assembler {

    public byte[] add(@NotNull final Operand o1, @NotNull final Operand o2) {

        Preconditions.checkNotNull(o1);
        Preconditions.checkNotNull(o2);
        return this.resolveInstruction(Instructions.ADD.class, o1, o2);

    }

    public byte[] or(@NotNull final Operand o1, @NotNull final Operand o2) {

        Preconditions.checkNotNull(o1);
        Preconditions.checkNotNull(o2);
        return this.resolveInstruction(Instructions.OR.class, o1, o2);

    }

    public byte[] adc(@NotNull final Operand o1, @NotNull final Operand o2) {

        Preconditions.checkNotNull(o1);
        Preconditions.checkNotNull(o2);
        return this.resolveInstruction(Instructions.ADC.class, o1, o2);

    }

    public byte[] sbb(@NotNull final Operand o1, @NotNull final Operand o2) {

        Preconditions.checkNotNull(o1);
        Preconditions.checkNotNull(o2);
        return this.resolveInstruction(Instructions.SBB.class, o1, o2);

    }

    public byte[] and(@NotNull final Operand o1, @NotNull final Operand o2) {

        Preconditions.checkNotNull(o1);
        Preconditions.checkNotNull(o2);
        return this.resolveInstruction(Instructions.AND.class, o1, o2);

    }

    public byte[] sub(@NotNull final Operand o1, @NotNull final Operand o2) {

        Preconditions.checkNotNull(o1);
        Preconditions.checkNotNull(o2);
        return this.resolveInstruction(Instructions.SUB.class, o1, o2);

    }

    public byte[] xor(@NotNull final Operand o1, @NotNull final Operand o2) {

        Preconditions.checkNotNull(o1);
        Preconditions.checkNotNull(o2);
        return this.resolveInstruction(Instructions.XOR.class, o1, o2);

    }

    public byte[] cmp(@NotNull final Operand o1, @NotNull final Operand o2) {

        Preconditions.checkNotNull(o1);
        Preconditions.checkNotNull(o2);
        return this.resolveInstruction(Instructions.CMP.class, o1, o2);

    }

    public byte[] mov(@NotNull final Operand o1, @NotNull final Operand o2) {

        Preconditions.checkNotNull(o1);
        Preconditions.checkNotNull(o2);
        return this.resolveInstruction(Instructions.MOV.class, o1, o2);

    }

    private byte[] resolveInstruction(@NotNull final Class<? extends InstructionHolder> clazz, @NotNull final Operand... operands) {

        final List<Instruction> instructions = Instructions.resolve(clazz);
        for (final Instruction instruction : instructions) {
            if (instruction.signature().isSatisfying(operands))
                return instruction.create(operands);
        }

        throw Exceptional.of(IllegalArgumentException.class, "({}) Failed to find a matching signature for the provided operands: {}", clazz.getName(), Arrays.stream(operands).map((operand) -> operand.getClass().getName()).collect(Collectors.joining(", ")));

    }

}
