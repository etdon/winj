package com.etdon.winj.function.ntdll;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import com.etdon.winj.type.constant.NativeDataType;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static java.lang.foreign.ValueLayout.JAVA_BOOLEAN;

public final class NtQueryDefaultLocale extends NativeFunction {

    public static final String LIBRARY = Library.NTDLL;
    public static final String NATIVE_NAME = "NtQueryDefaultLocale";
    public static final FunctionDescriptor NT_QUERY_DEFAULT_LOCALE_SIGNATURE = FunctionDescriptor.ofVoid(
            JAVA_BOOLEAN.withName("UserProfile"),
            NativeDataType.PLCID.withName("DefaultLocaleId")
    );

    /**
     * If <code>true</code>, function returns UserMode default locale. If not, result is system locale.
     */
    private boolean userProfile = false;

    /**
     * Pointer to LCID value receiving current locale.
     */
    private final MemorySegment defaultLocaleIdPointer;

    private NtQueryDefaultLocale(@NotNull final Builder builder) {

        super(LIBRARY, NATIVE_NAME, NT_QUERY_DEFAULT_LOCALE_SIGNATURE);

        Conditional.executeIfNotNull(builder.userProfile, () -> this.userProfile = builder.userProfile);
        this.defaultLocaleIdPointer = builder.defaultLocaleIdPointer;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(this.userProfile, this.defaultLocaleIdPointer);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<NtQueryDefaultLocale> {

        private Boolean userProfile;
        private MemorySegment defaultLocaleIdPointer;

        private Builder() {

        }

        /**
         * Overrides the builder internal default locale id pointer value with the provided value.
         *
         * @param userProfile The user profile mode. {@link NtQueryDefaultLocale#userProfile}
         * @return The builder instance.
         */
        public Builder userProfile(final boolean userProfile) {

            this.userProfile = userProfile;
            return this;

        }

        /**
         * Overrides the builder internal default locale id pointer value with the provided value.
         *
         * @param defaultLocaleIdPointer The default locale id pointer. {@link NtQueryDefaultLocale#defaultLocaleIdPointer}
         * @return The builder instance.
         */
        public Builder defaultLocaleIdPointer(@NotNull final MemorySegment defaultLocaleIdPointer) {

            this.defaultLocaleIdPointer = defaultLocaleIdPointer;
            return this;

        }

        @NotNull
        @Override
        public NtQueryDefaultLocale build() {

            Preconditions.checkNotNull(this.defaultLocaleIdPointer);

            return new NtQueryDefaultLocale(this);

        }

    }

}
