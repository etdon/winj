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
 * The ReleaseDC function releases a device context (DC), freeing it for use by other applications. The effect of the
 * ReleaseDC function depends on the type of DC. It frees only common and window DCs. It has no effect on class or
 * private DCs.
 */
@NativeName(ReleaseDC.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-releasedc")
public final class ReleaseDC extends NativeFunction {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "ReleaseDC";
    public static final FunctionDescriptor RELEASE_DC_SIGNATURE = FunctionDescriptor.of(
            INTEGER,
            HWND.withName("hWnd"),
            HDC.withName("hDC")
    );

    /**
     * A handle to the window whose DC is to be released.
     */
    @NativeName("hWnd")
    private final MemorySegment windowHandle;

    /**
     * A handle to the DC to be released.
     */
    @NativeName("hDC")
    private final MemorySegment deviceContextHandle;

    private ReleaseDC(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, RELEASE_DC_SIGNATURE);

        this.windowHandle = builder.windowHandle;
        this.deviceContextHandle = builder.deviceContextHandle;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.windowHandle,
                this.deviceContextHandle
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<ReleaseDC> {

        private MemorySegment windowHandle;
        private MemorySegment deviceContextHandle;

        private Builder() {

        }

        /**
         * Overrides the builder internal window handle value with the provided value.
         *
         * @param windowHandle The window handle. {@link ReleaseDC#windowHandle}
         * @return The builder instance.
         */
        public Builder windowHandle(@NotNull final MemorySegment windowHandle) {

            this.windowHandle = windowHandle;
            return this;

        }

        /**
         * Overrides the builder internal device context handle value with the provided value.
         *
         * @param deviceContextHandle The device context handle. {@link ReleaseDC#deviceContextHandle}
         * @return The builder instance.
         */
        public Builder deviceContextHandle(@NotNull final MemorySegment deviceContextHandle) {

            this.deviceContextHandle = deviceContextHandle;
            return this;

        }

        @NotNull
        @Override
        public ReleaseDC build() {

            Preconditions.checkNotNull(this.windowHandle);
            Preconditions.checkNotNull(this.deviceContextHandle);

            return new ReleaseDC(this);

        }

    }

}
