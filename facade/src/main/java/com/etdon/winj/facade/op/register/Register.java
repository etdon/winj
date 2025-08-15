package com.etdon.winj.facade.op.register;

import com.etdon.winj.facade.op.address.Addressable;

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

}
