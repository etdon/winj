package com.etdon.winj.demo;

import com.etdon.winj.WinJ;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;

public final class Demo {

    public static void main(@NotNull final String[] args) {

        new WinJ(Arena.ofAuto()).demo();

    }

    private Demo() {

        throw new UnsupportedOperationException();

    }

}
