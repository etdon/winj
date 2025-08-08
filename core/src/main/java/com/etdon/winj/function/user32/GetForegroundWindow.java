package com.etdon.winj.function.user32;

import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;

public final class GetForegroundWindow extends NativeFunction {

    private static class GetForegroundWindowSingleton {

        private static final GetForegroundWindow INSTANCE = new GetForegroundWindow();

    }

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "GetForegroundWindow";
    public static final FunctionDescriptor GET_FOREGROUND_WINDOW_SIGNATURE = FunctionDescriptor.of(
            HWND
    );

    private GetForegroundWindow() {

        super(LIBRARY, NATIVE_NAME, GET_FOREGROUND_WINDOW_SIGNATURE);

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke();

    }

    public static GetForegroundWindow getInstance() {

        return GetForegroundWindowSingleton.INSTANCE;

    }

}
