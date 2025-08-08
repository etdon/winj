package com.etdon.winj.function.user32;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.*;
import java.nio.charset.StandardCharsets;

import static com.etdon.winj.type.constant.NativeDataType.*;
import static java.lang.foreign.ValueLayout.JAVA_INT;

public final class DrawTextW extends NativeFunction {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "DrawTextW";
    public static final FunctionDescriptor DRAW_TEXT_W_SIGNATURE = FunctionDescriptor.of(
            JAVA_INT,
            HDC.withName("hdc"),
            LPCWSTR.withName("lpchText"),
            JAVA_INT.withName("cchText"),
            LPRECT.withName("lprc"),
            JAVA_INT.withName("format")
    );

    /**
     * A handle to the device context.
     */
    private final MemorySegment deviceContextHandle;

    /**
     * A pointer to the string that specifies the text to be drawn. If the nCount parameter is -1, the string must be
     * null-terminated.
     */
    private final MemorySegment textPointer;

    /**
     * The length, in characters, of the string. If nCount is -1, then the lpchText parameter is assumed to be a
     * pointer to a null-terminated string and DrawText computes the character count automatically.
     */
    private final int textLength;

    /**
     * A pointer to a RECT structure that contains the rectangle (in logical coordinates) in which the text is to be
     * formatted.
     */
    private final MemorySegment rectanglePointer;

    /**
     * The method of formatting the text.
     */
    private final int format;

    private DrawTextW(@NotNull final Builder builder) {

        super(LIBRARY, NATIVE_NAME, DRAW_TEXT_W_SIGNATURE);

        this.deviceContextHandle = builder.deviceContextHandle;
        this.textPointer = builder.textPointer;
        this.textLength = builder.textLength;
        this.rectanglePointer = builder.rectanglePointer;
        this.format = builder.format;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.deviceContextHandle,
                this.textPointer,
                this.textLength,
                this.rectanglePointer,
                this.format
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<DrawTextW> {

        private MemorySegment deviceContextHandle;
        private MemorySegment textPointer;
        private Integer textLength;
        private MemorySegment rectanglePointer;
        private Integer format;

        private Builder() {

        }

        /**
         * Overrides the builder internal device context handle value with the provided value.
         *
         * @param deviceContextHandle The device context handle. {@link DrawTextW#deviceContextHandle}
         * @return The builder instance.
         */
        public Builder deviceContextHandle(@NotNull final MemorySegment deviceContextHandle) {

            this.deviceContextHandle = deviceContextHandle;
            return this;

        }

        /**
         * Overrides the builder internal text length value based on the provided text and allocates a new memory
         * segment in the provided arena to set the builder internal text pointer. This method simplifies the process
         * of setting the text by only requiring a single method call instead of one call to {@link DrawTextW#textPointer}
         * as well as one call to {@link DrawTextW#textLength}.
         *
         * @param arena The arena.
         * @param text  The text.
         * @return The builder instance.
         */
        public Builder text(@NotNull final Arena arena, @NotNull final String text) {

            this.textPointer(arena.allocateFrom(text, StandardCharsets.UTF_16LE));
            this.textLength(text.length());

            return this;

        }

        /**
         * Overrides the builder internal text pointer value with the provided value.
         *
         * @param textPointer The text pointer. {@link DrawTextW#textPointer}
         * @return The builder instance.
         */
        public Builder textPointer(@NotNull final MemorySegment textPointer) {

            this.textPointer = textPointer;
            return this;

        }

        /**
         * Overrides the builder internal text length value with the provided value.
         *
         * @param textLength The text length. {@link DrawTextW#textLength}
         * @return The builder instance.
         */
        public Builder textLength(final int textLength) {

            this.textLength = textLength;
            return this;

        }

        /**
         * Overrides the builder internal rectangle pointer value with the provided value.
         *
         * @param rectanglePointer The rectangle pointer. {@link DrawTextW#rectanglePointer}
         * @return The builder instance.
         */
        public Builder rectanglePointer(@NotNull final MemorySegment rectanglePointer) {

            this.rectanglePointer = rectanglePointer;
            return this;

        }

        /**
         * Overrides the builder internal format value with the provided value.
         *
         * @param format The format. {@link DrawTextW#format}
         * @return The builder instance.
         */
        public Builder format(final int format) {

            this.format = format;
            return this;

        }

        @NotNull
        @Override
        public DrawTextW build() {

            Preconditions.checkNotNull(this.deviceContextHandle);
            Preconditions.checkNotNull(this.textPointer);
            Preconditions.checkNotNull(this.textLength);
            Preconditions.checkNotNull(this.rectanglePointer);
            Preconditions.checkNotNull(this.format);

            return new DrawTextW(this);

        }

    }

}
