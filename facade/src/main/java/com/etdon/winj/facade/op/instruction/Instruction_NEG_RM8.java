package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.register.Register8;
import org.jetbrains.annotations.NotNull;

public final class Instruction_NEG_RM8 extends Instruction {

    private final Register8 destination;

    private Instruction_NEG_RM8(final Register8 destination) {

        this.destination = destination;

    }

    @Override
    public byte[] build() {

        if (this.destination.isExtended()) {
            return new byte[]{
                    Opcode.Prefix.of(true, false, false, false),
                    Opcode.Primary.NEG_RM8,
                    Opcode.ModRM.builder()
                            .mod(Opcode.ModRM.Mod.RD)
                            .reg(3)
                            .rm(this.destination.getValue())
                            .build()
            };
        } else {
            return new byte[]{
                    Opcode.Primary.NEG_RM8,
                    Opcode.ModRM.builder()
                            .mod(Opcode.ModRM.Mod.RD)
                            .reg(3)
                            .rm(this.destination.getValue())
                            .build()
            };
        }

    }

    public static Instruction_NEG_RM8 of(@NotNull final Register8 destination) {

        Preconditions.checkNotNull(destination);
        return new Instruction_NEG_RM8(destination);

    }

}
