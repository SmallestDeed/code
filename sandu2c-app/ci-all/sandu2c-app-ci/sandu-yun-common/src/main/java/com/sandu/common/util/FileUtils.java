package com.sandu.common.util;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
	/**
	 * 创建不存在的目录
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean mkNotExistsDirs(String dir) {
		File file = new File(dir);
		if (!file.exists()) {
			return file.mkdirs();
		}
		return false;
	}

	/**
	 * 创建父目录
	 */
	public static boolean mkNotExistsParentDirs(String path) {
		File file = new File(path);
		File pfile = file.getParentFile();
		if (!pfile.exists()) {
			return pfile.mkdirs();
		}
		return false;
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExt(String filename) {
		if (filename != null) {
			int index = filename.lastIndexOf(".");
			if (index > 0) {
				return filename.substring(index);
			}
		}
		return null;
	}

	/**
	 * 获取文件名称
	 * 
	 */
	public static String getFileName(String s) {
		int index = s.lastIndexOf("/");
		String name = null;
		if (index > -1) {
			name = s.substring(index + 1);
		} else {
			index = s.lastIndexOf(File.separator);
			if (index > 0) {
				name = s.substring(index + 1);
			}
		}
		return name;
	}

	
	public static String readStringFromStream(InputStream in)      throws IOException {
     /*   StringBuilder sb = new StringBuilder();
        for (int i = in.read(); i != -1; i = in.read()) {
            sb.append((char) i);
        }
        in.close();
        return sb.toString();*/
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[512];
		int len = 0;
		while ((len = in.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();// 网页的二进制数据
		outStream.close();
		in.close();
		return new String(data,"UTF-8");
	}
	
	public static byte[] readBytesFromStream(InputStream in) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(512);
        IOUtils.copy(in, bos);
        in.close();
        return bos.toByteArray();
	}
}
