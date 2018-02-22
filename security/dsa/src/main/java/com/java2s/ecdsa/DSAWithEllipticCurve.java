package com.java2s.ecdsa;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class DSAWithEllipticCurve {
	public static void main(String[] args) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
		ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
		keyGen.initialize(ecSpec, new SecureRandom());
		KeyPair keyPair = keyGen.generateKeyPair();
		Signature signature = Signature.getInstance("ECDSA", "BC");
		signature.initSign(keyPair.getPrivate(), new SecureRandom());
		byte[] message = "abc".getBytes();
		signature.update(message);
		byte[] sigBytes = signature.sign();
		signature.initVerify(keyPair.getPublic());
		signature.update(message);
		System.out.println(signature.verify(sigBytes));
	}
}
