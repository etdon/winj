package com.etdon.winj.function.user32;

import com.etdon.jbinder.common.NativeDocumentation;
import com.etdon.jbinder.common.NativeName;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;

/**
 * Retrieves a handle to the foreground window (the window with which the user is currently working). The system
 * assigns a slightly higher priority to the thread that creates the foreground window than it does to other threads.
 */
@NativeName(GetForegroundWindow.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-getforegroundwindow")
public final class GetForegroundWindow extends NativeFunction<MemorySegment> {

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
    public MemorySegment call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return (MemorySegment) super.obtainHandle(linker, symbolLookup).invoke();

    }

    public static GetForegroundWindow getInstance() {

        return GetForegroundWindowSingleton.INSTANCE;

    }

}
