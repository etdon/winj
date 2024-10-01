package com.etdon.winj.constant;

import java.lang.foreign.MemorySegment;

public final class WindowHandle {

    /**
     * The message is posted to all top-level windows in the system, including disabled or invisible unowned windows,
     * overlapped windows, and pop-up windows. The message is not posted to child windows.
     */
    public static final MemorySegment HWND_BROADCAST = MemorySegment.ofAddress(0xffff);

    private WindowHandle() {

        throw new UnsupportedOperationException();

    }

}
