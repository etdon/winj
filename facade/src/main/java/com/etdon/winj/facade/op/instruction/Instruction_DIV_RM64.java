package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.register.Register;
import org.jetbrains.annotations.NotNull;

public final class Instruction_DIV_RM64 extends Instruction {

    private final Register source;

    private Instruction_DIV_RM64(final Register source) {

        this.source = source;

    }

    @Override
    public byte[] build() {

        return new byte[]{
                Opcode.Prefix.of(source.isExtended(), false, false, true),
                Opcode.Primary.DIV_RM64,
                Opcode.ModRM.builder()
                        .mod(Opcode.ModRM.Mod.RD)
                        .reg(6)
                        .rm(source.getValue())
                        .build()
        };

    }

    public static Instruction_DIV_RM64 of(@NotNull final Register source) {

        Preconditions.checkNotNull(source);
        return new Instruction_DIV_RM64(source);

    }

}
