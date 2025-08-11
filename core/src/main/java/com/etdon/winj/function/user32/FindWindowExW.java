package com.etdon.winj.function.user32;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.jbinder.common.NativeDocumentation;
import com.etdon.jbinder.common.NativeName;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;

/**
 * Retrieves a handle to a window whose class name and window name match the specified strings. The function searches
 * child windows, beginning with the one following the specified child window. This function does not perform a
 * case-sensitive search.
 */
@NativeName(FindWindowExW.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-findwindowexw")
public final class FindWindowExW extends NativeFunction {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "FindWindowExW";
    public static final FunctionDescriptor FIND_WINDOW_EX_W_SIGNATURE = FunctionDescriptor.of(
            HWND,
            HWND.withName("hWndParent"),
            HWND.withName("hWndChildAfter"),
            LPCWSTR.withName("lpszClass"),
            LPCWSTR.withName("lpszWindow")
    );

    /**
     * A handle to the parent window whose child windows are to be searched.
     * <p>
     * If hwndParent is NULL, the function uses the desktop window as the parent window. The function searches among
     * windows that are child windows of the desktop.
     * <p>
     * If hwndParent is HWND_MESSAGE, the function searches all message-only windows.
     */
    @NativeName("hWndParent")
    private MemorySegment parentWindowHandle = MemorySegment.NULL;

    /**
     * A handle to a child window. The search begins with the next child window in the Z order. The child window must
     * be a direct child window of hwndParent, not just a descendant window.
     * <p>
     * If hwndChildAfter is NULL, the search begins with the first child window of hwndParent.
     * <p>
     * Note that if both hwndParent and hwndChildAfter are NULL, the function searches all top-level and message-only
     * windows.
     */
    @NativeName("hWndChildAfter")
    private MemorySegment childAfterWindowHandle = MemorySegment.NULL;

    /**
     * The class name or a class atom created by a previous call to the RegisterClass or RegisterClassEx function. The
     * atom must be placed in the low-order word of lpszClass; the high-order word must be zero.
     * <p>
     * If lpszClass is a string, it specifies the window class name. The class name can be any name registered with
     * RegisterClass or RegisterClassEx, or any of the predefined control-class names, or it can be
     * MAKEINTATOM(0x8000). In this latter case, 0x8000 is the atom for a menu class. For more information, see the
     * Remarks section of this topic.
     */
    @NativeName("lpszClass")
    private MemorySegment className = MemorySegment.NULL;

    /**
     * The window name (the window's title). If this parameter is NULL, all window names match.
     */
    @NativeName("lpszWindow")
    private MemorySegment windowName = MemorySegment.NULL;

    private FindWindowExW(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, FIND_WINDOW_EX_W_SIGNATURE);

        Conditional.executeIfNotNull(builder.parentWindowHandle, () -> this.parentWindowHandle = builder.parentWindowHandle);
        Conditional.executeIfNotNull(builder.childAfterWindowHandle, () -> this.childAfterWindowHandle = builder.childAfterWindowHandle);
        Conditional.executeIfNotNull(builder.className, () -> this.className = builder.className);
        Conditional.executeIfNotNull(builder.windowName, () -> this.windowName = builder.windowName);

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.parentWindowHandle,
                this.childAfterWindowHandle,
                this.className,
                this.windowName
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<FindWindowExW> {

        private MemorySegment parentWindowHandle;
        private MemorySegment childAfterWindowHandle;
        private MemorySegment className;
        private MemorySegment windowName;

        private Builder() {

        }

        /**
         * Overrides the builder internal parentWindowHandle value with the provided value.
         *
         * @param parentWindowHandle The parentWindowHandle. {@link FindWindowExW#parentWindowHandle}
         * @return The builder instance.
         */
        public Builder parentWindowHandle(@NotNull final MemorySegment parentWindowHandle) {

            this.parentWindowHandle = parentWindowHandle;
            return this;

        }

        /**
         * Overrides the builder internal childAfterWindowHandle value with the provided value.
         *
         * @param childAfterWindowHandle The childAfterWindowHandle. {@link FindWindowExW#childAfterWindowHandle}
         * @return The builder instance.
         */
        public Builder childAfterWindowHandle(@NotNull final MemorySegment childAfterWindowHandle) {

            this.childAfterWindowHandle = childAfterWindowHandle;
            return this;

        }

        /**
         * Overrides the builder internal className value with the provided value.
         *
         * @param className The className. {@link FindWindowExW#className}
         * @return The builder instance.
         */
        public Builder className(@NotNull final MemorySegment className) {

            this.className = className;
            return this;

        }

        /**
         * Overrides the builder internal windowName value with the provided value.
         *
         * @param windowName The windowName. {@link FindWindowExW#windowName}
         * @return The builder instance.
         */
        public Builder windowName(@NotNull final MemorySegment windowName) {

            this.windowName = windowName;
            return this;

        }

        @NotNull
        @Override
        public FindWindowExW build() {

            return new FindWindowExW(this);

        }

    }

}