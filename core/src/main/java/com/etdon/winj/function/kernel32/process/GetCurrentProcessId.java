package com.etdon.winj.function.kernel32.process;

import com.etdon.jbinder.common.NativeDocumentation;
import com.etdon.jbinder.common.NativeName;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.DWORD;

/**
 * Retrieves the process identifier of the calling process.
 */
@NativeName(GetCurrentProcessId.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/processthreadsapi/nf-processthreadsapi-getcurrentprocessid")
public final class GetCurrentProcessId extends NativeFunction<Integer> {

    private static class GetCurrentProcessIdSingleton {

        private static final GetCurrentProcessId INSTANCE = new GetCurrentProcessId();

    }

    public static final String LIBRARY = Library.KERNEL_32;
    public static final String NATIVE_NAME = "GetCurrentProcessId";
    public static final FunctionDescriptor GET_CURRENT_PROCESS_ID_SIGNATURE = FunctionDescriptor.of(
            DWORD
    );

    private GetCurrentProcessId() {

        super(LIBRARY, NATIVE_NAME, GET_CURRENT_PROCESS_ID_SIGNATURE);

    }

    @Override
    public Integer call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return (Integer) super.obtainHandle(linker, symbolLookup).invoke();

    }

    public static GetCurrentProcessId getInstance() {

        return GetCurrentProcessIdSingleton.INSTANCE;

    }

}
