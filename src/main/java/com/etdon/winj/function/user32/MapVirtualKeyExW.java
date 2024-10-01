package com.etdon.winj.function.user32;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.function.NativeFunction;
import com.etdon.winj.constant.VirtualKeyMapType;
import com.etdon.winj.constant.Library;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import static com.etdon.winj.type.NativeDataType.*;

public final class MapVirtualKeyExW extends NativeFunction {

    public static final String LIBRARY = Library.USER_32;
    public static final String NATIVE_NAME = "MapVirtualKeyExW";
    public static final FunctionDescriptor MAP_VIRTUAL_KEY_EX_W_SIGNATURE = FunctionDescriptor.of(
            UINT,
            UINT,
            UINT,
            HKL
    );

    /**
     * The virtual key code or scan code for a key. How this value is interpreted depends on the value of the uMapType
     * parameter.
     */
    private final int code;

    /**
     * The translation to perform.
     *
     * @see VirtualKeyMapType
     */
    private final int mapType;

    /**
     * Input locale identifier to use for translating the specified code. This parameter can be any input locale
     * identifier previously returned by the LoadKeyboardLayout function.
     */
    private MemorySegment localeIdentifierHandle = MemorySegment.NULL;

    private MapVirtualKeyExW(@NotNull final Builder builder) {

        super(LIBRARY, NATIVE_NAME, MAP_VIRTUAL_KEY_EX_W_SIGNATURE);

        this.code = builder.code;
        this.mapType = builder.mapType;
        Conditional.executeIfNotNull(builder.localeIdentifierHandle, () -> this.localeIdentifierHandle = builder.localeIdentifierHandle);

    }

    @Override
    public Object call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return super.obtainHandle(linker, symbolLookup).invoke(
                this.code,
                this.mapType,
                this.localeIdentifierHandle
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<MapVirtualKeyExW> {

        private Integer code;
        private Integer mapType;
        private MemorySegment localeIdentifierHandle;

        private Builder() {

        }

        /**
         * Overrides the builder internal code value with the provided value.
         *
         * @param code The code. {@link MapVirtualKeyExW#code}
         * @return The builder instance.
         */
        public Builder code(final int code) {

            this.code = code;
            return this;

        }

        /**
         * Overrides the builder internal map type value with the provided value.
         *
         * @param mapType The map type. {@link MapVirtualKeyExW#mapType}
         * @return The builder instance.
         */
        public Builder mapType(final int mapType) {

            this.mapType = mapType;
            return this;

        }

        /**
         * Overrides the builder internal locale identifier handle value with the provided value.
         *
         * @param localeIdentifierHandle The locale identifier handle. {@link MapVirtualKeyExW#localeIdentifierHandle}
         * @return The builder instance.
         */
        public Builder localeIdentifierHandle(@NotNull final MemorySegment localeIdentifierHandle) {

            this.localeIdentifierHandle = localeIdentifierHandle;
            return this;

        }

        @NotNull
        @Override
        public MapVirtualKeyExW build() {

            Preconditions.checkNotNull(this.code);
            Preconditions.checkNotNull(this.mapType);

            return new MapVirtualKeyExW(this);

        }

    }

}
