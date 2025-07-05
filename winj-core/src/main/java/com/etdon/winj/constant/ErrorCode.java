package com.etdon.winj.constant;

public final class ErrorCode {

    /**
     * The operation completed successfully.
     */
    public static final int ERROR_SUCCESS = 0x0;
    /**
     * Incorrect function.
     */
    public static final int ERROR_INVALID_FUNCTION = 0x1;
    /**
     * The system cannot find the file specified.
     */
    public static final int ERROR_FILE_NOT_FOUND = 0x2;
    /**
     * The system cannot find the path specified.
     */
    public static final int ERROR_PATH_NOT_FOUND = 0x3;
    /**
     * The system cannot open the file.
     */
    public static final int ERROR_TOO_MANY_OPEN_FILES = 0x4;
    /**
     * Access is denied.
     */
    public static final int ERROR_ACCESS_DENIED = 0x5;
    /**
     * The handle is invalid.
     */
    public static final int ERROR_INVALID_HANDLE = 0x6;
    /**
     * The storage control blocks were destroyed.
     */
    public static final int ERROR_ARENA_TRASHED = 0x7;
    /**
     * Not enough memory resources are available to process this command.
     */
    public static final int ERROR_NOT_ENOUGH_MEMORY = 0x8;
    /**
     * The storage control block address is invalid.
     */
    public static final int ERROR_INVALID_BLOCK = 0x9;
    /**
     * The environment is incorrect.
     */
    public static final int ERROR_BAD_ENVIRONMENT = 0xA;

    private ErrorCode() {

        throw new UnsupportedOperationException();

    }

}
