package com.etdon.winj.function.user32;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;

public final class GetDC extends NativeFunction {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "GetDC";
    public static final FunctionDescriptor GET_DC_SIGNATURE = FunctionDescriptor.of(
            HDC,
            HWND
    );

    /**
     * A handle to the window whose DC is to be retrieved. If this value is NULL, GetDC retrieves the DC for the entire
     * screen.
     */
    private MemorySegment windowHandle = MemorySegment.NULL;

    private GetDC() {

        super(LIBRARY, NATIVE_NAME, GET_DC_SIGNATURE);

    }

    private GetDC(@NotNull final Builder builder) {

        super(LIBRARY, NATIVE_NAME, GET_DC_SIGNATURE);

        Conditional.executeIfNotNull(builder.windowHandle, () -> this.windowHandle = builder.windowHandle);

    }

    private GetDC(@NotNull final MemorySegment windowHandle) {

        super(LIBRARY, NATIVE_NAME, GET_DC_SIGNATURE);

        this.windowHandle = windowHandle;

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
