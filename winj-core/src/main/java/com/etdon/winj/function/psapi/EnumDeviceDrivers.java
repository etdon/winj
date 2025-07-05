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

public final class EnumDeviceDrivers extends NativeFunction {

    public static final String LIBRARY = Library.PSAPI;
    public static final String NATIVE_NAME = "EnumDeviceDrivers";
    public static final FunctionDescriptor ENUM_DEVICE_DRIVERS_SIGNATURE = FunctionDescriptor.of(
            BOOL,
            LPVOID,
            DWORD,
            LPDWORD
    );

    /**
     * An array that receives the list of load addresses for the device drivers.
     */
    private final MemorySegment driverArrayPointer;

    /**
     * The size of the lpImageBase array, in bytes. If the array is not large enough to store the load addresses, the
     * lpcbNeeded parameter receives the required size of the array.
     */
    private final int arraySize;

    /**
     * The number of bytes returned in the lpImageBase array.
     */
    private final MemorySegment bytesNeededPointer;

    private EnumDeviceDrivers(@NotNull final Builder builder) {

        super(LIBRARY, NATIVE_NAME, ENUM_DEVICE_DRIVERS_SIGNATURE);

        this.driverArrayPointer = builder.driverArrayPointer;
        this.arraySize = builder.arraySize;
        this.bytesNeededPointer = builder.bytesNeededPointer;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.driverArrayPointer,
                this.arraySize,
                this.bytesNeededPointer
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<EnumDeviceDrivers> {

        private MemorySegment driverArrayPointer;
        private Integer arraySize;
        private MemorySegment bytesNeededPointer;

        private Builder() {

        }

        /**
         * Overrides the builder internal driver array pointer value and array size value based on the provided value.
         *
         * @param driverArrayPointer The driver array.
         * @return The builder instance;
         */
        public Builder sizedDriverArrayPointer(@NotNull final MemorySegment driverArrayPointer) {

            this.driverArrayPointer(driverArrayPointer);
            this.arraySize((int) driverArrayPointer.byteSize());
            return this;

        }

        /**
         * Overrides the builder internal drivers array pointer value with the provided value.
         *
         * @param driverArrayPointer The driver array pointer.. {@link EnumDeviceDrivers#driverArrayPointer}
         * @return The builder instance.
         */
        public Builder driverArrayPointer(@NotNull final MemorySegment driverArrayPointer) {

            this.driverArrayPointer = driverArrayPointer;
            return this;

        }

        /**
         * Overrides the builder internal array size value with the provided value.
         *
         * @param arraySize The array size. {@link EnumDeviceDrivers#arraySize}
         * @return The builder instance.
         */
        public Builder arraySize(final int arraySize) {

            this.arraySize = arraySize;
            return this;

        }

        /**
         * Overrides the builder internal bytes needed pointer value with the provided value.
         *
         * @param bytesNeededPointer The bytes needed pointer. {@link EnumDeviceDrivers#bytesNeededPointer}
         * @return The builder instance.
         */
        public Builder bytesNeededPointer(@NotNull final MemorySegment bytesNeededPointer) {

            this.bytesNeededPointer = bytesNeededPointer;
            return this;

        }

        @NotNull
        @Override
        public EnumDeviceDrivers build() {

            Preconditions.checkNotNull(this.driverArrayPointer);
            Preconditions.checkNotNull(this.arraySize);
            Preconditions.checkNotNull(this.bytesNeededPointer);

            return new EnumDeviceDrivers(this);

        }

    }

}
