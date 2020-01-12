package t5750.security.dsa.java2s;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.security.SignedObject;
import java.util.Vector;

public class SignedObjectTest {
	public static void main(String[] args) throws Exception {
		String alg = "DSA";
		KeyPairGenerator kg = KeyPairGenerator.getInstance(alg);
		KeyPair keyPair = kg.genKeyPair();
		Vector v = new Vector();
		v.add("This is a test!");
		Signature sign = Signature.getInstance(alg);
		SignedObject so = new SignedObject(v, keyPair.getPrivate(), sign);
		System.out.println(so.verify(keyPair.getPublic(), sign));
	}
}
