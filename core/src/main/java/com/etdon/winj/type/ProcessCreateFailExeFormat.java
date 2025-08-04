package com.etdon.winj.type;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.MemorySegmentable;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static com.etdon.winj.type.NativeDataType.USHORT;
import static java.lang.foreign.ValueLayout.JAVA_SHORT;

public final class ProcessCreateFailExeFormat implements MemorySegmentable {

    public static final MemoryLayout PS_CREATE_FAIL_EXE_FORMAT = MemoryLayout.structLayout(
            USHORT.withName("DllCharacteristics")
    );

    private final short dllCharacteristics;

    public ProcessCreateFailExeFormat(final short dllCharacteristics) {

        this.dllCharacteristics = dllCharacteristics;

    }

    public ProcessCreateFailExeFormat(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(PS_CREATE_FAIL_EXE_FORMAT.byteSize(), arena, null);

        this.dllCharacteristics = memorySegment.get(JAVA_SHORT, 0);

    }

    private ProcessCreateFailExeFormat(@NotNull final Builder builder) {

        this.dllCharacteristics = builder.dllCharacteristics;

    }

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment memorySegment = arena.allocate(PS_CREATE_FAIL_EXE_FORMAT.byteSize());
        memorySegment.set(JAVA_SHORT, 0, this.dllCharacteristics);

        return memorySegment;

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<ProcessCreateFailExeFormat> {

        private Short dllCharacteristics;

        public Builder dllCharacteristics(final short dllCharacteristics) {

            this.dllCharacteristics = dllCharacteristics;
            return this;

        }

        @NotNull
        @Override
        public ProcessCreateFailExeFormat build() {

            Preconditions.checkNotNull(this.dllCharacteristics);
            return new ProcessCreateFailExeFormat(this);

        }

    }

}
