package com.etdon.winj.type;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.MemorySegmentable;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static java.lang.foreign.ValueLayout.JAVA_INT;

public final class Rectangle implements MemorySegmentable {

    public static final MemoryLayout RECT = MemoryLayout.structLayout(
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT
    );

    /**
     * Specifies the x-coordinate of the upper-left corner of the rectangle.
     */
    private final int left;

    /**
     * Specifies the y-coordinate of the upper-left corner of the rectangle.
     */
    private final int top;

    /**
     * Specifies the x-coordinate of the lower-right corner of the rectangle.
     */
    private final int right;

    /**
     * Specifies the y-coordinate of the lower-right corner of the rectangle.
     */
    private final int bottom;

    public Rectangle(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(RECT.byteSize(), arena, null);

        this.left = memorySegment.get(JAVA_INT, 0);
        this.top = memorySegment.get(JAVA_INT, 4);
        this.right = memorySegment.get(JAVA_INT, 8);
        this.bottom = memorySegment.get(JAVA_INT, 12);

    }

    public Rectangle(final int left,
                     final int top,
                     final int right,
                     final int bottom) {

        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;

    }

    private Rectangle(@NotNull final Builder builder) {

        this.left = builder.left;
        this.top = builder.top;
        this.right = builder.right;
        this.bottom = builder.bottom;

    }

    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment memorySegment = arena.allocate(RECT);
        memorySegment.set(JAVA_INT, 0, this.left);
        memorySegment.set(JAVA_INT, 4, this.top);
        memorySegment.set(JAVA_INT, 8, this.right);
        memorySegment.set(JAVA_INT, 12, this.bottom);

        return memorySegment;

    }

    public static Rectangle of(final int left, final int top, final int right, final int bottom) {

        return new Rectangle(left, top, right, bottom);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<Rectangle> {

        private Integer left;
        private Integer top;
        private Integer right;
        private Integer bottom;

        public Builder left(final int left) {

            this.left = left;
            return this;

        }

        public Builder top(final int top) {

            this.top = top;
            return this;

        }

        public Builder right(final int right) {

            this.right = right;
            return this;

        }

        public Builder bottom(final int bottom) {

            this.bottom = bottom;
            return this;

        }

        @NotNull
        @Override
        public Rectangle build() {

            Preconditions.checkNotNull(this.left);
            Preconditions.checkNotNull(this.top);
            Preconditions.checkNotNull(this.right);
            Preconditions.checkNotNull(this.bottom);
            return new Rectangle(this);

        }

    }

}
