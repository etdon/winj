package com.etdon.winj.function.user32;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import com.etdon.winj.type.input.Input;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;

public final class SendInput extends NativeFunction {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "SendInput";
    public static final FunctionDescriptor SEND_INPUT_SIGNATURE = FunctionDescriptor.of(
            UINT,
            UINT,
            LPINPUT,
            INTEGER
    );

    /**
     * The number of structures in the pInputs array.
     */
    private final int inputCount;

    /**
     * An array of INPUT structures. Each structure represents an event to be inserted into the keyboard or mouse input
     * stream.
     */
    private final MemorySegment inputArray;

    /**
     * The size, in bytes, of an INPUT structure. If cbSize is not the size of an INPUT structure, the function fails.
     */
    private final int inputByteSize;

    private SendInput(@NotNull final Builder builder) {

        super(LIBRARY, NATIVE_NAME, SEND_INPUT_SIGNATURE);

        this.inputCount = builder.inputCount;
        this.inputArray = builder.inputArray;
        this.inputByteSize = builder.inputByteSize;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.inputCount,
                this.inputArray,
                this.inputByteSize
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<SendInput> {

        private Integer inputCount;
        private MemorySegment inputArray;
        private Integer inputByteSize;

        private Builder() {

        }

        /**
         * Overrides the builder internal input count value with the provided value.
         *
         * @param inputCount The input count. {@link SendInput#inputCount}
         * @return The builder instance.
         */
        public Builder inputCount(final int inputCount) {

            this.inputCount = inputCount;
            return this;

        }

        /**
         * Overrides the builder internal input count value, input array value and input byte size based on the
         * provided value.
         *
         * @param inputArray The input array. {@link SendInput#inputArray}
         * @return The builder instance.
         */
        public Builder sizedInputArray(@NotNull final MemorySegment inputArray) {

            this.inputCount((int) (inputArray.byteSize() / Input.INPUT.byteSize()));
            this.inputArray(inputArray);
            this.inputByteSize((int) Input.INPUT.byteSize());
            return this;

        }

        /**
         * Overrides the builder internal input array value with the provided value.
         *
         * @param inputArray The input array. {@link SendInput#inputArray}
         * @return The builder instance.
         */
        public Builder inputArray(@NotNull final MemorySegment inputArray) {

            this.inputArray = inputArray;
            return this;

        }

        /**
         * Overrides the builder internal input byte size value with the provided value.
         *
         * @param inputByteSize The input byte size. {@link SendInput#inputByteSize}
         * @return The builder instance.
         */
        public Builder inputByteSize(final int inputByteSize) {

            this.inputByteSize = inputByteSize;
            return this;

        }

        @NotNull
        @Override
        public SendInput build() {

            Preconditions.checkNotNull(this.inputCount);
            Preconditions.checkNotNull(this.inputArray);
            Preconditions.checkNotNull(this.inputByteSize);

            return new SendInput(this);

        }

    }

}
