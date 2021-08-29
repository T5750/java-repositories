# Protobuf Language Guide

## Scalar Value Types

.proto Type | C++ Type | Java/Kotlin Type | Python Type | Go Type | Ruby Type | C# Type | PHP Type | Dart Type
---|---|---|---|---|---|---|---|---
double | double | double | float | float64 | Float | double | float | double
float | float | float | float | float32 | Float | float | float | double
int32 | int32 | int | int | int32 | Fixnum or Bignum (as required) | int | integer | int
int64 | int64 | long | int/long | int64 | Bignum | long | integer/string | Int64
uint32 | uint32 | int | int/long | uint32 | Fixnum or Bignum (as required) | uint | integer | int
uint64 | uint64 | long | int/long | uint64 | Bignum | ulong | integer/string | Int64
sint32 | int32 | int | int | int32 | Fixnum or Bignum (as required) | int | integer | int
sint64 | int64 | long | int/long | int64 | Bignum | long | integer/string | Int64
fixed32 | uint32 | int | int/long | uint32 | Fixnum or Bignum (as required) | uint | integer | int
fixed64 | uint64 | long | int/long | uint64 | Bignum | ulong | integer/string | Int64
sfixed32 | int64 | long | int/long | int64 | Bignum | long | integer/string | Int64
bool | bool | boolean | bool | bool | TrueClass/FalseClass | bool | boolean | bool
string | string | String | str/unicode | string | String (UTF-8) | string | string | String
bytes | string | ByteString | str | []byte | String (ASCII-8BIT) | ByteString | string | List

## Default Values
- For strings, the default value is the empty string.
- For bytes, the default value is empty bytes.
- For bools, the default value is false.
- For numeric types, the default value is zero.
- For [enums](https://developers.google.cn/protocol-buffers/docs/proto3?hl=zh-cn#enum), the default value is the **first defined enum value**, which must be 0.
- For message fields, the field is not set. Its exact value is language-dependent. See the [generated code guide](https://developers.google.cn/protocol-buffers/docs/reference/overview?hl=zh-cn) for details.

## References
- [Language Guide (proto3)](https://developers.google.cn/protocol-buffers/docs/proto3?hl=zh-cn)