package com.etdon.winj.type;

import com.etdon.jbinder.common.MemorySegmentable;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static com.etdon.winj.type.NativeDataType.*;
import static com.etdon.winj.type.UnicodeString.UNICODE_STRING;

public final class CurrentDirectoryDriveLetter implements MemorySegmentable {

    public static final MemoryLayout RTL_DRIVE_LETTER_CURDIR = MemoryLayout.structLayout(
            USHORT.withName("Flags"),
            USHORT.withName("Length"),
            ULONG.withName("TimeStamp"),
            UNICODE_STRING.withName("DosPath")
    );

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        return null;

    }

}
