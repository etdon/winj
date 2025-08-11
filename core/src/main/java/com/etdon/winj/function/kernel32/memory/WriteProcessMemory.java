package com.etdon.winj.function.kernel32.memory;

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
import static java.lang.foreign.ValueLayout.ADDRESS;

/**
 * Writes data to an area of memory in a specified process. The entire area to be written to must be accessible or the
 * operation fails.
 */
@NativeName(WriteProcessMemory.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/memoryapi/nf-memoryapi-writeprocessmemory")
public final class WriteProcessMemory extends NativeFunction {

    public static final String LIBRARY = Library.KERNEL_32;
    public static final String NATIVE_NAME = "WriteProcessMemory";
    public static final FunctionDescriptor WRITE_PROCESS_MEMORY_SIGNATURE = FunctionDescriptor.of(
            BOOL,
            HANDLE.withName("hProcess"),
            LPVOID.withName("lpBaseAddress"),
            LPCVOID.withName("lpBuffer"),
            SIZE_T.withName("nSize"),
            ADDRESS.withName("lpNumberOfBytesWritten")
    );

    /**
     * A handle to the process memory to be modified. The handle must have PROCESS_VM_WRITE and PROCESS_VM_OPERATION
     * access to the process.
     */
    @NativeName("hProcess")
    private final MemorySegment processHandle;

    /**
     * A pointer to the base address in the specified process to which data is written. Before data transfer occurs,
     * the system verifies that all data in the base address and memory of the specified size is accessible for write
     * access, and if it is not accessible, the function fails.
     */
    @NativeName("lpBaseAddress")
    private final MemorySegment baseAddressPointer;

    /**
     * A pointer to the buffer that contains data to be written in the address space of the specified process.
     */
    @NativeName("lpBuffer")
    private final MemorySegment bufferPointer;

    /**
     * The number of bytes to be written to the specified process.
     */
    @NativeName("nSize")
    private final long size;

    /**
     * A pointer to a variable that receives the number of bytes transferred into the specified process. This parameter
     * is optional. If lpNumberOfBytesWritten is NULL, the parameter is ignored.
     */
    @NativeName("lpNumberOfBytesWritten")
    private MemorySegment bytesWrittenOutputPointer = MemorySegment.NULL;

    private WriteProcessMemory(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, WRITE_PROCESS_MEMORY_SIGNATURE);

        this.processHandle = builder.processHandle;
        this.baseAddressPointer = builder.baseAddressPointer;
        this.bufferPointer = builder.bufferPointer;
        this.size = builder.size;
        Conditional.executeIfNotNull(builder.bytesWrittenOutputPointer, () -> this.bytesWrittenOutputPointer = builder.bytesWrittenOutputPointer);

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.processHandle,
                this.baseAddressPointer,
                this.bufferPointer,
                this.size,
                this.bytesWrittenOutputPointer
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<WriteProcessMemory> {

        private MemorySegment processHandle;
        private MemorySegment baseAddressPointer;
        private MemorySegment bufferPointer;
        private Long size;
        private MemorySegment bytesWrittenOutputPointer;

        private Builder() {

        }

        public Builder processHandle(@NotNull final MemorySegment processHandle) {

            this.processHandle = processHandle;
            return this;

        }

        public Builder baseAddressPointer(@NotNull final MemorySegment baseAddressPointer) {

            this.baseAddressPointer = baseAddressPointer;
            return this;

        }

        public Builder bufferPointer(@NotNull final MemorySegment bufferPointer) {

            this.bufferPointer = bufferPointer;
            return this;

        }

        public Builder size(final long size) {

            this.size = size;
            return this;

        }

        public Builder bytesWrittenOutputPointer(@NotNull final MemorySegment bytesWrittenOutputPointer) {

            this.bytesWrittenOutputPointer = bytesWrittenOutputPointer;
            return this;

        }

        @NotNull
        @Override
        public WriteProcessMemory build() {

            Preconditions.checkNotNull(this.processHandle);
            Preconditions.checkNotNull(this.baseAddressPointer);
            Preconditions.checkNotNull(this.bufferPointer);
            Preconditions.checkNotNull(this.size);
            return new WriteProcessMemory(this);

        }

    }

}
