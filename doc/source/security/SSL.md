# SSL/TLS

## SSL/TLS 协议的介绍
SSL/TLS 协议（RFC2246 RFC4346）处于 TCP/IP 协议与各种应用层协议之间，为数据通讯提供安全支持。

从协议内部的功能层面上来看，SSL/TLS 协议可分为两层：

1. SSL/TLS 记录协议（SSL/TLS Record Protocol），它建立在可靠的传输层协议（如 TCP）之上，为上层协议提供数据封装、压缩、加密等基本功能。

2. SSL/TLS 握手协议（SSL/TLS Handshake Protocol），它建立在 SSL/TLS 记录协议之上，用于在实际的数据传输开始前，通讯双方进行身份认证、协商加密算法、交换加密密钥等初始化协商功能。

从协议使用方式来看，又可以分成两种类型：

1. SSL/TLS 单向认证，就是用户到服务器之间只存在单方面的认证，即客户端会认证服务器端身份，而服务器端不会去对客户端身份进行验证。首先，客户端发起握手请求，服务器收到握手请求后，会选择适合双方的协议版本和加密方式。然后，再将协商的结果和服务器端的公钥一起发送给客户端。客户端利用服务器端的公钥，对要发送的数据进行加密，并发送给服务器端。服务器端收到后，会用本地私钥对收到的客户端加密数据进行解密。然后，通讯双方都会使用这些数据来产生双方之间通讯的加密密钥。接下来，双方就可以开始安全通讯过程了。

2. SSL/TLS 双向认证，就是双方都会互相认证，也就是两者之间将会交换证书。基本的过程和单向认证完全一样，只是在协商阶段多了几个步骤。在服务器端将协商的结果和服务器端的公钥一起发送给客户端后，会请求客户端的证书，客户端则会将证书发送给服务器端。然后，在客户端给服务器端发送加密数据后，客户端会将私钥生成的数字签名发送给服务器端。而服务器端则会用客户端证书中的公钥来验证数字签名的合法性。建立握手之后过程则和单向通讯完全保持一致。

## SSL/TLS 协议建立通讯的基本流程
![SSL/TLS 基本流程图](https://www.ibm.com/developerworks/cn/java/j-lo-ssltls/image001.png)

**步骤 1.** ClientHello – 客户端发送所支持的 SSL/TLS 最高协议版本号和所支持的加密算法集合及压缩方法集合等信息给服务器端。

**步骤 2.** ServerHello – 服务器端收到客户端信息后，选定双方都能够支持的 SSL/TLS 协议版本和加密方法及压缩方法，返回给客户端。

**（可选）步骤 3.** SendCertificate – 服务器端发送服务端证书给客户端。

**（可选）步骤 4.** RequestCertificate – 如果选择双向验证，服务器端向客户端请求客户端证书。

**步骤 5.** ServerHelloDone – 服务器端通知客户端初始协商结束。

**（可选）步骤 6.** ResponseCertificate – 如果选择双向验证，客户端向服务器端发送客户端证书。

**步骤 7.** ClientKeyExchange – 客户端使用服务器端的公钥，对客户端公钥和密钥种子进行加密，再发送给服务器端。

**（可选）步骤 8.** CertificateVerify – 如果选择双向验证，客户端用本地私钥生成数字签名，并发送给服务器端，让其通过收到的客户端公钥进行身份验证。

**步骤 9.** CreateSecretKey – 通讯双方基于密钥种子等信息生成通讯密钥。

**步骤 10.** ChangeCipherSpec – 客户端通知服务器端已将通讯方式切换到加密模式。

**步骤 11.** Finished – 客户端做好加密通讯的准备。

**步骤 12.** ChangeCipherSpec – 服务器端通知客户端已将通讯方式切换到加密模式。

**步骤 13.** Finished – 服务器做好加密通讯的准备。

**步骤 14.** Encrypted/DecryptedData – 双方使用客户端密钥，通过对称加密算法对通讯内容进行加密。

**步骤 15.** ClosedConnection – 通讯结束后，任何一方发出断开 SSL 连接的消息。

## SSL/TLS 协议概念

**Key：** Key 是一个比特（bit）字符串，用来加密解密数据的，就像是一把开锁的钥匙。

**对称算法（symmetric cryptography）：** 就是需要双方使用一样的 key 来加密解密消息算法，常用密钥算法有 Data Encryption Standard（DES）、triple-strength DES（3DES）、Rivest Cipher 2 （RC2）和 Rivest Cipher 4（RC4）。因为对称算法效率相对较高，因此 SSL 会话中的敏感数据都用通过密钥算法加密。

**非对称算法（asymmetric cryptography）：** 就是 key 的组成是公钥私钥对 （key-pair），公钥传递给对方私钥自己保留。公钥私钥算法是互逆的，一个用来加密，另一个可以解密。常用的算法有 Rivest Shamir Adleman（RSA）、Diffie-Hellman（DH）。非对称算法计算量大比较慢，因此仅适用于少量数据加密，如对密钥加密，而不适合大量数据的通讯加密。

**公钥证书（public key certificate）：** 公钥证书类似数字护照，由受信机构颁发。受信组织的公钥证书就是 certificate authority（CA）。多证书可以连接成证书串，第一个是发送人，下一个是给其颁发证书实体，往上到根证书是世界范围受信组织，包括 VeriSign, Entrust, 和 GTE CyberTrust。公钥证书让非对称算法的公钥传递更安全，可以避免身份伪造，比如 C 创建了公钥私钥，对并冒充 A 将公钥传递给 B，这样 C 与 B 之间进行的通讯会让 B 误认是 A 与 B 之间通讯。

**加密哈希功能（Cryptographic Hash Functions）：** 加密哈希功能与 checksum 功能相似。不同之处在于，checksum 用来侦测意外的数据变化而前者用来侦测故意的数据篡改。数据被哈希后产生一小串比特字符串，微小的数据改变将导致哈希串的变化。发送加密数据时，SSL 会使用加密哈希功能来确保数据一致性，用来阻止第三方破坏通讯数据完整性。SSL 常用的哈希算法有 Message Digest 5（MD5）和 Secure Hash Algorithm（SHA）。

**消息认证码（Message Authentication Code）：** 消息认证码与加密哈希功能相似，除了它需要基于密钥。密钥信息与加密哈希功能产生的数据结合就是哈希消息认证码（HMAC）。如果 A 要确保给 B 发的消息不被 C 篡改，他要按如下步骤做 --A 首先要计算出一个 HMAC 值，将其添加到原始消息后面。用 A 与 B 之间通讯的密钥加密消息体，然后发送给 B。B 收到消息后用密钥解密，然后重新计算出一个 HMAC，来判断消息是否在传输中被篡改。SSL 用 HMAC 来保证数据传输的安全。

**数字签名（Digital Signature）：** 一个消息的加密哈希被创建后，哈希值用发送者的私钥加密，加密的结果就是叫做数字签名。

## JSSE（Java Secure Socket Extension）使用介绍
在 Java SDK 中有一个叫 JSSE（javax.net.ssl）包，这个包中提供了一些类来建立 SSL/TLS 连接。通过这些类，开发者就可以忽略复杂的协议建立流程，较为简单地在网络上建成安全的通讯通道。JSSE 包中主要包括以下一些部分：
- 安全套接字（secure socket）和安全服务器端套接字
- 非阻塞式 SSL/TLS 数据处理引擎（SSLEngine）
- 套接字创建工厂 , 用来产生 SSL 套接字和服务器端套接字
- 套接字上下文 , 用来保存用于创建和数据引擎处理过程中的信息
- 符合 X.509 规范密码匙和安全管理接口

## 安全钥匙与证书的管理工具 Keytool
1. 进入本地的 java 安装位置的 bin 目录中 cd /java/bin
2. 创建一个客户端 keystore 文件
    - `keytool -genkey -alias sslclient -keystore sslclientkeys`
3. 将客户端 keystore 文件导出成证书格式
    - `keytool -export -alias sslclient -keystore sslclientkeys -file sslclient.cer`
4. 创建一个服务器端 keystore 文件
    - `keytool -genkey -alias sslserver -keystore sslserverkeys`
5. 将服务器端 keystore 文件导出成证书格式
    - `keytool -export -alias sslserver -keystore sslserverkeys -file sslserver.cer`
6. 将客户端证书导入到服务器端受信任的 keystore 中
    - `keytool -import -alias sslclient -keystore sslservertrust -file sslclient.cer`
7. 将服务器端证书导入到客户端受信任的 keystore 中
    - `keytool -import -alias sslserver -keystore sslclienttrust -file sslserver.cer`

以上所有步骤都完成后，还可以通过命令来查看 keystore 文件基本信息

`keytool -list -keystore sslclienttrust`

## References
- [Java SSL/TLS 安全通讯协议介绍](https://www.ibm.com/developerworks/cn/java/j-lo-ssltls/)
- [SSL/TLS协议运行机制的概述](http://www.ruanyifeng.com/blog/2014/02/ssl_tls.html)
- [图解SSL/TLS协议](http://www.ruanyifeng.com/blog/2014/09/illustration-ssl.html)