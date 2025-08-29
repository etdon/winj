package com.etdon.winj.facade.assembler.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.assembler.ByteBuffer;
import com.etdon.winj.facade.assembler.Opcodes;
import com.etdon.winj.facade.assembler.address.RegisterAddressor;
import com.etdon.winj.facade.assembler.register.Register64;
import org.jetbrains.annotations.NotNull;

public final class Instruction_MOV_RM64_R64 extends Instruction {

    private final RegisterAddressor destination;
    private final Register64 source;

    private Instruction_MOV_RM64_R64(final RegisterAddressor destination,
                                     final Register64 source) {

        this.destination = destination;
        this.source = source;

    }

    @Override
    public byte[] build() {

        final ByteBuffer byteBuffer = ByteBuffer.size(4);
        final boolean sibExtension = this.destination.requiresSIB() && this.destination.getSIB().getIndex().isExtended();
        byteBuffer.put(Opcodes.Prefix.of(this.destination.getRegister().isExtended(), sibExtension, this.source.isExtended(), true));
        byteBuffer.put(Opcodes.Primary.MOV.RM64_R64);
        byteBuffer.put(
                Opcodes.ModRM.builder()
                        .mod(this.destination.getMod())
                        .reg(this.source.getValue())
                        .rm(this.destination.getRegister().getValue())
                        .build()
        );
        if (this.destination.requiresSIB())
            byteBuffer.put(this.destination.getSIB().toByte());

        return byteBuffer.get();

    }

    public static Instruction_MOV_RM64_R64 of(@NotNull final RegisterAddressor destination, @NotNull final Register64 source) {

        Preconditions.checkNotNull(destination);
        Preconditions.checkNotNull(source);
        return new Instruction_MOV_RM64_R64(destination, source);

    }

}
