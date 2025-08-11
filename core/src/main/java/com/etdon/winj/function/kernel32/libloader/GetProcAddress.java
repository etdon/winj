package com.etdon.winj.function.kernel32.libloader;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.NativeDocumentation;
import com.etdon.jbinder.common.NativeName;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;

/**
 * Retrieves the address of an exported function (also known as a procedure) or variable from the specified
 * dynamic-link library (DLL).
 */
@NativeName(GetProcAddress.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/libloaderapi/nf-libloaderapi-getprocaddress")
public final class GetProcAddress extends NativeFunction {

    public static final String LIBRARY = Library.KERNEL_32;
    public static final String NATIVE_NAME = "GetProcAddress";
    public static final FunctionDescriptor GET_PROC_ADDRESS_SIGNATURE = FunctionDescriptor.of(
            FARPROC,
            HMODULE.withName("hModule"),
            LPCSTR.withName("lpProcName")
    );

    /**
     * A handle to the DLL module that contains the function or variable.
     */
    @NativeName("hModule")
    private final MemorySegment moduleHandle;

    /**
     * The function or variable name, or the function's ordinal value. If this parameter is an ordinal value, it must be
     * in the low-order word; the high-order word must be zero.
     */
    @NativeName("lpProcName")
    private final MemorySegment localeProcessNamePointer;

    private GetProcAddress(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, GET_PROC_ADDRESS_SIGNATURE);

        this.moduleHandle = builder.moduleHandle;
        this.localeProcessNamePointer = builder.localeNamePointer;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(this.moduleHandle, this.localeProcessNamePointer);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<GetProcAddress> {

        private MemorySegment moduleHandle;
        private MemorySegment localeNamePointer;

        private Builder() {

        }

        public Builder moduleHandle(@NotNull final MemorySegment moduleHandle) {

            this.moduleHandle = moduleHandle;
            return this;

        }

        public Builder localeNamePointer(@NotNull final MemorySegment localeNamePointer) {

            this.localeNamePointer = localeNamePointer;
            return this;

        }

        @NotNull
        @Override
        public GetProcAddress build() {

            Preconditions.checkNotNull(this.moduleHandle);
            Preconditions.checkNotNull(this.localeNamePointer);
            return new GetProcAddress(this);

        }

    }

}
