package com.lihp.common;

import java.awt.Image;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {

	/**
	 * 文件 转 byte[]
	 * 
	 * @param f
	 * @return
	 */
	public static byte[] getBytesFromFile(File f) {
		if (f == null) {
			return null;
		}
		try {
			FileInputStream stream = new FileInputStream(f);
			ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = stream.read(b)) != -1) {
				out.write(b, 0, n);
			}
			stream.close();
			out.close();
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * byte[] 写入outputFile
	 * 
	 * @param b
	 * @param outputFile
	 * @return
	 */
	public static void createFileFromBytes(byte[] b, String outputFile) {
		BufferedOutputStream stream = null;
		File file = null;
		try {
			file = new File(outputFile);
			FileOutputStream fstream = new FileOutputStream(file);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * byte[] 转 Image
	 * 
	 * @param imageBytes
	 * @return
	 */
	public static Image createImage(byte[] imageBytes) {
		Image image = null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
			image = ImageIO.read(bais);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}

}
