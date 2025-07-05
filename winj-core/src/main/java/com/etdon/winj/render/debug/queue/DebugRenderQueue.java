package com.etdon.winj.render.debug.queue;

import com.etdon.jbinder.function.NativeCaller;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class DebugRenderQueue {

    private final Deque<DebugRenderQueueItem<?>> queue = new ArrayDeque<>();
    private final Arena arena;
    private final NativeCaller nativeCaller;

    public DebugRenderQueue(@NotNull final Arena arena,
                            @NotNull final NativeCaller nativeCaller) {

        this.arena = arena;
        this.nativeCaller = nativeCaller;

    }

    public void apply(@NotNull final MemorySegment windowHandle) throws Throwable {

        int position = 1;
        final List<DebugRenderQueueItem<?>> repeating = new ArrayList<>();
        while (!this.queue.isEmpty()) {
            final DebugRenderQueueItem<?> item = queue.poll();
            if (item.getRepeats() >= 1) {
                item.decrease();
                repeating.add(item);
            } else if (item.getRepeats() <= -1) {
                repeating.add(item);
            }
            item.render(windowHandle, new DebugRenderQueueContext(this, position++));
        }
        this.queue.addAll(repeating);

    }

    public void add(@NotNull final DebugRenderQueueItem<?> item) {

        this.queue.add(item);

    }

    public void remove(@NotNull final DebugRenderQueueItem<?> item) {

        this.queue.remove(item);

    }

    @NotNull
    public Arena getArena() {

        return this.arena;

    }

    @NotNull
    public NativeCaller getNativeCaller() {

        return this.nativeCaller;

    }

}
