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

@NativeName(EndPaint.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-endpaint")
public final class EndPaint extends NativeFunction<Integer> {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "EndPaint";
    public static final FunctionDescriptor END_PAINT_SIGNATURE = FunctionDescriptor.of(
            BOOL,
            HWND.withName("hWnd"),
            PAINTSTRUCT.withName("lpPaint")
    );

    /**
     * Handle to the window that has been repainted.
     */
    @NativeName("hWnd")
    private final MemorySegment windowHandle;

    /**
     * Pointer to a PAINTSTRUCT structure that contains the painting information retrieved by BeginPaint.
     */
    @NativeName("lpPaint")
    private final MemorySegment paintDataPointer;

    private EndPaint(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, END_PAINT_SIGNATURE);

        this.windowHandle = builder.windowHandle;
        this.paintDataPointer = builder.paintDataPointer;

    }

    @Override
    public Integer call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return (Integer) super.obtainHandle(linker, symbolLookup).invoke(
                this.windowHandle,
                this.paintDataPointer
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<EndPaint> {

        private MemorySegment windowHandle;
        private MemorySegment paintDataPointer;

        private Builder() {

        }

        /**
         * Overrides the builder internal window handle value with the provided value.
         *
         * @param windowHandle The window handle. {@link EndPaint#windowHandle}
         * @return The builder instance.
         */
        public Builder windowHandle(@NotNull final MemorySegment windowHandle) {

            Preconditions.checkNotNull(windowHandle);
            this.windowHandle = windowHandle;
            return this;

        }

        /**
         * Overrides the builder internal paint data pointer value with the provided value.
         *
         * @param paintDataPointer The paint data pointer. {@link EndPaint#paintDataPointer}
         * @return The builder instance.
         */
        public Builder paintDataPointer(@NotNull final MemorySegment paintDataPointer) {

            Preconditions.checkNotNull(paintDataPointer);
            this.paintDataPointer = paintDataPointer;
            return this;

        }

        @NotNull
        @Override
        public EndPaint build() {

            Preconditions.checkNotNull(this.windowHandle);
            Preconditions.checkNotNull(this.paintDataPointer);

            return new EndPaint(this);

        }

    }

}
