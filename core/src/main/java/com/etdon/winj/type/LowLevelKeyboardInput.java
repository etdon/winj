package com.etdon.winj.type;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.MemorySegmentable;
import com.etdon.winj.constant.LowLevelKeyboardHookFlag;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static java.lang.foreign.ValueLayout.*;

public final class LowLevelKeyboardInput implements MemorySegmentable {

    public static final MemoryLayout KBDLLHOOKSTRUCT = MemoryLayout.structLayout(
            JAVA_INT.withName("vkCode"),
            JAVA_INT.withName("scanCode"),
            JAVA_INT.withName("flags"),
            JAVA_INT.withName("time"),
            ADDRESS.withName("dwExtraInfo")
    );

    /**
     * A virtual-key code. The code must be a value in the range 1 to 254.
     */
    private final int virtualKeyCode;

    /**
     * A hardware scan code for the key.
     */
    private final int scanCode;

    /**
     * The extended-key flag, event-injected flags, context code, and transition-state flag. This member is specified
     * as follows. An application can use the following values to test the keystroke flags. Testing LLKHF_INJECTED
     * (bit 4) will tell you whether the event was injected. If it was, then testing LLKHF_LOWER_IL_INJECTED (bit 1)
     * will tell you whether or not the event was injected from a process running at lower integrity level.
     *
     * @see LowLevelKeyboardHookFlag
     */
    private int flags;

    /**
     * The time stamp for this message, equivalent to what GetMessageTime would return for this message.
     */
    private final int timeStamp;

    /**
     * Additional information associated with the message.
     */
    private final MemorySegment extraInfo;

    public LowLevelKeyboardInput(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(KBDLLHOOKSTRUCT.byteSize(), arena, null);

        this.virtualKeyCode = memorySegment.get(JAVA_INT, 0);
        this.scanCode = memorySegment.get(JAVA_INT, 4);
        this.flags = memorySegment.get(JAVA_INT, 8);
        this.timeStamp = memorySegment.get(JAVA_INT, 12);
        this.extraInfo = memorySegment.get(ADDRESS, 16);

    }

    private LowLevelKeyboardInput(@NotNull final Builder builder) {

        this.virtualKeyCode = builder.virtualKeyCode;
        this.scanCode = builder.scanCode;
        this.flags = builder.flags;
        this.timeStamp = builder.timeStamp;
        this.extraInfo = builder.extraInfo;

    }

    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment memorySegment = arena.allocate(KBDLLHOOKSTRUCT);
        memorySegment.set(JAVA_INT, 0, this.virtualKeyCode);
        memorySegment.set(JAVA_INT, 4, this.scanCode);
        memorySegment.set(JAVA_INT, 8, this.flags);
        memorySegment.set(JAVA_INT, 12, this.timeStamp);
        memorySegment.set(ADDRESS, 16, this.extraInfo);

        return memorySegment;

    }

    public int getVirtualKeyCode() {

        return this.virtualKeyCode;

    }

    public int getScanCode() {

        return this.scanCode;

    }

    public int getFlags() {

        return this.flags;

    }

    public void setFlags(final int flags) {

        this.flags = flags;

    }

    public int getTimeStamp() {

        return this.timeStamp;

    }

    @NotNull
    public MemorySegment getExtraInfo() {

        return this.extraInfo;

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<LowLevelKeyboardInput> {

        private Integer virtualKeyCode;
        private Integer scanCode;
        private Integer flags;
        private Integer timeStamp;
        private MemorySegment extraInfo;

        private Builder() {

        }

        public Builder virtualKeyCode(final int virtualKeyCode) {

            this.virtualKeyCode = virtualKeyCode;
            return this;

        }

        public Builder scanCode(final int scanCode) {

            this.scanCode = scanCode;
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
        public LowLevelKeyboardInput build() {

            Preconditions.checkNotNull(this.virtualKeyCode);
            Preconditions.checkNotNull(this.scanCode);
            Preconditions.checkNotNull(this.flags);
            Preconditions.checkNotNull(this.timeStamp);
            Preconditions.checkNotNull(this.extraInfo);

            return new LowLevelKeyboardInput(this);

        }

    }

}
