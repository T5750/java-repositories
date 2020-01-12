package t5750.security.rsa.java2s;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class RSAKeyPairAsTwoFiles {
	public static void main(String[] args) throws Exception {
		String password = "password";
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.genKeyPair();
		String publicKeyFilename = "public";
		byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
		FileOutputStream fos = new FileOutputStream(publicKeyFilename);
		fos.write(publicKeyBytes);
		fos.close();
		String privateKeyFilename = "privateKeyFilename";
		byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
		byte[] encryptedPrivateKeyBytes = passwordEncrypt(
				password.toCharArray(), privateKeyBytes);
		fos = new FileOutputStream(privateKeyFilename);
		fos.write(encryptedPrivateKeyBytes);
		fos.close();
	}

	private static byte[] passwordEncrypt(char[] password, byte[] plaintext)
			throws Exception {
		int MD5_ITERATIONS = 1000;
		byte[] salt = new byte[8];
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);
		PBEKeySpec keySpec = new PBEKeySpec(password);
		// SecretKeyFactory keyFactory = SecretKeyFactory
		// .getInstance("PBEWithSHAAndTwofish-CBC");
		SecretKeyFactory keyFactory = SecretKeyFactory
				.getInstance("PBEWithMD5AndDES");
		// changed PBEWithSHAAndTwofish-CBC to PBEWithMD5AndDES
		// https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#SecretKeyFactory
		SecretKey key = keyFactory.generateSecret(keySpec);
		PBEParameterSpec paramSpec = new PBEParameterSpec(salt, MD5_ITERATIONS);
		// Cipher cipher = Cipher.getInstance("PBEWithSHAAndTwofish-CBC");
		Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
		cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		byte[] ciphertext = cipher.doFinal(plaintext);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(salt);
		baos.write(ciphertext);
		return baos.toByteArray();
	}
}