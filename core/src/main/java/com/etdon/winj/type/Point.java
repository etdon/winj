package com.etdon.winj.type;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.NativeType;
import com.etdon.jbinder.common.NativeDocumentation;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static com.etdon.winj.type.constant.NativeDataType.LONG;
import static java.lang.foreign.ValueLayout.JAVA_LONG;

@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/windef/ns-windef-point")
public final class Point extends NativeType {

    public static final MemoryLayout POINT = MemoryLayout.structLayout(
            LONG.withName("x"),
            LONG.withName("y")
    );

    /**
     * Specifies the x-coordinate of the point.
     */
    private final long x;

    /**
     * Specifies the y-coordinate of the point.
     */
    private final long y;

    public Point(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(POINT.byteSize(), arena, null);

        this.x = memorySegment.get(JAVA_LONG, 0);
        this.y = memorySegment.get(JAVA_LONG, 4);

    }

    public Point(final long x,
                 final long y) {

        this.x = x;
        this.y = y;

    }

    private Point(@NotNull final Builder builder) {

        this.x = builder.x;
        this.y = builder.y;

    }

    @NotNull
    @Override
    public MemoryLayout getMemoryLayout() {

        return POINT;

    }

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment memorySegment = arena.allocate(POINT);
        memorySegment.set(JAVA_LONG, 0, this.x);
        memorySegment.set(JAVA_LONG, 4, this.y);

        return memorySegment;

    }

    public long getX() {

        return this.x;

    }

    public long getY() {

        return this.y;

    }

    public static Point of(final long x, final long y) {

        return new Point(x, y);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<Point> {

        private Long x;
        private Long y;

        public Builder x(final long x) {

            this.x = x;
            return this;

        }

        public Builder y(final long y) {

            this.y = y;
            return this;

        }

        @NotNull
        @Override
        public Point build() {

            Preconditions.checkNotNull(this.x);
            Preconditions.checkNotNull(this.y);

            return new Point(this);

        }

    }

}
