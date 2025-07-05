package com.etdon.winj.function.ntdll;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static java.lang.foreign.ValueLayout.*;

public final class NtAlertThread extends NativeFunction {

    public static final String LIBRARY = Library.NTDLL;
    public static final String NATIVE_NAME = "NtAlertThread";
    public static final FunctionDescriptor NT_ALERT_THREAD_SIGNATURE = FunctionDescriptor.ofVoid(
            ADDRESS.withName("ThreadHandle")
    );

    /**
     * Handle to opened Thread Object.
     */
    private final MemorySegment threadHandle;

    private NtAlertThread(@NotNull final Builder builder) {

        super(LIBRARY, NATIVE_NAME, NT_ALERT_THREAD_SIGNATURE);

        this.threadHandle = builder.threadHandle;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(this.threadHandle);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<NtAlertThread> {

        private MemorySegment threadHandle;

        private Builder() {

        }

        /**
         * Overrides the builder internal thread handle value with the provided value.
         *
         * @param threadHandle The thread handle. {@link NtAlertThread#threadHandle}
         * @return The builder instance.
         */
        public Builder threadHandle(@NotNull final MemorySegment threadHandle) {

            this.threadHandle = threadHandle;
            return this;

        }

        @NotNull
        @Override
        public NtAlertThread build() {

            Preconditions.checkNotNull(this.threadHandle);

            return new NtAlertThread(this);

        }

    }

}
