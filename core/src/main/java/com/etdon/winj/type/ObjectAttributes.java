package com.etdon.winj.type;

import com.etdon.jbinder.NativeType;
import com.etdon.jbinder.common.NativeDocumentation;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

import static com.etdon.winj.type.constant.NativeDataType.*;
import static java.lang.foreign.ValueLayout.ADDRESS;

/**
 * TODO: Implement
 */
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/ntdef/ns-ntdef-_object_attributes")
public final class ObjectAttributes extends NativeType {

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
    public MemoryLayout getMemoryLayout() {

        return OBJECT_ATTRIBUTES;

    }

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        return null;

    }

}
