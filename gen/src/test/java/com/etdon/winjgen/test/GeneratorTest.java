package com.etdon.winjgen.test;

import com.etdon.winjgen.Generator;
import com.etdon.winjgen.Tokenizer;
import org.junit.jupiter.api.Test;

public class GeneratorTest {

    private static final String CREATE_WINDOW_EX_W = """
            HWND CreateWindowExW(
              [in]           DWORD     dwExStyle,
              [in, optional] LPCWSTR   lpClassName,
              [in, optional] LPCWSTR   lpWindowName,
              [in]           DWORD     dwStyle,
              [in]           int       X,
              [in]           int       Y,
              [in]           int       nWidth,
              [in]           int       nHeight,
              [in, optional] HWND      hWndParent,
              [in, optional] HMENU     hMenu,
              [in, optional] HINSTANCE hInstance,
              [in, optional] LPVOID    lpParam
            );
            """;

    @Test
    public void testFunctionClassGeneration() {

        final Tokenizer tokenizer = new Tokenizer("""
                DWORD GetDeviceDriverFileNameW(
                    [in]  LPVOID ImageBase,
                    [out] LPWSTR lpFilename,
                    [in]  DWORD  nSize
                );
                """);

        System.out.println(Generator.getInstance().generateClass(tokenizer.tokenize()));

    }

}
