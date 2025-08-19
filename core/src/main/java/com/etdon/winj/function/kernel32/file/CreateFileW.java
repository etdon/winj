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
 * Creates or opens a file or I/O device. The most commonly used I/O devices are as follows: file, file stream,
 * directory, physical disk, volume, console buffer, tape drive, communications resource, mailslot, and pipe. The
 * function returns a handle that can be used to access the file or device for various types of I/O depending on the
 * file or device and the flags and attributes specified.
 * <p>
 * To perform this operation as a transacted operation, which results in a handle that can be used for transacted I/O,
 * use the CreateFileTransacted function.
 */
@NativeName(CreateFileW.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-createfilew")
public final class CreateFileW extends NativeFunction<MemorySegment> {

    public static final String LIBRARY = Library.KERNEL_32;
    public static final String NATIVE_NAME = "CreateFileW";
    public static final FunctionDescriptor CREATE_FILE_W_SIGNATURE = FunctionDescriptor.of(
            HANDLE,
            LPCWSTR.withName("lpFileName"),
            DWORD.withName("dwDesiredAccess"),
            DWORD.withName("dwShareMode"),
            LPSECURITY_ATTRIBUTES.withName("lpSecurityAttributes"),
            DWORD.withName("dwCreationDisposition"),
            DWORD.withName("dwFlagsAndAttributes"),
            HANDLE.withName("hTemplateFile")
    );

    /**
     * The name of the file or device to be created or opened. You may use either forward slashes (/) or backslashes
     * (\) in this name.
     * <p>
     * For information on special device names, see Defining an MS-DOS Device Name.
     * <p>
     * To create a file stream, specify the name of the file, a colon, and then the name of the stream. For more
     * information, see File Streams.
     * <p>
     * By default, the name is limited to MAX_PATH characters. To extend this limit to 32,767 wide characters, prepend
     * "\\?\" to the path. For more information, see Naming Files, Paths, and Namespaces.
     */
    @NativeName("lpFileName")
    private final MemorySegment fileNamePointer;

    /**
     * The requested access to the file or device, which can be summarized as read, write, both or neither zero).
     * <p>
     * The most commonly used values are GENERIC_READ, GENERIC_WRITE, or both (GENERIC_READ | GENERIC_WRITE). For more
     * information, see Generic Access Rights, File Security and Access Rights, File Access Rights Constants, and
     * ACCESS_MASK.
     * <p>
     * If this parameter is zero, the application can query certain metadata such as file, directory, or device
     * attributes without accessing that file or device, even if GENERIC_READ access would have been denied.
     * <p>
     * You cannot request an access mode that conflicts with the sharing mode that is specified by the dwShareMode
     * parameter in an open request that already has an open handle.
     * <p>
     * For more information, see the Remarks section of this topic and Creating and Opening Files.
     *
     * @see com.etdon.winj.constant.GenericAccessRight
     * @see com.etdon.winj.constant.file.FileAccessRight
     */
    @NativeName("dwDesiredAccess")
    private int desiredAccess = 0;

    /**
     * The requested sharing mode of the file or device, which can be read, write, both, delete, all of these, or none
     * (refer to the following table). Access requests to attributes or extended attributes are not affected by this
     * flag.
     * <p>
     * If this parameter is zero and CreateFile succeeds, the file or device cannot be shared and cannot be opened
     * again until the handle to the file or device is closed. For more information, see the Remarks section.
     * <p>
     * You cannot request a sharing mode that conflicts with the access mode that is specified in an existing request
     * that has an open handle. CreateFile would fail and the GetLastError function would return
     * ERROR_SHARING_VIOLATION.
     * <p>
     * To enable a process to share a file or device while another process has the file or device open, use a
     * compatible combination of one or more of the following values. For more information about valid combinations of
     * this parameter with the dwDesiredAccess parameter, see Creating and Opening Files.
     *
     * @see com.etdon.winj.constant.file.FileShareMode
     */
    @NativeName("dwShareMode")
    private int shareMode = 0;

    /**
     * A pointer to a SECURITY_ATTRIBUTES structure that contains two separate but related data members: an optional
     * security descriptor, and a Boolean value that determines whether the returned handle can be inherited by child
     * processes.
     * <p>
     * This parameter can be NULL.
     * <p>
     * If this parameter is NULL, the handle returned by CreateFile cannot be inherited by any child processes the
     * application may create and the file or device associated with the returned handle gets a default security
     * descriptor.
     * <p>
     * The lpSecurityDescriptor member of the structure specifies a SECURITY_DESCRIPTOR for a file or device. If this
     * member is NULL, the file or device associated with the returned handle is assigned a default security descriptor.
     * <p>
     * CreateFile ignores the lpSecurityDescriptor member when opening an existing file or device, but continues to use
     * the bInheritHandle member.
     * <p>
     * The bInheritHandle member of the structure specifies whether the returned handle can be inherited.
     */
    @NativeName("lpSecurityAttributes")
    private MemorySegment securityAttributesPointer = MemorySegment.NULL;

    /**
     * An action to take on a file or device that exists or does not exist.
     * <p>
     * For devices other than files, this parameter is usually set to OPEN_EXISTING.
     *
     * @see com.etdon.winj.constant.file.FileCreationDisposition
     */
    @NativeName("dwCreationDisposition")
    private int creationDisposition = 0;

    /**
     * The file or device attributes and flags, FILE_ATTRIBUTE_NORMAL being the most common default value for files.
     * <p>
     * This parameter can include any combination of the available file attributes (FILE_ATTRIBUTE_*). All other file
     * attributes override FILE_ATTRIBUTE_NORMAL.
     * <p>
     * This parameter can also contain combinations of flags (FILE_FLAG_) for control of file or device caching
     * behavior, access modes, and other special-purpose flags. These combine with any FILE_ATTRIBUTE_ values.
     * <p>
     * This parameter can also contain Security Quality of Service (SQOS) information by specifying the
     * SECURITY_SQOS_PRESENT flag. Additional SQOS-related flags information is presented in the table following the
     * attributes and flags tables.
     *
     * @see com.etdon.winj.constant.file.FileAttribute
     */
    @NativeName("dwFlagsAndAttributes")
    private int flagsAndAttributes = 0;

    /**
     * A valid handle to a template file with the GENERIC_READ access right. The template file supplies file attributes
     * and extended attributes for the file that is being created.
     * <p>
     * This parameter can be NULL.
     * <p>
     * When opening an existing file, CreateFile ignores this parameter.
     * <p>
     * When opening a new encrypted file, the file inherits the discretionary access control list from its parent
     * directory. For additional information, see File Encryption.
     */
    @NativeName("hTemplateFile")
    private MemorySegment templateFileHandle = MemorySegment.NULL;

    private CreateFileW(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, CREATE_FILE_W_SIGNATURE);

        this.fileNamePointer = builder.fileNamePointer;
        Conditional.executeIfNotNull(builder.desiredAccess, () -> this.desiredAccess = builder.desiredAccess);
        Conditional.executeIfNotNull(builder.shareMode, () -> this.shareMode = builder.shareMode);
        Conditional.executeIfNotNull(builder.securityAttributesPointer, () -> this.securityAttributesPointer = builder.securityAttributesPointer);
        Conditional.executeIfNotNull(builder.creationDisposition, () -> this.creationDisposition = builder.creationDisposition);
        Conditional.executeIfNotNull(builder.flagsAndAttributes, () -> this.flagsAndAttributes = builder.flagsAndAttributes);
        Conditional.executeIfNotNull(builder.templateFileHandle, () -> this.templateFileHandle = builder.templateFileHandle);

    }

    @Override
    public MemorySegment call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return (MemorySegment) super.obtainHandle(linker, symbolLookup).invoke(
                this.fileNamePointer,
                this.desiredAccess,
                this.shareMode,
                this.securityAttributesPointer,
                this.creationDisposition,
                this.flagsAndAttributes,
                this.templateFileHandle
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<CreateFileW> {

        private MemorySegment fileNamePointer;
        private Integer desiredAccess;
        private Integer shareMode;
        private MemorySegment securityAttributesPointer;
        private Integer creationDisposition;
        private Integer flagsAndAttributes;
        private MemorySegment templateFileHandle;

        private Builder() {

        }

        public Builder fileNamePointer(@NotNull final MemorySegment fileNamePointer) {

            this.fileNamePointer = fileNamePointer;
            return this;

        }

        public Builder desiredAccess(final int desiredAccess) {

            this.desiredAccess = desiredAccess;
            return this;

        }

        public Builder shareMode(final int shareMode) {

            this.shareMode = shareMode;
            return this;

        }

        public Builder securityAttributesPointer(@NotNull final MemorySegment securityAttributesPointer) {

            this.securityAttributesPointer = securityAttributesPointer;
            return this;

        }

        public Builder creationDisposition(final int creationDisposition) {

            this.creationDisposition = creationDisposition;
            return this;

        }

        public Builder flagsAndAttributes(final int flagsAndAttributes) {

            this.flagsAndAttributes = flagsAndAttributes;
            return this;

        }

        public Builder templateFileHandle(@NotNull final MemorySegment templateFileHandle) {

            this.templateFileHandle = templateFileHandle;
            return this;

        }

        @NotNull
        @Override
        public CreateFileW build() {

            Preconditions.checkNotNull(this.fileNamePointer);
            return new CreateFileW(this);

        }

    }

}
