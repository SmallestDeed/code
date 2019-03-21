///**  
// * All rights Reserved, Designed By www.xxxx.com
// * @Title:  NewFtpUploadUtils.java   
// * @Package com.nork.common.util   
// * @Description:    TODO(用一句话描述该文件做什么)   
// * @author: xxxx    
// * @date:   2017年3月13日 下午4:07:54   
// * @version V1.0 
// * @Copyright: 2017 www.xxxx.com Inc. All rights reserved. 
// * 注意：本内容仅限于xxxx内部传阅，禁止外泄以及用于其他的商业目
// */  
//package com.nork.common.util;
//
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.InetSocketAddress;
//import java.util.List;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.net.ftp.FTPClient;
//import org.apache.commons.net.ftp.FTPFile;
//import org.apache.commons.net.ftp.FTPReply;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.multipart.MultipartFile;
//
//import sun.net.ftp.FtpClient;
//
///**   
// * @ClassName:  NewFtpUploadUtils   
// * @Description:TODO(这里用一句话描述这个类的作用)   
// * @author: xxxx 
// * @date:   2017年3月13日 下午4:07:54   
// *     
// * @Copyright: 2017 www.xxxx.com Inc. All rights reserved. 
// * 注意：本内容仅限于xxxx内部传阅，禁止外泄以及用于其他的商业目 
// */
//public class NewFtpUploadUtils {
//
//	private static final Logger logger = LoggerFactory.getLogger(NewFtpUploadUtils.class);
//
//	public final static String FTP_ROOT_PATH = Utils.getValue("app.ftp.upload.root", "/").trim();
//	public final static String FTP_HOST = Utils.getValue("app.ftp.server.host", "").trim();
//	public final static String FTP_USER = Utils.getValue("app.ftp.server.user", "").trim();
//	public final static String FTP_PASSWORD = Utils.getValue("app.ftp.server.password", "").trim();
//
//	/**
//	 * 
//	 * @Title: ftpUploadFile   
//	 * @Description: 向FTP服务器上传文件   
//	 * @param path
//	 * @param file
//	 * @param filename
//	 * @return: boolean      
//	 */
//	public static boolean ftpUploadFile(String path, MultipartFile file, String filename) {
//		boolean success = false;
//		FTPClient ftpc = new FTPClient();
//		String ftpPath = ((StringUtils.isEmpty(FTP_ROOT_PATH.trim()) || "/".equals(FTP_ROOT_PATH.trim())) ? "" : FTP_ROOT_PATH.trim().replace("\\", "/")) + path;
//		String dbFilePath = path + filename;
//		File sFile = new File(dbFilePath);
//		try {
//			int reply;
//			ftpc.connect(FTP_HOST);// 连接FTP服务器
//			ftpc.login(FTP_USER, FTP_PASSWORD);// 登录
//			reply = ftpc.getReplyCode();
//			if (!FTPReply.isPositiveCompletion(reply)) {
//				ftpc.disconnect();
//				return success;
//			}
//			String newpath = ftpPath.substring(0, ftpPath.lastIndexOf("/") - 1);
//			String ppath = newpath.substring(0, newpath.lastIndexOf("/"));
//			if (!isDirExist(ftpc, ppath)) {
//				if (!ftpc.makeDirectory(new String(ppath.getBytes("GBK"), "iso-8859-1"))) {
//					logger.info("创建文件目录【" + ppath + "】 失败！");
//				}
//			}
//			
//			if (!isDirExist(ftpc, ftpPath)) {
//				if (!ftpc.makeDirectory(new String(ftpPath.getBytes("GBK"), "iso-8859-1"))) {
//					logger.info("创建文件目录【" + ftpPath + "】 失败！");
//				}
//			}
//			ftpc.changeWorkingDirectory(ftpPath);
//			ftpc.setBufferSize(1024); // 设置1M缓冲
//			ftpc.setControlEncoding("GBK"); // 设置编码为GBK
//			ftpc.setFileType(FTPClient.BINARY_FILE_TYPE); // 文件类型为二进制文件
//			OutputStream os = ftpc.appendFileStream(filename);
//			byte[] bytes = new byte[1024];
//			InputStream is = file.getInputStream();
//			int c;
//			while ((c = is.read(bytes)) != -1) {
//				os.write(bytes, 0, c);
//			}
//			os.flush();
//			logger.info("====================>" + sFile.getPath());
//			is.close();
//			os.close();
//			ftpc.logout();
//			success = true;
//		} catch (Exception e) {
//			logger.error("向FTP服务器上传文件操作失败", e);
//			e.printStackTrace();
//		} finally {
//			try {
//				ftpc.disconnect();
//			} catch (Exception e) {
//				logger.error("向FTP服务器上传文件操作失败", e);
//				e.printStackTrace();
//			}
//		}
//		return success;
//	}
//
//	/**
//	 * 
//	 * @Title: isDirExist   
//	 * @Description: 判断Ftp目录是否存在,如果不存在则创建目录   
//	 * @param ftpClient
//	 * @param dir
//	 * @return: boolean      
//	 */
//	public static boolean isDirExist(FTPClient ftpClient, String dir) {
//		boolean success = false;
//		try {
//			success = ftpClient.changeWorkingDirectory(dir); 
//		} catch (Exception e) {
//			logger.error("判断Ftp目录是否存在,如果不存在则创建目录操作失败", e);
//			e.printStackTrace();
//		}
//		return success;
//	}
//
//	/**
//	 * 
//	 * @Title: makeWorkDir   
//	 * @Description: 创建ftp文件目录   
//	 * @param ftpc
//	 * @param ftpPath
//	 * @return: boolean      
//	 */
//	public static boolean makeWorkDir(FTPClient ftpc, String ftpPath){
//		boolean flag =true;
//		try{
//		String newpath = ftpPath.substring(0, ftpPath.lastIndexOf("/") - 1);
//		String ppath = newpath.substring(0, newpath.lastIndexOf("/"));
//		if (!isDirExist(ftpc, ppath)) {
//			if (!ftpc.makeDirectory(new String(ppath.getBytes("GBK"), "iso-8859-1"))) {
//				logger.info("创建文件目录【" + ppath + "】 失败！");
//				return false;
//			}
//		}
//		if (!isDirExist(ftpc, ftpPath)) {
//			if (!ftpc.makeDirectory(new String(ftpPath.getBytes("GBK"), "iso-8859-1"))) {
//				logger.info("创建文件目录【" + ftpPath + "】 失败！");
//				return false;
//			}
//		 }
//		}catch(Exception e){
//			logger.error("创建ftp文件目录操作失败", e);
//			e.printStackTrace();
//		}
//		return flag;
//	}
//	
//	/**
//	 * 
//	 * @Title: uploadFile   
//	 * @Description: 向FTP服务器上传文件 </p>   
//	 * @param filename
//	 *          文件名称
//	 * @param sourcePath
//	 * 			文件路径
//	 * @param ftpPath
//	 * 			ftp存放路径
//	 * @return: boolean      
//	 */
//	public static boolean uploadFile(String filename, String sourcePath, String ftpPath) {
//		boolean success = false;
//		FTPClient ftpc = new FTPClient();
//		try {
//			int reply;
//			ftpc.connect(FTP_HOST);
//			ftpc.login(FTP_USER, FTP_PASSWORD);
//			reply = ftpc.getReplyCode();
//			if (!FTPReply.isPositiveCompletion(reply)) {
//				logger.info("----------登录失败！");
//				ftpc.disconnect();
//				return success;
//			}
//			String ftpFilePath = ((StringUtils.isEmpty(FTP_ROOT_PATH.trim()) || "/".equals(FTP_ROOT_PATH.trim())) ? "" : FTP_ROOT_PATH.trim().replace("\\", "/")) + ftpPath;
//			logger.info("----------登录成功！");
//			
//			RenderUtil.makeFtpDirectory(ftpc,ftpFilePath);
//			
//			ftpc.changeWorkingDirectory(new String(ftpFilePath.getBytes("GBK"), "iso-8859-1"));
//			
//			logger.info("----------原路径=" + sourcePath);
//			ftpc.setBufferSize(1024); // 设置1M缓冲
//			ftpc.setControlEncoding("GBK"); // 设置编码为GBK
//			ftpc.setFileType(FTPClient.BINARY_FILE_TYPE); // 文件类型为二进制文件
//			FileInputStream in = new FileInputStream(new File(sourcePath));
//			ftpc.storeFile(new String(filename.getBytes("GBK"), "iso-8859-1"), in);
//			logger.info("----------Ftp路径=" + ftpFilePath + filename);
//			in.close();
//			ftpc.logout();
//			success = true;
//		} catch (Exception e) {
//			logger.error("向FTP服务器上传文件操作失败",e);
//			e.printStackTrace();
//		} finally {
//			if (ftpc != null && ftpc.isConnected()) {
//				try {
//					ftpc.disconnect();
//				} catch (Exception e) {
//					logger.error("向FTP服务器上传文件操作失败",e);
//					e.printStackTrace();
//				}
//			}
//		}
//		return success;
//
//	}
//
//	/**
//	 * 
//	 * @Title: downFile   
//	 * @Description: 从FTP服务器下载文件 </p>   
//	 * @param path
//	 * 			FTP服务器上的相对路径
//	 * @param resourcesName
//	 * 			要下载的文件名
//	 * @param localPath
//	 * 			下载后保存到本地的路径
//	 * @return: boolean      
//	 */
//	public static boolean downFile(String path, String resourcesName, String localPath) {
//		boolean success = false;
//		FTPClient ftp = new FTPClient();
//		try {
//			int reply;
//			ftp.connect(FTP_HOST);
//			ftp.login(FTP_USER, FTP_PASSWORD);
//			reply = ftp.getReplyCode();
//			if (!FTPReply.isPositiveCompletion(reply)) {
//				logger.info("ftp连接异常,请检查ftp地址帐号，用户和密码是否正确。。。");
//				ftp.disconnect();
//				return success;
//			}
//			String ftpPath = ((StringUtils.isEmpty(FTP_ROOT_PATH.trim()) || "/".equals(FTP_ROOT_PATH.trim())) ? "" : FTP_ROOT_PATH.trim().replace("\\", "/")) + path;
//			/*String bdFilePath = Constants.UPLOAD_ROOT.replace("\\", "/") + localPath;*/
//			String bdFilePath = Utils.getAbsolutePath(localPath, null);
//			ftp.changeWorkingDirectory(ftpPath);
//			logger.info("----------Ftp路径=" + ftpPath);
//			FTPFile[] fs = ftp.listFiles();
//			if (fs == null || fs.length == 0) {
//				logger.info("ftp路径获取错误！！！ftpPath=" + ftpPath + ";bdFilePath=" + bdFilePath + ";localPath=" + localPath + ";resourcesName=" + resourcesName + ";path=" + path);
//				return success;
//			}
//			for (FTPFile ff : fs) {
//				if (ff != null && ff.getName().equals(resourcesName)) {
//					File localFile = new File(bdFilePath);
//					if (!localFile.exists()) {
//						try {
//							localFile.getParentFile().mkdirs();
//							localFile.createNewFile();
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//					logger.info("----------本地路径===================>" + bdFilePath);
//					OutputStream is = new FileOutputStream(localFile);
//					ftp.retrieveFile(ff.getName(), is);
//					is.close();
//				}
//			}
//			ftp.logout();
//			success = true;
//		} catch (Exception e) {
//			logger.error("从FTP服务器下载文件操作失败", e);
//			e.printStackTrace();
//		} finally {
//			if (ftp.isConnected()) {
//				try {
//					ftp.disconnect();
//				} catch (Exception e) {
//					logger.error("从FTP服务器下载文件操作失败", e);
//					e.printStackTrace();
//				}
//			}
//		}
//		return success;
//	}
//
//
//	/**
//	 * 
//	 * @Title: downFile   
//	 * @Description: 从FTP服务器下载文件   
//	 * @param path
//	 * @param resourcesName
//	 * @return: boolean      
//	 */
//	public static boolean downFile(String path, String resourcesName) {
//		boolean success = false;
//		FTPClient ftp = new FTPClient();
//		try {
//			int reply;
//			ftp.connect(FTP_HOST);
//			ftp.login(FTP_USER, FTP_PASSWORD);
//			reply = ftp.getReplyCode();
//			if (!FTPReply.isPositiveCompletion(reply)) {
//				logger.info("ftp连接异常,请检查ftp地址帐号，用户和密码是否正确。。。");
//				ftp.disconnect();
//				return success;
//			}
//		
//			String ftpdirpath = ((StringUtils.isEmpty(FTP_ROOT_PATH.trim()) || "/".equals(FTP_ROOT_PATH.trim())) ? "" : FTP_ROOT_PATH.trim().replace("\\", "/")) + path;
//			String ftpPath = ftpdirpath+resourcesName;
//			
//			String relativePath = ("".equals(Utils.getValue(FileUploadUtils.SYSTEM_FORMAT,"linux")) ? path:path.replace("/", "\\"))+resourcesName;
//			String localPath = Utils.getAbsolutePath(relativePath, null);
//			/*String localPath =  Constants.UPLOAD_ROOT +("".equals(Utils.getValue(FileUploadUtils.SYSTEM_FORMAT,"linux")) ? path:path.replace("/", "\\"))+resourcesName;*/
//			ftp.changeWorkingDirectory(ftpdirpath); // 转移到FTP服务器目录
//			logger.info("----------Ftp目录路径=" + ftpdirpath);
//			FTPFile[] fs = ftp.listFiles();
//			if (fs == null || fs.length == 0) {
//				logger.info("ftp路径获取错误！！！ftpPath=" + ftpPath + ";localPath=" + localPath + ";localPath=" + localPath + ";resourcesName=" + resourcesName + ";path=" + path);
//				return success;
//			}
//			for (FTPFile ff : fs) {
//				if (ff != null && ff.getName().equals(resourcesName)) {
//					File localFile = new File(localPath);
//					if (!localFile.exists()) {
//						try {
//							localFile.getParentFile().mkdirs();
//							localFile.createNewFile();
//						} catch (Exception e) {
//							logger.error("从FTP服务器下载文件 操作失败", e);
//							e.printStackTrace();
//						}
//					}
//					logger.info("----------本地路径=" + localPath);
//					OutputStream is = new FileOutputStream(localFile);
//					ftp.retrieveFile(ff.getName(), is);
//					is.close();
//				}
//			}
//			ftp.logout();
//			success = true;
//		} catch (Exception e) {
//			logger.error("从FTP服务器下载文件 操作失败", e);
//			e.printStackTrace();
//		} finally {
//			if (ftp.isConnected()) {
//				try {
//					ftp.disconnect();
//				} catch (Exception e) {
//					logger.error("从FTP服务器下载文件 操作失败", e);
//					e.printStackTrace();
//				}
//			}
//		}
//		return success;
//	}
//	
//	/**
//	 * 
//	 * @Title: replaceFile   
//	 * @Description: 替换文件内容   
//	 * @param filePath
//	 * 			文件地址
//	 * @param context
//	 * 			替换的内容
//	 * @return: boolean      
//	 */
//	public static boolean replaceFile(String filePath, String context) {
//		if (StringUtils.isBlank(filePath)) {
//			return false;
//		}
//		String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
//		String ftpPath = ((StringUtils.isEmpty(FTP_ROOT_PATH.trim()) || "/".equals(FTP_ROOT_PATH.trim())) ? "" : FTP_ROOT_PATH.trim().replace("\\", "/")) + filePath.substring(0, filePath.lastIndexOf("/"));
//		FTPClient ftp = new FTPClient();
//		try {
//			ftp.connect(FTP_HOST);
//			ftp.login(FTP_USER, FTP_PASSWORD);
//			boolean flag =makeWorkDir(ftp,ftpPath);
//			if(flag){
//				ftp.changeWorkingDirectory(new String(ftpPath.getBytes("GBK"), "iso-8859-1"));
//			}else{
//				return false;
//			}
//			ftp.changeWorkingDirectory(ftpPath);
//			InputStream is = new ByteArrayInputStream(context.getBytes());
//			// 先删除之前的文件
//			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
//			ftp.storeFile(new String(fileName.getBytes("utf-8"), "iso-8859-1"), is);
//		} catch (Exception e) {
//			logger.error("替换文件内容操作失败", e);
//			e.printStackTrace();
//			return false;
//		} finally {
//			if (ftp.isConnected()) {
//				try {
//					ftp.disconnect();
//				} catch (Exception e) {
//					logger.error("替换文件内容操作失败", e);
//					e.printStackTrace();
//					return false;
//				}
//			}
//		}
//		return true;
//	}
//
//	/**
//	 * 
//	 * @Title: downloadFileFtp   
//	 * @Description: 从ftp下载文件 </p>   
//	 * @param filePath
//	 * @param response
//	 * @throws Exception      
//	 * @return: void      
//	 */
//	public static void downloadFileFtp(String filePath, HttpServletResponse response) throws Exception{
//			OutputStream outputStream = response.getOutputStream();
//			String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
//			String ftpPath = ((StringUtils.isEmpty(FTP_ROOT_PATH.trim()) || "/".equals(FTP_ROOT_PATH.trim())) ? "" : FTP_ROOT_PATH.trim().replace("\\", "/")) + filePath.substring(0, filePath.lastIndexOf("/"));
//			FtpClient ftpClient = FtpClient.create();
//			response.setHeader("Content-disposition","attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
//			ftpClient.connect(new InetSocketAddress(FTP_HOST, 21));
//			ftpClient.login(FTP_USER, FTP_PASSWORD.toCharArray());
//			ftpClient.changeDirectory(ftpPath);
//			ftpClient.getFile(fileName, outputStream);
//			ftpClient.close();
//	}
//
//
//	/**
//	 * 
//	 * @Title: copyFileFromFtp   
//	 * @Description: 从ftp复制文件   
//	 * @param path1
//	 * 			from 目录	
//	 * @param path2
//	 * 			to 目录
//	 * @return: boolean      
//	 * @throws
//	 */
//	public static boolean copyFileFromFtp(String path1, String path2) {
//		boolean flag = true;
//		String resourcesName = path1.substring(path1.lastIndexOf("/") + 1, path1.length());
//		path1 = path1.substring(0, path1.lastIndexOf("/") + 1);
//		String localPath = Tools.getRootPath(path1, "") + path1.replace("/", "\\") + resourcesName;
//		if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
//			localPath = Tools.getRootPath(path1, "")  + path1 + resourcesName;
//		}
//		File file = new File(localPath);
//		if (!file.exists()) {
//			flag = downFile(path1, resourcesName, localPath);
//		}
//		if (!flag) {
//			logger.info("------从ftp上下载文件失败");
//			return false;
//		}
//		/* 上传文件到指定路径 */
//		flag = uploadFile(resourcesName, localPath, path2);
//		/* 是否删除本地文件 */
//		if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 2) {
//			FileUploadUtils.deleteFile(localPath);
//		}
//		return true;
//	}
//
//	
//	public static void main(String args[]) {
//		String ftpServer = "192.168.1.211";
//		String userName = "zwj";
//		String password = "123456";
//		NewFtpUploadUtils.deleteDir("/MaxRender/MaxRender/","11",ftpServer,userName,password);
//	}
//
//	/**
//	 * 
//	 * @Title: uploadFiles   
//	 * @Description: 文件批量上传ftp   
//	 * @param files
//	 * 			本地文件list      
//	 * @return: void      
//	 */
//	public static void uploadFiles(List<File> files) {
//		FTPClient ftpClient = new FTPClient();
//		try {
//			ftpClient.connect(FTP_HOST);
//			ftpClient.login(FTP_USER, FTP_PASSWORD);
//			int reply = ftpClient.getReplyCode();
//			if (!FTPReply.isPositiveCompletion(reply)) {
//				logger.info("------连接ftp失败");
//				ftpClient.disconnect();
//			}
//			for (File file : files) {
//				String filePath = file.getPath().replace("\\", "/");
//				String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
//				/*String rootPath = Constants.UPLOAD_ROOT;*/
//				/*String ftpPath = filePath.replace(rootPath.replace("\\", "/"), "");*/
//				String ftpPath = Utils.getRelativeUrlByAbsolutePath(filePath);
//				ftpPath = ftpPath.substring(0, ftpPath.lastIndexOf("/") + 1);
//				String ftpFilePath = ((StringUtils.isEmpty(FTP_ROOT_PATH.trim()) || "/".equals(FTP_ROOT_PATH.trim())) ? "" : FTP_ROOT_PATH.trim().replace("\\", "/")) + ftpPath;
//			
//				String newpath = ftpFilePath.substring(0, ftpFilePath.lastIndexOf("/") - 1);
//				String ppath = newpath.substring(0, newpath.lastIndexOf("/"));
//				if (!isDirExist(ftpClient, ppath)) {
//					if (!ftpClient.makeDirectory(new String(ppath.getBytes("GBK"), "iso-8859-1"))) {
//						logger.info("创建文件目录【" + ppath + "】 失败！");
//					}
//				}
//				if (!isDirExist(ftpClient, ftpFilePath)) {
//					if (!ftpClient.makeDirectory(new String(ftpFilePath.getBytes("GBK"), "iso-8859-1"))) {
//						logger.info("创建文件目录【" + ftpFilePath + "】 失败！");
//					}
//				}
//				ftpClient.changeWorkingDirectory(new String(ftpFilePath.getBytes("GBK"), "iso-8859-1"));
//				logger.info("----------原路径=" + file.getPath());
//				ftpClient.setBufferSize(1024); // 设置1M缓冲
//				ftpClient.setControlEncoding("GBK"); // 设置编码为GBK
//				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); // 文件类型为二进制文件
//				FileInputStream in = new FileInputStream(file);
//				ftpClient.storeFile(new String(fileName.getBytes("GBK"), "iso-8859-1"), in);
//				logger.info("----------Ftp路径=" + ftpFilePath + fileName);
//				in.close();
//			}
//			/* 注销 */
//			ftpClient.logout();
//		} catch (Exception e) {
//			logger.error("文件批量上传ftp操作失败", e);
//			e.printStackTrace();
//		} finally {
//			if (ftpClient != null && ftpClient.isConnected()) {
//				try {
//					ftpClient.disconnect();
//				} catch (Exception e) {
//					logger.error("文件批量上传ftp操作失败", e);
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//
//	/**
//	 * 
//	 * @Title: getFileContext   
//	 * @Description: 获取ftp服务器上文件的文本   
//	 * @param filePath
//	 * @return: String      
//	 */
//	public static String getFileContext(String filePath) {
//		String context = "";
//		String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
//		boolean flag = NewFtpUploadUtils.downFile(filePath.substring(0, filePath.lastIndexOf("/")), fileName,
//				"\\" + fileName);
//		if (flag) {
//			/*context = FileUploadUtils.getFileContext(Constants.UPLOAD_ROOT + "\\" + fileName);*/
//			context = FileUploadUtils.getFileContext(Utils.getAbsolutePath(fileName, null));
//			FileUploadUtils.deleteFile("\\" + fileName);
//		}
//		return context;
//	}
//
//	/**
//	 * 
//	 * @Title: uploadDirectory   
//	 * @Description: 从本地上传整个文件夹到ftp   
//	 * @param file
//	 * @param ftpServer
//	 * @param userName
//	 * @param password
//	 * @param isDelete      
//	 * @return: void      
//	 */
//	public static void uploadDirectory(File file,String ftpServer,String userName,String password,Boolean isDelete){
//		FTPClient ftpc = new FTPClient();
//		try {
//			int reply;
//
//			ftpc.connect(ftpServer);
//			logger.info("----------连接成功！");
//			ftpc.login(userName, password);
//			reply = ftpc.getReplyCode();
//			if (!FTPReply.isPositiveCompletion(reply)) {
//				logger.info("----------登录失败！");
//				ftpc.disconnect();
//			}
//			String occu = Utils.getPropertyName("render", "design.designPlan.maxrender.designPlan.upload.path", "/MaxRender/");
//			RenderUtil.makeFtpDirectory(ftpc,occu);
//			ftpc.changeWorkingDirectory(occu);
//			upload(file, ftpc);
//			if( isDelete ){
//				FileUploadUtils.deleteDir(file);
//			}
//		}catch(Exception e){
//			logger.error("从本地上传整个文件夹到ftp操作失败", e);	
//			e.printStackTrace();
//		} finally {
//			if ( ftpc != null ) {
//				try {
//					ftpc.disconnect();
//				} catch (Exception e) {
//					logger.error("从本地上传整个文件夹到ftp操作失败", e);	
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//
//	/**
//	 * 
//	 * @Title: upload   
//	 * @Description: 向对应ftp上传文件   
//	 * @param file
//	 * @param ftpc      
//	 * @return: void      
//	 */
//	public static void upload(File file,FTPClient ftpc){
//		try {
//			if( file.isDirectory() ){
//				ftpc.makeDirectory(file.getName());
//				ftpc.changeWorkingDirectory(file.getName());
//				File[] files = file.listFiles();
//				for( int i=0;i<files.length;i++ ){
//					File cFile = files[i];
//					if( cFile.isDirectory() ){
//						upload(cFile,ftpc);
//						ftpc.changeToParentDirectory();
//					}else{
//						FileInputStream input = new FileInputStream(cFile);
//						ftpc.storeFile(cFile.getName(), input);
//						input.close();
//					}
//				}
//			}else{
//				FileInputStream input = new FileInputStream(file);
//				ftpc.storeFile(file.getName(), input);
//				input.close();
//			}
//		}catch (Exception e) {
//			logger.error("向对应ftp上传文件操作失败", e);
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 
//	 * @Title: deleteDir   
//	 * @Description: 删除指定ftp文件目录   
//	 * @param rootPath
//	 * @param dirName
//	 * @param ftpServer
//	 * @param userName
//	 * @param password      
//	 * @return: void      
//	 */
//	public static void deleteDir(String rootPath,String dirName,String ftpServer,String userName,String password){
//		FTPClient ftp = new FTPClient();
//		try {
//			ftp.connect(ftpServer);
//			ftp.login(userName,password);
//			int reply = ftp.getReplyCode();
//			if( !FTPReply.isPositiveCompletion(reply) ){
//				ftp.disconnect();
//			}
//			ftp.changeWorkingDirectory(rootPath);
//			recursiveDelete(dirName,ftp);
//			ftp.changeWorkingDirectory(rootPath);
//			ftp.removeDirectory(dirName);
//
//		} catch (Exception e) {
//			logger.error("删除指定ftp文件目录操作失败", e);
//			e.printStackTrace();
//		}finally {
//			if( ftp != null ){
//				try {
//					ftp.disconnect();
//				} catch (Exception e) {
//					logger.error("删除指定ftp文件目录操作失败", e);
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//
//	/**
//	 * 
//	 * @Title: recursiveDelete   
//	 * @Description: 删除指定ftp文件   
//	 * @param pathName
//	 * @param ftp      
//	 * @return: void      
//	 * @throws
//	 */
//	public static void recursiveDelete(String pathName,FTPClient ftp){
//		try {
//			ftp.changeWorkingDirectory(pathName);
//			FTPFile[] ftpFiles = ftp.listFiles();
//			if( ftpFiles.length > 0 ) {
//				for (int i = 0; i < ftpFiles.length; i++) {
//					FTPFile cFile = ftpFiles[i];
//					if (cFile.isDirectory()) {
//						recursiveDelete(cFile.getName(),ftp);
//						ftp.changeToParentDirectory();
//						ftp.removeDirectory(cFile.getName());
//					} else {
//						ftp.deleteFile(cFile.getName());
//					}
//				}
//			}else{
//				ftp.changeToParentDirectory();
//				ftp.removeDirectory(pathName);
//			}
//		} catch (Exception e) {
//			logger.error("删除指定ftp文件操作失败", e);
//			e.printStackTrace();
//		}
//	}
//}
