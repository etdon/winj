package com.etdon.winj.constant;

public final class ShutdownFlag {

    /**
     * All sessions are forcefully logged off. If this flag is not set and users other than the current user are logged
     * on to the computer specified by the lpMachineName parameter, this function fails with a return value of
     * ERROR_SHUTDOWN_USERS_LOGGED_ON.
     */
    public static final int SHUTDOWN_FORCE_OTHERS = 0x1;
    /**
     * Specifies that the originating session is logged off forcefully. If this flag is not set, the originating
     * session is shut down interactively, so a shutdown is not guaranteed even if the function returns successfully.
     */
    public static final int SHUTDOWN_FORCE_SELF = 0x2;
    /**
     * Overrides the grace period so that the computer is shut down immediately.
     */
    public static final int SHUTDOWN_GRACE_OVERRIDE = 0x20;
    /**
     * Beginning with InitiateShutdown running on Windows 8, you must include the SHUTDOWN_HYBRID flag with one or more
     * of the flags in this table to specify options for the shutdown.
     * <p>
     * Beginning with Windows 8, InitiateShutdown always initiate a full system shutdown if the SHUTDOWN_HYBRID flag is
     * absent.
     */
    public static final int SHUTDOWN_HYBRID = 0x200;
    /**
     * The computer installs any updates before starting the shutdown.
     */
    public static final int SHUTDOWN_INSTALL_UPDATES = 0x40;
    /**
     * The computer is shut down but is not powered down or rebooted.
     */
    public static final int SHUTDOWN_NOREBOOT = 0x10;
    /**
     * The computer is shut down and powered down.
     */
    public static final int SHUTDOWN_POWEROFF = 0x8;
    /**
     * The computer is shut down and rebooted.
     */
    public static final int SHUTDOWN_RESTART = 0x4;
    /**
     * The system is rebooted using the ExitWindowsEx function with the EWX_RESTARTAPPS flag. This restarts any
     * applications that have been registered for restart using the RegisterApplicationRestart function.
     */
    public static final int SHUTDOWN_RESTARTAPPS = 0x80;

    private ShutdownFlag() {

        throw new UnsupportedOperationException();

    }

}
