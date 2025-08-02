package com.etdon.winj.constant.memory;

public final class FreeType {

    /**
     * Decommits the specified region of committed pages. After the operation, the pages are in the reserved state.
     * <p>
     * The function does not fail if you attempt to decommit an uncommitted page. This means that you can decommit a
     * range of pages without first determining the current commitment state.
     * <p>
     * The MEM_DECOMMIT value is not supported when the lpAddress parameter provides the base address for an enclave.
     * This is true for enclaves that do not support dynamic memory management (i.e. SGX1). SGX2 enclaves permit
     * MEM_DECOMMIT anywhere in the enclave.
     */
    public static final int MEM_DECOMMIT = 0x00004000;
    /**
     * Releases the specified region of pages, or placeholder (for a placeholder, the address space is released and
     * available for other allocations). After this operation, the pages are in the free state.
     * <p>
     * If you specify this value, dwSize must be 0 (zero), and lpAddress must point to the base address returned by the
     * VirtualAlloc function when the region is reserved. The function fails if either of these conditions is not met.
     * <p>
     * If any pages in the region are committed currently, the function first decommits, and then releases them.
     * <p>
     * The function does not fail if you attempt to release pages that are in different states, some reserved and some
     * committed. This means that you can release a range of pages without first determining the current commitment state.
     */
    public static final int MEM_RELEASE = 0x00008000;
    /**
     * To coalesce two adjacent placeholders, specify MEM_RELEASE | MEM_COALESCE_PLACEHOLDERS. When you coalesce
     * placeholders, lpAddress and dwSize must exactly match the overall range of the placeholders to be merged.
     */
    public static final int MEM_COALESCE_PLACEHOLDERS = 0x00000001;
    /**
     * Frees an allocation back to a placeholder (after you've replaced a placeholder with a private allocation using
     * VirtualAlloc2 or Virtual2AllocFromApp).
     * <p>
     * To split a placeholder into two placeholders, specify MEM_RELEASE | MEM_PRESERVE_PLACEHOLDER.
     */
    public static final int MEM_PRESERVE_PLACEHOLDER = 0x00000002;

    private FreeType() {

        throw new UnsupportedOperationException();

    }

}
