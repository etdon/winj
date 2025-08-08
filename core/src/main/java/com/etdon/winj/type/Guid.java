package com.etdon.winj.type;

import com.etdon.jbinder.NativeType;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.UUID;

public final class Guid extends NativeType {

    public static final MemoryLayout GUID = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("Data1"),
            ValueLayout.JAVA_SHORT.withName("Data2"),
            ValueLayout.JAVA_SHORT.withName("Data3"),
            MemoryLayout.sequenceLayout(8,
                    ValueLayout.JAVA_BYTE
            ).withName("Data4")
    );

    /**
     * Specifies the first 8 hexadecimal digits of the GUID.
     */
    private final int data1;

    /**
     * Specifies the first group of 4 hexadecimal digits.
     */
    private final short data2;

    /**
     * Specifies the second group of 4 hexadecimal digits.
     */
    private final short data3;

    /**
     * Array of 8 bytes. The first 2 bytes contain the third group of 4 hexadecimal digits. The remaining 6 bytes
     * contain the final 12 hexadecimal digits.
     */
    private final byte[] data4 = new byte[8];

    public Guid(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(GUID.byteSize(), arena, null);

        this.data1 = memorySegment.get(ValueLayout.JAVA_INT, 0);
        this.data2 = memorySegment.get(ValueLayout.JAVA_SHORT, 4);
        this.data3 = memorySegment.get(ValueLayout.JAVA_SHORT, 6);
        for (int i = 0; i < 8; i++)
            this.data4[i] = memorySegment.get(ValueLayout.JAVA_BYTE, 8 + i);

    }

    @NotNull
    @Override
    public MemoryLayout getMemoryLayout() {

        return GUID;

    }

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment memorySegment = arena.allocate(GUID.byteSize());
        memorySegment.set(ValueLayout.JAVA_INT, 0, this.data1);
        memorySegment.set(ValueLayout.JAVA_SHORT, 4, this.data2);
        memorySegment.set(ValueLayout.JAVA_SHORT, 6, this.data3);
        for (int i = 0; i < 8; i++)
            memorySegment.set(ValueLayout.JAVA_BYTE, 8 + i, this.data4[i]);

        return memorySegment;

    }

    public int getData1() {

        return this.data1;

    }

    public short getData2() {

        return this.data2;

    }

    public short getData3() {

        return this.data3;

    }

    public byte[] getData4() {

        return this.data4;

    }

    @NotNull
    public UUID toUniqueId() {

        long mostSignificantBits = 0;
        mostSignificantBits |= ((long) this.data1) << 32;
        mostSignificantBits |= ((long) this.data2 & 0xFFFFL) << 16;
        mostSignificantBits |= ((long) this.data3 & 0xFFFFL);

        long leastSignificantBits = 0;
        for (int i = 0; i < 8; i++)
            leastSignificantBits |= ((long) (data4[i] & 0xFF)) << (8 * (7 - i));

        return new UUID(mostSignificantBits, leastSignificantBits);

    }

    @NotNull
    public static Guid fromUniqueId(@NotNull final Arena arena, @NotNull final UUID uuid) {

        final long mostSignificantBits = uuid.getMostSignificantBits();
        final long leastSignificantBits = uuid.getLeastSignificantBits();
        final MemorySegment memorySegment = arena.allocate(GUID.byteSize());
        memorySegment.set(ValueLayout.JAVA_INT, 0, (int) (mostSignificantBits >>> 32));
        memorySegment.set(ValueLayout.JAVA_SHORT, 4, (short) ((mostSignificantBits >>> 16) & 0xFFFF));
        memorySegment.set(ValueLayout.JAVA_SHORT, 6, (short) (mostSignificantBits & 0xFFFF));
        for (int i = 0; i < 8; i++)
            memorySegment.set(ValueLayout.JAVA_BYTE, 8 + i, (byte) (leastSignificantBits >>> (8 * (7 - i))));

        return new Guid(arena, memorySegment);

    }

}
