package com.yiibai;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Date;
import java.util.regex.Pattern;

/* Listen for connections and tell callers what time it is.
 * Demonstrates NIO socket channels (accepting and writing),
 * buffer handling, charsets, and regular expressions.
 */
/**
 * Java NIO时间服务器示例
 */
public class TimeServer {
	// The port we'll actually use
	private static int port = 8125;
	// Charset and encoder for US-ASCII
	private static Charset charset = Charset.forName("US-ASCII");
	private static CharsetEncoder encoder = charset.newEncoder();
	// Direct byte buffer for writing
	private static ByteBuffer dbuf = ByteBuffer.allocateDirect(1024);

	// Open and bind the server-socket channel
	//
	private static ServerSocketChannel setup() throws IOException {
		ServerSocketChannel ssc = ServerSocketChannel.open();
		String host = InetAddress.getLocalHost().getHostAddress();
		System.out.println("Listen at Host:" + host + ", port:" + port);
		InetSocketAddress isa = new InetSocketAddress(host, port);
		ssc.socket().bind(isa);
		return ssc;
	}

	// Service the next request to come in on the given channel
	//
	private static void serve(ServerSocketChannel ssc) throws IOException {
		SocketChannel sc = ssc.accept();
		try {
			String now = new Date().toString();
			sc.write(encoder.encode(CharBuffer.wrap(now + "\r\n")));
			System.out.println(sc.socket().getInetAddress() + " : " + now);
			sc.close();
		} finally {
			// Make sure we close the channel (and hence the socket)
			sc.close();
		}
	}

	public static void main(String[] args) throws IOException {
		if (args.length > 1) {
			System.err.println("Usage: java TimeServer [port]");
			return;
		}
		// If the first argument is a string of digits then we take that
		// to be the port number
		if ((args.length == 1) && Pattern.matches("[0-9]+", args[0])) {
			port = Integer.parseInt(args[0]);
		}
		ServerSocketChannel ssc = setup();
		for (;;) {
			serve(ssc);
		}
	}
}