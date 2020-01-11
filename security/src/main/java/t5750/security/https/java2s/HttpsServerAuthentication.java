package t5750.security.https.java2s;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

public class HttpsServerAuthentication {
	public static void main(String args[]) throws Exception {
		SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory
				.getDefault();
		SSLServerSocket ss = (SSLServerSocket) ssf.createServerSocket(443);
		ss.setNeedClientAuth(true);
		while (true) {
			Socket s = ss.accept();
			SSLSession session = ((SSLSocket) s).getSession();
			Certificate[] cchain = session.getPeerCertificates();
			for (int j = 0; j < cchain.length; j++) {
				System.out
						.println(((X509Certificate) cchain[j]).getSubjectDN());
			}
			PrintStream out = new PrintStream(s.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			String info = null;
			while ((info = in.readLine()) != null) {
				System.out.println("now got " + info);
				if (info.equals("")) {
					break;
				}
			}
			out.println("HTTP/1.0 200 OK\nMIME_version:1.0");
			out.println("Content_Type:text/html");
			String c = "<html> <head></head><body> <h1> Hi,</h1></Body></html>";
			out.println("Content_Length:" + c.length());
			out.println("");
			out.println(c);
			out.close();
			s.close();
			in.close();
		}
	}
}