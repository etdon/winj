package com.etdon.winj.type;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Conditional;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.jbinder.NativeType;
import com.etdon.winj.constant.WindowClassStyle;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_INT;

public final class WindowClass extends NativeType {

    public static final MemoryLayout WNDCLASSEXA = MemoryLayout.structLayout(
            JAVA_INT.withName("cbSize"),
            JAVA_INT.withName("style"),
            ADDRESS.withName("lpfnWndProc"),
            JAVA_INT.withName("cbClsExtra"),
            JAVA_INT.withName("cbWndExtra"),
            ADDRESS.withName("hInstance"),
            ADDRESS.withName("hIcon"),
            ADDRESS.withName("hCursor"),
            ADDRESS.withName("hbrBackground"),
            ADDRESS.withName("lpszMenuName"),
            ADDRESS.withName("lpszClassName"),
            ADDRESS.withName("hIconSm")
    );

    /**
     * The size, in bytes, of this structure. Set this member to sizeof(WNDCLASSEX). Be sure to set this member before
     * calling the GetClassInfoEx function.
     */
    private final int size;

    /**
     * The class style(s). This member can be any combination of the Class Styles.
     *
     * @see WindowClassStyle
     */
    private int classStyle = 0;

    /**
     * A pointer to the window procedure. You must use the CallWindowProc function to call the window procedure.
     */
    private final MemorySegment procedurePointer;

    /**
     * The number of extra bytes to allocate following the window-class structure. The system initializes the bytes to
     * zero.
     */
    private int extraClassBytes = 0;

    /**
     * The number of extra bytes to allocate following the window instance. The system initializes the bytes to zero.
     * If an application uses WNDCLASSEX to register a dialog box created by using the CLASS directive in the resource
     * file, it must set this member to DLGWINDOWEXTRA.
     */
    private int extraWindowBytes = 0;

    /**
     * A handle to the instance that contains the window procedure for the class.
     */
    private final MemorySegment procedureOwner;

    /**
     * A handle to the class icon. This member must be a handle to an icon resource. If this member is NULL, the system
     * provides a default icon.
     */
    private MemorySegment iconResourceHandle = MemorySegment.NULL;

    /**
     * A handle to the class cursor. This member must be a handle to a cursor resource. If this member is NULL, an
     * application must explicitly set the cursor shape whenever the mouse moves into the application's window.
     */
    private MemorySegment cursorHandle = MemorySegment.NULL;

    /**
     * A handle to the class background brush. This member can be a handle to the brush to be used for painting the
     * background, or it can be a color value. A color value must be one of the following standard system colors
     * (the value 1 must be added to the chosen color).
     */
    private MemorySegment backgroundBrushHandle = MemorySegment.NULL;

    /**
     * A pointer to a null-terminated character string that specifies the resource name of the class menu, as the name
     * appears in the resource file. If you use an integer to identify the menu, use the MAKEINTRESOURCE macro. If this
     * member is NULL, windows belonging to this class have no default menu.
     */
    private MemorySegment menuName = MemorySegment.NULL;

    /**
     * A pointer to a null-terminated string or is an atom. If this parameter is an atom, it must be a class atom
     * created by a previous call to the RegisterClass or RegisterClassEx function. The atom must be in the low-order
     * word of lpszClassName; the high-order word must be zero.
     * <p>
     * If lpszClassName is a string, it specifies the window class name. The class name can be any name registered with
     * RegisterClass or RegisterClassEx, or any of the predefined control-class names.
     * <p>
     * The maximum length for lpszClassName is 256. If lpszClassName is greater than the maximum length,
     * the RegisterClassEx function will fail.
     */
    private final MemorySegment className;

    /**
     * A handle to a small icon that is associated with the window class. If this member is NULL, the system searches
     * the icon resource specified by the hIcon member for an icon of the appropriate size to use as the small icon.
     */
    private MemorySegment smallIconHandle = MemorySegment.NULL;

    public WindowClass(final MemorySegment procedurePointer,
                       final MemorySegment procedureOwner,
                       final MemorySegment className) {

        this.size = (int) WNDCLASSEXA.byteSize();
        this.procedurePointer = procedurePointer;
        this.procedureOwner = procedureOwner;
        this.className = className;

    }

    public WindowClass(final int classStyle,
                       final MemorySegment procedurePointer,
                       final int extraClassBytes,
                       final int extraWindowBytes,
                       final MemorySegment procedureOwner,
                       final MemorySegment iconResourceHandle,
                       final MemorySegment cursorHandle,
                       final MemorySegment backgroundBrushHandle,
                       final MemorySegment menuName,
                       final MemorySegment className,
                       final MemorySegment smallIconHandle) {

        this.size = (int) WNDCLASSEXA.byteSize();
        this.classStyle = classStyle;
        this.procedurePointer = procedurePointer;
        this.extraClassBytes = extraClassBytes;
        this.extraWindowBytes = extraWindowBytes;
        this.procedureOwner = procedureOwner;
        this.iconResourceHandle = iconResourceHandle;
        this.cursorHandle = cursorHandle;
        this.backgroundBrushHandle = backgroundBrushHandle;
        this.menuName = menuName;
        this.className = className;
        this.smallIconHandle = smallIconHandle;

    }

    public WindowClass(@NotNull final Arena arena, @NotNull MemorySegment memorySegment) {

        if (memorySegment.byteSize() == 0)
            memorySegment = memorySegment.reinterpret(WNDCLASSEXA.byteSize(), arena, null);

        this.size = memorySegment.get(JAVA_INT, 0);
        this.classStyle = memorySegment.get(JAVA_INT, 4);
        this.procedurePointer = memorySegment.get(ADDRESS, 8);
        this.extraClassBytes = memorySegment.get(JAVA_INT, 16);
        this.extraWindowBytes = memorySegment.get(JAVA_INT, 20);
        this.procedureOwner = memorySegment.get(ADDRESS, 24);
        this.iconResourceHandle = memorySegment.get(ADDRESS, 32);
        this.cursorHandle = memorySegment.get(ADDRESS, 40);
        this.backgroundBrushHandle = memorySegment.get(ADDRESS, 48);
        this.menuName = memorySegment.get(ADDRESS, 56);
        this.className = memorySegment.get(ADDRESS, 64);
        this.smallIconHandle = memorySegment.get(ADDRESS, 72);

    }

    private WindowClass(final Builder builder) {

        this.size = (int) WNDCLASSEXA.byteSize();
        Conditional.executeIfNotNull(builder.classStyle, () -> this.classStyle = builder.classStyle);
        this.procedurePointer = builder.procedurePointer;
        Conditional.executeIfNotNull(builder.extraClassBytes, () -> this.extraClassBytes = builder.extraClassBytes);
        Conditional.executeIfNotNull(builder.extraWindowBytes, () -> this.extraWindowBytes = builder.extraWindowBytes);
        this.procedureOwner = builder.procedureOwner;
        Conditional.executeIfNotNull(builder.iconResourceHandle, () -> this.iconResourceHandle = builder.iconResourceHandle);
        Conditional.executeIfNotNull(builder.cursorHandle, () -> this.cursorHandle = builder.cursorHandle);
        Conditional.executeIfNotNull(builder.backgroundBrushHandle, () -> this.backgroundBrushHandle = builder.backgroundBrushHandle);
        Conditional.executeIfNotNull(builder.menuName, () -> this.menuName = builder.menuName);
        this.className = builder.className;
        Conditional.executeIfNotNull(builder.smallIconHandle, () -> this.smallIconHandle = builder.smallIconHandle);

    }

    @NotNull
    @Override
    public MemoryLayout getMemoryLayout() {

        return WNDCLASSEXA;

    }

    @NotNull
    @Override
    public MemorySegment createMemorySegment(@NotNull final Arena arena) {

        final MemorySegment memorySegment = arena.allocate(WNDCLASSEXA);
        memorySegment.set(JAVA_INT, 0, this.size);
        memorySegment.set(JAVA_INT, 4, this.classStyle);
        memorySegment.set(ADDRESS, 8, this.procedurePointer);
        memorySegment.set(JAVA_INT, 16, this.extraClassBytes);
        memorySegment.set(JAVA_INT, 20, this.extraWindowBytes);
        memorySegment.set(ADDRESS, 24, this.procedureOwner);
        memorySegment.set(ADDRESS, 32, this.iconResourceHandle);
        memorySegment.set(ADDRESS, 40, this.cursorHandle);
        memorySegment.set(ADDRESS, 48, this.backgroundBrushHandle);
        memorySegment.set(ADDRESS, 56, this.menuName);
        memorySegment.set(ADDRESS, 64, this.className);
        memorySegment.set(ADDRESS, 72, this.smallIconHandle);

        return memorySegment;

    }

    public int getSize() {

        return this.size;

    }

    public int getClassStyle() {

        return this.classStyle;

    }

    public MemorySegment getProcedurePointer() {

        return this.procedurePointer;

    }

    public int getExtraClassBytes() {

        return this.extraClassBytes;

    }

    public int getExtraWindowBytes() {

        return this.extraWindowBytes;

    }

    public MemorySegment getProcedureOwner() {

        return this.procedureOwner;

    }

    public MemorySegment getIconResourceHandle() {

        return this.iconResourceHandle;

    }

    public MemorySegment getCursorHandle() {

        return this.cursorHandle;

    }

    public MemorySegment getBackgroundBrushHandle() {

        return this.backgroundBrushHandle;

    }

    public MemorySegment getMenuName() {

        return this.menuName;

    }

    public MemorySegment getClassName() {

        return this.className;

    }

    public MemorySegment getSmallIconHandle() {

        return this.smallIconHandle;

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<WindowClass> {

        private Integer classStyle;
        private MemorySegment procedurePointer;
        private Integer extraClassBytes;
        private Integer extraWindowBytes;
        private MemorySegment procedureOwner;
        private MemorySegment iconResourceHandle;
        private MemorySegment cursorHandle;
        private MemorySegment backgroundBrushHandle;
        private MemorySegment menuName;
        private MemorySegment className;
        private MemorySegment smallIconHandle;

        /**
         * Overrides the builder internal class style value with the provided value.
         *
         * @param classStyle The class style. {@link WindowClass#classStyle}
         * @return The builder instance.
         */
        public Builder classStyle(final int classStyle) {

            this.classStyle = classStyle;
            return this;

        }

        /**
         * Overrides the builder internal procedure pointer value with the provided value.
         *
         * @param procedurePointer The procedure pointer.
         * @return The builder instance.
         */
        public Builder procedurePointer(@NotNull final MemorySegment procedurePointer) {

            this.procedurePointer = procedurePointer;
            return this;

        }

        /**
         * Overrides the builder internal extra class bytes value with the provided value.
         *
         * @param extraClassBytes The extra class bytes.
         * @return The builder instance.
         */
        public Builder extraClassBytes(final int extraClassBytes) {

            this.extraClassBytes = extraClassBytes;
            return this;

        }

        /**
         * Overrides the builder internal extra window bytes value with the provided value.
         *
         * @param extraWindowBytes The extra window bytes.
         * @return The builder instance.
         */
        public Builder extraWindowBytes(final int extraWindowBytes) {

            this.extraWindowBytes = extraWindowBytes;
            return this;

        }

        /**
         * Overrides the builder internal procedure owner value with the provided value.
         *
         * @param procedureOwner The procedure owner.
         * @return The builder instance.
         */
        public Builder procedureOwner(@NotNull final MemorySegment procedureOwner) {

            this.procedureOwner = procedureOwner;
            return this;

        }

        /**
         * Overrides the builder internal icon resource handle value with the provided value.
         *
         * @param iconResourceHandle The icon resource handle.
         * @return The builder instance.
         */
        public Builder iconResourceHandle(@NotNull final MemorySegment iconResourceHandle) {

            this.iconResourceHandle = iconResourceHandle;
            return this;

        }

        /**
         * Overrides the builder internal cursor handle value with the provided value.
         *
         * @param cursorHandle The cursor handle.
         * @return The builder instance.
         */
        public Builder cursorHandle(@NotNull final MemorySegment cursorHandle) {

            this.cursorHandle = cursorHandle;
            return this;

        }

        /**
         * Overrides the builder internal background brush handle value with the provided value.
         *
         * @param backgroundBrushHandle The background brush handle.
         * @return The builder instance.
         */
        public Builder backgroundBrushHandle(@NotNull final MemorySegment backgroundBrushHandle) {

            this.backgroundBrushHandle = backgroundBrushHandle;
            return this;

        }

        /**
         * Overrides the builder internal menu name value with the provided value.
         *
         * @param menuName The menu name.
         * @return The builder instance.
         */
        public Builder menuName(@NotNull final MemorySegment menuName) {

            this.menuName = menuName;
            return this;

        }

        /**
         * Overrides the builder internal class name value with the provided value.
         *
         * @param className The class name.
         * @return The builder instance.
         */
        public Builder className(@NotNull final MemorySegment className) {

            this.className = className;
            return this;

        }

        /**
         * Overrides the builder internal small icon handle value with the provided value.
         *
         * @param smallIconHandle The small icon handle.
         * @return The builder instance.
         */
        public Builder smallIconHandle(@NotNull final MemorySegment smallIconHandle) {

            this.smallIconHandle = smallIconHandle;
            return this;

        }

        @NotNull
        @Override
        public WindowClass build() {

            Preconditions.checkNotNull(this.procedurePointer);
            Preconditions.checkNotNull(this.procedureOwner);
            Preconditions.checkNotNull(this.className);

            return new WindowClass(this);

        }

    }

}
