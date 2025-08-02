package com.etdon.winj.function.kernel32;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.NativeDataType.*;

public final class VirtualAllocEx extends NativeFunction {

    public static final String LIBRARY = Library.KERNEL_32;
    public static final String NATIVE_NAME = "VirtualAllocEx";
    public static final FunctionDescriptor VIRTUAL_ALLOC_EX_SIGNATURE = FunctionDescriptor.of(
            LPVOID,
            HANDLE.withName("hProcess"),
            LPVOID.withName("lpAddress"),
            SIZE_T.withName("dwSize"),
            DWORD.withName("flAllocationType"),
            DWORD.withName("flProtect")
    );

    /**
     * The handle to a process. The function allocates memory within the virtual address space of this process.
     * <p>
     * The handle must have the PROCESS_VM_OPERATION access right.
     */
    private final MemorySegment processHandle;

    /**
     * The pointer that specifies a desired starting address for the region of pages that you want to allocate.
     * <p>
     * If you are reserving memory, the function rounds this address down to the nearest multiple of the allocation
     * granularity.
     * <p>
     * If you are committing memory that is already reserved, the function rounds this address down to the nearest page
     * boundary. To determine the size of a page and the allocation granularity on the host computer, use the
     * GetSystemInfo function.
     * <p>
     * If lpAddress is NULL, the function determines where to allocate the region.
     * <p>
     * If this address is within an enclave that you have not initialized by calling InitializeEnclave, VirtualAllocEx
     * allocates a page of zeros for the enclave at that address. The page must be previously uncommitted, and will not
     * be measured with the EEXTEND instruction of the Intel Software Guard Extensions programming model.
     * <p>
     * If the address in within an enclave that you initialized, then the allocation operation fails with the
     * ERROR_INVALID_ADDRESS error. That is true for enclaves that do not support dynamic memory management
     * (i.e. SGX1). SGX2 enclaves will permit allocation, and the page must be accepted by the enclave after it has
     * been allocated.
     */
    private MemorySegment addressPointer = MemorySegment.NULL;

    /**
     * The size of the region of memory to allocate, in bytes.
     * <p>
     * If lpAddress is NULL, the function rounds dwSize up to the next page boundary.
     * <p>
     * If lpAddress is not NULL, the function allocates all pages that contain one or more bytes in the range from
     * lpAddress to lpAddress+dwSize. This means, for example, that a 2-byte range that straddles a page boundary
     * causes the function to allocate both pages.
     */
    private final long size;

    /**
     * The type of memory allocation.
     *
     * @see com.etdon.winj.constant.memory.AllocationType
     */
    private final int allocationType;

    /**
     * The memory protection for the region of pages to be allocated. If the pages are being committed, you can specify
     * any one of the memory protection constants.
     * <p>
     * If lpAddress specifies an address within an enclave, flProtect cannot be any of the following values:
     * <p>
     * PAGE_NOACCESS
     * PAGE_GUARD
     * PAGE_NOCACHE
     * PAGE_WRITECOMBINE
     * <p>
     * When allocating dynamic memory for an enclave, the flProtect parameter must be PAGE_READWRITE or
     * PAGE_EXECUTE_READWRITE.
     *
     * @see com.etdon.winj.constant.memory.MemoryProtection
     */
    private final int memoryProtection;

    private VirtualAllocEx(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, VIRTUAL_ALLOC_EX_SIGNATURE);

        this.processHandle = builder.processHandle;
        Conditional.executeIfNotNull(builder.addressPointer, () -> this.addressPointer = builder.addressPointer);
        this.size = builder.size;
        this.allocationType = builder.allocationType;
        this.memoryProtection = builder.memoryProtection;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.processHandle,
                this.addressPointer,
                this.size,
                this.allocationType,
                this.memoryProtection
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<VirtualAllocEx> {

        private MemorySegment processHandle;
        private MemorySegment addressPointer;
        private Long size;
        private Integer allocationType;
        private Integer memoryProtection;

        private Builder() {

        }

        public Builder processHandle(@NotNull final MemorySegment processHandle) {

            this.processHandle = processHandle;
            return this;

        }

        public Builder addressPointer(@NotNull final MemorySegment addressPointer) {

            this.addressPointer = addressPointer;
            return this;

        }

        public Builder size(final long size) {

            this.size = size;
            return this;

        }

        public Builder allocationType(final int allocationType) {

            this.allocationType = allocationType;
            return this;

        }

        public Builder memoryProtection(final int memoryProtection) {

            this.memoryProtection = memoryProtection;
            return this;

        }

        @NotNull
        @Override
        public VirtualAllocEx build() {

            Preconditions.checkNotNull(this.processHandle);
            Preconditions.checkNotNull(this.size);
            Preconditions.checkNotNull(this.allocationType);
            Preconditions.checkNotNull(this.memoryProtection);
            return new VirtualAllocEx(this);

        }

    }

}
