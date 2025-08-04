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
import java.lang.foreign.ValueLayout;

import static com.etdon.winj.type.NativeDataType.HANDLE;
import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_LONG;

public final class ClientId implements MemorySegmentable {

    public static final MemoryLayout CLIENT_ID = MemoryLayout.structLayout(
            HANDLE.withName("UniqueProcess"),
            HANDLE.withName("UniqueThread")
    );
    public static final ValueLayout PCLIENT_ID = ADDRESS.withName("PCLIENT_ID");

    private final long uniqueProcess;

    private long uniqueThread;

    public ClientId(final int uniqueProcess,
                    final int uniqueThread) {

        Preconditions.checkNotNull(uniqueProcess);
        this.uniqueProcess = uniqueProcess;
        this.uniqueThread = uniqueThread;

    }

    public ClientId(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(CLIENT_ID.byteSize(), arena, null);

        this.uniqueProcess = memorySegment.get(JAVA_LONG, 0);
        this.uniqueThread = memorySegment.get(JAVA_LONG, JAVA_LONG.byteSize());

    }

    private ClientId(@NotNull final Builder builder) {

        this.uniqueProcess = builder.uniqueProcess;
        Conditional.executeIfNotNull(builder.uniqueThread, () -> this.uniqueThread = builder.uniqueThread);

    }

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment memorySegment = arena.allocate(CLIENT_ID.byteSize());
        memorySegment.set(JAVA_LONG, 0, this.uniqueProcess);
        memorySegment.set(JAVA_LONG, JAVA_LONG.byteSize(), this.uniqueThread);

        return memorySegment;

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<ClientId> {

        private Long uniqueProcess;
        private Long uniqueThread;

        public Builder uniqueProcess(final long uniqueProcess) {

            this.uniqueProcess = uniqueProcess;
            return this;

        }

        public Builder uniqueThread(final long uniqueThread) {

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
