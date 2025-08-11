package com.etdon.winj.function.user32;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.NativeDocumentation;
import com.etdon.jbinder.common.NativeName;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;

/**
 * The GetDC function retrieves a handle to a device context (DC) for the client area of a specified window or for the
 * entire screen. You can use the returned handle in subsequent GDI functions to draw in the DC. The device context is
 * an opaque data structure, whose values are used internally by GDI.
 * <p>
 * The GetDCEx function is an extension to GetDC, which gives an application more control over how and whether clipping
 * occurs in the client area.
 */
@NativeName(GetDC.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-getdc")
public final class GetDC extends NativeFunction {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "GetDC";
    public static final FunctionDescriptor GET_DC_SIGNATURE = FunctionDescriptor.of(
            HDC,
            HWND.withName("hWnd")
    );

    /**
     * A handle to the window whose DC is to be retrieved. If this value is NULL, GetDC retrieves the DC for the entire
     * screen.
     */
    @NativeName("hWnd")
    private MemorySegment windowHandle = MemorySegment.NULL;

    private GetDC() {

        super(LIBRARY, NATIVE_NAME, GET_DC_SIGNATURE);

    }

    private GetDC(@NotNull final MemorySegment windowHandle) {

        super(LIBRARY, NATIVE_NAME, GET_DC_SIGNATURE);

        this.windowHandle = windowHandle;

    }

    private GetDC(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, GET_DC_SIGNATURE);

        Conditional.executeIfNotNull(builder.windowHandle, () -> this.windowHandle = builder.windowHandle);

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.windowHandle
        );

    }

    public static GetDC empty() {

        return new GetDC();

    }

    public static GetDC ofWindowHandle(@NotNull final MemorySegment windowHandle) {

        Preconditions.checkNotNull(windowHandle);
        return new GetDC(windowHandle);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<GetDC> {

        private MemorySegment windowHandle;

        private Builder() {

        }

        /**
         * Overrides the builder internal window handle value with the provided value.
         *
         * @param windowHandle The window handle. {@link GetDC#windowHandle}
         * @return The builder instance.
         */
        public Builder windowHandle(@Nullable final MemorySegment windowHandle) {

            this.windowHandle = windowHandle;
            return this;

        }

        @NotNull
        @Override
        public GetDC build() {

            return new GetDC(this);

        }

    }

}
