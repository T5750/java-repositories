package t5750.security.aes.key;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;

import t5750.security.aes.Base64;

public class AESTest {
	private static final String ALGORITHM = "AES";

	/**
	 * 生成密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	private SecretKey geneKey() throws Exception {
		// 获取一个密钥生成器实例
		KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
		SecureRandom random = new SecureRandom();
		random.setSeed("123456".getBytes());// 设置加密用的种子，密钥
		keyGenerator.init(random);
		SecretKey secretKey = keyGenerator.generateKey();
		// 把上面的密钥存起来
		Path keyPath = Paths.get("D:/aes.key");
		Files.write(keyPath, secretKey.getEncoded());
		return secretKey;
	}

	/**
	 * 读取存储的密钥
	 * 
	 * @param keyPath
	 * @return
	 * @throws Exception
	 */
	private SecretKey readKey(Path keyPath) throws Exception {
		// 读取存起来的密钥
		byte[] keyBytes = Files.readAllBytes(keyPath);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM);
		return keySpec;
	}

	/**
	 * 加密测试
	 */
	@Test
	public void testEncrypt() throws Exception {
		// 1、指定算法、获取Cipher对象
		Cipher cipher = Cipher.getInstance(ALGORITHM);// 算法是AES
		// 2、生成/读取用于加解密的密钥
		SecretKey secretKey = this.geneKey();
		// 3、用指定的密钥初始化Cipher对象，指定是加密模式，还是解密模式
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		String content = "Hello AES";// 需要加密的内容
		// 4、更新需要加密的内容
		cipher.update(content.getBytes());
		// 5、进行最终的加解密操作
		byte[] result = cipher.doFinal();// 加密后的字节数组
		// 也可以把4、5步组合到一起，但是如果保留了4步，同时又是如下这样使用的话，加密的内容将是之前update传递的内容和doFinal传递的内容的和。
		// byte[] result = cipher.doFinal(content.getBytes());
		String base64Result = Base64.getEncoder().encodeToString(result);// 对加密后的字节数组进行Base64编码
		System.out.println("Result: " + base64Result);
	}

	/**
	 * 解密测试
	 */
	@Test
	public void testDecrpyt() throws Exception {
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		SecretKey secretKey = this.geneKey();
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		String content = "pK9Xw4zqTMXYraDadSGJE3x/ftrDxIg2AM/acq0uixA=";// 经过Base64加密的待解密的内容
		byte[] encodedBytes = Base64.getDecoder().decode(content.getBytes());
		byte[] result = cipher.doFinal(encodedBytes);// 对加密后的字节数组进行解密
		System.out.println("Result: " + new String(result));
	}

	@Test
	public void test() throws Exception {
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
		keyGenerator.init(128);
		SecretKey secretKey = keyGenerator.generateKey();
		// 把上面的密钥存起来
		Path keyPath = Paths.get("D:/aes.key");
		Files.write(keyPath, secretKey.getEncoded());
		// 读取存起来的密钥
		SecretKey key = this.readKey(keyPath);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		cipher.update("Hello World".getBytes());
		// 密文
		byte[] encryptBytes = cipher.doFinal();
		System.out.println(Base64.getEncoder().encodeToString(encryptBytes));
		// 用取出来的密钥进行解密
		cipher.init(Cipher.DECRYPT_MODE, key);
		// 明文
		byte[] decryptBytes = cipher.doFinal(encryptBytes);
		System.out.println(new String(decryptBytes));
	}
}
