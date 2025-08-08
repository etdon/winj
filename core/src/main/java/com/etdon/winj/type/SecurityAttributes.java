package com.etdon.winj.type;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.NativeType;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static com.etdon.winj.type.constant.NativeDataType.*;
import static java.lang.foreign.ValueLayout.*;

public final class SecurityAttributes extends NativeType {

    public static final MemoryLayout SECURITY_ATTRIBUTES = MemoryLayout.structLayout(
            DWORD.withName("nLength"),
            LPVOID.withName("lpSecurityDescriptor"),
            BOOL.withName("bInheritHandle")
    );

    /**
     * The size, in bytes, of this structure. Set this value to the size of the SECURITY_ATTRIBUTES structure.
     */
    private final int length;

    /**
     * A pointer to a SECURITY_DESCRIPTOR structure that controls access to the object. If the value of this member is
     * NULL, the object is assigned the default security descriptor associated with the access token of the calling
     * process. This is not the same as granting access to everyone by assigning a NULL discretionary access control
     * list (DACL). By default, the default DACL in the access token of a process allows access only to the user
     * represented by the access token.
     *
     * @see SecurityDescriptor
     */
    private final MemorySegment securityDescriptorPointer;

    /**
     * A Boolean value that specifies whether the returned handle is inherited when a new process is created. If this
     * member is TRUE, the new process inherits the handle.
     */
    private final boolean inheritHandle;

    public SecurityAttributes(final int length,
                              @NotNull final MemorySegment securityDescriptorPointer,
                              final boolean inheritHandle) {

        Preconditions.checkNotNull(securityDescriptorPointer);
        this.length = length;
        this.securityDescriptorPointer = securityDescriptorPointer;
        this.inheritHandle = inheritHandle;

    }

    public SecurityAttributes(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(SECURITY_ATTRIBUTES.byteSize(), arena, null);

        this.length = memorySegment.get(JAVA_INT, 0);
        this.securityDescriptorPointer = memorySegment.get(ADDRESS, 4);
        this.inheritHandle = memorySegment.get(JAVA_BOOLEAN, 12);

    }

    private SecurityAttributes(@NotNull final Builder builder) {

        this.length = builder.length;
        this.securityDescriptorPointer = builder.securityDescriptorPointer;
        this.inheritHandle = builder.inheritHandle;

    }

    @NotNull
    @Override
    public MemoryLayout getMemoryLayout() {

        return SECURITY_ATTRIBUTES;

    }

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment memorySegment = arena.allocate(SECURITY_ATTRIBUTES);
        memorySegment.set(JAVA_INT, 0, this.length);
        memorySegment.set(ADDRESS, 4, this.securityDescriptorPointer);
        memorySegment.set(JAVA_BOOLEAN, 12, this.inheritHandle);

        return memorySegment;

    }

    public int getLength() {

        return this.length;

    }

    public boolean isInheritHandle() {

        return this.inheritHandle;

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<SecurityAttributes> {

        private Integer length;
        private MemorySegment securityDescriptorPointer;
        private Boolean inheritHandle;

        public Builder length(final int length) {

            this.length = length;
            return this;

        }

        public Builder securityDescriptorPointer(@NotNull final MemorySegment securityDescriptorPointer) {

            this.securityDescriptorPointer = securityDescriptorPointer;
            return this;

        }

        public Builder inheritHandle(final boolean inheritHandle) {

            this.inheritHandle = inheritHandle;
            return this;

        }

        @NotNull
        @Override
        public SecurityAttributes build() {

            Preconditions.checkNotNull(this.length);
            Preconditions.checkNotNull(this.securityDescriptorPointer);
            Preconditions.checkNotNull(this.inheritHandle);
            return new SecurityAttributes(this);

        }

    }

}
