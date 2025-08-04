package com.etdon.winj.constant;

public final class ProcessCreateFlag {

    public static final int PROCESS_CREATE_FLAGS_NONE = 0x00000000;
    public static final int PROCESS_CREATE_FLAGS_BREAKAWAY = 0x00000001;
    public static final int PROCESS_CREATE_FLAGS_NO_DEBUG_INHERIT = 0x00000002;
    public static final int PROCESS_CREATE_FLAGS_INHERIT_HANDLES = 0x00000004;
    public static final int PROCESS_CREATE_FLAGS_OVERRIDE_ADDRESS_SPACE = 0x00000008;
    public static final int PROCESS_CREATE_FLAGS_LARGE_PAGES = 0x00000010;
    public static final int PROCESS_CREATE_FLAGS_LARGE_PAGE_SYSTEM_DLL = 0x00000020;
    public static final int PROCESS_CREATE_FLAGS_PROTECTED_PROCESS = 0x00000040;
    public static final int PROCESS_CREATE_FLAGS_CREATE_SESSION = 0x00000080;
    public static final int PROCESS_CREATE_FLAGS_INHERIT_FROM_PARENT = 0x00000100;
    public static final int PROCESS_CREATE_FLAGS_CREATE_SUSPENDED = 0x00000200;
    public static final int PROCESS_CREATE_FLAGS_FORCE_BREAKAWAY = 0x00000400;
    public static final int PROCESS_CREATE_FLAGS_MINIMAL_PROCESS = 0x00000800;
    public static final int PROCESS_CREATE_FLAGS_RELEASE_SECTION = 0x00001000;
    public static final int PROCESS_CREATE_FLAGS_CLONE_MINIMAL = 0x00002000;
    public static final int PROCESS_CREATE_FLAGS_CLONE_MINIMAL_REDUCED_COMMIT = 0x00004000;
    public static final int PROCESS_CREATE_FLAGS_AUXILIARY_PROCESS = 0x00008000;
    public static final int PROCESS_CREATE_FLAGS_CREATE_STORE = 0x00020000;
    public static final int PROCESS_CREATE_FLAGS_USE_PROTECTED_ENVIRONMENT = 0x00040000;
    public static final int PROCESS_CREATE_FLAGS_IMAGE_EXPANSION_MITIGATION_DISABLE = 0x00080000;
    public static final int PROCESS_CREATE_FLAGS_PARTITION_CREATE_SLAB_IDENTITY = 0x00400000;

    private ProcessCreateFlag() {

        throw new UnsupportedOperationException();

    }

}
