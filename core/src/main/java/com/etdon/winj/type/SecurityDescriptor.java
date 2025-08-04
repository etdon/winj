package com.etdon.winj.type;

import com.etdon.jbinder.common.MemorySegmentable;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static com.etdon.winj.type.NativeDataType.*;

/**
 * TODO: Implement
 */
public final class SecurityDescriptor implements MemorySegmentable {

    public static final MemoryLayout SECURITY_DESCRIPTOR = MemoryLayout.structLayout(
            BYTE.withName("Revision"),
            BYTE.withName("Sbz1"),
            WORD.withName("Control"),
            PSID.withName("Owner"),
            PSID.withName("Group"),
            PACL.withName("Sacl"),
            PACL.withName("Dacl")
    );

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull Arena arena) {

        return null;

    }

}
