package com.etdon.winj.constant;

public final class WaitResult {

    /**
     * The specified object is a mutex object that was not released by the thread that owned the mutex object before
     * the owning thread terminated. Ownership of the mutex object is granted to the calling thread and the mutex state
     * is set to non-signaled.
     * <p>
     * If the mutex was protecting persistent state information, you should check it for consistency.
     */
    public static final long WAIT_ABANDONED = 0x00000080L;
    /**
     * The state of the specified object is signaled.
     */
    public static final long WAIT_OBJECT_0 = 0x00000000L;
    /**
     * The time-out interval elapsed, and the object's state is non-signaled.
     */
    public static final long WAIT_TIMEOUT = 0x00000102L;
    /**
     * The function has failed. To get extended error information, call GetLastError.
     */
    public static final long WAIT_FAILED = 0xFFFFFFFFL;

    private WaitResult() {

        throw new UnsupportedOperationException();

    }

}
