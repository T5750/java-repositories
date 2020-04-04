# Hyperic SIGAR

## Short & Quick introduction
1. 下载[sigar.jar](http://sourceforge.net/projects/sigar/files/latest/download?source=files)
2. 按照主页上的说明解压包后将相应的文件copy到java路径。比如windows32位操作系统需要将lib中sigar-x86-winnt.dll文件拷贝到java SDK目录的bin内

File | Language | Description | Required
------|----|------|----
sigar.jar | Java | Java API | Yes (for Java only)
log4j.jar | Java | Java logging API | No
libsigar-x86-linux.so | C | Linux AMD/Intel 32-bit | *
libsigar-amd64-linux.so | C | Linux AMD/Intel 64-bit | *
libsigar-ppc-linux.so | C | Linux PowerPC 32-bit | *
libsigar-ppc64-linux.so | C | Linux PowerPC 64-bit | *
libsigar-ia64-linux.so | C | Linux Itanium 64-bit | *
libsigar-s390x-linux.so | C | Linux zSeries 64-bit | *
sigar-x86-winnt.dll | C | Windows AMD/Intel 32-bit | *
sigar-amd64-winnt.dll | C | Windows AMD/Intel 64-bit | *
libsigar-ppc-aix-5.so | C | AIX PowerPC 32-bit | *
libsigar-ppc64-aix-5.so | C | AIX PowerPC 64-bit | *
libsigar-pa-hpux-11.sl | C | HP-UX PA-RISC 32-bit | *
libsigar-ia64-hpux-11.sl | C | HP-UX Itanium 64-bt | *
libsigar-sparc-solaris.so | C | Solaris Sparc 32-bit | *
libsigar-sparc64-solaris.so | C | Solaris Sparc 64-bit | *
libsigar-x86-solaris.so | C | Solaris AMD/Intel 32-bit | *
libsigar-amd64-solaris.so | C | Solaris AMD/Intel 64-bit | *
libsigar-universal-macosx.dylib | C | Mac OS X PowerPC/Intel 32-bit | *
libsigar-universal64-macosx.dylib | C | Mac OS X PowerPC/Intel 64-bit | *
libsigar-x86-freebsd-5.so | C | FreeBSD 5.x AMD/Intel 32-bit | *
libsigar-x86-freebsd-6.so | C | FreeBSD 6.x AMD/Intel 64-bit | *
libsigar-amd64-freebsd-6.so | C | FreeBSD 6.x AMD/Intel 64-bit | *

## Results
- `RuntimeTest`
- `t5750.utils.sigar.cmd.*`

## References
- [sigar](https://github.com/hyperic/sigar)
- [java使用siger 获取服务器硬件信息（CPU 内存 网络 io等）](http://www.cnblogs.com/jifeng/archive/2012/05/16/2503519.html)
