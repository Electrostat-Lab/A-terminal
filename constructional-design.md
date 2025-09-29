# Constructional Design Infrastructure
> Author: pavl_g

**Preface:** The following document examines the constructional design components infrastructure; the infrastructure provides the adequate runtime over the host OS dynamic libraries and the binaries of the system services. The infrastructure components are built over Unix processes, executing binaries, inter-process communication methodologies (IPC) including signal emitters and signal handlers, and IO pipes and FIFOs.

**Infrastructure Components:**
1) Shell Interface 
	* struct unix_process (proc_id, group_id, permissions, file_descriptors, threads, data_struct subprocesses, others).
	* Create subprocess --> fork and clone the current process, provides callback

P_x --> write (file) --> switches the file to busy flagged!
P_y --> polling (file)

2) VFS Interface 
	

**Infrastructure Runtime Automata:**
