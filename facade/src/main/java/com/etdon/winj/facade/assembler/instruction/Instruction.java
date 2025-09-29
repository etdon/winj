package com.etdon.winj.facade.assembler.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.assembler.Opcode;
import com.etdon.winj.facade.assembler.operand.Operand;
import com.etdon.winj.facade.assembler.operand.encoding.InstructionEncoding;
import org.jetbrains.annotations.NotNull;

public record Instruction(Opcode opcode, Signature signature, InstructionEncoding encoding) {

    public byte[] create(final Operand... operands) {

        return this.encoding.process(this.opcode, operands);

    }

    public static Instruction of(@NotNull final Opcode opcode, @NotNull final Signature signature, @NotNull final InstructionEncoding encoding) {

        Preconditions.checkNotNull(opcode);
        Preconditions.checkNotNull(encoding);

        return new Instruction(opcode, signature, encoding);

    }

}
