package com.etdon.winj.render.debug.queue;

import org.jetbrains.annotations.NotNull;

public record DebugRenderQueueContext(@NotNull DebugRenderQueue owner,
                                      int position) {

}
