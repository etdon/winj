package com.etdon.winj.facade.op.marshal;

import org.jetbrains.annotations.NotNull;

public abstract class Marshal<T, K extends MarshalContext<T, ?>> {

    public abstract byte[] marshal(@NotNull final T input, @NotNull final K context);

    public abstract boolean isValid(@NotNull final Object input);

    public abstract boolean isValid(@NotNull final MarshalContext<?, ?> context);

}
