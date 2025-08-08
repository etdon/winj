package com.etdon.winj.type;

import com.etdon.jbinder.NativeType;
import com.etdon.jbinder.common.NativeDocumentation;
import com.etdon.winj.constant.Global;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

import static com.etdon.winj.type.constant.NativeDataType.*;
import static com.etdon.winj.type.UnicodeString.*;
import static java.lang.foreign.ValueLayout.ADDRESS;

/**
 * TODO: Implement
 */
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/winternl/ns-winternl-rtl_user_process_parameters")
public final class UserProcessParameters extends NativeType {

    /**
     * Flags: {@link com.etdon.winj.constant.UserProcessParametersFlag}
     */
    public static final MemoryLayout RTL_USER_PROCESS_PARAMETERS = MemoryLayout.structLayout(
            ULONG.withName("MaximumLength"),
            ULONG.withName("Length"),
            ULONG.withName("Flags"),
            ULONG.withName("DebugFlags"),
            HANDLE.withName("ConsoleHandle"),
            ULONG.withName("ConsoleFlags"),
            MemoryLayout.paddingLayout(4),
            HANDLE.withName("StandardInput"),
            HANDLE.withName("StandardOutput"),
            HANDLE.withName("StandardError"),
            CurrentDirectory.CURDIR.withName("CurrentDirectory"),
            UNICODE_STRING.withName("DllPath"),
            UNICODE_STRING.withName("ImagePathName"),
            UNICODE_STRING.withName("CommandLine"),
            PVOID.withName("Environment"),
            ULONG.withName("StartingX"),
            ULONG.withName("StartingY"),
            ULONG.withName("CountX"),
            ULONG.withName("CountY"),
            ULONG.withName("CountCharsX"),
            ULONG.withName("CountCharsY"),
            ULONG.withName("FillAttributes"),
            ULONG.withName("WindowFlags"),
            ULONG.withName("ShowWindowFlags"),
            MemoryLayout.paddingLayout(4),
            UNICODE_STRING.withName("WindowTitle"),
            UNICODE_STRING.withName("DesktopInfo"),
            UNICODE_STRING.withName("ShellInfo"),
            UNICODE_STRING.withName("RuntimeData"),
            MemoryLayout.sequenceLayout(Global.RTL_MAX_DRIVE_LETTERS,
                    CurrentDirectoryDriveLetter.RTL_DRIVE_LETTER_CURDIR
            ).withName("CurrentDirectories"),
            ULONG_PTR.withName("EnvironmentSize"),
            ULONG_PTR.withName("EnvironmentVersion"),
            PVOID.withName("PackageDependencyData"),
            ULONG.withName("ProcessGroupId"),
            ULONG.withName("LoaderThreads"),
            UNICODE_STRING.withName("RedirectionDllName"),
            UNICODE_STRING.withName("HeapPartitionName"),
            PULONGLONG.withName("DefaultThreadpoolCpuSetMasks"),
            ULONG.withName("DefaultThreadpoolCpuSetMaskCount"),
            ULONG.withName("DefaultThreadpoolThreadMaximum"),
            ULONG.withName("HeapMemoryTypeMask")
    );
    public static final ValueLayout PRTL_USER_PROCESS_PARAMETERS = ADDRESS.withName("PRTL_USER_PROCESS_PARAMETERS");

    @NotNull
    @Override
    public MemoryLayout getMemoryLayout() {

        return RTL_USER_PROCESS_PARAMETERS;

    }

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull Arena arena) {

        return null;

    }

}
