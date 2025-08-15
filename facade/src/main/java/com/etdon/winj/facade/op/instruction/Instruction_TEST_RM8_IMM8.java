package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.address.RegisterAddressor;
import org.jetbrains.annotations.NotNull;

public final class Instruction_TEST_RM8_IMM8 extends Instruction {

    private final RegisterAddressor destination;
    private final byte value;

    private Instruction_TEST_RM8_IMM8(final RegisterAddressor destination,
                                      final byte value) {

        this.destination = destination;
        this.value = value;

    }

    @Override
    public byte[] build() {

        if (this.destination.getRegister().isExtended()) {
            return new byte[]{
                    Opcode.Prefix.of(true, false, false, false),
                    Opcode.Primary.TEST_RM8_IMM8,
                    Opcode.ModRM.builder()
                            .mod(this.destination.getMod())
                            .reg(0)
                            .rm(this.destination.getRegister().getValue())
                            .build(),
                    this.value
            };
        } else {
            return new byte[]{
                    Opcode.Primary.TEST_RM8_IMM8,
                    Opcode.ModRM.builder()
                            .mod(this.destination.getMod())
                            .reg(0)
                            .rm(this.destination.getRegister().getValue())
                            .build(),
                    this.value
            };
        }

    }

    public static Instruction_TEST_RM8_IMM8 of(@NotNull final RegisterAddressor destination,
                                               final byte value) {

        Preconditions.checkNotNull(destination);
        return new Instruction_TEST_RM8_IMM8(destination, value);

    }

}
