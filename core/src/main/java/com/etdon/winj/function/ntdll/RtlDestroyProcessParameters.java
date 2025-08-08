package com.etdon.winj.function.ntdll;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import com.etdon.winj.type.UserProcessParameters;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;

public final class RtlDestroyProcessParameters extends NativeFunction {

    public static final String LIBRARY = Library.NTDLL;
    public static final String NATIVE_NAME = "RtlDestroyProcessParameters";
    public static final FunctionDescriptor RTL_DESTROY_PROCESS_PARAMETERS = FunctionDescriptor.of(
            DWORD,
            UserProcessParameters.PRTL_USER_PROCESS_PARAMETERS.withName("ProcessParameters")
    );

    private final MemorySegment processParameters;

    private RtlDestroyProcessParameters(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, RTL_DESTROY_PROCESS_PARAMETERS);

        this.processParameters = builder.processParameters;

    }

    private RtlDestroyProcessParameters(final MemorySegment processParameters) {

        super(LIBRARY, NATIVE_NAME, RTL_DESTROY_PROCESS_PARAMETERS);

        this.processParameters = processParameters;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(this.processParameters);

    }

    public static RtlDestroyProcessParameters ofProcessParameters(@NotNull final MemorySegment processParameters) {

        return new RtlDestroyProcessParameters(processParameters);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<RtlDestroyProcessParameters> {

        private MemorySegment processParameters;

        public Builder processParameters(@NotNull final MemorySegment processParameters) {

            this.processParameters = processParameters;
            return this;

        }

        @NotNull
        @Override
        public RtlDestroyProcessParameters build() {

            Preconditions.checkNotNull(this.processParameters);
            return new RtlDestroyProcessParameters(this);

        }

    }

}
