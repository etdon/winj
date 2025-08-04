package com.etdon.winj.type;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.MemorySegmentable;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

import static com.etdon.winj.type.NativeDataType.*;
import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_SHORT;

public final class UnicodeString implements MemorySegmentable {

    public static final MemoryLayout UNICODE_STRING = MemoryLayout.structLayout(
            USHORT.withName("Length"),
            USHORT.withName("MaximumLength"),
            MemoryLayout.paddingLayout(4),
            PWCH.withName("Buffer")
    );
    public static final ValueLayout PCUNICODE_STRING = ADDRESS.withName("PCUNICODE_STRING");

    private final short length;

    private final short maximumLength;

    private final MemorySegment buffer;

    public UnicodeString(final short length,
                         final short maximumLength,
                         @NotNull final MemorySegment buffer) {

        Preconditions.checkNotNull(buffer);
        this.length = length;
        this.maximumLength = maximumLength;
        this.buffer = buffer;

    }

    public UnicodeString(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(UNICODE_STRING.byteSize(), arena, null);

        this.length = memorySegment.get(JAVA_SHORT, 0);
        this.maximumLength = memorySegment.get(JAVA_SHORT, 2);
        this.buffer = memorySegment.get(ADDRESS, 8);

    }

    private UnicodeString(@NotNull final Builder builder) {

        this.length = builder.length;
        this.maximumLength = builder.maximumLength;
        this.buffer = builder.buffer;

    }

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull Arena arena) {

        final MemorySegment memorySegment = arena.allocate(UNICODE_STRING.byteSize());
        memorySegment.set(JAVA_SHORT, 0, this.length);
        memorySegment.set(JAVA_SHORT, 2, this.maximumLength);
        memorySegment.set(ADDRESS, 8, this.buffer);

        return memorySegment;

    }

    public short getLength() {

        return this.length;

    }

    public short getMaximumLength() {

        return this.maximumLength;

    }

    public MemorySegment getBuffer() {

        return this.buffer;

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<UnicodeString> {

        private Short length;
        private Short maximumLength;
        private MemorySegment buffer;

        public Builder length(final short length) {

            this.length = length;
            return this;

        }

        public Builder maximumLength(final short maximumLength) {

            this.maximumLength = maximumLength;
            return this;

        }

        public Builder buffer(@NotNull final MemorySegment buffer) {

            this.buffer = buffer;
            return this;

        }

        @NotNull
        @Override
        public UnicodeString build() {

            Preconditions.checkNotNull(this.length);
            Preconditions.checkNotNull(this.maximumLength);
            Preconditions.checkNotNull(this.buffer);
            return new UnicodeString(this);

        }

    }

}
