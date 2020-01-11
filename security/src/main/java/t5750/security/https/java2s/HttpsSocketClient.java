package t5750.security.https.java2s;

import java.io.*;
import java.net.Socket;

import javax.net.ssl.SSLSocketFactory;

public class HttpsSocketClient {
	public static void main(String args[]) throws Exception {
		System.setProperty("javax.net.ssl.trustStore", "clienttrust");
		SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
		Socket s = ssf.createSocket("127.0.0.1", 8080);
		OutputStream outs = s.getOutputStream();
		PrintStream out = new PrintStream(outs);
		InputStream ins = s.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(ins));
		out.println("Hi,How are u!");
		out.println("");
		String line = null;
		while ((line = in.readLine()) != null) {
			System.out.println(line);
		}
		in.close();
		out.close();
	}
}
