package com.etdon.winj.function.user32;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.NativeDocumentation;
import com.etdon.jbinder.common.NativeName;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.WindowHandle;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;

/**
 * Places (posts) a message in the message queue associated with the thread that created the specified window and
 * returns without waiting for the thread to process the message.
 * <p>
 * To post a message in the message queue associated with a thread, use the PostThreadMessage function.
 */
@NativeName(PostMessageW.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-postmessagew")
public final class PostMessageW extends NativeFunction<Integer> {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "PostMessageW";
    public static final FunctionDescriptor POST_MESSAGE_W_SIGNATURE = FunctionDescriptor.of(
            BOOL,
            HWND.withName("hWnd"),
            UINT.withName("Msg"),
            WPARAM.withName("wParam"),
            LPARAM.withName("lParam")
    );

    /**
     * A handle to the window whose window procedure is to receive the message. The following values have special
     * meanings:
     * <p>
     * HWND_BROADCAST((HWND)0xffff)
     * <p>
     * The message is posted to all top-level windows in the system, including disabled or invisible unowned windows,
     * overlapped windows, and pop-up windows. The message is not posted to child windows.
     * <p>
     * NULL
     * <p>
     * The function behaves like a call to PostThreadMessage with the dwThreadId parameter set to the identifier of the
     * current thread.
     * <p>
     * Starting with Windows Vista, message posting is subject to UIPI. The thread of a process can post messages only
     * to message queues of threads in processes of lesser or equal integrity level.
     *
     * @see WindowHandle
     */
    @NativeName("hWnd")
    private MemorySegment windowHandle = MemorySegment.NULL;

    /**
     * The message to be posted.
     */
    @NativeName("Msg")
    private final int message;

    /**
     * Additional message-specific information.
     */
    @NativeName("wParam")
    private final long firstParameter;

    /**
     * Additional message-specific information.
     */
    @NativeName("lParam")
    private final long secondParameter;

    private PostMessageW(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, POST_MESSAGE_W_SIGNATURE);

        Conditional.executeIfNotNull(builder.windowHandle, () -> this.windowHandle = builder.windowHandle);
        this.message = builder.message;
        this.firstParameter = builder.firstParameter;
        this.secondParameter = builder.secondParameter;

    }

    @Override
    public Integer call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return (Integer) super.obtainHandle(linker, symbolLookup).invoke(
                this.windowHandle,
                this.message,
                this.firstParameter,
                this.secondParameter
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<PostMessageW> {

        private MemorySegment windowHandle;
        private Integer message;
        private Long firstParameter;
        private Long secondParameter;

        private Builder() {

        }

        /**
         * Overrides the builder internal windowHandle value with the provided value.
         *
         * @param windowHandle The windowHandle. {@link PostMessageW#windowHandle}
         * @return The builder instance.
         */
        public Builder windowHandle(@NotNull final MemorySegment windowHandle) {

            this.windowHandle = windowHandle;
            return this;

        }

        /**
         * Overrides the builder internal message value with the provided value.
         *
         * @param message The message. {@link PostMessageW#message}
         * @return The builder instance.
         */
        public Builder message(final int message) {

            this.message = message;
            return this;

        }

        /**
         * Overrides the builder internal firstParameter value with the provided value.
         *
         * @param firstParameter The firstParameter. {@link PostMessageW#firstParameter}
         * @return The builder instance.
         */
        public Builder firstParameter(final long firstParameter) {

            this.firstParameter = firstParameter;
            return this;

        }

        /**
         * Overrides the builder internal secondParameter value with the provided value.
         *
         * @param secondParameter The secondParameter. {@link PostMessageW#secondParameter}
         * @return The builder instance.
         */
        public Builder secondParameter(final long secondParameter) {

            this.secondParameter = secondParameter;
            return this;

        }

        @NotNull
        @Override
        public PostMessageW build() {

            Preconditions.checkNotNull(this.message);
            Preconditions.checkNotNull(this.firstParameter);
            Preconditions.checkNotNull(this.secondParameter);

            return new PostMessageW(this);

        }

    }

}
