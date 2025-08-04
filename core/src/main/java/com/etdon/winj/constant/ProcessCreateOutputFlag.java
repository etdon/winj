package com.etdon.winj.constant;

public final class ProcessCreateOutputFlag {

    public static final byte PROTECTED_PROCESS = 0x01;
    public static final byte ADDRESS_SPACE_OVERRIDE = 0x02;
    public static final byte DEV_OVERRIDE_ENABLED = 0x04;
    public static final byte MANIFEST_DETECTED = 0x08;
    public static final byte PROTECTED_PROCESS_LIGHT = 0x10;
    public static final byte SPARE_BITS_1 = -1; // DO NOT USE
    public static final byte SPARE_BITS_2 = -1; // DO NOT USE
    public static final short SPARE_BITS_3 = -1; // DO NOT USE

    private ProcessCreateOutputFlag() {

        throw new UnsupportedOperationException();

    }

}
