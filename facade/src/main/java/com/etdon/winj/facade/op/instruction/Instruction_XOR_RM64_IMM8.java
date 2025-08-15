package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.address.RegisterAddressor;
import org.jetbrains.annotations.NotNull;

public final class Instruction_XOR_RM64_IMM8 extends Instruction {

    private final RegisterAddressor destination;
    private final byte value;

    private Instruction_XOR_RM64_IMM8(final RegisterAddressor destination,
                                      final byte value) {

        this.destination = destination;
        this.value = value;

    }

    @Override
    public byte[] build() {

        return new byte[]{
                Opcode.Prefix.of(this.destination.getRegister().isExtended(), false, false, true),
                Opcode.Primary.XOR.RM64_IMM8,
                Opcode.ModRM.builder()
                        .mod(this.destination.getMod())
                        .reg(6)
                        .rm(this.destination.getRegister().getValue())
                        .build(),
                this.value
        };

    }

    public static Instruction_XOR_RM64_IMM8 of(@NotNull final RegisterAddressor destination, final byte value) {

        Preconditions.checkNotNull(destination);
        return new Instruction_XOR_RM64_IMM8(destination, value);

    }

}
