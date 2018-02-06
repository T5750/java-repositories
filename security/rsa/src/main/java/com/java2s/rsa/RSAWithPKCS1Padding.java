package com.java2s.rsa;

import java.security.*;

import javax.crypto.Cipher;

public class RSAWithPKCS1Padding {
	public static void main(String[] args) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		byte[] input = "abc".getBytes();
		Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
		SecureRandom random = new SecureRandom();
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
		generator.initialize(256, random);
		KeyPair pair = generator.generateKeyPair();
		Key pubKey = pair.getPublic();
		Key privKey = pair.getPrivate();
		cipher.init(Cipher.ENCRYPT_MODE, pubKey, random);
		byte[] cipherText = cipher.doFinal(input);
		System.out.println("cipher: " + new String(cipherText));
		cipher.init(Cipher.DECRYPT_MODE, privKey);
		byte[] plainText = cipher.doFinal(cipherText);
		System.out.println("plain : " + new String(plainText));
	}
}