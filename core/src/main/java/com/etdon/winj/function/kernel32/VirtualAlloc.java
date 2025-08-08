package com.etdon.winj.function.kernel32;

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
import static com.etdon.winj.type.constant.NativeDataType.DWORD;

@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/memoryapi/nf-memoryapi-virtualalloc")
public final class VirtualAlloc extends NativeFunction {

    public static final String LIBRARY = Library.KERNEL_32;
    public static final String NATIVE_NAME = "VirtualAlloc";
    public static final FunctionDescriptor VIRTUAL_ALLOC_SIGNATURE = FunctionDescriptor.of(
            LPVOID,
            LPVOID.withName("lpAddress"),
            SIZE_T.withName("dwSize"),
            DWORD.withName("flAllocationType"),
            DWORD.withName("flProtect")
    );

    /**
     * The starting address of the region to allocate. If the memory is being reserved, the specified address is
     * rounded down to the nearest multiple of the allocation granularity. If the memory is already reserved and is
     * being committed, the address is rounded down to the next page boundary. To determine the size of a page and the
     * allocation granularity on the host computer, use the GetSystemInfo function. If this parameter is NULL, the
     * system determines where to allocate the region.
     * <p>
     * If this address is within an enclave that you have not initialized by calling InitializeEnclave, VirtualAlloc
     * allocates a page of zeros for the enclave at that address. The page must be previously uncommitted, and will not
     * be measured with the EEXTEND instruction of the Intel Software Guard Extensions programming model.
     * <p>
     * If the address is within an enclave that you initialized, then the allocation operation fails with the
     * ERROR_INVALID_ADDRESS error. That is true for enclaves that do not support dynamic memory management
     * (i.e. SGX1). SGX2 enclaves will permit allocation, and the page must be accepted by the enclave after it has
     * been allocated.
     */
    private MemorySegment addressPointer = MemorySegment.NULL;

    /**
     * The size of the region, in bytes. If the lpAddress parameter is NULL, this value is rounded up to the next page
     * boundary. Otherwise, the allocated pages include all pages containing one or more bytes in the range from
     * lpAddress to lpAddress+dwSize. This means that a 2-byte range straddling a page boundary causes both pages to be
     * included in the allocated region.
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

    private VirtualAlloc(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, VIRTUAL_ALLOC_SIGNATURE);

        Conditional.executeIfNotNull(builder.addressPointer, () -> this.addressPointer = builder.addressPointer);
        this.size = builder.size;
        this.allocationType = builder.allocationType;
        this.memoryProtection = builder.memoryProtection;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.addressPointer,
                this.size,
                this.allocationType,
                this.memoryProtection
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<VirtualAlloc> {

        private MemorySegment addressPointer;
        private Long size;
        private Integer allocationType;
        private Integer memoryProtection;

        private Builder() {

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
        public VirtualAlloc build() {

            Preconditions.checkNotNull(this.size);
            Preconditions.checkNotNull(this.allocationType);
            Preconditions.checkNotNull(this.memoryProtection);
            return new VirtualAlloc(this);

        }

    }

}
