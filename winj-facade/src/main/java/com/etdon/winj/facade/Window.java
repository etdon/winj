package com.etdon.winj.facade;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.common.NativeContext;
import com.etdon.winj.function.user32.GetWindowTextLengthW;
import com.etdon.winj.function.user32.GetWindowTextW;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.MemorySegment;
import java.nio.charset.StandardCharsets;

/**
 * A handle wrapper for a window allowing for easier facade oriented method calling related to windows.
 */

public class Window extends Handle {

    public Window(@NotNull final MemorySegment windowHandle) {

        super(windowHandle);

    }

    public String getText(@NotNull final NativeContext nativeContext) throws Throwable {

        final int textLength = 1 + (int) nativeContext.getCaller().call(
                GetWindowTextLengthW.builder()
                        .handle(super.handle)
                        .build()
        );
        final MemorySegment textBuffer = nativeContext.getArena().allocate(textLength * 2L);
        final int validatedTextLength = (int) nativeContext.getCaller().call(
                GetWindowTextW.builder()
                        .windowHandle(super.handle)
                        .textBuffer(textBuffer)
                        .textLength(textLength)
                        .build()
        );

        // Consider null character for text length validation.
        Preconditions.checkState(validatedTextLength + 1 == textLength);

        return textBuffer.getString(0, StandardCharsets.UTF_16LE);

    }

}
