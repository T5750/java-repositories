# AES

## Runtime Environment
- [Java 7](http://www.oracle.com/technetwork/java/javase/downloads/jdk6downloads-1902814.html)

## Short & Quick introduction

### Features
- Symmetric key symmetric block cipher
- 128-bit data, 128/192/256-bit keys
- Stronger and faster than Triple-DES
- Provide full specification and design details
- Software implementable in C and Java

### Operation of AES
![Operation of AES](http://www.wailian.work/images/2018/03/14/OperationofAES.jpg)

### Encryption Process
![Encryption Process](http://www.wailian.work/images/2018/03/14/EncryptionProcess.jpg)

1. Byte Substitution (SubBytes)
1. Shift rows
1. Mix Columns
1. Add round key

### Decryption Process
1. Add round key
1. Mix columns
1. Shift rows
1. Byte substitution

## Links
- [Java AES (Advanced Encryption Standard) Algorithm Example](https://howtodoinjava.com/security/java-aes-encryption-example/)
- [Advanced Encryption Standard](http://www.java2s.com/Tutorial/Java/0490__Security/0320__Digital-Signature-Algorithm.htm)
- [Advanced Encryption Standard](https://www.tutorialspoint.com/cryptography/advanced_encryption_standard.htm)
- [Java使用AES加解密](http://blog.csdn.net/elim168/article/details/73456866)
