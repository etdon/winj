package com.etdon.winjgen.component.impl;

import com.etdon.commons.util.StringUtils;
import com.etdon.winjgen.constant.Constants;
import com.etdon.winjgen.type.NativeDataTypeMapper;
import com.etdon.winjgen.util.Humanizer;
import org.jetbrains.annotations.Nullable;

public class BuilderMethodComponent extends ClassComponent {

    private String functionName;
    private String fieldName;
    private String typeName;

    @Nullable
    public String getFunctionName() {

        return this.functionName;

    }

    public void setFunctionName(final String functionName) {

        this.functionName = functionName;

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

    @Override
    public String toString() {

        return new StringBuilder()
                .append(StringUtils.repeat(Constants.PADDING, 2))
                .append("/**")
                .append(StringUtils.repeat(System.lineSeparator(), 1))
                .append(StringUtils.repeat(Constants.PADDING, 2))
                .append("* Overrides the builder internal ").append(Humanizer.splitParameterName(this.fieldName)).append(" value with the provided value.")
                .append(StringUtils.repeat(System.lineSeparator(), 1))
                .append(StringUtils.repeat(Constants.PADDING, 2))
                .append("*")
                .append(StringUtils.repeat(System.lineSeparator(), 1))
                .append(StringUtils.repeat(Constants.PADDING, 2))
                .append("* @param ").append(this.fieldName).append(" The ").append(Humanizer.splitParameterName(this.fieldName))
                .append(". {@link ").append(this.functionName).append("#").append(this.fieldName).append("}")
                .append(StringUtils.repeat(System.lineSeparator(), 1))
                .append(StringUtils.repeat(Constants.PADDING, 2))
                .append("* @return The builder instance.")
                .append(StringUtils.repeat(System.lineSeparator(), 1))
                .append(StringUtils.repeat(Constants.PADDING, 2))
                .append("*/")
                .append(StringUtils.repeat(System.lineSeparator(), 1))
                .append(StringUtils.repeat(Constants.PADDING, 2))
                .append("public Builder ").append(this.fieldName)
                .append(NativeDataTypeMapper.PRIMITIVE_TO_NON_PRIMITIVE.containsKey(this.typeName) ? "(final " : "(@NotNull final ").append(this.typeName).append(' ').append(this.fieldName).append(") {")
                .append(StringUtils.repeat(System.lineSeparator(), 2))
                .append(StringUtils.repeat(Constants.PADDING, 3))
                .append("this.").append(this.fieldName).append(" = ").append(this.fieldName).append(';')
                .append(System.lineSeparator())
                .append(StringUtils.repeat(Constants.PADDING, 3))
                .append("return this;")
                .append(StringUtils.repeat(System.lineSeparator(), 2))
                .append(StringUtils.repeat(Constants.PADDING, 2))
                .append('}')
                .toString();

    }

}
