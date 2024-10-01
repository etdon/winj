package com.etdon.winj.function.ntdll;

import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.SymbolLookup;

import static java.lang.foreign.ValueLayout.JAVA_LONG;

public final class NtGetTickCount extends NativeFunction {

    private static class NtGetTickCountSingleton {

        private static final NtGetTickCount INSTANCE = new NtGetTickCount();

    }

    public static final String LIBRARY = Library.NTDLL;
    public static final String NATIVE_NAME = "NtGetTickCount";
    public static final FunctionDescriptor NT_GET_TICK_COUNT_SIGNATURE = FunctionDescriptor.of(
            JAVA_LONG
    );

    private NtGetTickCount() {

        super(LIBRARY, NATIVE_NAME, NT_GET_TICK_COUNT_SIGNATURE);

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke();

    }

    public static NtGetTickCount getInstance() {

        return NtGetTickCountSingleton.INSTANCE;

    }

}
