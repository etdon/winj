package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.ByteBuffer;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.address.RegisterAddressor;
import com.etdon.winj.facade.op.register.Register64;
import org.jetbrains.annotations.NotNull;

public final class Instruction_XOR_RM64_R64 extends Instruction {

    private final RegisterAddressor destination;
    private final Register64 source;

    private Instruction_XOR_RM64_R64(final RegisterAddressor destination,
                                     final Register64 source) {

        this.destination = destination;
        this.source = source;

    }

    @Override
    public byte[] build() {

        final ByteBuffer byteBuffer = ByteBuffer.size(4);
        byteBuffer.put(Opcode.Prefix.of(this.destination.getRegister().isExtended(), false, this.source.isExtended(), true));
        byteBuffer.put(Opcode.Primary.XOR.RM64_R64);
        byteBuffer.put(
                Opcode.ModRM.builder()
                        .mod(this.destination.getMod())
                        .reg(this.source.getValue())
                        .rm(this.destination.getRegister().getValue())
                        .build()
        );
        if (this.destination.requiresSIB())
            byteBuffer.put(this.destination.getSIB());

        return byteBuffer.get();

    }

    public static Instruction_XOR_RM64_R64 of(@NotNull final RegisterAddressor destination, @NotNull final Register64 source) {

        Preconditions.checkNotNull(destination);
        Preconditions.checkNotNull(source);
        return new Instruction_XOR_RM64_R64(destination, source);

    }

}
