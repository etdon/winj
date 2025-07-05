package com.etdon.winj.facade;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.function.NativeCaller;
import com.etdon.winj.WinJ;
import com.etdon.winj.function.user32.GetForegroundWindow;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public class WindowsAPI {

    private final WinJ winJ;
    private final Arena arena;
    private final NativeCaller nativeCaller;

    public WindowsAPI(@NotNull final WinJ winJ) {

        Preconditions.checkNotNull(winJ);

        this.winJ = winJ;
        this.arena = winJ.getArena();
        this.nativeCaller = winJ.getNativeCaller();

    }

    public Window getForegroundWindow() throws Throwable {

        final MemorySegment windowHandle = (MemorySegment) this.nativeCaller.call(GetForegroundWindow.getInstance());
        return new Window(windowHandle);

    }

    public static WindowsAPI of(@NotNull final WinJ winJ) {

        Preconditions.checkNotNull(winJ);

        return new WindowsAPI(winJ);

    }

}
