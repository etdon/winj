package com.etdon.winj.facade.op.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.register.Register64;
import com.etdon.winj.marshal.primitive.LongMarshal;
import com.etdon.winj.marshal.primitive.PrimitiveMarshalContext;
import org.jetbrains.annotations.NotNull;

public final class Instruction_MOV_R64_IMM64 extends Instruction {

    private final Register64 destination;
    private final long value;

    private Instruction_MOV_R64_IMM64(final Register64 destination,
                                      final long value) {

        this.destination = destination;
        this.value = value;

    }

    @Override
    public byte[] build() {

        final byte[] buffer = new byte[10];
        buffer[0] = Opcode.Prefix.of(this.destination.isExtended(), false, false, true);
        buffer[1] = (byte) (Opcode.Primary.MOV.R64_IMM64 | this.destination.getValue());
        System.arraycopy(LongMarshal.getInstance().marshal(this.value, PrimitiveMarshalContext.empty()), 0, buffer, 2, 8);
        return buffer;

    }

    public static Instruction_MOV_R64_IMM64 of(@NotNull final Register64 destination,
                                               final long value) {

        Preconditions.checkNotNull(destination);
        return new Instruction_MOV_R64_IMM64(destination, value);

    }

}
