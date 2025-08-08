package com.etdon.winj.function.ntdll;

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

import static com.etdon.winj.type.constant.NativeDataType.*;

public final class RtlAllocateHeap extends NativeFunction {

    public static final String LIBRARY = Library.NTDLL;
    public static final String NATIVE_NAME = "RtlAllocateHeap";
    public static final FunctionDescriptor RTL_ALLOCATE_HEAP_SIGNATURE = FunctionDescriptor.of(
            PVOID,
            PVOID.withName("HeapHandle"),
            ULONG.withName("Flags"),
            SIZE_T.withName("Size")
    );

    /**
     * Handle for a private heap from which the memory will be allocated. This parameter is a handle returned from a
     * successful call to RtlCreateHeap .
     */
    private final MemorySegment heapHandle;

    /**
     * Controllable aspects of heap allocation. Specifying any of these values will override the corresponding value
     * specified when the heap was created with RtlCreateHeap.
     *
     * @see com.etdon.winj.constant.memory.HeapAllocationFlag
     */
    private int flags = 0;

    /**
     * Number of bytes to be allocated. If the heap, specified by the HeapHandle parameter, is a non-growable heap,
     * Size must be less than or equal to the heap's virtual memory threshold. (For more information, see the
     * VirtualMemoryThreshold member of the Parameters parameter to RtlCreateHeap.)
     */
    private final long size;

    private RtlAllocateHeap(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, RTL_ALLOCATE_HEAP_SIGNATURE);

        this.heapHandle = builder.heapHandle;
        Conditional.executeIfNotNull(builder.flags, () -> this.flags = builder.flags);
        this.size = builder.size;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(this.heapHandle, this.flags, this.size);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<RtlAllocateHeap> {

        private MemorySegment heapHandle;
        private Integer flags;
        private Long size;

        public Builder heapHandle(@NotNull final MemorySegment heapHandle) {

            this.heapHandle = heapHandle;
            return this;

        }

        public Builder flags(final int flags) {

            this.flags = flags;
            return this;

        }

        public Builder size(final long size) {

            this.size = size;
            return this;

        }

        @NotNull
        @Override
        public RtlAllocateHeap build() {

            Preconditions.checkNotNull(this.heapHandle);
            Preconditions.checkNotNull(this.size);
            return new RtlAllocateHeap(this);

        }

    }

}
