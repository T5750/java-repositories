# Java Keytool Keystore Commands

## Keytool介绍
Keytool 是一个Java数据证书的管理工具 ,Keytool将密钥（key）和证书（certificates）存在一个称为keystore的文件中，包含两种数据：
- 密钥实体（Key entity）-密钥（secret key） 或私钥
- 配对公钥（采用非对称加密）可信任的证书实体（trusted certificate entries）-只包含公钥

## Keytool创建和导入命令
创建keystore和密钥对

Generate a Java keystore and key pair
```
keytool -genkey -alias mydomain -keyalg RSA -keystore keystore.jks -keysize 2048
```
为存在的keystore生成证书请求文件CSR

Generate a certificate signing request (CSR) for an existing Java keystore
```
keytool -certreq -alias mydomain -keystore keystore.jks -file mydomain.csr
```
导入根证书或中级证书到keystore

Import a root or intermediate CA certificate to an existing Java keystore
```
keytool -import -trustcacerts -alias root -file mydomain.crt -keystore keystore.jks
```
导入SSL服务器证书到keystore

Import a signed primary certificate to an existing Java keystore
```
keytool -import -trustcacerts -alias mydomain -file mydomain.crt -keystore keystore.jks
```
为存在的keystore生成自签名证书

Generate a keystore and self-signed certificate
```
keytool -genkey -keyalg RSA -alias selfsigned -keystore keystore.jks -storepass password -validity 360 -keysize 2048
```

## Keytool查看命令
查看单个证书

Check a stand-alone certificate
```
keytool -printcert -v -file mydomain.crt
```
列出keystore存在的所有证书

Check which certificates are in a Java keystore
```
keytool -list -v -keystore keystore.jks
```
使用别名查看keystore特定条目

Check a particular keystore entry using an alias
```
keytool -list -v -keystore keystore.jks -alias mydomain
```

## 其他Keytool命令
删除keystore里面指定证书

Delete a certificate from a Java Keytool keystore
```
keytool -delete -alias mydomain -keystore keystore.jks
```
更改keysore密码

Change a Java keystore password
```
keytool -storepasswd -new new_storepass -keystore keystore.jks
```
导出keystore里面的指定证书

Export a certificate from a keystore
```
keytool -export -alias mydomain -file mydomain.crt -keystore keystore.jks
```
列出信任的CA证书

List Trusted CA Certs
```
keytool -list -v -keystore $JAVA_HOME/jre/lib/security/cacerts
```
导入新的CA到信任证书

Import New CA into Trusted Certs
```
keytool -import -trustcacerts -file /path/to/ca/ca.pem -alias CA_ALIAS -keystore $JAVA_HOME/jre/lib/security/cacerts
```

## Keytool常用参数
JDK中keytool常用参数说明（不同版本有差异）：
- `-genkey`: 在用户主目录中创建一个默认文件”.keystore”,还会产生一个mykey的别名，mykey中包含用户的公钥、私钥和证书（在没有指定生成位置的情况下,keystore会存在用户系统默认目录）
- `-alias`: 产生别名 每个keystore都关联这一个独一无二的alias，这个alias通常不区分大小写
- `-keystore`: 指定密钥库的名称（产生的各类信息将不在.keystore文件中）
- `-keyalg`: 指定密钥的算法 （如 RSA DSA，默认值为：DSA）
- `-validity`: 指定创建的证书有效期多少天（默认 90）
- `-keysize`: 指定密钥长度 （默认 1024）
- `-storepass`: 指定密钥库的密码（获取keystore信息所需的密码）
- `-keypass`: 指定别名条目的密码（私钥的密码）
- `-dname`: 指定证书发行者信息 其中： “CN=名字与姓氏,OU=组织单位名称,O=组织名称,L=城市或区域名 称,ST=州或省份名称,C=单位的两字母国家代码”
- `-list`: 显示密钥库中的证书信息 `keytool -list -v -keystore 指定keystore -storepass 密码`
- `-v`: 显示密钥库中的证书详细信息
- `-export`: 将别名指定的证书导出到文件
    ```
    keytool -export -alias 需要导出的别名 -keystore 指定keystore -file 指定导出的证书位置及证书名称 -storepass 密码
    ```
- `-file`: 参数指定导出到文件的文件名
- `-delete`: 删除密钥库中某条目 `keytool -delete -alias 指定需删除的别 -keystore 指定keystore – storepass 密码`
- `-printcert`: 查看导出的证书信息 `keytool -printcert -file g:\sso\michael.crt`
- `-keypasswd`: 修改密钥库中指定条目口令 `keytool -keypasswd -alias 需修改的别名 -keypass 旧密码 -new 新密码 -storepass keystore密码 -keystore sage`
- `-storepasswd`: 修改keystore口令
    ```
    keytool -storepasswd -keystore g:\sso\michael.keystore(需修改口令的keystore) -storepass pwdold(原始密码) -new pwdnew(新密码)
    ```
- `-import`: 将已签名数字证书导入密钥库 `keytool -import -alias 指定导入条目的别名 -keystore 指定keystore -file 需导入的证书`

## References
- [常用的Java Keytool Keystore命令](https://www.chinassl.net/ssltools/keytool-commands.html)
- [keytool - Key and Certificate Management Tool](https://docs.oracle.com/javase/7/docs/technotes/tools/windows/keytool.html)