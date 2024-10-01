package com.etdon.winj.function.ntdll;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.ShutdownAction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.SymbolLookup;

import static java.lang.foreign.ValueLayout.JAVA_INT;

public final class NtShutdownSystem extends NativeFunction {

    public static final String LIBRARY = Library.NTDLL;
    public static final String NATIVE_NAME = "NtShutdownSystem";
    public static final FunctionDescriptor NT_SHUTDOWN_SYSTEM_SIGNATURE = FunctionDescriptor.ofVoid(
            JAVA_INT.withName("Action")
    );

    /**
     * Type of shutdown defined in SHUTDOWN_ACTION enumeration type.
     *
     * @see ShutdownAction
     */
    private final int action;

    private NtShutdownSystem(@NotNull final Builder builder) {

        super(LIBRARY, NATIVE_NAME, NT_SHUTDOWN_SYSTEM_SIGNATURE);

        this.action = builder.action;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(this.action);

    }

    public static Builder builder() {

        return new NtShutdownSystem.Builder();

    }

    public static final class Builder implements FluentBuilder<NtShutdownSystem> {

        private Integer action;

        private Builder() {

        }

        /**
         * Overrides the builder internal action value with the provided value.
         *
         * @param action The action. {@link NtShutdownSystem#action}
         * @return The builder instance.
         */
        public Builder action(final int action) {

            this.action = action;
            return this;

        }

        @NotNull
        @Override
        public NtShutdownSystem build() {

            Preconditions.checkNotNull(this.action);

            return new NtShutdownSystem(this);

        }

    }

}
