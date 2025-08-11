package com.etdon.winj.function.ntdll;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.NativeName;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;

@NativeName(RtlFreeHeap.NATIVE_NAME)
public final class RtlFreeHeap extends NativeFunction {

    public static final String LIBRARY = Library.NTDLL;
    public static final String NATIVE_NAME = "RtlFreeHeap";
    public static final FunctionDescriptor RTL_FREE_HEAP_SIGNATURE = FunctionDescriptor.of(
            BOOL,
            HANDLE.withName("HeapHandle"),
            ULONG.withName("Flags"),
            LPVOID.withName("BaseAddress")
    );

    /**
     * A handle to the heap whose memory block is to be freed. This handle is returned by either the HeapCreate or
     * GetProcessHeap function.
     */
    @NativeName("HeapHandle")
    private final MemorySegment heapHandle;

    /**
     * The heap free options. Specifying the following value overrides the corresponding value specified in the
     * flOptions parameter when the heap was created by using the HeapCreate function.
     *
     * @see com.etdon.winj.constant.memory.HeapAllocationFlag
     */
    @NativeName("Flags")
    private int flags = 0;

    /**
     * A pointer to the memory block to be freed. This pointer is returned by the HeapAlloc or HeapReAlloc function.
     * This pointer can be NULL.
     */
    @NativeName("BaseAddress")
    private MemorySegment baseAddress = MemorySegment.NULL;

    private RtlFreeHeap(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, RTL_FREE_HEAP_SIGNATURE);

        this.heapHandle = builder.heapHandle;
        Conditional.executeIfNotNull(builder.flags, () -> this.flags = builder.flags);
        Conditional.executeIfNotNull(builder.baseAddress, () -> this.baseAddress = builder.baseAddress);

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(this.heapHandle, this.flags, this.baseAddress);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<RtlFreeHeap> {

        private MemorySegment heapHandle;
        private Integer flags;
        private MemorySegment baseAddress;

        public Builder heapHandle(@NotNull final MemorySegment heapHandle) {

            this.heapHandle = heapHandle;
            return this;

        }

        public Builder flags(final int flags) {

            this.flags = flags;
            return this;

        }

        public Builder baseAddress(@NotNull final MemorySegment baseAddress) {

            this.baseAddress = baseAddress;
            return this;

        }

        @NotNull
        @Override
        public RtlFreeHeap build() {

            Preconditions.checkNotNull(this.heapHandle);
            return new RtlFreeHeap(this);

        }

    }

}
