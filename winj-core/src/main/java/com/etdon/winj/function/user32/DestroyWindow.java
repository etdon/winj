package com.etdon.winj.function.user32;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.NativeDataType.*;

public final class DestroyWindow extends NativeFunction {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "DestroyWindow";
    public static final FunctionDescriptor DESTROY_WINDOW_SIGNATURE = FunctionDescriptor.of(
            BOOL,
            HWND
    );

    /**
     * A handle to the window to be destroyed.
     */
    private final MemorySegment windowHandle;

    private DestroyWindow(@NotNull final Builder builder) {

        super(LIBRARY, NATIVE_NAME, DESTROY_WINDOW_SIGNATURE);

        this.windowHandle = builder.windowHandle;

    }

    private DestroyWindow(@NotNull final MemorySegment memorySegment) {

        super(LIBRARY, NATIVE_NAME, DESTROY_WINDOW_SIGNATURE);

        this.windowHandle = memorySegment;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.windowHandle
        );

    }

    public static DestroyWindow ofWindowHandle(@NotNull final MemorySegment windowHandle) {

        Preconditions.checkNotNull(windowHandle);
        return new DestroyWindow(windowHandle);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<DestroyWindow> {

        private MemorySegment windowHandle;

        private Builder() {

        }

        /**
         * Overrides the builder internal window handle value with the provided value.
         *
         * @param windowHandle The window handle. {@link DestroyWindow#windowHandle}
         * @return The builder instance.
         */
        public Builder windowHandle(@NotNull final MemorySegment windowHandle) {

            this.windowHandle = windowHandle;
            return this;

        }

        @NotNull
        @Override
        public DestroyWindow build() {

            Preconditions.checkNotNull(this.windowHandle);

            return new DestroyWindow(this);

        }

    }

}
