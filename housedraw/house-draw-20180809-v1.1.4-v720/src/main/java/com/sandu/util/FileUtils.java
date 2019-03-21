package com.sandu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

import com.sandu.api.space.bo.DrawSpaceFileBO;
import org.apache.commons.lang3.StringUtils;

import com.sandu.common.constant.SystemConfigEnum;
import com.sandu.common.constant.house.SystemConstant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtils {

	/**
	 * 加密文件
	 * copy from 通用版本 
	 * 
	 * @param path 源文件位置
	 * @param toPath 加密后文件存放位置
	 */
	public static void encryptFile(String path, String toPath) {
		// 参数验证 ->start
		if (StringUtils.isEmpty(path)) {
			log.warn("参数path为空， path ==> {}", path);
			return;
		}
		if (StringUtils.isEmpty(toPath)) {
			log.warn("参数toPath为空， toPath ==> {}", toPath);
			return;
		}
		// 参数验证 ->end
		
		File sFile = new File(path);
		if (!sFile.exists()) {
			log.warn("文件不存在，path ==> {}", sFile.getPath());
			return;
		}
		
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(sFile);
			// 注释原因:资源存储方式改为分布式存储
			/* String encryptFilePath = encryptUploadRoot + relativePath; */
			List<String> DECSuffixList = new ArrayList<>();
			DECSuffixList.add("txt");

			// 加密 ->start
			if (DECSuffixList.indexOf(sFile.getPath().substring(sFile.getPath().lastIndexOf(".") + 1)) != -1) {
				encryptFileOfTxt(toPath, sFile);
			} else {
				addRedundance(toPath, inputStream);
			}
			// 加密 ->end
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 配置文件进行加密
	 *
	 * @param destination  结果输出到目标文件
	 * @param file  输入流
	 * @throws IOException
	 */
	public static void encryptFileOfTxt(String destination, File file) {
		// 配置文件加密
		/*
		 * String keyAes = ""; if(key.length() < 8){ keyAes =
		 * String.format("%1$0"+(8-key.length())+"d",0); }else{ keyAes =
		 * key.substring(0,8); }
		 */

		File outFile = new File(destination);
		if (!outFile.exists())// 文件不存在则创建目录
			outFile.getParentFile().mkdirs();

		FileOutputStream oupPut = null;
		String value = null;
		// 加密
		try {
			value = org.apache.commons.io.FileUtils.readFileToString(file, Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}

		String encrypt = AESUtil.getInstance().encrypt(value);
		try {
			oupPut = new FileOutputStream(outFile); // 输出文件
			oupPut.write(encrypt.getBytes(), 0, encrypt.getBytes().length);
			oupPut.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != oupPut)
					oupPut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 加密assetbundle后缀类型的文件
	 *
	 * @param destination  结果输出到目标文件
	 * @param input  输入流
	 * @throws IOException
	 */
	public static void addRedundance(String destination, InputStream input) {

		String key = Utils.getValue(SystemConfigEnum.AES_RESOURCES_ENCRYPT_KEY.getKey());

		int index;
		byte[] bytes = new byte[1024];

		File outFile = new File(destination);
		if (!outFile.exists())// 文件不存在则创建目录
			outFile.getParentFile().mkdirs();

		FileOutputStream oupPut = null;
		try {
			oupPut = new FileOutputStream(outFile); // 输出文件
			oupPut.write(key.getBytes(), 0, key.getBytes().length);
			oupPut.flush();
			while ((index = input.read(bytes)) != -1) { // 每次读取1024byte，直到返回-1表示结束
				oupPut.write(bytes, 0, index);
				oupPut.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != oupPut)
					oupPut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @param source
	 *            /aa/design_templet/2018/3/12/e89903kjsk_kljsdj3l_kljsd.png
	 * @param fileKey
	 * @return
	 * @throws IOException
	 */
	public static String copyFile(String source, String fileKey) throws IOException {
		return copyFileWithReplaceable(source, fileKey, null);
	}

	/**
	 *
	 * @param source
	 *            /aa/design_templet/2018/3/12/e89903kjsk_kljsdj3l_kljsd.png
	 * @param fileKey
	 * @return
	 * @throws IOException
	 */
	public static String copyFileWithReplaceable(String source, String fileKey, String replaceStr) throws IOException {
		Objects.requireNonNull(source, "参数source不能为空！");
		// 复制物理文件 ->start
		String sourceFile = source.replace(SystemConstant.UPLOAD_ROOT, "");
		String targetPath = (replaceStr == null ? Utils.getValue(fileKey) : Utils.getValueWithReplaceable(fileKey, replaceStr)) + Utils.getNewFileName(sourceFile);
		String targetFile = SystemConstant.UPLOAD_ROOT + targetPath;
		// copy file
		org.apache.commons.io.FileUtils.copyFile(new File(SystemConstant.UPLOAD_ROOT + sourceFile), new File(targetFile));
		return targetPath;
	}

	/**
	 * 批量删除文件
	 * @param file
	 */
	public static void delete(String file) {
		delete(SystemConstant.UPLOAD_ROOT, file);
	}

	/**
	 * 批量删除文件
	 * @param files
	 */
	public static void delete(String[] files) {
		delete(SystemConstant.UPLOAD_ROOT, files);
	}

	/**
	 * 批量删除文件
	 * @param files
	 */
	public static void delete(String rootDir, String...files) {
		if (files == null || files.length <= 0) {
			return;
		}

		File file;
		// 文件的根目录
		rootDir = StringUtils.isEmpty(rootDir) ? SystemConstant.UPLOAD_ROOT : rootDir;

		for (String fileDir : files) {
			if (!StringUtils.isEmpty(fileDir)) {
				file = new File(rootDir + fileDir);
				try {
					file.delete();
				} catch (Exception e) {
				}
//				org.apache.commons.io.FileUtils.deleteQuietly(file);
				log.debug("删除文件 file => {}", file);
			}
		}
	}

	public static void delete(List<DrawSpaceFileBO> drawSpaceFiles) {
		if (drawSpaceFiles == null || drawSpaceFiles.isEmpty()) {
			return;
		}

		File file;
		// 文件的根目录
		String rootDir = SystemConstant.UPLOAD_ROOT;

		for (DrawSpaceFileBO fileBO : drawSpaceFiles) {
			if (!StringUtils.isEmpty(fileBO.getFilePath())) {
				file = new File(rootDir + fileBO.getFilePath());
				try {
					file.delete();
				} catch (Exception e) {
				}
//				org.apache.commons.io.FileUtils.deleteQuietly(file);
				log.debug("删除文件 file => {}", file);
			}
		}
	}
}
