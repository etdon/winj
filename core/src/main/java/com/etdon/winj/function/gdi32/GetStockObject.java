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

import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_INT;

/**
 * The GetStockObject function retrieves a handle to one of the stock pens, brushes, fonts, or palettes.
 */
@NativeName(GetStockObject.NATIVE_NAME)
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/wingdi/nf-wingdi-getstockobject")
public final class GetStockObject extends NativeFunction<MemorySegment> {

    public static final String LIBRARY = Library.GDI_32;
    public static final String NATIVE_NAME = "GetStockObject";
    public static final FunctionDescriptor GET_STOCK_SIGNATURE = FunctionDescriptor.of(
            ADDRESS,
            JAVA_INT.withName("i")
    );

    /**
     * The type of stock object.
     *
     * @see com.etdon.winj.constant.StockObject
     */
    @NativeName("i")
    private final int id;

    private GetStockObject(final Builder builder) {

        super(LIBRARY, NATIVE_NAME, GET_STOCK_SIGNATURE);

        this.id = builder.id;

    }

    @Override
    public MemorySegment call(@NotNull final Linker linker, @NotNull final SymbolLookup symbolLookup) throws Throwable {

        return (MemorySegment) super.obtainHandle(linker, symbolLookup).invoke(this.id);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<GetStockObject> {

        private Integer id;

        private Builder() {

        }

        public Builder id(final int id) {

            this.id = id;
            return this;

        }

        @NotNull
        @Override
        public GetStockObject build() {

            Preconditions.checkNotNull(id);

            return new GetStockObject(this);

        }

    }

}
