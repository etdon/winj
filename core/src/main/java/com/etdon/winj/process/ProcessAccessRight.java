package com.etdon.winj.process;

public final class ProcessAccessRight {

    /**
     * All possible access rights for a process object.Windows Server 2003 and Windows XP: The size of the
     * PROCESS_ALL_ACCESS flag increased on Windows Server 2008 and Windows Vista. If an application compiled for
     * Windows Server 2008 and Windows Vista is run on Windows Server 2003 or Windows XP, the PROCESS_ALL_ACCESS flag
     * is too large and the function specifying this flag fails with ERROR_ACCESS_DENIED. To avoid this problem,
     * specify the minimum set of access rights required for the operation. If PROCESS_ALL_ACCESS must be used,
     * set _WIN32_WINNT to the minimum operating system targeted by your application (for example,
     * #define _WIN32_WINNT _WIN32_WINNT_WINXP).
     */
    public static final int PROCESS_ALL_ACCESS = (0x000F0000 | 0x00100000 | 0xFFFF);

    /**
     * Required to use this process as the parent process with PROC_THREAD_ATTRIBUTE_PARENT_PROCESS.
     */
    public static final int PROCESS_CREATE_PROCESS = 0x0080;

    /**
     * Required to create a thread in the process.
     */
    public static final int PROCESS_CREATE_THREAD = 0x0002;

    /**
     * Required to duplicate a handle using DuplicateHandle.
     */
    public static final int PROCESS_DUP_HANDLE = 0x0040;

    /**
     * Required to retrieve certain information about a process, such as its token, exit code, and priority class.
     */
    public static final int PROCESS_QUERY_INFORMATION = 0x0400;

    /**
     * Required to retrieve certain information about a process. A handle that has the PROCESS_QUERY_INFORMATION access
     * right is automatically granted PROCESS_QUERY_LIMITED_INFORMATION.
     * <p>
     * Windows Server 2003 and Windows XP: This access right is not supported.
     */
    public static final int PROCESS_QUERY_LIMITED_INFORMATION = 0x1000;

    /**
     * Required to set certain information about a process, such as its priority class.
     */
    public static final int PROCESS_SET_INFORMATION = 0x0200;

    /**
     * Required to set memory limits using SetProcessWorkingSetSize.
     */
    public static final int PROCESS_SET_QUOTA = 0x0100;

    /**
     * Required to suspend or resume a process.
     */
    public static final int PROCESS_SUSPEND_RESUME = 0x0800;

    /**
     * Required to terminate a process using TerminateProcess.
     */
    public static final int PROCESS_TERMINATE = 0x0001;

    /**
     * Required to perform an operation on the address space of a process.
     */
    public static final int PROCESS_VM_OPERATION = 0x0008;

    /**
     * Required to read memory in a process using ReadProcessMemory.
     */
    public static final int PROCESS_VM_READ = 0x0010;

    /**
     * Required to write to memory in a process using WriteProcessMemory.
     */
    public static final int PROCESS_VM_WRITE = 0x0020;

    /**
     * Required to wait for the process to terminate using the wait functions.
     */
    public static final int SYNCHRONIZE = 0x00100000;

    private ProcessAccessRight() {

        throw new UnsupportedOperationException();

    }

}
