package com.etdon.winj.render.debug.queue;

import org.jetbrains.annotations.NotNull;

import java.lang.foreign.MemorySegment;

public abstract class DebugRenderQueueItem<T> {

    private int repeats;

    public DebugRenderQueueItem(final int repeats) {

        this.repeats = Math.max(repeats, -1);

    }

    public int getRepeats() {

        return this.repeats;

    }

    public void increase() {

        this.repeats++;

    }

    public void decrease() {

        this.repeats--;

    }

    public void setRepeats(final int repeats) {

        this.repeats = repeats;

    }

    public abstract void render(@NotNull final MemorySegment windowHandle, @NotNull final DebugRenderQueueContext context) throws Throwable;

}
