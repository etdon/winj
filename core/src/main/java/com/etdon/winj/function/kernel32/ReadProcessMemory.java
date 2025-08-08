package com.etdon.winj.function.kernel32;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.type.constant.NativeDataType;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static java.lang.foreign.ValueLayout.ADDRESS;

public final class ReadProcessMemory extends NativeFunction {

    public static final String LIBRARY = Library.KERNEL_32;
    public static final String NATIVE_NAME = "ReadProcessMemory";
    public static final FunctionDescriptor READ_PROCESS_MEMORY_SIGNATURE = FunctionDescriptor.of(
            NativeDataType.BOOL,
            NativeDataType.HANDLE.withName("hProcess"),
            NativeDataType.LPCVOID.withName("lpBaseAddress"),
            NativeDataType.LPVOID.withName("lpBuffer"),
            NativeDataType.SIZE_T.withName("nSize"),
            ADDRESS.withName("lpNumberOfBytesRead")
    );

    /**
     * A handle to the process with memory that is being read. The handle must have PROCESS_VM_READ access to the
     * process.
     */
    private final MemorySegment processHandle;

    /**
     * A pointer to the base address in the specified process from which to read. Before any data transfer occurs, the
     * system verifies that all data in the base address and memory of the specified size is accessible for read
     * access, and if it is not accessible the function fails.
     */
    private final MemorySegment baseAddress;

    /**
     * A pointer to a buffer that receives the contents from the address space of the specified process.
     */
    private final MemorySegment buffer;

    /**
     * The number of bytes to be read from the specified process.
     */
    private final long count;

    /**
     * A pointer to a variable that receives the number of bytes transferred into the specified buffer. If
     * lpNumberOfBytesRead is NULL, the parameter is ignored.
     */
    private MemorySegment bytesReadPointer = MemorySegment.NULL;

    private ReadProcessMemory(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, READ_PROCESS_MEMORY_SIGNATURE);

        this.processHandle = builder.processHandle;
        this.baseAddress = builder.baseAddress;
        this.buffer = builder.buffer;
        this.count = builder.count;
        Conditional.executeIfNotNull(builder.bytesReadPointer, () -> this.bytesReadPointer = builder.bytesReadPointer);

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.processHandle,
                this.baseAddress,
                this.buffer,
                this.count,
                this.bytesReadPointer
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<ReadProcessMemory> {

        private MemorySegment processHandle;
        private MemorySegment baseAddress;
        private MemorySegment buffer;
        private Long count;
        private MemorySegment bytesReadPointer;

        private Builder() {

        }

        public Builder processHandle(@NotNull final MemorySegment processHandle) {

            this.processHandle = processHandle;
            return this;

        }

        public Builder baseAddress(@NotNull final MemorySegment baseAddress) {

            this.baseAddress = baseAddress;
            return this;

        }

        public Builder buffer(@NotNull final MemorySegment buffer) {

            this.buffer = buffer;
            return this;

        }

        public Builder count(final long count) {

            this.count = count;
            return this;

        }

        public Builder bytesReadPointer(@NotNull final MemorySegment bytesReadPointer) {

            this.bytesReadPointer = bytesReadPointer;
            return this;

        }

        @NotNull
        @Override
        public ReadProcessMemory build() {

            Preconditions.checkNotNull(this.processHandle);
            Preconditions.checkNotNull(this.baseAddress);
            Preconditions.checkNotNull(this.buffer);
            Preconditions.checkNotNull(this.count);
            return new ReadProcessMemory(this);

        }

    }

}
