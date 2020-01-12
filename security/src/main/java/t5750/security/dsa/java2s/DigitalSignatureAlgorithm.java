package t5750.security.dsa.java2s;

import java.security.*;

public class DigitalSignatureAlgorithm {
	public static void main(String[] args) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "BC");
		keyGen.initialize(512, new SecureRandom());
		KeyPair keyPair = keyGen.generateKeyPair();
		Signature signature = Signature.getInstance("DSA", "BC");
		signature.initSign(keyPair.getPrivate(), new SecureRandom());
		byte[] message = "abc".getBytes();
		signature.update(message);
		byte[] sigBytes = signature.sign();
		signature.initVerify(keyPair.getPublic());
		signature.update(message);
		System.out.println(signature.verify(sigBytes));
	}
}
