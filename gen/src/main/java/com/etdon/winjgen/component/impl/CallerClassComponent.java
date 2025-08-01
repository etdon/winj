package com.etdon.winjgen.component.impl;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.util.StringUtils;
import com.etdon.winjgen.constant.Constants;
import org.jetbrains.annotations.NotNull;

public class CallerClassComponent extends ClassComponent {

    private final StringBuilder entries = new StringBuilder();

    public void addEntry(@NotNull final String fieldName) {

        Preconditions.checkNotNull(fieldName);
        this.entries.append(this.createEntry(fieldName));

    }

    @NotNull
    private String createEntry(@NotNull final String fieldName) {

        return new StringBuilder()
                .append(StringUtils.repeat(Constants.PADDING, 4))
                .append("this.").append(fieldName).append(',')
                .append(System.lineSeparator())
                .toString();

    }

    @Override
    public String toString() {

        return entries.substring(0, entries.length() - 3);

    }

}
