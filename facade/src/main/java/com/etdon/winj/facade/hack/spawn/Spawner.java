package com.etdon.winj.facade.hack.spawn;

import com.etdon.winj.common.NativeContext;
import org.jetbrains.annotations.NotNull;

public abstract class Spawner {

    protected NativeContext nativeContext;

    public Spawner(@NotNull final NativeContext nativeContext) {

        this.nativeContext = nativeContext;

    }

    public abstract int spawn() throws Throwable;

}
