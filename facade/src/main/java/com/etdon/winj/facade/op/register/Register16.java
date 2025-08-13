package com.etdon.winj.facade.op.register;

public final class Register16 extends Register {

    public static final Register16 AX = Register16.of((byte) 0b000, false);
    public static final Register16 CX = Register16.of((byte) 0b001, false);
    public static final Register16 DX = Register16.of((byte) 0b010, false);
    public static final Register16 BX = Register16.of((byte) 0b011, false);
    public static final Register16 SP = Register16.of((byte) 0b100, false);
    public static final Register16 BP = Register16.of((byte) 0b101, false);
    public static final Register16 SI = Register16.of((byte) 0b110, false);
    public static final Register16 DI = Register16.of((byte) 0b111, false);
    public static final Register16 R8W = Register16.of((byte) 0b000, true);
    public static final Register16 R9W = Register16.of((byte) 0b001, true);
    public static final Register16 R10W = Register16.of((byte) 0b010, true);
    public static final Register16 R11W = Register16.of((byte) 0b011, true);
    public static final Register16 R12W = Register16.of((byte) 0b100, true);
    public static final Register16 R13W = Register16.of((byte) 0b101, true);
    public static final Register16 R14W = Register16.of((byte) 0b110, true);
    public static final Register16 R15W = Register16.of((byte) 0b111, true);

    private Register16(final byte value,
                       final boolean extended) {

        super(value, extended);

    }

    public static Register16 of(final byte value, final boolean extended) {

        return new Register16(value, extended);

    }

}
