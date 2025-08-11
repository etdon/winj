package com.etdon.winj.function.advapi32;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.NativeDocumentation;
import com.etdon.jbinder.common.NativeName;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.ShutdownFlag;
import com.etdon.winj.constant.ShutdownReason;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.DWORD;
import static java.lang.foreign.ValueLayout.*;

@NativeName(InitiateShutdownW.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/winreg/nf-winreg-initiateshutdownw")
public final class InitiateShutdownW extends NativeFunction {

    public static final String LIBRARY = Library.ADVAPI_32;
    public static final String NATIVE_NAME = "InitiateShutdownW";
    public static final FunctionDescriptor INITIATE_SHUTDOWN_W_SIGNATURE = FunctionDescriptor.of(
            DWORD,
            ADDRESS.withName("lpMachineName"),
            ADDRESS.withName("lpMessage"),
            DWORD.withName("dwGracePeriod"),
            DWORD.withName("dwShutdownFlags"),
            DWORD.withName("dwReason")
    );

    /**
     * The name of the computer to be shut down. If the value of this parameter is NULL, the local computer is shut
     * down.
     */
    @NativeName("lpMachineName")
    private MemorySegment machineName = MemorySegment.NULL;

    /**
     * The message to be displayed in the interactive shutdown dialog box.
     */
    @NativeName("lpMessage")
    private MemorySegment message = MemorySegment.NULL;

    /**
     * The number of seconds to wait before shutting down the computer. If the value of this parameter is zero, the
     * computer is shut down immediately. This value is limited to MAX_SHUTDOWN_TIMEOUT.
     * <p>
     * If the value of this parameter is greater than zero, and the dwShutdownFlags parameter specifies the flag
     * SHUTDOWN_GRACE_OVERRIDE, the function fails and returns the error code ERROR_BAD_ARGUMENTS.
     */
    @NativeName("dwGracePeriod")
    private final int gracePeriod;

    /**
     * One or more bit flags that specify options for the shutdown.
     *
     * @see ShutdownFlag
     */
    @NativeName("dwShutdownFlags")
    private final int shutdownFlags;

    /**
     * The reason for initiating the shutdown. This parameter must be one of the system shutdown reason codes. If this
     * parameter is zero, the default is an undefined shutdown that is logged as "No title for this reason could be
     * found". By default, it is also an unplanned shutdown. Depending on how the system is configured, an unplanned
     * shutdown triggers the creation of a file that contains the system state information, which can delay shutdown.
     * Therefore, do not use zero for this parameter.
     *
     * @see ShutdownReason
     */
    @NativeName("dwReason")
    private final int reason;

    private InitiateShutdownW(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, INITIATE_SHUTDOWN_W_SIGNATURE);

        Conditional.executeIfNotNull(builder.machineName, () -> this.machineName = builder.machineName);
        Conditional.executeIfNotNull(builder.message, () -> this.message = builder.message);
        this.gracePeriod = builder.gracePeriod;
        this.shutdownFlags = builder.shutdownFlags;
        this.reason = builder.reason;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.machineName,
                this.message,
                this.gracePeriod,
                this.shutdownFlags,
                this.reason
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<InitiateShutdownW> {

        private MemorySegment machineName;
        private MemorySegment message;
        private Integer gracePeriod;
        private Integer shutdownFlags;
        private Integer reason;

        private Builder() {

        }

        public Builder machineName(@NotNull final MemorySegment machineName) {

            this.machineName = machineName;
            return this;

        }

        public Builder message(@NotNull final MemorySegment message) {

            this.message = message;
            return this;

        }

        public Builder gracePeriod(final int gracePeriod) {

            this.gracePeriod = gracePeriod;
            return this;

        }

        public Builder shutdownFlags(final int shutdownFlags) {

            this.shutdownFlags = shutdownFlags;
            return this;

        }

        public Builder reason(final int reason) {

            this.reason = reason;
            return this;

        }

        @NotNull
        @Override
        public InitiateShutdownW build() {

            Preconditions.checkNotNull(this.gracePeriod);
            Preconditions.checkNotNull(this.shutdownFlags);
            Preconditions.checkNotNull(this.reason);

            return new InitiateShutdownW(this);

        }

    }

}
