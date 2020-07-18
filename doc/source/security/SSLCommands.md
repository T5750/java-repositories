# SSL Commands

## pem -> p12
```
openssl pkcs12 -export -in from.pem -inkey privatekey.key -out to.p12
```

## crt -> p12
```
openssl pkcs12 -export -in from.crt -inkey privatekey.key -out to.p12
```

## p12 -> jks
```
keytool -importkeystore -srckeystore testserver.p12 -srcstoretype PKCS12 -deststoretype JKS -destkeystore testserver.jks
```

## jks -> p12
```
keytool -importkeystore -srckeystore testserver.jks -srcstoretype JKS -deststoretype PKCS12 -destkeystore testserver.p12
```

## jks -> nginx
```
keytool -list -keystore testserver.keystore
keytool -importkeystore -srckeystore testserver.keystore -srcalias test.server.com -destkeystore nginxkeystore.p12 -deststoretype PKCS12
keytool -deststoretype PKCS12 -keystore nginxkeystore.p12 -list
openssl pkcs12 -in nginxkeystore.p12 -nokeys -clcerts -out nginxssl.crt
openssl pkcs12 -in nginxkeystore.p12 -nokeys -cacerts -out gs_intermediate_ca.crt
cat nginxssl.crt gs_intermediate_ca.crt >testnginx.crt
openssl pkcs12 -nocerts -nodes -in nginxkeystore.p12 -out testnginx.key
```

## References
- [crt转为p12证书](https://www.jianshu.com/p/59e2bb2befa9)
- [Nginx证书配置：tomcat证书jks文件转nginx证书.cet和key文件](https://blog.csdn.net/liuchuan_com/article/details/54376258)