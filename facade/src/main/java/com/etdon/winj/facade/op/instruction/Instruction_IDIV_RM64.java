package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.register.Register64;
import org.jetbrains.annotations.NotNull;

public final class Instruction_IDIV_RM64 extends Instruction {

    private final Register64 source;

    private Instruction_IDIV_RM64(final Register64 source) {

        this.source = source;

    }

    @Override
    public byte[] build() {

        return new byte[]{
                Opcode.Prefix.of(this.source.isExtended(), false, false, true),
                Opcode.Primary.IDIV_RM64,
                Opcode.ModRM.builder()
                        .mod(Opcode.ModRM.Mod.RD)
                        .reg(7)
                        .rm(this.source.getValue())
                        .build()

        };

    }

    public static Instruction_IDIV_RM64 of(@NotNull final Register64 source) {

        Preconditions.checkNotNull(source);
        return new Instruction_IDIV_RM64(source);

    }

}
