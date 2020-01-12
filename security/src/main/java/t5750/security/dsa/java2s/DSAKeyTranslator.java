package t5750.security.dsa.java2s;

import java.security.*;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.InvalidKeySpecException;

public class DSAKeyTranslator {
	public static void main(String[] args) throws NoSuchAlgorithmException,
			InvalidKeySpecException {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
		kpg.initialize(512);
		KeyPair keys = kpg.genKeyPair();
		PrivateKey priKey = keys.getPrivate();
		PublicKey pubKey = keys.getPublic();
		KeyFactory kf = KeyFactory.getInstance("DSA");
		DSAPrivateKeySpec dsaPriKeySpec = (DSAPrivateKeySpec) kf.getKeySpec(
				priKey, DSAPrivateKeySpec.class);
		DSAPublicKeySpec dsaPubKeySpec = (DSAPublicKeySpec) kf.getKeySpec(
				pubKey, DSAPublicKeySpec.class);
		System.out.println("\nDSA Private Key");
		System.out.println("\nx = " + dsaPriKeySpec.getX());
		System.out.println("\nDSA Public Key");
		System.out.println("\ng = " + dsaPubKeySpec.getG());
		System.out.println("\np = " + dsaPubKeySpec.getP());
		System.out.println("\nq = " + dsaPubKeySpec.getQ());
		System.out.println("\ny = " + dsaPubKeySpec.getY());
	}
}
/*
 * 
 * DSA Private Key
 * 
 * x = 776400661570001590971791637592968309673321751461
 * 
 * DSA Public Key
 * 
 * g =
 * 5421644057436475141609648488325705128047428394380474376834667300766108262613900542681289080713724597310673074119355136085795982097390670890367185141189796
 * 
 * p =
 * 13232376895198612407547930718267435757728527029623408872245156039757713029036368719146452186041204237350521785240337048752071462798273003935646236777459223
 * 
 * q = 857393771208094202104259627990318636601332086981
 * 
 * y =
 * 9079896982621092847112483863863391775338648287464668946120962630349123906761002084264031103470728516533966483834610830067548970604189069706612392762346323
 */
