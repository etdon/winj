package com.etdon.winj.test.util;

import com.etdon.jbinder.util.MemoryUtils;
import org.junit.jupiter.api.Test;

import java.lang.foreign.MemorySegment;
import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MemoryUtilsTest {

    @Test
    void testToHexString() {

        final ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put((byte) 0x12);
        buffer.put((byte) 0x34);
        buffer.put((byte) 0x56);
        buffer.put((byte) 0x78);
        buffer.flip();

        final MemorySegment memorySegment = MemorySegment.ofArray(buffer.array());
        final String hexString = MemoryUtils.toHexString(memorySegment);
        assertEquals("12345678", hexString);

    }

    @Test
    void testToHexStringWithGroupSize() {

        final ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put((byte) 0x12);
        buffer.put((byte) 0x34);
        buffer.put((byte) 0x56);
        buffer.put((byte) 0x78);
        buffer.flip();

        final MemorySegment memorySegment = MemorySegment.ofArray(buffer.array());
        final String hexString = MemoryUtils.toHexString(memorySegment, 2);
        assertEquals("1234 5678", hexString);

    }

    @Test
    void testToHexStringEmptySegment() {

        final ByteBuffer buffer = ByteBuffer.allocate(0);
        final MemorySegment segment = MemorySegment.ofArray(buffer.array());
        final String hexString = MemoryUtils.toHexString(segment);
        assertEquals("", hexString);

    }

}
