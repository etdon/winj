package com.etdon.winj.input;

import com.etdon.jbinder.function.NativeCaller;
import com.etdon.winj.constant.KeyCode;
import com.etdon.winj.WinJ;
import com.etdon.winj.constant.InputType;
import com.etdon.winj.constant.KeyboardEventFlag;
import com.etdon.winj.constant.VirtualKeyMapType;
import com.etdon.winj.function.kernel32.GetLastError;
import com.etdon.winj.function.user32.MapVirtualKeyExW;
import com.etdon.winj.function.user32.SendInput;
import com.etdon.winj.type.input.Input;
import com.etdon.winj.type.input.KeyboardInput;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static java.lang.foreign.ValueLayout.ADDRESS;

public class InputSender {

    private final Arena arena;
    private final NativeCaller nativeCaller;

    public InputSender(@NotNull final WinJ winJ) {

        this.arena = winJ.getArena();
        this.nativeCaller = winJ.getNativeCaller();

    }

    /**
     * Posts a new input message sending the provided key code (up and down).
     *
     * @param keyCode The key code.
     * @see KeyCode
     */
    public void simulateKeyPress(final int keyCode) {

        try {
            final int scanCode = (int) this.nativeCaller.call(
                    MapVirtualKeyExW.builder()
                            .code(keyCode)
                            .mapType(VirtualKeyMapType.MAPVK_VK_TO_VSC)
                            .build()
            );

            final MemorySegment inputArray = this.arena.allocate(Input.INPUT, 2);
            MemorySegment.copy(Input.builder()
                    .type(InputType.INPUT_KEYBOARD)
                    .data(KeyboardInput.builder()
                            .virtualKeyCode(keyCode)
                            .scanCode(scanCode)
                            .flags(0)
                            .timeStamp(0)
                            .extraInfo(this.arena.allocate(ADDRESS))
                            .build()
                    )
                    .build()
                    .createMemorySegment(this.arena), 0, inputArray, 0, Input.INPUT.byteSize());
            MemorySegment.copy(Input.builder()
                    .type(InputType.INPUT_KEYBOARD)
                    .data(KeyboardInput.builder()
                            .virtualKeyCode(keyCode)
                            .scanCode(scanCode)
                            .flags(KeyboardEventFlag.KEYEVENTF_KEYUP)
                            .timeStamp(0)
                            .extraInfo(this.arena.allocate(ADDRESS))
                            .build()
                    )
                    .build()
                    .createMemorySegment(this.arena), 0, inputArray, Input.INPUT.byteSize(), Input.INPUT.byteSize());

            final int c = (int) this.nativeCaller.call(
                    SendInput.builder()
                            .inputArray(inputArray)
                            .inputCount(2)
                            .inputByteSize((int) Input.INPUT.byteSize())
                            .build()
            );
            System.out.println("SENT: " + c);
            System.out.println("LAST ERROR: " + this.nativeCaller.call(GetLastError.getInstance()));
        } catch (final Throwable ex) {
            throw new RuntimeException(ex);
        }

    }

}
