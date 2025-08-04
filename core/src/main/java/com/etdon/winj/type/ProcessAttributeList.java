package com.etdon.winj.type;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.MemorySegmentable;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

import static com.etdon.winj.type.NativeDataType.SIZE_T;
import static java.lang.foreign.ValueLayout.*;

public final class ProcessAttributeList implements MemorySegmentable {

    public static final MemoryLayout PS_ATTRIBUTE_LIST = MemoryLayout.structLayout(
            SIZE_T.withName("TotalLength"),
            MemoryLayout.sequenceLayout(1,
                    ProcessAttribute.PS_ATTRIBUTE
            ).withName("Attributes")
    );
    public static final ValueLayout PPS_ATTRIBUTE_LIST = ADDRESS.withName("PPS_ATTRIBUTE_LIST");

    private long totalLength;

    private MemorySegment[] attributes;

    public ProcessAttributeList(final long totalLength,
                                @NotNull final MemorySegment[] attributes) {

        Preconditions.checkNotNull(attributes);
        this.totalLength = totalLength;
        this.attributes = attributes;

    }

    public ProcessAttributeList(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(PS_ATTRIBUTE_LIST.byteSize(), arena, null);

        this.totalLength = memorySegment.get(JAVA_LONG, 0);
        final int entries = (int) ((this.totalLength - 8) / ProcessAttribute.PS_ATTRIBUTE.byteSize());
        this.attributes = new MemorySegment[entries];
        for (int i = 0; i < entries; i++) {
            final MemorySegment slice = memorySegment.asSlice(8 + i * ProcessAttribute.PS_ATTRIBUTE.byteSize(), ProcessAttribute.PS_ATTRIBUTE.byteSize());
            this.attributes[i] = new ProcessAttribute(arena, slice).createMemorySegment(arena);
        }

    }

    private ProcessAttributeList(@NotNull final Builder builder) {

        this.totalLength = builder.totalLength;
        this.attributes = builder.attributes;

    }

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment memorySegment = arena.allocate(8 + this.attributes.length * ProcessAttribute.PS_ATTRIBUTE.byteSize());
        memorySegment.set(JAVA_LONG, 0, this.totalLength);
        for (int i = 0; i < this.attributes.length; i++)
            MemorySegment.copy(this.attributes[i], 0, memorySegment, 8 + i * ProcessAttribute.PS_ATTRIBUTE.byteSize(), ProcessAttribute.PS_ATTRIBUTE.byteSize());

        return memorySegment;

    }

    public long getTotalLength() {

        return this.totalLength;

    }

    public void setTotalLength(final long totalLength) {

        this.totalLength = totalLength;

    }

    public MemorySegment[] getAttributes() {

        return this.attributes;

    }

    public void setAttributes(@NotNull final MemorySegment[] attributes) {

        this.attributes = attributes;

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<ProcessAttributeList> {

        private Long totalLength;
        private MemorySegment[] attributes = new MemorySegment[1];

        public Builder totalLength(final long totalLength) {

            this.totalLength = totalLength;
            return this;

        }

        public Builder attribute(@NotNull final MemorySegment attribute) {

            int targetIndex = -1;
            for (int i = 0; i < this.attributes.length; i++) {
                if (attributes[i] == null) {
                    targetIndex = i;
                    break;
                }
            }

            if (targetIndex != -1) {
                this.attributes[targetIndex] = attribute;
            } else {
                final MemorySegment[] buffer = new MemorySegment[this.attributes.length + 1];
                System.arraycopy(this.attributes, 0, buffer, 0, this.attributes.length);
                buffer[buffer.length - 1] = attribute;
                this.attributes = buffer;
            }
            return this;

        }

        public Builder attributes(@NotNull final MemorySegment[] attributes) {

            this.attributes = attributes;
            return this;

        }

        @NotNull
        @Override
        public ProcessAttributeList build() {

            Preconditions.checkNotNull(this.totalLength);
            Preconditions.checkNotNull(this.attributes);
            return new ProcessAttributeList(this);

        }

    }

}
