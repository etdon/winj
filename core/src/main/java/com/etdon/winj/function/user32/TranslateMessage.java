package com.etdon.winj.function.user32;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;

public final class TranslateMessage extends NativeFunction {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "TranslateMessage";
    public static final FunctionDescriptor TRANSLATE_MESSAGE_SIGNATURE = FunctionDescriptor.of(
            BOOL,
            MSG
    );

    /**
     * A pointer to an MSG structure that contains message information retrieved from the calling thread's message
     * queue by using the GetMessage or PeekMessage function.
     */
    private final MemorySegment messagePointer;

    private TranslateMessage(@NotNull final Builder builder) {

        super(LIBRARY, NATIVE_NAME, TRANSLATE_MESSAGE_SIGNATURE);

        this.messagePointer = builder.messagePointer;

    }

    private TranslateMessage(@NotNull final MemorySegment messagePointer) {

        super(LIBRARY, NATIVE_NAME, TRANSLATE_MESSAGE_SIGNATURE);

        this.messagePointer = messagePointer;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.messagePointer
        );

    }

    public static TranslateMessage ofMessagePointer(@NotNull final MemorySegment messagePointer) {

        return new TranslateMessage(messagePointer);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<TranslateMessage> {

        private MemorySegment messagePointer;

        private Builder() {

        }

        /**
         * Overrides the builder internal message pointer value with the provided value.
         *
         * @param messagePointer The message pointer. {@link TranslateMessage#messagePointer}
         * @return The builder instance.
         */
        public Builder messagePointer(@NotNull final MemorySegment messagePointer) {

            Preconditions.checkNotNull(messagePointer);
            this.messagePointer = messagePointer;
            return this;

        }

        @NotNull
        @Override
        public TranslateMessage build() {

            Preconditions.checkNotNull(this.messagePointer);

            return new TranslateMessage(this);

        }

    }

}
