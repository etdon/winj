package com.etdon.winj.util;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.io.ByteOrder;
import com.etdon.commons.util.Exceptional;
import org.jetbrains.annotations.NotNull;

public final class ByteBuffer {

    private ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
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
        if (this.byteOrder == ByteOrder.LITTLE_ENDIAN) {
            for (final byte value : values)
                this.buffer[this.size++] = value;
        } else {
            for (int i = values.length - 1; i >= 0; i--)
                this.buffer[this.size++] = values[i];
        }

    }

    public void put(final short value) {

        final byte[] buffer = new byte[Short.BYTES];
        for (int i = 0; i < Short.BYTES; i++)
            buffer[i] = (byte) ((value >>> (i * 8)) & 0xFF);
        this.put(buffer);

    }

    public void put(final int value) {

        final byte[] buffer = new byte[Integer.BYTES];
        for (int i = 0; i < Integer.BYTES; i++)
            buffer[i] = (byte) ((value >>> (i * 8)) & 0xFF);
        this.put(buffer);

    }

    public void put(final float value) {

        final int floatBits = Float.floatToIntBits(value);
        final byte[] buffer = new byte[Float.BYTES];
        for (int i = 0; i < Float.BYTES; i++)
            buffer[i] = (byte) ((floatBits >>> (i * 8)) & 0xFF);
        this.put(buffer);

    }

    public void put(final long value) {

        final byte[] buffer = new byte[Long.BYTES];
        for (int i = 0; i < Long.BYTES; i++)
            buffer[i] = (byte) ((value >>> (i * 8)) & 0xFF);
        this.put(buffer);

    }

    public void put(final double value) {

        final long doubleBits = Double.doubleToLongBits(value);
        final byte[] buffer = new byte[Double.BYTES];
        for (int i = 0; i < Double.BYTES; i++)
            buffer[i] = (byte) ((doubleBits >>> (i * 8)) & 0xFF);
        this.put(buffer);

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

    public void setByteOrder(@NotNull final ByteOrder byteOrder) {

        Preconditions.checkNotNull(byteOrder);
        this.byteOrder = byteOrder;

    }

    public ByteOrder getByteOrder() {

        return this.byteOrder;

    }

    public static ByteBuffer auto() {

        return new ByteBuffer(64);

    }

    public static ByteBuffer size(final int initialSize) {

        return new ByteBuffer(initialSize);

    }

}
