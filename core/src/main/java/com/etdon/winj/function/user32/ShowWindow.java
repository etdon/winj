package com.etdon.winj.function.user32;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.NativeDocumentation;
import com.etdon.jbinder.common.NativeName;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.WindowPresentation;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;
import static java.lang.foreign.ValueLayout.*;

/**
 * Sets the specified window's show state.
 */
@NativeName(ShowWindow.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-showwindow")
public final class ShowWindow extends NativeFunction<Integer> {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "ShowWindow";
    public static final FunctionDescriptor SHOW_WINDOW_SIGNATURE = FunctionDescriptor.of(
            BOOL,
            HWND.withName("hWnd"),
            JAVA_INT.withName("nCmdShow")
    );

    /**
     * A handle to the window.
     */
    @NativeName("hWnd")
    private final MemorySegment handle;

    /**
     * Controls how the window is to be shown. This parameter is ignored the first time an application calls
     * ShowWindow, if the program that launched the application provides a STARTUPINFO structure. Otherwise, the first
     * time ShowWindow is called, the value should be the value obtained by the WinMain function in its nCmdShow
     * parameter.
     *
     * @see WindowPresentation
     */
    @NativeName("nCmdShow")
    private int presentation = WindowPresentation.SW_SHOW;

    private ShowWindow(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, SHOW_WINDOW_SIGNATURE);

        this.handle = builder.handle;
        Conditional.executeIfNotNull(builder.presentation, () -> this.presentation = builder.presentation);

    }

    /**
     * Constructor that only takes a window handle, only used by the ofHandle static factory method.
     *
     * @param handle The window handle.
     * @see ShowWindow#ofHandle(MemorySegment)
     */
    private ShowWindow(@NotNull final MemorySegment handle) {

        super(LIBRARY, NATIVE_NAME, SHOW_WINDOW_SIGNATURE);

        this.handle = handle;

    }

    @Override
    public Integer call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return (Integer) super.obtainHandle(linker, symbolLookup).invoke(this.handle, this.presentation);

    }

    public static ShowWindow ofHandle(@NotNull final MemorySegment handle) {

        return new ShowWindow(handle);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<ShowWindow> {

        private MemorySegment handle;
        private Integer presentation;

        private Builder() {

        }

        /**
         * Overrides the builder internal handle value with the provided value.
         *
         * @param handle The handle. {@link ShowWindow#handle}
         * @return The builder instance.
         */
        public Builder handle(@NotNull final MemorySegment handle) {

            this.handle = handle;
            return this;

        }

        /**
         * Overrides the builder internal presentation value with the provided value.
         *
         * @param presentation The presentation. {@link ShowWindow#presentation}
         * @return The builder instance.
         */
        public Builder presentation(final int presentation) {

            this.presentation = presentation;
            return this;

        }

        @NotNull
        @Override
        public ShowWindow build() {

            Preconditions.checkNotNull(this.handle);

            return new ShowWindow(this);

        }

    }

}
