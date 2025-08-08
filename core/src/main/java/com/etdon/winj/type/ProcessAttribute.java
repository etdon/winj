package com.etdon.winj.type;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.NativeType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static com.etdon.winj.type.constant.NativeDataType.*;
import static java.lang.foreign.ValueLayout.*;

public final class ProcessAttribute extends NativeType {

    public static final MemoryLayout PS_ATTRIBUTE = MemoryLayout.structLayout(
            ULONG_PTR.withName("Attribute"),
            SIZE_T.withName("Size"),
            MemoryLayout.unionLayout(
                    ULONG_PTR.withName("Value"),
                    PVOID.withName("ValuePtr")
            ),
            PSIZE_T.withName("ReturnLength")
    );

    /**
     * {@link com.etdon.winj.constant.ProcessAttributeValue}
     */
    private final long attribute;

    private final long size;

    private final MemorySegment value;

    private MemorySegment returnLengthOutputPointer = MemorySegment.NULL;

    public ProcessAttribute(final long attribute,
                            final long size,
                            @NotNull final MemorySegment value,
                            @Nullable final MemorySegment returnLengthOutputPointer) {

        Preconditions.checkNotNull(value);
        this.attribute = attribute;
        this.size = size;
        this.value = value;
        Conditional.executeIfNotNull(returnLengthOutputPointer, () -> this.returnLengthOutputPointer = returnLengthOutputPointer);

    }

    public ProcessAttribute(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(PS_ATTRIBUTE.byteSize(), arena, null);

        this.attribute = memorySegment.get(JAVA_LONG, 0);
        this.size = memorySegment.get(JAVA_LONG, 8);
        this.value = memorySegment.get(ADDRESS, 16);
        this.returnLengthOutputPointer = memorySegment.get(ADDRESS, 24);

    }

    private ProcessAttribute(@NotNull final Builder builder) {

        this.attribute = builder.attribute;
        this.size = builder.size;
        this.value = builder.value;
        Conditional.executeIfNotNull(builder.returnLengthOutputPointer, () -> this.returnLengthOutputPointer = builder.returnLengthOutputPointer);

    }

    @NotNull
    @Override
    public MemoryLayout getMemoryLayout() {

        return PS_ATTRIBUTE;

    }

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment memorySegment = arena.allocate(PS_ATTRIBUTE.byteSize());
        memorySegment.set(JAVA_LONG, 0, this.attribute);
        memorySegment.set(JAVA_LONG, 8, this.size);
        memorySegment.set(ADDRESS, 16, this.value);
        memorySegment.set(ADDRESS, 24, this.returnLengthOutputPointer);

        return memorySegment;

    }

    public long getAttribute() {

        return this.attribute;

    }

    public long getSize() {

        return this.size;

    }

    public MemorySegment getValue() {

        return this.value;

    }

    public MemorySegment getReturnLengthOutputPointer() {

        return this.returnLengthOutputPointer;

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<ProcessAttribute> {

        private Long attribute;
        private Long size;
        private MemorySegment value;
        private MemorySegment returnLengthOutputPointer;

        public Builder attribute(final long attribute) {

            this.attribute = attribute;
            return this;

        }

        public Builder size(final long size) {

            this.size = size;
            return this;

        }

        public Builder value(@NotNull final MemorySegment value) {

            this.value = value;
            return this;

        }

        public Builder returnLengthOutputPointer(@NotNull final MemorySegment returnLengthOutputPointer) {

            this.returnLengthOutputPointer = returnLengthOutputPointer;
            return this;

        }

        @NotNull
        @Override
        public ProcessAttribute build() {

            Preconditions.checkNotNull(this.attribute);
            Preconditions.checkNotNull(this.size);
            Preconditions.checkNotNull(this.value);
            return new ProcessAttribute(this);

        }

    }

}
