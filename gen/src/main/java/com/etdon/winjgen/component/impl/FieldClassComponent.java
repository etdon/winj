package com.etdon.winjgen.component.impl;

import com.etdon.winjgen.constant.Constants;
import org.jetbrains.annotations.Nullable;

public class FieldClassComponent extends ClassComponent {

    private String nativeName;
    private String fieldName;
    private String typeName;
    private boolean finalState;

    @Nullable
    public String getNativeName() {

        return this.nativeName;

    }

    public void setNativeName(@Nullable final String nativeName) {

        this.nativeName = nativeName;

    }

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

        return new StringBuilder()
                .append(Constants.PADDING)
                .append("@NativeName(\"").append(this.nativeName).append("\")")
                .append(System.lineSeparator()).append(Constants.PADDING)
                .append("private ").append(this.finalState ? "final " : "")
                // TODO: Add default value for non-final non-primitive values.
                .append(this.typeName).append(' ').append(this.fieldName).append(";")
                .toString();

    }

}
