package com.etdon.winj.function.kernel32.sync;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.NativeDocumentation;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import com.etdon.winj.constant.WaitTime;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.*;

/**
 * Waits until the specified object is in the signaled state or the time-out interval elapses.
 * <p>
 * To enter an alertable wait state, use the WaitForSingleObjectEx function. To wait for multiple objects, use
 * WaitForMultipleObjects.
 */
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/synchapi/nf-synchapi-waitforsingleobject")
public final class WaitForSingleObject extends NativeFunction {

    public static final String LIBRARY = Library.KERNEL_32;
    public static final String NATIVE_NAME = "WaitForSingleObject";
    public static final FunctionDescriptor WAIT_FOR_SINGLE_OBJECT_SIGNATURE = FunctionDescriptor.of(
            DWORD,
            HANDLE.withName("hHandle"),
            DWORD.withName("dwMilliseconds")
    );

    /**
     * A handle to the object. For a list of the object types whose handles can be specified, see the following Remarks
     * section.
     * <p>
     * If this handle is closed while the wait is still pending, the function's behavior is undefined.
     * <p>
     * The handle must have the SYNCHRONIZE access right. For more information, see Standard Access Rights.
     */
    private final MemorySegment handle;

    /**
     * The time-out interval, in milliseconds. If a nonzero value is specified, the function waits until the object is
     * signaled or the interval elapses. If dwMilliseconds is zero, the function does not enter a wait state if the
     * object is not signaled; it always returns immediately. If dwMilliseconds is INFINITE, the function will return
     * only when the object is signaled.
     * <p>
     * Windows XP, Windows Server 2003, Windows Vista, Windows 7, Windows Server 2008, and Windows Server 2008 R2: The
     * dwMilliseconds value does include time spent in low-power states. For example, the timeout does keep counting
     * down while the computer is asleep.
     * <p>
     * Windows 8 and newer, Windows Server 2012 and newer: The dwMilliseconds value does not include time spent in
     * low-power states. For example, the timeout does not keep counting down while the computer is asleep.
     *
     * @see com.etdon.winj.constant.WaitTime
     */
    private int timeoutMillis = -1;

    private WaitForSingleObject(@NotNull final MemorySegment handle) {

        super(LIBRARY, NATIVE_NAME, WAIT_FOR_SINGLE_OBJECT_SIGNATURE);

        this.handle = handle;
        this.timeoutMillis = WaitTime.INFINITE;

    }

    private WaitForSingleObject(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, WAIT_FOR_SINGLE_OBJECT_SIGNATURE);

        this.handle = builder.handle;
        Conditional.executeIfNotNull(builder.timeoutMillis, () -> this.timeoutMillis = builder.timeoutMillis);

    }

    /**
     * Returns a wait result.
     *
     * @see com.etdon.winj.constant.WaitResult
     */
    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(this.handle, this.timeoutMillis);

    }

    public static WaitForSingleObject infinite(@NotNull final MemorySegment handle) {

        return new WaitForSingleObject(handle);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<WaitForSingleObject> {

        private MemorySegment handle;
        private Integer timeoutMillis;

        public Builder handle(@NotNull final MemorySegment handle) {

            this.handle = handle;
            return this;

        }

        public Builder timeoutMillis(final int timeoutMillis) {

            this.timeoutMillis = timeoutMillis;
            return this;

        }

        @NotNull
        @Override
        public WaitForSingleObject build() {

            Preconditions.checkNotNull(this.handle);
            return new WaitForSingleObject(this);

        }

    }

}
