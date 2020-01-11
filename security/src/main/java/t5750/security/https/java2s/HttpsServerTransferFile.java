package t5750.security.https.java2s;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLServerSocketFactory;

public class HttpsServerTransferFile {
	public static void main(String args[]) throws Exception {
		SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory
				.getDefault();
		ServerSocket ss = ssf.createServerSocket(443);
		while (true) {
			Socket s = ss.accept();
			PrintStream out = new PrintStream(s.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			String info = null;
			String request = null;
			while ((info = in.readLine()) != null) {
				if (info.indexOf("GET") != -1) {
					request = info;
				}
				if (info.equals("")) {
					break;
				}
			}
			if (request != null) {
				out.println("HTTP/1.0 200 OK\nMIME_version:1.0\nContent_Type:text/html");
				int sp1 = request.indexOf(' ');
				int sp2 = request.indexOf(' ', sp1 + 1);
				String filename = request.substring(sp1 + 2, sp2);
				if (filename.equals("") || filename.endsWith("/")) {
					filename += "index.html";
				}
				File fi = new File(filename);
				InputStream fs = new FileInputStream(fi);
				int n = fs.available();
				byte buf[] = new byte[1024];
				out.println("Content_Length:" + n);
				out.println("");
				while ((n = fs.read(buf)) >= 0) {
					out.write(buf, 0, n);
				}
				out.close();
				s.close();
				in.close();
			}
		}
	}
}