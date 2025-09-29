# <img src="https://github.com/Electrostat-Lab/A-terminal/blob/master/assets/native-term-logo.png" width=80 height=80 /> A-terminal

A complete development suite for Systems Engineering on the Android through the Android Shell and Shell commands. Android Shell is a constellation of user-space binaries designed to manipulate the Android Platform and the Android Kernel through the Shell interface and System Calls (See the architecture of the GNU/Linux and Linux Kernel).

There are **two channels or two portals** for this project; either through **the shell interface** or **the native code manipulation** through the Kernel Userspace APIs (e.g., Sysfs, Chardev userspace interface, and IOCTL userspace interface from file-operations interface).

Depending on the requirements and goals of the user applications that will utilize this library; if the application is required to manipulate the Operating System resources mainly affecting other applications on the same user namespace then it should use the **shell interface** to control the system services (e.g., The Android Package Manager System Binary `pm`, and The Android Activity Manager System Binary `am`); if it's required to read or write hardware data or control other system resources (such as memory and disk), then the **native code manipulation** is your solution; the **shell interface** could also be accessed from the native code.

## Table of contents:
* **Milestones**.
* **Chapter.01: Problem Analysis and Architecture**
    * Section 1.1 Problem Analysis
    * Section 1.2 Architecture
* **Chapter.02: Detailed and Constructional Design**
    * Section 2.1: Technology stack and building architecture
    * Section 2.2: Managing native and JVM memory through C/JNI interfaces
    * Section 2.3: Mapping the SES/MB component-based diagram from Section 1.2 to an Object-oriented diagram
    * Section 2.4: Software Components and Functions   
* **Chapter.03: Testing and Software Verification**
    * Section 3.1 Unit Testing and Software Verification
    * Section 3.2 Integration Testing
    * Section 3.3 Integration with jMonkeyEngine and JmeSurfaceView

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
* Let, $P$ be the major problem of concern, the "advanced access and control over software resources and IO resources"; such that $P$ is a set that is formed by juxtaposing other sets $P_{io}$ and $P_{OS}$ for problems of IO and problems of OS resources respectively.
$$P = P_{OS} \cup P_{io}$$.
* Breaking down the sets $P_{OS}$ and $P_{io}$ will yield the smallest subproblems that will build into the architectural components.

$$P_{OS}=\\{P_{Forking\ and\ cloning\ processes}, P_{Sandboxing\ Processes}, P_{Launching\ Applications}, P_{Installing\ Applications}, P_{Uninstalling\ Applications}, P_{Starting\ Intents}, P_{Starting\ Services}, P_{Stopping\ Services}, P_{Intercepting\ Applications\ Intent\ Data}, P_{Reading\ Other\ Applications\ Data}, P_{Killing\ Applications}, P_{Allocating\ memory\ for\ processes}, P_{Deallocating\ memory\ from\ processes}, P_{Security\ Operations}, P_{Networking\ Operations}...\\}$$

$$P_{io}=\\{P_{Read\ Hardware\ Data}, P_{Write\ Hardware\ Data}, P_{Input\ Emulation}, P_{Output\ Emulation}\\}$$

* These problems represent fine concrete components that need to grouped, by mapping them to _Subsystems_ with an entity structural skeleton (Optional).
* For example, the Applications-related manipulations are mapped to "Application Resources (or `AppRes`, for short) Controller Subsystem", while the Processes-related manipulations are mapped to the "Unix Process Resources (or `ProcRes`, for short) Controller Subsystem", and the "Networking Resources (or `NetRes`, for short) Controller Subsystem".

<img src="https://github.com/Electrostat-Lab/A-terminal/blob/master/assets/problem-diagram.png" alt="Problem Analysis Figure"/>

### Section 1.2 Architecture 
Software architecture is all about synthesizing solutions for these problems; decomposing the solutions into components, and mapping these solutions to the appropriate subsystems.

* Operating System Subsystems include the following; `ProcRes` for processes resources management, `AppRes` for Application Resources management, `MemRes` for Memory Resources Management, `SecOp` for security operations, `NetRes` for Networking Resources management.
* IO Control Subsystems include the following; `R/W HW Data`, and `I/O Emulation`.
* The architecture includes the functional (e.g., ProcRes, and AppRes) and the non-functional requirements (e.g., MemRes, and SecOp) for the software based on the problem analysis.

<img src="https://github.com/Electrostat-Lab/A-terminal/blob/master/assets/architecture-diagram.png" alt="Software Architecture Figure"/>

## Chapter.02: Detailed and Constructional Design (functional req.)
The detailed software design will depend on the [Electrostatic-Sandbox SDK]() build for Android variants; the SDK provides the _Project: ElectroNetSoft_ that utilizes the GNU/Linux POSIX OS interfaces to provide the applications with appropriate **Shell Access** and **Linux Kernel VFS Access**; both are channels to the Operating System resources (i.e., processes and memory), and IO resources (e.g., PMIO-based and DMA-based devices).

### Section 2.1: Technology stack and building architecture (methodologies)
**Technology Stack:**
1) The Electrostatic-Sandbox SDK (dynamically linked against the byte code).
2) The Android SDK and the Android OS Services (found in the `system.img`).
3) The Java Platform (compiled to byte code and packaged into dalvik executables `.dex`).
4) JNI and C/C++ (compiled to machine code and linked against the byte code during runtime).
5) GNU/Linux POSIX interfaces (dynamically linked with the native binaries).
6) The Linux Kernel (found in the `boot.img`).

**Building Stack and Architecture:**
1) Gradle building tool (starts a Gradle daemon; passes commands to the daemon process; executes building routines defined with the `build.gradle` files).
2) Gradle Dependency Manager (utilizes a RESTful API to pull dependencies from central repositories; and link them with the compilation process or package them for a runtime linking process).
3) Maven Central Repository (one of the central repositories; for uploading and downloading third-party dependencies including the Electrostatic SDK, Serial4j, Jector, and Articular-ES).
4) Google Central Repository (one of the central repositories; for uploading and downloading dependencies utilized by Google Framework including the Android Framework and Flutter).
5) Android Gradle Plugin (AGP) (A constellation of Gradle APIs found on the Google Central Repo, that wrap the Android building framework in the form of sequential Gradle tasks).
  
### Section 2.2: Managing native and JVM memory through C/JNI interfaces (non-functional req.)

**Types of references in the Java Platform:**
* Reference: A reference object encapsulates a reference to some other object so that the reference itself may be examined and manipulated like any other object. Three types of reference objects are provided, each weaker than the last: soft, weak, and phantom. Each type corresponds to a different level of reachability, as defined below.
* Soft Reference: Soft reference objects, which are cleared at the discretion of the garbage collector in response to memory demand. Soft references are most often used to implement memory-sensitive caches.
* Weak Reference: Weak reference objects, which do not prevent their referents (i.e., their objects) from being made finalizable, finalized, and then reclaimed. Weak references are most often used to implement canonicalizing mappings (i.e., Reclaimable memory references).
* Phantom Reference: Phantom reference objects, which are enqueued after the collector determines that their referents may otherwise be
reclaimed. Phantom references are most often used to schedule post-mortem cleanup actions.
* Strong Reference: An object is strongly reachable if it can be reached by some thread without traversing any reference objects. A newly-created object is strongly reachable by the thread that created it.

> [!NOTE]
> **Constructional Design Tips:**
> * On the Java side, it's recommended to use only one strong reference object (i.e., the initial object from a factory pattern).
> * The rest of the references to the same object should be weak references that don't prevent the GC from reclaiming the memory of the object once all the strong references are nullified.
> * When passing references from Java to JNI glue layers or otherwise from JNI layers to Java; it's recommended to use weak references that will not trap the memory of its referents for any reason, once the original strong reference is nullified.
> * Another way to avoid the Java references memory leak is to use an interpreter pattern that utilizes primitive local variables as messaging mechanisms over the Java layers (e.g., interpreting error codes to Java runtime unchecked exceptions).

**Shell Interface:**
1) Access to the shell environment: access to the shell environment from the GNU/Linux interfaces can be managed using `unistd` Unix Standard libraries; by forking the current parent process into a new Unix child process, running in parallel with its parent process, piping the IO back to its parent process, and executing an executable binary; eventually returning to its parent process with blocking, polling or async signaling. 
2) Data Output from the Shell environment to the filesystem: data output can be retrieved by examining the output end (`filedes[1]`) of the process pipe filesystems.
3) Data Input from the filesystem to the Shell environment: data input can be retrieved examining the input end (`filedes[0]`) of the process pipe filesystems that points to the same circular buffer but with a read pointer.
4) Memory Management: implementation of a rigorous memory management system involves using a Lifecycle pattern to control when the pipes and memory are allocated/deallocated on the behalf of the application.

**VFS Kernel Userspace Interface:**


### Section 2.3: Mapping the SES/MB component-based diagram from Section 1.2 to an Object-oriented diagram
### Section 2.4: Software Components and Functions (functional req.)
Software components and functions are instantiated on the native side, and glued to the JVM side through the dynamic loading of function tables. The following is the general class hierarchy for the library: 

<img src="https://electrostat-lab.github.io/A-terminal/inherit_graph_10.svg" alt="UML Class Diagram"/>

<img src="https://electrostat-lab.github.io/A-terminal/classElectrostatic_1_1PlatformChannel__inherit__graph_org.svg" alt="UML Class Diagram"/>


> [!NOTE]
> Each entity has a `VFSChannel`, and a `ShellChannel` components. This composition enables the developer to access the functionalities either via the Kernel VFS Subsystems or the Shell interface which also routes the call to the Kernel via System interfaces or System binaries.

## Chapter.03: Testing and Software Verification

### Section 3.1 Unit Testing and Software Verification (non-functional req.)
### Section 3.2 Integration Testing (non-functional req.)
### Section 3.3 Integration with jMonkeyEngine and JmeSurfaceView (non-functional req.)

## References:
* Linux Kernel Architecture.
* GNU/Linux Systems.
* Android Platform Architecture.
* Discrete Mathematics, Formal Methods for Software Engineering.
* IEEE SWEBOK.
* UML Class diagrams.
* Z-formal Specification Language.
