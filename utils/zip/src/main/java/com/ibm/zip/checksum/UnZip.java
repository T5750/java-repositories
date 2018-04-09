package com.ibm.zip.checksum;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 清单 5. 源代码
 */
public class UnZip {
	public static void main(String argv[]) {
		try {
			final int BUFFER = 2048;
			BufferedOutputStream dest = null;
			FileInputStream fis = new FileInputStream(argv[0]);
			CheckedInputStream checksum = new CheckedInputStream(fis,
					new Adler32());
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(
					checksum));
			ZipEntry entry;
			while ((entry = zis.getNextEntry()) != null) {
				System.out.println("Extracting: " + entry);
				int count;
				byte data[] = new byte[BUFFER];
				// write the files to the disk
				FileOutputStream fos = new FileOutputStream(entry.getName());
				dest = new BufferedOutputStream(fos, BUFFER);
				while ((count = zis.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();
			}
			zis.close();
			System.out
					.println("Checksum: " + checksum.getChecksum().getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}