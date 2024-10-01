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

public final class DefWindowProcW extends NativeFunction {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "DefWindowProcW";
    public static final FunctionDescriptor DEF_WINDOW_PROC_W_SIGNATURE = FunctionDescriptor.of(
            LRESULT,
            HWND,
            UINT,
            WPARAM,
            LPARAM
    );

    /**
     * A handle to the window procedure that received the message.
     */
    private final MemorySegment windowHandle;

    /**
     * The message.
     */
    private final int message;

    /**
     * Additional message information. The content of this parameter depends on the value of the Msg parameter.
     */
    private final long firstParameter;

    /**
     * Additional message information. The content of this parameter depends on the value of the Msg parameter.
     */
    private final long secondParameter;

    private DefWindowProcW(@NotNull final Builder builder) {

        super(LIBRARY, NATIVE_NAME, DEF_WINDOW_PROC_W_SIGNATURE);

        this.windowHandle = builder.windowHandle;
        this.message = builder.message;
        this.firstParameter = builder.firstParameter;
        this.secondParameter = builder.secondParameter;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.windowHandle,
                this.message,
                this.firstParameter,
                this.secondParameter
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<DefWindowProcW> {

        private MemorySegment windowHandle;
        private Integer message;
        private Long firstParameter;
        private Long secondParameter;

        private Builder() {

        }

        /**
         * Overrides the builder internal window handle value with the provided value.
         *
         * @param windowHandle The window handle. {@link DefWindowProcW#windowHandle}
         * @return The builder instance.
         */
        public Builder windowHandle(@NotNull final MemorySegment windowHandle) {

            this.windowHandle = windowHandle;
            return this;

        }

        /**
         * Overrides the builder internal message value with the provided value.
         *
         * @param message The message. {@link DefWindowProcW#message}
         * @return The builder instance.
         */
        public Builder message(final int message) {

            this.message = message;
            return this;

        }

        /**
         * Overrides the builder internal first parameter value with the provided value.
         *
         * @param firstParameter The first parameter. {@link DefWindowProcW#firstParameter}
         * @return The builder instance.
         */
        public Builder firstParameter(final long firstParameter) {

            this.firstParameter = firstParameter;
            return this;

        }

        /**
         * Overrides the builder internal second parameter value with the provided value.
         *
         * @param secondParameter The second parameter. {@link DefWindowProcW#secondParameter}
         * @return The builder instance.
         */
        public Builder secondParameter(final long secondParameter) {

            this.secondParameter = secondParameter;
            return this;

        }

        @NotNull
        @Override
        public DefWindowProcW build() {

            Preconditions.checkNotNull(this.windowHandle);
            Preconditions.checkNotNull(this.message);
            Preconditions.checkNotNull(this.firstParameter);
            Preconditions.checkNotNull(this.secondParameter);

            return new DefWindowProcW(this);

        }

    }

}
