package com.java2s.dsa;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;

public class DSAParameters {
	public static void main(String[] argv) throws Exception {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
		keyGen.initialize(1024);
		KeyPair keypair = keyGen.genKeyPair();
		DSAPrivateKey privateKey = (DSAPrivateKey) keypair.getPrivate();
		DSAPublicKey publicKey = (DSAPublicKey) keypair.getPublic();
		DSAParams dsaParams = privateKey.getParams();
		BigInteger p = dsaParams.getP();
		BigInteger q = dsaParams.getQ();
		BigInteger g = dsaParams.getG();
		BigInteger x = privateKey.getX();
		BigInteger y = publicKey.getY();
	}
}
