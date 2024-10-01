package com.etdon.winjgen.component.impl;

import com.etdon.commons.util.StringUtils;
import com.etdon.winjgen.constant.Constants;
import org.jetbrains.annotations.Nullable;

public class BuilderBuildComponent extends ClassComponent {

    private String fieldName;
    private boolean finalState;

    @Nullable
    public String getFieldName() {

        return this.fieldName;

    }

    public void setFieldName(@Nullable final String fieldName) {

        this.fieldName = fieldName;

    }

    public boolean isFinalState() {

        return this.finalState;

    }

    public void setFinalState(final boolean finalState) {

        this.finalState = finalState;

    }

    @Override
    public String toString() {

        final StringBuilder output = new StringBuilder();
        if (this.finalState)
            output.append(System.lineSeparator())
                    .append(StringUtils.repeat(Constants.PADDING, 3))
                    .append("Preconditions.checkNotNull(this.")
                    .append(this.fieldName)
                    .append(");");

        return output.toString();

    }

}
