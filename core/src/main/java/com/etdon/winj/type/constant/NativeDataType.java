package com.etdon.winj.type.constant;

import java.lang.foreign.ValueLayout;

import static java.lang.foreign.ValueLayout.*;

public final class NativeDataType {

    /**
     * A 16-bit unsigned integer. The range is 0 through 65535 decimal.
     */
    public static final ValueLayout WORD = JAVA_SHORT.withName("WORD");
    /**
     * An atom.
     */
    public static final ValueLayout ATOM = WORD.withName("ATOM");
    /**
     * A Boolean variable (should be TRUE or FALSE).
     */
    public static final ValueLayout BOOL = JAVA_INT.withName("BOOL");
    /**
     * A pointer to a constant null-terminated string of 16-bit Unicode characters.
     */
    public static final ValueLayout LPCWSTR = ADDRESS.withName("LPCWSTR");
    /**
     * A pointer to a constant null-terminated string of 8-bit Windows (ANSI) characters.
     */
    public static final ValueLayout LPCSTR = ADDRESS.withName("LPCSTR");
    /**
     * A pointer to an LCID.
     */
    public static final ValueLayout PLCID = ADDRESS.withName("PLCID");
    /**
     * A pointer to a null-terminated string of 16-bit Unicode characters.
     */
    public static final ValueLayout LPWSTR = ADDRESS.withName("LPWSTR");
    /**
     * A locale identifier.
     */
    public static final ValueLayout LCID = JAVA_INT.withName("LCID");
    /**
     * A pointer to any type.
     */
    public static final ValueLayout PVOID = ADDRESS.withName("PVOID");
    /**
     * A handle to an object.
     */
    public static final ValueLayout HANDLE = PVOID.withName("HANDLE");
    /**
     * A pointer to a handle.
     */
    public static final ValueLayout PHANDLE = PVOID.withName("PHANDLE");
    /**
     * A handle to a window.
     */
    public static final ValueLayout HWND = HANDLE.withName("HWND");
    /**
     * A handle to a menu.
     */
    public static final ValueLayout HMENU = HANDLE.withName("HMENU");
    /**
     * A handle to an instance. This is the base address of the module in memory.
     */
    public static final ValueLayout HINSTANCE = HANDLE.withName("HINSTANCE");
    /**
     * A handle to a device context (DC).
     */
    public static final ValueLayout HDC = HANDLE.withName("HDC");
    /**
     * A handle to a module. This is the base address of the module in memory.
     */
    public static final ValueLayout HMODULE = HINSTANCE.withName("HMODULE");
    /**
     * A pointer to a constant of any type.
     */
    public static final ValueLayout LPCVOID = ADDRESS.withName("LPCVOID");
    /**
     * A pointer to any type.
     */
    public static final ValueLayout LPVOID = ADDRESS.withName("LPVOID");
    /**
     * A pointer to a RECT structure.
     */
    public static final ValueLayout LPRECT = ADDRESS.withName("LPRECT");
    /**
     * The maximum number of bytes to which a pointer can point. Use for a count that must span the full range of a
     * pointer.
     */
    public static final ValueLayout SIZE_T = JAVA_LONG.withName("SIZE_T");
    /**
     * A pointer to a SIZE_T.
     */
    public static final ValueLayout PSIZE_T = ADDRESS.withName("PSIZE_T");
    /**
     * A 32-bit signed integer. The range is -2147483648 through 2147483647 decimal.
     */
    public static final ValueLayout LONG = JAVA_INT.withName("LONG");
    /**
     * An unsigned LONG. The range is 0 through 4294967295 decimal.
     */
    public static final ValueLayout ULONG = LONG.withName("ULONG");
    /**
     * A 32-bit unsigned integer. The range is 0 through 4294967295 decimal.
     */
    public static final ValueLayout DWORD = JAVA_INT.withName("DWORD");
    /**
     * A handle to a hook.
     */
    public static final ValueLayout HHOOK = ADDRESS.withName("HHOOK");
    /**
     * A message parameter.
     */
    public static final ValueLayout WPARAM = JAVA_LONG.withName("WPARAM");
    /**
     * A message parameter.
     */
    public static final ValueLayout LPARAM = JAVA_LONG.withName("LPARAM");
    /**
     * Basic signed integer type.
     */
    public static final ValueLayout INTEGER = JAVA_INT.withName("INT");
    /**
     * An unsigned INT. The range is 0 through 4294967295 decimal.
     */
    public static final ValueLayout UINT = JAVA_INT.withName("UINT");
    /**
     * A signed long type for pointer precision. Use when casting a pointer to a long to perform pointer arithmetic.
     */
    public static final ValueLayout LONG_PTR = ADDRESS.withName("LONG_PTR");
    /**
     * An unsigned {@link NativeDataType#LONG_PTR}.
     */
    public static final ValueLayout ULONG_PTR = JAVA_LONG.withName("ULONG_PTR");
    /**
     * Signed result of message processing.
     */
    public static final ValueLayout LRESULT = LONG_PTR.withName("LRESULT");
    /**
     * A pointer to a DWORD.
     */
    public static final ValueLayout LPDWORD = ADDRESS.withName("LPDWORD");
    /**
     * An input locale identifier.
     */
    public static final ValueLayout HKL = HANDLE.withName("HKL");
    /**
     * A pointer to an array of INPUT structures.
     */
    public static final ValueLayout LPINPUT = ADDRESS.withName("LPINPUT");
    /**
     * A pointer to the hook procedure.
     */
    public static final ValueLayout HOOKPROC = ADDRESS.withName("HOOKPROC");
    /**
     * The red, green, blue (RGB) color value (32 bits).
     */
    public static final ValueLayout COLORREF = DWORD.withName("COLORREF");
    /**
     * A pointer to a MSG structure.
     */
    public static final ValueLayout LPMSG = ADDRESS.withName("LPMSG");
    /**
     * A pointer to a MSG structure.
     */
    public static final ValueLayout MSG = LPMSG.withName("MSG");
    /**
     * Pointer to the PAINTSTRUCT structure.
     */
    public static final ValueLayout LPPAINTSTRUCT = ADDRESS.withName("LPPAINTSTRUCT");
    /**
     * Pointer to the PAINTSTRUCT structure.
     */
    public static final ValueLayout PAINTSTRUCT = LPPAINTSTRUCT.withName("PAINTSTRUCT");
    /**
     * A byte (8 bits).
     */
    public static final ValueLayout BYTE = JAVA_BYTE.withName("BYTE");
    /**
     * An address to an exported function or variable.
     */
    public static final ValueLayout FARPROC = ADDRESS.withName("FARPROC");
    /**
     * Pointer to the SECURITY_ATTRIBUTES structure.
     */
    public static final ValueLayout LPSECURITY_ATTRIBUTES = ADDRESS.withName("LPSECURITY_ATTRIBUTES");
    /**
     *
     */
    public static final ValueLayout PSID = ADDRESS.withName("PSID");
    /**
     *
     */
    public static final ValueLayout PACL = ADDRESS.withName("PACL");
    /**
     *
     */
    public static final ValueLayout LPTHREAD_START_ROUTINE = ADDRESS.withName("LPTHREAD_START_ROUTINE");
    /**
     * The ACCESS_MASK data type is a DWORD value that defines standard, specific, and generic rights. These rights are
     * used in access control entries (ACEs) and are the primary means of specifying the requested or granted access to
     * an object.
     */
    public static final ValueLayout ACCESS_MASK = DWORD.withName("ACCESS_MASK");
    /**
     * A 16-bit integer. The range is -32768 through 32767 decimal.
     */
    public static final ValueLayout SHORT = JAVA_SHORT.withName("SHORT");
    /**
     * An unsigned SHORT. The range is 0 through 65535 decimal.
     */
    public static final ValueLayout USHORT = SHORT.withName("USHORT");
    /**
     *
     */
    public static final ValueLayout PWCH = ADDRESS.withName("PWCH");
    /**
     * A 64-bit unsigned integer. The range is 0 through 18446744073709551615 decimal.
     */
    public static final ValueLayout ULONGLONG = JAVA_LONG.withName("ULONGLONG");
    /**
     * A pointer to a ULONGLONG.
     */
    public static final ValueLayout PULONGLONG = ADDRESS.withName("PULONGLONG");
    /**
     * An 8-bit Windows (ANSI) character.
     */
    public static final ValueLayout CHAR = JAVA_CHAR.withName("CHAR");
    /**
     * An unsigned CHAR.
     */
    public static final ValueLayout UCHAR = CHAR.withName("UCHAR");
    /**
     * A pointer to a constant null-terminated string of 16-bit Unicode characters. For more information, see Character
     * Sets Used By Fonts.
     */
    public static final ValueLayout PCWSTR = ADDRESS.withName("PCWSTR");
    /**
     * NTSTATUS is a standard 32-bit datatype for system-supplied status code values.
     */
    public static final ValueLayout NTSTATUS = JAVA_INT.withName("NTSTATUS");
    /**
     * A DWORD64 is a 64-bit unsigned integer.
     */
    public static final ValueLayout DWORD64 = JAVA_LONG.withName("DWORD64");

    private NativeDataType() {

        throw new UnsupportedOperationException();

    }

}
