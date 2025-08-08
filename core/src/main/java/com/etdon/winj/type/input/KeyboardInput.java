package com.etdon.winj.type.input;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.constant.KeyboardEventFlag;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static com.etdon.winj.type.constant.NativeDataType.*;
import static java.lang.foreign.ValueLayout.*;

public final class KeyboardInput extends InputData {

    public static final MemoryLayout KEYBDINPUT = MemoryLayout.structLayout(
            WORD.withName("wVk"),
            WORD.withName("wScan"),
            DWORD.withName("dwFlags"),
            DWORD.withName("time"),
            MemoryLayout.paddingLayout(4),
            ULONG_PTR.withName("dwExtraInfo")
    ).withByteAlignment(8);

    /**
     * A virtual-key code. The code must be a value in the range 1 to 254. If the dwFlags member specifies
     * KEYEVENTF_UNICODE, wVk must be 0.
     */
    private final short virtualKeyCode;

    /**
     * A hardware scan code for the key. If dwFlags specifies KEYEVENTF_UNICODE, wScan specifies a Unicode character
     * which is to be sent to the foreground application.
     */
    private final short scanCode;

    /**
     * Specifies various aspects of a keystroke.
     *
     * @see KeyboardEventFlag
     */
    private final int flags;

    /**
     * The time stamp for the event, in milliseconds. If this parameter is zero, the system will provide its own time
     * stamp.
     */
    private final int timeStamp;

    /**
     * An additional value associated with the keystroke. Use the GetMessageExtraInfo function to obtain this
     * information.
     */
    private final MemorySegment extraInfo;

    public KeyboardInput(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(KEYBDINPUT.byteSize(), arena, null);

        this.virtualKeyCode = memorySegment.get(JAVA_SHORT, 0);
        this.scanCode = memorySegment.get(JAVA_SHORT, 2);
        this.flags = memorySegment.get(JAVA_INT, 4);
        this.timeStamp = memorySegment.get(JAVA_INT, 8);
        this.extraInfo = memorySegment.get(ADDRESS, 16);

    }

    private KeyboardInput(@NotNull final Builder builder) {

        this.virtualKeyCode = builder.virtualKeyCode;
        this.scanCode = builder.scanCode;
        this.flags = builder.flags;
        this.timeStamp = builder.timeStamp;
        this.extraInfo = builder.extraInfo;

    }

    @NotNull
    @Override
    public MemoryLayout getMemoryLayout() {

        return KEYBDINPUT;

    }

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment memorySegment = arena.allocate(KEYBDINPUT.byteSize());
        memorySegment.set(JAVA_SHORT, 0, this.virtualKeyCode);
        memorySegment.set(JAVA_SHORT, 2, this.scanCode);
        memorySegment.set(JAVA_INT, 4, this.flags);
        memorySegment.set(JAVA_INT, 8, this.timeStamp);
        memorySegment.set(ADDRESS, 16, this.extraInfo);

        return memorySegment;

    }

    public short getVirtualKeyCode() {

        return this.virtualKeyCode;

    }

    public short getScanCode() {

        return this.scanCode;

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

    public static final class Builder implements FluentBuilder<KeyboardInput> {

        private Short virtualKeyCode;
        private Short scanCode;
        private Integer flags;
        private Integer timeStamp;
        private MemorySegment extraInfo;

        private Builder() {

        }

        public Builder virtualKeyCode(final short virtualKeyCode) {

            this.virtualKeyCode = virtualKeyCode;
            return this;

        }

        public Builder virtualKeyCode(final int virtualKeyCode) {

            this.virtualKeyCode = (short) virtualKeyCode;
            return this;

        }

        public Builder scanCode(final short scanCode) {

            this.scanCode = scanCode;
            return this;

        }

        public Builder scanCode(final int scanCode) {

            this.scanCode = (short) scanCode;
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
        public KeyboardInput build() {

            Preconditions.checkNotNull(this.virtualKeyCode);
            Preconditions.checkNotNull(this.scanCode);
            Preconditions.checkNotNull(this.flags);
            Preconditions.checkNotNull(this.timeStamp);
            Preconditions.checkNotNull(this.extraInfo);

            return new KeyboardInput(this);

        }

    }

}
