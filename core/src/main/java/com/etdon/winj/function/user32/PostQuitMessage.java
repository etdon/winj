package com.etdon.winj.function.user32;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;

public final class PostQuitMessage extends NativeFunction {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "PostQuitMessage";
    public static final FunctionDescriptor POST_QUIT_MESSAGE_SIGNATURE = FunctionDescriptor.ofVoid(
            INTEGER
    );

    /**
     * The application exit code. This value is used as the wParam parameter of the WM_QUIT message.
     */
    private final int exitCode;

    private PostQuitMessage(@NotNull final Builder builder) {

        super(LIBRARY, NATIVE_NAME, POST_QUIT_MESSAGE_SIGNATURE);

        this.exitCode = builder.exitCode;

    }

    private PostQuitMessage(final int exitCode) {

        super(LIBRARY, NATIVE_NAME, POST_QUIT_MESSAGE_SIGNATURE);

        this.exitCode = exitCode;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.exitCode
        );

    }

    public static PostQuitMessage ofExitCode(final int exitCode) {

        return new PostQuitMessage(exitCode);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<PostQuitMessage> {

        private Integer exitCode;

        private Builder() {

        }

        /**
         * Overrides the builder internal exit code value with the provided value.
         *
         * @param exitCode The exit code. {@link PostQuitMessage#exitCode}
         * @return The builder instance.
         */
        public Builder exitCode(final int exitCode) {

            this.exitCode = exitCode;
            return this;

        }

        @NotNull
        @Override
        public PostQuitMessage build() {

            Preconditions.checkNotNull(this.exitCode);

            return new PostQuitMessage(this);

        }

    }

}
