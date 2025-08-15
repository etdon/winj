package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.address.RegisterAddressor;
import org.jetbrains.annotations.NotNull;

public final class Instruction_DEC_RM64 extends Instruction {

    private final RegisterAddressor destination;

    private Instruction_DEC_RM64(final RegisterAddressor destination) {

        this.destination = destination;

    }

    @Override
    public byte[] build() {

        return new byte[]{
                Opcode.Prefix.of(this.destination.getRegister().isExtended(), false, false, true),
                Opcode.Primary.DEC_RM64,
                Opcode.ModRM.builder()
                        .mod(this.destination.getMod())
                        .reg(1)
                        .rm(this.destination.getRegister().getValue())
                        .build()
        };

    }

    public static Instruction_DEC_RM64 of(@NotNull final RegisterAddressor destination) {

        Preconditions.checkNotNull(destination);
        return new Instruction_DEC_RM64(destination);

    }

}
