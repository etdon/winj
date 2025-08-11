package com.etdon.winj.function.ntdll;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.common.NativeDocumentation;
import com.etdon.jbinder.common.NativeName;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.Library;
import com.etdon.winj.type.UnicodeString;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.constant.NativeDataType.PCWSTR;

@NativeName(RtlInitUnicodeString.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows-hardware/drivers/ddi/wdm/nf-wdm-rtlinitunicodestring")
public final class RtlInitUnicodeString extends NativeFunction {

    public static final String LIBRARY = Library.NTDLL;
    public static final String NATIVE_NAME = "RtlInitUnicodeString";
    public static final FunctionDescriptor RTL_INIT_UNICODE_STRING = FunctionDescriptor.ofVoid(
            UnicodeString.PCUNICODE_STRING.withName("DestinationString"),
            PCWSTR.withName("SourceString")
    );

    /**
     * A pointer to the UNICODE_STRING structure to be initialized.
     */
    @NativeName("DestinationString")
    private final MemorySegment destinationStringPointer;

    /**
     * A pointer to a null-terminated wide-character string. This string is used to initialize the counted string
     * pointed to by DestinationString.
     */
    @NativeName("SourceString")
    private final MemorySegment sourceStringPointer;

    private RtlInitUnicodeString(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, RTL_INIT_UNICODE_STRING);

        this.destinationStringPointer = builder.destinationStringPointer;
        this.sourceStringPointer = builder.sourceStringPointer;

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(this.destinationStringPointer, this.sourceStringPointer);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<RtlInitUnicodeString> {

        private MemorySegment destinationStringPointer;
        private MemorySegment sourceStringPointer;

        public Builder destinationStringPointer(@NotNull final MemorySegment destinationStringPointer) {

            this.destinationStringPointer = destinationStringPointer;
            return this;

        }

        public Builder sourceStringPointer(@NotNull final MemorySegment sourceStringPointer) {

            this.sourceStringPointer = sourceStringPointer;
            return this;

        }

        @NotNull
        @Override
        public RtlInitUnicodeString build() {

            Preconditions.checkNotNull(this.destinationStringPointer);
            Preconditions.checkNotNull(this.sourceStringPointer);
            return new RtlInitUnicodeString(this);

        }

    }

}
