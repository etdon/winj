package com.etdon.winj.facade.op.address;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.op.Opcode;
import com.etdon.winj.facade.op.register.Register;
import org.jetbrains.annotations.NotNull;

public final class RegisterAddressor extends Addressor {

    private final Register register;
    private final byte mod;

    private RegisterAddressor(final Register register,
                              final byte mod) {

        this.register = register;
        this.mod = mod;

    }

    public Register getRegister() {

        return this.register;

    }

    public byte getMod() {

        return this.mod;

    }

    public static RegisterAddressor of(@NotNull final Register register) {

        return of(register, Opcode.ModRM.Mod.RD);

    }

    public static RegisterAddressor of(@NotNull final Register register,
                                       final byte mod) {

        Preconditions.checkNotNull(register);
        return new RegisterAddressor(register, mod);

    }

}
