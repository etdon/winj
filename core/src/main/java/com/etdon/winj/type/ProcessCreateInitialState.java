package com.etdon.winj.type;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.jbinder.NativeType;
import com.etdon.winj.constant.ProcessCreateInitFlag;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static com.etdon.winj.type.constant.NativeDataType.*;
import static java.lang.foreign.ValueLayout.JAVA_INT;

public final class ProcessCreateInitialState extends NativeType {

    public static final MemoryLayout PS_CREATE_INITIAL_STATE = MemoryLayout.structLayout(
            ULONG.withName("InitFlags"),
            ACCESS_MASK.withName("AdditionalFileAccess")
    );

    /**
     * {@link ProcessCreateInitFlag}
     */
    private int initFlags = 0;

    private int additionalFileAccess = 0;

    public ProcessCreateInitialState(final int initFlags,
                                     final int additionalFileAccess) {

        this.initFlags = initFlags;
        this.additionalFileAccess = additionalFileAccess;

    }

    public ProcessCreateInitialState(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(PS_CREATE_INITIAL_STATE.byteSize(), arena, null);

        this.initFlags = memorySegment.get(JAVA_INT, 0);
        this.additionalFileAccess = memorySegment.get(JAVA_INT, 4);

    }

    private ProcessCreateInitialState(@NotNull final Builder builder) {

        Conditional.executeIfNotNull(builder.initFlags, () -> this.initFlags = builder.initFlags);
        Conditional.executeIfNotNull(builder.additionalFileAccess, () -> this.additionalFileAccess = builder.additionalFileAccess);

    }

    private ProcessCreateInitialState() {

    }

    @NotNull
    @Override
    public MemoryLayout getMemoryLayout() {

        return PS_CREATE_INITIAL_STATE;

    }

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment memorySegment = arena.allocate(PS_CREATE_INITIAL_STATE.byteSize());
        memorySegment.set(JAVA_INT, 0, this.initFlags);
        memorySegment.set(JAVA_INT, 4, this.additionalFileAccess);

        return memorySegment;

    }

    public int getInitFlags() {

        return this.initFlags;

    }

    public int getAdditionalFileAccess() {

        return this.additionalFileAccess;

    }

    public static ProcessCreateInitialState empty() {

        return new ProcessCreateInitialState();

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<ProcessCreateInitialState> {

        private Integer initFlags;
        private Integer additionalFileAccess;

        public Builder initFlags(final int initFlags) {

            this.initFlags = initFlags;
            return this;

        }

        public Builder additionalFileAccess(final int additionalFileAccess) {

            this.additionalFileAccess = additionalFileAccess;
            return this;

        }

        @NotNull
        @Override
        public ProcessCreateInitialState build() {

            return new ProcessCreateInitialState(this);

        }

    }

}
