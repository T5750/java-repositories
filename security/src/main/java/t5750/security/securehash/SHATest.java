package t5750.security.securehash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SHATest {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		String passwordToHash = "password";
		byte[] salt = getSalt();
		String securePassword = getSecurePassword(passwordToHash, salt,
				"SHA-1");
		System.out.println(securePassword);
		securePassword = getSecurePassword(passwordToHash, salt, "SHA-256");
		System.out.println(securePassword);
		securePassword = getSecurePassword(passwordToHash, salt, "SHA-384");
		System.out.println(securePassword);
		securePassword = getSecurePassword(passwordToHash, salt, "SHA-512");
		System.out.println(securePassword);
	}

	private static String getSecurePassword(String passwordToHash, byte[] salt,
			String algorithm) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(salt);
			byte[] bytes = md.digest(passwordToHash.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	/**
	 * Add salt
	 */
	private static byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}
}