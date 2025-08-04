package com.etdon.winj.constant;

public final class ProcessCreateInitFlag {

    public static final byte WRITE_OUTPUT_ON_EXIT = 0x01;
    public static final byte DETECT_MANIFEST = 0x02;
    public static final byte IFEO_SKIP_DEBUGGER = 0x04;
    public static final byte IFEO_DO_NOT_PROPAGATE_KEY_STATE = 0x08;
    public static final byte SPARE_BITS_1 = 0xF; // DO NOT USE
    public static final byte SPARE_BITS_2 = -1; // DO NOT USE
    public static final short PROHIBITED_IMAGE_CHARACTERISTICS = -1; // 0xFFFF

    private ProcessCreateInitFlag() {

        throw new UnsupportedOperationException();

    }

}
