package com.etdon.winj.facade.op.register;

public final class Register {

    public static final Register RAX = Register.of((byte) 0b000, false);
    public static final Register RCX = Register.of((byte) 0b001, false);
    public static final Register RDX = Register.of((byte) 0b010, false);
    public static final Register RBX = Register.of((byte) 0b011, false);
    public static final Register RSP = Register.of((byte) 0b100, false);
    public static final Register RBP = Register.of((byte) 0b101, false);
    public static final Register RSI = Register.of((byte) 0b110, false);
    public static final Register RDI = Register.of((byte) 0b111, false);
    public static final Register R8 = Register.of((byte) 0b000, true);
    public static final Register R9 = Register.of((byte) 0b001, true);
    public static final Register R10 = Register.of((byte) 0b010, true);
    public static final Register R11 = Register.of((byte) 0b011, true);
    public static final Register R12 = Register.of((byte) 0b100, true);
    public static final Register R13 = Register.of((byte) 0b101, true);
    public static final Register R14 = Register.of((byte) 0b110, true);
    public static final Register R15 = Register.of((byte) 0b111, true);

    private final byte value;
    private final boolean extended;

    private Register(final byte value,
                     final boolean extended) {

        this.value = value;
        this.extended = extended;

    }

    public byte getValue() {

        return this.value;

    }

    public boolean isExtended() {

        return this.extended;

    }

    public static Register of(final byte value, final boolean extended) {

        return new Register(value, extended);

    }

}
