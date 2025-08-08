package com.etdon.winj.type;

import com.etdon.commons.annotation.RequiresTesting;
import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.NativeType;
import com.etdon.jbinder.common.NativeDocumentation;
import com.etdon.jbinder.common.NativeName;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static com.etdon.winj.type.constant.NativeDataType.*;
import static com.etdon.winj.type.Rectangle.RECT;
import static java.lang.foreign.ValueLayout.*;


@RequiresTesting
@NativeDocumentation("https://learn.microsoft.com/en-us/windows/win32/api/winuser/ns-winuser-paintstruct")
@NativeName("tagPAINTSTRUCT")
public final class PaintData extends NativeType {

    public static final MemoryLayout PAINTSTRUCT = MemoryLayout.structLayout(
            HDC.withName("hdc"),
            BOOL.withName("fErase"),
            RECT.withName("rcPaint"),
            BOOL.withName("fRestore"),
            BOOL.withName("fIncUpdate"),
            MemoryLayout.sequenceLayout(32,
                    BYTE
            ).withName("rgbReserved")
    );

    /**
     * A handle to the display DC to be used for painting.
     */
    private final MemorySegment deviceContextHandle;

    /**
     * Indicates whether the background must be erased. This value is nonzero if the application should erase the
     * background. The application is responsible for erasing the background if a window class is created without a
     * background brush. For more information, see the description of the hbrBackground member of the WNDCLASS
     * structure.
     */
    private final int eraseBackground;

    /**
     * A RECT structure that specifies the upper left and lower right corners of the rectangle in which the painting is
     * requested, in device units relative to the upper-left corner of the client area.
     */
    private final MemorySegment rectangle;

    /**
     * Reserved; used internally by the system.
     */
    private final int restore;

    /**
     * Reserved; used internally by the system.
     */
    private final int update;

    /**
     * Reserved; used internally by the system.
     */
    private final byte[] reservedRgb;

    public PaintData(@NotNull final MemorySegment deviceContextHandle,
                     final int eraseBackground,
                     @NotNull final MemorySegment rectangle,
                     final int restore,
                     final int update,
                     final byte[] reservedRgb) {

        Preconditions.checkNotNull(deviceContextHandle);
        Preconditions.checkNotNull(rectangle);
        this.deviceContextHandle = deviceContextHandle;
        this.eraseBackground = eraseBackground;
        this.rectangle = rectangle;
        this.restore = restore;
        this.update = update;
        this.reservedRgb = reservedRgb;

    }

    public PaintData(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(PAINTSTRUCT.byteSize(), arena, null);

        this.deviceContextHandle = memorySegment.get(ADDRESS, 0);
        this.eraseBackground = memorySegment.get(JAVA_INT, 8);
        this.rectangle = memorySegment.asSlice(12, RECT);
        this.restore = memorySegment.get(JAVA_INT, 12 + RECT.byteSize());
        this.update = memorySegment.get(JAVA_INT, 12 + RECT.byteSize() + 4);
        final byte[] reserveRgb = new byte[32];
        for (int i = 0; i < 32; i++)
            reserveRgb[i] = memorySegment.get(JAVA_BYTE, 12 + RECT.byteSize() + 8 + i);
        this.reservedRgb = reserveRgb;

    }

    private PaintData(@NotNull final Builder builder) {

        this.deviceContextHandle = builder.deviceContextHandle;
        this.eraseBackground = builder.eraseBackground;
        this.rectangle = builder.rectangle;
        this.restore = builder.restore;
        this.update = builder.update;
        this.reservedRgb = builder.reservedRgb;

    }

    @NotNull
    @Override
    public MemoryLayout getMemoryLayout() {

        return PAINTSTRUCT;

    }

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment memorySegment = arena.allocate(PAINTSTRUCT);
        memorySegment.set(ADDRESS, 0, this.deviceContextHandle);
        memorySegment.set(JAVA_INT, 8, this.eraseBackground);
        MemorySegment.copy(this.rectangle, 0, memorySegment, 12, RECT.byteSize());
        memorySegment.set(JAVA_INT, 12 + RECT.byteSize(), this.restore);
        memorySegment.set(JAVA_INT, 12 + RECT.byteSize() + 4, this.update);
        for (int i = 0; i < 32; i++)
            memorySegment.set(JAVA_BYTE, 12 + RECT.byteSize() + 8 + i, this.reservedRgb[i]);

        return memorySegment;

    }

    public MemorySegment getDeviceContextHandle() {

        return this.deviceContextHandle;

    }

    public int getEraseBackground() {

        return this.eraseBackground;

    }

    public MemorySegment getRectangle() {

        return this.rectangle;

    }

    public int getRestore() {

        return this.restore;

    }

    public int getUpdate() {

        return this.update;

    }

    public byte[] getReservedRgb() {

        return this.reservedRgb;

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<PaintData> {

        private MemorySegment deviceContextHandle;
        private int eraseBackground;
        private MemorySegment rectangle;
        private int restore;
        private int update;
        private byte[] reservedRgb;

        public Builder deviceContextHandle(@NotNull final MemorySegment deviceContextHandle) {

            this.deviceContextHandle = deviceContextHandle;
            return this;

        }

        public Builder eraseBackground(final int eraseBackground) {

            this.eraseBackground = eraseBackground;
            return this;

        }

        public Builder rectangle(@NotNull final MemorySegment rectangle) {

            this.rectangle = rectangle;
            return this;

        }

        public Builder restore(final int restore) {

            this.restore = restore;
            return this;

        }

        public Builder update(final int update) {

            this.update = update;
            return this;

        }

        public Builder reservedRgb(final byte[] reservedRgb) {

            this.reservedRgb = reservedRgb;
            return this;

        }

        @NotNull
        @Override
        public PaintData build() {

            return new PaintData(this);

        }

    }

}
