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
 * The BeginPaint function prepares the specified window for painting and fills a PAINTSTRUCT structure with
 * information about the painting.
 */
@NativeName(BeginPaint.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-beginpaint")
public final class BeginPaint extends NativeFunction<MemorySegment> {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "BeginPaint";
    public static final FunctionDescriptor BEGIN_PAINT_SIGNATURE = FunctionDescriptor.of(
            HDC,
            HWND.withName("hWnd"),
            LPPAINTSTRUCT.withName("lpPaint")
    );

    /**
     * Handle to the window to be repainted.
     */
    @NativeName("hWnd")
    private final MemorySegment windowHandle;

    /**
     * Pointer to the PAINTSTRUCT structure that will receive painting information.
     *
     * @see com.etdon.winj.type.PaintData
     */
    @NativeName("lpPaint")
    private final MemorySegment paintDataPointer;

    private BeginPaint(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, BEGIN_PAINT_SIGNATURE);

        this.windowHandle = builder.windowHandle;
        this.paintDataPointer = builder.paintDataPointer;

    }

    @Override
    public MemorySegment call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return (MemorySegment) super.obtainHandle(linker, symbolLookup).invoke(
                this.windowHandle,
                this.paintDataPointer
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<BeginPaint> {

        private MemorySegment windowHandle;
        private MemorySegment paintDataPointer;

        private Builder() {

        }

        /**
         * Overrides the builder internal window handle value with the provided value.
         *
         * @param windowHandle The window handle. {@link BeginPaint#windowHandle}
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
         * @param paintDataPointer The paint data pointer. {@link BeginPaint#paintDataPointer}
         * @return The builder instance.
         */
        public Builder paintDataPointer(@NotNull final MemorySegment paintDataPointer) {

            Preconditions.checkNotNull(paintDataPointer);
            this.paintDataPointer = paintDataPointer;
            return this;

        }

        @NotNull
        @Override
        public BeginPaint build() {

            Preconditions.checkNotNull(this.windowHandle);
            Preconditions.checkNotNull(this.paintDataPointer);

            return new BeginPaint(this);

        }

    }

}
