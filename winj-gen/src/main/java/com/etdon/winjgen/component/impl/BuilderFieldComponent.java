package com.etdon.winjgen.component.impl;

import com.etdon.commons.util.StringUtils;
import com.etdon.winjgen.constant.Constants;
import org.jetbrains.annotations.Nullable;

public class BuilderFieldComponent extends ClassComponent {

    private String nonPrimitiveTypeName;
    private String fieldName;
    private boolean finalState;

    @Nullable
    public String getNonPrimitiveTypeName() {

        return this.nonPrimitiveTypeName;

    }

    public void setNonPrimitiveTypeName(@Nullable final String nonPrimitiveTypeName) {

        this.nonPrimitiveTypeName = nonPrimitiveTypeName;

    }

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

        return new StringBuilder()
                .append(StringUtils.repeat(Constants.PADDING, 2))
                .append("private ")
                .append(this.nonPrimitiveTypeName)
                .append(' ')
                .append(this.fieldName)
                .append(';')
                .toString();

    }

}
