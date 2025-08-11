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
 * Removes a hook procedure installed in a hook chain by the SetWindowsHookEx function.
 */
@NativeName(UnhookWindowsHookEx.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-unhookwindowshookex")
public final class UnhookWindowsHookEx extends NativeFunction {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "UnhookWindowsHookEx";
    public static final FunctionDescriptor UNHOOK_WINDOWS_HOOK_EX_SIGNATURE = FunctionDescriptor.of(
            BOOL,
            HHOOK.withName("hhk")
    );

    /**
     * A handle to the hook to be removed. This parameter is a hook handle obtained by a previous call to
     * SetWindowsHookEx.
     */
    @NativeName("hhk")
    private final MemorySegment hookHandle;

    private UnhookWindowsHookEx(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, UNHOOK_WINDOWS_HOOK_EX_SIGNATURE);

        this.hookHandle = builder.hookHandle;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.hookHandle
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<UnhookWindowsHookEx> {

        private MemorySegment hookHandle;

        private Builder() {

        }

        /**
         * Overrides the builder internal hook handle value with the provided value.
         *
         * @param hookHandle The hook handle. {@link UnhookWindowsHookEx#hookHandle}
         * @return The builder instance.
         */
        public Builder hookHandle(@NotNull final MemorySegment hookHandle) {

            this.hookHandle = hookHandle;
            return this;

        }

        @NotNull
        @Override
        public UnhookWindowsHookEx build() {

            Preconditions.checkNotNull(this.hookHandle);

            return new UnhookWindowsHookEx(this);

        }

    }

}
