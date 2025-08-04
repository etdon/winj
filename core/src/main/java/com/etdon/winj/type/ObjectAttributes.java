package com.etdon.winj.type;

import com.etdon.jbinder.common.MemorySegmentable;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

import static com.etdon.winj.type.NativeDataType.*;
import static java.lang.foreign.ValueLayout.ADDRESS;

/**
 * TODO: Implement
 */
public final class ObjectAttributes implements MemorySegmentable {

    public static final MemoryLayout OBJECT_ATTRIBUTES = MemoryLayout.structLayout(
            ULONG.withName("Length"),
            MemoryLayout.paddingLayout(4),
            HANDLE.withName("RootDirectory"),
            UnicodeString.PCUNICODE_STRING.withName("ObjectName"),
            ULONG.withName("Attributes"),
            MemoryLayout.paddingLayout(4),
            PVOID.withName("SecurityDescriptor"),
            PVOID.withName("SecurityQualityOfService")
    );
    public static final ValueLayout PCOBJECT_ATTRIBUTES = ADDRESS.withName("PCOBJECT_ATTRIBUTES");

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull Arena arena) {

        return null;

    }

}
