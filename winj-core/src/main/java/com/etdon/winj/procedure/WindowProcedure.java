package com.etdon.winj.procedure;

import com.etdon.jbinder.NativeProcedure;

import java.lang.foreign.FunctionDescriptor;

import static com.etdon.winj.type.NativeDataType.*;

public final class WindowProcedure extends NativeProcedure {

    public static final String NATIVE_NAME = "Wndproc";
    public static final FunctionDescriptor WINDOW_PROCEDURE_SIGNATURE = FunctionDescriptor.of(
            LRESULT,
            HWND,
            UINT,
            WPARAM,
            LPARAM
    );

    public WindowProcedure() {

        super(WINDOW_PROCEDURE_SIGNATURE);

    }

}
