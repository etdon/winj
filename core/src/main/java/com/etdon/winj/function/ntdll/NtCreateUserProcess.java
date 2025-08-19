package com.etdon.winj.function.ntdll;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.NativeName;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import com.etdon.winj.type.ObjectAttributes;
import com.etdon.winj.type.ProcessAttributeList;
import com.etdon.winj.type.ProcessCreateInfo;
import com.etdon.winj.type.UserProcessParameters;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;

@NativeName(NtCreateUserProcess.NATIVE_NAME)
public final class NtCreateUserProcess extends NativeFunction<Integer> {

    public static final String LIBRARY = Library.NTDLL;
    public static final String NATIVE_NAME = "NtCreateUserProcess";
    public static final FunctionDescriptor NT_CREATE_USER_PROCESS_SIGNATURE = FunctionDescriptor.of(
            DWORD,
            HANDLE.withName("ProcessHandle"),
            HANDLE.withName("ThreadHandle"),
            ACCESS_MASK.withName("ProcessDesiredAccess"),
            ACCESS_MASK.withName("ThreadDesiredAccess"),
            ObjectAttributes.PCOBJECT_ATTRIBUTES.withName("ProcessObjectAttributes"),
            ObjectAttributes.PCOBJECT_ATTRIBUTES.withName("ThreadObjectAttributes"),
            ULONG.withName("ProcessFlags"),
            ULONG.withName("ThreadFlags"),
            UserProcessParameters.PRTL_USER_PROCESS_PARAMETERS.withName("ProcessParameters"),
            ProcessCreateInfo.PPS_CREATE_INFO.withName("CreateInfo"),
            ProcessAttributeList.PPS_ATTRIBUTE_LIST.withName("AttributeList")
    );

    /**
     * A pointer to a handle that receives the process object handle.
     */
    private final MemorySegment processHandleOutputPointer;

    /**
     * A pointer to a handle that receives the thread object handle.
     */
    private final MemorySegment threadHandleOutputPointer;

    /**
     * The access rights desired for the process object.
     *
     * @see com.etdon.winj.constant.process.ProcessAccessRight
     */
    private final int processDesiredAccess;

    /**
     * The access rights desired for the thread object.
     *
     * @see com.etdon.winj.constant.thread.ThreadAccessRight
     */
    private final int threadDesiredAccess;

    /**
     * Optional. A pointer to an OBJECT_ATTRIBUTES structure that specifies the attributes of the new process.
     */
    private MemorySegment processObjectAttributes = MemorySegment.NULL;

    /**
     * Optional. A pointer to an OBJECT_ATTRIBUTES structure that specifies the attributes of the new thread.
     */
    private MemorySegment threadObjectAttributes = MemorySegment.NULL;

    /**
     * Flags that control the creation of the process. These flags are defined as PROCESS_CREATE_FLAGS_*.
     *
     * @see com.etdon.winj.constant.ProcessCreateFlag
     */
    private int processFlags;

    /**
     * Flags that control the creation of the thread. These flags are defined as THREAD_CREATE_FLAGS_*.
     *
     * @see com.etdon.winj.constant.ThreadCreateFlag
     */
    private int threadFlags;

    /**
     * Optional. A pointer to an RTL_USER_PROCESS_PARAMETERS structure that specifies the parameters for the new
     * process.
     *
     * @see UserProcessParameters
     */
    private MemorySegment processParameters = MemorySegment.NULL;

    /**
     * A pointer to a PS_CREATE_INFO structure that specifies additional information for the process creation.
     *
     * @see ProcessCreateInfo
     */
    private final MemorySegment createInfo;

    /**
     * Optional. A pointer to a list of attributes for the process and thread.
     *
     * @see ProcessAttributeList
     */
    private MemorySegment attributeList = MemorySegment.NULL;

    private NtCreateUserProcess(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, NT_CREATE_USER_PROCESS_SIGNATURE);

        this.processHandleOutputPointer = builder.processHandleOutputPointer;
        this.threadHandleOutputPointer = builder.threadHandleOutputPointer;
        this.processDesiredAccess = builder.processDesiredAccess;
        this.threadDesiredAccess = builder.threadDesiredAccess;
        Conditional.executeIfNotNull(builder.processObjectAttributes, () -> this.processObjectAttributes = builder.processObjectAttributes);
        Conditional.executeIfNotNull(builder.threadObjectAttributes, () -> this.threadObjectAttributes = builder.threadObjectAttributes);
        Conditional.executeIfNotNull(builder.processFlags, () -> this.processFlags = builder.processFlags);
        Conditional.executeIfNotNull(builder.threadFlags, () -> this.threadFlags = builder.threadFlags);
        Conditional.executeIfNotNull(builder.processParameters, () -> this.processParameters = builder.processParameters);
        this.createInfo = builder.createInfo;
        Conditional.executeIfNotNull(builder.attributeList, () -> this.attributeList = builder.attributeList);

    }

    @Override
    public Integer call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return (Integer) super.obtainHandle(linker, symbolLookup).invoke(
                this.processHandleOutputPointer,
                this.threadHandleOutputPointer,
                this.processDesiredAccess,
                this.threadDesiredAccess,
                this.processObjectAttributes,
                this.threadObjectAttributes,
                this.processFlags,
                this.threadFlags,
                this.processParameters,
                this.createInfo,
                this.attributeList
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<NtCreateUserProcess> {

        private MemorySegment processHandleOutputPointer;
        private MemorySegment threadHandleOutputPointer;
        private Integer processDesiredAccess;
        private Integer threadDesiredAccess;
        private MemorySegment processObjectAttributes;
        private MemorySegment threadObjectAttributes;
        private Integer processFlags;
        private Integer threadFlags;
        private MemorySegment processParameters;
        private MemorySegment createInfo;
        private MemorySegment attributeList;

        public Builder processHandleOutputPointer(@NotNull final MemorySegment processHandleOutputPointer) {

            this.processHandleOutputPointer = processHandleOutputPointer;
            return this;

        }

        public Builder threadHandleOutputPointer(@NotNull final MemorySegment threadHandleOutputPointer) {

            this.threadHandleOutputPointer = threadHandleOutputPointer;
            return this;

        }

        public Builder processDesiredAccess(final int processDesiredAccess) {

            this.processDesiredAccess = processDesiredAccess;
            return this;

        }

        public Builder threadDesiredAccess(final int threadDesiredAccess) {

            this.threadDesiredAccess = threadDesiredAccess;
            return this;

        }

        public Builder processObjectAttributes(@NotNull final MemorySegment processObjectAttributes) {

            this.processObjectAttributes = processObjectAttributes;
            return this;

        }

        public Builder threadObjectAttributes(@NotNull final MemorySegment threadObjectAttributes) {

            this.threadObjectAttributes = threadObjectAttributes;
            return this;

        }

        public Builder processFlags(final int processFlags) {

            this.processFlags = processFlags;
            return this;

        }

        public Builder threadFlags(final int threadFlags) {

            this.threadFlags = threadFlags;
            return this;

        }

        public Builder processParameters(@NotNull final MemorySegment processParameters) {

            this.processParameters = processParameters;
            return this;

        }

        public Builder createInfo(@NotNull final MemorySegment createInfo) {

            this.createInfo = createInfo;
            return this;

        }

        public Builder attributeList(@NotNull final MemorySegment attributeList) {

            this.attributeList = attributeList;
            return this;

        }

        @NotNull
        @Override
        public NtCreateUserProcess build() {

            Preconditions.checkNotNull(this.processHandleOutputPointer);
            Preconditions.checkNotNull(this.threadHandleOutputPointer);
            Preconditions.checkNotNull(this.processDesiredAccess);
            Preconditions.checkNotNull(this.threadDesiredAccess);
            Preconditions.checkNotNull(this.createInfo);
            return new NtCreateUserProcess(this);

        }

    }

}
