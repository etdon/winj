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

/**
 * Releases, decommits, or releases and decommits a region of memory within the virtual address space of a specified
 * process.
 */
@NativeName(VirtualFreeEx.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/memoryapi/nf-memoryapi-virtualfreeex")
public final class VirtualFreeEx extends NativeFunction<Integer> {

    public static final String LIBRARY = Library.KERNEL_32;
    public static final String NATIVE_NAME = "VirtualFreeEx";
    public static final FunctionDescriptor VIRTUAL_FREE_EX_SIGNATURE = FunctionDescriptor.of(
            BOOL,
            HANDLE.withName("hProcess"),
            LPVOID.withName("lpAddress"),
            SIZE_T.withName("dwSize"),
            DWORD.withName("dwFreeType")
    );

    /**
     * A handle to a process. The function frees memory within the virtual address space of the process.
     */
    @NativeName("hProcess")
    private final MemorySegment processHandle;

    /**
     * A pointer to the starting address of the region of memory to be freed.
     * <p>
     * If the dwFreeType parameter is MEM_RELEASE, lpAddress must be the base address returned by the VirtualAllocEx
     * function when the region is reserved.
     */
    @NativeName("lpAddress")
    private final MemorySegment addressPointer;

    /**
     * The size of the region of memory to free, in bytes.
     * <p>
     * If the dwFreeType parameter is MEM_RELEASE, dwSize must be 0 (zero). The function frees the entire region that
     * is reserved in the initial allocation call to VirtualAllocEx.
     * <p>
     * If dwFreeType is MEM_DECOMMIT, the function decommits all memory pages that contain one or more bytes in the
     * range from the lpAddress parameter to (lpAddress+dwSize). This means, for example, that a 2-byte region of
     * memory that straddles a page boundary causes both pages to be decommitted. If lpAddress is the base address
     * returned by VirtualAllocEx and dwSize is 0 (zero), the function decommits the entire region that is allocated
     * by VirtualAllocEx. After that, the entire region is in the reserved state.
     */
    @NativeName("dwSize")
    private long size = 0;

    /**
     * The type of free operation.
     *
     * @see com.etdon.winj.constant.memory.FreeType
     */
    @NativeName("dwFreeType")
    private final int freeType;

    private VirtualFreeEx(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, VIRTUAL_FREE_EX_SIGNATURE);

        this.processHandle = builder.processHandle;
        this.addressPointer = builder.addressPointer;
        Conditional.executeIfNotNull(builder.size, () -> this.size = builder.size);
        this.freeType = builder.freeType;

    }

    @Override
    public Integer call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return (Integer) super.obtainHandle(linker, symbolLookup).invoke(
                this.processHandle,
                this.addressPointer,
                this.size,
                this.freeType
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<VirtualFreeEx> {

        private MemorySegment processHandle;
        private MemorySegment addressPointer;
        private Long size;
        private Integer freeType;

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

        public Builder freeType(final int freeType) {

            this.freeType = freeType;
            return this;

        }

        @NotNull
        @Override
        public VirtualFreeEx build() {

            Preconditions.checkNotNull(this.processHandle);
            Preconditions.checkNotNull(this.addressPointer);
            Preconditions.checkNotNull(this.freeType);
            return new VirtualFreeEx(this);

        }

    }

}
