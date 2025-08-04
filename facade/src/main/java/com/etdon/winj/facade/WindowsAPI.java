package com.etdon.winj.facade;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.di.InstanceServiceProvider;
import com.etdon.commons.di.ServiceProvider;
import com.etdon.jbinder.SymbolLookupCache;
import com.etdon.jbinder.function.NativeCaller;
import com.etdon.winj.function.std.AllocateMemory;
import com.etdon.winj.function.std.FreeMemory;
import com.etdon.winj.function.user32.*;
import com.etdon.winj.render.debug.queue.DebugRenderQueue;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.constant.Library.*;
import static com.etdon.winj.constant.Library.ADVAPI_32;

public class WindowsAPI {

    private final ServiceProvider serviceProvider = InstanceServiceProvider.builder()
            .selfService()
            .build();
    private final SymbolLookupCache symbolLookupCache = new SymbolLookupCache();
    private final Arena arena;
    private final Linker linker;
    private final NativeCaller nativeCaller;
    private final DebugRenderQueue debugRenderQueue;

    public WindowsAPI(@NotNull final Arena arena) {

        Preconditions.checkNotNull(arena);
        this.arena = arena;
        this.serviceProvider.register(this.symbolLookupCache);
        this.linker = Linker.nativeLinker();
        this.serviceProvider.register(Linker.class, this.linker);
        this.nativeCaller = new NativeCaller(this.linker, this.symbolLookupCache);
        this.serviceProvider.register(this.nativeCaller);
        this.debugRenderQueue = new DebugRenderQueue(this.arena, this.nativeCaller);
        this.serviceProvider.register(this.debugRenderQueue);

        this.initialize();

    }

    private void initialize() {

        this.symbolLookupCache.register(USER_32, SymbolLookup.libraryLookup(USER_32, arena));
        this.symbolLookupCache.register(KERNEL_32, SymbolLookup.libraryLookup(KERNEL_32, arena));
        this.symbolLookupCache.register(GDI_32, SymbolLookup.libraryLookup(GDI_32, arena));
        this.symbolLookupCache.register(PSAPI, SymbolLookup.libraryLookup(PSAPI, arena));
        this.symbolLookupCache.register(NTDLL, SymbolLookup.libraryLookup(NTDLL, arena));
        this.symbolLookupCache.register(ADVAPI_32, SymbolLookup.libraryLookup(ADVAPI_32, arena));

    }

    public Window getForegroundWindow() throws Throwable {

        final MemorySegment windowHandle = (MemorySegment) this.nativeCaller.call(GetForegroundWindow.getInstance());
        return new Window(windowHandle);

    }

    public MemorySegment allocateManagedMemory(@NotNull final Arena arena, final long size) throws Throwable {

        MemorySegment memorySegment = (MemorySegment) this.nativeCaller.call(AllocateMemory.builder().size(size).build());
        memorySegment = memorySegment.reinterpret(size, arena, (target) -> {
            try {
                this.nativeCaller.call(FreeMemory.builder().memoryBlock(target).build());
            } catch (final Throwable ex) {
                throw new RuntimeException(ex);
            }
        });

        return memorySegment;

    }

    public void freeMemorySegment(@NotNull final MemorySegment target) {

        try {
            Preconditions.checkNotNull(target);
            this.nativeCaller.call(FreeMemory.builder().memoryBlock(target).build());
        } catch (final Throwable ex) {
            throw new RuntimeException(ex);
        }

    }

    public ServiceProvider getServiceProvider() {

        return this.serviceProvider;

    }

    public static WindowsAPI of(@NotNull final Arena arena) {

        Preconditions.checkNotNull(arena);
        return new WindowsAPI(arena);

    }

}
