package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.ByteBuffer;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.address.RegisterAddressor;
import org.jetbrains.annotations.NotNull;

public final class Instruction_IDIV_RM8 extends Instruction {

    private final RegisterAddressor source;

    private Instruction_IDIV_RM8(final RegisterAddressor source) {

        this.source = source;

    }

    @Override
    public byte[] build() {

        final ByteBuffer byteBuffer = ByteBuffer.size(4);
        final boolean sibExtension = this.source.requiresSIB() && this.source.getSIB().getIndex().isExtended();
        if (this.source.getRegister().isExtended() || sibExtension)
            byteBuffer.put(Opcode.Prefix.of(this.source.getRegister().isExtended(), sibExtension, false, false));
        byteBuffer.put(Opcode.Primary.IDIV_RM8);
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

    public static Instruction_IDIV_RM8 of(@NotNull final RegisterAddressor source) {

        Preconditions.checkNotNull(source);
        return new Instruction_IDIV_RM8(source);

    }

}
