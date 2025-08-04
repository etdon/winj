package com.etdon.winj.type;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.jbinder.common.MemorySegmentable;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static com.etdon.winj.type.NativeDataType.*;
import static java.lang.foreign.ValueLayout.*;

public final class ProcessCreateSuccess implements MemorySegmentable {

    public static final MemoryLayout PS_CREATE_SUCCESS = MemoryLayout.structLayout(
            ULONG.withName("OutputFlags"),
            MemoryLayout.paddingLayout(4),
            HANDLE.withName("FileHandle"),
            HANDLE.withName("SectionHandle"),
            ULONGLONG.withName("UserProcessParametersNative"),
            ULONG.withName("UserProcessParametersWow64"),
            ULONG.withName("CurrentParameterFlags"),
            ULONGLONG.withName("PebAddressNative"),
            ULONG.withName("PebAddressWow64"),
            MemoryLayout.paddingLayout(4),
            ULONGLONG.withName("ManifestAddress"),
            ULONG.withName("ManifestSize"),
            MemoryLayout.paddingLayout(4)
    );

    private int outputFlags;

    private MemorySegment fileHandle;

    private MemorySegment sectionHandle;

    private long userProcessParametersNative;

    private int userProcessParametersWow64;

    private int currentParametersFlags;

    private long pebAddressNative;

    private int pebAddressWow64;

    private long manifestAddress;

    private int manifestSize;

    public ProcessCreateSuccess(final int outputFlags,
                                @NotNull final MemorySegment fileHandle,
                                @NotNull final MemorySegment sectionHandle,
                                final long userProcessParametersNative,
                                final int userProcessParametersWow64,
                                final int currentParametersFlags,
                                final long pebAddressNative,
                                final int pebAddressWow64,
                                final long manifestAddress,
                                final int manifestSize) {

        this.outputFlags = outputFlags;
        this.fileHandle = fileHandle;
        this.sectionHandle = sectionHandle;
        this.userProcessParametersNative = userProcessParametersNative;
        this.userProcessParametersWow64 = userProcessParametersWow64;
        this.currentParametersFlags = currentParametersFlags;
        this.pebAddressNative = pebAddressNative;
        this.pebAddressWow64 = pebAddressWow64;
        this.manifestAddress = manifestAddress;
        this.manifestSize = manifestSize;

    }

    public ProcessCreateSuccess(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(PS_CREATE_SUCCESS.byteSize(), arena, null);

        this.outputFlags = memorySegment.get(JAVA_INT, 0);
        this.fileHandle = memorySegment.get(ADDRESS, 4);
        this.sectionHandle = memorySegment.get(ADDRESS, 12);
        this.userProcessParametersNative = memorySegment.get(JAVA_LONG, 20);
        this.userProcessParametersWow64 = memorySegment.get(JAVA_INT, 28);
        this.currentParametersFlags = memorySegment.get(JAVA_INT, 32);
        this.pebAddressNative = memorySegment.get(JAVA_LONG, 36);
        this.pebAddressWow64 = memorySegment.get(JAVA_INT, 44);
        this.manifestAddress = memorySegment.get(JAVA_LONG, 48);
        this.manifestSize = memorySegment.get(JAVA_INT, 56);

    }

    private ProcessCreateSuccess(@NotNull final Builder builder) {

        Conditional.executeIfNotNull(builder.outputFlags, () -> this.outputFlags = builder.outputFlags);
        Conditional.executeIfNotNull(builder.fileHandle, () -> this.fileHandle = builder.fileHandle);
        Conditional.executeIfNotNull(builder.sectionHandle, () -> this.sectionHandle = builder.sectionHandle);
        Conditional.executeIfNotNull(builder.userProcessParametersNative, () -> this.userProcessParametersNative = builder.userProcessParametersNative);
        Conditional.executeIfNotNull(builder.userProcessParametersWow64, () -> this.userProcessParametersWow64 = builder.pebAddressWow64);
        Conditional.executeIfNotNull(builder.currentParametersFlags, () -> this.currentParametersFlags = builder.currentParametersFlags);
        Conditional.executeIfNotNull(builder.pebAddressNative, () -> this.pebAddressNative = builder.pebAddressNative);
        Conditional.executeIfNotNull(builder.pebAddressWow64, () -> this.pebAddressWow64 = builder.pebAddressWow64);
        Conditional.executeIfNotNull(builder.manifestAddress, () -> this.manifestAddress = builder.manifestAddress);
        Conditional.executeIfNotNull(builder.manifestSize, () -> this.manifestSize = builder.manifestSize);

    }

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment memorySegment = arena.allocate(PS_CREATE_SUCCESS.byteSize());
        memorySegment.set(JAVA_INT, 0, this.outputFlags);
        memorySegment.set(ADDRESS, 4, this.fileHandle);
        memorySegment.set(ADDRESS, 12, this.sectionHandle);
        memorySegment.set(JAVA_LONG, 20, this.userProcessParametersNative);
        memorySegment.set(JAVA_INT, 28, this.userProcessParametersWow64);
        memorySegment.set(JAVA_INT, 32, this.currentParametersFlags);
        memorySegment.set(JAVA_LONG, 36, this.pebAddressNative);
        memorySegment.set(JAVA_INT, 44, this.pebAddressWow64);
        memorySegment.set(JAVA_LONG, 48, this.manifestAddress);
        memorySegment.set(JAVA_INT, 56, this.manifestSize);

        return memorySegment;

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<ProcessCreateSuccess> {

        private Integer outputFlags;
        private MemorySegment fileHandle;
        private MemorySegment sectionHandle;
        private Long userProcessParametersNative;
        private Integer userProcessParametersWow64;
        private Integer currentParametersFlags;
        private Long pebAddressNative;
        private Integer pebAddressWow64;
        private Long manifestAddress;
        private Integer manifestSize;

        public Builder outputFlags(final int outputFlags) {

            this.outputFlags = outputFlags;
            return this;

        }

        public Builder fileHandle(@NotNull final MemorySegment fileHandle) {

            this.fileHandle = fileHandle;
            return this;

        }

        public Builder sectionHandle(@NotNull final MemorySegment sectionHandle) {


            this.sectionHandle = sectionHandle;
            return this;

        }

        public Builder userProcessParametersNative(final long userProcessParametersNative) {

            this.userProcessParametersNative = userProcessParametersNative;
            return this;

        }

        public Builder userProcessParametersWow64(final int userProcessParametersWow64) {

            this.userProcessParametersWow64 = userProcessParametersWow64;
            return this;

        }

        public Builder currentParametersFlags(final int currentParametersFlags) {

            this.currentParametersFlags = currentParametersFlags;
            return this;

        }

        public Builder pebAddressNative(final long pebAddressNative) {

            this.pebAddressNative = pebAddressNative;
            return this;

        }

        public Builder pebAddressWow64(final int pebAddressWow64) {

            this.pebAddressWow64 = pebAddressWow64;
            return this;

        }

        public Builder manifestAddress(final long manifestAddress) {

            this.manifestAddress = manifestAddress;
            return this;

        }

        public Builder manifestSize(final int manifestSize) {

            this.manifestSize = manifestSize;
            return this;

        }

        @NotNull
        @Override
        public ProcessCreateSuccess build() {

            return new ProcessCreateSuccess(this);

        }

    }

}
