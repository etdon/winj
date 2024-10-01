package com.etdon.winj.function.psapi;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.NativeDataType.*;

public final class GetDeviceDriverFileNameW extends NativeFunction {

    public static final String LIBRARY = Library.PSAPI;
    public static final String NATIVE_NAME = "GetDeviceDriverFileNameW";
    public static final FunctionDescriptor GET_DEVICE_DRIVER_FILE_NAME_W_SIGNATURE = FunctionDescriptor.of(
            DWORD,
            LPVOID,
            LPWSTR,
            DWORD
    );

    /**
     * The load address of the device driver.
     */
    private final MemorySegment driverAddress;

    /**
     * A pointer to the buffer that receives the path to the device driver.
     */
    private final MemorySegment fileNameBufferPointer;

    /**
     * The size of the lpFilename buffer, in characters. If the buffer is not large enough to store the path plus the
     * terminating null character, the string is truncated.
     */
    private final int fileNameBufferSize;

    private GetDeviceDriverFileNameW(@NotNull final Builder builder) {

        super(LIBRARY, NATIVE_NAME, GET_DEVICE_DRIVER_FILE_NAME_W_SIGNATURE);

        this.driverAddress = builder.driverAddress;
        this.fileNameBufferPointer = builder.fileNameBufferPointer;
        this.fileNameBufferSize = builder.fileNameBufferSize;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.driverAddress,
                this.fileNameBufferPointer,
                this.fileNameBufferSize
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<GetDeviceDriverFileNameW> {

        private MemorySegment driverAddress;
        private MemorySegment fileNameBufferPointer;
        private Integer fileNameBufferSize;

        private Builder() {

        }

        /**
         * Overrides the builder internal driver address value with the provided value.
         *
         * @param driverAddress The driver address. {@link GetDeviceDriverFileNameW#driverAddress}
         * @return The builder instance.
         */
        public Builder driverAddress(@NotNull final MemorySegment driverAddress) {

            Preconditions.checkNotNull(driverAddress);
            this.driverAddress = driverAddress;
            return this;

        }

        /**
         * Overrides the builder internal file name buffer pointer value and file name buffer size based on the
         * provided value.
         *
         * @param fileNameBufferPointer The file name buffer pointer. {@link GetDeviceDriverFileNameW#fileNameBufferPointer}
         * @return The builder instance.
         */
        public Builder sizedFileNameBufferPointer(@NotNull final MemorySegment fileNameBufferPointer) {

            Preconditions.checkNotNull(fileNameBufferPointer);
            this.fileNameBufferPointer(fileNameBufferPointer);
            this.fileNameBufferSize((int) fileNameBufferPointer.byteSize());
            return this;

        }

        /**
         * Overrides the builder internal file name buffer pointer value with the provided value.
         *
         * @param fileNameBufferPointer The file name buffer pointer. {@link GetDeviceDriverFileNameW#fileNameBufferPointer}
         * @return The builder instance.
         */
        public Builder fileNameBufferPointer(@NotNull final MemorySegment fileNameBufferPointer) {

            Preconditions.checkNotNull(fileNameBufferPointer);
            this.fileNameBufferPointer = fileNameBufferPointer;
            return this;

        }

        /**
         * Overrides the builder internal file name buffer size value with the provided value.
         *
         * @param fileNameBufferSize The file name buffer size. {@link GetDeviceDriverFileNameW#fileNameBufferSize}
         * @return The builder instance.
         */
        public Builder fileNameBufferSize(final int fileNameBufferSize) {

            this.fileNameBufferSize = fileNameBufferSize;
            return this;

        }

        @NotNull
        @Override
        public GetDeviceDriverFileNameW build() {

            Preconditions.checkNotNull(this.driverAddress);
            Preconditions.checkNotNull(this.fileNameBufferPointer);
            Preconditions.checkNotNull(this.fileNameBufferSize);

            return new GetDeviceDriverFileNameW(this);

        }

    }

}
