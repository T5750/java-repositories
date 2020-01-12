package t5750.security.aes.java2s;

import java.security.*;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AesWrapsRsa {
	public static void main(String[] args) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
		SecureRandom random = new SecureRandom();
		KeyPairGenerator fact = KeyPairGenerator.getInstance("RSA", "BC");
		fact.initialize(1024, random);
		KeyPair keyPair = fact.generateKeyPair();
		Key wrapKey = createKeyForAES(256, random);
		cipher.init(Cipher.WRAP_MODE, wrapKey);
		byte[] wrappedKey = cipher.wrap(keyPair.getPrivate());
		cipher.init(Cipher.UNWRAP_MODE, wrapKey);
		Key key = cipher.unwrap(wrappedKey, "RSA", Cipher.PRIVATE_KEY);
		System.out.println(keyPair.getPrivate().equals(key));
	}

	public static SecretKey createKeyForAES(int bitLength, SecureRandom random)
			throws NoSuchAlgorithmException, NoSuchProviderException {
		KeyGenerator generator = KeyGenerator.getInstance("AES", "BC");
		generator.init(128, random);
		return generator.generateKey();
	}
}
