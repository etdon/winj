package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.address.RegisterAddressor;
import com.etdon.winj.facade.op.register.Register64;
import org.jetbrains.annotations.NotNull;

public final class Instruction_MOV_RM64_R64 extends Instruction {

    private final RegisterAddressor destination;
    private final Register64 source;

    private Instruction_MOV_RM64_R64(final RegisterAddressor destination,
                                     final Register64 source) {

        this.destination = destination;
        this.source = source;

    }

    @Override
    public byte[] build() {

        return new byte[]{
                Opcode.Prefix.of(this.destination.getRegister().isExtended(), false, this.source.isExtended(), true),
                Opcode.Primary.MOV.RM64_R64,
                Opcode.ModRM.builder()
                        .mod(this.destination.getMod())
                        .reg(this.source.getValue())
                        .rm(this.destination.getRegister().getValue())
                        .build()
        };

    }

    public static Instruction_MOV_RM64_R64 of(@NotNull final RegisterAddressor destination, @NotNull final Register64 source) {

        Preconditions.checkNotNull(destination);
        Preconditions.checkNotNull(source);
        return new Instruction_MOV_RM64_R64(destination, source);

    }

}
