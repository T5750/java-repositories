package com.yiibai;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 基本FileLock示例
 */
public class PrintFile {
	public static void print(String path) throws IOException {
		FileReader filereader = new FileReader(path);
		BufferedReader bufferedreader = new BufferedReader(filereader);
		String tr = bufferedreader.readLine();
		System.out.println("The Content of testout-file.txt file is: ");
		while (tr != null) {
			System.out.println("    " + tr);
			tr = bufferedreader.readLine();
		}
		filereader.close();
		bufferedreader.close();
	}
}