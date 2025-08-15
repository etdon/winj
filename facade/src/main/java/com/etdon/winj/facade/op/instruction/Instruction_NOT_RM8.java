package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.ByteBuffer;
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

        final ByteBuffer byteBuffer = ByteBuffer.size(3);
        if (this.destination.getRegister().isExtended())
            byteBuffer.put(Opcode.Prefix.of(true, false, false, false));
        byteBuffer.put(Opcode.Primary.NOT_RM8);
        byteBuffer.put(
                Opcode.ModRM.builder()
                        .mod(this.destination.getMod())
                        .reg(2)
                        .rm(this.destination.getRegister().getValue())
                        .build()
        );

        return byteBuffer.get();

    }

    public static Instruction_NOT_RM8 of(@NotNull final RegisterAddressor destination) {

        Preconditions.checkNotNull(destination);
        return new Instruction_NOT_RM8(destination);

    }

}
