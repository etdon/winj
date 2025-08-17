package com.etdon.winj.facade.op;

import com.etdon.commons.util.Exceptional;

public final class ByteBuffer {

    private byte[] buffer;
    private int size;

    private ByteBuffer(final int initialSize) {

        this.buffer = new byte[initialSize];

    }

    public void put(final byte value) {

        this.ensureSize(1);
        this.buffer[this.size++] = value;

    }

    public void put(final byte... values) {

        this.ensureSize(values.length);
        for (final byte value : values)
            this.buffer[this.size++] = value;

    }

    public void set(final int index, final byte value) {

        if (this.buffer.length <= index)
            throw Exceptional.of(RuntimeException.class, "Cannot size index '{}' for buffer with length '{}'.", index, this.buffer.length);
        this.buffer[index] = value;

    }

    public byte[] get() {

        if (this.buffer.length != this.size) {
            final byte[] resizedBuffer = new byte[this.size];
            System.arraycopy(this.buffer, 0, resizedBuffer, 0, this.size);
            this.buffer = resizedBuffer;
        }

        return this.buffer;

    }

    private void ensureSize(final int count) {

        if (this.buffer.length >= (this.size + count)) return;
        final int growth = Math.max((this.size + count) - this.buffer.length, this.buffer.length);
        final byte[] resizedBuffer = new byte[this.buffer.length + growth];
        System.arraycopy(this.buffer, 0, resizedBuffer, 0, this.buffer.length);
        this.buffer = resizedBuffer;

    }

    public static ByteBuffer auto() {

        return new ByteBuffer(64);

    }

    public static ByteBuffer size(final int initialSize) {

        return new ByteBuffer(initialSize);

    }

}
