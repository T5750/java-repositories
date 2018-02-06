package com.spell.rsa;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.*;

/**
 * 产生公钥和私钥对，并且保存在文件中，公钥 pk.dat，私钥 sk.dat
 *
 * @author Administrator
 *
 */
public class KeyGen {
	/**
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// 加密的种子信息
		String keyInfo = "ASDFSDFNUGD__TYTY";
		KeyGen kg = new KeyGen();
		kg.genKeys(keyInfo);
	}

	/**
	 * 根据keyInfo产生公钥和私钥，并且保存到pk.dat和sk.dat文件中
	 *
	 * @param keyInfo
	 * @throws Exception
	 */
	public void genKeys(String keyInfo) throws Exception {
		KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
		SecureRandom random = new SecureRandom();
		random.setSeed(keyInfo.getBytes());
		// 初始加密，长度为512，必须是大于512才可以的
		keygen.initialize(512, random);
		// 取得密钥对
		KeyPair kp = keygen.generateKeyPair();
		// 取得公钥
		PublicKey publicKey = kp.getPublic();
		System.out.println(publicKey);
		saveFile(publicKey, "pk.dat");
		// 取得私钥
		PrivateKey privateKey = kp.getPrivate();
		saveFile(privateKey, "sk.dat");
	}

	/**
	 * 保存对象到文件
	 *
	 * @param obj
	 * @param fileName
	 * @throws Exception
	 */
	private void saveFile(Object obj, String fileName) throws Exception {
		ObjectOutputStream output = new ObjectOutputStream(
				new FileOutputStream(fileName));
		output.writeObject(obj);
		output.close();
	}
}