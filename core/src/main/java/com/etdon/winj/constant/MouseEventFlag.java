package com.etdon.winj.constant;

public final class MouseEventFlag {

    /**
     * Movement occurred.
     */
    public static final int MOUSEEVENTF_MOVE = 0x0001;
    /**
     * The left button was pressed.
     */
    public static final int MOUSEEVENTF_LEFTDOWN = 0x0002;
    /**
     * The left button was released.
     */
    public static final int MOUSEEVENTF_LEFTUP = 0x0004;
    /**
     * The right button was pressed.
     */
    public static final int MOUSEEVENTF_RIGHTDOWN = 0x0008;
    /**
     * The right button was released.
     */
    public static final int MOUSEEVENTF_RIGHTUP = 0x0010;
    /**
     * The middle button was pressed.
     */
    public static final int MOUSEEVENTF_MIDDLEDOWN = 0x0020;
    /**
     * The middle button was released.
     */
    public static final int MOUSEEVENTF_MIDDLEUP = 0x0040;
    /**
     * An X button was pressed.
     */
    public static final int MOUSEEVENTF_XDOWN = 0x0080;
    /**
     * An X button was released.
     */
    public static final int MOUSEEVENTF_XUP = 0x0100;
    /**
     * The wheel was moved, if the mouse has a wheel. The amount of movement is specified in mouseData.
     */
    public static final int MOUSEEVENTF_WHEEL = 0x0800;
    /**
     * The wheel was moved horizontally, if the mouse has a wheel. The amount of movement is specified in mouseData.
     * <p>
     * Windows XP/2000: This value is not supported.
     */
    public static final int MOUSEEVENTF_HWHEEL = 0x1000;
    /**
     * The WM_MOUSEMOVE messages will not be coalesced. The default behavior is to coalesce WM_MOUSEMOVE messages.
     * <p>
     * Windows XP/2000: This value is not supported.
     */
    public static final int MOUSEEVENTF_MOVE_NOCOALESCE = 0x2000;
    /**
     * Maps coordinates to the entire desktop. Must be used with MOUSEEVENTF_ABSOLUTE.
     */
    public static final int MOUSEEVENTF_VIRTUALDESK = 0x4000;
    /**
     * The dx and dy members contain normalized absolute coordinates. If the flag is not set, dxand dy contain relative
     * data (the change in position since the last reported position). This flag can be set, or not set, regardless of
     * what kind of mouse or other pointing device, if any, is connected to the system. For further information about
     * relative mouse motion, see the following Remarks section.
     */
    public static final int MOUSEEVENTF_ABSOLUTE = 0x8000;

    private MouseEventFlag() {

        throw new UnsupportedOperationException();

    }

}
