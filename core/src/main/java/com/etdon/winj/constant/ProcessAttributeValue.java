package com.etdon.winj.constant;

public final class ProcessAttributeValue {

    public static final int PS_ATTRIBUTE_PARENT_PROCESS = 0x60000;
    public static final int PS_ATTRIBUTE_DEBUG_PORT = 0x60001;
    public static final int PS_ATTRIBUTE_TOKEN = 0x60002;
    public static final int PS_ATTRIBUTE_CLIENT_ID = 0x10003;
    public static final int PS_ATTRIBUTE_TEB_ADDRESS = 0x10004;
    public static final int PS_ATTRIBUTE_IMAGE_NAME = 0x20005;
    public static final int PS_ATTRIBUTE_IMAGE_INFO = 0x00006;
    public static final int PS_ATTRIBUTE_MEMORY_RESERVE = 0x20007;
    public static final int PS_ATTRIBUTE_PRIORITY_CLASS = 0x20008;
    public static final int PS_ATTRIBUTE_ERROR_MODE = 0x20009;
    public static final int PS_ATTRIBUTE_STD_HANDLE_INFO = 0x2000A;
    public static final int PS_ATTRIBUTE_HANDLE_LIST = 0x2000B;
    public static final int PS_ATTRIBUTE_GROUP_AFFINITY = 0x2000C;
    public static final int PS_ATTRIBUTE_PREFERRED_NODE = 0x2000D;
    public static final int PS_ATTRIBUTE_IDEAL_PROCESSOR = 0x2000E;
    public static final int PS_ATTRIBUTE_UMS_THREAD = 0x3000F;
    public static final int PS_ATTRIBUTE_MITIGATION_OPTIONS = 0x20010;
    public static final int PS_ATTRIBUTE_PROTECTION_LEVEL = 0x20011;
    public static final int PS_ATTRIBUTE_SECURE_PROCESS = 0x20012;
    public static final int PS_ATTRIBUTE_JOB_LIST = 0x20013;
    public static final int PS_ATTRIBUTE_CHILD_PROCESS_POLICY = 0x20014;
    public static final int PS_ATTRIBUTE_ALL_APPLICATION_PACKAGES_POLICY = 0x20015;
    public static final int PS_ATTRIBUTE_WIN32K_FILTER = 0x20016;
    public static final int PS_ATTRIBUTE_SAFE_OPEN_PROMPT_ORIGIN_CLAIM = 0x20017;
    public static final int PS_ATTRIBUTE_BNO_ISOLATION = 0x20018;
    public static final int PS_ATTRIBUTE_DESKTOP_APP_POLICY = 0x20019;
    public static final int PS_ATTRIBUTE_CHPE = 0x6001A;
    public static final int PS_ATTRIBUTE_MITIGATION_AUDIT_OPTIONS = 0x2001B;
    public static final int PS_ATTRIBUTE_MACHINE_TYPE = 0x6001C;
    public static final int PS_ATTRIBUTE_COMPONENT_FILTER = 0x2001D;
    public static final int PS_ATTRIBUTE_ENABLE_OPTIONAL_XSTATE_FEATURES = 0x3001E;

    private ProcessAttributeValue() {

        throw new UnsupportedOperationException();

    }

}
