package com.etdon.winj.facade.assembler.operand.encoding;

import com.etdon.winj.facade.assembler.Opcode;
import com.etdon.winj.facade.assembler.operand.Operand;

public abstract class InstructionEncoding {

    public abstract byte[] process(final Opcode opcode, final Operand... operands);

}
