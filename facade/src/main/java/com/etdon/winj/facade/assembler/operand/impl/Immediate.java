package com.etdon.winj.facade.assembler.operand.impl;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.assembler.operand.Operand;
import com.etdon.winj.facade.assembler.operand.OperandSize;
import com.etdon.winj.marshal.AutoMarshal;
import com.etdon.winj.marshal.primitive.PrimitiveMarshalContext;
import org.jetbrains.annotations.NotNull;

public record Immediate(Number value) implements Operand {

    @Override
    public OperandSize getSize() {

        return OperandSize.fromPrimitive(this.value);

    }

    public byte[] marshal() {

        return AutoMarshal.marshal(this.value, PrimitiveMarshalContext.empty());

    }

    public static Immediate of(@NotNull final Number value) {

        Preconditions.checkNotNull(value);
        return new Immediate(value);

    }

}
