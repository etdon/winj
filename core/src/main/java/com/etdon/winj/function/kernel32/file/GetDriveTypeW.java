package com.etdon.winj.function.kernel32.file;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
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
 * Determines whether a disk drive is a removable, fixed, CD-ROM, RAM disk, or network drive.
 * <p>
 * To determine whether a drive is a USB-type drive, call SetupDiGetDeviceRegistryProperty and specify the
 * SPDRP_REMOVAL_POLICY property.
 * <p>
 * Returns {@link com.etdon.winj.constant.file.DriveType}
 */
@NativeName(GetDriveTypeW.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-getdrivetypew")
public final class GetDriveTypeW extends NativeFunction<Integer> {

    public static final String LIBRARY = Library.KERNEL_32;
    public static final String NATIVE_NAME = "GetDriveTypeW";
    public static final FunctionDescriptor GET_DRIVE_TYPE_W_SIGNATURE = FunctionDescriptor.of(
            UINT,
            LPCWSTR.withName("lpRootPathName")
    );

    /**
     * The root directory for the drive.
     * <p>
     * A trailing backslash is required. If this parameter is NULL, the function uses the root of the current directory.
     */
    @NativeName("lpRootPathName")
    private MemorySegment rootPathNamePointer;

    private GetDriveTypeW(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, GET_DRIVE_TYPE_W_SIGNATURE);

        Conditional.executeIfNotNull(builder.rootPathNamePointer, () -> this.rootPathNamePointer = builder.rootPathNamePointer);

    }

    @Override
    public Integer call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return (Integer) super.obtainHandle(linker, symbolLookup).invoke(this.rootPathNamePointer);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<GetDriveTypeW> {

        private MemorySegment rootPathNamePointer;

        private Builder() {

        }

        public Builder rootPathNamePointer(@NotNull final MemorySegment rootPathNamePointer) {

            this.rootPathNamePointer = rootPathNamePointer;
            return this;

        }

        @NotNull
        @Override
        public GetDriveTypeW build() {

            return new GetDriveTypeW(this);

        }

    }

}
