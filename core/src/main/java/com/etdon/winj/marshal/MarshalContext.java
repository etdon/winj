package com.etdon.winj.marshal;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class MarshalContext<T, K extends MarshalContext<T, ?>> {

    private final MarshalStrategy<T, K> strategy;

    public MarshalContext(@Nullable final MarshalStrategy<T, K> strategy) {

        this.strategy = strategy;

    }

    public Optional<MarshalStrategy<T, K>> getStrategy() {

        return Optional.ofNullable(this.strategy);

    }

}
