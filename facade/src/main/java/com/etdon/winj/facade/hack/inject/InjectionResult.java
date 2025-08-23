package com.etdon.winj.facade.hack.inject;

public final class InjectionResult {

    public static final int SUCCESS = 0;
    public static final int FAIL = 1;
    public static final int FAIL_FILE_NOT_FOUND = 2;

    private InjectionResult() {

        throw new UnsupportedOperationException();

    }

}
