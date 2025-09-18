# A-terminal

A complete development suite for Systems Engineering on the Android through the Android Shell and Shell commands. Android Shell is a constellation of user-space binaries designed to manipulate the Android Platform and the Android Kernel through the Shell interface and System Calls (See the architecture of the GNU/Linux and Linux Kernel).

There are **two channels or two portals** for this project; either through **the shell interface** or **the native code manipulation** through the Kernel Userspace APIs (e.g., Sysfs, Chardev userspace interface, and IOCTL userspace interface from file-operations interface).

Depending on the requirements and goals of the user applications that will utilize this library; if the application is required to manipulate the Operating System resources mainly affecting other applications on the same user namespace then it should use the **shell interface** to control the system services (e.g., The Android Package Manager System Binary `pm`, and The Android Activity Manager System Binary `am`); if it's required to read or write hardware data or control other system resources (such as memory and disk), then the **native code manipulation** is your solution; the **shell interface** could also be accessed from the native code.

## Milestones:
- [ ] Hardware/Software problem analysis and architecture.
- [x] Manipulation via Shell Interface.
- [x] Abstraction for concurrency execution.
- [ ] Concretion for concurrency execution.
- [ ] Manipulation via File-Operations Kernel userspace interface through the Kernel VFS.
- [ ] Manipulation of CharDev devices via the CharDev/VFS interface.
- [ ] Manipulation of Block devices via the block/VFS interface.
- [ ] Manipulation of Bus protocol devices via the Bus/VFS (Sysfs) interface.
- [ ] Manipulation of the Kernel Virtual Machine Subsystem (KVM) (ADVANCED).
- [ ] Unit testing software components.
- [ ] Deployment to maven-central.
- [ ] Integration testing with an application from jMonkeyEngine.
