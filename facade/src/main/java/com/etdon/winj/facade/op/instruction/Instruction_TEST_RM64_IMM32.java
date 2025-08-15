package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
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

        final byte[] buffer = new byte[7];
        buffer[0] = Opcode.Prefix.of(this.destination.getRegister().isExtended(), false, false, true);
        buffer[1] = Opcode.Primary.TEST_RM64_IMM32;
        buffer[2] = Opcode.ModRM.builder()
                .mod(this.destination.getMod())
                .reg(0)
                .rm(this.destination.getRegister().getValue())
                .build();
        System.arraycopy(IntegerMarshal.getInstance().marshal(this.value, PrimitiveMarshalContext.empty()), 0, buffer, 3, 4);
        return buffer;

    }

    public static Instruction_TEST_RM64_IMM32 of(@NotNull final RegisterAddressor destination, final int value) {

        Preconditions.checkNotNull(destination);
        return new Instruction_TEST_RM64_IMM32(destination, value);

    }

}
