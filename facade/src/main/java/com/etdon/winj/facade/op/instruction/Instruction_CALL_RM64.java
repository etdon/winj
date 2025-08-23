package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.ByteBuffer;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.address.RegisterAddressor;
import org.jetbrains.annotations.NotNull;

public final class Instruction_CALL_RM64 extends Instruction {

    private final RegisterAddressor destination;

    private Instruction_CALL_RM64(final RegisterAddressor destination) {

        this.destination = destination;

    }

    @Override
    public byte[] build() {

        final ByteBuffer byteBuffer = ByteBuffer.size(4);
        final boolean sibExtension = this.destination.requiresSIB() && this.destination.getSIB().getIndex().isExtended();
        byteBuffer.put(Opcode.Prefix.of(this.destination.getRegister().isExtended(), sibExtension, false, true));
        byteBuffer.put(Opcode.Primary.CALL_RM64);
        byteBuffer.put(
                Opcode.ModRM.builder()
                        .mod(this.destination.getMod())
                        .reg(2)
                        .rm(this.destination.getRegister().getValue())
                        .build()
        );
        if (this.destination.requiresSIB())
            byteBuffer.put(this.destination.getSIB().toByte());

        return byteBuffer.get();

    }

    public static Instruction_CALL_RM64 of(@NotNull final RegisterAddressor destination) {

        Preconditions.checkNotNull(destination);
        return new Instruction_CALL_RM64(destination);

    }

}
