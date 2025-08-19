package com.etdon.winj.function.psapi;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.NativeDocumentation;
import com.etdon.jbinder.common.NativeName;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;

/**
 * Retrieves the base name of the specified device driver.
 */
@NativeName(GetDeviceDriverBaseNameW.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/psapi/nf-psapi-getdevicedriverbasenamew")
public final class GetDeviceDriverBaseNameW extends NativeFunction<Integer> {

    public static final String LIBRARY = Library.PSAPI;
    public static final String NATIVE_NAME = "GetDeviceDriverBaseNameW";
    public static final FunctionDescriptor GET_DEVICE_DRIVER_BASE_NAME_W_SIGNATURE = FunctionDescriptor.of(
            DWORD,
            LPVOID.withName("ImageBase"),
            LPWSTR.withName("lpBaseName"),
            DWORD.withName("nSize")
    );

    /**
     * The load address of the device driver. This value can be retrieved using the EnumDeviceDrivers function.
     */
    @NativeName("ImageBase")
    private final MemorySegment driverAddress;

    /**
     * A pointer to the buffer that receives the base name of the device driver.
     */
    @NativeName("lpBaseName")
    private final MemorySegment baseNameBufferPointer;

    /**
     * The size of the lpBaseName buffer, in characters. If the buffer is not large enough to store the base name plus
     * the terminating null character, the string is truncated.
     */
    @NativeName("nSize")
    private final int baseNameBufferSize;

    private GetDeviceDriverBaseNameW(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, GET_DEVICE_DRIVER_BASE_NAME_W_SIGNATURE);

        this.driverAddress = builder.driverAddress;
        this.baseNameBufferPointer = builder.baseNameBufferPointer;
        this.baseNameBufferSize = builder.baseNameBufferSize;

    }

    @Override
    public Integer call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return (Integer) super.obtainHandle(linker, symbolLookup).invoke(
                this.driverAddress,
                this.baseNameBufferPointer,
                this.baseNameBufferSize
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<GetDeviceDriverBaseNameW> {

        private MemorySegment driverAddress;
        private MemorySegment baseNameBufferPointer;
        private Integer baseNameBufferSize;

        private Builder() {

        }

        /**
         * Overrides the builder internal driver address value with the provided value.
         *
         * @param driverAddress The driver address. {@link GetDeviceDriverBaseNameW#driverAddress}
         * @return The builder instance.
         */
        public Builder driverAddress(@NotNull final MemorySegment driverAddress) {

            Preconditions.checkNotNull(driverAddress);
            this.driverAddress = driverAddress;
            return this;

        }

        /**
         * Overrides the builder internal base name buffer pointer and base name buffer size based on the provided
         * value.
         *
         * @param baseNameBufferPointer The base name buffer pointer. {@link GetDeviceDriverBaseNameW#baseNameBufferPointer}
         * @return The builder instance.
         */
        public Builder sizedBaseNameBufferPointer(@NotNull final MemorySegment baseNameBufferPointer) {

            Preconditions.checkNotNull(baseNameBufferPointer);
            this.baseNameBufferPointer(baseNameBufferPointer);
            this.baseNameBufferSize((int) baseNameBufferPointer.byteSize());
            return this;

        }

        /**
         * Overrides the builder internal base name buffer pointer value with the provided value.
         *
         * @param baseNameBufferPointer The base name buffer pointer. {@link GetDeviceDriverBaseNameW#baseNameBufferPointer}
         * @return The builder instance.
         */
        public Builder baseNameBufferPointer(@NotNull final MemorySegment baseNameBufferPointer) {

            Preconditions.checkNotNull(baseNameBufferPointer);
            this.baseNameBufferPointer = baseNameBufferPointer;
            return this;

        }

        /**
         * Overrides the builder internal base name buffer size value with the provided value.
         *
         * @param baseNameBufferSize The base name buffer size. {@link GetDeviceDriverBaseNameW#baseNameBufferSize}
         * @return The builder instance.
         */
        public Builder baseNameBufferSize(final int baseNameBufferSize) {

            this.baseNameBufferSize = baseNameBufferSize;
            return this;

        }

        @NotNull
        @Override
        public GetDeviceDriverBaseNameW build() {

            Preconditions.checkNotNull(this.driverAddress);
            Preconditions.checkNotNull(this.baseNameBufferPointer);
            Preconditions.checkNotNull(this.baseNameBufferSize);

            return new GetDeviceDriverBaseNameW(this);

        }

    }

}
