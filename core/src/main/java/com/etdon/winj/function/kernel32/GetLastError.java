package com.etdon.winj.function.kernel32;

import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.NativeDataType.DWORD;

public final class GetLastError extends NativeFunction {

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
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke();

    }

    public static GetLastError getInstance() {

        return GetLastErrorSingleton.INSTANCE;

    }

}
