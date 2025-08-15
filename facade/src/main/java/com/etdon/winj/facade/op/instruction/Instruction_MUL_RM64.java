package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.address.RegisterAddressor;
import org.jetbrains.annotations.NotNull;

public final class Instruction_MUL_RM64 extends Instruction {

    private final RegisterAddressor source;

    private Instruction_MUL_RM64(final RegisterAddressor source) {

        this.source = source;

    }

    @Override
    public byte[] build() {

        return new byte[]{
                Opcode.Prefix.of(this.source.getRegister().isExtended(), false, false, true),
                Opcode.Primary.MUL_RM64,
                Opcode.ModRM.builder()
                        .mod(this.source.getMod())
                        .reg(4)
                        .rm(this.source.getRegister().getValue())
                        .build()
        };

    }

    public static Instruction_MUL_RM64 of(@NotNull final RegisterAddressor source) {

        Preconditions.checkNotNull(source);
        return new Instruction_MUL_RM64(source);

    }

}
