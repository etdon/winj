package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.register.Register;
import org.jetbrains.annotations.NotNull;

public final class Instruction_SBB_RM64_IMM8 extends Instruction {

    private final Register destination;
    private final byte value;

    private Instruction_SBB_RM64_IMM8(final Register destination,
                                      final byte value) {

        this.destination = destination;
        this.value = value;

    }

    @Override
    public byte[] build() {

        return new byte[]{
                Opcode.Prefix.of(this.destination.isExtended(), false, false, true),
                Opcode.Primary.SBB_RM64_IMM8,
                Opcode.ModRM.builder()
                        .mod(Opcode.ModRM.Mod.RD)
                        .reg(3)
                        .rm(this.destination.getValue())
                        .build(),
                this.value
        };

    }

    public static Instruction_SBB_RM64_IMM8 of(@NotNull final Register destination, final byte value) {

        Preconditions.checkNotNull(destination);
        return new Instruction_SBB_RM64_IMM8(destination, value);

    }

}
