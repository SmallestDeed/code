package com.nork.base.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class FileUploadController {

	/*** 获取配置文件 tmg.properties */
	private final static ResourceBundle app = ResourceBundle.getBundle("app");

	/*** 获取路径开始部分 */
	private final static String uploadPathB = app.getString("app.upload.dir");

	/*** 定义路径结束部分键值 */
	protected String UPLOADPATHT = null;

	/*** 文件上传方法 */
	public void saveFile(HttpServletRequest request)
			throws IllegalStateException, IOException {

		/*** 解析器 完成对 MultipartFile 的解析 */
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());

		if (multipartResolver.isMultipart(request)) {

			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

			/*** 迭代器 获取每个上传的文件 */
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {

				/*** 获取MultipartFile 上传文件 */
				MultipartFile file = multiRequest.getFile((String) iter.next());

				/*** 判断文件是否为空 */
				if (file != null) {

					/*** 获取上传文件的文件名 */
					String fileName = file.getOriginalFilename();

					/*** 拼接存储文件名盖上时间戳 */
					Calendar now = Calendar.getInstance();
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyMMddhhmmss");
					String fileTimeStamp = dateFormat.format(now.getTime());
					String finalFlieName = fileName.substring(0,
							fileName.indexOf("."))
							+ "_"
							+ fileTimeStamp
							+ fileName.substring(fileName.indexOf("."));

					/*** 从配置文件获取文件保存路径 */
					String uploadPathE = app.getString(UPLOADPATHT);
					/*** 上传文件保存的路径 */
					String path = uploadPathB + uploadPathE + finalFlieName;

					File localFile = new File(path);
					/*** 判断文件及路径是否存在 */
					if (!localFile.exists()) {
						localFile.mkdirs();
					}

					/*** 写入上传文件 */
					file.transferTo(localFile);
				}
			}
		}
	}
}
