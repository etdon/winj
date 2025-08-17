package com.etdon.winj.facade.op.register;

import com.etdon.winj.facade.op.address.Addressable;
import com.etdon.winj.facade.op.address.RegisterAddressor;
import org.jetbrains.annotations.NotNull;

public abstract class Register implements Addressable {

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
    public RegisterAddressor addressor(final byte mod, final byte sib) {

        return RegisterAddressor.of(this, mod, sib);

    }

}
