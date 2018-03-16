package com.java2s;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import javax.net.ssl.SSLServerSocketFactory;

public class HttpsServerProxy {
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
			String refer = null;
			while ((info = in.readLine()) != null) {
				if (info.startsWith("GET")) {
					request = info;
				}
				if (info.startsWith("Referer:")) {
					refer = info;
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
				if (refer != null) {
					sp1 = refer.indexOf(' ');
					refer = refer.substring(sp1 + 1, refer.length());
					if (!refer.endsWith("/")) {
						refer = refer + "/";
					}
					filename = refer + filename;
				}
				URL con = new URL(filename);
				InputStream gotoin = con.openStream();
				int n = gotoin.available();
				byte buf[] = new byte[1024];
				out.println("HTTP/1.0 200 OK\nMIME_version:1.0\nContent_Type:text/html");
				out.println("Content_Length:" + n + "\n");
				while ((n = gotoin.read(buf)) >= 0) {
					out.write(buf, 0, n);
				}
				out.close();
				s.close();
				in.close();
			}
		}
	}
}
