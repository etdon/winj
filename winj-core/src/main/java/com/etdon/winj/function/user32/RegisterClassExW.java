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

import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_INT;

public final class RegisterClassExW extends NativeFunction {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "RegisterClassExW";
    public static final FunctionDescriptor REGISTER_CLASS_EX_W_SIGNATURE = FunctionDescriptor.of(
            JAVA_INT,
            ADDRESS.withName("unnamedParam1")
    );

    /**
     * A pointer to a WNDCLASSEX structure. You must fill the structure with the appropriate class attributes before
     * passing it to the function.
     */
    private final MemorySegment windowClassPointer;

    private RegisterClassExW(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, REGISTER_CLASS_EX_W_SIGNATURE);

        this.windowClassPointer = builder.windowClassPointer;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(this.windowClassPointer);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<RegisterClassExW> {

        private MemorySegment windowClassPointer;

        private Builder() {

        }

        /**
         * Overrides the builder internal window class pointer value with the provided value.
         *
         * @param windowClassPointer The window class pointer. {@link RegisterClassExW#windowClassPointer}
         * @return The builder instance.
         */
        public Builder windowClassPointer(@NotNull final MemorySegment windowClassPointer) {

            this.windowClassPointer = windowClassPointer;
            return this;

        }

        @NotNull
        @Override
        public RegisterClassExW build() {

            Preconditions.checkNotNull(this.windowClassPointer);

            return new RegisterClassExW(this);

        }

    }

}
