package com.etdon.winj.function.user32;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.NativeDocumentation;
import com.etdon.jbinder.common.NativeName;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;

/**
 * Installs an application-defined hook procedure into a hook chain. You would install a hook procedure to monitor the
 * system for certain types of events. These events are associated either with a specific thread or with all threads in
 * the same desktop as the calling thread.
 */
@NativeName(SetWindowsHookExW.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-setwindowshookexw")
public final class SetWindowsHookExW extends NativeFunction {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "SetWindowsHookExW";
    public static final FunctionDescriptor SET_WINDOWS_HOOK_EX_W_SIGNATURE = FunctionDescriptor.of(
            HHOOK,
            INTEGER.withName("idHook"),
            HOOKPROC.withName("lpfn"),
            HINSTANCE.withName("hmod"),
            DWORD.withName("dwThreadId")
    );

    /**
     * The type of hook procedure to be installed.
     *
     * @see com.etdon.winj.constant.HookType
     */
    @NativeName("idHook")
    private final int type;

    /**
     * A pointer to the hook procedure. If the dwThreadId parameter is zero or specifies the identifier of a thread
     * created by a different process, the lpfn parameter must point to a hook procedure in a DLL. Otherwise, lpfn can
     * point to a hook procedure in the code associated with the current process.
     */
    @NativeName("lpfn")
    private final MemorySegment procedurePointer;

    /**
     * A handle to the DLL containing the hook procedure pointed to by the lpfn parameter. The hMod parameter must be
     * set to NULL if the dwThreadId parameter specifies a thread created by the current process and if the hook
     * procedure is within the code associated with the current process.
     */
    @NativeName("hmod")
    private MemorySegment ownerHandle = MemorySegment.NULL;

    /**
     * The identifier of the thread with which the hook procedure is to be associated. For desktop apps, if this
     * parameter is zero, the hook procedure is associated with all existing threads running in the same desktop as the
     * calling thread. For Windows Store apps, see the Remarks section.
     */
    @NativeName("dwThreadId")
    private int threadId = 0;

    private SetWindowsHookExW(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, SET_WINDOWS_HOOK_EX_W_SIGNATURE);

        this.type = builder.type;
        this.procedurePointer = builder.procedurePointer;
        Conditional.executeIfNotNull(builder.ownerHandle, () -> this.ownerHandle = builder.ownerHandle);
        Conditional.executeIfNotNull(builder.threadId, () -> this.threadId = builder.threadId);

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.type,
                this.procedurePointer,
                this.ownerHandle,
                this.threadId
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<SetWindowsHookExW> {

        private Integer type;
        private MemorySegment procedurePointer;
        private MemorySegment ownerHandle;
        private Integer threadId;

        private Builder() {

        }

        public Builder type(final int type) {

            this.type = type;
            return this;

        }

        /**
         * Overrides the builder internal procedure pointer value with the provided value.
         *
         * @param procedurePointer The procedure pointer. {@link SetWindowsHookExW#procedurePointer}
         * @return The builder instance.
         */
        public Builder procedurePointer(@NotNull final MemorySegment procedurePointer) {

            Preconditions.checkNotNull(procedurePointer);
            this.procedurePointer = procedurePointer;
            return this;

        }

        /**
         * Overrides the builder internal owner handle value with the provided value.
         *
         * @param ownerHandle The owner handle. {@link SetWindowsHookExW#ownerHandle}
         * @return The builder instance.
         */
        public Builder ownerHandle(@Nullable final MemorySegment ownerHandle) {

            this.ownerHandle = ownerHandle;
            return this;

        }

        /**
         * Overrides the builder internal thread id value with the provided value.
         *
         * @param threadId The thread id. {@link SetWindowsHookExW#threadId}
         * @return The builder instance.
         */
        public Builder threadId(final int threadId) {

            this.threadId = threadId;
            return this;

        }

        @NotNull
        @Override
        public SetWindowsHookExW build() {

            Preconditions.checkNotNull(type);
            Preconditions.checkNotNull(procedurePointer);

            return new SetWindowsHookExW(this);

        }

    }

}
