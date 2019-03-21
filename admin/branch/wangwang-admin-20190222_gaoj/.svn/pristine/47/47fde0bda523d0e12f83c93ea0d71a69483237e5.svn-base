package com.sandu.service.solution.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUtils {

	/**
	 * 下载远程文件并保存到本地
	 * 
	 * @param remoteFilePath 远程文件路径
	 * @param localFilePath
	 *            本地文件路径
	 */
	public static void downloadFile(String remoteFilePath, String localFilePath) {
		URL urlfile = null;
		HttpURLConnection httpUrl = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		File f = new File(localFilePath);
		if (!f.getParent().isEmpty()) {
			try {
				f.getParentFile().mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			urlfile = new URL(remoteFilePath);
			httpUrl = (HttpURLConnection) urlfile.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			bos = new BufferedOutputStream(new FileOutputStream(f));
			int len = 2048;
			byte[] b = new byte[len];
			while ((len = bis.read(b)) != -1) {
				bos.write(b, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bos.flush();
				httpUrl.disconnect();
				bis.close();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		FileUtils.downloadFile(
				"https://show.ci.sanduspace.com/AA/c_basedesign_recommended/2018/05/07/13/design/designPlanRecommended/render/847763_1525671607269.png",
				"D:/upload/windowsPc/resRenderPic/2018/05/24/14/design/designPlanRecommended/render/pic/125918_20180524145802135.png");
//		FileUtils.downloadFile(
//				"https://show.ci.sanduspace.com/AA/c_basedesign_recommended/2018/03/30/18/design/designPlanRecommended/render/448846_1522404047239.png",
//				"d:/image/123.png");
		// FileUtils.fileCopy(new
		// File("https://show.ci.sanduspace.com/AA/c_basedesign_recommended/2018/03/30/18/design/designPlanRecommended/render/"),
		// new
		// File("https://show.ci.sanduspace.com/AA/c_basedesign_recommended/2018/05/24/18/design/designPlanRecommended/render/"));
	}
}
