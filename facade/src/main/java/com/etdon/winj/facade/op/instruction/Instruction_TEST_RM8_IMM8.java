package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.register.Register8;
import org.jetbrains.annotations.NotNull;

public final class Instruction_TEST_RM8_IMM8 extends Instruction {

    private final Register8 destination;
    private final byte value;

    private Instruction_TEST_RM8_IMM8(final Register8 destination,
                                      final byte value) {

        this.destination = destination;
        this.value = value;

    }

    @Override
    public byte[] build() {

        if (this.destination.isExtended()) {
            return new byte[]{
                    Opcode.Prefix.of(true, false, false, false),
                    Opcode.Primary.TEST_RM8_IMM8,
                    Opcode.ModRM.builder()
                            .mod(Opcode.ModRM.Mod.RD)
                            .reg(0)
                            .rm(this.destination.getValue())
                            .build(),
                    this.value
            };
        } else {
            return new byte[]{
                    Opcode.Primary.TEST_RM8_IMM8,
                    Opcode.ModRM.builder()
                            .mod(Opcode.ModRM.Mod.RD)
                            .reg(0)
                            .rm(this.destination.getValue())
                            .build(),
                    this.value
            };
        }

    }

    public static Instruction_TEST_RM8_IMM8 of(@NotNull final Register8 destination,
                                               final byte value) {

        Preconditions.checkNotNull(destination);
        return new Instruction_TEST_RM8_IMM8(destination, value);

    }

}
