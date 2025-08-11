package com.etdon.winj.type;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.NativeType;
import com.etdon.jbinder.common.MemorySegmentable;
import com.etdon.jbinder.common.NativeName;
import com.etdon.winj.constant.ProcessCreateState;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

import static com.etdon.winj.type.constant.NativeDataType.*;
import static java.lang.foreign.ValueLayout.*;

public final class ProcessCreateInfo extends NativeType {

    public static final MemoryLayout PS_CREATE_INFO = MemoryLayout.structLayout(
            SIZE_T.withName("Size"),
            DWORD.withName("State"),
            MemoryLayout.paddingLayout(4),
            MemoryLayout.unionLayout(
                    ProcessCreateInitialState.PS_CREATE_INITIAL_STATE.withName("ProcessCreateInitialState"),
                    ProcessCreateFailOnSectionCreate.PS_CREATE_FAIL_ON_SECTION_CREATE.withName("ProcessCreateFailOnSectionCreate"),
                    ProcessCreateFailExeFormat.PS_CREATE_FAIL_EXE_FORMAT.withName("ProcessCreateFailExeFormat"),
                    ProcessCreateFailExeName.PS_CREATE_FAIL_EXE_NAME.withName("ProcessCreateFailExeName"),
                    ProcessCreateSuccess.PS_CREATE_SUCCESS.withName("ProcessCreateSuccess")
            )
    );
    public static final ValueLayout PPS_CREATE_INFO = ADDRESS.withName("PPS_CREATE_INFO");

    @NativeName("Size")
    private final long size;

    /**
     * {@link com.etdon.winj.constant.ProcessCreateState}
     */
    @NativeName("State")
    private final int state;

    private MemorySegmentable info = MemorySegmentable.NULL;

    public ProcessCreateInfo(final long size,
                             final int state,
                             final MemorySegmentable info) {

        Preconditions.checkNotNull(info);
        this.size = size;
        this.state = state;
        this.info = info;

    }

    public ProcessCreateInfo(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(PS_CREATE_INFO.byteSize(), arena, null);

        this.size = memorySegment.get(JAVA_LONG, 0);
        this.state = memorySegment.get(JAVA_INT, 8);
        final MemorySegment infoSlice = memorySegment.asSlice(16);
        switch (state) {
            case ProcessCreateState.PS_CREATE_INITIAL_STATE -> {
                this.info = new ProcessCreateInitialState(arena, infoSlice);
            }
            case ProcessCreateState.PS_CREATE_FAIL_ON_SECTION_CREATE -> {
                this.info = new ProcessCreateFailOnSectionCreate(arena, infoSlice);
            }
            case ProcessCreateState.PS_CREATE_FAIL_EXE_FORMAT -> {
                this.info = new ProcessCreateFailExeFormat(arena, infoSlice);
            }
            case ProcessCreateState.PS_CREATE_FAIL_EXE_NAME -> {
                this.info = new ProcessCreateFailExeName(arena, infoSlice);
            }
            case ProcessCreateState.PS_CREATE_SUCCESS -> {
                this.info = new ProcessCreateSuccess(arena, infoSlice);
            }
        }

    }

    private ProcessCreateInfo(@NotNull final Builder builder) {

        this.size = builder.size;
        this.state = builder.state;
        Conditional.executeIfNotNull(builder.info, () -> this.info = builder.info);

    }

    @NotNull
    @Override
    public MemoryLayout getMemoryLayout() {

        return PS_CREATE_INFO;

    }

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment memorySegment = arena.allocate(PS_CREATE_INFO.byteSize());
        memorySegment.set(JAVA_LONG, 0, this.size);
        memorySegment.set(JAVA_INT, 8, this.state);
        final MemorySegment infoMemorySegment = this.info.createMemorySegment(arena);
        MemorySegment.copy(infoMemorySegment, 0, memorySegment, 16, infoMemorySegment.byteSize());

        return memorySegment;

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<ProcessCreateInfo> {

        private Long size;
        private Integer state;
        private MemorySegmentable info;

        public Builder size(final long size) {

            this.size = size;
            return this;

        }

        public Builder state(final int state) {

            this.state = state;
            return this;

        }

        public Builder info(@NotNull final MemorySegmentable info) {

            this.info = info;
            return this;

        }

        @NotNull
        @Override
        public ProcessCreateInfo build() {

            Preconditions.checkNotNull(this.size);
            Preconditions.checkNotNull(this.state);
            return new ProcessCreateInfo(this);

        }

    }

}
