package com.etdon.winj.function.user32;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;

public final class FindWindowExW extends NativeFunction {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "FindWindowExW";
    public static final FunctionDescriptor FIND_WINDOW_EX_W_SIGNATURE = FunctionDescriptor.of(
            HWND,
            HWND,
            LPCWSTR,
            LPCWSTR
    );

    private MemorySegment parentWindowHandle = MemorySegment.NULL;

    private MemorySegment childAfterWindowHandle = MemorySegment.NULL;

    private MemorySegment className = MemorySegment.NULL;

    private MemorySegment windowName = MemorySegment.NULL;

    private FindWindowExW(@NotNull final Builder builder) {

        super(LIBRARY, NATIVE_NAME, FIND_WINDOW_EX_W_SIGNATURE);

        Conditional.executeIfNotNull(builder.parentWindowHandle, () -> this.parentWindowHandle = builder.parentWindowHandle);
        Conditional.executeIfNotNull(builder.childAfterWindowHandle, () -> this.childAfterWindowHandle = builder.childAfterWindowHandle);
        Conditional.executeIfNotNull(builder.className, () -> this.className = builder.className);
        Conditional.executeIfNotNull(builder.windowName, () -> this.windowName = builder.windowName);

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.parentWindowHandle,
                this.childAfterWindowHandle,
                this.className,
                this.windowName
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<FindWindowExW> {

        private MemorySegment parentWindowHandle;
        private MemorySegment childAfterWindowHandle;
        private MemorySegment className;
        private MemorySegment windowName;

        private Builder() {

        }

        /**
         * Overrides the builder internal parentWindowHandle value with the provided value.
         *
         * @param parentWindowHandle The parentWindowHandle. {@link FindWindowExW#parentWindowHandle}
         * @return The builder instance.
         */
        public Builder parentWindowHandle(@NotNull final MemorySegment parentWindowHandle) {

            this.parentWindowHandle = parentWindowHandle;
            return this;

        }

        /**
         * Overrides the builder internal childAfterWindowHandle value with the provided value.
         *
         * @param childAfterWindowHandle The childAfterWindowHandle. {@link FindWindowExW#childAfterWindowHandle}
         * @return The builder instance.
         */
        public Builder childAfterWindowHandle(@NotNull final MemorySegment childAfterWindowHandle) {

            this.childAfterWindowHandle = childAfterWindowHandle;
            return this;

        }

        /**
         * Overrides the builder internal className value with the provided value.
         *
         * @param className The className. {@link FindWindowExW#className}
         * @return The builder instance.
         */
        public Builder className(@NotNull final MemorySegment className) {

            this.className = className;
            return this;

        }

        /**
         * Overrides the builder internal windowName value with the provided value.
         *
         * @param windowName The windowName. {@link FindWindowExW#windowName}
         * @return The builder instance.
         */
        public Builder windowName(@NotNull final MemorySegment windowName) {

            this.windowName = windowName;
            return this;

        }

        @NotNull
        @Override
        public FindWindowExW build() {

            return new FindWindowExW(this);

        }

    }

}