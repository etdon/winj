package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.register.Register64;
import org.jetbrains.annotations.NotNull;

public final class Instruction_PUSH_R64 extends Instruction {

    private final Register64 destination;

    private Instruction_PUSH_R64(final Register64 destination) {

        this.destination = destination;

    }

    @Override
    public byte[] build() {

        if (this.destination.isExtended()) {
            return new byte[]{
                    Opcode.Prefix.of(true, false, false, false),
                    (byte) (Opcode.Primary.PUSH_R64 | this.destination.getValue())
            };
        } else {
            return new byte[]{(byte) (Opcode.Primary.PUSH_R64 | this.destination.getValue())};
        }

    }

    public static Instruction_PUSH_R64 of(@NotNull final Register64 destination) {

        Preconditions.checkNotNull(destination);
        return new Instruction_PUSH_R64(destination);

    }

}
