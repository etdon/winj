package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.register.Register;
import org.jetbrains.annotations.NotNull;

public final class Instruction_XOR_RM64_R64 extends Instruction {

    private final Register destination;
    private final Register source;

    private Instruction_XOR_RM64_R64(final Register destination,
                                     final Register source) {

        this.destination = destination;
        this.source = source;

    }

    @Override
    public byte[] build() {

        return new byte[]{
                Opcode.Prefix.of(destination.isExtended(), false, source.isExtended(), true),
                Opcode.Primary.XOR.RM64_R64,
                Opcode.ModRM.builder()
                        .mod(Opcode.ModRM.Mod.RD)
                        .reg(source.getValue())
                        .rm(destination.getValue())
                        .build()
        };

    }

    public static Instruction_XOR_RM64_R64 of(@NotNull final Register destination, @NotNull final Register source) {

        Preconditions.checkNotNull(destination);
        Preconditions.checkNotNull(source);
        return new Instruction_XOR_RM64_R64(destination, source);

    }

}
