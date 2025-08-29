package com.etdon.winj.facade.assembler.instruction;

import com.etdon.winj.facade.assembler.ByteBuffer;
import com.etdon.winj.facade.assembler.Opcodes;
import com.etdon.winj.marshal.primitive.PrimitiveMarshalContext;
import com.etdon.winj.marshal.primitive.ShortMarshal;

public final class Instruction_RETN_IMM16 extends Instruction {

    private final short value;

    private Instruction_RETN_IMM16(final short value) {

        this.value = value;

    }

    @Override
    public byte[] build() {

        final ByteBuffer byteBuffer = ByteBuffer.size(3);
        byteBuffer.put(Opcodes.Primary.RETN_IMM16);
        byteBuffer.put(ShortMarshal.getInstance().marshal(this.value, PrimitiveMarshalContext.empty()));

        return byteBuffer.get();

    }

    public static Instruction_RETN_IMM16 of(final short value) {

        return new Instruction_RETN_IMM16(value);

    }

}
