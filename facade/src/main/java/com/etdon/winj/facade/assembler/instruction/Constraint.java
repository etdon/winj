package com.etdon.winj.facade.assembler.instruction;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.assembler.operand.Operand;
import com.etdon.winj.facade.assembler.operand.OperandSize;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Constraint {

    public static final Constraint EMPTY = new Constraint();

    private final Class<? extends Operand> type;
    private final List<OperandSize> sizes;

    private Constraint() {

        this.type = null;
        this.sizes = List.of();

    }

    private Constraint(@NotNull final Class<? extends Operand> type,
                       @NotNull final OperandSize[] sizes) {

        this.type = type;
        this.sizes = Collections.unmodifiableList(Arrays.asList(sizes));

    }

    public Class<? extends Operand> getOperandType() {

        return this.type;

    }

    public boolean isSatisfying(@NotNull final Operand operand) {

        Preconditions.checkNotNull(operand);
        if (this.type == null)
            return false;

        return this.type.isAssignableFrom(operand.getClass()) && this.sizes.contains(operand.getSize());

    }

    public List<OperandSize> getSizes() {

        return this.sizes;

    }

    public static Constraint of(@NotNull final Class<? extends Operand> type, final OperandSize... sizes) {

        Preconditions.checkNotNull(type);
        return new Constraint(type, sizes);

    }

}
