package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.ByteBuffer;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.address.RegisterAddressor;
import org.jetbrains.annotations.NotNull;

public final class Instruction_CMP_RM64_IMM8 extends Instruction {

    private final RegisterAddressor destination;
    private final byte value;

    private Instruction_CMP_RM64_IMM8(final RegisterAddressor destination,
                                      final byte value) {

        this.destination = destination;
        this.value = value;

    }

    @Override
    public byte[] build() {

        final ByteBuffer byteBuffer = ByteBuffer.size(5);
        byteBuffer.put(Opcode.Prefix.of(this.destination.getRegister().isExtended(), false, false, true));
        byteBuffer.put(Opcode.Primary.CMP.RM64_IMM8);
        byteBuffer.put(
                Opcode.ModRM.builder()
                        .mod(this.destination.getMod())
                        .reg(7)
                        .rm(this.destination.getRegister().getValue())
                        .build()
        );
        if (this.destination.requiresSIB())
            byteBuffer.put(this.destination.getSIB());
        byteBuffer.put(this.value);

        return byteBuffer.get();

    }

    public static Instruction_CMP_RM64_IMM8 of(@NotNull final RegisterAddressor destination, final byte value) {

        Preconditions.checkNotNull(destination);
        return new Instruction_CMP_RM64_IMM8(destination, value);

    }

}
