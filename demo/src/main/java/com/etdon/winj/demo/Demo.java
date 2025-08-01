package com.etdon.winj.demo;

import com.etdon.winj.WinJ;
import com.etdon.winj.common.NativeContext;
import com.etdon.winj.facade.Window;
import com.etdon.winj.facade.WindowsAPI;
import com.etdon.winj.render.debug.queue.StringDebugRenderQueueItem;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;

public final class Demo {

    private static class DemoSingleton {

        private static final Demo INSTANCE = new Demo();

    }

    public static void main(@NotNull final String[] args) {

        Demo.getInstance().initialize();

    }

    private void initialize() {

        try (final Arena arena = Arena.ofConfined()) {
            final WinJ winJ = new WinJ(arena);
            final NativeContext nativeContext = NativeContext.of(arena, winJ.getNativeCaller());
            final WindowsAPI windowsAPI = WindowsAPI.of(winJ);
            final Window window = windowsAPI.getForegroundWindow();
            final String text = window.getText(nativeContext);
            System.out.println("Text: " + text);
            winJ.getDebugRenderQueue().add(StringDebugRenderQueueItem.repeating("Hello!"));
            winJ.demo();
        } catch (final Throwable ex) {
            throw new RuntimeException(ex);
        }

    }

    private Demo() {

    }

    public static Demo getInstance() {

        return DemoSingleton.INSTANCE;

    }


}
