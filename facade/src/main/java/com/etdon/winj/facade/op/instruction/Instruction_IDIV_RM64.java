package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.ByteBuffer;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.address.RegisterAddressor;
import org.jetbrains.annotations.NotNull;

public final class Instruction_IDIV_RM64 extends Instruction {

    private final RegisterAddressor source;

    private Instruction_IDIV_RM64(final RegisterAddressor source) {

        this.source = source;

    }

    @Override
    public byte[] build() {

        final ByteBuffer byteBuffer = ByteBuffer.size(4);
        final boolean sibExtension = this.source.requiresSIB() && this.source.getSIB().getIndex().isExtended();
        byteBuffer.put(Opcode.Prefix.of(this.source.getRegister().isExtended(), sibExtension, false, true));
        byteBuffer.put(Opcode.Primary.IDIV_RM64);
        byteBuffer.put(
                Opcode.ModRM.builder()
                        .mod(this.source.getMod())
                        .reg(7)
                        .rm(this.source.getRegister().getValue())
                        .build()
        );
        if (this.source.requiresSIB())
            byteBuffer.put(this.source.getSIB().toByte());

        return byteBuffer.get();

    }

    public static Instruction_IDIV_RM64 of(@NotNull final RegisterAddressor source) {

        Preconditions.checkNotNull(source);
        return new Instruction_IDIV_RM64(source);

    }

}
