package com.java2s.rsa;

import java.security.*;

public class RSASignatureGeneration {
	public static void main(String[] args) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC");
		keyGen.initialize(512, new SecureRandom());
		KeyPair keyPair = keyGen.generateKeyPair();
		Signature signature = Signature.getInstance("SHA1withRSA", "BC");
		signature.initSign(keyPair.getPrivate(), new SecureRandom());
		byte[] message = "abc".getBytes();
		signature.update(message);
		byte[] sigBytes = signature.sign();
		signature.initVerify(keyPair.getPublic());
		signature.update(message);
		System.out.println(signature.verify(sigBytes));
	}
}
