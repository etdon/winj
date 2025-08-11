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

/**
 * Copies the text of the specified window's title bar (if it has one) into a buffer. If the specified window is a
 * control, the text of the control is copied. However, GetWindowText cannot retrieve the text of a control in another
 * application.
 */
@NativeName(GetWindowTextW.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-getwindowtextw")
public final class GetWindowTextW extends NativeFunction {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "GetWindowTextW";
    public static final FunctionDescriptor GET_WINDOW_TEXT_W_SIGNATURE = FunctionDescriptor.of(
            INTEGER,
            HWND.withName("hWnd"),
            LPWSTR.withName("lpString"),
            INTEGER.withName("nMaxCount")
    );

    /**
     * A handle to the window or control containing the text.
     */
    @NativeName("hWnd")
    private final MemorySegment windowHandle;

    /**
     * The buffer that will receive the text. If the string is as long or longer than the buffer, the string is
     * truncated and terminated with a null character.
     */
    @NativeName("lpString")
    private final MemorySegment textBuffer;

    /**
     * The maximum number of characters to copy to the buffer, including the null character. If the text exceeds this
     * limit, it is truncated.
     */
    @NativeName("nMaxCount")
    private final int textLength;

    private GetWindowTextW(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, GET_WINDOW_TEXT_W_SIGNATURE);

        this.windowHandle = builder.windowHandle;
        this.textBuffer = builder.textBuffer;
        this.textLength = builder.textLength;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.windowHandle,
                this.textBuffer,
                this.textLength
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<GetWindowTextW> {

        private MemorySegment windowHandle;
        private MemorySegment textBuffer;
        private int textLength;

        private Builder() {

        }

        /**
         * Overrides the builder internal window handle value with the provided value.
         *
         * @param windowHandle The window handle. {@link GetWindowTextW#windowHandle}
         * @return The builder instance.
         */
        public Builder windowHandle(@NotNull final MemorySegment windowHandle) {

            Preconditions.checkNotNull(windowHandle);
            this.windowHandle = windowHandle;
            return this;

        }

        /**
         * Overrides the builder internal text buffer value with the provided value.
         *
         * @param textBuffer The text buffer. {@link GetWindowTextW#textBuffer}
         * @return The builder instance.
         */
        public Builder textBuffer(@NotNull final MemorySegment textBuffer) {

            Preconditions.checkNotNull(textBuffer);
            this.textBuffer = textBuffer;
            return this;

        }

        /**
         * Overrides the builder internal text length value with the provided value.
         *
         * @param textLength The text length. {@link GetWindowTextW#textLength}
         * @return The builder instance.
         */
        public Builder textLength(final int textLength) {

            Preconditions.checkState(textLength >= 0);
            this.textLength = textLength;
            return this;

        }

        @NotNull
        @Override
        public GetWindowTextW build() {

            Preconditions.checkNotNull(this.windowHandle);
            Preconditions.checkNotNull(this.textBuffer);
            Preconditions.checkState(this.textLength > 0);

            return new GetWindowTextW(this);

        }

    }

}
