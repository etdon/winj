package com.etdon.winj.function.kernel32.error;

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
 * Retrieves the calling thread's last-error code value. The last-error code is maintained on a per-thread basis.
 * Multiple threads do not overwrite each other's last-error code.
 */
@NativeName(GetLastError.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/errhandlingapi/nf-errhandlingapi-getlasterror")
public final class GetLastError extends NativeFunction<Integer> {

    private static class GetLastErrorSingleton {

        private static final GetLastError INSTANCE = new GetLastError();

    }

    public static final String LIBRARY = Library.KERNEL_32;
    public static final String NATIVE_NAME = "GetLastError";
    public static final FunctionDescriptor GET_LAST_ERROR_SIGNATURE = FunctionDescriptor.of(
            DWORD
    );

    private GetLastError() {

        super(LIBRARY, NATIVE_NAME, GET_LAST_ERROR_SIGNATURE);

    }

    @Override
    public Integer call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return (Integer) super.obtainHandle(linker, symbolLookup).invoke();

    }

    public static GetLastError getInstance() {

        return GetLastErrorSingleton.INSTANCE;

    }

}
