package com.yiibai;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * 基本管道示例
 */
public class PipeExample {
	public static void main(String[] args) throws IOException {
		// The Pipe is created
		Pipe pipe = Pipe.open();
		// For accessing the pipe sink channel
		Pipe.SinkChannel skChannel = pipe.sink();
		String td = "Data is successfully sent for checking the java NIO Channel Pipe.";
		ByteBuffer bb = ByteBuffer.allocate(512);
		bb.clear();
		bb.put(td.getBytes());
		bb.flip();
		// write the data into a sink channel.
		while (bb.hasRemaining()) {
			skChannel.write(bb);
		}
		// For accessing the pipe source channel
		Pipe.SourceChannel sourceChannel = pipe.source();
		bb = ByteBuffer.allocate(512);
		// The data is write to the console
		while (sourceChannel.read(bb) > 0) {
			bb.flip();
			while (bb.hasRemaining()) {
				char TestData = (char) bb.get();
				System.out.print(TestData);
			}
			bb.clear();
		}
	}
}