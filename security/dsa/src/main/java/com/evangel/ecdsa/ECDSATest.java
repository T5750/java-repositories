package com.evangel.ecdsa;

import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

public class ECDSATest {
	private static String KEY_INFO = "ecdsa security";

	public static void main(String[] args) {
		jdkECDSA();
	}

	public static void jdkECDSA() {
		try {
			// 1.初始化密钥
			KeyPairGenerator keyPairGenerator = KeyPairGenerator
					.getInstance("EC");
			keyPairGenerator.initialize(256);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			ECPublicKey ecPublicKey = (ECPublicKey) keyPair.getPublic();
			ECPrivateKey ecPrivateKey = (ECPrivateKey) keyPair.getPrivate();
			// 2.执行签名
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
					ecPrivateKey.getEncoded());
			KeyFactory keyFactory = KeyFactory.getInstance("EC");
			PrivateKey privateKey = keyFactory
					.generatePrivate(pkcs8EncodedKeySpec);
			Signature signature = Signature.getInstance("SHA1withECDSA");
			signature.initSign(privateKey);
			signature.update(KEY_INFO.getBytes());
			byte[] res = signature.sign();
			System.out.println("签名：" + HexBin.encode(res));
			// 3.验证签名
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
					ecPublicKey.getEncoded());
			keyFactory = KeyFactory.getInstance("EC");
			PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
			signature = Signature.getInstance("SHA1withECDSA");
			signature.initVerify(publicKey);
			signature.update(KEY_INFO.getBytes());
			boolean bool = signature.verify(res);
			System.out.println("验证：" + bool);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}