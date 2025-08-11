package com.etdon.winj.function.user32;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.NativeDocumentation;
import com.etdon.jbinder.common.NativeName;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.ExtendedWindowStyle;
import com.etdon.winj.constant.WindowStyle;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;
import static java.lang.foreign.ValueLayout.JAVA_INT;

/**
 * Creates an overlapped, pop-up, or child window with an extended window style; otherwise, this function is identical
 * to the CreateWindow function. For more information about creating a window and for full descriptions of the other
 * parameters of CreateWindowEx, see CreateWindow.
 */
@NativeName(CreateWindowExW.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-createwindowexw")
public final class CreateWindowExW extends NativeFunction {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "CreateWindowExW";
    public static final FunctionDescriptor CREATE_WINDOW_EX_W_SIGNATURE = FunctionDescriptor.of(
            HWND,
            DWORD.withName("dwExStyle"),
            LPCWSTR.withName("lpClassName"),
            LPCWSTR.withName("lpWindowName"),
            DWORD.withName("dwStyle"),
            JAVA_INT.withName("X"),
            JAVA_INT.withName("Y"),
            JAVA_INT.withName("nWidth"),
            JAVA_INT.withName("nHeight"),
            HWND.withName("hWndParent"),
            HMENU.withName("hMenu"),
            HINSTANCE.withName("hInstance"),
            LPVOID.withName("lpParam")
    );

    /**
     * The extended window style of the window being created.
     *
     * @see ExtendedWindowStyle
     */
    @NativeName("dwExStyle")
    private final int extendedStyle;

    /**
     * A null-terminated string or a class atom created by a previous call to the RegisterClass or RegisterClassEx
     * function. The atom must be in the low-order word of lpClassName; the high-order word must be zero. If
     * lpClassName is a string, it specifies the window class name. The class name can be any name registered with
     * RegisterClass or RegisterClassEx, provided that the module that registers the class is also the module that
     * creates the window. The class name can also be any of the predefined system class names.
     */
    @NativeName("lpClassName")
    private MemorySegment className = MemorySegment.NULL;

    /**
     * The window name. If the window style specifies a title bar, the window title pointed to by lpWindowName is
     * displayed in the title bar. When using CreateWindow to create controls, such as buttons, check boxes, and
     * static controls, use lpWindowName to specify the text of the control. When creating a static control with the
     * SS_ICON style, use lpWindowName to specify the icon name or identifier. To specify an identifier, use the
     * syntax "#num".
     */
    @NativeName("lpWindowName")
    private MemorySegment windowName = MemorySegment.NULL;

    /**
     * The style of the window being created. This parameter can be a combination of the window style values, plus the
     * control styles indicated in the Remarks section.
     *
     * @see WindowStyle
     */
    @NativeName("dwStyle")
    private final int style;

    /**
     * The initial horizontal position of the window. For an overlapped or pop-up window, the x parameter is the
     * initial x-coordinate of the window's upper-left corner, in screen coordinates. For a child window, x is the
     * x-coordinate of the upper-left corner of the window relative to the upper-left corner of the parent window's
     * client area. If x is set to CW_USEDEFAULT, the system selects the default position for the window's upper-left
     * corner and ignores the y parameter. CW_USEDEFAULT is valid only for overlapped windows; if it is specified for
     * a pop-up or child window, the x and y parameters are set to zero.
     */
    @NativeName("X")
    private final int x;

    /**
     * The initial vertical position of the window. For an overlapped or pop-up window, the y parameter is the initial
     * y-coordinate of the window's upper-left corner, in screen coordinates. For a child window, y is the initial
     * y-coordinate of the upper-left corner of the child window relative to the upper-left corner of the parent
     * window's client area. For a list box y is the initial y-coordinate of the upper-left corner of the list box's
     * client area relative to the upper-left corner of the parent window's client area.
     * <p>
     * If an overlapped window is created with the WS_VISIBLE style bit set and the x parameter is set toCW_USEDEFAULT,
     * then the y parameter determines how the window is shown. If the y parameter is CW_USEDEFAULT, then the window
     * manager calls ShowWindow with the SW_SHOW flag after the window has been created. If the y parameter is some
     * other value, then the window manager calls ShowWindow with that value as the nCmdShow parameter.
     */
    @NativeName("Y")
    private final int y;

    /**
     * The width, in device units, of the window. For overlapped windows, nWidth is the window's width, in screen
     * coordinates, or CW_USEDEFAULT. If nWidth is CW_USEDEFAULT, the system selects a default width and height for the
     * window; the default width extends from the initial x-coordinates to the right edge of the screen; the default
     * height extends from the initial y-coordinate to the top of the icon area. CW_USEDEFAULT is valid only for
     * overlapped windows; if CW_USEDEFAULT is specified for a pop-up or child window, the nWidth and nHeight parameter
     * are set to zero.
     */
    @NativeName("nWidth")
    private final int width;

    /**
     * The height, in device units, of the window. For overlapped windows, nHeight is the window's height, in screen
     * coordinates. If the nWidth parameter is set to CW_USEDEFAULT, the system ignores nHeight.
     */
    @NativeName("nHeight")
    private final int height;

    /**
     * A handle to the parent or owner window of the window being created. To create a child window or an owned window,
     * supply a valid window handle. This parameter is optional for pop-up windows.
     */
    @NativeName("hWndParent")
    private MemorySegment parentHandle = MemorySegment.NULL;

    /**
     * A handle to a menu, or specifies a child-window identifier, depending on the window style. For an overlapped or
     * pop-up window, hMenu identifies the menu to be used with the window; it can be NULL if the class menu is to be
     * used. For a child window, hMenu specifies the child-window identifier, an integer value used by a dialog box
     * control to notify its parent about events. The application determines the child-window identifier; it must be
     * unique for all child windows with the same parent window.
     */
    @NativeName("hMenu")
    private MemorySegment menuHandle = MemorySegment.NULL;

    /**
     * A handle to the instance of the module to be associated with the window.
     */
    @NativeName("hInstance")
    private MemorySegment moduleHandle = MemorySegment.NULL;

    /**
     * Pointer to a value to be passed to the window through the CREATESTRUCT structure (lpCreateParams member) pointed
     * to by the lParam param of the WM_CREATE message. This message is sent to the created window by this function
     * before it returns.
     * <p>
     * If an application calls CreateWindow to create a MDI client window, lpParam should point to a CLIENTCREATESTRUCT
     * structure. If an MDI client window calls CreateWindow to create an MDI child window, lpParam should point to a
     * MDICREATESTRUCT structure. lpParam may be NULL if no additional data is needed.
     */
    @NativeName("lpParam")
    private MemorySegment parameter = MemorySegment.NULL;

    private CreateWindowExW(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, CREATE_WINDOW_EX_W_SIGNATURE);

        this.extendedStyle = builder.extendedStyle;
        Conditional.executeIfNotNull(builder.className, () -> this.className = builder.className);
        Conditional.executeIfNotNull(builder.windowName, () -> this.windowName = builder.windowName);
        this.style = builder.style;
        this.x = builder.x;
        this.y = builder.y;
        this.width = builder.width;
        this.height = builder.height;
        Conditional.executeIfNotNull(builder.parentHandle, () -> this.parentHandle = builder.parentHandle);
        Conditional.executeIfNotNull(builder.menuHandle, () -> this.menuHandle = builder.menuHandle);
        Conditional.executeIfNotNull(builder.moduleHandle, () -> this.moduleHandle = builder.moduleHandle);
        Conditional.executeIfNotNull(builder.parameter, () -> this.parameter = builder.parameter);

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.extendedStyle,
                this.className,
                this.windowName,
                this.style,
                this.x,
                this.y,
                this.width,
                this.height,
                this.parentHandle,
                this.menuHandle,
                this.moduleHandle,
                this.parameter
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<CreateWindowExW> {

        private Integer extendedStyle;
        private MemorySegment className;
        private MemorySegment windowName;
        private Integer style;
        private Integer x;
        private Integer y;
        private Integer width;
        private Integer height;
        private MemorySegment parentHandle;
        private MemorySegment menuHandle;
        private MemorySegment moduleHandle;
        private MemorySegment parameter;

        private Builder() {

        }

        /**
         * Overrides the builder internal extended style value with the provided value.
         *
         * @param extendedStyle The extended style. {@link CreateWindowExW#extendedStyle}
         * @return The builder instance.
         */
        public Builder extendedStyle(final int extendedStyle) {

            this.extendedStyle = extendedStyle;
            return this;

        }

        /**
         * Overrides the builder internal class name value with the provided value.
         *
         * @param className The class name. {@link CreateWindowExW#className}
         * @return The builder instance.
         */
        public Builder className(@NotNull final MemorySegment className) {

            this.className = className;
            return this;

        }

        /**
         * Overrides the builder internal window name value with the provided value.
         *
         * @param windowName The window name. {@link CreateWindowExW#windowName}
         * @return The builder instance.
         */
        public Builder windowName(@NotNull final MemorySegment windowName) {

            this.windowName = windowName;
            return this;

        }

        /**
         * Overrides the builder internal style value with the provided value.
         *
         * @param style The style. {@link CreateWindowExW#style}
         * @return The builder instance.
         */
        public Builder style(final int style) {

            this.style = style;
            return this;

        }

        /**
         * Overrides the builder internal x value with the provided value.
         *
         * @param x The initial horizontal position. {@link CreateWindowExW#x}
         * @return The builder instance.
         */
        public Builder x(final int x) {

            this.x = x;
            return this;

        }

        /**
         * Overrides the builder internal y value with the provided value.
         *
         * @param y The initial vertical position. {@link CreateWindowExW#y}
         * @return The builder instance.
         */
        public Builder y(final int y) {

            this.y = y;
            return this;

        }

        /**
         * Overrides the builder internal width value with the provided value.
         *
         * @param width The width. {@link CreateWindowExW#width}
         * @return The builder instance.
         */
        public Builder width(final int width) {

            this.width = width;
            return this;

        }

        /**
         * Overrides the builder internal height value with the provided value.
         *
         * @param height The height. {@link CreateWindowExW#height}
         * @return The builder instance.
         */
        public Builder height(final int height) {

            this.height = height;
            return this;

        }

        /**
         * Overrides the builder internal parent handle value with the provided value.
         *
         * @param parentHandle The parent handle. {@link CreateWindowExW#parentHandle}
         * @return The builder instance.
         */
        public Builder parentHandle(@NotNull final MemorySegment parentHandle) {

            this.parentHandle = parentHandle;
            return this;

        }

        /**
         * Overrides the builder internal menu handle value with the provided value.
         *
         * @param menuHandle The menu handle. {@link CreateWindowExW#menuHandle}
         * @return The builder instance.
         */
        public Builder menuHandle(@NotNull final MemorySegment menuHandle) {

            this.menuHandle = menuHandle;
            return this;

        }

        /**
         * Overrides the builder internal module handle value with the provided value.
         *
         * @param moduleHandle The module handle. {@link CreateWindowExW#moduleHandle}
         * @return The builder instance.
         */
        public Builder moduleHandle(@NotNull final MemorySegment moduleHandle) {

            this.moduleHandle = moduleHandle;
            return this;

        }

        /**
         * Overrides the builder internal parameter value with the provided value.
         *
         * @param parameter The parameter. {@link CreateWindowExW#parameter}
         * @return The builder instance.
         */
        public Builder parameter(@NotNull final MemorySegment parameter) {

            this.parameter = parameter;
            return this;

        }

        @NotNull
        @Override
        public CreateWindowExW build() {

            Preconditions.checkNotNull(this.extendedStyle);
            Preconditions.checkNotNull(this.style);
            Preconditions.checkNotNull(this.x);
            Preconditions.checkNotNull(this.y);
            Preconditions.checkNotNull(this.width);
            Preconditions.checkNotNull(this.height);

            return new CreateWindowExW(this);

        }

    }

}
