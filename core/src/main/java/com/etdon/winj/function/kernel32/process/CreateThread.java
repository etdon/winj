package com.etdon.winj.function.kernel32.process;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
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
 * Creates a thread to execute within the virtual address space of the calling process.
 * <p>
 * To create a thread that runs in the virtual address space of another process, use the CreateRemoteThread function.
 */
@NativeName(CreateThread.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/processthreadsapi/nf-processthreadsapi-createthread")
public final class CreateThread extends NativeFunction<MemorySegment> {

    public static final String LIBRARY = Library.KERNEL_32;
    public static final String NATIVE_NAME = "CreateThread";
    public static final FunctionDescriptor CREATE_THREAD_SIGNATURE = FunctionDescriptor.of(
            HANDLE,
            LPSECURITY_ATTRIBUTES.withName("lpThreadAttributes"),
            SIZE_T.withName("dwStackSize"),
            LPTHREAD_START_ROUTINE.withName("lpStartAddress"),
            LPVOID.withName("lpParameter"),
            DWORD.withName("dwCreationFlags"),
            LPDWORD.withName("lpThreadId")
    );

    /**
     * A pointer to a SECURITY_ATTRIBUTES structure that specifies a security descriptor for the new thread and
     * determines whether child processes can inherit the returned handle. If lpThreadAttributes is NULL, the thread
     * gets a default security descriptor and the handle cannot be inherited. The access control lists (ACL) in the
     * default security descriptor for a thread come from the primary token of the creator.
     *
     * @see com.etdon.winj.type.SecurityAttributes
     */
    @NativeName("lpThreadAttributes")
    private MemorySegment securityAttributesPointer = MemorySegment.NULL;

    /**
     * The initial size of the stack, in bytes. The system rounds this value to the nearest page. If this parameter is
     * 0 (zero), the new thread uses the default size for the executable. For more information, see Thread Stack Size.
     */
    @NativeName("dwStackSize")
    private long stackSize = 0;

    /**
     * A pointer to the application-defined function of type LPTHREAD_START_ROUTINE to be executed by the thread and
     * represents the starting address of the thread in the remote process. The function must exist in the remote
     * process. For more information, see ThreadProc.
     */
    @NativeName("lpStartAddress")
    private final MemorySegment threadStartRoutinePointer;

    /**
     * A pointer to a variable to be passed to the thread function.
     */
    @NativeName("lpParameter")
    private MemorySegment parameterPointer = MemorySegment.NULL;

    /**
     * The flags that control the creation of the thread.
     *
     * @see com.etdon.winj.constant.ThreadCreationFlag
     */
    @NativeName("dwCreationFlags")
    private int creationFlags = 0;

    /**
     * A pointer to a variable that receives the thread identifier.
     */
    @NativeName("lpThreadId")
    private MemorySegment threadIdentifierOutputPointer = MemorySegment.NULL;

    private CreateThread(final CreateThread.Builder builder) {

        super(LIBRARY, NATIVE_NAME, CREATE_THREAD_SIGNATURE);

        Conditional.executeIfNotNull(builder.securityAttributesPointer, () -> this.securityAttributesPointer = builder.securityAttributesPointer);
        Conditional.executeIfNotNull(builder.stackSize, () -> this.stackSize = builder.stackSize);
        this.threadStartRoutinePointer = builder.threadStartRoutinePointer;
        Conditional.executeIfNotNull(builder.parameterPointer, () -> this.parameterPointer = builder.parameterPointer);
        Conditional.executeIfNotNull(builder.creationFlags, () -> this.creationFlags = builder.creationFlags);
        Conditional.executeIfNotNull(builder.threadIdentifierOutputPointer, () -> this.threadIdentifierOutputPointer = builder.threadIdentifierOutputPointer);

    }

    @Override
    public MemorySegment call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return (MemorySegment) super.obtainHandle(linker, symbolLookup).invoke(
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

    public static final class Builder implements FluentBuilder<CreateThread> {

        private MemorySegment securityAttributesPointer;
        private Long stackSize;
        private MemorySegment threadStartRoutinePointer;
        private MemorySegment parameterPointer;
        private Integer creationFlags;
        private MemorySegment threadIdentifierOutputPointer;

        private Builder() {

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
        public CreateThread build() {

            Preconditions.checkNotNull(this.threadStartRoutinePointer);
            return new CreateThread(this);

        }

    }

}
