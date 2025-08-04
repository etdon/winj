package com.etdon.winj.function.kernel32;

import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.NativeDataType.HANDLE;

public final class GetProcessHeap extends NativeFunction {

    private static class GetProcessHeapSingleton {

        private static final GetProcessHeap INSTANCE = new GetProcessHeap();

    }

    public static final String LIBRARY = Library.KERNEL_32;
    public static final String NATIVE_NAME = "GetProcessHeap";
    public static final FunctionDescriptor GET_PROCESS_HEAP_SIGNATURE = FunctionDescriptor.of(
            HANDLE
    );

    private GetProcessHeap() {

        super(LIBRARY, NATIVE_NAME, GET_PROCESS_HEAP_SIGNATURE);

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke();

    }

    public static GetProcessHeap getInstance() {

        return GetProcessHeapSingleton.INSTANCE;

    }

}
