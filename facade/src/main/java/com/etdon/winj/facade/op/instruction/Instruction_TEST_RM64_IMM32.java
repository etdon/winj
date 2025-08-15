package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.ByteBuffer;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.address.RegisterAddressor;
import com.etdon.winj.marshal.primitive.IntegerMarshal;
import com.etdon.winj.marshal.primitive.PrimitiveMarshalContext;
import org.jetbrains.annotations.NotNull;

public final class Instruction_TEST_RM64_IMM32 extends Instruction {

    private final RegisterAddressor destination;
    private final int value;

    private Instruction_TEST_RM64_IMM32(final RegisterAddressor destination,
                                        final int value) {

        this.destination = destination;
        this.value = value;

    }

    @Override
    public byte[] build() {

        final ByteBuffer byteBuffer = ByteBuffer.size(7);
        byteBuffer.put(Opcode.Prefix.of(this.destination.getRegister().isExtended(), false, false, true));
        byteBuffer.put(Opcode.Primary.TEST_RM64_IMM32);
        byteBuffer.put(
                Opcode.ModRM.builder()
                        .mod(this.destination.getMod())
                        .reg(0)
                        .rm(this.destination.getRegister().getValue())
                        .build()
        );
        if (this.destination.requiresSIB())
            byteBuffer.put(this.destination.getSIB());
        byteBuffer.put(IntegerMarshal.getInstance().marshal(this.value, PrimitiveMarshalContext.empty()));

        return byteBuffer.get();

    }

    public static Instruction_TEST_RM64_IMM32 of(@NotNull final RegisterAddressor destination, final int value) {

        Preconditions.checkNotNull(destination);
        return new Instruction_TEST_RM64_IMM32(destination, value);

    }

}
