package com.nork.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nork.common.properties.ResProperties;
import com.nork.onekeydesign.exception.IntelligenceDecorationException;

public class FileUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);
	
	private static final String LOGPREFIX = "[文件工具类模块]:";
	
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
	
	/**
	 * 复制设计方案配置文件(ResDesign -> ResDesignOnekeyTemplate),需要复制一份物理文件,保持独立,互不影响
	 * 
	 * @author huangsongbo
	 * @param filePath
	 * @param fileKey
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	public static String copyFile(String filePath, String fileKey) throws IntelligenceDecorationException {
		String exceptionLogPrefix = "拷贝设计方案物理文件失败";
		
		// 参数验证 ->start
		if(StringUtils.isEmpty(filePath)) {
			String log = "StringUtils.isEmpty(filePath) = true";
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + "(" + log + ")");
		}
		if(StringUtils.isEmpty(fileKey)) {
			String log = "StringUtils.isEmpty(fileKey) = true";
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + "(" + log + ")");
		}
		// 参数验证 ->end
		
		
		// copy file ->start
		
		// get old file ->start
		String oldPath = Utils.getAbsolutePath(filePath, null);
		File oldFile = new File(oldPath);
		if(!oldFile.exists()) {
			String log = "oldFile.exists() = false; oldFilePath = " + oldPath;
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + "(" + log + ")");
		}
		// get old file ->end
		
		// get new file ->start
		String newFileName = 
				Utils.generateRandomDigitString(6) + "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS) 
				+ "_1" + oldPath.substring(oldPath.lastIndexOf("."));
		String newPathPart = Utils.getValueByFileKey(ResProperties.RES, fileKey, "");
		if(StringUtils.isEmpty(newPathPart)) {
			String log = "fileKey未配置; fileKey = " + fileKey;
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + "(" + log + ")");
		}
		String newPath = Utils.getAbsolutePath(newPathPart + newFileName, null);
		File newFile = new File(newPath);
		// get new file ->end
		
		// copy ->start
		try {
			org.apache.commons.io.FileUtils.copyFile(oldFile, newFile);
		} catch (IOException e) {
			String log = e.getMessage();
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + "(" + log + ")");
		}
		// copy ->end
		
		// copy file ->end
		return newPathPart + newFileName;
	}
	
}
