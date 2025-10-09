package com.etdon.winj.marshal;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.io.ByteOrder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class MarshalContext<T, K extends MarshalContext<T, ?>> {

    private final MarshalStrategy<T, K> strategy;
    private final ByteOrder byteOrder;

    public MarshalContext(@Nullable final MarshalStrategy<T, K> strategy) {

        this.strategy = strategy;
        this.byteOrder = ByteOrder.LITTLE_ENDIAN;

    }

    public MarshalContext(@Nullable final MarshalStrategy<T, K> strategy,
                          @NotNull final ByteOrder byteOrder) {

        Preconditions.checkNotNull(byteOrder);
        this.strategy = strategy;
        this.byteOrder = byteOrder;

    }

    @NotNull
    public Optional<MarshalStrategy<T, K>> getStrategy() {

        return Optional.ofNullable(this.strategy);

    }

    @NotNull
    public ByteOrder getByteOrder() {

        return this.byteOrder;

    }

}
