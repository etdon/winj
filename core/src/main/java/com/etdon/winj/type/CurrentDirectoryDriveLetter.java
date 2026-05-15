package com.etdon.winj.type;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.NativeType;
import com.etdon.jbinder.common.NativeName;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static com.etdon.winj.type.constant.NativeDataType.*;
import static com.etdon.winj.type.UnicodeString.UNICODE_STRING;
import static java.lang.foreign.ValueLayout.*;

public final class CurrentDirectoryDriveLetter extends NativeType {

    public static final MemoryLayout RTL_DRIVE_LETTER_CURDIR = MemoryLayout.structLayout(
            USHORT.withName("Flags"),
            USHORT.withName("Length"),
            ULONG.withName("TimeStamp"),
            UNICODE_STRING.withName("DosPath")
    );

    @NativeName("Flags")
    private final short flags;

    @NativeName("Length")
    private final short length;

    @NativeName("TimeStamp")
    private final int timeStamp;

    @NativeName("DosPath")
    private final MemorySegment dosPath;

    public CurrentDirectoryDriveLetter(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(RTL_DRIVE_LETTER_CURDIR.byteSize(), arena, null);

        this.flags = memorySegment.get(JAVA_SHORT, 0);
        this.length = memorySegment.get(JAVA_SHORT, 2);
        this.timeStamp = memorySegment.get(JAVA_INT, 4);
        this.dosPath = memorySegment.asSlice(8, UNICODE_STRING.byteSize());

    }

    private CurrentDirectoryDriveLetter(@NotNull final Builder builder) {

        this.flags = builder.flags;
        this.length = builder.length;
        this.timeStamp = builder.timeStamp;
        this.dosPath = builder.dosPath;

    }

    @NotNull
    @Override
    public MemoryLayout getMemoryLayout() {

        return RTL_DRIVE_LETTER_CURDIR;

    }

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment memorySegment = arena.allocate(RTL_DRIVE_LETTER_CURDIR);
        memorySegment.set(JAVA_SHORT, 0, this.flags);
        memorySegment.set(JAVA_SHORT, 2, this.length);
        memorySegment.set(JAVA_INT, 4, this.timeStamp);
        MemorySegment.copy(this.dosPath, 0, memorySegment, 8, UNICODE_STRING.byteSize());

        return memorySegment;

    }

    public short getFlags() {

        return this.flags;

    }

    public short getLength() {

        return this.length;

    }

    public int getTimeStamp() {

        return this.timeStamp;

    }

    public MemorySegment getDosPath() {

        return this.dosPath;

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<CurrentDirectoryDriveLetter> {

        private Short flags;
        private Short length;
        private Integer timeStamp;
        private MemorySegment dosPath;

        /**
         * Overrides the builder internal flags value with the provided value.
         * <p>
         * This value is not optional.
         * <p>
         * Please refer to the {@link CurrentDirectoryDriveLetter#flags} field for documentation.
         *
         * @param flags the flags
         * @return the builder instance
         */
        public Builder flags(final short flags) {

            this.flags = flags;
            return this;

        }

        /**
         * Overrides the builder internal length value with the provided value.
         * <p>
         * This value is not optional.
         * <p>
         * Please refer to the {@link CurrentDirectoryDriveLetter#length} field for documentation.
         *
         * @param length the length
         * @return the builder instance
         */
        public Builder length(final short length) {

            this.length = length;
            return this;

        }

        /**
         * Overrides the builder internal time stamp value with the provided value.
         * <p>
         * This value is not optional.
         * <p>
         * Please refer to the {@link CurrentDirectoryDriveLetter#timeStamp} field for documentation.
         *
         * @param timeStamp the time stamp
         * @return the builder instance
         */
        public Builder timeStamp(final int timeStamp) {

            this.timeStamp = timeStamp;
            return this;

        }

        /**
         * Overrides the builder internal dos path value with the provided value.
         * <p>
         * This value is not optional.
         * <p>
         * Please refer to the {@link CurrentDirectoryDriveLetter#dosPath} field for documentation.
         *
         * @param dosPath the dos path {@link CurrentDirectoryDriveLetter#dosPath}
         * @return the builder instance
         */
        public Builder dosPath(@NotNull final MemorySegment dosPath) {

            this.dosPath = dosPath;
            return this;

        }

        @NotNull
        @Override
        public CurrentDirectoryDriveLetter build() {

            Preconditions.checkNotNull(this.flags);
            Preconditions.checkNotNull(this.length);
            Preconditions.checkNotNull(this.timeStamp);
            Preconditions.checkNotNull(this.dosPath);
            return new CurrentDirectoryDriveLetter(this);

        }

    }

}
