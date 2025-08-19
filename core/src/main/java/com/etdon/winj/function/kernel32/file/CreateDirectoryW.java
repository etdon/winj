package com.etdon.winj.function.kernel32.file;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
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
 * Creates a new directory. If the underlying file system supports security on files and directories, the function
 * applies a specified security descriptor to the new directory.
 * <p>
 * To specify a template directory, use the CreateDirectoryEx function.
 * <p>
 * To perform this operation as a transacted operation, use the CreateDirectoryTransacted function.
 */
@NativeName(CreateDirectoryW.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-createdirectoryw")
public final class CreateDirectoryW extends NativeFunction<Integer> {

    public static final String LIBRARY = Library.KERNEL_32;
    public static final String NATIVE_NAME = "CreateDirectoryW";
    public static final FunctionDescriptor CREATE_DIRECTORY_W_SIGNATURE = FunctionDescriptor.of(
            BOOL,
            LPCWSTR.withName("lpPathName"),
            LPSECURITY_ATTRIBUTES.withName("lpSecurityAttributes")
    );

    /**
     * The path of the directory to be created.
     * <p>
     * By default, the name is limited to MAX_PATH characters. To extend this limit to 32,767 wide characters, prepend
     * "\\?\" to the path. For more information, see Naming Files, Paths, and Namespaces.
     */
    @NativeName("lpPathName")
    private final MemorySegment pathNamePointer;

    /**
     * A pointer to a SECURITY_ATTRIBUTES structure. The lpSecurityDescriptor member of the structure specifies a
     * security descriptor for the new directory. If lpSecurityAttributes is NULL, the directory gets a default
     * security descriptor. The ACLs in the default security descriptor for a directory are inherited from its parent
     * directory.
     * <p>
     * The target file system must support security on files and directories for this parameter to have an effect.
     * (This is indicated when GetVolumeInformation returns FS_PERSISTENT_ACLS.)
     */
    @NativeName("lpSecurityAttributes")
    private MemorySegment securityAttributesPointer = MemorySegment.NULL;

    private CreateDirectoryW(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, CREATE_DIRECTORY_W_SIGNATURE);

        this.pathNamePointer = builder.pathNamePointer;
        Conditional.executeIfNotNull(builder.securityAttributesPointer, () -> this.securityAttributesPointer = builder.securityAttributesPointer);

    }

    @Override
    public Integer call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return (Integer) super.obtainHandle(linker, symbolLookup).invoke(
                this.pathNamePointer,
                this.securityAttributesPointer
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<CreateDirectoryW> {

        private MemorySegment pathNamePointer;
        private MemorySegment securityAttributesPointer;

        private Builder() {

        }

        /**
         * Overrides the builder internal path name pointer value with the provided value.
         *
         * @param pathNamePointer The path name pointer. {@link CreateDirectoryW#pathNamePointer}
         * @return The builder instance.
         */
        public Builder pathNamePointer(@NotNull final MemorySegment pathNamePointer) {

            this.pathNamePointer = pathNamePointer;
            return this;

        }

        /**
         * Overrides the builder internal security attributes pointer value with the provided value.
         *
         * @param securityAttributesPointer The security attributes pointer. {@link CreateDirectoryW#securityAttributesPointer}
         * @return The builder instance.
         */
        public Builder securityAttributesPointer(@NotNull final MemorySegment securityAttributesPointer) {

            this.securityAttributesPointer = securityAttributesPointer;
            return this;

        }

        @NotNull
        @Override
        public CreateDirectoryW build() {

            Preconditions.checkNotNull(this.pathNamePointer);
            return new CreateDirectoryW(this);

        }

    }

}
