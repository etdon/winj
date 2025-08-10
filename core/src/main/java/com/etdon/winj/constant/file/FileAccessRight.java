package com.etdon.winj.constant.file;

import com.etdon.winj.constant.StandardAccessRight;
import com.etdon.winj.util.Flag;

public final class FileAccessRight {

    /**
     * For a file object, the right to read the corresponding file data. For a directory object, the right to read the
     * corresponding directory data.
     */
    public static final int FILE_READ_DATA = 0x0001;
    /**
     * For a directory, the right to list the contents of the directory.
     */
    public static final int FILE_LIST_DIRECTORY = 0x0001;
    /**
     * For a file object, the right to write data to the file. For a directory object, the right to create a file in
     * the directory (FILE_ADD_FILE).
     */
    public static final int FILE_WRITE_DATA = 0x0002;
    /**
     * For a directory, the right to create a file in the directory.
     */
    public static final int FILE_ADD_FILE = 0x0002;
    /**
     * For a file object, the right to append data to the file. (For local files, write operations will not overwrite
     * existing data if this flag is specified without FILE_WRITE_DATA.) For a directory object, the right to create a
     * subdirectory (FILE_ADD_SUBDIRECTORY).
     */
    public static final int FILE_APPEND_DATA = 0x0004;
    /**
     * For a directory, the right to create a subdirectory.
     */
    public static final int FILE_ADD_SUBDIRECTORY = 0x0004;
    /**
     * For a named pipe, the right to create a pipe.
     */
    public static final int FILE_CREATE_PIPE_INSTANCE = 0x0004;
    /**
     * The right to read extended file attributes.
     */
    public static final int FILE_READ_EA = 0x0008;
    /**
     * The right to write extended file attributes.
     */
    public static final int FILE_WRITE_EA = 0x0010;
    /**
     * For a native code file, the right to execute the file. This access right given to scripts may cause the script
     * to be executable, depending on the script interpreter.
     */
    public static final int FILE_EXECUTE = 0x0020;
    /**
     * For a directory, the right to traverse the directory. By default, users are assigned the
     * BYPASS_TRAVERSE_CHECKING privilege, which ignores the FILE_TRAVERSE access right. See the remarks in File
     * Security and Access Rights for more information.
     */
    public static final int FILE_TRAVERSE = 0x0020;
    /**
     * For a directory, the right to delete a directory and all the files it contains, including read-only files.
     */
    public static final int FILE_DELETE_CHILD = 0x0040;
    /**
     * The right to read file attributes.
     */
    public static final int FILE_READ_ATTRIBUTES = 0x0080;
    /**
     * The right to write file attributes.
     */
    public static final int FILE_WRITE_ATTRIBUTES = 0x0100;
    /**
     * All possible access rights for a file.
     */
    public static final int FILE_ALL_ACCESS = (StandardAccessRight.STANDARD_RIGHTS_REQUIRED | StandardAccessRight.SYNCHRONIZE | 0x1FF);
    /**
     * Read access.
     */
    public static final int FILE_GENERIC_READ = (int) Flag.combine(
            StandardAccessRight.STANDARD_RIGHTS_READ,
            FILE_READ_DATA,
            FILE_READ_ATTRIBUTES,
            FILE_READ_EA,
            StandardAccessRight.SYNCHRONIZE
    );
    /**
     * Write access.
     */
    public static final int FILE_GENERIC_WRITE = (int) Flag.combine(
            StandardAccessRight.STANDARD_RIGHTS_WRITE,
            FILE_WRITE_DATA,
            FILE_WRITE_ATTRIBUTES,
            FILE_WRITE_EA,
            FILE_APPEND_DATA,
            StandardAccessRight.SYNCHRONIZE
    );
    /**
     * Execute access.
     */
    public static final int FILE_GENERIC_EXECUTE = (int) Flag.combine(
            StandardAccessRight.STANDARD_RIGHTS_EXECUTE,
            FILE_READ_ATTRIBUTES,
            FILE_EXECUTE,
            StandardAccessRight.SYNCHRONIZE
    );

    private FileAccessRight() {

        throw new UnsupportedOperationException();

    }

}
