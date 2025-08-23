package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.ByteBuffer;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.address.RegisterAddressor;
import org.jetbrains.annotations.NotNull;

public final class Instruction_TEST_RM8_IMM8 extends Instruction {

    private final RegisterAddressor destination;
    private final byte value;

    private Instruction_TEST_RM8_IMM8(final RegisterAddressor destination,
                                      final byte value) {

        this.destination = destination;
        this.value = value;

    }

    @Override
    public byte[] build() {

        final ByteBuffer byteBuffer = ByteBuffer.size(5);
        final boolean sibExtension = this.destination.requiresSIB() && this.destination.getSIB().getIndex().isExtended();
        if (this.destination.getRegister().isExtended() || sibExtension)
            byteBuffer.put(Opcode.Prefix.of(this.destination.getRegister().isExtended(), sibExtension, false, false));
        byteBuffer.put(Opcode.Primary.TEST_RM8_IMM8);
        byteBuffer.put(
                Opcode.ModRM.builder()
                        .mod(this.destination.getMod())
                        .reg(0)
                        .rm(this.destination.getRegister().getValue())
                        .build()
        );
        if (this.destination.requiresSIB())
            byteBuffer.put(this.destination.getSIB().toByte());
        byteBuffer.put(this.value);

        return byteBuffer.get();

    }

    public static Instruction_TEST_RM8_IMM8 of(@NotNull final RegisterAddressor destination,
                                               final byte value) {

        Preconditions.checkNotNull(destination);
        return new Instruction_TEST_RM8_IMM8(destination, value);

    }

}
