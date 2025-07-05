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

import static com.etdon.winj.type.NativeDataType.*;

public final class DispatchMessageW extends NativeFunction {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "DispatchMessageW";
    public static final FunctionDescriptor DISPATCH_MESSAGE_W_SIGNATURE = FunctionDescriptor.of(
            LRESULT,
            MSG
    );

    /**
     * A pointer to a structure that contains the message.
     */
    private final MemorySegment messagePointer;

    private DispatchMessageW(@NotNull final Builder builder) {

        super(LIBRARY, NATIVE_NAME, DISPATCH_MESSAGE_W_SIGNATURE);

        this.messagePointer = builder.messagePointer;

    }

    private DispatchMessageW(@NotNull final MemorySegment messagePointer) {

        super(LIBRARY, NATIVE_NAME, DISPATCH_MESSAGE_W_SIGNATURE);

        this.messagePointer = messagePointer;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.messagePointer
        );

    }

    public static DispatchMessageW ofMessagePointer(@NotNull final MemorySegment messagePointer) {

        return new DispatchMessageW(messagePointer);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<DispatchMessageW> {

        private MemorySegment messagePointer;

        private Builder() {

        }

        /**
         * Overrides the builder internal message pointer value with the provided value.
         *
         * @param messagePointer The message pointer. {@link DispatchMessageW#messagePointer}
         * @return The builder instance.
         */
        public Builder messagePointer(@NotNull final MemorySegment messagePointer) {

            Preconditions.checkNotNull(messagePointer);
            this.messagePointer = messagePointer;
            return this;

        }

        @NotNull
        @Override
        public DispatchMessageW build() {

            Preconditions.checkNotNull(this.messagePointer);

            return new DispatchMessageW(this);

        }

    }

}
