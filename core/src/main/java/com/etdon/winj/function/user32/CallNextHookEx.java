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
 * Passes the hook information to the next hook procedure in the current hook chain. A hook procedure can call this
 * function either before or after processing the hook information.
 */
@NativeName(CallNextHookEx.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-callnexthookex")
public final class CallNextHookEx extends NativeFunction {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "CallNextHookEx";
    public static final FunctionDescriptor CALL_NEXT_HOOK_EX_SIGNATURE = FunctionDescriptor.of(
            LRESULT,
            HHOOK.withName("hhk"),
            INTEGER.withName("nCode"),
            WPARAM.withName("wParam"),
            LPARAM.withName("lParam")
    );

    /**
     * This parameter is ignored.
     */
    @NativeName("hhk")
    private MemorySegment hookHandle = MemorySegment.NULL;

    /**
     * The hook code passed to the current hook procedure. The next hook procedure uses this code to determine how to
     * process the hook information.
     */
    @NativeName("nCode")
    private final int code;

    /**
     * The wParam value passed to the current hook procedure. The meaning of this parameter depends on the type of hook
     * associated with the current hook chain.
     */
    @NativeName("wParam")
    private final long firstParameter;

    /**
     * The lParam value passed to the current hook procedure. The meaning of this parameter depends on the type of hook
     * associated with the current hook chain.
     */
    @NativeName("lParam")
    private final long secondParameter;

    private CallNextHookEx(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, CALL_NEXT_HOOK_EX_SIGNATURE);

        Conditional.executeIfNotNull(builder.hookHandle, () -> this.hookHandle = builder.hookHandle);
        this.code = builder.code;
        this.firstParameter = builder.firstParameter;
        this.secondParameter = builder.secondParameter;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.hookHandle,
                this.code,
                this.firstParameter,
                this.secondParameter
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<CallNextHookEx> {

        private MemorySegment hookHandle;
        private Integer code;
        private Long firstParameter;
        private Long secondParameter;

        private Builder() {

        }

        /**
         * Overrides the builder internal hook handle value with the provided value.
         *
         * @param hookHandle The hook handle. {@link CallNextHookEx#hookHandle}
         * @return The builder instance.
         */
        public Builder hookHandle(@Nullable final MemorySegment hookHandle) {

            this.hookHandle = hookHandle;
            return this;

        }

        /**
         * Overrides the builder internal code value with the provided value.
         *
         * @param code The code. {@link CallNextHookEx#code}
         * @return The builder instance.
         */
        public Builder code(final int code) {

            this.code = code;
            return this;

        }

        /**
         * Overrides the builder internal first parameter value with the provided value.
         *
         * @param firstParameter The first parameter. {@link CallNextHookEx#firstParameter}
         * @return The builder instance.
         */
        public Builder firstParameter(final long firstParameter) {

            this.firstParameter = firstParameter;
            return this;

        }

        /**
         * Overrides the builder internal second parameter value with the provided value.
         *
         * @param secondParameter The second parameter. {@link CallNextHookEx#secondParameter}
         * @return The builder instance.
         */
        public Builder secondParameter(final long secondParameter) {

            this.secondParameter = secondParameter;
            return this;

        }

        @NotNull
        @Override
        public CallNextHookEx build() {

            Preconditions.checkNotNull(this.code);
            Preconditions.checkNotNull(this.firstParameter);
            Preconditions.checkNotNull(this.secondParameter);

            return new CallNextHookEx(this);

        }

    }

}
