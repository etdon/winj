package com.etdon.winj.function.user32;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
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
import static com.etdon.winj.type.constant.NativeDataType.INTEGER;

/**
 * Retrieves the length, in characters, of the specified window's title bar text (if the window has a title bar). If
 * the specified window is a control, the function retrieves the length of the text within the control. However,
 * GetWindowTextLength cannot retrieve the length of the text of an edit control in another application.
 */
@NativeName(GetWindowTextLengthW.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-getwindowtextlengthw")
public final class GetWindowTextLengthW extends NativeFunction {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "GetWindowTextLengthW";
    public static final FunctionDescriptor GET_WINDOW_TEXT_LENGTH_W_SIGNATURE = FunctionDescriptor.of(
            INTEGER,
            HWND.withName("hWnd")
    );

    /**
     * A handle to the window or control.
     */
    @NativeName("hWnd")
    private final MemorySegment handle;

    private GetWindowTextLengthW(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, GET_WINDOW_TEXT_LENGTH_W_SIGNATURE);

        this.handle = builder.handle;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(this.handle);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<GetWindowTextLengthW> {

        private MemorySegment handle;

        private Builder() {

        }

        /**
         * Overrides the builder internal handle value with the provided value.
         *
         * @param handle The window or control handle. {@link GetWindowTextLengthW#handle}
         * @return The builder instance.
         */
        public Builder handle(@NotNull final MemorySegment handle) {

            Preconditions.checkNotNull(handle);
            this.handle = handle;
            return this;

        }

        @NotNull
        @Override
        public GetWindowTextLengthW build() {

            Preconditions.checkNotNull(this.handle);

            return new GetWindowTextLengthW(this);

        }

    }

}
