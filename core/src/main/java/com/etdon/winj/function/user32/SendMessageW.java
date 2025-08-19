package com.etdon.winj.function.user32;

import com.etdon.commons.builder.FluentBuilder;
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
 * Sends the specified message to a window or windows. The SendMessage function calls the window procedure for the
 * specified window and does not return until the window procedure has processed the message.
 * <p>
 * To send a message and return immediately, use the SendMessageCallback or SendNotifyMessage function. To post a
 * message to a thread's message queue and return immediately, use the PostMessage or PostThreadMessage function.
 */
@NativeName(SendMessageW.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-sendmessagew")
public final class SendMessageW extends NativeFunction<MemorySegment> {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "SendMessageW";
    public static final FunctionDescriptor SEND_MESSAGE_W_SIGNATURE = FunctionDescriptor.of(
            LRESULT,
            HWND.withName("hWnd"),
            UINT.withName("Msg"),
            WPARAM.withName("wParam"),
            LPARAM.withName("lParam")
    );

    /**
     * A handle to the window whose window procedure will receive the message. If this parameter is HWND_BROADCAST
     * ((HWND)0xffff), the message is sent to all top-level windows in the system, including disabled or invisible
     * unowned windows, overlapped windows, and pop-up windows; but the message is not sent to child windows.
     * <p>
     * Message sending is subject to UIPI. The thread of a process can send messages only to message queues of threads
     * in processes of lesser or equal integrity level.
     *
     * @see WindowHandle
     */
    @NativeName("hWnd")
    private final MemorySegment windowHandle;

    /**
     * The message to be sent.
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

    private SendMessageW(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, SEND_MESSAGE_W_SIGNATURE);

        this.windowHandle = builder.windowHandle;
        this.message = builder.message;
        this.firstParameter = builder.firstParameter;
        this.secondParameter = builder.secondParameter;

    }

    @Override
    public MemorySegment call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return (MemorySegment) super.obtainHandle(linker, symbolLookup).invoke(
                this.windowHandle,
                this.message,
                this.firstParameter,
                this.secondParameter
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<SendMessageW> {

        private MemorySegment windowHandle;
        private Integer message;
        private Long firstParameter;
        private Long secondParameter;

        private Builder() {

        }

        /**
         * Overrides the builder internal windowHandle value with the provided value.
         *
         * @param windowHandle The windowHandle. {@link SendMessageW#windowHandle}
         * @return The builder instance.
         */
        public Builder windowHandle(@NotNull final MemorySegment windowHandle) {

            this.windowHandle = windowHandle;
            return this;

        }

        /**
         * Overrides the builder internal message value with the provided value.
         *
         * @param message The message. {@link SendMessageW#message}
         * @return The builder instance.
         */
        public Builder message(final int message) {

            this.message = message;
            return this;

        }

        /**
         * Overrides the builder internal firstParameter value with the provided value.
         *
         * @param firstParameter The firstParameter. {@link SendMessageW#firstParameter}
         * @return The builder instance.
         */
        public Builder firstParameter(final long firstParameter) {

            this.firstParameter = firstParameter;
            return this;

        }

        /**
         * Overrides the builder internal secondParameter value with the provided value.
         *
         * @param secondParameter The secondParameter. {@link SendMessageW#secondParameter}
         * @return The builder instance.
         */
        public Builder secondParameter(final long secondParameter) {

            this.secondParameter = secondParameter;
            return this;

        }

        @NotNull
        @Override
        public SendMessageW build() {

            Preconditions.checkNotNull(this.windowHandle);
            Preconditions.checkNotNull(this.message);
            Preconditions.checkNotNull(this.firstParameter);
            Preconditions.checkNotNull(this.secondParameter);

            return new SendMessageW(this);

        }

    }

}
