package com.etdon.winj.function.ntdll;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import com.etdon.winj.constant.UserProcessParametersFlag;
import com.etdon.winj.type.UnicodeString;
import com.etdon.winj.type.UserProcessParameters;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;

public final class RtlCreateProcessParametersEx extends NativeFunction {

    public static final String LIBRARY = Library.NTDLL;
    public static final String NATIVE_NAME = "RtlCreateProcessParametersEx";
    public static final FunctionDescriptor RTL_CREATE_PROCESS_PARAMETERS_EX_SIGNATURE = FunctionDescriptor.of(
            DWORD,
            UserProcessParameters.PRTL_USER_PROCESS_PARAMETERS.withName("ProcessParameters"),
            UnicodeString.PCUNICODE_STRING.withName("ImagePathName"),
            UnicodeString.PCUNICODE_STRING.withName("DllPath"),
            UnicodeString.PCUNICODE_STRING.withName("CurrentDirectory"),
            UnicodeString.PCUNICODE_STRING.withName("CommandLine"),
            PVOID.withName("Environment"),
            UnicodeString.PCUNICODE_STRING.withName("WindowTitle"),
            UnicodeString.PCUNICODE_STRING.withName("DesktopInfo"),
            UnicodeString.PCUNICODE_STRING.withName("ShellInfo"),
            UnicodeString.PCUNICODE_STRING.withName("RuntimeData"),
            ULONG.withName("Flags")
    );

    private final MemorySegment processParametersPointerOutput;

    private final MemorySegment imagePathNamePointer;

    private MemorySegment dllPathPointer = MemorySegment.NULL;

    private MemorySegment currentDirectoryPointer = MemorySegment.NULL;

    private MemorySegment commandLinePointer = MemorySegment.NULL;

    private MemorySegment environmentPointer = MemorySegment.NULL;

    private MemorySegment windowTitlePointer = MemorySegment.NULL;

    private MemorySegment desktopInfoPointer = MemorySegment.NULL;

    private MemorySegment shellInfoPointer = MemorySegment.NULL;

    private MemorySegment runtimeDataPointer = MemorySegment.NULL;

    /**
     * {@link com.etdon.winj.constant.UserProcessParametersFlag}
     */
    private int flags = UserProcessParametersFlag.RTL_USER_PROC_PARAMS_NORMALIZED;

    private RtlCreateProcessParametersEx(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, RTL_CREATE_PROCESS_PARAMETERS_EX_SIGNATURE);

        this.processParametersPointerOutput = builder.processParametersPointerOutput;
        this.imagePathNamePointer = builder.imagePathNamePointer;
        Conditional.executeIfNotNull(builder.dllPathPointer, () -> this.dllPathPointer = builder.dllPathPointer);
        Conditional.executeIfNotNull(builder.currentDirectoryPointer, () -> this.currentDirectoryPointer = builder.currentDirectoryPointer);
        Conditional.executeIfNotNull(builder.commandLinePointer, () -> this.commandLinePointer = builder.commandLinePointer);
        Conditional.executeIfNotNull(builder.environmentPointer, () -> this.environmentPointer = builder.environmentPointer);
        Conditional.executeIfNotNull(builder.windowTitlePointer, () -> this.windowTitlePointer = builder.windowTitlePointer);
        Conditional.executeIfNotNull(builder.desktopInfoPointer, () -> this.desktopInfoPointer = builder.desktopInfoPointer);
        Conditional.executeIfNotNull(builder.shellInfoPointer, () -> this.shellInfoPointer = builder.shellInfoPointer);
        Conditional.executeIfNotNull(builder.runtimeDataPointer, () -> this.runtimeDataPointer = builder.runtimeDataPointer);
        Conditional.executeIfNotNull(builder.flags, () -> this.flags = builder.flags);

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.processParametersPointerOutput,
                this.imagePathNamePointer,
                this.dllPathPointer,
                this.currentDirectoryPointer,
                this.commandLinePointer,
                this.environmentPointer,
                this.windowTitlePointer,
                this.desktopInfoPointer,
                this.shellInfoPointer,
                this.runtimeDataPointer,
                this.flags
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<RtlCreateProcessParametersEx> {

        private MemorySegment processParametersPointerOutput;
        private MemorySegment imagePathNamePointer;
        private MemorySegment dllPathPointer;
        private MemorySegment currentDirectoryPointer;
        private MemorySegment commandLinePointer;
        private MemorySegment environmentPointer;
        private MemorySegment windowTitlePointer;
        private MemorySegment desktopInfoPointer;
        private MemorySegment shellInfoPointer;
        private MemorySegment runtimeDataPointer;
        private Integer flags;

        public Builder processParametersPointerOutput(@NotNull final MemorySegment processParametersPointerOutput) {

            this.processParametersPointerOutput = processParametersPointerOutput;
            return this;

        }

        public Builder imagePathNamePointer(@NotNull final MemorySegment imagePathNamePointer) {

            this.imagePathNamePointer = imagePathNamePointer;
            return this;

        }

        public Builder dllPathPointer(@NotNull final MemorySegment dllPathPointer) {

            this.dllPathPointer = dllPathPointer;
            return this;

        }

        public Builder currentDirectoryPointer(@NotNull final MemorySegment currentDirectoryPointer) {

            this.currentDirectoryPointer = currentDirectoryPointer;
            return this;

        }

        public Builder commandLinePointer(@NotNull final MemorySegment commandLinePointer) {

            this.commandLinePointer = commandLinePointer;
            return this;

        }

        public Builder environmentPointer(@NotNull final MemorySegment environmentPointer) {

            this.environmentPointer = environmentPointer;
            return this;

        }

        public Builder windowTitlePointer(@NotNull final MemorySegment windowTitlePointer) {

            this.windowTitlePointer = windowTitlePointer;
            return this;

        }

        public Builder desktopInfoPointer(@NotNull final MemorySegment desktopInfoPointer) {

            this.desktopInfoPointer = desktopInfoPointer;
            return this;

        }

        public Builder shellInfoPointer(@NotNull final MemorySegment shellInfoPointer) {

            this.shellInfoPointer = shellInfoPointer;
            return this;

        }

        public Builder runtimeDataPointer(@NotNull final MemorySegment runtimeDataPointer) {

            this.runtimeDataPointer = runtimeDataPointer;
            return this;

        }

        public Builder flags(final int flags) {

            this.flags = flags;
            return this;

        }

        @NotNull
        @Override
        public RtlCreateProcessParametersEx build() {

            Preconditions.checkNotNull(this.processParametersPointerOutput);
            Preconditions.checkNotNull(this.imagePathNamePointer);
            return new RtlCreateProcessParametersEx(this);

        }

    }

}
