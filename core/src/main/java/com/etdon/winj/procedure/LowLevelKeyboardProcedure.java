package com.etdon.winj.procedure;

import com.etdon.jbinder.NativeProcedure;

import java.lang.foreign.FunctionDescriptor;

import static com.etdon.winj.type.constant.NativeDataType.*;

public final class LowLevelKeyboardProcedure extends NativeProcedure {

    public static final String NATIVE_NAME = "LowLevelKeyboardProc";
    public static final FunctionDescriptor LOW_LEVEL_KEYBOARD_PROCEDURE_SIGNATURE = FunctionDescriptor.of(
            LRESULT,
            INTEGER,
            WPARAM,
            LPARAM
    );

    public LowLevelKeyboardProcedure() {

        super(LOW_LEVEL_KEYBOARD_PROCEDURE_SIGNATURE);

    }

}
