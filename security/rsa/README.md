# RSA

## Runtime Environment
- [Java 7](http://www.oracle.com/technetwork/java/javase/downloads/jdk6downloads-1902814.html)

## Short & Quick introduction

### Digital Signature
- 数字签名：带有密钥（公钥，私钥）的消息摘要算法
私钥用于签名，公钥用于验证。

- 数字签名的作用：验证数据的完整性，认证数据来源，抗否认。
- 常用数字签名算法：RSA,DSA,ECDSA

### RSA
RSA是目前最有影响力的公钥加密算法，它能够抵抗到目前为止已知的绝大多数密码攻击，已被ISO推荐为公钥数据加密标准。

算法分类信息：

算法 | 密钥长度 | 默认长度 | 签名长度 | 实现的方
------|------|----|----|----
MD2withRSA | 512-65536（64的整数倍） | 1024 | 同密钥 | JDK
MD5withRSA | 同上 | 1024 | 同密钥 | JDK
SHA1withRSA | ... | 1024 | 同密钥 | JDK
SHA224withRSA | ... | 2048 | 同密钥 | BC
SHA256withRSA | ... | 2048 | 同密钥 | BC
SHA384withRSA | ... | 2048 | 同密钥 | BC
SHA512withRSA | ... | 2048 | 同密钥 | BC
RIPEMD128withRSA |   | 2048 | 同密钥 | BC
RIPEMD160withRSA | 同上 | 2048 | 同密钥 | BC

## Links
- [java RSA使用](http://www.cnblogs.com/freeman-rain/archive/2012/03/29/2424423.html)
- [RSA algorithm](http://www.java2s.com/Tutorial/Java/0490__Security/0740__RSA-algorithm.htm)
- [java RSA/DSA/ECDSA实现数字签名](http://blog.csdn.net/caiandyong/article/details/50282889)
- [RSA (cryptosystem)](https://en.wikipedia.org/wiki/RSA_(cryptosystem))
