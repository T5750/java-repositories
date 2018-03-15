package com.java2s;

import java.security.*;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class TamperedMessageWithHMac {
	public static void main(String[] args) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		SecureRandom random = new SecureRandom();
		IvParameterSpec ivSpec = createCtrIvForAES();
		Key key = createKeyForAES(256, random);
		Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");
		String input = "12345678";
		Mac hMac = Mac.getInstance("HmacSHA1", "BC");
		Key hMacKey = new SecretKeySpec(key.getEncoded(), "HmacSHA1");
		System.out.println("input : " + input);
		cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
		byte[] cipherText = new byte[cipher.getOutputSize(input.length()
				+ hMac.getMacLength())];
		int ctLength = cipher.update(input.getBytes(), 0, input.length(),
				cipherText, 0);
		hMac.init(hMacKey);
		hMac.update(input.getBytes());
		ctLength += cipher.doFinal(hMac.doFinal(), 0, hMac.getMacLength(),
				cipherText, ctLength);
		cipherText[9] ^= '0' ^ '9';
		cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
		byte[] plainText = cipher.doFinal(cipherText, 0, ctLength);
		int messageLength = plainText.length - hMac.getMacLength();
		hMac.init(hMacKey);
		hMac.update(plainText, 0, messageLength);
		byte[] messageHash = new byte[hMac.getMacLength()];
		System.arraycopy(plainText, messageLength, messageHash, 0,
				messageHash.length);
		System.out.println("plain : " + new String(plainText) + " verified: "
				+ MessageDigest.isEqual(hMac.doFinal(), messageHash));
	}

	public static SecretKey createKeyForAES(int bitLength, SecureRandom random)
			throws NoSuchAlgorithmException, NoSuchProviderException {
		KeyGenerator generator = KeyGenerator.getInstance("AES", "BC");
		generator.init(128, random);
		return generator.generateKey();
	}

	public static IvParameterSpec createCtrIvForAES() {
		return new IvParameterSpec("1234567812345678".getBytes());
	}
}
