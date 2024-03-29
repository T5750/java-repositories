# HTTPS

## HTTPS 简介
超文本传输安全协议（英语：Hypertext Transfer Protocol Secure，缩写：HTTPS，常称为HTTP over TLS，HTTP over SSL或HTTP Secure）是一种网络安全传输协议。

## HTTPS 工作原理
1. 浏览器将自己支持的一套加密规则发送给网站。
1. 网站从中选出一组加密算法与HASH算法，并将自己的身份信息以证书的形式发回给浏览器。证书里面包含了网站地址，加密公钥，以及证书的颁发机构等信息（证书中的私钥只能用于服务器端进行解密，在握手的整个过程中，都用到了证书中的公钥和浏览器发送给服务器的随机密码以及对称加密算法）。
1. 浏览器获得网站证书之后浏览器要做以下工作：  
    - 验证证书的合法性（颁发证书的机构是否合法，证书中包含的网站地址是否与正在访问的地址一致等），如果证书受信任，则浏览器栏里面会显示一个小锁头，否则会给出证书不受信的提示。
    - 如果证书受信任，或者是用户接受了不受信的证书，浏览器会生成一串随机数的密码，并用证书中提供的公钥加密。
    - 使用约定好的HASH算法计算握手消息，并使用生成的随机数对消息进行加密，最后将之前生成的所有信息发送给网站。
1. 网站接收浏览器发来的数据之后要做以下的操作：
    - 使用自己的私钥将信息解密取出密码，使用密码解密浏览器发来的握手消息，并验证HASH是否与浏览器发来的一致。
    - 使用密码加密一段握手消息，发送给浏览器。
1. 浏览器解密并计算握手消息的HASH，如果与服务端发来的HASH一致，此时握手过程结束，之后所有的通信数据将由之前浏览器生成的随机密码并利用对称加密算法进行加密。

## HTTPS 通信时序图
![https](https://s0.wailian.download/2018/03/15/https.png)

## HTTPS协议和HTTP协议的区别
- https协议需要到ca申请证书，一般免费证书很少，需要交费。
- http是超文本传输协议，信息是明文传输，https 则是具有安全性的ssl加密传输协议。
- http和https使用的是完全不同的连接方式用的端口也不一样,前者是80,后者是443。
- http的连接很简单,是无状态的 。
- HTTPS协议是由SSL+HTTP协议构建的可进行加密传输、身份认证的网络协议， 要比http协议安全。

## SSL 证书
SSL证书和我们日常用的身份证类似，是一个支持HTTPS网站的身份证明，SSL证书里面包含了网站的域名，证书有效期，证书的颁发机构以及用于加密传输密码的公钥等信息

## 证书的类型
1. SSL证书，用于加密HTTP协议，也就是HTTPS。
1. 代码签名证书，用于签名二进制文件，比如Windows内核驱动，Firefox插件，Java代码签名等等。
1. 客户端证书，用于加密邮件。
1. 双因素证书，网银专业版使用的USB Key里面用的就是这种类型的证书。

## java实现HTTPS工作原理
使用java代码模拟整个握手过程

### 准备工作
1.创建java证书
```
D:\>keytool -genkey -alias evaneo -keypass evaneo -keyalg RSA -keysize 1024 -keystore https.keystore -storepass evaneo
```

2.将创建的证书保存到D盘（为了方便演示）
```
D:\>keytool -export -keystore https.keystore -alias evaneo -file https.crt -storepass evaneo
```

### 代码实现

名称 | 说明
------|------
`CertifcateUtils` | 证书操作类
`DesCoder` | Des对称加密和解密工具类
`HttpsMockBase` | https父类
`HttpsMockClient` | client类
`HttpsMockServer` | 服务器类
`SocketUtils` | socket工具类

## java项目http变更https
### 1. 创建 keystore 文件
执行 ```keytool -genkey -alias tomcat -keyalg RSA```

这样就在用户的主目录下创建了一个 `.keystore` 文件

### 2. 配置 Tomcat 以使用 keystore 文件
打开 `server.xml` 找到下面被注释的这段，添加并编辑 `keystoreFile`, `keystorePass`
```
<!--
<Connector port="8443" protocol="HTTP/1.1" SSLEnabled="true"
maxThreads="150" scheme="https" secure="true"
clientAuth="false" sslProtocol="TLS" />
-->

<Connector SSLEnabled="true" acceptCount="100" clientAuth="false"
disableUploadTimeout="true" enableLookups="false" maxThreads="25"
port="8443" keystoreFile="/Users/t5750/.keystore" keystorePass="password"
protocol="org.apache.coyote.http11.Http11NioProtocol" scheme="https"
secure="true" sslProtocol="TLS" />
```

### 3. 测试
启动 Tomcat 并访问 [https://localhost:8443](https://localhost:8443)

### 4. 配置应用使用 SSL
打开应用的 `web.xml` 文件，增加配置如下：
```
<security-constraint>
	<web-resource-collection>
		<web-resource-name>securedapp</web-resource-name>
		<url-pattern>/*</url-pattern>
	</web-resource-collection>
	<user-data-constraint>
		<transport-guarantee>CONFIDENTIAL</transport-guarantee>
	</user-data-constraint>
</security-constraint>
```

将 URL 映射设为 `/*` ，这样你的整个应用都要求是 HTTPS 访问，而 `transport-guarantee` 标签设置为 `CONFIDENTIAL` 以便使应用支持 SSL。

如果你希望关闭 SSL ，只需要将 `CONFIDENTIAL` 改为 `NONE` 即可。

## References
- [HTTPS](http://www.java2s.com/Tutorial/Java/0490__Security/0880__HTTPS.htm)
- [HTTPS 与 SSL 证书概要](http://www.runoob.com/w3cnote/https-ssl-intro.html)
- [HTTPS那些事 用java实现HTTPS工作原理](http://kingj.iteye.com/blog/2103662)
- [java项目http变更https](http://www.cnblogs.com/moon521/p/5948058.html)