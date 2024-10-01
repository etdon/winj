package com.etdon.winj.type;

import com.etdon.jbinder.common.MemorySegmentable;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static com.etdon.winj.type.NativeDataType.*;
import static com.etdon.winj.type.Rectangle.RECT;

// TODO: Implement.
public final class PaintData implements MemorySegmentable {

    public static final MemoryLayout PAINTSTRUCT = MemoryLayout.structLayout(
            HDC.withName("hdc"),
            BOOL.withName("fErase"),
            RECT.withName("rcPaint"),
            BOOL.withName("fRestore"),
            BOOL.withName("fIncUpdate"),
            MemoryLayout.sequenceLayout(32,
                    BYTE
            ).withName("rgbReserved")
    );

    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        return null;

    }

}
