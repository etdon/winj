package com.etdon.winj.function.kernel32.process;

import com.etdon.jbinder.common.NativeDocumentation;
import com.etdon.jbinder.common.NativeName;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.SymbolLookup;

import static java.lang.foreign.ValueLayout.*;

/**
 * Retrieves a pseudo handle for the current process.
 */
@NativeName(GetCurrentProcess.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/processthreadsapi/nf-processthreadsapi-getcurrentprocess")
public final class GetCurrentProcess extends NativeFunction {

    private static class GetCurrentProcessSingleton {

        private static final GetCurrentProcess INSTANCE = new GetCurrentProcess();

    }

    public static final String LIBRARY = Library.KERNEL_32;
    public static final String NATIVE_NAME = "GetCurrentProcess";
    public static final FunctionDescriptor GET_CURRENT_PROCESS_SIGNATURE = FunctionDescriptor.of(
            ADDRESS
    );

    private GetCurrentProcess() {

        super(LIBRARY, NATIVE_NAME, GET_CURRENT_PROCESS_SIGNATURE);

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke();

    }

    public static GetCurrentProcess getInstance() {

        return GetCurrentProcessSingleton.INSTANCE;

    }

}
