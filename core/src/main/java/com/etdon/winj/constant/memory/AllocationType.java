package com.etdon.winj.constant.memory;

public final class AllocationType {

    /**
     * Allocates memory charges (from the overall size of memory and the paging files on disk) for the specified
     * reserved memory pages. The function also guarantees that when the caller later initially accesses the memory,
     * the contents will be zero. Actual physical pages are not allocated unless/until the virtual addresses are
     * actually accessed.
     * <p>
     * To reserve and commit pages in one step, call VirtualAllocEx with MEM_COMMIT | MEM_RESERVE.
     * <p>
     * Attempting to commit a specific address range by specifying MEM_COMMIT without MEM_RESERVE and a non-NULL
     * lpAddress fails unless the entire range has already been reserved. The resulting error code is
     * ERROR_INVALID_ADDRESS.
     * <p>
     * An attempt to commit a page that is already committed does not cause the function to fail. This means that you
     * can commit pages without first determining the current commitment state of each page.
     * <p>
     * If lpAddress specifies an address within an enclave, flAllocationType must be MEM_COMMIT.
     */
    public static final int MEM_COMMIT = 0x00001000;

    /**
     * Reserves a range of the process's virtual address space without allocating any actual physical storage in memory
     * or in the paging file on disk.
     * <p>
     * You commit reserved pages by calling VirtualAllocEx again with MEM_COMMIT. To reserve and commit pages in one
     * step, call VirtualAllocEx with MEM_COMMIT | MEM_RESERVE.
     * <p>
     * Other memory allocation functions, such as malloc and LocalAlloc, cannot use reserved memory until it has been
     * released.
     */
    public static final int MEM_RESERVE = 0x00002000;

    /**
     * Indicates that data in the memory range specified by lpAddress and dwSize is no longer of interest. The pages
     * should not be read from or written to the paging file. However, the memory block will be used again later, so it
     * should not be decommitted. This value cannot be used with any other value.
     * <p>
     * Using this value does not guarantee that the range operated on with MEM_RESET will contain zeros. If you want
     * the range to contain zeros, decommit the memory and then recommit it.
     * <p>
     * When you use MEM_RESET, the VirtualAllocEx function ignores the value of fProtect. However, you must still set
     * fProtect to a valid protection value, such as PAGE_NOACCESS.
     * <p>
     * VirtualAllocEx returns an error if you use MEM_RESET and the range of memory is mapped to a file. A shared view
     * is only acceptable if it is mapped to a paging file.
     */
    public static final int MEM_RESET = 0x00080000;

    /**
     * MEM_RESET_UNDO should only be called on an address range to which MEM_RESET was successfully applied earlier. It
     * indicates that the data in the specified memory range specified by lpAddress and dwSize is of interest to the
     * caller and attempts to reverse the effects of MEM_RESET. If the function succeeds, that means all data in the
     * specified address range is intact. If the function fails, at least some of the data in the address range has
     * been replaced with zeroes.
     * <p>
     * This value cannot be used with any other value. If MEM_RESET_UNDO is called on an address range which was not
     * MEM_RESET earlier, the behavior is undefined. When you specify MEM_RESET, the VirtualAllocEx function ignores
     * the value of flProtect. However, you must still set flProtect to a valid protection value, such as PAGE_NOACCESS.
     * <p>
     * Windows Server 2008 R2, Windows 7, Windows Server 2008, Windows Vista, Windows Server 2003 and Windows XP: The
     * MEM_RESET_UNDO flag is not supported until Windows 8 and Windows Server 2012.
     */
    public static final int MEM_RESET_UNDO = 0x1000000;

    /**
     * Allocates memory using large page support.
     * <p>
     * The size and alignment must be a multiple of the large-page minimum. To obtain this value, use the
     * GetLargePageMinimum function.
     * <p>
     * If you specify this value, you must also specify MEM_RESERVE and MEM_COMMIT.
     */
    public static final int MEM_LARGE_PAGES = 0x20000000;

    /**
     * Reserves an address range that can be used to map Address Windowing Extensions (AWE) pages.
     * <p>
     * This value must be used with MEM_RESERVE and no other values.
     */
    public static final int MEM_PHYSICAL = 0x00400000;

    /**
     * Allocates memory at the highest possible address. This can be slower than regular allocations, especially when
     * there are many allocations.
     */
    public static final int MEM_TOP_DOWN = 0x00100000;

    private AllocationType() {

        throw new UnsupportedOperationException();

    }

}
