package com.java2s;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CreateSecretKeySpec {
	public static void main(String[] args) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		byte[] input = "input".getBytes();
		byte[] ivBytes = "1234567812345678".getBytes();
		Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");
		KeyGenerator generator = KeyGenerator.getInstance("AES", "BC");
		generator.init(128);
		Key encryptionKey = generator.generateKey();
		System.out.println("key : " + new String(encryptionKey.getEncoded()));
		cipher.init(Cipher.ENCRYPT_MODE, encryptionKey, new IvParameterSpec(
				ivBytes));
		byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
		int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
		ctLength += cipher.doFinal(cipherText, ctLength);
		Key decryptionKey = new SecretKeySpec(encryptionKey.getEncoded(),
				encryptionKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, decryptionKey, new IvParameterSpec(
				ivBytes));
		byte[] plainText = new byte[cipher.getOutputSize(ctLength)];
		int ptLength = cipher.update(cipherText, 0, ctLength, plainText, 0);
		ptLength += cipher.doFinal(plainText, ptLength);
		System.out.println("plain : " + new String(plainText));
	}
}
