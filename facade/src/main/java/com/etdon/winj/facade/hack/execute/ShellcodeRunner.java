package com.etdon.winj.facade.hack.execute;

import com.etdon.commons.annotation.RequiresTesting;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.function.NativeCaller;
import com.etdon.winj.common.NativeContext;
import com.etdon.winj.constant.memory.AllocationType;
import com.etdon.winj.constant.memory.MemoryProtection;
import com.etdon.winj.function.kernel32.CloseHandle;
import com.etdon.winj.function.kernel32.CreateThread;
import com.etdon.winj.function.kernel32.VirtualAlloc;
import com.etdon.winj.function.kernel32.WaitForSingleObject;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

@RequiresTesting
public class ShellcodeRunner {

    private static final byte[] RUNNER_INSTRUCTIONS = new byte[]{
            (byte) 0x48, (byte) 0x89, (byte) 0xC8,
            (byte) 0xFF, (byte) 0xD0,
            (byte) 0x31, (byte) 0xC0,
            (byte) 0xC3
    };

    private final NativeContext nativeContext;

    public ShellcodeRunner(@NotNull final NativeContext nativeContext) {

        Preconditions.checkNotNull(nativeContext);
        this.nativeContext = nativeContext;

    }

    public MemorySegment allocateRunner() throws Throwable {

        final NativeCaller caller = this.nativeContext.getCaller();
        final MemorySegment runnerMemory = ((MemorySegment) caller.call(
                VirtualAlloc.builder()
                        .size(RUNNER_INSTRUCTIONS.length)
                        .allocationType(AllocationType.MEM_COMMIT)
                        .memoryProtection(MemoryProtection.PAGE_EXECUTE_READWRITE)
                        .build()
        )).reinterpret(RUNNER_INSTRUCTIONS.length);
        MemorySegment.copy(RUNNER_INSTRUCTIONS, 0, runnerMemory, ValueLayout.JAVA_BYTE, 0, RUNNER_INSTRUCTIONS.length);

        return runnerMemory;

    }

    public void execute(@NotNull final MemorySegment runnerPointer, final byte[] shellcode) throws Throwable {

        final NativeCaller caller = this.nativeContext.getCaller();
        final MemorySegment shellcodeMemory = ((MemorySegment) caller.call(
                VirtualAlloc.builder()
                        .size(shellcode.length)
                        .allocationType(AllocationType.MEM_COMMIT)
                        .memoryProtection(MemoryProtection.PAGE_EXECUTE_READWRITE)
                        .build()
        )).reinterpret(shellcode.length);
        MemorySegment.copy(shellcode, 0, shellcodeMemory, ValueLayout.JAVA_BYTE, 0, shellcode.length);

        final MemorySegment threadHandle = (MemorySegment) caller.call(
                CreateThread.builder()
                        .threadStartRoutinePointer(runnerPointer)
                        .parameterPointer(shellcodeMemory)
                        .build()
        );

        caller.call(WaitForSingleObject.infinite(threadHandle));
        caller.call(CloseHandle.ofHandle(threadHandle));

    }

}
