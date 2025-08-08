package com.etdon.winj.function.kernel32;

import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.DWORD;

public final class GetCurrentProcessId extends NativeFunction {

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
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke();

    }

    public static GetCurrentProcessId getInstance() {

        return GetCurrentProcessIdSingleton.INSTANCE;

    }

}
