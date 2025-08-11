package com.etdon.winj.function.kernel32.heap;

import com.etdon.jbinder.common.NativeDocumentation;
import com.etdon.jbinder.common.NativeName;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.HANDLE;

/**
 * Retrieves a handle to the default heap of the calling process. This handle can then be used in subsequent calls to
 * the heap functions.
 */
@NativeName(GetProcessHeap.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/heapapi/nf-heapapi-getprocessheap")
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
