package com.etdon.winj.hook;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.commons.conditional.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.foreign.MemorySegment;
import java.util.Objects;

/**
 * Represents the data of a managed hook that can be used with a {@link HookManager} to automatically manage (register and
 * unregister) windows hooks. Managed hooks can automatically be re-registered with a fix interval allowing them to
 * stay on top of the LIFO style hook chain.
 */
public class ManagedHook {

    private final String id;
    private final int type;
    private final MemorySegment stub;
    private MemorySegment owner;
    private MemorySegment handle;
    private int threadId;

    private ManagedHook(@NotNull final Builder builder) {

        this.id = builder.id;
        this.type = builder.type;
        this.stub = builder.stub;
        Conditional.executeIfNotNull(builder.owner, () -> this.owner = builder.owner);
        Conditional.executeIfNotNull(builder.handle, () -> this.handle = builder.handle);
        Conditional.executeIfNotNull(builder.threadId, () -> this.threadId = builder().threadId);

    }

    @NotNull
    public String getId() {

        return this.id;

    }

    public int getType() {

        return this.type;

    }

    @NotNull
    public MemorySegment getStub() {

        return this.stub;

    }

    @Nullable
    public MemorySegment getOwner() {

        return this.owner;

    }

    @Nullable
    public MemorySegment getHandle() {

        return this.handle;

    }

    public void setHandle(@Nullable final MemorySegment handle) {

        this.handle = handle;

    }

    public int getThreadId() {

        return this.threadId;

    }

    @Override
    public String toString() {

        return "Hook{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", stub=" + stub +
                ", owner=" + owner +
                ", handle=" + handle +
                ", threadId=" + threadId +
                '}';

    }

    @Override
    public int hashCode() {

        return Objects.hash(this.id, this.type, this.stub, this.owner);

    }

    @Override
    public boolean equals(final Object other) {

        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        final ManagedHook managedHook = (ManagedHook) other;
        return Objects.equals(this.id, managedHook.id) &&
                Objects.equals(this.type, managedHook.type) &&
                Objects.equals(this.stub, managedHook.stub) &&
                Objects.equals(this.owner, managedHook.owner);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<ManagedHook> {

        private String id;
        private Integer type;
        private MemorySegment stub;
        private MemorySegment owner;
        private MemorySegment handle;
        private Integer threadId;

        private Builder() {

        }

        public Builder id(@NotNull final String id) {

            Preconditions.checkNotNull(id);
            this.id = id;
            return this;

        }

        public Builder type(final int type) {

            this.type = type;
            return this;

        }

        public Builder stub(@NotNull final MemorySegment stub) {

            Preconditions.checkNotNull(stub);
            this.stub = stub;
            return this;

        }

        public Builder owner(@Nullable final MemorySegment owner) {

            this.owner = owner;
            return this;

        }

        public Builder handle(@Nullable final MemorySegment handle) {

            this.handle = handle;
            return this;

        }

        public Builder threadId(final int threadId) {

            this.threadId = threadId;
            return this;

        }

        @NotNull
        @Override
        public ManagedHook build() {

            return new ManagedHook(this);

        }

    }

}
