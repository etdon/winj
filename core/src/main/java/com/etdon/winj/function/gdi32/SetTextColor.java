package com.etdon.winj.function.gdi32;

import com.etdon.commons.builder.FluentBuilder;
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
 * The SetTextColor function sets the text color for the specified device context to the specified color.
 */
@NativeName(SetTextColor.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/wingdi/nf-wingdi-settextcolor")
public final class SetTextColor extends NativeFunction<Integer> {

    public static final String LIBRARY = Library.GDI_32;
    public static final String NATIVE_NAME = "SetTextColor";
    public static final FunctionDescriptor SET_TEXT_COLOR_SIGNATURE = FunctionDescriptor.of(
            COLORREF,
            HDC.withName("hdc"),
            COLORREF.withName("color")
    );

    /**
     * A handle to the device context.
     */
    @NativeName("hdc")
    private final MemorySegment deviceContextHandle;

    /**
     * The color of the text.
     */
    @NativeName("color")
    private final int color;

    private SetTextColor(@NotNull final Builder builder) {

        super(LIBRARY, NATIVE_NAME, SET_TEXT_COLOR_SIGNATURE);

        this.deviceContextHandle = builder.deviceContextHandle;
        this.color = builder.color;

    }

    @Override
    public Integer call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return (Integer) super.obtainHandle(linker, symbolLookup).invoke(
                this.deviceContextHandle,
                this.color
        );

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<SetTextColor> {

        private MemorySegment deviceContextHandle;
        private Integer color;

        private Builder() {

        }

        /**
         * Overrides the builder internal device context handle value with the provided value.
         *
         * @param deviceContextHandle The device context handle. {@link SetTextColor#deviceContextHandle}
         * @return The builder instance.
         */
        public Builder deviceContextHandle(@NotNull final MemorySegment deviceContextHandle) {

            this.deviceContextHandle = deviceContextHandle;
            return this;

        }

        /**
         * Overrides the builder internal color value with the provided value.
         *
         * @param color The color. {@link SetTextColor#color}
         * @return The builder instance.
         */
        public Builder color(final int color) {

            this.color = color;
            return this;

        }

        @NotNull
        @Override
        public SetTextColor build() {

            Preconditions.checkNotNull(this.deviceContextHandle);
            Preconditions.checkNotNull(this.color);

            return new SetTextColor(this);

        }

    }

}
