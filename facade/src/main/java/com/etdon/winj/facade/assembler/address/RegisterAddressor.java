package com.etdon.winj.facade.assembler.address;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.winj.facade.assembler.Opcodes;
import com.etdon.winj.facade.assembler.operand.Operand;
import com.etdon.winj.facade.assembler.operand.OperandSize;
import com.etdon.winj.facade.assembler.register.Register;
import org.jetbrains.annotations.NotNull;

public final class RegisterAddressor extends Addressor implements Operand {

    private final Register register;
    private final byte mod;
    private final Opcodes.SIB sib;

    private RegisterAddressor(final Register register,
                              final byte mod,
                              final Opcodes.SIB sib) {

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

    public Opcodes.SIB getSIB() {

        return this.sib;

    }

    public boolean requiresSIB() {

        return this.mod != Opcodes.ModRM.Mod.RD && this.register.getValue() == 0b100;

    }

    @Override
    public OperandSize getSize() {

        return this.register.getSize();

    }

    public static RegisterAddressor of(@NotNull final Register register) {

        return of(register, Opcodes.ModRM.Mod.RD);

    }

    public static RegisterAddressor of(@NotNull final Register register,
                                       final byte mod) {

        return of(register, mod, null);

    }

    public static RegisterAddressor of(@NotNull final Register register,
                                       final byte mod,
                                       final Opcodes.SIB sib) {

        Preconditions.checkNotNull(register);
        return new RegisterAddressor(register, mod, sib);

    }

}
