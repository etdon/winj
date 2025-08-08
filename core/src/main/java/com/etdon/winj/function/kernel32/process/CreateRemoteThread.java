package com.etdon.winj.function.kernel32.process;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.NativeDocumentation;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;

@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/processthreadsapi/nf-processthreadsapi-createremotethread")
public final class CreateRemoteThread extends NativeFunction {

    public static final String LIBRARY = Library.KERNEL_32;
    public static final String NATIVE_NAME = "CreateRemoteThread";
    public static final FunctionDescriptor CREATE_REMOTE_THREAD_SIGNATURE = FunctionDescriptor.of(
            HANDLE,
            HANDLE.withName("hProcess"),
            LPSECURITY_ATTRIBUTES.withName("lpThreadAttributes"),
            SIZE_T.withName("dwStackSize"),
            LPTHREAD_START_ROUTINE.withName("lpStartAddress"),
            LPVOID.withName("lpParameter"),
            DWORD.withName("dwCreationFlags"),
            LPDWORD.withName("lpThreadId")
    );

    /**
     * A handle to the process in which the thread is to be created. The handle must have the PROCESS_CREATE_THREAD,
     * PROCESS_QUERY_INFORMATION, PROCESS_VM_OPERATION, PROCESS_VM_WRITE, and PROCESS_VM_READ access rights, and may
     * fail without these rights on certain platforms. For more information, see Process Security and Access Rights.
     */
    private final MemorySegment processHandle;

    /**
     * A pointer to a SECURITY_ATTRIBUTES structure that specifies a security descriptor for the new thread and
     * determines whether child processes can inherit the returned handle. If lpThreadAttributes is NULL, the thread
     * gets a default security descriptor and the handle cannot be inherited. The access control lists (ACL) in the
     * default security descriptor for a thread come from the primary token of the creator.
     *
     * @see com.etdon.winj.type.SecurityAttributes
     */
    private MemorySegment securityAttributesPointer = MemorySegment.NULL;

    /**
     * The initial size of the stack, in bytes. The system rounds this value to the nearest page. If this parameter is
     * 0 (zero), the new thread uses the default size for the executable. For more information, see Thread Stack Size.
     */
    private long stackSize = 0;

    /**
     * A pointer to the application-defined function of type LPTHREAD_START_ROUTINE to be executed by the thread and
     * represents the starting address of the thread in the remote process. The function must exist in the remote
     * process. For more information, see ThreadProc.
     */
    private final MemorySegment threadStartRoutinePointer;

    /**
     * A pointer to a variable to be passed to the thread function.
     */
    private final MemorySegment parameterPointer;

    /**
     * The flags that control the creation of the thread.
     *
     * @see com.etdon.winj.constant.ThreadCreationFlag
     */
    private int creationFlags = 0;

    /**
     * A pointer to a variable that receives the thread identifier.
     */
    private MemorySegment threadIdentifierOutputPointer = MemorySegment.NULL;

    private CreateRemoteThread(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, CREATE_REMOTE_THREAD_SIGNATURE);

        this.processHandle = builder.processHandle;
        Conditional.executeIfNotNull(builder.securityAttributesPointer, () -> this.securityAttributesPointer = builder.securityAttributesPointer);
        Conditional.executeIfNotNull(builder.stackSize, () -> this.stackSize = builder.stackSize);
        this.threadStartRoutinePointer = builder.threadStartRoutinePointer;
        this.parameterPointer = builder.parameterPointer;
        Conditional.executeIfNotNull(builder.creationFlags, () -> this.creationFlags = builder.creationFlags);
        Conditional.executeIfNotNull(builder.threadIdentifierOutputPointer, () -> this.threadIdentifierOutputPointer = builder.threadIdentifierOutputPointer);

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.processHandle,
                this.securityAttributesPointer,
                this.stackSize,
                this.threadStartRoutinePointer,
                this.parameterPointer,
                this.creationFlags,
                this.threadIdentifierOutputPointer
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<CreateRemoteThread> {

        private MemorySegment processHandle;
        private MemorySegment securityAttributesPointer;
        private Long stackSize;
        private MemorySegment threadStartRoutinePointer;
        private MemorySegment parameterPointer;
        private Integer creationFlags;
        private MemorySegment threadIdentifierOutputPointer;

        private Builder() {

        }

        public Builder processHandle(@NotNull final MemorySegment processHandle) {

            this.processHandle = processHandle;
            return this;

        }

        public Builder securityAttributesPointer(@NotNull final MemorySegment securityAttributesPointer) {

            this.securityAttributesPointer = securityAttributesPointer;
            return this;

        }

        public Builder stackSize(final long stackSize) {

            this.stackSize = stackSize;
            return this;

        }

        public Builder threadStartRoutinePointer(@NotNull final MemorySegment threadStartRoutinePointer) {

            this.threadStartRoutinePointer = threadStartRoutinePointer;
            return this;

        }

        public Builder parameterPointer(@NotNull final MemorySegment parameterPointer) {

            this.parameterPointer = parameterPointer;
            return this;

        }

        public Builder creationFlags(final int creationFlags) {

            this.creationFlags = creationFlags;
            return this;

        }

        public Builder threadIdentifierOutputPointer(@NotNull final MemorySegment threadIdentifierOutputPointer) {

            this.threadIdentifierOutputPointer = threadIdentifierOutputPointer;
            return this;

        }

        @NotNull
        @Override
        public CreateRemoteThread build() {

            Preconditions.checkNotNull(this.processHandle);
            Preconditions.checkNotNull(this.threadStartRoutinePointer);
            Preconditions.checkNotNull(this.parameterPointer);
            return new CreateRemoteThread(this);

        }

    }

}
