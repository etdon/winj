package com.etdon.winj.facade.op.register;

public final class Register8 extends Register {

    // With and without REX prefix
    public static final Register8 AL = Register8.of((byte) 0b000, false);
    public static final Register8 CL = Register8.of((byte) 0b001, false);
    public static final Register8 DL = Register8.of((byte) 0b010, false);
    public static final Register8 BL = Register8.of((byte) 0b011, false);

    // With REX prefix
    public static final Register8 SPL = Register8.of((byte) 0b100, true);
    public static final Register8 BPL = Register8.of((byte) 0b101, true);
    public static final Register8 SIL = Register8.of((byte) 0b110, true);
    public static final Register8 DIL = Register8.of((byte) 0b111, true);

    // Without REX prefix
    public static final Register8 AH = Register8.of((byte) 0b100, false);
    public static final Register8 CH = Register8.of((byte) 0b101, false);
    public static final Register8 DH = Register8.of((byte) 0b110, false);
    public static final Register8 BH = Register8.of((byte) 0b111, false);

    // With REX prefix
    public static final Register8 R8B = Register8.of((byte) 0b000, true);
    public static final Register8 R9B = Register8.of((byte) 0b001, true);
    public static final Register8 R10B = Register8.of((byte) 0b010, true);
    public static final Register8 R11B = Register8.of((byte) 0b011, true);
    public static final Register8 R12B = Register8.of((byte) 0b100, true);
    public static final Register8 R13B = Register8.of((byte) 0b101, true);
    public static final Register8 R14B = Register8.of((byte) 0b110, true);
    public static final Register8 R15B = Register8.of((byte) 0b111, true);

    private Register8(final byte value,
                      final boolean extended) {

        super(value, extended);

    }

    public static Register8 of(final byte value, final boolean extended) {

        return new Register8(value, extended);

    }

}
