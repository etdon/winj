package com.etdon.winj.function.kernel32;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.NativeDocumentation;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.NativeDataType.*;

@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/handleapi/nf-handleapi-closehandle")
public final class CloseHandle extends NativeFunction {

    public static final String LIBRARY = Library.KERNEL_32;
    public static final String NATIVE_NAME = "CloseHandle";
    public static final FunctionDescriptor CLOSE_HANDLE_SIGNATURE = FunctionDescriptor.of(
            BOOL,
            HANDLE.withName("hObject")
    );

    /**
     * A valid handle to an open object.
     */
    private final MemorySegment handle;

    private CloseHandle(final MemorySegment handle) {

        super(LIBRARY, NATIVE_NAME, CLOSE_HANDLE_SIGNATURE);

        this.handle = handle;

    }

    private CloseHandle(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, CLOSE_HANDLE_SIGNATURE);

        this.handle = builder.handle;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(this.handle);

    }

    public static CloseHandle ofHandle(@NotNull final MemorySegment handle) {

        Preconditions.checkNotNull(handle);
        return new CloseHandle(handle);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<CloseHandle> {

        private MemorySegment handle;

        public Builder handle(@NotNull final MemorySegment handle) {

            this.handle = handle;
            return this;

        }

        @NotNull
        @Override
        public CloseHandle build() {

            Preconditions.checkNotNull(this.handle);
            return new CloseHandle(this);

        }

    }

}
