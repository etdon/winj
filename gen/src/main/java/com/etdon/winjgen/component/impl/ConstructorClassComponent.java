package com.etdon.winjgen.component.impl;

import com.etdon.commons.util.StringUtils;
import com.etdon.winjgen.constant.Constants;
import org.jetbrains.annotations.Nullable;

public class ConstructorClassComponent extends ClassComponent {

    private String typeName;
    private String fieldName;
    private boolean finalState;

    @Nullable
    public String getFieldName() {

        return this.fieldName;

    }

    public void setFieldName(@Nullable final String fieldName) {

        this.fieldName = fieldName;

    }

    @Nullable
    public String getTypeName() {

        return this.typeName;

    }

    public void setTypeName(@Nullable final String typeName) {

        this.typeName = typeName;

    }

    public boolean isFinalState() {

        return this.finalState;

    }

    public void setFinalState(final boolean finalState) {

        this.finalState = finalState;

    }

    @Override
    public String toString() {

        final StringBuilder output = new StringBuilder()
                .append(StringUtils.repeat(Constants.PADDING, 2));

        if (this.finalState) {
            output.append("this.").append(this.fieldName).append(" = builder.").append(this.fieldName).append(";");
        } else {
            output.append("Conditional.executeIfNotNull(builder.").append(this.fieldName).append(", () -> this.")
                    .append(this.fieldName).append(" = builder.").append(this.fieldName).append(");");
        }

        return output.toString();

    }

}
