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

public final class SecurityDescriptor extends NativeType {

    public static final MemoryLayout SECURITY_DESCRIPTOR = MemoryLayout.structLayout(
            BYTE.withName("Revision"),
            BYTE.withName("Sbz1"),
            WORD.withName("Control"),
            PSID.withName("Owner"),
            PSID.withName("Group"),
            PACL.withName("Sacl"),
            PACL.withName("Dacl")
    );

    private final byte revision;

    private final byte sbz1;

    private final short control;

    private final MemorySegment owner;

    private final MemorySegment group;

    private final MemorySegment sacl;

    private final MemorySegment dacl;

    public SecurityDescriptor(final byte revision,
                              final byte sbz1,
                              final short control,
                              @NotNull final MemorySegment owner,
                              @NotNull final MemorySegment group,
                              @NotNull final MemorySegment sacl,
                              @NotNull final MemorySegment dacl) {

        Preconditions.checkNotNull(owner);
        Preconditions.checkNotNull(group);
        Preconditions.checkNotNull(sacl);
        Preconditions.checkNotNull(dacl);
        this.revision = revision;
        this.sbz1 = sbz1;
        this.control = control;
        this.owner = owner;
        this.group = group;
        this.sacl = sacl;
        this.dacl = dacl;

    }

    public SecurityDescriptor(@NotNull final Arena arena, @NotNull MemorySegment segment) {

        if (segment.byteSize() == 0)
            segment = segment.reinterpret(SECURITY_DESCRIPTOR.byteSize(), arena, null);

        this.revision = segment.get(JAVA_BYTE, 0);
        this.sbz1 = segment.get(JAVA_BYTE, 1);
        this.control = segment.get(JAVA_SHORT, 2);
        this.owner = segment.get(ADDRESS, 4);
        this.group = segment.get(ADDRESS, 12);
        this.sacl = segment.get(ADDRESS, 20);
        this.dacl = segment.get(ADDRESS, 28);

    }

    private SecurityDescriptor(final Builder builder) {

        this.revision = builder.revision;
        this.sbz1 = builder.sbz1;
        this.control = builder.control;
        this.owner = builder.owner;
        this.group = builder.group;
        this.sacl = builder.sacl;
        this.dacl = builder.dacl;

    }

    @NotNull
    @Override
    public MemoryLayout getMemoryLayout() {

        return SECURITY_DESCRIPTOR;

    }

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment segment = arena.allocate(SECURITY_DESCRIPTOR);
        segment.set(JAVA_BYTE, 0, this.revision);
        segment.set(JAVA_BYTE, 1, this.sbz1);
        segment.set(JAVA_SHORT, 2, this.control);
        segment.set(ADDRESS, 4, this.owner);
        segment.set(ADDRESS, 12, this.group);
        segment.set(ADDRESS, 20, this.sacl);
        segment.set(ADDRESS, 28, this.dacl);

        return segment;

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<SecurityDescriptor> {

        private Byte revision;
        private Byte sbz1;
        private Short control;
        private MemorySegment owner;
        private MemorySegment group;
        private MemorySegment sacl;
        private MemorySegment dacl;

        public Builder revision(final byte revision) {

            this.revision = revision;
            return this;

        }

        public Builder sbz1(final byte sbz1) {

            this.sbz1 = sbz1;
            return this;

        }

        public Builder control(final short control) {

            this.control = control;
            return this;

        }

        public Builder owner(@NotNull final MemorySegment owner) {

            this.owner = owner;
            return this;

        }

        public Builder group(@NotNull final MemorySegment group) {
            this.group = group;
            return this;
        }

        public Builder sacl(@NotNull final MemorySegment sacl) {

            this.sacl = sacl;
            return this;

        }

        public Builder dacl(@NotNull final MemorySegment dacl) {

            this.dacl = dacl;
            return this;

        }

        @NotNull
        @Override
        public SecurityDescriptor build() {

            Preconditions.checkNotNull(revision);
            Preconditions.checkNotNull(sbz1);
            Preconditions.checkNotNull(control);
            Preconditions.checkNotNull(owner);
            Preconditions.checkNotNull(group);
            Preconditions.checkNotNull(sacl);
            Preconditions.checkNotNull(dacl);
            return new SecurityDescriptor(this);

        }

    }

}
