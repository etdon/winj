package com.etdon.winj.type.input;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.constant.MouseEventFlag;
import com.etdon.winj.constant.XButton;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static com.etdon.winj.type.NativeDataType.*;
import static java.lang.foreign.ValueLayout.*;

public final class MouseInput extends InputData {

    public static final MemoryLayout MOUSEINPUT = MemoryLayout.structLayout(
            LONG.withName("dx"),
            LONG.withName("dy"),
            DWORD.withName("mouseData"),
            DWORD.withName("dwFlags"),
            DWORD.withName("time"),
            MemoryLayout.paddingLayout(4),
            ULONG_PTR.withName("dwExtraInfo")
    ).withByteAlignment(8);

    /**
     * The absolute position of the mouse, or the amount of motion since the last mouse event was generated, depending
     * on the value of the dwFlags member. Absolute data is specified as the x coordinate of the mouse; relative data
     * is specified as the number of pixels moved.
     */
    private final long x;

    /**
     * The absolute position of the mouse, or the amount of motion since the last mouse event was generated, depending
     * on the value of the dwFlags member. Absolute data is specified as the y coordinate of the mouse; relative data
     * is specified as the number of pixels moved.
     */
    private final long y;

    /**
     * If dwFlags contains MOUSEEVENTF_WHEEL, then mouseData specifies the amount of wheel movement. A positive value
     * indicates that the wheel was rotated forward, away from the user; a negative value indicates that the wheel was
     * rotated backward, toward the user. One wheel click is defined as WHEEL_DELTA, which is 120.
     * <p>
     * Windows Vista: If dwFlags contains MOUSEEVENTF_HWHEEL, then dwData specifies the amount of wheel movement. A
     * positive value indicates that the wheel was rotated to the right; a negative value indicates that the wheel was
     * rotated to the left. One wheel click is defined as WHEEL_DELTA, which is 120.
     * <p>
     * If dwFlags does not contain MOUSEEVENTF_WHEEL, MOUSEEVENTF_XDOWN, or MOUSEEVENTF_XUP, then mouseData should be
     * zero.
     * <p>
     * If dwFlags contains MOUSEEVENTF_XDOWN or MOUSEEVENTF_XUP, then mouseData specifies which X buttons were pressed
     * or released.
     *
     * @see XButton
     */
    private final int mouseData;

    /**
     * A set of bit flags that specify various aspects of mouse motion and button clicks.
     * <p>
     * The bit flags that specify mouse button status are set to indicate changes in status, not ongoing conditions.
     * For example, if the left mouse button is pressed and held down, MOUSEEVENTF_LEFTDOWN is set when the left button
     * is first pressed, but not for subsequent motions. Similarly MOUSEEVENTF_LEFTUP is set only when the button is
     * first released.
     * <p>
     * You cannot specify both the MOUSEEVENTF_WHEEL flag and either MOUSEEVENTF_XDOWN or MOUSEEVENTF_XUP flags
     * simultaneously in the dwFlags parameter, because they both require use of the mouseData field.
     *
     * @see MouseEventFlag
     */
    private final int flags;

    /**
     * The time stamp for the event, in milliseconds. If this parameter is 0, the system will provide its own time
     * stamp.
     */
    private final int timeStamp;

    /**
     * An additional value associated with the mouse event. An application calls GetMessageExtraInfo to obtain this
     * extra information.
     */
    private final MemorySegment extraInfo;

    public MouseInput(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(MOUSEINPUT.byteSize(), arena, null);

        this.x = memorySegment.get(JAVA_LONG, 0);
        this.y = memorySegment.get(JAVA_LONG, 8);
        this.mouseData = memorySegment.get(JAVA_INT, 16);
        this.flags = memorySegment.get(JAVA_INT, 20);
        this.timeStamp = memorySegment.get(JAVA_INT, 24);
        this.extraInfo = memorySegment.get(ADDRESS, 32);

    }

    private MouseInput(@NotNull final Builder builder) {

        this.x = builder.x;
        this.y = builder.y;
        this.mouseData = builder.mouseData;
        this.flags = builder.flags;
        this.timeStamp = builder.timeStamp;
        this.extraInfo = builder.extraInfo;

    }

    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment memorySegment = arena.allocate(MOUSEINPUT.byteSize());
        memorySegment.set(JAVA_LONG, 0, this.x);
        memorySegment.set(JAVA_LONG, 8, this.y);
        memorySegment.set(JAVA_INT, 16, this.mouseData);
        memorySegment.set(JAVA_INT, 20, this.flags);
        memorySegment.set(JAVA_INT, 24, this.timeStamp);
        memorySegment.set(ADDRESS, 32, this.extraInfo);

        return memorySegment;

    }

    public long getX() {

        return this.x;

    }

    public long getY() {

        return this.y;

    }

    public int getMouseData() {

        return this.mouseData;

    }

    public int getFlags() {

        return this.flags;

    }

    public int getTimeStamp() {

        return this.timeStamp;

    }

    public MemorySegment getExtraInfo() {

        return this.extraInfo;

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<MouseInput> {

        private Long x;
        private Long y;
        private Integer mouseData;
        private Integer flags;
        private Integer timeStamp;
        private MemorySegment extraInfo;

        private Builder() {

        }

        public Builder x(final long x) {

            this.x = x;
            return this;

        }

        public Builder y(final long y) {

            this.y = y;
            return this;

        }

        public Builder mouseData(final int mouseData) {

            this.mouseData = mouseData;
            return this;

        }

        public Builder flags(final int flags) {

            this.flags = flags;
            return this;

        }

        public Builder timeStamp(final int timeStamp) {

            this.timeStamp = timeStamp;
            return this;

        }

        public Builder extraInfo(@NotNull final MemorySegment extraInfo) {

            this.extraInfo = extraInfo;
            return this;

        }

        @NotNull
        @Override
        public MouseInput build() {

            Preconditions.checkNotNull(this.x);
            Preconditions.checkNotNull(this.y);
            Preconditions.checkNotNull(this.mouseData);
            Preconditions.checkNotNull(this.flags);
            Preconditions.checkNotNull(this.timeStamp);
            Preconditions.checkNotNull(this.extraInfo);

            return new MouseInput(this);

        }

    }

}
