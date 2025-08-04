package com.etdon.winj.demo;

import com.etdon.winj.WinJ;
import com.etdon.winj.common.NativeContext;
import com.etdon.winj.constant.*;
import com.etdon.winj.constant.memory.AllocationType;
import com.etdon.winj.constant.memory.FreeType;
import com.etdon.winj.constant.memory.HeapAllocationFlag;
import com.etdon.winj.constant.memory.MemoryProtection;
import com.etdon.winj.constant.process.ProcessAccessRight;
import com.etdon.winj.constant.thread.ThreadAccessRight;
import com.etdon.winj.facade.Window;
import com.etdon.winj.facade.WindowsAPI;
import com.etdon.winj.function.kernel32.*;
import com.etdon.winj.function.ntdll.*;
import com.etdon.winj.render.debug.queue.StringDebugRenderQueueItem;
import com.etdon.winj.type.*;
import com.etdon.winj.util.Flag;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.AddressLayout;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.charset.StandardCharsets;

import static com.etdon.winj.type.NativeDataType.*;

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

            final MemorySegment ntImagePath = arena.allocate(UnicodeString.UNICODE_STRING.byteSize());
            nativeContext.getCaller().call(
                    RtlInitUnicodeString.builder()
                            .destinationStringPointer(ntImagePath)
                            .sourceStringPointer(arena.allocateFrom("\\??\\C:\\Windows\\System32\\mmc.exe", StandardCharsets.UTF_16LE))
                            .build()
            );

            final MemorySegment commandLine = arena.allocate(UnicodeString.UNICODE_STRING.byteSize());
            nativeContext.getCaller().call(
                    RtlInitUnicodeString.builder()
                            .destinationStringPointer(commandLine)
                            .sourceStringPointer(arena.allocateFrom("C:\\Windows\\System32\\mmc.exe", StandardCharsets.UTF_16LE))
                            .build()
            );

            final MemorySegment currentDirectory = arena.allocate(UnicodeString.UNICODE_STRING.byteSize());
            nativeContext.getCaller().call(
                    RtlInitUnicodeString.builder()
                            .destinationStringPointer(currentDirectory)
                            .sourceStringPointer(arena.allocateFrom("C:\\Windows\\System32", StandardCharsets.UTF_16LE))
                            .build()
            );

            final MemorySegment processParametersPointer = arena.allocate(AddressLayout.ADDRESS);
            int ntStatus = (int) nativeContext.getCaller().call(
                    RtlCreateProcessParametersEx.builder()
                            .processParametersPointerOutput(processParametersPointer)
                            .imagePathNamePointer(ntImagePath)
                            .currentDirectoryPointer(currentDirectory)
                            .commandLinePointer(commandLine)
                            .flags(UserProcessParametersFlag.RTL_USER_PROC_PARAMS_NORMALIZED)
                            .build()
            );
            if (ntStatus != 0) {
                System.out.printf("RtlCreateProcessParametersEx: NTSTATUS=0x%08X%n", ntStatus);
            }
            final MemorySegment processParameters = processParametersPointer.get(ValueLayout.ADDRESS, 0);

            final ProcessCreateInfo processCreateInfo = ProcessCreateInfo.builder()
                    .size(ProcessCreateInfo.PS_CREATE_INFO.byteSize())
                    .state(ProcessCreateState.PS_CREATE_INITIAL_STATE)
                    .info(ProcessCreateInitialState.empty())
                    .build();
            final MemorySegment processCreateInfoPointer = processCreateInfo.createMemorySegment(arena);

            final MemorySegment objectAttributes = arena.allocate(ObjectAttributes.OBJECT_ATTRIBUTES.byteSize());
            final MemorySegment parentHandlePointer = arena.allocate(HANDLE.byteSize());
            final MemorySegment clientId = ClientId.builder()
                    .uniqueProcess(27676)
                    .build()
                    .createMemorySegment(arena);
            ntStatus = (int) nativeContext.getCaller().call(
                    NtOpenProcess.builder()
                            .processHandleOutputPointer(parentHandlePointer)
                            .desiredAccess(ThreadAccessRight.THREAD_ALL_ACCESS)
                            .objectAttributesPointer(objectAttributes)
                            .clientIdPointer(clientId)
                            .build()
            );
            if (ntStatus != 0) {
                System.out.printf("NtOpenProcess: NTSTATUS=0x%08X%n", ntStatus);
            }
            final MemorySegment parentHandle = parentHandlePointer.get(ValueLayout.ADDRESS, 0);

            final int attributeCount = 3;
            final long totalLength = SIZE_T.byteSize() + (attributeCount * ProcessAttribute.PS_ATTRIBUTE.byteSize());
            final MemorySegment heapHandle = (MemorySegment) nativeContext.getCaller().call(GetProcessHeap.getInstance());
            final MemorySegment attributeListPointer = (MemorySegment) nativeContext.getCaller().call(
                    RtlAllocateHeap.builder()
                            .heapHandle(heapHandle)
                            .flags(HeapAllocationFlag.HEAP_ZERO_MEMORY)
                            .size(totalLength)
                            .build()
            );
            final MemorySegment attributeList = attributeListPointer.reinterpret(totalLength);

            final UnicodeString imagePath = new UnicodeString(arena, ntImagePath);
            final MemorySegment policy = arena.allocateFrom(ValueLayout.JAVA_LONG,
                    Flag.combine(
                            ProcessCreationMitigationPolicy.PROCESS_CREATION_MITIGATION_POLICY_BLOCK_NON_MICROSOFT_BINARIES_ALWAYS_ON,
                            ProcessCreationMitigationPolicy.PROCESS_CREATION_MITIGATION_POLICY_PROHIBIT_DYNAMIC_CODE_ALWAYS_ON
                    )
            );
            final ProcessAttributeList processAttributeList = ProcessAttributeList.builder()
                    .totalLength(totalLength)
                    .attribute(
                            ProcessAttribute.builder()
                                    .attribute(ProcessAttributeValue.PS_ATTRIBUTE_IMAGE_NAME)
                                    .size(imagePath.getLength())
                                    .value(imagePath.getBuffer())
                                    .build()
                                    .createMemorySegment(arena)
                    )
                    .attribute(
                            ProcessAttribute.builder()
                                    .attribute(ProcessAttributeValue.PS_ATTRIBUTE_PARENT_PROCESS)
                                    .size(HANDLE.byteSize())
                                    .value(parentHandle)
                                    .build()
                                    .createMemorySegment(arena)
                    )
                    .attribute(
                            ProcessAttribute.builder()
                                    .attribute(ProcessAttributeValue.PS_ATTRIBUTE_MITIGATION_OPTIONS)
                                    .size(DWORD64.byteSize())
                                    .value(policy)
                                    .build()
                                    .createMemorySegment(arena)
                    )
                    .build();
            MemorySegment.copy(processAttributeList.createMemorySegment(arena), 0, attributeList, 0, totalLength);

            final MemorySegment processHandle = arena.allocate(HANDLE.byteSize());
            final MemorySegment threadHandle = arena.allocate(HANDLE.byteSize());
            ntStatus = (int) nativeContext.getCaller().call(
                    NtCreateUserProcess.builder()
                            .processHandleOutputPointer(processHandle)
                            .threadHandleOutputPointer(threadHandle)
                            .processDesiredAccess(ProcessAccessRight.PROCESS_ALL_ACCESS)
                            .threadDesiredAccess(ThreadAccessRight.THREAD_ALL_ACCESS)
                            .processParameters(processParameters)
                            .createInfo(processCreateInfoPointer)
                            .attributeList(attributeList)
                            .build()
            );
            if (ntStatus != 0) {
                System.out.printf("NtCreateUserProcess: NTSTATUS=0x%08X%n", ntStatus);
            }

            boolean success = (int) nativeContext.getCaller().call(CloseHandle.ofHandle(parentHandle)) > 0;
            if (!success) {
                System.out.println("CloseHandle Error: " + nativeContext.getCaller().call(GetLastError.getInstance()));
            }

            success = (int) nativeContext.getCaller().call(
                    RtlFreeHeap.builder()
                            .heapHandle(heapHandle)
                            .baseAddress(attributeList)
                            .build()
            ) > 0;
            if (!success) {
                System.out.println("RtlFreeHeap Error: " + nativeContext.getCaller().call(GetLastError.getInstance()));
            }
            ntStatus = (int) nativeContext.getCaller().call(RtlDestroyProcessParameters.ofProcessParameters(processParameters));
            if (ntStatus != 0) {
                System.out.printf("RtlDestroyProcessParameters: NTSTATUS=0x%08X%n", ntStatus);
            }

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

        success = (int) nativeContext.getCaller().call(CloseHandle.ofHandle(remoteThreadHandle)) > 0;
        if (!success) {
            //
        }

        success = (int) nativeContext.getCaller().call(CloseHandle.ofHandle(processHandle)) > 0;
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
