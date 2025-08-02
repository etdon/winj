package com.etdon.winj.constant;

public final class ThreadCreationFlag {

    public static final int DEFAULT = 0;
    public static final int CREATE_SUSPENDED = 0x00000004;
    public static final int STACK_SIZE_PARAM_IS_A_RESERVATION = 0x00010000;

    private ThreadCreationFlag() {

        throw new UnsupportedOperationException();

    }

}
