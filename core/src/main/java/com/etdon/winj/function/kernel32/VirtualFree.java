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

import static com.etdon.winj.type.NativeDataType.*;

@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/memoryapi/nf-memoryapi-virtualfree")
public final class VirtualFree extends NativeFunction {

    public static final String LIBRARY = Library.KERNEL_32;
    public static final String NATIVE_NAME = "VirtualFree";
    public static final FunctionDescriptor VIRTUAL_FREE_SIGNATURE = FunctionDescriptor.of(
            BOOL,
            LPVOID.withName("lpAddress"),
            SIZE_T.withName("dwSize"),
            DWORD.withName("dwFreeType")
    );

    /**
     * A pointer to the base address of the region of pages to be freed.
     * <p>
     * If the dwFreeType parameter is MEM_RELEASE, this parameter must be the base address returned by the VirtualAlloc
     * function when the region of pages is reserved.
     */
    private final MemorySegment addressPointer;

    /**
     * The size of the region of memory to be freed, in bytes.
     * <p>
     * If the dwFreeType parameter is MEM_RELEASE, this parameter must be 0 (zero). The function frees the entire
     * region that is reserved in the initial allocation call to VirtualAlloc.
     * <p>
     * If the dwFreeType parameter is MEM_DECOMMIT, the function decommits all memory pages that contain one or more
     * bytes in the range from the lpAddress parameter to (lpAddress+dwSize). This means, for example, that a 2-byte
     * region of memory that straddles a page boundary causes both pages to be decommitted. If lpAddress is the base
     * address returned by VirtualAlloc and dwSize is 0 (zero), the function decommits the entire region that is
     * allocated by VirtualAlloc. After that, the entire region is in the reserved state.
     */
    private long size = 0;

    /**
     * The type of free operation.
     *
     * @see com.etdon.winj.constant.memory.FreeType
     */
    private final int freeType;

    private VirtualFree(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, VIRTUAL_FREE_SIGNATURE);

        this.addressPointer = builder.addressPointer;
        Conditional.executeIfNotNull(builder.size, () -> this.size = builder.size);
        this.freeType = builder.freeType;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.addressPointer,
                this.size,
                this.freeType
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<VirtualFree> {

        private MemorySegment addressPointer;
        private Long size;
        private Integer freeType;

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

        public Builder freeType(final int freeType) {

            this.freeType = freeType;
            return this;

        }

        @NotNull
        @Override
        public VirtualFree build() {

            Preconditions.checkNotNull(this.addressPointer);
            Preconditions.checkNotNull(this.freeType);
            return new VirtualFree(this);

        }

    }

}
