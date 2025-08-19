package com.etdon.winj.function.kernel32.process;

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
 * Opens an existing local process object.
 */
@NativeName(OpenProcess.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/processthreadsapi/nf-processthreadsapi-openprocess")
public final class OpenProcess extends NativeFunction<MemorySegment> {

    public static final String LIBRARY = Library.KERNEL_32;
    public static final String NATIVE_NAME = "OpenProcess";
    public static final FunctionDescriptor OPEN_PROCESS_SIGNATURE = FunctionDescriptor.of(
            HANDLE,
            DWORD.withName("dwDesiredAccess"),
            BOOL.withName("bInheritHandle"),
            DWORD.withName("dwProcessId")
    );

    /**
     * The access to the process object. This access right is checked against the security descriptor for the process.
     * This parameter can be one or more of the process access rights.
     *
     * @see com.etdon.winj.constant.process.ProcessAccessRight
     */
    @NativeName("dwDesiredAccess")
    private final int access;

    /**
     * If this value is TRUE, processes created by this process will inherit the handle. Otherwise, the processes do
     * not inherit this handle.
     */
    @NativeName("bInheritHandle")
    private final boolean inheritHandle;

    /**
     * The identifier of the local process to be opened.
     */
    @NativeName("dwProcessId")
    private final int processId;

    private OpenProcess(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, OPEN_PROCESS_SIGNATURE);

        this.access = builder.access;
        this.inheritHandle = builder.inheritHandle;
        this.processId = builder.processId;

    }

    @Override
    public MemorySegment call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return (MemorySegment) super.obtainHandle(linker, symbolLookup).invoke(this.access, this.inheritHandle, this.processId);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<OpenProcess> {

        private Integer access;
        private Boolean inheritHandle;
        private Integer processId;

        private Builder() {

        }

        public Builder access(final int access) {

            this.access = access;
            return this;

        }

        public Builder inheritHandle(final boolean inheritHandle) {

            this.inheritHandle = inheritHandle;
            return this;

        }

        public Builder processId(final int processId) {

            this.processId = processId;
            return this;

        }

        @NotNull
        @Override
        public OpenProcess build() {

            Preconditions.checkNotNull(this.access);
            Preconditions.checkNotNull(this.inheritHandle);
            Preconditions.checkNotNull(this.processId);

            return new OpenProcess(this);

        }

    }

}
