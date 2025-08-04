package com.etdon.winj.type;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.MemorySegmentable;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static com.etdon.winj.type.NativeDataType.*;
import static com.etdon.winj.type.UnicodeString.UNICODE_STRING;
import static java.lang.foreign.ValueLayout.*;

public final class CurrentDirectory implements MemorySegmentable {

    public static final MemoryLayout CURDIR = MemoryLayout.structLayout(
            UNICODE_STRING.withName("DosPath"),
            HANDLE.withName("Handle")
    );

    private final MemorySegment dosPath;

    private final MemorySegment handle;

    public CurrentDirectory(@NotNull final MemorySegment dosPath,
                            @NotNull final MemorySegment handle) {

        Preconditions.checkNotNull(dosPath);
        Preconditions.checkNotNull(handle);
        this.dosPath = dosPath;
        this.handle = handle;

    }

    public CurrentDirectory(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(CURDIR.byteSize(), arena, null);

        this.dosPath = memorySegment.asSlice(0, UNICODE_STRING);
        this.handle = memorySegment.get(ADDRESS, UNICODE_STRING.byteSize());

    }

    private CurrentDirectory(@NotNull final Builder builder) {

        this.dosPath = builder.dosPath;
        this.handle = builder.handle;

    }

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment memorySegment = arena.allocate(CURDIR);
        MemorySegment.copy(this.dosPath, 0, memorySegment, 0, UNICODE_STRING.byteSize());
        memorySegment.set(ADDRESS, UNICODE_STRING.byteSize(), this.handle);

        return memorySegment;

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<CurrentDirectory> {

        private MemorySegment dosPath;
        private MemorySegment handle;

        public Builder dosPath(@NotNull final MemorySegment dosPath) {

            this.dosPath = dosPath;
            return this;

        }

        public Builder handle(@NotNull final MemorySegment handle) {

            this.handle = handle;
            return this;

        }

        @NotNull
        @Override
        public CurrentDirectory build() {

            Preconditions.checkNotNull(this.dosPath);
            Preconditions.checkNotNull(this.handle);
            return new CurrentDirectory(this);

        }

    }

}
