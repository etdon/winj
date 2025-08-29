package com.etdon.winj.facade.assembler.operand;

public enum OperandSize {

    BYTE(8),
    WORD(16),
    DWORD(32),
    QWORD(64);

    final int bitSize;

    OperandSize(final int bitSize) {

        this.bitSize = bitSize;

    }

    public int getBitSize() {

        return this.bitSize;

    }

    public static OperandSize fromPrimitive(final Number input) {

        return switch (input) {
            case Byte _ -> OperandSize.BYTE;
            case Short _ -> OperandSize.WORD;
            case Integer _, Float _ -> OperandSize.DWORD;
            case Long _, Double _ -> OperandSize.QWORD;
            case null, default -> throw new IllegalArgumentException("Unknown input type: " + input);
        };

    }

}
