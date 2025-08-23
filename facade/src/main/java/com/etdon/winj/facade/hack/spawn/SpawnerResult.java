package com.etdon.winj.facade.hack.spawn;

public final class SpawnerResult {

    public static final int SUCCESS = 0;
    public static final int FAIL = 1;
    public static final int FAIL_FILE_NOT_FOUND = 2;
    public static final int FAIL_PROCESS_PARAMETER_CREATION = 3;
    public static final int FAIL_OPEN_PARENT_PROCESS = 4;
    public static final int FAIL_CREATE_USER_PROCESS = 5;
    public static final int FAIL_CLOSE_PARENT_HANDLE = 6;
    public static final int FAIL_FREE_MEMORY = 7;
    public static final int FAIL_PROCESS_PARAMETER_DESTRUCTION = 8;

    private SpawnerResult() {

        throw new UnsupportedOperationException();

    }

}
