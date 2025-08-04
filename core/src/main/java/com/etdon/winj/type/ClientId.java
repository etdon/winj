package com.etdon.winj.type;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.MemorySegmentable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static com.etdon.winj.type.NativeDataType.HANDLE;
import static java.lang.foreign.ValueLayout.ADDRESS;

public final class ClientId implements MemorySegmentable {

    public static final MemoryLayout CLIENT_ID = MemoryLayout.structLayout(
            HANDLE.withName("UniqueProcess"),
            HANDLE.withName("UniqueThread")
    );

    private final MemorySegment uniqueProcess;

    private MemorySegment uniqueThread = MemorySegment.NULL;

    public ClientId(@NotNull final MemorySegment uniqueProcess,
                    @Nullable final MemorySegment uniqueThread) {

        Preconditions.checkNotNull(uniqueProcess);
        this.uniqueProcess = uniqueProcess;
        this.uniqueThread = uniqueThread;

    }

    public ClientId(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(CLIENT_ID.byteSize(), arena, null);

        this.uniqueProcess = memorySegment.get(ADDRESS, 0);
        this.uniqueThread = memorySegment.get(ADDRESS, ADDRESS.byteSize());

    }

    private ClientId(@NotNull final Builder builder) {

        this.uniqueProcess = builder.uniqueProcess;
        Conditional.executeIfNotNull(builder.uniqueThread, () -> this.uniqueThread = builder.uniqueThread);

    }

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment memorySegment = arena.allocate(CLIENT_ID.byteSize());
        memorySegment.set(ADDRESS, 0, this.uniqueProcess);
        memorySegment.set(ADDRESS, ADDRESS.byteSize(), this.uniqueThread);

        return memorySegment;

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<ClientId> {

        private MemorySegment uniqueProcess;
        private MemorySegment uniqueThread;

        public Builder uniqueProcess(@NotNull final MemorySegment uniqueProcess) {

            this.uniqueProcess = uniqueProcess;
            return this;

        }

        public Builder uniqueThread(@NotNull final MemorySegment uniqueThread) {

            this.uniqueThread = uniqueThread;
            return this;

        }

        @NotNull
        @Override
        public ClientId build() {

            Preconditions.checkNotNull(this.uniqueProcess);
            return new ClientId(this);

        }

    }

}
