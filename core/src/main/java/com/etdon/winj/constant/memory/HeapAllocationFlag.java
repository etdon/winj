package com.etdon.winj.constant.memory;

public final class HeapAllocationFlag {

    /**
     * The system will raise an exception to indicate a function failure, such as an out-of-memory condition, instead
     * of returning NULL.
     * <p>
     * To ensure that exceptions are generated for all calls to this function, specify HEAP_GENERATE_EXCEPTIONS in the
     * call to HeapCreate. In this case, it is not necessary to additionally specify HEAP_GENERATE_EXCEPTIONS in this
     * function call.
     */
    public static final int HEAP_GENERATE_EXCEPTIONS = 0x00000004;
    /**
     * Serialized access will not be used for this allocation.
     * <p>
     * For more information, see Remarks.
     * <p>
     * To ensure that serialized access is disabled for all calls to this function, specify HEAP_NO_SERIALIZE in the
     * call to HeapCreate. In this case, it is not necessary to additionally specify HEAP_NO_SERIALIZE in this function
     * call.
     * <p>
     * This value should not be specified when accessing the process's default heap. The system may create additional
     * threads within the application's process, such as a CTRL+C handler, that simultaneously access the process's
     * default heap.
     */
    public static final int HEAP_NO_SERIALIZE = 0x00000001;
    /**
     * The allocated memory will be initialized to zero. Otherwise, the memory is not initialized to zero.
     */
    public static final int HEAP_ZERO_MEMORY = 0x00000008;

    private HeapAllocationFlag() {

        throw new UnsupportedOperationException();

    }

}
