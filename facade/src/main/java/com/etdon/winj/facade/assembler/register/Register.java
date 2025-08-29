package com.etdon.winj.facade.assembler.register;

import com.etdon.winj.facade.assembler.Opcodes;
import com.etdon.winj.facade.assembler.address.Addressable;
import com.etdon.winj.facade.assembler.address.RegisterAddressor;
import com.etdon.winj.facade.assembler.operand.Operand;
import org.jetbrains.annotations.NotNull;

public abstract class Register implements Operand, Addressable {

    private final byte value;
    private final boolean extended;

    protected Register(final byte value,
                       final boolean extended) {

        this.value = value;
        this.extended = extended;

    }

    public byte getValue() {

        return this.value;

    }

    public boolean isExtended() {

        return this.extended;

    }

    @NotNull
    public RegisterAddressor addressor() {

        return RegisterAddressor.of(this);

    }

    @NotNull
    public RegisterAddressor addressor(final byte mod) {

        return RegisterAddressor.of(this, mod);

    }

    @NotNull
    public RegisterAddressor addressor(final byte mod, final Opcodes.SIB sib) {

        return RegisterAddressor.of(this, mod, sib);

    }

}
