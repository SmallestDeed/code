package com.nork.user.controller;

import java.io.File;
import java.io.IOException;

public class FileUtil {

	public static void main(String[] args) {
		String createDir = "E://nork/tmp/up/txt.jpg";
		//createDir(createDir);
		CreateFile(createDir);
	}

	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			//////System.out.println("创建目录" + destDirName + "失败，目标目录已存在！");
			return false;
		}
		if (!destDirName.endsWith(File.separator))
			destDirName = destDirName + File.separator;
		// 创建单个目录
		if (dir.mkdirs()) {
			//////System.out.println("创建目录" + destDirName + "成功！");
			return true;
		} else {
			//////System.out.println("创建目录" + destDirName + "成功！");
			return false;
		}
	}

	public static boolean CreateFile(String destFileName) {
		File file = new File(destFileName);
		if (file.exists()) {
			//////System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
			return false;
		}
		if (destFileName.endsWith(File.separator)) {
			//////System.out.println("创建单个文件" + destFileName + "失败，目标不能是目录！");
			return false;
		}
		if (!file.getParentFile().exists()) {
			//////System.out.println("目标文件所在路径不存在，准备创建。。。");
			if (!file.getParentFile().mkdirs()) {
				//////System.out.println("创建目录文件所在的目录失败！");
				return false;
			}
		}

		// 创建目标文件
		try {
			if (file.createNewFile()) {
				//////System.out.println("创建单个文件" + destFileName + "成功！");
				return true;
			} else {
				//////System.out.println("创建单个文件" + destFileName + "失败！");
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			//////System.out.println("创建单个文件" + destFileName + "失败！");
			return false;
		}
	}

}
