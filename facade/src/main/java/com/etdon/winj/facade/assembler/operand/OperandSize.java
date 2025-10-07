package com.etdon.winj.facade.assembler.operand;

import com.etdon.commons.conditional.Preconditions;
import org.jetbrains.annotations.NotNull;

/**
 * Operand size values between 1 and 8 bytes.
 */
public enum OperandSize {

    /**
     * Byte, 1 byte.
     */
    BYTE(8),
    /**
     * Alternative name for {@link OperandSize#BYTE}.
     */
    BIT_8(8),
    /**
     * Word, 2 bytes.
     */
    WORD(16),
    /**
     * Alternative name for {@link OperandSize#WORD}.
     */
    BIT_16(16),
    /**
     * Double word, 4 bytes.
     */
    DWORD(32),
    /**
     * Alternative name for {@link OperandSize#DWORD}.
     */
    BIT_32(32),
    /**
     * Quad word, 8 bytes.
     */
    QWORD(64),
    /**
     * Alternative name for {@link OperandSize#QWORD}.
     */
    BIT_64(64);

    /**
     * The size of the operand in bits.
     */
    final int bitSize;

    OperandSize(final int bitSize) {

        this.bitSize = bitSize;

    }

    /**
     * Returns the size of the operand in bits.
     *
     * @return The operand size in bits.
     */
    public int getBitSize() {

        return this.bitSize;

    }

    /**
     * Converts the provided primitive number into the corresponding {@link OperandSize} value. An integer will, for
     * example, be converted to {@link OperandSize#DWORD}.
     *
     * @param input The primitive number input.
     * @return The converted {@link OperandSize} value.
     */
    @NotNull
    public static OperandSize fromPrimitive(@NotNull final Number input) {

        Preconditions.checkNotNull(input);
        return switch (input) {
            case Byte _ -> OperandSize.BYTE;
            case Short _ -> OperandSize.WORD;
            case Integer _, Float _ -> OperandSize.DWORD;
            case Long _, Double _ -> OperandSize.QWORD;
            default -> throw new IllegalArgumentException("Unknown input type: " + input);
        };

    }

}
