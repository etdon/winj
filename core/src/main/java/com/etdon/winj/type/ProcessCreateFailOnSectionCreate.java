package com.etdon.winj.type;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.MemorySegmentable;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static com.etdon.winj.type.NativeDataType.*;
import static java.lang.foreign.ValueLayout.ADDRESS;

public final class ProcessCreateFailOnSectionCreate implements MemorySegmentable {

    public static final MemoryLayout PS_CREATE_FAIL_ON_SECTION_CREATE = MemoryLayout.structLayout(
            HANDLE.withName("FileHandle")
    );

    private final MemorySegment fileHandle;

    public ProcessCreateFailOnSectionCreate(@NotNull final MemorySegment fileHandle) {

        this.fileHandle = fileHandle;

    }

    public ProcessCreateFailOnSectionCreate(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(PS_CREATE_FAIL_ON_SECTION_CREATE.byteSize(), arena, null);

        this.fileHandle = memorySegment.get(ADDRESS, 0);

    }

    private ProcessCreateFailOnSectionCreate(@NotNull final Builder builder) {

        this.fileHandle = builder.fileHandle;

    }

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment memorySegment = arena.allocate(PS_CREATE_FAIL_ON_SECTION_CREATE.byteSize());
        memorySegment.set(ADDRESS, 0, this.fileHandle);

        return memorySegment;

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<ProcessCreateFailOnSectionCreate> {

        private MemorySegment fileHandle;

        public Builder fileHandle(@NotNull final MemorySegment fileHandle) {

            this.fileHandle = fileHandle;
            return this;

        }

        @NotNull
        @Override
        public ProcessCreateFailOnSectionCreate build() {

            Preconditions.checkNotNull(this.fileHandle);
            return new ProcessCreateFailOnSectionCreate(this);

        }

    }

}
