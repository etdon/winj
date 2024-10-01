package com.etdon.winj.function.gdi32;

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

public final class SetBkMode extends NativeFunction {

    public static final String LIBRARY = Library.GDI_32;
    public static final String NATIVE_NAME = "SetBkMode";
    public static final FunctionDescriptor SET_BK_MODE_SIGNATURE = FunctionDescriptor.of(
            INTEGER,
            HDC,
            INTEGER
    );

    /**
     * A handle to the device context.
     */
    private final MemorySegment deviceContextHandle;

    /**
     * The background mode.
     *
     * @see com.etdon.winj.constant.BackgroundMode
     */
    private final int mode;

    private SetBkMode(@NotNull final Builder builder) {

        super(LIBRARY, NATIVE_NAME, SET_BK_MODE_SIGNATURE);

        this.deviceContextHandle = builder.deviceContextHandle;
        this.mode = builder.mode;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.deviceContextHandle,
                this.mode
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<SetBkMode> {

        private MemorySegment deviceContextHandle;
        private Integer mode;

        private Builder() {

        }

        /**
         * Overrides the builder internal device context handle value with the provided value.
         *
         * @param deviceContextHandle The device context handle. {@link SetBkMode#deviceContextHandle}
         * @return The builder instance.
         */
        public Builder deviceContextHandle(@NotNull final MemorySegment deviceContextHandle) {

            this.deviceContextHandle = deviceContextHandle;
            return this;

        }

        /**
         * Overrides the builder internal mode value with the provided value.
         *
         * @param mode The mode. {@link SetBkMode#mode}
         * @return The builder instance.
         */
        public Builder mode(final int mode) {

            this.mode = mode;
            return this;

        }

        @NotNull
        @Override
        public SetBkMode build() {

            Preconditions.checkNotNull(this.deviceContextHandle);
            Preconditions.checkNotNull(this.mode);

            return new SetBkMode(this);

        }

    }

}
