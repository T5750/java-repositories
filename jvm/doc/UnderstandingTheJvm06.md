## Chapter06

### Class类文件的结构
类型 | 名称 | 数量
---|----|----
u4 | magic | 1
u2 | minor_version | 1
u2 | major_version | 1
u2 | constant_pool_count | 1
cp_info | constant_pool | constant_pool_count - 1
u2 | access_flags | 1
u2 | this_class | 1
u2 | super_class | 1
u2 | interfaces_count | 1
u2 | interfaces | interfaces_count
u2 | fields_count | 1
field_info | fields | fields_count
u2 | methods_count | 1
method_info | methods | methods_count
u2 | attributes_count | 1
attribute_info | attributes | attributes_count

### 常量池
类型 | 标志 | 描述
----|---|----
CONSTANT_Utf8_info | 1 | UTF-8 编码的字符串
CONSTANT_Integer_info | 3 | 整型字面量
CONSTANT_Float_info | 4 | 浮点型字面量
CONSTANT_Long_info | 5 | 长整型字面量
CONSTANT_Double_info | 6 | 双精度浮点型字面量
CONSTANT_Class_info | 7 | 类或接口的符号引用
CONSTANT_String_info | 8 | 字符串类型字面量
CONSTANT_Fieldref_info | 9 | 字段的符号引用
CONSTANT_Methodref_info | 10 | 类中方法的符号引用
CONSTANT_InterfaceMethodref_info | 11 | 接口中方法的符号引用
CONSTANT_NameAndType_info | 12 | 字段或方法的部分符号引用
CONSTANT_MethodHandle_info | 15 | 标识方法句柄
CONSTANT_MethodType_info | 16 | 标识方法类型
CONSTANT_InvokeDynamic_info | 18 | 表示一个方法的调用点

使用javap命令输出常量表：`javap -verbose TestClass`

![Jvm-WinHex-javap-min-min](https://www.wailian.work/images/2019/04/16/Jvm-WinHex-javap-min-min.png)

常量池中14种数据类型的结构总表

![Jvm-CONSTANT-Structure-min-min](https://www.wailian.work/images/2019/04/16/Jvm-CONSTANT-Structure-min-min.png)

### 访问标志
标志名称 | 标志值 | 含义
----|---|----
ACC_PUBLIC | 0x0001 | 是否为Public类型
ACC_FINAL | 0x0010 | 是否被声明为final，只有类可以设置
ACC_SUPER | 0x0020 | 是否允许使用invokespecial字节码指令的新语义．
ACC_INTERFACE | 0x0200 | 标志这是一个接口
ACC_ABSTRACT | 0x0400 | 是否为abstract类型，对于接口或者抽象类来说，次标志值为真，其他类型为假
ACC_SYNTHETIC | 0x1000 | 标志这个类并非由用户代码产生
ACC_ANNOTATION | 0x2000 | 标志这是一个注解
ACC_ENUM | 0x4000 | 标志这是一个枚举

### 字段表集合
字段表结构

类型 | 名称 | 数量
----|---|----
u2 | access_flags | 1
u2 | name_index | 1
u2 | descriptor_index | 1
u2 | attributes_count | 1
attribute_info | attributes | attributes_count

字段访问标志

标志名称 | 标志值 | 含义
----|---|----
ACC_PUBLIC | 0x0001 | 字段是否为public
ACC_PRIVATE | 0x0002 | 字段是否为private
ACC_PROTECTED | 0x0004 | 字段是否为protected
ACC_STATIC | 0x0008 | 字段是否为static
ACC_FINAL | 0x0010 | 字段是否为final
ACC_VOLATILE | 0x0040 | 字段是否为volatile
ACC_TRANSTENT | 0x0080 | 字段是否为transient
ACC_SYNCHETIC | 0x1000 | 字段是否为由编译器自动产生
ACC_ENUM | 0x4000 | 字段是否为enum

描述符标识字符含义

标志符 | 含义
---|----
B | 基本数据类型byte
C | 基本数据类型char
D | 基本数据类型double
F | 基本数据类型float
I | 基本数据类型int
J | 基本数据类型long
S | 基本数据类型short
Z | 基本数据类型boolean
V | 基本数据类型void
L | 对象类型

### 方法表集合
方法表结构

类型 | 名称 | 数量
----|---|----
u2 | access_flags | 1
u2 | name_index | 1
u2 | descriptor_index | 1
u2 | attributes_count | 1
attribute_info | attributes | attributes_count

方法访问标志

标志名称 | 标志值 | 含义
----|---|----
ACC_PUBLIC | 0x0001 | 方法是否为public
ACC_PRIVATE | 0x0002 | 方法是否为private
ACC_PROTECTED | 0x0004 | 方法是否为protected
ACC_STATIC | 0x0008 | 方法是否为static
ACC_FINAL | 0x0010 | 方法是否为final
ACC_SYHCHRONRIZED | 0x0020 | 方法是否为synchronized
ACC_BRIDGE | 0x0040 | 方法是否是有编译器产生的方法
ACC_VARARGS | 0x0080 | 方法是否接受参数
ACC_NATIVE | 0x0100 | 方法是否为native
ACC_ABSTRACT | 0x0400 | 方法是否为abstract
ACC_STRICTFP | 0x0800 | 方法是否为strictfp
ACC_SYNTHETIC | 0x1000 | 方法是否是有编译器自动产生的

### 属性表集合
虚拟机规范预定义的属性

属性名称 | 使用位置 | 含义
----|---|----
Code | 方法表 | Java代码编译成的字节码指令
ConstantValue | 字段表 | final关键字定义的常量池
Deprecated | 类，方法，字段表 | 被声明为deprecated的方法和字段
Exceptions | 方法表  | 方法抛出的异常 
EnclosingMethod | 类文件  | 仅当一个类为局部类或者匿名类是才能拥有这个属性，这个属性用于标识这个类所在的外围方法 
InnerClass | 类文件  | 内部类列表 
LineNumberTable | Code属性  | Java源码的行号与字节码指令的对应关系 
LocalVariableTable | Code属性  | 方法的局部便狼描述 
StackMapTable | Code属性  | JDK1.6中新增的属性，供新的类型检查检验器检查和处理目标方法的局部变量和操作数有所需要的类是否匹配 
Signature | 类，方法表，字段表  |  用于支持泛型情况下的方法签名
SourceFile | 类文件  | 记录源文件名称 
SourceDebugExtension | 类文件  | 用于存储额外的调试信息 
Synthetic | 类，方法表，字段表  | 标志方法或字段为编译器自动生成的 
LocalVariableTypeTable | 类  | 使用特征签名代替描述符，是为了引入泛型语法之后能描述泛型参数化类型而添加 
RuntimeVisibleAnnotations | 类，方法表，字段表  | 为动态注解提供支持 
RuntimeInvisibleAnnotations | 表，方法表，字段表  | 用于指明哪些注解是运行时不可见的 
RuntimeVisibleParameterAnnotation | 方法表  | 作用与RuntimeVisibleAnnotations属性类似，只不过作用对象为方法
RuntimeInvisibleParameterAnnotation  |  方法表 |  作用与RuntimeInvisibleAnnotations属性类似，作用对象哪个为方法参数
AnnotationDefault |  方法表 | 用于记录注解类元素的默认值 
BootstrapMethods | 类文件  | 用于保存invokeddynamic指令引用的引导方式限定符

属性表结构

类型 | 名称 | 数量
----|---|----
u2 | attribute_name_index | 1
u2 | attribute_length | 1
u1 | info | attribute_length

Code属性表结构

类型 | 名称 | 数量
----|---|----
u2 | attribute_name_index | 1
u4 | attribute_length | 1
u2 | max_stack | 1
u2 | max_locals | 1
u4 | code_length | 1
u1 | code | code_length
u2 | exception_table_length | 1
exception_info | exception_table | exception_length
u2 | attributes_count | 1
attribute_info | attributes | attributes_count

Exceptions属性表结构

类型 | 名称 | 数量
----|---|----
u2 | attribute_name_index | 1
u2 | attribute_length | 1
u2 | attribute_of_exception | 1
u2 | exception_index_table | number_of_exceptions

LineNumberTable属性结构

类型 | 名称 | 数量
----|---|----
u2 | attribute_name_index | 1
u4 | attribute_length | 1
u2 | line_number_table_length | 1
line_number_info | line_number_table | line_number_table_length