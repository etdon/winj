package com.etdon.winj.constant.file;

public final class DriveType {

    /**
     * The drive type cannot be determined.
     */
    public static final int DRIVE_UNKNOWN = 0;
    /**
     * The root path is invalid; for example, there is no volume mounted at the specified path.
     */
    public static final int DRIVE_NO_ROOT_DIR = 1;
    /**
     * The drive has removable media; for example, a floppy drive, thumb drive, or flash card reader.
     */
    public static final int DRIVE_REMOVABLE = 2;
    /**
     * The drive has fixed media; for example, a hard disk drive or flash drive.
     */
    public static final int DRIVE_FIXED = 3;
    /**
     * The drive is a remote (network) drive.
     */
    public static final int DRIVE_REMOTE = 4;
    /**
     * The drive is a CD-ROM drive.
     */
    public static final int DRIVE_CDROM = 5;
    /**
     * The drive is a RAM disk.
     */
    public static final int DRIVE_RAMDISK = 6;

    private DriveType() {

        throw new UnsupportedOperationException();

    }

}
