package com.etdon.winj.type.input;

import com.etdon.jbinder.NativeType;
import com.etdon.winj.constant.InputType;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Map;
import java.util.Optional;

public abstract class InputData extends NativeType {

    private static final Map<Integer, Binder> INPUT_DATA_TYPES = Map.of(
            InputType.INPUT_MOUSE, MouseInput::new,
            InputType.INPUT_KEYBOARD, KeyboardInput::new,
            InputType.INPUT_HARDWARE, HardwareInput::new
    );

    public void merge(@NotNull final MemorySegment target, final int offset) {

        try (final Arena arena = Arena.ofConfined()) {
            final MemorySegment memorySegment = this.createMemorySegment(arena);
            MemorySegment.copy(memorySegment, 0, target, offset, memorySegment.byteSize());
        }

    }

    @NotNull
    public static InputData create(@NotNull final Arena arena, @NotNull final MemorySegment memorySegment, final int type) {

        return Optional.ofNullable(INPUT_DATA_TYPES.get(type))
                .map((binder) -> binder.create(arena, memorySegment))
                .orElseThrow();

    }

    private interface Binder {

        InputData create(@NotNull final Arena arena, @NotNull final MemorySegment memorySegment);

    }

}
