# Protobuf Java

>Protocol buffers are a language-neutral, platform-neutral extensible mechanism for serializing structured data.

## Defining Your Protocol Format
- `employee.proto`
- `addressbook.proto`

## Compiling Your Protocol Buffers
```
protoc -I=$SRC_DIR --java_out=$DST_DIR $SRC_DIR/addressbook.proto
```

## Results
- `ProtobufJavaTest`
- `AddressBookTest`
- `AddPerson`
- `ListPeople`

## References
- [Protocol Buffers](https://developers.google.cn/protocol-buffers?hl=zh-cn)
- [Protocol Buffer Basics: Java](https://developers.google.cn/protocol-buffers/docs/javatutorial?hl=zh-cn)
- [How to use Google Protocol Buffers in Java to serialize structured Data](https://roytuts.com/how-to-use-google-protocol-buffers-in-java-to-serialize-structured-data/)