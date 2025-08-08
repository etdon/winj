package com.etdon.winj.facade.hack.spawn;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.function.NativeCaller;
import com.etdon.winj.common.NativeContext;
import com.etdon.winj.constant.ProcessAttributeValue;
import com.etdon.winj.constant.ProcessCreateState;
import com.etdon.winj.constant.ProcessCreationMitigationPolicy;
import com.etdon.winj.constant.UserProcessParametersFlag;
import com.etdon.winj.constant.memory.HeapAllocationFlag;
import com.etdon.winj.constant.process.ProcessAccessRight;
import com.etdon.winj.constant.thread.ThreadAccessRight;
import com.etdon.winj.function.kernel32.CloseHandle;
import com.etdon.winj.function.kernel32.GetLastError;
import com.etdon.winj.function.kernel32.GetProcessHeap;
import com.etdon.winj.function.ntdll.*;
import com.etdon.winj.type.*;
import com.etdon.winj.util.Flag;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.AddressLayout;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

import static com.etdon.winj.type.constant.NativeDataType.*;
import static com.etdon.winj.type.constant.NativeDataType.HANDLE;

/**
 * Used to spawn a child process with a spoofed parent, spoofed command line, spoofed current directory, blocked
 * loading for non-Microsoft signed DLLs and Arbitrary Code Guard (ACG).
 * <p>
 * TODO: Customization regarding (security) features, logging, state codes.
 */
public class ProtectedChildProcess extends Spawner {

    private static final Logger LOGGER = Logger.getLogger(ProtectedChildProcess.class.getName());

    /**
     * The process id of the parent.
     */
    private final int parentPid;

    /**
     * The target path.
     * <p>
     * Example: C:\Windows\System32\mmc.exe
     */
    private final String path;

    private ProtectedChildProcess(final Builder builder) {

        super(builder.nativeContext);

        this.parentPid = builder.parentPid;
        this.path = builder.path;

    }

    @Override
    public void spawn() throws Throwable {

        if (!Files.exists(Path.of(path))) {
            //
            return;
        }

        final String nativePath = "\\??\\".concat(this.path);
        final String directoryPath = Path.of(this.path).getParent().toString();

        final Arena arena = super.nativeContext.getArena();
        final NativeCaller caller = super.nativeContext.getCaller();
        final MemorySegment ntImagePath = arena.allocate(UnicodeString.UNICODE_STRING.byteSize());
        caller.call(
                RtlInitUnicodeString.builder()
                        .destinationStringPointer(ntImagePath)
                        .sourceStringPointer(arena.allocateFrom(nativePath, StandardCharsets.UTF_16LE))
                        .build()
        );

        final MemorySegment commandLine = arena.allocate(UnicodeString.UNICODE_STRING.byteSize());
        caller.call(
                RtlInitUnicodeString.builder()
                        .destinationStringPointer(commandLine)
                        .sourceStringPointer(arena.allocateFrom(this.path, StandardCharsets.UTF_16LE))
                        .build()
        );

        final MemorySegment currentDirectory = arena.allocate(UnicodeString.UNICODE_STRING.byteSize());
        caller.call(
                RtlInitUnicodeString.builder()
                        .destinationStringPointer(currentDirectory)
                        .sourceStringPointer(arena.allocateFrom(directoryPath, StandardCharsets.UTF_16LE))
                        .build()
        );

        final MemorySegment processParametersPointer = arena.allocate(AddressLayout.ADDRESS);
        int ntStatus = (int) caller.call(
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
                .uniqueProcess(this.parentPid)
                .build()
                .createMemorySegment(arena);
        ntStatus = (int) caller.call(
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
        final MemorySegment heapHandle = (MemorySegment) caller.call(GetProcessHeap.getInstance());
        final MemorySegment attributeListPointer = (MemorySegment) caller.call(
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
        ntStatus = (int) caller.call(
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

        boolean success = (int) caller.call(CloseHandle.ofHandle(parentHandle)) > 0;
        if (!success) {
            System.out.println("CloseHandle Error: " + caller.call(GetLastError.getInstance()));
        }

        success = (int) caller.call(
                RtlFreeHeap.builder()
                        .heapHandle(heapHandle)
                        .baseAddress(attributeList)
                        .build()
        ) > 0;
        if (!success) {
            System.out.println("RtlFreeHeap Error: " + caller.call(GetLastError.getInstance()));
        }
        ntStatus = (int) caller.call(RtlDestroyProcessParameters.ofProcessParameters(processParameters));
        if (ntStatus != 0) {
            System.out.printf("RtlDestroyProcessParameters: NTSTATUS=0x%08X%n", ntStatus);
        }

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<ProtectedChildProcess> {

        private NativeContext nativeContext;
        private Integer parentPid;
        private String path;

        private Builder() {

        }

        public Builder nativeContext(@NotNull final NativeContext nativeContext) {

            this.nativeContext = nativeContext;
            return this;

        }

        public Builder parentPid(final int parentPid) {

            this.parentPid = parentPid;
            return this;

        }

        public Builder path(@NotNull final String path) {

            this.path = path;
            return this;

        }

        @NotNull
        @Override
        public ProtectedChildProcess build() {

            Preconditions.checkNotNull(this.nativeContext);
            Preconditions.checkNotNull(this.parentPid);
            Preconditions.checkNotNull(this.path);
            return new ProtectedChildProcess(this);

        }

    }

}
