package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.ByteBuffer;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.address.RegisterAddressor;
import org.jetbrains.annotations.NotNull;

public final class Instruction_IMUL_RM8 extends Instruction {

    private final RegisterAddressor source;

    private Instruction_IMUL_RM8(final RegisterAddressor source) {

        this.source = source;

    }

    @Override
    public byte[] build() {

        final ByteBuffer byteBuffer = ByteBuffer.size(4);
        final boolean sibExtension = this.source.requiresSIB() && this.source.getSIB().getIndex().isExtended();
        if (this.source.getRegister().isExtended() || sibExtension)
            byteBuffer.put(Opcode.Prefix.of(this.source.getRegister().isExtended(), sibExtension, false, false));
        byteBuffer.put(Opcode.Primary.IMUL_RM8);
        byteBuffer.put(
                Opcode.ModRM.builder()
                        .mod(this.source.getMod())
                        .reg(5)
                        .rm(this.source.getRegister().getValue())
                        .build()
        );
        if (this.source.requiresSIB())
            byteBuffer.put(this.source.getSIB().toByte());

        return byteBuffer.get();

    }

    public static Instruction_IMUL_RM8 of(@NotNull final RegisterAddressor source) {

        Preconditions.checkNotNull(source);
        return new Instruction_IMUL_RM8(source);

    }

}
