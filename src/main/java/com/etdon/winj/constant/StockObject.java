package com.etdon.winj.constant;

public final class StockObject {

    /**
     * White brush.
     */
    public static final int WHITE_BRUSH = 0;
    /**
     * Light gray brush.
     */
    public static final int LTGRAY_BRUSH = 1;
    /**
     * Gray brush.
     */
    public static final int GRAY_BRUSH = 2;
    /**
     * Dark gray brush.
     */
    public static final int DKGRAY_BRUSH = 3;
    /**
     * Black pen.
     */
    public static final int BLACK_BRUSH = 4;
    /**
     * Null brush (equivalent to HOLLOW_BRUSH).
     */
    public static final int NULL_BRUSH = 5;
    /**
     * Hollow brush (equivalent to NULL_BRUSH).
     */
    public static final int HOLLOW_BRUSH = 5;
    /**
     * White pen.
     */
    public static final int WHITE_PEN = 6;
    /**
     * Black pen.
     */
    public static final int BLACK_PEN = 7;
    /**
     * Null pen. The null pen draws nothing.
     */
    public static final int NULL_PEN = 8;
    /**
     * Original equipment manufacturer (OEM) dependent fixed-pitch (monospace) font.
     */
    public static final int OEM_FIXED_FONT = 10;
    /**
     * Windows fixed-pitch (monospace) system font.
     */
    public static final int ANSI_FIXED_FONT = 11;
    /**
     * Windows variable-pitch (proportional space) system font.
     */
    public static final int ANSI_VAR_FONT = 12;
    /**
     * System font. By default, the system uses the system font to draw menus, dialog box controls, and text. It is not
     * recommended that you use DEFAULT_GUI_FONT or SYSTEM_FONT to obtain the font used by dialogs and windows; for
     * more information, see the remarks section.
     * <p>
     * The default system font is Tahoma.
     */
    public static final int SYSTEM_FONT = 13;
    /**
     * Device-dependent font.
     */
    public static final int DEVICE_DEFAULT_FONT = 14;
    /**
     * Default palette. This palette consists of the static colors in the system palette.
     */
    public static final int DEFAULT_PALETTE = 15;
    /**
     * Fixed-pitch (monospace) system font. This stock object is provided only for compatibility with 16-bit Windows
     * versions earlier than 3.0.
     */
    public static final int SYSTEM_FIXED_FONT = 16;
    /**
     * Default font for user interface objects such as menus and dialog boxes. It is not recommended that you use
     * DEFAULT_GUI_FONT or SYSTEM_FONT to obtain the font used by dialogs and windows; for more information, see the
     * remarks section.
     * <p>
     * The default font is Tahoma.
     */
    public static final int DEFAULT_GUI_FONT = 17;
    /**
     * Solid color brush. The default color is white. The color can be changed by using the SetDCBrushColor function. For
     * more information, see the Remarks section.
     */
    public static final int DC_BRUSH = 18;
    /**
     * Solid pen color. The default color is black. The color can be changed by using the SetDCPenColor function. For
     * more information, see the Remarks section.
     */
    public static final int DC_PEN = 19;
    /**
     * Internally referred to as 20.
     */
    public static final int COLOR_SPACE = 20;
    /**
     * Internally referred to as 21.
     */
    public static final int BITMAP = 21;

    private StockObject() {

        throw new UnsupportedOperationException();

    }

}
