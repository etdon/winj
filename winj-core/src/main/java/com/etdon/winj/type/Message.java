package com.etdon.winj.type;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.MemorySegmentable;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static com.etdon.winj.type.NativeDataType.*;
import static java.lang.foreign.ValueLayout.*;

public final class Message implements MemorySegmentable {

    public static final MemoryLayout MSG = MemoryLayout.structLayout(
            HWND.withName("hwnd"),
            JAVA_INT.withName("message"),
            MemoryLayout.paddingLayout(4),
            WPARAM.withName("wParam"),
            LPARAM.withName("lParam"),
            DWORD.withName("time"),
            MemoryLayout.paddingLayout(4),
            Point.POINT.withName("pt")
    ).withByteAlignment(8);

    /**
     * A handle to the window whose window procedure receives the message. This member is NULL when the message is a
     * thread message.
     */
    private final MemorySegment windowHandle;

    /**
     * The message identifier. Applications can only use the low word; the high word is reserved by the system.
     */
    private final int messageId;

    /**
     * Additional information about the message. The exact meaning depends on the value of the message member.
     */
    private final long firstParameter;

    /**
     * Additional information about the message. The exact meaning depends on the value of the message member.
     */
    private final long secondParameter;

    /**
     * The time at which the message was posted.
     */
    private final int time;

    /**
     * The cursor position, in screen coordinates, when the message was posted.
     */
    private final MemorySegment point;

    public Message(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(MSG.byteSize(), arena, null);

        this.windowHandle = memorySegment.get(ADDRESS, 0);
        this.messageId = memorySegment.get(JAVA_INT, 8);
        this.firstParameter = memorySegment.get(JAVA_LONG, 12);
        this.secondParameter = memorySegment.get(JAVA_LONG, 20);
        this.time = memorySegment.get(JAVA_INT, 28);
        this.point = memorySegment.get(ADDRESS, 32);

    }

    private Message(@NotNull final Builder builder) {

        this.windowHandle = builder.windowHandle;
        this.messageId = builder.messageId;
        this.firstParameter = builder.firstParameter;
        this.secondParameter = builder.secondParameter;
        this.time = builder.time;
        this.point = builder.point;

    }

    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment memorySegment = arena.allocate(MSG.byteSize());
        memorySegment.set(ADDRESS, 0, this.windowHandle);
        memorySegment.set(JAVA_INT, 8, this.messageId);
        memorySegment.set(JAVA_LONG, 12, this.firstParameter);
        memorySegment.set(JAVA_LONG, 20, this.secondParameter);
        memorySegment.set(JAVA_INT, 28, this.time);
        memorySegment.set(ADDRESS, 32, this.point);

        return memorySegment;

    }

    public MemorySegment getWindowHandle() {

        return this.windowHandle;

    }

    public int getMessageId() {

        return this.messageId;

    }

    public long getFirstParameter() {

        return this.firstParameter;

    }

    public long getSecondParameter() {

        return this.secondParameter;

    }

    public int getTime() {

        return this.time;

    }

    public MemorySegment getPoint() {

        return this.point;

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<Message> {

        private MemorySegment windowHandle;
        private Integer messageId;
        private Long firstParameter;
        private Long secondParameter;
        private Integer time;
        private MemorySegment point;

        private Builder() {

        }

        public Builder windowHandle(@NotNull final MemorySegment windowHandle) {

            this.windowHandle = windowHandle;
            return this;

        }

        public Builder messageId(final int messageId) {

            this.messageId = messageId;
            return this;

        }

        public Builder firstParameter(final long firstParameter) {

            this.firstParameter = firstParameter;
            return this;

        }

        public Builder secondParameter(final long secondParameter) {

            this.secondParameter = secondParameter;
            return this;

        }

        public Builder time(final int time) {

            this.time = time;
            return this;

        }

        public Builder point(@NotNull final MemorySegment point) {

            this.point = point;
            return this;

        }

        @NotNull
        @Override
        public Message build() {

            Preconditions.checkNotNull(this.windowHandle);
            Preconditions.checkNotNull(this.messageId);
            Preconditions.checkNotNull(this.firstParameter);
            Preconditions.checkNotNull(this.secondParameter);
            Preconditions.checkNotNull(this.time);
            Preconditions.checkNotNull(this.point);

            return new Message(this);

        }

    }

}
