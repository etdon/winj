package com.etdon.winj.render.debug.queue;

import com.etdon.winj.function.user32.DrawTextW;
import com.etdon.winj.type.Rectangle;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.MemorySegment;
import java.nio.charset.StandardCharsets;

public class StringDebugRenderQueueItem extends DebugRenderQueueItem<String> {

    private final String text;

    public StringDebugRenderQueueItem(final String text) {

        super(0);

        this.text = text;

    }

    public StringDebugRenderQueueItem(final int repeats,
                                      final String text) {

        super(repeats);

        this.text = text;

    }

    @Override
    public void render(@NotNull final MemorySegment windowHandle, @NotNull final DebugRenderQueueContext context) throws Throwable {

        final DebugRenderQueue owner = context.owner();
        final MemorySegment textSegment = owner.getArena().allocateFrom(text, StandardCharsets.UTF_16LE);
        final MemorySegment rectangle = Rectangle.of(10, 10 + (20 * (context.position() - 1)), 300, 50 + (20 * (context.position() - 1))).createMemorySegment(owner.getArena());
        owner.getNativeCaller().call(
                DrawTextW.builder()
                        .deviceContextHandle(windowHandle)
                        .textPointer(textSegment)
                        .textLength(this.text.length())
                        .rectanglePointer(rectangle)
                        .format(0)
                        .build()
        );

    }

    public static StringDebugRenderQueueItem of(@NotNull final String text) {

        return new StringDebugRenderQueueItem(text);

    }

    public static StringDebugRenderQueueItem repeating(@NotNull final String text) {

        return new StringDebugRenderQueueItem(-1, text);

    }

}
