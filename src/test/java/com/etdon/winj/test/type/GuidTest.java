package com.etdon.winj.test.type;

import com.etdon.winj.type.Guid;
import org.junit.jupiter.api.Test;

import java.lang.foreign.Arena;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GuidTest {

    @Test
    public void testFromUniqueId() {

        final UUID uuid = UUID.fromString("7c48c246-cc62-47f4-be30-1157de092195");
        try (final Arena arena = Arena.ofConfined()) {
            final Guid guid = Guid.fromUniqueId(arena, uuid);
            final short[] data4 = new short[]{0xBE, 0x30, 0x11, 0x57, 0xDE, 0x09, 0x21, 0x95};
            assertEquals(0x7c48c246, guid.getData1());
            assertEquals(0xffffcc62, guid.getData2());
            assertEquals(0x47f4, guid.getData3());
            final byte[] bytes = guid.getData4();
            for (int i = 0; i < bytes.length; i++)
                assertEquals((byte) data4[i], bytes[i]);
        }

    }

    @Test
    public void testToUniqueId() {

        final UUID uuid = UUID.fromString("7c48c246-cc62-47f4-be30-1157de092195");
        try (final Arena arena = Arena.ofConfined()) {
            final Guid guid = Guid.fromUniqueId(arena, uuid);
            assertEquals(uuid, guid.toUniqueId());
        }

    }

}
