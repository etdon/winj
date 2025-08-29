package com.etdon.winj.facade.assembler.register;

import com.etdon.winj.facade.assembler.operand.OperandSize;

public final class Register64 extends Register {

    public static final Register64 RAX = Register64.of((byte) 0b000, false);
    public static final Register64 RCX = Register64.of((byte) 0b001, false);
    public static final Register64 RDX = Register64.of((byte) 0b010, false);
    public static final Register64 RBX = Register64.of((byte) 0b011, false);
    public static final Register64 RSP = Register64.of((byte) 0b100, false);
    public static final Register64 RBP = Register64.of((byte) 0b101, false);
    public static final Register64 RSI = Register64.of((byte) 0b110, false);
    public static final Register64 RDI = Register64.of((byte) 0b111, false);
    public static final Register64 R8 = Register64.of((byte) 0b000, true);
    public static final Register64 R9 = Register64.of((byte) 0b001, true);
    public static final Register64 R10 = Register64.of((byte) 0b010, true);
    public static final Register64 R11 = Register64.of((byte) 0b011, true);
    public static final Register64 R12 = Register64.of((byte) 0b100, true);
    public static final Register64 R13 = Register64.of((byte) 0b101, true);
    public static final Register64 R14 = Register64.of((byte) 0b110, true);
    public static final Register64 R15 = Register64.of((byte) 0b111, true);

    private Register64(final byte value,
                       final boolean extended) {

        super(value, extended);

    }

    @Override
    public OperandSize getSize() {

        return OperandSize.QWORD;

    }

    public static Register64 of(final byte value, final boolean extended) {

        return new Register64(value, extended);

    }

}
