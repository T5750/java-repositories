package java2s;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class AesKeyGenerator {
	public static void main(String[] args) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		KeyGenerator generator = KeyGenerator.getInstance("AES", "BC");
		generator.init(128);
		Key keyToBeWrapped = generator.generateKey();
		System.out.println("input    : "
				+ new String(keyToBeWrapped.getEncoded()));
		Cipher cipher = Cipher.getInstance("AESWrap", "BC");
		KeyGenerator KeyGen = KeyGenerator.getInstance("AES", "BC");
		KeyGen.init(256);
		Key wrapKey = KeyGen.generateKey();
		cipher.init(Cipher.WRAP_MODE, wrapKey);
		byte[] wrappedKey = cipher.wrap(keyToBeWrapped);
		System.out.println("wrapped : " + new String(wrappedKey));
		cipher.init(Cipher.UNWRAP_MODE, wrapKey);
		Key key = cipher.unwrap(wrappedKey, "AES", Cipher.SECRET_KEY);
		System.out.println("unwrapped: " + new String(key.getEncoded()));
	}
}
