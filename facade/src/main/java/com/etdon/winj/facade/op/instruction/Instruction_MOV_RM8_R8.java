package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.ByteBuffer;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.address.RegisterAddressor;
import com.etdon.winj.facade.op.register.Register8;
import org.jetbrains.annotations.NotNull;

public final class Instruction_MOV_RM8_R8 extends Instruction {

    private final RegisterAddressor destination;
    private final Register8 source;

    private Instruction_MOV_RM8_R8(final RegisterAddressor destination,
                                   final Register8 source) {

        this.destination = destination;
        this.source = source;

    }

    @Override
    public byte[] build() {

        final ByteBuffer byteBuffer = ByteBuffer.size(4);
        if (this.destination.getRegister().isExtended())
            byteBuffer.put(Opcode.Prefix.of(true, false, false, false));
        byteBuffer.put(Opcode.Primary.MOV.RM8_R8);
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

    public static Instruction_MOV_RM8_R8 of(@NotNull final RegisterAddressor destination, @NotNull final Register8 source) {

        Preconditions.checkNotNull(destination);
        Preconditions.checkNotNull(source);
        return new Instruction_MOV_RM8_R8(destination, source);

    }

}
