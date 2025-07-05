package com.etdon.winj.function.user32;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.NativeDataType.*;

public final class GetMessageW extends NativeFunction {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "GetMessageW";
    public static final FunctionDescriptor GET_MESSAGE_W_SIGNATURE = FunctionDescriptor.of(
            BOOL,
            LPMSG,
            HWND,
            UINT,
            UINT
    );

    /**
     * A pointer to an MSG structure that receives message information from the thread's message queue.
     */
    private final MemorySegment messagePointer;

    /**
     * A handle to the window whose messages are to be retrieved. The window must belong to the current thread.
     * <p>
     * If hWnd is NULL, GetMessage retrieves messages for any window that belongs to the current thread, and any
     * messages on the current thread's message queue whose hwnd value is NULL (see the MSG structure). Therefore if
     * hWnd is NULL, both window messages and thread messages are processed.
     * <p>
     * If hWnd is -1, GetMessage retrieves only messages on the current thread's message queue whose hwnd value is NULL,
     * that is, thread messages as posted by PostMessage (when the hWnd parameter is NULL) or PostThreadMessage.
     */
    private MemorySegment windowHandle = MemorySegment.NULL;

    /**
     * The integer value of the lowest message value to be retrieved. Use WM_KEYFIRST (0x0100) to specify the first
     * keyboard message or WM_MOUSEFIRST (0x0200) to specify the first mouse message.
     * <p>
     * Use WM_INPUT here and in wMsgFilterMax to specify only the WM_INPUT messages.
     * <p>
     * If wMsgFilterMin and wMsgFilterMax are both zero, GetMessage returns all available messages (that is, no range
     * filtering is performed).
     */
    private int lowestMessageId;

    /**
     * The integer value of the highest message value to be retrieved. Use WM_KEYLAST to specify the last keyboard
     * message or WM_MOUSELAST to specify the last mouse message.
     * <p>
     * Use WM_INPUT here and in wMsgFilterMin to specify only the WM_INPUT messages.
     * <p>
     * If wMsgFilterMin and wMsgFilterMax are both zero, GetMessage returns all available messages (that is, no range
     * filtering is performed).
     */
    private int highestMessageId;

    private GetMessageW(@NotNull final Builder builder) {

        super(LIBRARY, NATIVE_NAME, GET_MESSAGE_W_SIGNATURE);

        this.messagePointer = builder.messagePointer;
        Conditional.executeIfNotNull(builder.windowHandle, () -> this.windowHandle = builder.windowHandle);
        Conditional.executeIfNotNull(builder.lowestMessageId, () -> this.lowestMessageId = builder.lowestMessageId);
        Conditional.executeIfNotNull(builder.highestMessageId, () -> this.highestMessageId = builder.highestMessageId);

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.messagePointer,
                this.windowHandle,
                this.lowestMessageId,
                this.highestMessageId
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<GetMessageW> {

        private MemorySegment messagePointer;
        private MemorySegment windowHandle;
        private Integer lowestMessageId;
        private Integer highestMessageId;

        private Builder() {

        }

        /**
         * Overrides the builder internal message pointer value with the provided value.
         *
         * @param messagePointer The message pointer. {@link GetMessageW#messagePointer}
         * @return The builder instance.
         */
        public Builder messagePointer(@NotNull final MemorySegment messagePointer) {

            Preconditions.checkNotNull(messagePointer);
            this.messagePointer = messagePointer;
            return this;

        }

        /**
         * Overrides the builder internal window handle value with the provided value.
         *
         * @param windowHandle The window handle. {@link GetMessageW#windowHandle}
         * @return The builder instance.
         */
        public Builder windowHandle(@Nullable final MemorySegment windowHandle) {

            this.windowHandle = windowHandle;
            return this;

        }

        /**
         * Overrides the builder internal lowest message id value with the provided value.
         *
         * @param lowestMessageId The lowest message id. {@link GetMessageW#lowestMessageId}
         * @return The builder instance.
         */
        public Builder lowestMessageId(final int lowestMessageId) {

            this.lowestMessageId = lowestMessageId;
            return this;

        }

        /**
         * Overrides the builder internal highest message id value with the provided value.
         *
         * @param highestMessageId The highest message id. {@link GetMessageW#highestMessageId}
         * @return The builder instance.
         */
        public Builder highestMessageId(final int highestMessageId) {

            this.highestMessageId = highestMessageId;
            return this;

        }

        @NotNull
        @Override
        public GetMessageW build() {

            Preconditions.checkNotNull(this.messagePointer);

            return new GetMessageW(this);

        }

    }

}
