package com.etdon.winj.constant;

public final class ShutdownAction {

    public static final int SHUTDOWN_NO_REBOOT = 0;
    public static final int SHUTDOWN_REBOOT = 1;
    public static final int SHUTDOWN_POWER_OFF = 2;

    private ShutdownAction() {

        throw new UnsupportedOperationException();

    }

}
