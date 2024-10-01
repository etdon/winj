package com.etdon.winj.constant;

public final class LowLevelKeyboardHookFlag {

    /**
     * Test the extended-key flag.
     */
    public static final int LLKHF_EXTENDED = (KeystrokeFlag.KF_EXTENDED >> 8);
    /**
     * Test the event-injected (from a process running at lower integrity level) flag.
     */
    public static final int LLKHF_LOWER_IL_INJECTED = 0x00000002;
    /**
     * Test the event-injected (from any process) flag.
     */
    public static final int LLKHF_INJECTED = 0x00000010;
    /**
     * Test the context code.
     */
    public static final int LLKHF_ALTDOWN = (KeystrokeFlag.KF_ALTDOWN >> 8);
    /**
     * Test the transition-state flag.
     */
    public static final int LLKHF_UP = (KeystrokeFlag.KF_UP >> 8);

    private LowLevelKeyboardHookFlag() {

        throw new UnsupportedOperationException();

    }

}
