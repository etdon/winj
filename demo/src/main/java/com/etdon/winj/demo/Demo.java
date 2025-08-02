package com.etdon.winj.demo;

import com.etdon.winj.WinJ;
import com.etdon.winj.common.NativeContext;
import com.etdon.winj.constant.WaitTime;
import com.etdon.winj.constant.memory.AllocationType;
import com.etdon.winj.constant.memory.FreeType;
import com.etdon.winj.constant.memory.MemoryProtection;
import com.etdon.winj.constant.process.ProcessAccessRight;
import com.etdon.winj.facade.Window;
import com.etdon.winj.facade.WindowsAPI;
import com.etdon.winj.function.kernel32.*;
import com.etdon.winj.render.debug.queue.StringDebugRenderQueueItem;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.nio.charset.StandardCharsets;

import static com.etdon.winj.type.NativeDataType.SIZE_T;

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

    public void injectDll(@NotNull final NativeContext nativeContext, final int pid, @NotNull final String path) throws Throwable {

        final Arena arena = nativeContext.getArena();
        final MemorySegment processHandle = (MemorySegment) nativeContext.getCaller().call(
                OpenProcess.builder()
                        .access(ProcessAccessRight.PROCESS_ALL_ACCESS)
                        .inheritHandle(false)
                        .processId(pid)
                        .build()
        );
        if (processHandle.address() == 0) {
            //
        }

        final MemorySegment kernelModuleHandle = (MemorySegment) nativeContext.getCaller().call(
                GetModuleHandleW.builder()
                        .moduleName(arena.allocateFrom("kernel32.dll", StandardCharsets.UTF_16LE))
                        .build()
        );
        if (kernelModuleHandle.address() == 0) {
            //
        }

        final MemorySegment loadLibraryPointer = (MemorySegment) nativeContext.getCaller().call(
                GetProcAddress.builder()
                        .moduleHandle(kernelModuleHandle)
                        .localeNamePointer(arena.allocateFrom("LoadLibraryA"))
                        .build()
        );
        if (loadLibraryPointer.address() == 0) {
            //
        }

        final MemorySegment dllPath = arena.allocateFrom(path);
        final MemorySegment allocatedBaseAddress = (MemorySegment) nativeContext.getCaller().call(
                VirtualAllocEx.builder()
                        .processHandle(processHandle)
                        .size(dllPath.byteSize())
                        .allocationType(AllocationType.MEM_RESERVE | AllocationType.MEM_COMMIT)
                        .memoryProtection(MemoryProtection.PAGE_READWRITE)
                        .build()
        );
        if (allocatedBaseAddress.address() == 0) {
            //
        }

        final MemorySegment bytesWritten = arena.allocate(SIZE_T.byteSize());
        boolean success = (int) nativeContext.getCaller().call(
                WriteProcessMemory.builder()
                        .processHandle(processHandle)
                        .baseAddressPointer(allocatedBaseAddress)
                        .bufferPointer(dllPath)
                        .size(dllPath.byteSize())
                        .bytesWrittenOutputPointer(bytesWritten)
                        .build()
        ) > 0;
        if (!success) {
            //
        }

        final MemorySegment remoteThreadHandle = (MemorySegment) nativeContext.getCaller().call(
                CreateRemoteThread.builder()
                        .processHandle(processHandle)
                        .threadStartRoutinePointer(loadLibraryPointer)
                        .parameterPointer(allocatedBaseAddress)
                        .build()
        );
        if (remoteThreadHandle.address() == 0) {
            //
        }

        int result = (int) nativeContext.getCaller().call(
                WaitForSingleObject.builder()
                        .handle(remoteThreadHandle)
                        .timeoutMillis(WaitTime.INFINITE)
                        .build()
        );

        success = (int) nativeContext.getCaller().call(
                VirtualFreeEx.builder()
                        .processHandle(processHandle)
                        .addressPointer(allocatedBaseAddress)
                        .freeType(FreeType.MEM_RELEASE)
                        .build()
        ) > 0;
        if (!success) {
            //
        }

        success = (int) nativeContext.getCaller().call(CloseHandle.of(remoteThreadHandle)) > 0;
        if (!success) {
            //
        }

        success = (int) nativeContext.getCaller().call(CloseHandle.of(processHandle)) > 0;
        if (!success) {
            //
        }

    }

    private Demo() {

    }

    public static Demo getInstance() {

        return DemoSingleton.INSTANCE;

    }


}
