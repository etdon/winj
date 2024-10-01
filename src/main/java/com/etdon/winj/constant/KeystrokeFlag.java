package com.etdon.winj.constant;

public final class KeystrokeFlag {

    /**
     * Manipulates the extended key flag.
     */
    public static final int KF_EXTENDED = 0x0100;
    /**
     * Manipulates the dialog mode flag, which indicates whether a dialog box is active.
     */
    public static final int KF_DLGMODE = 0x0800;
    /**
     * Manipulates the menu mode flag, which indicates whether a menu is active.
     */
    public static final int KF_MENUMODE = 0x1000;
    /**
     * Manipulates the context code flag.
     */
    public static final int KF_ALTDOWN = 0x2000;
    /**
     * Manipulates the previous key state flag.
     */
    public static final int KF_REPEAT = 0x4000;
    /**
     * Manipulates the transition state flag.
     */
    public static final int KF_UP = 0x8000;

    private KeystrokeFlag() {

        throw new UnsupportedOperationException();

    }

}
