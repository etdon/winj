package com.etdon.winjgen.component.impl;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.util.StringUtils;
import com.etdon.winjgen.constant.Constants;
import org.jetbrains.annotations.NotNull;

public class SignatureClassComponent extends ClassComponent {

    private final StringBuilder entries = new StringBuilder();

    public void addEntry(@NotNull final String typeName) {

        Preconditions.checkNotNull(typeName);
        this.entries.append(this.createEntry(typeName));

    }

    @NotNull
    private String createEntry(@NotNull final String typeName) {

        final StringBuilder entry = new StringBuilder();
        if (!this.entries.isEmpty())
            entry.append(System.lineSeparator());
        entry.append(StringUtils.repeat(Constants.PADDING, 3)).append(typeName).append(',');

        return entry.toString();

    }

    @Override
    public String toString() {

        return entries.substring(0, entries.length() - 1);

    }

}
