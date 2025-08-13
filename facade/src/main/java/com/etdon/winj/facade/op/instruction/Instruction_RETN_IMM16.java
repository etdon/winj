package com.etdon.winj.facade.op.instruction;

import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.marshal.primitive.PrimitiveMarshalContext;
import com.etdon.winj.marshal.primitive.ShortMarshal;

public final class Instruction_RETN_IMM16 extends Instruction {

    private final short value;

    private Instruction_RETN_IMM16(final short value) {

        this.value = value;

    }

    @Override
    public byte[] build() {

        final byte[] buffer = new byte[3];
        buffer[0] = Opcode.Primary.RETN_IMM16;
        System.arraycopy(ShortMarshal.getInstance().marshal(this.value, PrimitiveMarshalContext.empty()), 0, buffer, 1, 2);
        return buffer;

    }

    public static Instruction_RETN_IMM16 of(final short value) {

        return new Instruction_RETN_IMM16(value);

    }

}
