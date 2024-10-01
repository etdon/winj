package com.etdon.winj.constant;

@SuppressWarnings("SpellCheckingInspection")
public final class HookType {

    /**
     * Installs a hook procedure that monitors messages before the system sends them to the destination window procedure.
     */
    public static final int WH_CALLWNDPROC = 4;
    /**
     * Installs a hook procedure that monitors messages after they have been processed by the destination window procedure.
     */
    public static final int WH_CALLWNDPROCRET = 12;
    /**
     * Installs a hook procedure that receives notifications useful to a CBT application.
     */
    public static final int WH_CBT = 5;
    /**
     * Installs a hook procedure useful for debugging other hook procedures.
     */
    public static final int WH_DEBUG = 9;
    /**
     * Installs a hook procedure that will be called when the application's foreground thread is about to become idle.
     * This hook is useful for performing low priority tasks during idle time.
     */
    public static final int WH_FOREGROUNDIDLE = 11;
    /**
     * Installs a hook procedure that monitors messages posted to a message queue.
     */
    public static final int WH_GETMESSAGE = 3;
    /**
     * Installs a hook procedure that posts messages previously recorded by a WH_JOURNALRECORD hook procedure.
     */
    public static final int WH_JOURNALPLAYBACK = 1;
    /**
     * Installs a hook procedure that records input messages posted to the system message queue. This hook is useful
     * for recording macros.
     */
    public static final int WH_JOURNALRECORD = 0;
    /**
     * Installs a hook procedure that monitors keystroke messages.
     */
    public static final int WH_KEYBOARD = 2;
    /**
     * Installs a hook procedure that monitors low-level keyboard input events.
     */
    public static final int WH_KEYBOARD_LL = 13;
    /**
     * Installs a hook procedure that monitors mouse messages.
     */
    public static final int WH_MOUSE = 7;
    /**
     * Installs a hook procedure that monitors low-level mouse input events.
     */
    public static final int WH_MOUSE_LL = 14;
    /**
     * Installs a hook procedure that monitors messages generated as a result of an input event in a dialog box,
     * message box, menu, or scroll bar.
     */
    public static final int WH_MSGFILTER = -1;
    /**
     * Installs a hook procedure that receives notifications useful to shell applications.
     */
    public static final int WH_SHELL = 10;
    /**
     * Installs a hook procedure that monitors messages generated as a result of an input event in a dialog box,
     * message box, menu, or scroll bar. The hook procedure monitors these messages for all applications in the same
     * desktop as the calling thread.
     */
    public static final int WH_SYSMSGFILTER = 6;

    private HookType() {

        throw new UnsupportedOperationException();

    }

}
