package com.etdon.winj.function.std;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.SymbolLookup;

import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_LONG;

public final class AllocateMemory extends NativeFunction {

    public static final String LIBRARY = Library.STD;
    public static final String NATIVE_NAME = "malloc";
    public static final FunctionDescriptor MALLOC_SIGNATURE = FunctionDescriptor.of(
            ADDRESS,
            JAVA_LONG
    );

    /**
     * Bytes to allocate.
     */
    private final long size;

    private AllocateMemory(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, MALLOC_SIGNATURE);

        this.size = builder.size;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(this.size);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<AllocateMemory> {

        private long size;

        public Builder size(final long size) {

            this.size = size;
            return this;

        }

        @Override
        public AllocateMemory build() {

            return new AllocateMemory(this);

        }

    }

}
