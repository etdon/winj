package com.etdon.winj.constant;

public final class GenericAccessRight {

    /**
     * All possible access rights.
     */
    public static final int GENERIC_ALL = 0x10000000;
    /**
     * Execute access.
     */
    public static final int GENERIC_EXECUTE = 0x20000000;
    /**
     * Write access.
     */
    public static final int GENERIC_WRITE = 0x40000000;
    /**
     * Read access.
     */
    public static final int GENERIC_READ = 0x80000000;

    private GenericAccessRight() {

        throw new UnsupportedOperationException();

    }

}
