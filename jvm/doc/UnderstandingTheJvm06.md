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

`javap -verbose TestClass`

![Jvm-WinHex-javap-min-min](https://www.wailian.work/images/2019/04/16/Jvm-WinHex-javap-min-min.png)

常量池中14种数据类型的结构总表

![Jvm-CONSTANT-Structure-min-min](https://www.wailian.work/images/2019/04/16/Jvm-CONSTANT-Structure-min-min.png)