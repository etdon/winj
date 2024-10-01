package com.etdon.winj.constant;

public final class VirtualKeyMapType {

    /**
     * The uCode parameter is a virtual-key code and is translated into a scan code. If it is a virtual-key code that
     * does not distinguish between left- and right-hand keys, the left-hand scan code is returned. If there is no
     * translation, the function returns 0.
     */
    public static final int MAPVK_VK_TO_VSC = 0;
    /**
     * The uCode parameter is a scan code and is translated into a virtual-key code that does not distinguish between
     * left- and right-hand keys. If there is no translation, the function returns 0.
     * <p>
     * Windows Vista and later: the high byte of the uCode value can contain either 0xe0 or 0xe1 to specify the
     * extended scan code.
     */
    public static final int MAPVK_VSC_TO_VK = 1;
    /**
     * The uCode parameter is a virtual-key code and is translated into an unshifted character value in the low order
     * word of the return value. Dead keys (diacritics) are indicated by setting the top bit of the return value. If
     * there is no translation, the function returns 0. See Remarks.
     */
    public static final int MAPVK_VK_TO_CHAR = 2;
    /**
     * The uCode parameter is a scan code and is translated into a virtual-key code that distinguishes between left-
     * and right-hand keys. If there is no translation, the function returns 0.
     * <p>
     * Windows Vista and later: the high byte of the uCode value can contain either 0xe0 or 0xe1 to specify the
     * extended scan code.
     */
    public static final int MAPVK_VSC_TO_VK_EX = 3;
    /**
     * Windows Vista and later: The uCode parameter is a virtual-key code and is translated into a scan code. If it is
     * a virtual-key code that does not distinguish between left- and right-hand keys, the left-hand scan code is
     * returned. If the scan code is an extended scan code, the high byte of the returned value will contain either
     * 0xe0 or 0xe1 to specify the extended scan code. If there is no translation, the function returns 0.
     */
    public static final int MAPVK_VK_TO_VSC_EX = 4;

    private VirtualKeyMapType() {

        throw new UnsupportedOperationException();

    }

}
