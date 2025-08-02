package com.etdon.winj.common;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.function.NativeCaller;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;

public class NativeContext {

    private final Arena arena;
    private final NativeCaller caller;

    public NativeContext(@NotNull final Arena arena,
                         @NotNull final NativeCaller caller) {

        Preconditions.checkNotNull(arena);
        Preconditions.checkNotNull(caller);

        this.arena = arena;
        this.caller = caller;

    }

    public Arena getArena() {

        return this.arena;

    }

    public NativeCaller getCaller() {

        return this.caller;

    }

    public static NativeContext of(@NotNull final Arena arena, @NotNull final NativeCaller caller) {

        Preconditions.checkNotNull(arena);
        Preconditions.checkNotNull(caller);

        return new NativeContext(arena, caller);

    }

}
