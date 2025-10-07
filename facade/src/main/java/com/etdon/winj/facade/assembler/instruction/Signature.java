package com.etdon.winj.facade.assembler.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.util.Exceptional;
import com.etdon.winj.facade.assembler.operand.Operand;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Signature {

    public static final Signature EMPTY = new Signature();

    private final List<Constraint> constraints;

    private Signature() {

        this.constraints = List.of();

    }

    private Signature(@NotNull final Constraint[] constraints) {

        this.constraints = Collections.unmodifiableList(Arrays.asList(constraints));

    }

    public Constraint getConstraint(final int index) {

        if (index < 0 || index > this.constraints.size())
            throw Exceptional.of(IllegalArgumentException.class, "The provided index {} is out of bounds: 0 to {}", index, this.constraints.size());
        return this.constraints.get(index);

    }

    public boolean isSatisfying(@NotNull final Operand... operands) {

        Preconditions.checkNotNull(operands);
        if (this.constraints.isEmpty() && operands.length > 0)
            return true;
        if (!this.constraints.isEmpty() && operands.length == 0)
            return false;

        for (int i = 0; i < operands.length; i++) {
            final Operand operand = operands[i];
            final Constraint constraint = this.constraints.get(i);
            if (!constraint.isSatisfying(operand))
                return false;
        }

        return true;

    }

    public List<Constraint> getConstraints() {

        return this.constraints;

    }

    public static Signature of(@NotNull final Constraint... constraints) {

        Preconditions.checkNotNull(constraints);
        return new Signature(constraints);

    }

}
