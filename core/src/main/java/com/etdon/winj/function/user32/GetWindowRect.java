package com.etdon.winj.function.user32;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.NativeDocumentation;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;

@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-getwindowrect")
public final class GetWindowRect extends NativeFunction {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "GetWindowRect";
    public static final FunctionDescriptor GET_WINDOW_RECT_SIGNATURE = FunctionDescriptor.of(
            BOOL,
            HWND.withName("hWnd"),
            LPRECT.withName("lpRect")
    );

    /**
     * A handle to the window.
     */
    private final MemorySegment windowHandle;

    /**
     * A pointer to a RECT structure that receives the screen coordinates of the upper-left and lower-right corners of
     * the window.
     *
     * @see com.etdon.winj.type.Rectangle
     */
    private final MemorySegment rectanglePointer;

    private GetWindowRect(@NotNull final Builder builder) {

        super(LIBRARY, NATIVE_NAME, GET_WINDOW_RECT_SIGNATURE);

        this.windowHandle = builder.windowHandle;
        this.rectanglePointer = builder.rectanglePointer;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.windowHandle,
                this.rectanglePointer
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<GetWindowRect> {

        private MemorySegment windowHandle;
        private MemorySegment rectanglePointer;

        private Builder() {

        }

        /**
         * Overrides the builder internal window handle value with the provided value.
         *
         * @param windowHandle The window handle. {@link GetWindowRect#windowHandle}
         * @return The builder instance.
         */
        public Builder windowHandle(@NotNull final MemorySegment windowHandle) {

            Preconditions.checkNotNull(windowHandle);
            this.windowHandle = windowHandle;
            return this;

        }

        /**
         * Overrides the builder internal rectangle pointer value with the provided value.
         *
         * @param rectanglePointer The rectangle pointer. {@link GetWindowRect#rectanglePointer}
         * @return The builder instance.
         */
        public Builder rectanglePointer(@NotNull final MemorySegment rectanglePointer) {

            Preconditions.checkNotNull(rectanglePointer);
            this.rectanglePointer = rectanglePointer;
            return this;

        }

        @NotNull
        @Override
        public GetWindowRect build() {

            Preconditions.checkNotNull(this.windowHandle);
            Preconditions.checkNotNull(this.rectanglePointer);

            return new GetWindowRect(this);

        }

    }

}
