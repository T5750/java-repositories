# zip

## Runtime Environment
- [Java 7](http://www.oracle.com/technetwork/java/javase/downloads/jdk6downloads-1902814.html)

## Short & Quick introduction
### 数据压缩概述
Java提供了`java.util.zip`包用来兼容ZIP格式的数据压缩。

表 1. `java.util.zip` 包

条目 | 类型 | 描述
------|----|------
Checksum | 接口 | 被类Adler32和CRC32实现的接口
Adler32 | 类 | 使用Alder32算法来计算Checksum数目
CheckedInputStream | 类 | 一个输入流，保存着被读取数据的Checksum
CheckedOutputStream | 类 | 一个输出流，保存着被读取数据的Checksum
CRC32 | 类 | 使用CRC32算法来计算Checksum数目
Deflater | 类 | 使用ZLIB压缩类，支持通常的压缩方式
DeflaterOutputStream | 类 | 一个输出过滤流，用来压缩Deflater格式数据
GZIPInputStream | 类 | 一个输入过滤流，读取GZIP格式压缩数据
GZIPOutputStream | 类 | 一个输出过滤流，读取GZIP格式压缩数据
Inflater | 类 | 使用ZLIB压缩类，支持通常的解压方式
InlfaterInputStream | 类 | 一个输入过滤流，用来解压Inlfater格式的压缩数据
ZipEntry | 类 | 存储ZIP条目
ZipFile | 类 | 从ZIP文件中读取ZIP条目
ZipInputStream | 类 | 一个输入过滤流，用来读取ZIP格式文件中的文件
ZipOutputStream | 类 | 一个输出过滤流，用来向ZIP格式文件口写入文件
DataFormatException | 异常类 | 抛出一个数据格式错误
ZipException | 异常类 | 抛出一个ZIP文件

### ZIP文件属性
表格 2. 类 `ZipEntry` 中一些有用的方法

方法签名 | 描述
------|------
public String getComment() | 返回条目的注释, 没有返回null
public long getCompressedSize() | 返回条目压缩后的大小, 未知返回-1
public int getMethod() | 返回条目的压缩方式,没有指定返回 -1
public String getName() | 返回条目的名称
public long getSize() | 返回未被压缩的条目的大小，未知返回-1
public long getTime() | 返回条目的修改时间, 没有指定返回-1
public void setComment(String c) | 设置条目的注释
public void setMethod(int method) | 设置条目的压缩方式
public void setSize(long size) | 设置没有压缩的条目的大小
public void setTime(long time) | 设置条目的修改时间

### 求和校验
`java.util.zip`包中另外一些比较重要的类是 `Adler32` 和 `CRC32`，它们实现了 `java.util.zip.Checksum` 接口，并估算了压缩数据的校验和（`Checksum`）。

### 压缩对象
- `Employee`
- `SaveEmployee`
- `ReadEmployee`

### 如何对JAR文件进行操作呢？
Java档案文件（JAR）格式是基于标准的ZIP文件格式，并附有可选择的文件清单列表。可以使用`java.util.jar`包。

## Links
- [利用JAVA API函数实现数据的压缩与解压缩](https://www.ibm.com/developerworks/cn/java/l-compress/index.html)
