package com.etdon.winj.facade.assembler.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.assembler.ByteBuffer;
import com.etdon.winj.facade.assembler.Opcodes;
import com.etdon.winj.facade.assembler.register.Register64;
import org.jetbrains.annotations.NotNull;

public final class Instruction_POP_R64 extends Instruction {

    private final Register64 source;

    private Instruction_POP_R64(final Register64 source) {

        this.source = source;

    }

    @Override
    public byte[] build() {

        final ByteBuffer byteBuffer = ByteBuffer.size(2);
        if (this.source.isExtended())
            byteBuffer.put(Opcodes.Prefix.of(true, false, false, false));
        byteBuffer.put((byte) (Opcodes.Primary.POP.R64 | this.source.getValue()));

        return byteBuffer.get();

    }

    public static Instruction_POP_R64 of(@NotNull final Register64 source) {

        Preconditions.checkNotNull(source);
        return new Instruction_POP_R64(source);

    }

}
