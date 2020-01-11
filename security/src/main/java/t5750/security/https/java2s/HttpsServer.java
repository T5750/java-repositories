package t5750.security.https.java2s;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLServerSocketFactory;

public class HttpsServer {
	public static void main(String[] args) throws IOException {
		SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory
				.getDefault();
		ServerSocket ss = ssf.createServerSocket(8080);
		while (true) {
			try {
				Socket s = ss.accept();
				OutputStream out = s.getOutputStream();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						s.getInputStream()));
				String line = null;
				while (((line = in.readLine()) != null) && (!("".equals(line)))) {
					System.out.println(line);
				}
				StringBuffer buffer = new StringBuffer();
				buffer.append("<HTML><HEAD><TITLE>HTTPS Server</TITLE></HEAD>\n");
				buffer.append("<BODY>\n<H1>Success!</H1></BODY></HTML>\n");
				String string = buffer.toString();
				byte[] data = string.getBytes();
				out.write("HTTP/1.0 200 OK\n".getBytes());
				out.write(new String("Content-Length: " + data.length + "\n")
						.getBytes());
				out.write("Content-Type: text/html\n\n".getBytes());
				out.write(data);
				out.flush();
				out.close();
				in.close();
				s.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}