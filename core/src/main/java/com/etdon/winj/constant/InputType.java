package com.etdon.winj.constant;

public final class InputType {

    /**
     * The event is a mouse event. Use the mi structure of the union.
     */
    public static final int INPUT_MOUSE = 0;
    /**
     * The event is a keyboard event. Use the ki structure of the union.
     */
    public static final int INPUT_KEYBOARD = 1;
    /**
     * The event is a hardware event. Use the hi structure of the union.
     */
    public static final int INPUT_HARDWARE = 2;

    private InputType() {

        throw new UnsupportedOperationException();

    }

}
