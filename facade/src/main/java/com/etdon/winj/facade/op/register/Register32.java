package com.etdon.winj.facade.op.register;

public final class Register32 extends Register {

    public static final Register32 EAX = Register32.of((byte) 0b000, false);
    public static final Register32 ECX = Register32.of((byte) 0b001, false);
    public static final Register32 EDX = Register32.of((byte) 0b010, false);
    public static final Register32 EBX = Register32.of((byte) 0b011, false);
    public static final Register32 ESP = Register32.of((byte) 0b100, false);
    public static final Register32 EBP = Register32.of((byte) 0b101, false);
    public static final Register32 ESI = Register32.of((byte) 0b110, false);
    public static final Register32 EDI = Register32.of((byte) 0b111, false);
    public static final Register32 R8D = Register32.of((byte) 0b000, true);
    public static final Register32 R9D = Register32.of((byte) 0b001, true);
    public static final Register32 R10D = Register32.of((byte) 0b010, true);
    public static final Register32 R11D = Register32.of((byte) 0b011, true);
    public static final Register32 R12D = Register32.of((byte) 0b100, true);
    public static final Register32 R13D = Register32.of((byte) 0b101, true);
    public static final Register32 R14D = Register32.of((byte) 0b110, true);
    public static final Register32 R15D = Register32.of((byte) 0b111, true);

    private Register32(final byte value,
                       final boolean extended) {

        super(value, extended);

    }

    public static Register32 of(final byte value, final boolean extended) {

        return new Register32(value, extended);

    }

}
