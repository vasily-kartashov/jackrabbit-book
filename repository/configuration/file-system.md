File system
====================================

Early versions on Jackrabbit were designed to abstract their persistence mechanism using a virtual file system layer 
defined in the FileSystem interface. This low-level approach didn't work that well in practice, and so most of the 
persistence abstraction is now handled in a higher level. However, certain parts of Jackrabbit still use this file 
system abstraction.

A virtual file system is configured in a ```<FileSystem/>``` bean configuration element. See the main file system 
implementations ```LocalFileSystem```, ```DatabaseFileSystem``` (including subclasses), and ```MemoryFileSystem``` for 
the available options. The recommended alternative is to use the ```LocalFileSystem``` implementation that simply maps 
abstract file system accesses to the specified directory within the native file system.

@todo describe what are the options