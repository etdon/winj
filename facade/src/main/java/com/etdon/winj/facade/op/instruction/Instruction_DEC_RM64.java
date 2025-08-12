package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.register.Register;
import org.jetbrains.annotations.NotNull;

public final class Instruction_DEC_RM64 extends Instruction {

    private final Register destination;

    private Instruction_DEC_RM64(final Register destination) {

        this.destination = destination;

    }

    @Override
    public byte[] build() {

        return new byte[]{
                Opcode.Prefix.of(destination.isExtended(), false, false, true),
                Opcode.Primary.DEC_RM64,
                Opcode.ModRM.builder()
                        .mod(Opcode.ModRM.Mod.RD)
                        .reg(1)
                        .rm(destination.getValue())
                        .build()
        };

    }

    public static Instruction_DEC_RM64 of(@NotNull final Register destination) {

        Preconditions.checkNotNull(destination);
        return new Instruction_DEC_RM64(destination);

    }

}
