package com.etdon.winj.constant;

public final class UserProcessParametersFlag {

    public static final int RTL_USER_PROC_PARAMS_NORMALIZED = 0x00000001;
    public static final int RTL_USER_PROC_PROFILE_USER = 0x00000002;
    public static final int RTL_USER_PROC_PROFILE_KERNEL = 0x00000004;
    public static final int RTL_USER_PROC_PROFILE_SERVER = 0x00000008;
    public static final int RTL_USER_PROC_RESERVE_1MB = 0x00000020;
    public static final int RTL_USER_PROC_RESERVE_16MB = 0x00000040;
    public static final int RTL_USER_PROC_CASE_SENSITIVE = 0x00000080;
    public static final int RTL_USER_PROC_DISABLE_HEAP_DECOMMIT = 0x00000100;
    public static final int RTL_USER_PROC_DLL_REDIRECTION_LOCAL = 0x00001000;
    public static final int RTL_USER_PROC_APP_MANIFEST_PRESENT = 0x00002000;
    public static final int RTL_USER_PROC_IMAGE_KEY_MISSING = 0x00004000;
    public static final int RTL_USER_PROC_DEV_OVERRIDE_ENABLED = 0x00008000;
    public static final int RTL_USER_PROC_OPTIN_PROCESS = 0x00020000;
    public static final int RTL_USER_PROC_SESSION_OWNER = 0x00040000;
    public static final int RTL_USER_PROC_HANDLE_USER_CALLBACK_EXCEPTIONS = 0x00080000;
    public static final int RTL_USER_PROC_PROTECTED_PROCESS = 0x00400000;
    public static final int RTL_USER_PROC_SECURE_PROCESS = 0x80000000;

    private UserProcessParametersFlag() {

        throw new UnsupportedOperationException();

    }

}
