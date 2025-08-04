package com.etdon.winj.constant;

public final class ProcessAttributeValue {

    public static final int PS_ATTRIBUTE_PARENT_PROCESS = 0x60000;
    public static final int PS_ATTRIBUTE_DEBUG_PORT = 0x60001;
    public static final int PS_ATTRIBUTE_TOKEN = 0x60002;
    public static final int PS_ATTRIBUTE_CLIENT_ID = 0x10003;
    public static final int PS_ATTRIBUTE_TEB_ADDRESS = 0x10004;
    public static final int PS_ATTRIBUTE_IMAGE_NAME = 0x20005;
    public static final int PS_ATTRIBUTE_IMAGE_INFO = 0x6;
    public static final int PS_ATTRIBUTE_MEMORY_RESERVE = 0x20007;
    public static final int PS_ATTRIBUTE_PRIORITY_CLASS = 0x20008;
    public static final int PS_ATTRIBUTE_ERROR_MODE = 0x20009;
    public static final int PS_ATTRIBUTE_STD_HANDLE_INFO = 0x2000A;
    public static final int PS_ATTRIBUTE_HANDLE_LIST = 0x2000B;
    public static final int PS_ATTRIBUTE_GROUP_AFFINITY = 0x2000C;
    public static final int PS_ATTRIBUTE_PREFERRED_NODE = 0x2000D;
    public static final int PS_ATTRIBUTE_IDEAL_PROCESSOR = 0x2000E;
    public static final int PS_ATTRIBUTE_MITIGATION_OPTIONS = 0x60010;
    public static final int PS_ATTRIBUTE_PROTECTION_LEVEL = 0x20011;

    private ProcessAttributeValue() {

        throw new UnsupportedOperationException();

    }

}
