package t5750.security.aes.java2s;

import java.security.*;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class TamperedMessagePlainEncryption {
	public static void main(String[] args) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		SecureRandom random = new SecureRandom();
		IvParameterSpec ivSpec = createCtrIvForAES(1, random);
		Key key = createKeyForAES(256, random);
		Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");
		String input = "input1234567";
		cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
		byte[] cipherText = cipher.doFinal(input.getBytes());
		cipherText[9] ^= '0' ^ '9';
		cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
		byte[] plainText = cipher.doFinal(cipherText);
		System.out.println("plain : " + new String(plainText));
	}

	public static SecretKey createKeyForAES(int bitLength, SecureRandom random)
			throws NoSuchAlgorithmException, NoSuchProviderException {
		KeyGenerator generator = KeyGenerator.getInstance("AES", "BC");
		generator.init(128, random);
		return generator.generateKey();
	}

	public static IvParameterSpec createCtrIvForAES(int messageNumber,
			SecureRandom random) {
		byte[] ivBytes = new byte[16];
		random.nextBytes(ivBytes);
		ivBytes[0] = 1;
		ivBytes[1] = 2;
		ivBytes[2] = 3;
		ivBytes[3] = 4;
		for (int i = 0; i != 7; i++) {
			ivBytes[8 + i] = 0;
		}
		ivBytes[15] = 1;
		return new IvParameterSpec(ivBytes);
	}
}
