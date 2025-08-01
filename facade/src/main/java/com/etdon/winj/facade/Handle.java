package com.etdon.winj.facade;

import org.jetbrains.annotations.NotNull;

import java.lang.foreign.MemorySegment;

public abstract class Handle {

    protected final MemorySegment handle;

    public Handle(@NotNull final MemorySegment handle) {

        this.handle = handle;

    }

    public MemorySegment getHandle() {

        return this.handle;

    }

}
