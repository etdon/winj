package com.etdon.winj.constant;

@SuppressWarnings("SpellCheckingInspection")
public final class Privilege {

    /**
     * Required to assign the primary token of a process.
     * <p>User Right: Replace a process-level token.
     */
    public static final String SE_ASSIGNPRIMARYTOKEN_NAME = "SeAssignPrimaryTokenPrivilege";
    /**
     * Required to generate audit-log entries. Give this privilege to secure servers.
     * <p>User Right: Generate security audits.
     */
    public static final String SE_AUDIT_NAME = "SeAuditPrivilege";
    /**
     * Required to perform backup operations. This privilege causes the system to grant all read access control to any
     * file, regardless of the access control list (ACL) specified for the file. Any access request other than read is
     * still evaluated with the ACL. This privilege is required by the RegSaveKey and RegSaveKeyExfunctions. The
     * following access rights are granted if this privilege is held:
     * <p>
     * READ_CONTROL
     * ACCESS_SYSTEM_SECURITY
     * FILE_GENERIC_READ
     * FILE_TRAVERSE
     * <p>
     * User Right: Back up files and directories.
     * If the file is located on a removable drive and the "Audit Removable Storage" is enabled, the SE_SECURITY_NAME
     * is required to have ACCESS_SYSTEM_SECURITY.
     */
    public static final String SE_BACKUP_NAME = "SeBackupPrivilege";
    /**
     * Required to receive notifications of changes to files or directories. This privilege also causes the system to
     * skip all traversal access checks. It is enabled by default for all users.
     * <p>User Right: Bypass traverse checking.
     */
    public static final String SE_CHANGE_NOTIFY_NAME = "SeChangeNotifyPrivilege";
    /**
     * Required to create named file mapping objects in the global namespace during Terminal Services sessions. This
     * privilege is enabled by default for administrators, services, and the local system account.
     * <p>User Right: Create global objects.
     */
    public static final String SE_CREATE_GLOBAL_NAME = "SeCreateGlobalPrivilege";
    /**
     * Required to create a paging file.
     * <p>User Right: Create a pagefile.
     */
    public static final String SE_CREATE_PAGEFILE_NAME = "SeCreatePagefilePrivilege";
    /**
     * Required to create a permanent object.
     * <p>User Right: Create permanent shared objects.
     */
    public static final String SE_CREATE_PERMANENT_NAME = "SeCreatePermanentPrivilege";
    /**
     * Required to create a symbolic link.
     * <p>User Right: Create symbolic links.
     */
    public static final String SE_CREATE_SYMBOLIC_LINK_NAME = "SeCreateSymbolicLinkPrivilege";
    /**
     * Required to create a primary token.
     * <p>User Right: Create a token object.
     * <p>You cannot add this privilege to a user account with the "Create a token object" policy. Additionally, you
     * cannot add this privilege to an owned process using Windows APIs.Windows Server 2003 and Windows XP with SP1 and
     * earlier: Windows APIs can add this privilege to an owned process.
     */
    public static final String SE_CREATE_TOKEN_NAME = "SeCreateTokenPrivilege";
    /**
     * Debug and adjust the memory of any process, ignoring the DACL for the process.
     * <p>User Right: Debug programs.
     */
    public static final String SE_DEBUG_NAME = "SeDebugPrivilege";
    /**
     * Required to obtain an impersonation token for another user in the same session.
     * <p>User Right: Impersonate other users.
     */
    public static final String SE_DELEGATE_SESSION_USER_IMPERSONATE_NAME = "SeDelegateSessionUserImpersonatePrivilege";
    /**
     * Required to mark user and computer accounts as trusted for delegation.
     * <p>User Right: Enable computer and user accounts to be trusted for delegation.
     */
    public static final String SE_ENABLE_DELEGATION_NAME = "SeEnableDelegationPrivilege";
    /**
     * Required to impersonate.
     * <p>User Right: Impersonate a client after authentication.
     */
    public static final String SE_IMPERSONATE_NAME = "SeImpersonatePrivilege";
    /**
     * Required to increase the base priority of a process.
     * <p>User Right: Increase scheduling priority.
     */
    public static final String SE_INC_BASE_PRIORITY_NAME = "SeIncreaseBasePriorityPrivilege";
    /**
     * Required to increase the quota assigned to a process.
     * <p>User Right: Adjust memory quotas for a process.
     */
    public static final String SE_INCREASE_QUOTA_NAME = "SeIncreaseQuotaPrivilege";
    /**
     * Required to allocate more memory for applications that run in the context of users.
     * <p>User Right: Increase a process working set.
     */
    public static final String SE_INC_WORKING_SET_NAME = "SeIncreaseWorkingSetPrivilege";
    /**
     * Required to load or unload a device driver.
     * <p>User Right: Load and unload device drivers.
     */
    public static final String SE_LOAD_DRIVER_NAME = "SeLoadDriverPrivilege";
    /**
     * Required to lock physical pages in memory.
     * <p>User Right: Lock pages in memory.
     */
    public static final String SE_LOCK_MEMORY_NAME = "SeLockMemoryPrivilege";
    /**
     * Required to create a computer account.
     * <p>User Right: Add workstations to domain.
     */
    public static final String SE_MACHINE_ACCOUNT_NAME = "SeMachineAccountPrivilege";
    /**
     * Required to enable volume management privileges.
     * <p>User Right: Perform volume maintenance tasks.
     */
    public static final String SE_MANAGE_VOLUME_NAME = "SeManageVolumePrivilege";
    /**
     * Required to gather profiling information for a single process.
     * <p>User Right: Profile single process.
     */
    public static final String SE_PROF_SINGLE_PROCESS_NAME = "SeProfileSingleProcessPrivilege";
    /**
     * Required to modify the mandatory integrity level of an object.
     * <p>User Right: Modify an object label.
     */
    public static final String SE_RELABEL_NAME = "SeRelabelPrivilege";
    /**
     * Required to shut down a system using a network request.
     * <p>User Right: Force shutdown from a remote system.
     */
    public static final String SE_REMOTE_SHUTDOWN_NAME = "SeRemoteShutdownPrivilege";
    /**
     * Required to perform restore operations. This privilege causes the system to grant all write access control to
     * any file, regardless of the ACL specified for the file. Any access request other than write is still evaluated
     * with the ACL. Additionally, this privilege enables you to set any valid user or group SID as the owner of a
     * file. This privilege is required by the RegLoadKey function. The following access rights are granted if this
     * privilege is held:
     * <p>
     * WRITE_DAC
     * WRITE_OWNER
     * ACCESS_SYSTEM_SECURITY
     * FILE_GENERIC_WRITE
     * FILE_ADD_FILE
     * FILE_ADD_SUBDIRECTORY
     * DELETE
     * <p>
     * User Right: Restore files and directories.
     * If the file is located on a removable drive and the "Audit Removable Storage" is enabled, the SE_SECURITY_NAME
     * is required to have ACCESS_SYSTEM_SECURITY.
     */
    public static final String SE_RESTORE_NAME = "SeRestorePrivilege";
    /**
     * Required to perform a number of security-related functions, such as controlling and viewing audit messages.
     * This privilege identifies its holder as a security operator.
     * <p>User Right: Manage auditing and security log.
     */
    public static final String SE_SECURITY_NAME = "SeSecurityPrivilege";
    /**
     * Required to shut down a local system.
     * <p>User Right: Shut down the system.
     */
    public static final String SE_SHUTDOWN_NAME = "SeShutdownPrivilege";
    /**
     * Required for a domain controller to use the Lightweight Directory Access Protocol directory synchronization
     * services. This privilege enables the holder to read all objects and properties in the directory, regardless of
     * the protection on the objects and properties. By default, it is assigned to the Administrator and LocalSystem
     * accounts on domain controllers.
     * <p>User Right: Synchronize directory service data.
     */
    public static final String SE_SYNC_AGENT_NAME = "SeSyncAgentPrivilege";
    /**
     * Required to modify the nonvolatile RAM of systems that use this type of memory to store configuration
     * information.
     * <p>User Right: Modify firmware environment values.
     */
    public static final String SE_SYSTEM_ENVIRONMENT_NAME = "SeSystemEnvironmentPrivilege";
    /**
     * Required to gather profiling information for the entire system.
     * <p>User Right: Profile system performance.
     */
    public static final String SE_SYSTEM_PROFILE_NAME = "SeSystemProfilePrivilege";
    /**
     * Required to modify the system time.
     * <p>User Right: Change the system time.
     */
    public static final String SE_SYSTEMTIME_NAME = "SeSystemtimePrivilege";
    /**
     * Required to take ownership of an object without being granted discretionary access. This privilege allows the
     * owner value to be set only to those values that the holder may legitimately assign as the owner of an object.
     * <p>User Right: Take ownership of files or other objects.
     */
    public static final String SE_TAKE_OWNERSHIP_NAME = "SeTakeOwnershipPrivilege";
    /**
     * This privilege identifies its holder as part of the trusted computer base. Some trusted protected subsystems are
     * granted this privilege.
     * <p>User Right: Act as part of the operating system.
     */
    public static final String SE_TCB_NAME = "SeTcbPrivilege";
    /**
     * Required to adjust the time zone associated with the computer's internal clock.
     * <p>User Right: Change the time zone.
     */
    public static final String SE_TIME_ZONE_NAME = "SeTimeZonePrivilege";
    /**
     * Required to access Credential Manager as a trusted caller.
     * <p>User Right: Access Credential Manager as a trusted caller.
     */
    public static final String SE_TRUSTED_CREDMAN_ACCESS_NAME = "SeTrustedCredManAccessPrivilege";
    /**
     * Required to undock a laptop.
     * <p>User Right: Remove computer from docking station.
     */
    public static final String SE_UNDOCK_NAME = "SeUndockPrivilege";
    /**
     * Required to read unsolicited input from a terminal device.
     * <p>User Right: Not applicable.
     */
    public static final String SE_UNSOLICITED_INPUT_NAME = "SeUnsolicitedInputPrivilege";

    private Privilege() {

        throw new UnsupportedOperationException();

    }

}
