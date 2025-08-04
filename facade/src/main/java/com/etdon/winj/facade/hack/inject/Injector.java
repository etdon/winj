package com.etdon.winj.facade.hack.inject;

import com.etdon.winj.common.NativeContext;
import org.jetbrains.annotations.NotNull;

public abstract class Injector {

    protected NativeContext nativeContext;

    public Injector(@NotNull final NativeContext nativeContext) {

        this.nativeContext = nativeContext;

    }

    public abstract int inject(final int pid, @NotNull final String path) throws Throwable;

}
