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
    private final byte[] displacement;

    private RegisterAddressor(final Register register,
                              final byte mod,
                              final Opcodes.SIB sib,
                              final byte[] displacement) {

        this.register = register;
        this.mod = mod;
        this.sib = sib;
        this.displacement = displacement;

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

    public byte[] getDisplacement() {

        return this.displacement.clone();

    }

    public boolean requiresSIB() {

        return this.mod != Opcodes.ModRM.Mod.RD && this.register.getValue() == 0b100;

    }

    public boolean isBaseExtended() {

        return this.requiresSIB() ? this.sib.getBase().isExtended() : this.register.isExtended();

    }

    public boolean isIndexExtended() {

        return this.requiresSIB() && this.sib.getIndex().isExtended();

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

        return of(register, mod, sib, new byte[0]);

    }

    public static RegisterAddressor of(@NotNull final Register register,
                                       final byte mod,
                                       final Opcodes.SIB sib,
                                       final byte[] displacement) {

        Preconditions.checkNotNull(register);
        Preconditions.checkNotNull(displacement);
        if (mod == Opcodes.ModRM.Mod.RD && sib != null)
            throw new IllegalArgumentException("SIB cannot be used in register-direct mode.");
        if (sib != null && register.getValue() != 0b100)
            throw new IllegalArgumentException("SIB requires ModR/M r/m field 0b100.");
        if (mod != Opcodes.ModRM.Mod.RD && register.getValue() == 0b100 && sib == null)
            throw new IllegalArgumentException("SIB required for register-indirect mode with ModR/M r/m field 0b100.");

        if (mod == Opcodes.ModRM.Mod.RD && displacement.length > 0 ||
                mod == Opcodes.ModRM.Mod.RI_D0 && displacement.length != 0 ||
                mod == Opcodes.ModRM.Mod.RI_D8 && displacement.length != 1 ||
                mod == Opcodes.ModRM.Mod.RI_D32 && displacement.length != 4)
            throw new IllegalArgumentException("Displacement byte array length doesn't match specified displacement.");

        return new RegisterAddressor(register, mod, sib, displacement.clone());

    }

}
