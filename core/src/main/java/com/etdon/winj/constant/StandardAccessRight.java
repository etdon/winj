package com.etdon.winj.constant;

public final class StandardAccessRight {

    public static final int DELETE = 0x00010000;
    public static final int READ_CONTROL = 0x00020000;
    public static final int SYNCHRONIZE = 0x00100000;
    public static final int WRITE_DAC = 0x00040000;
    public static final int WRITE_OWNER = 0x00080000;

    private StandardAccessRight() {

        throw new UnsupportedOperationException();

    }

}
