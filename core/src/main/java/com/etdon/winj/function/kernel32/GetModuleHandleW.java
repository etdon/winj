package com.etdon.winj.function.kernel32;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;

public final class GetModuleHandleW extends NativeFunction {

    public static final String LIBRARY = Library.KERNEL_32;
    public static final String NATIVE_NAME = "GetModuleHandleW";
    public static final FunctionDescriptor GET_MODULE_HANDLE_W = FunctionDescriptor.of(
            HMODULE,
            LPCWSTR.withName("lpModuleName")
    );

    /**
     * The name of the loaded module (either a .dll or .exe file). If the file name extension is omitted, the default
     * library extension .dll is appended. The file name string can include a trailing point character (.) to indicate
     * that the module name has no extension. The string does not have to specify a path. When specifying a path, be
     * sure to use backslashes (\), not forward slashes (/). The name is compared (case independently) to the names of
     * modules currently mapped into the address space of the calling process.
     * <p>
     * If this parameter is NULL, GetModuleHandle returns a handle to the file used to create the calling process
     * (.exe file).
     * <p>
     * The GetModuleHandle function does not retrieve handles for modules that were loaded using the
     * LOAD_LIBRARY_AS_DATAFILE flag.
     */
    private MemorySegment moduleName = MemorySegment.NULL;

    private GetModuleHandleW(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, GET_MODULE_HANDLE_W);

        Conditional.executeIfNotNull(builder.moduleName, () -> this.moduleName = builder.moduleName);

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(this.moduleName);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<GetModuleHandleW> {

        private MemorySegment moduleName;

        private Builder() {

        }

        public Builder moduleName(@NotNull final MemorySegment moduleName) {

            this.moduleName = moduleName;
            return this;

        }

        @NotNull
        @Override
        public GetModuleHandleW build() {

            return new GetModuleHandleW(this);

        }

    }

}
