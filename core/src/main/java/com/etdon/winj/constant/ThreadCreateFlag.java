package com.etdon.winj.constant;

public final class ThreadCreateFlag {

    public static final int THREAD_CREATE_FLAGS_NONE = 0x00000000;
    public static final int THREAD_CREATE_FLAGS_CREATE_SUSPENDED = 0x00000001;
    public static final int THREAD_CREATE_FLAGS_SKIP_THREAD_ATTACH = 0x00000002;
    public static final int THREAD_CREATE_FLAGS_HIDE_FROM_DEBUGGER = 0x00000004;
    public static final int THREAD_CREATE_FLAGS_LOADER_WORKER = 0x00000010;
    public static final int THREAD_CREATE_FLAGS_SKIP_LOADER_INIT = 0x00000020;
    public static final int THREAD_CREATE_FLAGS_BYPASS_PROCESS_FREEZE = 0x00000040;

    private ThreadCreateFlag() {

        throw new UnsupportedOperationException();

    }

}
