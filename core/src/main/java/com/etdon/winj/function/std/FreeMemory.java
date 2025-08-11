package com.etdon.winj.function.std;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.NativeName;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static java.lang.foreign.ValueLayout.ADDRESS;

@NativeName(FreeMemory.NATIVE_NAME)
public final class FreeMemory extends NativeFunction {

    public static final String LIBRARY = Library.STD;
    public static final String NATIVE_NAME = "free";
    public static final FunctionDescriptor FREE_SIGNATURE = FunctionDescriptor.ofVoid(
            ADDRESS
    );

    /**
     * Previously allocated memory block to be freed.
     */
    private final MemorySegment memoryBlock;

    private FreeMemory(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, FREE_SIGNATURE);

        this.memoryBlock = builder.memoryBlock;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(this.memoryBlock);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<FreeMemory> {

        private MemorySegment memoryBlock;

        public Builder memoryBlock(final MemorySegment memoryBlock) {

            this.memoryBlock = memoryBlock;
            return this;

        }

        @NotNull
        @Override
        public FreeMemory build() {

            Preconditions.checkNotNull(this.memoryBlock);

            return new FreeMemory(this);

        }

    }

}
