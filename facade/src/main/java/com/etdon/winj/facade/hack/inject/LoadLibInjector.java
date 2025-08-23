package com.etdon.winj.facade.hack.inject;

import com.etdon.jbinder.function.NativeCaller;
import com.etdon.winj.common.NativeContext;
import com.etdon.winj.constant.WaitTime;
import com.etdon.winj.constant.memory.AllocationType;
import com.etdon.winj.constant.memory.FreeType;
import com.etdon.winj.constant.memory.MemoryProtection;
import com.etdon.winj.constant.process.ProcessAccessRight;
import com.etdon.winj.function.kernel32.handle.CloseHandle;
import com.etdon.winj.function.kernel32.libloader.GetModuleHandleW;
import com.etdon.winj.function.kernel32.libloader.GetProcAddress;
import com.etdon.winj.function.kernel32.memory.VirtualAllocEx;
import com.etdon.winj.function.kernel32.memory.VirtualFreeEx;
import com.etdon.winj.function.kernel32.memory.WriteProcessMemory;
import com.etdon.winj.function.kernel32.process.CreateRemoteThread;
import com.etdon.winj.function.kernel32.process.OpenProcess;
import com.etdon.winj.function.kernel32.sync.WaitForSingleObject;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.etdon.winj.type.constant.NativeDataType.SIZE_T;

public class LoadLibInjector extends Injector {

    private static final Logger LOGGER = Logger.getLogger(LoadLibInjector.class.getName());

    public LoadLibInjector(@NotNull final NativeContext nativeContext) {

        super(nativeContext);

    }

    @Override
    public int inject(final int pid, @NotNull final String path) throws Throwable {

        if (!Files.exists(Path.of(path))) {
            LOGGER.log(Level.SEVERE, "Failed to find file under '{0}'", path);
            return InjectionResult.FAIL_FILE_NOT_FOUND;
        }

        final Arena arena = super.nativeContext.getArena();
        final NativeCaller caller = super.nativeContext.getCaller();
        final MemorySegment processHandle = caller.call(
                OpenProcess.builder()
                        .access(ProcessAccessRight.PROCESS_ALL_ACCESS)
                        .inheritHandle(false)
                        .processId(pid)
                        .build()
        );

        if (processHandle.address() == 0) {
            LOGGER.log(Level.SEVERE, "[{0}:{1}] Failed to obtain process handle for PID '{2}'", new Object[]{
                    OpenProcess.LIBRARY, OpenProcess.NATIVE_NAME, pid
            });
            return InjectionResult.FAIL;
        }

        final MemorySegment kernelModuleHandle = caller.call(
                GetModuleHandleW.builder()
                        .moduleName(arena.allocateFrom("kernel32.dll", StandardCharsets.UTF_16LE))
                        .build()
        );

        if (kernelModuleHandle.address() == 0) {
            LOGGER.log(Level.SEVERE, "[{0}:{1}] Failed to obtain kernel module handle", new Object[]{
                    GetModuleHandleW.LIBRARY, GetModuleHandleW.NATIVE_NAME
            });
            return InjectionResult.FAIL;
        }

        final MemorySegment loadLibraryPointer = caller.call(
                GetProcAddress.builder()
                        .moduleHandle(kernelModuleHandle)
                        .localeNamePointer(arena.allocateFrom("LoadLibraryA"))
                        .build()
        );

        if (loadLibraryPointer.address() == 0) {
            LOGGER.log(Level.SEVERE, "[{0}:{1}] Failed to obtain LoadLibraryA pointer", new Object[]{
                    GetProcAddress.LIBRARY, GetProcAddress.NATIVE_NAME
            });
            return InjectionResult.FAIL;
        }

        final MemorySegment dllPath = arena.allocateFrom(path);
        final MemorySegment allocatedBaseAddress = caller.call(
                VirtualAllocEx.builder()
                        .processHandle(processHandle)
                        .size(dllPath.byteSize())
                        .allocationType(AllocationType.MEM_RESERVE | AllocationType.MEM_COMMIT)
                        .memoryProtection(MemoryProtection.PAGE_READWRITE)
                        .build()
        );

        if (allocatedBaseAddress.address() == 0) {
            LOGGER.log(Level.SEVERE, "[{0}:{1}] Failed to allocate memory in process with PID '{2}'", new Object[]{
                    VirtualAllocEx.LIBRARY, VirtualAllocEx.NATIVE_NAME, pid
            });
            return InjectionResult.FAIL;
        }

        final MemorySegment bytesWritten = arena.allocate(SIZE_T.byteSize());
        boolean success = caller.call(
                WriteProcessMemory.builder()
                        .processHandle(processHandle)
                        .baseAddressPointer(allocatedBaseAddress)
                        .bufferPointer(dllPath)
                        .size(dllPath.byteSize())
                        .bytesWrittenOutputPointer(bytesWritten)
                        .build()
        ) > 0;

        if (!success) {
            LOGGER.log(Level.SEVERE, "[{0}:{1}] Failed to write memory in process with PID '{2}'", new Object[]{
                    WriteProcessMemory.LIBRARY, WriteProcessMemory.NATIVE_NAME, pid
            });
            return InjectionResult.FAIL;
        }

        final MemorySegment remoteThreadHandle = (MemorySegment) caller.call(
                CreateRemoteThread.builder()
                        .processHandle(processHandle)
                        .threadStartRoutinePointer(loadLibraryPointer)
                        .parameterPointer(allocatedBaseAddress)
                        .build()
        );

        if (remoteThreadHandle.address() == 0) {
            LOGGER.log(Level.SEVERE, "[{0}:{1}] Failed obtain remote thread handle", new Object[]{
                    CreateRemoteThread.LIBRARY, CreateRemoteThread.NATIVE_NAME
            });
            return InjectionResult.FAIL;
        }

        int result = (int) caller.call(
                WaitForSingleObject.builder()
                        .handle(remoteThreadHandle)
                        .timeoutMillis(WaitTime.INFINITE)
                        .build()
        );

        success = (int) caller.call(
                VirtualFreeEx.builder()
                        .processHandle(processHandle)
                        .addressPointer(allocatedBaseAddress)
                        .freeType(FreeType.MEM_RELEASE)
                        .build()
        ) > 0;

        if (!success) {
            LOGGER.log(Level.SEVERE, "[{0}:{1}] Failed to free memory in process with PID '{2}'", new Object[]{
                    VirtualFreeEx.LIBRARY, VirtualFreeEx.NATIVE_NAME, pid
            });
            return InjectionResult.FAIL;
        }

        success = (int) caller.call(CloseHandle.ofHandle(remoteThreadHandle)) > 0;
        if (!success) {
            LOGGER.log(Level.SEVERE, "[{0}:{1}] Failed to close remote thread handle", new Object[]{
                    CloseHandle.LIBRARY, CloseHandle.NATIVE_NAME
            });
            return InjectionResult.FAIL;
        }

        success = (int) caller.call(CloseHandle.ofHandle(processHandle)) > 0;
        if (!success) {
            LOGGER.log(Level.SEVERE, "[{0}:{1}] Failed to close remote thread handle", new Object[]{
                    CloseHandle.LIBRARY, CloseHandle.NATIVE_NAME
            });
            return InjectionResult.FAIL;
        }

        return InjectionResult.SUCCESS;

    }

}
