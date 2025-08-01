package com.etdon.winjgen;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Token {

    private final TokenType type;
    private final String value;

    public Token(@NotNull final TokenType type) {

        this.type = type;
        this.value = null;

    }

    public Token(@NotNull final TokenType type,
                 @Nullable final String value) {

        this.type = type;
        this.value = value;

    }

    @NotNull
    public TokenType getType() {

        return this.type;

    }

    @Nullable
    public String getValue() {

        return this.value;

    }

    @Override
    public String toString() {

        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';

    }

}
