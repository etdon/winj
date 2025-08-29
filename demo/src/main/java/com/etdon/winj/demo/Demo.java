package com.etdon.winj.demo;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.di.ServiceProvider;
import com.etdon.commons.util.ColorUtils;
import com.etdon.commons.util.Exceptional;
import com.etdon.jbinder.SymbolLookupCache;
import com.etdon.jbinder.function.NativeCaller;
import com.etdon.winj.common.NativeContext;
import com.etdon.winj.constant.*;
import com.etdon.winj.facade.Window;
import com.etdon.winj.facade.WindowsAPI;
import com.etdon.winj.facade.assembler.Opcodes;
import com.etdon.winj.facade.assembler.Shellcode;
import com.etdon.winj.facade.hack.execute.ShellcodeHelper;
import com.etdon.winj.facade.hack.execute.ShellcodeRunner;
import com.etdon.winj.facade.assembler.register.Register64;
import com.etdon.winj.marshal.tstring.StringMarshal;
import com.etdon.winj.marshal.tstring.StringMarshalContext;
import com.etdon.winj.marshal.tstring.XorStringMarshalStrategy;
import com.etdon.winj.function.gdi32.GetStockObject;
import com.etdon.winj.function.gdi32.SetBkMode;
import com.etdon.winj.function.gdi32.SetTextColor;
import com.etdon.winj.function.kernel32.error.GetLastError;
import com.etdon.winj.function.kernel32.libloader.GetModuleHandleW;
import com.etdon.winj.function.kernel32.locale.LCIDToLocaleName;
import com.etdon.winj.function.kernel32.process.GetCurrentProcessId;
import com.etdon.winj.function.ntdll.NtGetTickCount;
import com.etdon.winj.function.ntdll.NtQueryDefaultLocale;
import com.etdon.winj.function.psapi.EnumDeviceDrivers;
import com.etdon.winj.function.psapi.GetDeviceDriverBaseNameW;
import com.etdon.winj.function.psapi.GetDeviceDriverFileNameW;
import com.etdon.winj.function.user32.*;
import com.etdon.winj.hook.HookManager;
import com.etdon.winj.hook.ManagedHook;
import com.etdon.winj.procedure.LowLevelKeyboardProcedure;
import com.etdon.winj.procedure.WindowProcedure;
import com.etdon.winj.render.debug.queue.DebugRenderQueue;
import com.etdon.winj.render.debug.queue.StringDebugRenderQueueItem;
import com.etdon.winj.type.LowLevelKeyboardInput;
import com.etdon.winj.type.Message;
import com.etdon.winj.type.PaintData;
import com.etdon.winj.type.WindowClass;
import com.etdon.winj.util.Flag;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

import static com.etdon.winj.type.constant.NativeDataType.*;
import static java.lang.foreign.MemorySegment.NULL;
import static java.lang.foreign.ValueLayout.*;

public final class Demo {

    private static class DemoSingleton {

        private static final Demo INSTANCE = new Demo();

    }

    public static void main(@NotNull final String[] args) {

        Demo.getInstance().initialize();

    }

    private final WindowsAPI windowsAPI;
    private final ServiceProvider serviceProvider;
    private final HookManager hookManager;
    private final NativeCaller nativeCaller;
    private final Linker linker;
    private final DebugRenderQueue debugRenderQueue;

    private Demo() {

        this.windowsAPI = new WindowsAPI(Arena.ofAuto());
        this.serviceProvider = windowsAPI.getServiceProvider();
        this.hookManager = new HookManager(this.serviceProvider);
        this.serviceProvider.register(this.hookManager);
        this.nativeCaller = this.serviceProvider.get(NativeCaller.class);
        this.linker = this.serviceProvider.get(Linker.class);
        this.debugRenderQueue = this.serviceProvider.get(DebugRenderQueue.class);

    }

    private void initialize() {

        try (final Arena arena = Arena.ofConfined()) {
            final MemorySegment windowProcedureStub = new WindowProcedure().upcallStub(this.linker, arena, this, "handleWindowProc");
            final MemorySegment moduleHandle = this.nativeCaller.call(GetModuleHandleW.builder().build());
            System.out.println("moduleHandle Address: " + moduleHandle.address());

            final MemorySegment className = arena.allocateFrom("testClassName", StandardCharsets.UTF_16LE);
            final MemorySegment backgroundBrushHandle = this.nativeCaller.call(GetStockObject.builder().id(StockObject.WHITE_BRUSH).build());
            final MemorySegment wndClassEx = WindowClass.builder()
                    .procedurePointer(windowProcedureStub)
                    .procedureOwner(moduleHandle)
                    .className(className)
                    .backgroundBrushHandle(backgroundBrushHandle)
                    .build()
                    .createMemorySegment(arena);

            final int classId = this.nativeCaller.call(RegisterClassExW.builder().windowClassPointer(wndClassEx).build());
            if (classId == 0)
                throw new RuntimeException("Failed to register class " + wndClassEx + ": " + this.nativeCaller.call(GetLastError.getInstance()));
            System.out.println("Registered classId: " + classId);

            final MemorySegment keyboardHookStub = new LowLevelKeyboardProcedure().upcallStub(this.linker, arena, this, "handleLowLevelKeyboardProc");
            final ManagedHook managedHook = ManagedHook.builder()
                    .id("llKeyboardHook")
                    .type(HookType.WH_KEYBOARD_LL)
                    .stub(keyboardHookStub)
                    .owner(moduleHandle)
                    .build();
            this.hookManager.register(managedHook);

            final MemorySegment windowName = arena.allocateFrom("Demo Window", StandardCharsets.UTF_16LE);
            final MemorySegment windowHandle = this.nativeCaller.call(
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

            final NativeContext nativeContext = NativeContext.of(arena, this.nativeCaller);
            final Window window = this.windowsAPI.getForegroundWindow();
            final String text = window.getText(nativeContext);
            this.debugRenderQueue.add(StringDebugRenderQueueItem.repeating("Hello!"));
            this.printDrivers(arena);

            int state;
            final MemorySegment message = arena.allocate(Message.MSG.byteSize());
            while ((state = this.nativeCaller.call(
                    GetMessageW.builder()
                            .messagePointer(message)
                            .build())
            ) != 0) {
                if (state != -1) {
                    this.nativeCaller.call(TranslateMessage.ofMessagePointer(message));
                    this.nativeCaller.call(DispatchMessageW.ofMessagePointer(message));
                } else {
                    Exceptional.of(RuntimeException.class, "Received message state -1, last error: {}", this.nativeCaller.call(GetLastError.getInstance()));
                }
            }
        } catch (final Throwable ex) {
            throw new RuntimeException(ex);
        }

    }

    public void printDrivers(@NotNull final Arena arena) throws Throwable {

        this.debugRenderQueue.add(StringDebugRenderQueueItem.repeating("PID: " + this.nativeCaller.call(GetCurrentProcessId.getInstance()).toString()));
        this.debugRenderQueue.add(StringDebugRenderQueueItem.repeating("ntGetTickCount: " + this.nativeCaller.call(NtGetTickCount.getInstance())));
        final MemorySegment languageIdPointer = arena.allocate(JAVA_INT, 1);
        this.nativeCaller.call(
                NtQueryDefaultLocale.builder()
                        .userProfile(false)
                        .defaultLocaleIdPointer(languageIdPointer)
                        .build()
        );
        final int languageId = languageIdPointer.get(JAVA_INT, 0);
        final MemorySegment localeNamePointer = arena.allocate(85);
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
        final boolean result = (this.nativeCaller.call(
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
                final int nameCharsCopied = this.nativeCaller.call(
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
                final int filenameCharsCopied = this.nativeCaller.call(
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
                default -> this.nativeCaller.call(
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

    private void handlePaint(@NotNull final MemorySegment windowHandle, @NotNull final Arena arena) throws Throwable {

        final MemorySegment lpPaint = arena.allocate(PaintData.PAINTSTRUCT.byteSize());
        final MemorySegment deviceContextHandle = this.nativeCaller.call(BeginPaint.builder().windowHandle(windowHandle).paintDataPointer(lpPaint).build());
        this.nativeCaller.call(SetTextColor.builder().deviceContextHandle(deviceContextHandle).color(ColorUtils.compress(255, 0, 0)).build());
        this.nativeCaller.call(SetBkMode.builder().deviceContextHandle(deviceContextHandle).mode(BackgroundMode.OPAQUE).build());

        this.debugRenderQueue.apply(deviceContextHandle);

        this.nativeCaller.call(ReleaseDC.builder().windowHandle(windowHandle).deviceContextHandle(deviceContextHandle).build());
        final int endPaintStatus = this.nativeCaller.call(EndPaint.builder().windowHandle(windowHandle).paintDataPointer(lpPaint).build());
        Preconditions.checkState(endPaintStatus != 0);

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

            return this.nativeCaller.call(
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
                return this.nativeCaller.call(
                        CallNextHookEx.builder()
                                .code(nCode)
                                .firstParameter(wParam)
                                .secondParameter(lParam)
                                .build()
                ).address();
            } else if (nCode == 0) {
                return 1;
            } else {
                return 1;
            }
        } catch (final Throwable ex) {
            throw new RuntimeException(ex);
        }

    }

    private void demoShellcodeRunner() {

        try (final Arena arena = Arena.ofConfined()) {
            final ShellcodeHelper shellcodeHelper = new ShellcodeHelper(this.serviceProvider.getOrThrow(SymbolLookupCache.class));
            final byte[] exitProcess = Shellcode.builder()
                    .instructions(
                            Opcodes.Prefix.REX_W,
                            Opcodes.Group.G_83,
                            Opcodes.ModRM.builder()
                                    .mod(11)
                                    .reg(101)
                                    .rm(100)
                                    .build()
                    )
                    .value((byte) 0x20)
                    .instructions(0x48, 0xC7, 0xC1)
                    .value((byte) 0x5)
                    .padding(3)
                    .instructions(0x48, 0xB8)
                    .address(shellcodeHelper.getFunctionAddress(Library.KERNEL_32, "ExitProcess"))
                    .instructions(0xFF, 0xD0)
                    .build()
                    .export();

            final byte[] runCalc = Shellcode.builder()
                    .xor(Register64.RCX.addressor(), Register64.RCX)
                    .mul(Register64.RCX.addressor())
                    .push(Register64.RAX)
                    .mov(Register64.RAX,
                            ByteBuffer.wrap(
                                    StringMarshal.getInstance().marshal("calc.exe",
                                            StringMarshalContext.builder()
                                                    .strategy(XorStringMarshalStrategy.withKey(0xFF))
                                                    .build()
                                    )
                            ).order(ByteOrder.LITTLE_ENDIAN).getLong())
                    .not(Register64.RAX.addressor())
                    .push(Register64.RAX)
                    .mov(Register64.RCX.addressor(), Register64.RSP)
                    .inc(Register64.RDX.addressor())
                    .mov(Register64.R12, shellcodeHelper.getFunctionAddress(Library.KERNEL_32, "WinExec"))
                    .sub(Register64.RSP.addressor(), (byte) 0x20)
                    .call(Register64.R12.addressor())
                    .xor(Register64.RCX.addressor(), Register64.RCX)
                    .mov(Register64.R13, shellcodeHelper.getFunctionAddress(Library.KERNEL_32, "ExitProcess"))
                    .call(Register64.R13.addressor())
                    .build()
                    .export();

            final NativeContext nativeContext = NativeContext.of(arena, this.nativeCaller);
            final ShellcodeRunner shellcodeRunner = new ShellcodeRunner(nativeContext);
            final MemorySegment runner = shellcodeRunner.allocateRunner();
            shellcodeRunner.execute(runner, runCalc);
        } catch (final Throwable ex) {
            throw new RuntimeException(ex);
        }

    }

    public static Demo getInstance() {

        return DemoSingleton.INSTANCE;

    }


}
