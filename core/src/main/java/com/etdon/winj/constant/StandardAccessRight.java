package com.etdon.winj.constant;

public final class StandardAccessRight {

    /**
     * The right to delete the object.
     */
    public static final int DELETE = 0x00010000;
    /**
     * The right to read the information in the object's security descriptor, not including the information in the
     * system access control list (SACL).
     */
    public static final int READ_CONTROL = 0x00020000;
    /**
     * The right to modify the discretionary access control list (DACL) in the object's security descriptor.
     */
    public static final int WRITE_DAC = 0x00040000;
    /**
     * The right to change the owner in the object's security descriptor.
     */
    public static final int WRITE_OWNER = 0x00080000;
    /**
     * The right to use the object for synchronization. This enables a thread to wait until the object is in the
     * signaled state. Some object types do not support this access right.
     */
    public static final int SYNCHRONIZE = 0x00100000;
    /**
     * Combines DELETE, READ_CONTROL, WRITE_DAC, and WRITE_OWNER access.
     */
    public static final int STANDARD_RIGHTS_REQUIRED = 0x000F0000;
    /**
     * Currently defined to equal READ_CONTROL.
     */
    public static final int STANDARD_RIGHTS_READ = READ_CONTROL;
    /**
     * Currently defined to equal READ_CONTROL.
     */
    public static final int STANDARD_RIGHTS_WRITE = READ_CONTROL;
    /**
     * Currently defined to equal READ_CONTROL.
     */
    public static final int STANDARD_RIGHTS_EXECUTE = READ_CONTROL;
    /**
     * Combines DELETE, READ_CONTROL, WRITE_DAC, WRITE_OWNER, and SYNCHRONIZE access.
     */
    public static final int STANDARD_RIGHTS_ALL = 0x001F0000;

    private StandardAccessRight() {

        throw new UnsupportedOperationException();

    }

}
