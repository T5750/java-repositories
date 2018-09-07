package com.yiibai;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * 基本字符串示例
 */
public class CharsetExample {
	public static void main(String[] args) {
		Charset cs = Charset.forName("UTF-8");
		System.out.println(cs.displayName());
		System.out.println(cs.canEncode());
		String st = "Welcome to yiibai.com, it is Charset test Example.";
		// The conversion of byte buffer from given charset to char buffer in
		// unicode
		ByteBuffer bytebuffer = ByteBuffer.wrap(st.getBytes());
		CharBuffer charbuffer = cs.decode(bytebuffer);
		// The converesion of char buffer from unicode to byte buffer in given
		// charset
		ByteBuffer newbytebuffer = cs.encode(charbuffer);
		while (newbytebuffer.hasRemaining()) {
			char ca = (char) newbytebuffer.get();
			System.out.print(ca);
		}
		newbytebuffer.clear();
	}
}