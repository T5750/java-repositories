package com.yiibai;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

import com.yiibai.util.Global;

/**
 * 从4个不同文件读取文件内容的简单示例，并将它们的组合输出写入第五个文件
 */
public class TransferDemo {
	public static void main(String[] argv) throws Exception {
		String relativelyPath = System.getProperty("user.dir");
		// Path of Input files
		String[] iF = new String[] {
				relativelyPath + Global.PROJECT_DIR + "/testin.txt",
				relativelyPath + Global.PROJECT_DIR + "/testin.txt",
				relativelyPath + Global.PROJECT_DIR + "/testin.txt",
				relativelyPath + Global.PROJECT_DIR + "/testin.txt" };
		// Path of Output file and contents will be written in this file
		String oF = relativelyPath + Global.PROJECT_DIR + "/testout.txt";
		// Acquired the channel for output file
		FileOutputStream output = new FileOutputStream(new File(oF));
		WritableByteChannel targetChannel = output.getChannel();
		for (int j = 0; j < iF.length; j++) {
			// Get the channel for input files
			FileInputStream input = new FileInputStream(iF[j]);
			FileChannel inputChannel = input.getChannel();
			// The data is tranfer from input channel to output channel
			inputChannel.transferTo(0, inputChannel.size(), targetChannel);
			// close an input channel
			inputChannel.close();
			input.close();
		}
		// close the target channel
		targetChannel.close();
		output.close();
		System.out.println("All jobs done...");
	}
}