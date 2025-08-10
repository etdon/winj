package com.etdon.winj.constant.file;

public final class FileCreationDisposition {

    /**
     * Creates a new file, only if it does not already exist.
     * <p>
     * If the specified file exists, the function fails and the last-error code is set to ERROR_FILE_EXISTS (80).
     * <p>
     * If the specified file does not exist and is a valid path to a writable location, a new file is created.
     */
    public static final int CREATE_NEW = 1;
    /**
     * Creates a new file, always.
     * <p>
     * If the specified file exists and is writable, the function truncates the file, the function succeeds, and
     * last-error code is set to ERROR_ALREADY_EXISTS (183).
     * <p>
     * If the specified file does not exist and is a valid path, a new file is created, the function succeeds, and the
     * last-error code is set to zero.
     */
    public static final int CREATE_ALWAYS = 2;
    /**
     * Opens a file or device, only if it exists.
     * <p>
     * If the specified file or device does not exist, the function fails and the last-error code is set to
     * ERROR_FILE_NOT_FOUND (2).
     * <p>
     * For more information about devices, see the Remarks section.
     */
    public static final int OPEN_EXISTING = 3;
    /**
     * Opens a file, always.
     * <p>
     * If the specified file exists, the function succeeds and the last-error code is set to ERROR_ALREADY_EXISTS (183).
     * <p>
     * If the specified file does not exist and is a valid path to a writable location, the function creates a file and
     * the last-error code is set to zero.
     */
    public static final int OPEN_ALWAYS = 4;
    /**
     * Opens a file and truncates it so that its size is zero bytes, only if it exists.
     * <p>
     * If the specified file does not exist, the function fails and the last-error code is set to ERROR_FILE_NOT_FOUND
     * (2).
     * <p>
     * The calling process must open the file with the GENERIC_WRITE bit set as part of the dwDesiredAccess parameter.
     */
    public static final int TRUNCATE_EXISTING = 5;

    private FileCreationDisposition() {

        throw new UnsupportedOperationException();

    }

}
