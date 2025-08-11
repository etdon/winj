package com.etdon.winj.type;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.NativeType;
import com.etdon.jbinder.common.NativeName;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static com.etdon.winj.type.constant.NativeDataType.HANDLE;
import static java.lang.foreign.ValueLayout.ADDRESS;

public final class ProcessCreateFailExeName extends NativeType {

    public static final MemoryLayout PS_CREATE_FAIL_EXE_NAME = MemoryLayout.structLayout(
            HANDLE.withName("IFEOKey")
    );

    @NativeName("IFEOKey")
    private final MemorySegment ifeoKey;

    public ProcessCreateFailExeName(@NotNull final MemorySegment ifeoKey) {

        this.ifeoKey = ifeoKey;

    }

    public ProcessCreateFailExeName(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(PS_CREATE_FAIL_EXE_NAME.byteSize(), arena, null);

        this.ifeoKey = memorySegment.get(ADDRESS, 0);

    }

    private ProcessCreateFailExeName(@NotNull final Builder builder) {

        this.ifeoKey = builder.ifeoKey;

    }

    @NotNull
    @Override
    public MemoryLayout getMemoryLayout() {

        return PS_CREATE_FAIL_EXE_NAME;

    }

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment memorySegment = arena.allocate(PS_CREATE_FAIL_EXE_NAME.byteSize());
        memorySegment.set(ADDRESS, 0, this.ifeoKey);

        return memorySegment;

    }

    public MemorySegment getIfeoKey() {

        return this.ifeoKey;

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<ProcessCreateFailExeName> {

        private MemorySegment ifeoKey;

        public Builder ifeoKey(@NotNull final MemorySegment ifeoKey) {

            this.ifeoKey = ifeoKey;
            return this;

        }

        @NotNull
        @Override
        public ProcessCreateFailExeName build() {

            Preconditions.checkNotNull(ifeoKey);
            return new ProcessCreateFailExeName(this);

        }

    }

}
