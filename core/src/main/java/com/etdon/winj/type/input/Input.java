package com.etdon.winj.type.input;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.MemorySegmentable;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static com.etdon.winj.type.NativeDataType.DWORD;
import static java.lang.foreign.ValueLayout.*;

public final class Input implements MemorySegmentable {

    public static final MemoryLayout INPUT = MemoryLayout.structLayout(
            DWORD.withName("type"),
            MemoryLayout.paddingLayout(4),
            MemoryLayout.unionLayout(
                    HardwareInput.HARDWAREINPUT,
                    KeyboardInput.KEYBDINPUT,
                    MouseInput.MOUSEINPUT
            )
    ).withByteAlignment(8);

    /**
     * The type of the input event.
     */
    private final int type;

    /**
     * The data.
     */
    private final InputData data;

    public Input(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(INPUT.byteSize(), arena, null);

        this.type = memorySegment.get(JAVA_INT, 0);
        this.data = InputData.create(arena, memorySegment.asSlice(8), this.type);

    }

    private Input(@NotNull final Builder builder) {

        this.type = builder.type;
        this.data = builder.data;

    }

    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment memorySegment = arena.allocate(INPUT.byteSize());
        memorySegment.set(JAVA_INT, 0, this.type);
        this.data.merge(memorySegment, 8);

        return memorySegment;

    }

    public int getType() {

        return this.type;

    }

    public InputData getData() {

        return this.data;

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<Input> {

        private Integer type;
        private InputData data;

        private Builder() {

        }

        public Builder type(final int type) {

            this.type = type;
            return this;

        }

        public Builder data(@NotNull final InputData data) {

            this.data = data;
            return this;

        }

        @NotNull
        @Override
        public Input build() {

            Preconditions.checkNotNull(this.type);
            Preconditions.checkNotNull(this.data);

            return new Input(this);

        }

    }

}
