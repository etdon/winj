package com.etdon.winj.function.ntdll;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.NativeDocumentation;
import com.etdon.jbinder.common.NativeName;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import com.etdon.winj.type.ClientId;
import com.etdon.winj.type.ObjectAttributes;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;

@NativeName(NtOpenProcess.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows-hardware/drivers/ddi/ntddk/nf-ntddk-ntopenprocess")
public final class NtOpenProcess extends NativeFunction {

    public static final String LIBRARY = Library.NTDLL;
    public static final String NATIVE_NAME = "NtOpenProcess";
    public static final FunctionDescriptor NT_OPEN_PROCESS_SIGNATURE = FunctionDescriptor.of(
            NTSTATUS,
            PHANDLE.withName("ProcessHandle"),
            ACCESS_MASK.withName("DesiredAccess"),
            ObjectAttributes.PCOBJECT_ATTRIBUTES.withName("ObjectAttributes"),
            ClientId.PCLIENT_ID.withName("ClientId")
    );

    /**
     * A pointer to a variable of type HANDLE. The ZwOpenProcess routine writes the process handle to the variable that
     * this parameter points to.
     */
    private final MemorySegment processHandleOutputPointer;

    /**
     * An ACCESS_MASK value that contains the access rights that the caller has requested to the process object.
     *
     * @see com.etdon.winj.constant.process.ProcessAccessRight
     */
    private final int desiredAccess;

    /**
     * A pointer to an OBJECT_ATTRIBUTES structure that specifies the attributes to apply to the process object handle.
     * The ObjectName field of this structure must be set to NULL. For more information, see the following Remarks
     * section.
     */
    private final MemorySegment objectAttributesPointer;

    /**
     * A pointer to a client ID that identifies the thread whose process is to be opened. This parameter must be a
     * non-NULL pointer to a valid client ID. For more information, see the following Remarks section.
     */
    private MemorySegment clientIdPointer = MemorySegment.NULL;

    private NtOpenProcess(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, NT_OPEN_PROCESS_SIGNATURE);

        this.processHandleOutputPointer = builder.processHandleOutputPointer;
        this.desiredAccess = builder.desiredAccess;
        this.objectAttributesPointer = builder.objectAttributesPointer;
        Conditional.executeIfNotNull(builder.clientIdPointer, () -> this.clientIdPointer = builder.clientIdPointer);

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.processHandleOutputPointer,
                this.desiredAccess,
                this.objectAttributesPointer,
                this.clientIdPointer
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<NtOpenProcess> {

        private MemorySegment processHandleOutputPointer;
        private Integer desiredAccess;
        private MemorySegment objectAttributesPointer;
        private MemorySegment clientIdPointer;

        public Builder processHandleOutputPointer(@NotNull final MemorySegment processHandleOutputPointer) {

            this.processHandleOutputPointer = processHandleOutputPointer;
            return this;

        }

        public Builder desiredAccess(final int desiredAccess) {

            this.desiredAccess = desiredAccess;
            return this;

        }

        public Builder objectAttributesPointer(@NotNull final MemorySegment objectAttributesPointer) {

            this.objectAttributesPointer = objectAttributesPointer;
            return this;

        }

        public Builder clientIdPointer(@NotNull final MemorySegment clientIdPointer) {

            this.clientIdPointer = clientIdPointer;
            return this;

        }

        @NotNull
        @Override
        public NtOpenProcess build() {

            Preconditions.checkNotNull(this.processHandleOutputPointer);
            Preconditions.checkNotNull(this.desiredAccess);
            Preconditions.checkNotNull(this.objectAttributesPointer);
            return new NtOpenProcess(this);

        }

    }

}
