package com.etdon.winj.facade.hack.spawn;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.util.Numbers;
import com.etdon.jbinder.function.NativeCaller;
import com.etdon.winj.common.NativeContext;
import com.etdon.winj.constant.ProcessAttributeValue;
import com.etdon.winj.constant.ProcessCreateState;
import com.etdon.winj.constant.ProcessCreationMitigationPolicy;
import com.etdon.winj.constant.UserProcessParametersFlag;
import com.etdon.winj.constant.memory.HeapAllocationFlag;
import com.etdon.winj.constant.process.ProcessAccessRight;
import com.etdon.winj.constant.thread.ThreadAccessRight;
import com.etdon.winj.function.kernel32.handle.CloseHandle;
import com.etdon.winj.function.kernel32.error.GetLastError;
import com.etdon.winj.function.kernel32.heap.GetProcessHeap;
import com.etdon.winj.function.ntdll.*;
import com.etdon.winj.type.*;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.AddressLayout;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.etdon.winj.type.constant.NativeDataType.*;
import static com.etdon.winj.type.constant.NativeDataType.HANDLE;

/**
 * Used to spawn a child process with a spoofed parent, spoofed command line, spoofed current directory, blocked
 * loading for non-Microsoft signed DLLs and Arbitrary Code Guard (ACG).
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
    private final long mitigationOptions;

    private ProtectedChildProcess(final Builder builder) {

        super(builder.nativeContext);

        this.parentPid = builder.parentPid;
        this.path = builder.path;
        this.mitigationOptions = builder.mitigationOptions;

    }

    @Override
    public int spawn() throws Throwable {

        if (!Files.exists(Path.of(path))) {
            LOGGER.log(Level.SEVERE, "Failed to find file under '{0}'", path);
            return SpawnerResult.FAIL_FILE_NOT_FOUND;
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
        int ntStatus = caller.call(
                RtlCreateProcessParametersEx.builder()
                        .processParametersPointerOutput(processParametersPointer)
                        .imagePathNamePointer(ntImagePath)
                        .currentDirectoryPointer(currentDirectory)
                        .commandLinePointer(commandLine)
                        .flags(UserProcessParametersFlag.RTL_USER_PROC_PARAMS_NORMALIZED)
                        .build()
        );
        if (ntStatus != 0) {
            LOGGER.log(Level.SEVERE, "Failed to create process parameters, error code: {0}", Numbers.toHexString(ntStatus));
            return SpawnerResult.FAIL_PROCESS_PARAMETER_CREATION;
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
        ntStatus = caller.call(
                NtOpenProcess.builder()
                        .processHandleOutputPointer(parentHandlePointer)
                        .desiredAccess(ThreadAccessRight.THREAD_ALL_ACCESS)
                        .objectAttributesPointer(objectAttributes)
                        .clientIdPointer(clientId)
                        .build()
        );
        if (ntStatus != 0) {
            LOGGER.log(Level.SEVERE, "Failed to open parent process, error code: {0}", Numbers.toHexString(ntStatus));
            return SpawnerResult.FAIL_OPEN_PARENT_PROCESS;
        }
        final MemorySegment parentHandle = parentHandlePointer.get(ValueLayout.ADDRESS, 0);

        final int attributeCount = 3;
        final long totalLength = SIZE_T.byteSize() + (attributeCount * ProcessAttribute.PS_ATTRIBUTE.byteSize());
        final MemorySegment heapHandle = caller.call(GetProcessHeap.getInstance());
        final MemorySegment attributeListPointer = caller.call(
                RtlAllocateHeap.builder()
                        .heapHandle(heapHandle)
                        .flags(HeapAllocationFlag.HEAP_ZERO_MEMORY)
                        .size(totalLength)
                        .build()
        );
        final MemorySegment attributeList = attributeListPointer.reinterpret(totalLength);

        final UnicodeString imagePath = new UnicodeString(arena, ntImagePath);
        final MemorySegment policy = arena.allocateFrom(ValueLayout.JAVA_LONG, this.mitigationOptions);
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
        ntStatus = caller.call(
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
            LOGGER.log(Level.SEVERE, "Failed to create user process, error code: {0}", Numbers.toHexString(ntStatus));
            return SpawnerResult.FAIL_CREATE_USER_PROCESS;
        }

        boolean success = caller.call(CloseHandle.ofHandle(parentHandle)) > 0;
        if (!success) {
            LOGGER.log(Level.SEVERE, "Failed to close parent handle, error code: {0}", Numbers.toHexString(ntStatus));
            return SpawnerResult.FAIL_CLOSE_PARENT_HANDLE;
        }

        success = caller.call(
                RtlFreeHeap.builder()
                        .heapHandle(heapHandle)
                        .baseAddress(attributeList)
                        .build()
        ) > 0;
        if (!success) {
            LOGGER.log(Level.SEVERE, "Failed to free attribute list memory, error code: {0}", Numbers.toHexString(caller.call(GetLastError.getInstance())));
            return SpawnerResult.FAIL_FREE_MEMORY;
        }

        ntStatus = caller.call(RtlDestroyProcessParameters.ofProcessParameters(processParameters));
        if (ntStatus != 0) {
            LOGGER.log(Level.SEVERE, "Failed to destroy process parameters, error code: {0}", Numbers.toHexString(ntStatus));
            return SpawnerResult.FAIL_PROCESS_PARAMETER_DESTRUCTION;
        }

        return SpawnerResult.SUCCESS;

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<ProtectedChildProcess> {

        private NativeContext nativeContext;
        private Integer parentPid;
        private String path;
        private long mitigationOptions;

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

        public Builder mitigationOption(final long mitigationOption) {

            this.mitigationOptions |= mitigationOption;
            return this;

        }

        public Builder blockUnsignedDlls() {

            this.mitigationOptions |= ProcessCreationMitigationPolicy.PROCESS_CREATION_MITIGATION_POLICY_BLOCK_NON_MICROSOFT_BINARIES_ALWAYS_ON;
            return this;

        }

        public Builder acg() {

            this.mitigationOptions |= ProcessCreationMitigationPolicy.PROCESS_CREATION_MITIGATION_POLICY_PROHIBIT_DYNAMIC_CODE_ALWAYS_ON;
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
