# DSA

## Runtime Environment
- [Java 7](http://www.oracle.com/technetwork/java/javase/downloads/jdk6downloads-1902814.html)

## Short & Quick introduction

### DSA
DSA是Schnorr和ElGamal签名算法的变种，被美国NIST作为DSfS(DigitalSignature Standard)。

DSA是基于整数有限域离散对数难题的，其安全性与RSA相比差不多。DSA的一个重要特点是两个素数公开，这样，当使用别人的p和q时，即使不知道私钥，你也能确认它们是否是随机产生的，还是作了手脚。RSA却做不到。

算法分类信息：

算法 | 密钥长度 | 默认长度 | 签名长度 | 实现的方
------|------|----|----|----
SHA1withDSA | 512-65536（64的整数倍） | 1024 | 同密钥 | JDK
SHA224withDSA | 同上 | 1024 | 同密钥 | BC
SHA256withDSA | ... | 1024 | 同密钥 | BC
SHA384withDSA | ... | 1024 | 同密钥 | BC
SHA512withDSA | ... | 1024 | 同密钥 | BC

### ECDSA
ECDSA：椭圆曲线数字签名算法

特点：速度快，强度高，签名短。

算法分类信息：

算法 | 密钥长度 | 默认长度 | 签名长度 | 实现的方
------|------|----|----|----
NONEwithECDSA | 112-571 | 256 | 128 | JDK/BC
RIPEMD160withECDSA | 同上 | 256 | 160 | BC
SHA1withECDSA | ... | 256 | 160 | JDK/BC
SHA224withECDSA | ... | 256 | 224 | BC
SHA256withECDSA | ... | 256 | 256 | JDK/BC
SHA384withECDSA | ... | 256 | 384 | JDK/BC
SHA512withECDSA | ... | 256 | 512 | JDK/BC

## Links
- [Digital Signature Algorithm](http://www.java2s.com/Tutorial/Java/0490__Security/0320__Digital-Signature-Algorithm.htm)
- [java RSA/DSA/ECDSA实现数字签名](http://blog.csdn.net/caiandyong/article/details/50282889)
- [JAVA 上加密算法的实现用例](https://www.ibm.com/developerworks/cn/java/l-security/)
- [Elliptic Curve Digital Signature Algorithm](https://en.wikipedia.org/wiki/Elliptic_Curve_Digital_Signature_Algorithm)
