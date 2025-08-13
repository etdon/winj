package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.register.Register64;
import org.jetbrains.annotations.NotNull;

public final class Instruction_POP_R64 extends Instruction {

    private final Register64 source;

    private Instruction_POP_R64(final Register64 source) {

        this.source = source;

    }

    @Override
    public byte[] build() {

        if (this.source.isExtended()) {
            return new byte[]{
                    Opcode.Prefix.of(true, false, false, false),
                    (byte) (Opcode.Primary.POP_R64 | this.source.getValue())
            };
        } else {
            return new byte[]{(byte) (Opcode.Primary.POP_R64 | this.source.getValue())};
        }

    }

    public static Instruction_POP_R64 of(@NotNull final Register64 source) {

        Preconditions.checkNotNull(source);
        return new Instruction_POP_R64(source);

    }

}
