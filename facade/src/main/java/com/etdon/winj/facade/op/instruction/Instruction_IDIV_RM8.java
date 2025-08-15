package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.address.RegisterAddressor;
import org.jetbrains.annotations.NotNull;

public final class Instruction_IDIV_RM8 extends Instruction {

    private final RegisterAddressor source;

    private Instruction_IDIV_RM8(final RegisterAddressor source) {

        this.source = source;

    }

    @Override
    public byte[] build() {

        if (this.source.getRegister().isExtended()) {
            return new byte[]{
                    Opcode.Prefix.of(true, false, false, false),
                    Opcode.Primary.IDIV_RM8,
                    Opcode.ModRM.builder()
                            .mod(this.source.getMod())
                            .reg(7)
                            .rm(this.source.getRegister().getValue())
                            .build()
            };
        } else {
            return new byte[]{
                    Opcode.Primary.IDIV_RM8,
                    Opcode.ModRM.builder()
                            .mod(this.source.getMod())
                            .reg(7)
                            .rm(this.source.getRegister().getValue())
                            .build()
            };
        }

    }

    public static Instruction_IDIV_RM8 of(@NotNull final RegisterAddressor source) {

        Preconditions.checkNotNull(source);
        return new Instruction_IDIV_RM8(source);

    }

}
