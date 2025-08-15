package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.address.RegisterAddressor;
import org.jetbrains.annotations.NotNull;

public final class Instruction_NOT_RM8 extends Instruction {

    private final RegisterAddressor destination;

    private Instruction_NOT_RM8(final RegisterAddressor destination) {

        this.destination = destination;

    }

    @Override
    public byte[] build() {

        if (this.destination.getRegister().isExtended()) {
            return new byte[]{
                    Opcode.Prefix.of(true, false, false, false),
                    Opcode.Primary.NOT_RM8,
                    Opcode.ModRM.builder()
                            .mod(this.destination.getMod())
                            .reg(2)
                            .rm(this.destination.getRegister().getValue())
                            .build()
            };
        } else {
            return new byte[]{
                    Opcode.Primary.NOT_RM8,
                    Opcode.ModRM.builder()
                            .mod(this.destination.getMod())
                            .reg(2)
                            .rm(this.destination.getRegister().getValue())
                            .build()
            };
        }

    }

    public static Instruction_NOT_RM8 of(@NotNull final RegisterAddressor destination) {

        Preconditions.checkNotNull(destination);
        return new Instruction_NOT_RM8(destination);

    }

}
