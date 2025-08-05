package com.etdon.winj.facade.hack.execute;

import com.etdon.jbinder.SymbolLookupCache;
import org.jetbrains.annotations.NotNull;

public class ShellcodeHelper {

    private final SymbolLookupCache symbolLookupCache;

    public ShellcodeHelper(@NotNull final SymbolLookupCache symbolLookupCache) {

        this.symbolLookupCache = symbolLookupCache;

    }

    public long getFunctionAddress(@NotNull final String library, @NotNull final String name) {

        return this.symbolLookupCache.getOrThrow(library).find(name).orElseThrow().address();

    }

}
