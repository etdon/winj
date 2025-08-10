package com.etdon.winj.constant.file;

public final class FileShareMode {

    /**
     * Prevents subsequent open operations on a file or device if they request delete, read, or write access.
     */
    public static final int NONE = 0x0;
    /**
     * Enables subsequent open operations on a file or device to request delete access.
     * <p>
     * Otherwise, no process can open the file or device if it requests delete access.
     * <p>
     * If this flag is not specified, but the file or device has been opened for delete access, the function fails.
     */
    public static final int FILE_SHARE_DELETE = 0x00000004;
    /**
     * Enables subsequent open operations on a file or device to request read access.
     * <p>
     * Otherwise, no process can open the file or device if it requests read access.
     * <p>
     * If this flag is not specified, but the file or device has been opened for read access, the function fails.
     */
    public static final int FILE_SHARE_READ = 0x00000001;
    /**
     * Enables subsequent open operations on a file or device to request write access.
     * <p>
     * Otherwise, no process can open the file or device if it requests write access.
     * <p>
     * If this flag is not specified, but the file or device has been opened for write access or has a file mapping
     * with write access, the function fails.
     */
    public static final int FILE_SHARE_WRITE = 0x00000002;

    private FileShareMode() {

        throw new UnsupportedOperationException();

    }

}
