package com.etdon.winj.constant;

public final class ProcessCreateState {

    public static final int PS_CREATE_INITIAL_STATE = 0;
    public static final int PS_CREATE_FAIL_ON_FILE_OPEN = 1;
    public static final int PS_CREATE_FAIL_ON_SECTION_CREATE = 2;
    public static final int PS_CREATE_FAIL_EXE_FORMAT = 3;
    public static final int PS_CREATE_FAIL_MACHINE_MISMATCH = 4;
    public static final int PS_CREATE_FAIL_EXE_NAME = 5;
    public static final int PS_CREATE_SUCCESS = 6;
    public static final int PS_CREATE_MAXIMUM_STATES = 7;

    private ProcessCreateState() {

        throw new UnsupportedOperationException();

    }

}
