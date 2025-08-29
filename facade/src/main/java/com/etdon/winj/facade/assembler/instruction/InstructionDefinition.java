package com.etdon.winj.facade.assembler.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.assembler.Opcode;
import com.etdon.winj.facade.assembler.operand.Operand;
import com.etdon.winj.facade.assembler.operand.encoding.InstructionEncoding;
import org.jetbrains.annotations.NotNull;

// TODO: Rename to Instruction after the migration of the legacy instructions is done.
public record InstructionDefinition(Opcode opcode, InstructionEncoding encoding) {

    public byte[] create(final Operand... operands) {

        return this.encoding.process(this.opcode, operands);

    }

    public static InstructionDefinition of(@NotNull final Opcode opcode, @NotNull final InstructionEncoding encoding) {

        Preconditions.checkNotNull(opcode);
        Preconditions.checkNotNull(encoding);
        return new InstructionDefinition(opcode, encoding);

    }

}
