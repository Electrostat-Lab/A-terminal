# A-terminal

A complete development suite for Systems Engineering on the Android through the Android Shell and Shell commands. Android Shell is a constellation of user-space binaries designed to manipulate the Android Platform and the Android Kernel through the Shell interface and System Calls (See the architecture of the GNU/Linux and Linux Kernel).

There are **two channels or two portals** for this project; either through **the shell interface** or **the native code manipulation** through the Kernel Userspace APIs (e.g., Sysfs, Chardev userspace interface, and IOCTL userspace interface from file-operations interface).

Depending on the requirements and goals of the user applications that will utilize this library; if the application is required to manipulate the Operating System resources mainly affecting other applications on the same user namespace then it should use the **shell interface** to control the system services (e.g., The Android Package Manager System Binary `pm`, and The Android Activity Manager System Binary `am`); if it's required to read or write hardware data or control other system resources (such as memory and disk), then the **native code manipulation** is your solution; the **shell interface** could also be accessed from the native code.

## Milestones:
- [ ] Hardware/Software problem analysis and architecture.
- [x] Manipulation via Shell Interface.
- [x] Abstraction for concurrency execution.
- [ ] Concretion for concurrency execution.
- [ ] Reading system messages and kernel logs via dmseg.
- [ ] Manipulating the SELinux Kernel Subsystem Modes.
- [ ] Manipulation via File-Operations Kernel userspace interface through the Kernel VFS.
- [ ] Manipulation of CharDev devices via the CharDev/VFS interface.
- [ ] Manipulation of Block devices via the block/VFS interface.
- [ ] Manipulation of Bus protocol devices via the Bus/VFS (Sysfs) interface.
- [ ] Manipulation of the Kernel Virtual Machine Subsystem (KVM) (ADVANCED).
- [ ] Unit testing software components.
- [ ] Deployment to maven-central.
- [ ] Integration testing with an application from jMonkeyEngine.

## Chapter.01: Problem Analysis and Architecture
- **Problem:** The main problem is "access and control over software resources and IO resources in the OEM Android devices is limited". 
- **Solution:** To overcome this problem, it's required to set the SELinux (Security Enhanced Linux) Subsystem to permissive or to disabled, and to have root superuser accessibility to access the Kernel Virtual Filesystem (VFS) to gain control over the Kernel architecture either through the user process directly or by delegating the execution pipeline to another system binary.
- **Technology Stack**: the **Shell Interface**, the **Android Linux System Binaries**, and the **Linux Kernel Userspace API**.

> [!NOTE]
> SELinux, an acronym for Security Enhanced Linux, is a [Linux Security Module](https://www.kernel.org/doc/html/v4.15/admin-guide/LSM/index.html) that provides  a mechanism Mandatory Access Control (MAC) extensions which provide a comprehensive security policy.
>
> Modes of Operation:
> * **Enforcing Mode**: Violations of the policy are denied, and the action is blocked.
> * **Permissive Mode**: Violations are logged but are not blocked.
> * **Disabled Mode**: No SELinux checks are performed.
>
> The SELinux configuration modes can be changed before compiling the kernel or overriden during boot or system runtime or alternatively with a custom boot image (i.e., custom `boot.img`).
> 
> The `Linux Security Module (LSM)` framework provides a mechanism for various security checks to be hooked by new kernel extensions. The name “module” is a bit of a misnomer since these extensions are not actually loadable kernel modules. Instead, they are selectable at build-time via `CONFIG_DEFAULT_SECURITY` and can be overridden at boot-time via the `"security=..."` kernel command line argument, in the case where multiple LSMs were built into a given kernel.



### Section 1.1 Problem Analysis


### Section 1.2 Architecture 
