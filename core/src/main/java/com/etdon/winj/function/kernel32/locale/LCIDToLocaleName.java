package com.etdon.winj.function.kernel32.locale;

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
import static java.lang.foreign.ValueLayout.*;

/**
 * Converts a locale identifier to a locale name.
 */
@NativeName(LCIDToLocaleName.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/winnls/nf-winnls-lcidtolocalename")
public final class LCIDToLocaleName extends NativeFunction<Integer> {

    public static final String LIBRARY = Library.KERNEL_32;
    public static final String NATIVE_NAME = "LCIDToLocaleName";
    public static final FunctionDescriptor LCID_TO_LOCALE_NAME_SIGNATURE = FunctionDescriptor.of(
            JAVA_INT,
            LCID.withName("Locale"),
            LPWSTR.withName("lpName"),
            JAVA_INT.withName("cchName"),
            DWORD.withName("dwFlags")
    );

    /**
     * Locale identifier to translate.
     */
    @NativeName("Locale")
    private final int localeId;

    /**
     * Pointer to a buffer in which this function retrieves the locale name, or one of the following predefined values:
     * <p>
     * <ul>
     *     <li>LOCALE_NAME_INVARIANT</li>
     *     <li>LOCALE_NAME_SYSTEM_DEFAULT</li>
     *     <li>LOCALE_NAME_USER_DEFAULT</li>
     * </ul>
     */
    @NativeName("lpName")
    private MemorySegment localeNamePointer = MemorySegment.NULL;

    /**
     * Size, in characters, of the locale name buffer. The maximum possible length of a locale name, including a
     * terminating null character, is LOCALE_NAME_MAX_LENGTH. This is the recommended size to supply for this
     * parameter.
     * <p>
     * Alternatively, the application can set this parameter to 0. In this case, the function returns the required size
     * for the locale name buffer.
     */
    @NativeName("cchName")
    private final int localeNameBufferSize;

    /**
     * Before Windows 7: Reserved; should always be 0.
     * <p>
     * Starting with Windows 7: Can be set to LOCALE_ALLOW_NEUTRAL_NAMES to allow the return of a neutral name.
     */
    @NativeName("dwFlags")
    private int flags = 0;

    private LCIDToLocaleName(@NotNull final Builder builder) {

        super(LIBRARY, NATIVE_NAME, LCID_TO_LOCALE_NAME_SIGNATURE);

        this.localeId = builder.localeId;
        Conditional.executeIfNotNull(builder.localeNamePointer, () -> this.localeNamePointer = builder.localeNamePointer);
        this.localeNameBufferSize = builder.localeNameBufferSize;
        Conditional.executeIfNotNull(builder.flags, () -> this.flags = builder.flags);

    }

    @Override
    public Integer call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return (Integer) super.obtainHandle(linker, symbolLookup).invoke(this.localeId, this.localeNamePointer, this.localeNameBufferSize, this.flags);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<LCIDToLocaleName> {

        private Integer localeId;
        private MemorySegment localeNamePointer;
        private Integer localeNameBufferSize;
        private Integer flags;

        private Builder() {

        }

        public Builder localeId(final int localeId) {

            this.localeId = localeId;
            return this;

        }

        public Builder localeNamePointer(@NotNull final MemorySegment localeNamePointer) {

            this.localeNamePointer = localeNamePointer;
            return this;

        }

        public Builder localeNameBufferSize(final int localeNameBufferSize) {

            this.localeNameBufferSize = localeNameBufferSize;
            return this;

        }

        public Builder flags(final int flags) {

            this.flags = flags;
            return this;

        }

        @NotNull
        @Override
        public LCIDToLocaleName build() {

            Preconditions.checkNotNull(localeId);
            Preconditions.checkNotNull(localeNameBufferSize);
            return new LCIDToLocaleName(this);

        }

    }

}
