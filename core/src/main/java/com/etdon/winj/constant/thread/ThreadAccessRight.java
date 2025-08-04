package com.etdon.winj.constant.thread;

public final class ThreadAccessRight {

    public static final int SYNCHRONIZE = 0x00100000;
    public static final int THREAD_ALL_ACCESS = (0x000F0000 | SYNCHRONIZE | 0xFFFF);
    public static final int THREAD_DIRECT_IMPERSONATION = 0x0200;
    public static final int THREAD_GET_CONTEXT = 0x0008;
    public static final int THREAD_IMPERSONATE = 0x0100;
    public static final int THREAD_QUERY_INFORMATION = 0x0040;
    public static final int THREAD_QUERY_LIMITED_INFORMATION = 0x0800;
    public static final int THREAD_SET_CONTEXT = 0x0010;
    public static final int THREAD_SET_INFORMATION = 0x0020;
    public static final int THREAD_SET_LIMITED_INFORMATION = 0x0400;
    public static final int THREAD_SET_THREAD_TOKEN = 0x0080;
    public static final int THREAD_SUSPEND_RESUME = 0x0002;
    public static final int THREAD_TERMINATE = 0x0001;

    private ThreadAccessRight() {

        throw new UnsupportedOperationException();

    }

}
