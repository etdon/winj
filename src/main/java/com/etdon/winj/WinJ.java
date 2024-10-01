package com.etdon.winj;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.di.InstanceServiceProvider;
import com.etdon.commons.di.ServiceProvider;
import com.etdon.commons.util.ColorUtils;
import com.etdon.commons.util.Exceptional;
import com.etdon.jbinder.SymbolLookupCache;
import com.etdon.jbinder.function.NativeCaller;
import com.etdon.winj.constant.*;
import com.etdon.winj.function.gdi32.GetStockObject;
import com.etdon.winj.function.gdi32.SetBkMode;
import com.etdon.winj.function.gdi32.SetTextColor;
import com.etdon.winj.function.kernel32.*;
import com.etdon.winj.function.psapi.GetDeviceDriverBaseNameW;
import com.etdon.winj.function.psapi.GetDeviceDriverFileNameW;
import com.etdon.winj.function.user32.*;
import com.etdon.winj.function.ntdll.NtGetTickCount;
import com.etdon.winj.function.ntdll.NtQueryDefaultLocale;
import com.etdon.winj.function.psapi.EnumDeviceDrivers;
import com.etdon.winj.function.std.AllocateMemory;
import com.etdon.winj.function.std.FreeMemory;
import com.etdon.winj.hook.ManagedHook;
import com.etdon.winj.hook.HookManager;
import com.etdon.winj.input.InputSender;
import com.etdon.winj.procedure.LowLevelKeyboardProcedure;
import com.etdon.winj.procedure.WindowProcedure;
import com.etdon.winj.render.debug.queue.DebugRenderQueue;
import com.etdon.winj.render.debug.queue.StringDebugRenderQueueItem;
import com.etdon.winj.type.LowLevelKeyboardInput;
import com.etdon.winj.type.Message;
import com.etdon.winj.type.PaintData;
import com.etdon.winj.type.Window;
import com.etdon.winj.util.Flag;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.*;
import java.nio.charset.StandardCharsets;

import static com.etdon.winj.type.NativeDataType.*;
import static com.etdon.winj.constant.Library.*;
import static java.lang.foreign.ValueLayout.*;
import static java.lang.foreign.MemorySegment.*;

public class WinJ {

    private final ServiceProvider serviceProvider = InstanceServiceProvider.builder()
            .selfService()
            .build();
    private final SymbolLookupCache symbolLookupCache = new SymbolLookupCache();

    private final Arena arena;
    private final Linker linker;
    private final NativeCaller nativeCaller;
    private final DebugRenderQueue debugRenderQueue;
    private final HookManager hookManager;

    public WinJ(@NotNull final Arena arena) {

        Preconditions.checkNotNull(arena);
        this.serviceProvider.register(this.symbolLookupCache);
        this.arena = arena;
        this.serviceProvider.register(this.arena);
        this.linker = Linker.nativeLinker();
        this.serviceProvider.register(this.linker);
        this.nativeCaller = new NativeCaller(this.linker, this.symbolLookupCache);
        this.serviceProvider.register(this.nativeCaller);
        this.debugRenderQueue = new DebugRenderQueue(this.arena, this.nativeCaller);
        this.serviceProvider.register(this.debugRenderQueue);
        this.hookManager = new HookManager(this.serviceProvider);
        this.serviceProvider.register(this.hookManager);
        this.initialize();

    }

    private void initialize() {

        this.symbolLookupCache.register(USER_32, SymbolLookup.libraryLookup(USER_32, arena));
        this.symbolLookupCache.register(KERNEL_32, SymbolLookup.libraryLookup(KERNEL_32, arena));
        this.symbolLookupCache.register(GDI_32, SymbolLookup.libraryLookup(GDI_32, arena));
        this.symbolLookupCache.register(PSAPI, SymbolLookup.libraryLookup(PSAPI, arena));
        this.symbolLookupCache.register(NTDLL, SymbolLookup.libraryLookup(NTDLL, arena));
        this.symbolLookupCache.register(ADVAPI_32, SymbolLookup.libraryLookup(ADVAPI_32, arena));

    }

    public void demo() {

        try {
            this.debugRenderQueue.add(StringDebugRenderQueueItem.repeating("PID: " + this.nativeCaller.call(GetCurrentProcessId.getInstance()).toString()));
            this.debugRenderQueue.add(StringDebugRenderQueueItem.repeating("ntGetTickCount: " + this.nativeCaller.call(NtGetTickCount.getInstance())));
            final MemorySegment languageIdPointer = this.arena.allocate(JAVA_INT, 1);
            this.nativeCaller.call(
                    NtQueryDefaultLocale.builder()
                            .userProfile(false)
                            .defaultLocaleIdPointer(languageIdPointer)
                            .build()
            );
            final int languageId = languageIdPointer.get(JAVA_INT, 0);
            final MemorySegment localeNamePointer = this.arena.allocate(85);
            this.nativeCaller.call(
                    LCIDToLocaleName.builder()
                            .localeId(languageId)
                            .localeNamePointer(localeNamePointer)
                            .localeNameBufferSize((int) localeNamePointer.byteSize())
                            .build()
            );
            this.debugRenderQueue.add(StringDebugRenderQueueItem.repeating("ntQueryDefaultLocale: " + localeNamePointer.getString(0, StandardCharsets.UTF_16LE)));

            final MemorySegment driverArray = arena.allocate(LPVOID, 1024).fill((byte) 0);
            final MemorySegment driverArrayNeededBytes = arena.allocate(LPDWORD);
            final boolean result = ((int) this.nativeCaller.call(
                    EnumDeviceDrivers.builder()
                            .sizedDriverArrayPointer(driverArray)
                            .bytesNeededPointer(driverArrayNeededBytes)
                            .build()
            ) > 0);
            if (result) {
                final int driverCount = (int) (driverArrayNeededBytes.get(JAVA_INT, 0) / ADDRESS.byteSize());
                System.out.println("Driver count: " + driverCount);
                for (int i = 0; i < driverCount; i++) {
                    final MemorySegment driverAddress = driverArray.getAtIndex(ADDRESS.withByteAlignment(8), i);
                    System.out.println("Index " + i + " driver address: " + Long.toUnsignedString(driverAddress.address()));
                    final MemorySegment lpBaseName = arena.allocate(JAVA_CHAR, 200);
                    final int nameCharsCopied = (int) this.nativeCaller.call(
                            GetDeviceDriverBaseNameW.builder()
                                    .driverAddress(driverAddress)
                                    .sizedBaseNameBufferPointer(lpBaseName)
                                    .build()
                    );
                    if (nameCharsCopied > 0 && nameCharsCopied <= 200) {
                        System.out.println("Driver name: " + lpBaseName.getString(0, StandardCharsets.UTF_16LE));
                    } else {
                        throw new RuntimeException("Failed to copy driver name chars at address: " + Long.toUnsignedString(driverAddress.address()));
                    }
                    final MemorySegment lpFileName = arena.allocate(JAVA_CHAR, 200);
                    final int filenameCharsCopied = (int) this.nativeCaller.call(
                            GetDeviceDriverFileNameW.builder()
                                    .driverAddress(driverAddress)
                                    .sizedFileNameBufferPointer(lpFileName)
                                    .build()
                    );
                    if (filenameCharsCopied > 0 && filenameCharsCopied <= 200) {
                        System.out.println("Driver filename: " + lpFileName.getString(0, StandardCharsets.UTF_16LE));
                    } else {
                        throw new RuntimeException("Failed to copy driver filename at address: " + Long.toUnsignedString(driverAddress.address()));
                    }
                }
            } else {
                throw new RuntimeException("Failed to read drivers.");
            }
            // END_DRIVERS

            final MemorySegment f = this.arena.allocate(500);
            final MemorySegment f2 = this.arena.allocate(SIZE_T);
            final MemorySegment currentProcess = (MemorySegment) this.nativeCaller.call(GetCurrentProcess.getInstance());
            final int mem = (int) this.nativeCaller.call(
                    ReadProcessMemory.builder()
                            .processHandle(currentProcess)
                            .baseAddress((MemorySegment) this.nativeCaller.call(GetModuleHandleW.builder().build()))
                            .buffer(f)
                            .count(500L)
                            .bytesReadPointer(f2)
                            .build()
            );
            System.out.println("MEM: " + mem);
            System.out.println("COPIED: " + f2.reinterpret(arena, this::freeMemorySegment).get(JAVA_LONG, 0));
            final MemorySegment windowProcedureStub = new WindowProcedure().upcallStub(this.linker, this.arena, this, "handleWindowProc");
            final MemorySegment moduleHandle = (MemorySegment) this.nativeCaller.call(GetModuleHandleW.builder().build());
            System.out.println("moduleHandle Address: " + moduleHandle.address());

            final MemorySegment className = arena.allocateFrom("testClassName", StandardCharsets.UTF_16LE);
            final MemorySegment backgroundBrushHandle = (MemorySegment) this.nativeCaller.call(GetStockObject.builder().id(StockObject.WHITE_BRUSH).build());
            final MemorySegment wndClassEx = Window.builder()
                    .procedurePointer(windowProcedureStub)
                    .procedureOwner(moduleHandle)
                    .className(className)
                    .backgroundBrushHandle(backgroundBrushHandle)
                    .build()
                    .createMemorySegment(arena);

            final int classId = (int) this.nativeCaller.call(RegisterClassExW.builder().windowClassPointer(wndClassEx).build());
            if (classId == 0)
                throw new RuntimeException("Failed to register class " + wndClassEx + ": " + this.nativeCaller.call(GetLastError.getInstance()));
            System.out.println("Registered classId: " + classId);

            final MemorySegment keyboardHookStub = new LowLevelKeyboardProcedure().upcallStub(this.linker, this.arena, this, "handleLowLevelKeyboardProc");
            final ManagedHook managedHook = ManagedHook.builder()
                    .id("llKeyboardHook")
                    .type(HookType.WH_KEYBOARD_LL)
                    .stub(keyboardHookStub)
                    .owner(moduleHandle)
                    .build();
            this.hookManager.register(managedHook);

            final MemorySegment windowName = arena.allocateFrom("Demo Window", StandardCharsets.UTF_16LE);
            final MemorySegment windowHandle = (MemorySegment) this.nativeCaller.call(
                    CreateWindowExW.builder()
                            .extendedStyle(ExtendedWindowStyle.DEFAULT)
                            .className(className)
                            .windowName(windowName)
                            .style(WindowStyle.WS_SYSMENU | WindowStyle.WS_CAPTION | WindowStyle.WS_BORDER | WindowStyle.WS_SIZEBOX | WindowStyle.WS_MINIMIZEBOX | WindowStyle.WS_MAXIMIZEBOX)
                            .x(500)
                            .y(500)
                            .width(300)
                            .height(300)
                            .moduleHandle(moduleHandle)
                            .build()
            );

            if (windowHandle.address() == 0)
                throw new RuntimeException("Failed to obtain HWND: " + windowHandle.address() + " (Error: " + this.nativeCaller.call(GetLastError.getInstance()) + ")");

            this.nativeCaller.call(
                    ShowWindow.builder()
                            .handle(windowHandle)
                            .presentation(WindowPresentation.SW_SHOW)
                            .build()
            );

            final InputSender inputSender = new InputSender(this);
            inputSender.simulateKeyPress(KeyCode.VK_E);

            try (final Arena confinedArena = Arena.ofConfined()) {
                int state;
                MemorySegment message;
                while ((state = (int) (this.nativeCaller.call(
                        GetMessageW.builder()
                                .messagePointer((message = confinedArena.allocate(Message.MSG.byteSize())))
                                .build()))
                ) != 0) {
                    if (state != -1) {
                        this.nativeCaller.call(TranslateMessage.ofMessagePointer(message));
                        this.nativeCaller.call(DispatchMessageW.ofMessagePointer(message));
                    } else {
                        Exceptional.of(RuntimeException.class, "Received message state -1, last error: {}", this.nativeCaller.call(GetLastError.getInstance()));
                    }
                }
            }
        } catch (final Throwable ex) {
            throw new RuntimeException(ex);
        }

    }

    public MemorySegment handleWindowProc(@NotNull final MemorySegment windowHandle, final int messageId, final long firstParameter, final long secondParameter) {

        try (final Arena confinedArena = Arena.ofConfined()) {
            return switch (messageId) {
                case MessageId.WM_DESTROY -> {
                    this.hookManager.close();
                    this.nativeCaller.call(PostQuitMessage.ofExitCode(0));
                    yield NULL;
                }
                case MessageId.WM_PAINT -> {
                    this.handlePaint(windowHandle, confinedArena);
                    yield NULL;
                }
                case MessageId.WM_KEYDOWN -> {
                    final int keyCode = (int) firstParameter;
                    if (keyCode == KeyCode.VK_X)
                        System.out.println("Pressed X");
                    yield NULL;
                }
                default -> (MemorySegment) this.nativeCaller.call(
                        DefWindowProcW.builder()
                                .windowHandle(windowHandle)
                                .message(messageId)
                                .firstParameter(firstParameter)
                                .secondParameter(secondParameter)
                                .build()
                );
            };
        } catch (final Throwable ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Bypass LLKHF_INJECTED:
     * <p>
     * lowLevelKeyboardInput.setFlags(lowLevelKeyboardInput.getFlags() & ~LowLevelKeyboardHookFlag.LLKHF_INJECTED);
     * <p>
     * return (long) callNextHookEx.invoke(NULL, nCode, wParam, lowLevelKeyboardInput.createMemorySegment(confinedArena));
     * <p>
     * Or modify the present one:
     * <p>
     * final MemorySegment memorySegment = lParam.reinterpret(LowLevelKeyboardInput.KBDLLHOOKSTRUCT.byteSize(), confinedArena, null);
     * <p>
     * memorySegment.set(JAVA_INT, 8, lowLevelKeyboardInput.getFlags() & ~LowLevelKeyboardHookFlag.LLKHF_INJECTED);
     */
    public MemorySegment handleLowLevelKeyboardProc(final int code, final long firstParameter, final long secondParameter) {

        try (final Arena confinedArena = Arena.ofConfined()) {
            final MemorySegment lParamMemorySegment = MemorySegment.ofAddress(secondParameter);
            final LowLevelKeyboardInput lowLevelKeyboardInput = new LowLevelKeyboardInput(confinedArena, lParamMemorySegment);
            if (firstParameter == MessageId.WM_KEYDOWN) {
                final int keyCode = lowLevelKeyboardInput.getVirtualKeyCode();
                System.out.println("WM_KEYDOWN: " + keyCode + " (" + (char) keyCode + ")");
                System.out.println("INJECTED: " + Flag.check(lowLevelKeyboardInput.getFlags(), LowLevelKeyboardHookFlag.LLKHF_INJECTED));
            } else if (firstParameter == MessageId.WM_KEYUP) {
                final int keyCode = lowLevelKeyboardInput.getVirtualKeyCode();
                System.out.println("WM_KEYUP: " + keyCode + " (" + (char) keyCode + ")");
                System.out.println("INJECTED: " + Flag.check(lowLevelKeyboardInput.getFlags(), LowLevelKeyboardHookFlag.LLKHF_INJECTED));
            }

            return (MemorySegment) this.nativeCaller.call(
                    CallNextHookEx.builder()
                            .code(code)
                            .firstParameter(firstParameter)
                            .secondParameter(secondParameter)
                            .build()
            );
        } catch (final Throwable ex) {
            throw new RuntimeException(ex);
        }

    }

    private long handleDebugProc(final int nCode, final long wParam, final long lParam) {

        try {
            if (nCode < 0) {
                return (long) this.nativeCaller.call(
                        CallNextHookEx.builder()
                                .code(nCode)
                                .firstParameter(wParam)
                                .secondParameter(lParam)
                                .build()
                );
            } else if (nCode == 0) {
                return 1;
            } else {
                return 1;
            }
        } catch (final Throwable ex) {
            throw new RuntimeException(ex);
        }

    }

    private void handlePaint(@NotNull final MemorySegment windowHandle, @NotNull final Arena arena) throws Throwable {

        final MemorySegment lpPaint = arena.allocate(PaintData.PAINTSTRUCT.byteSize());
        final MemorySegment deviceContextHandle = (MemorySegment) this.nativeCaller.call(BeginPaint.builder().windowHandle(windowHandle).paintDataPointer(lpPaint).build());
        this.nativeCaller.call(SetTextColor.builder().deviceContextHandle(deviceContextHandle).color(ColorUtils.compress(255, 0, 0)).build());
        this.nativeCaller.call(SetBkMode.builder().deviceContextHandle(deviceContextHandle).mode(BackgroundMode.OPAQUE).build());

        this.debugRenderQueue.apply(deviceContextHandle);

        this.nativeCaller.call(ReleaseDC.builder().windowHandle(windowHandle).deviceContextHandle(deviceContextHandle).build());
        final int endPaintStatus = (int) this.nativeCaller.call(EndPaint.builder().windowHandle(windowHandle).paintDataPointer(lpPaint).build());
        Preconditions.checkState(endPaintStatus != 0);

    }

    private MemorySegment allocateManagedMemory(@NotNull final Arena arena, final long size) throws Throwable {

        MemorySegment memorySegment = (MemorySegment) this.nativeCaller.call(AllocateMemory.builder().size(size).build());
        memorySegment = memorySegment.reinterpret(size, arena, (target) -> {
            try {
                this.nativeCaller.call(FreeMemory.builder().memoryBlock(target).build());
            } catch (final Throwable ex) {
                throw new RuntimeException(ex);
            }
        });

        return memorySegment;

    }

    private void freeMemorySegment(@NotNull final MemorySegment target) {

        try {
            Preconditions.checkNotNull(target);
            this.nativeCaller.call(FreeMemory.builder().memoryBlock(target).build());
        } catch (final Throwable ex) {
            throw new RuntimeException(ex);
        }

    }

    public ServiceProvider getServiceProvider() {

        return this.serviceProvider;

    }

    public SymbolLookupCache getSymbolLookupCache() {

        return this.symbolLookupCache;

    }

    public Arena getArena() {

        return this.arena;

    }

    public Linker getLinker() {

        return this.linker;

    }

    public NativeCaller getNativeCaller() {

        return this.nativeCaller;

    }

    public DebugRenderQueue getDebugRenderQueue() {

        return this.debugRenderQueue;

    }

    public HookManager getHookManager() {

        return this.hookManager;

    }

}
