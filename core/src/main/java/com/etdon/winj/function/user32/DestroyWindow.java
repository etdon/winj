package com.etdon.winj.function.user32;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.NativeDocumentation;
import com.etdon.jbinder.common.NativeName;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;

/**
 * Destroys the specified window. The function sends WM_DESTROY and WM_NCDESTROY messages to the window to deactivate
 * it and remove the keyboard focus from it. The function also destroys the window's menu, destroys timers, removes
 * clipboard ownership, and breaks the clipboard viewer chain (if the window is at the top of the viewer chain).
 * <p>
 * If the specified window is a parent or owner window, DestroyWindow automatically destroys the associated child or
 * owned windows when it destroys the parent or owner window. The function first destroys child or owned windows, and
 * then it destroys the parent or owner window.
 * <p>
 * DestroyWindow also destroys modeless dialog boxes created by the CreateDialog function.
 */
@NativeName(DestroyWindow.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-destroywindow")
public final class DestroyWindow extends NativeFunction<Integer> {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "DestroyWindow";
    public static final FunctionDescriptor DESTROY_WINDOW_SIGNATURE = FunctionDescriptor.of(
            BOOL,
            HWND.withName("hWnd")
    );

    /**
     * A handle to the window to be destroyed.
     */
    @NativeName("hWnd")
    private final MemorySegment windowHandle;

    private DestroyWindow(@NotNull final MemorySegment memorySegment) {

        super(LIBRARY, NATIVE_NAME, DESTROY_WINDOW_SIGNATURE);

        this.windowHandle = memorySegment;

    }

    private DestroyWindow(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, DESTROY_WINDOW_SIGNATURE);

        this.windowHandle = builder.windowHandle;

    }

    @Override
    public Integer call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return (Integer) super.obtainHandle(linker, symbolLookup).invoke(
                this.windowHandle
        );

    }

    public static DestroyWindow ofWindowHandle(@NotNull final MemorySegment windowHandle) {

        Preconditions.checkNotNull(windowHandle);
        return new DestroyWindow(windowHandle);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<DestroyWindow> {

        private MemorySegment windowHandle;

        private Builder() {

        }

        /**
         * Overrides the builder internal window handle value with the provided value.
         *
         * @param windowHandle The window handle. {@link DestroyWindow#windowHandle}
         * @return The builder instance.
         */
        public Builder windowHandle(@NotNull final MemorySegment windowHandle) {

            this.windowHandle = windowHandle;
            return this;

        }

        @NotNull
        @Override
        public DestroyWindow build() {

            Preconditions.checkNotNull(this.windowHandle);

            return new DestroyWindow(this);

        }

    }

}
