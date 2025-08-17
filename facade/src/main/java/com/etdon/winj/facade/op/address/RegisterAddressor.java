package com.etdon.winj.facade.op.address;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.register.Register;
import org.jetbrains.annotations.NotNull;

public final class RegisterAddressor extends Addressor {

    private final Register register;
    private final byte mod;
    private final byte sib;

    private RegisterAddressor(final Register register,
                              final byte mod,
                              final byte sib) {

        this.register = register;
        this.mod = mod;
        this.sib = sib;

    }

    public Register getRegister() {

        return this.register;

    }

    public byte getMod() {

        return this.mod;

    }

    public byte getSIB() {

        return this.sib;

    }

    public boolean requiresSIB() {

        return this.mod != Opcode.ModRM.Mod.RD && this.register.getValue() == 0b100;

    }

    public static RegisterAddressor of(@NotNull final Register register) {

        return of(register, Opcode.ModRM.Mod.RD);

    }

    public static RegisterAddressor of(@NotNull final Register register,
                                       final byte mod) {

        return of(register, mod, (byte) 0);

    }

    public static RegisterAddressor of(@NotNull final Register register,
                                       final byte mod,
                                       final byte sib) {

        Preconditions.checkNotNull(register);
        return new RegisterAddressor(register, mod, sib);

    }

}
