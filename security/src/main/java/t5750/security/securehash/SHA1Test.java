package t5750.security.securehash;

import java.util.Random;

import t5750.security.securehash.util.SHA1Util;

public class SHA1Test {
	/**
	 * 随机生成16位字符串
	 *
	 * @return
	 */
	private static String genRandomStr() {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 16; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		String loginid = "loginid";
		String secrect = "123456";
		long timestamp = System.currentTimeMillis() / 1000;
		String token = SHA1Util.encode(loginid + secrect + timestamp);
		System.out.println(token);
		String nonce = genRandomStr();
		System.out.println(nonce);
	}
}
