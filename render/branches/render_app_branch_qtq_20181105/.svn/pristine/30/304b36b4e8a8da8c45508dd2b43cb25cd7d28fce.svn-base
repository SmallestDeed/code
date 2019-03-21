///**  
// * All rights Reserved, Designed By www.xxxx.com
// * @Title:  NewFileUploadUtils.java   
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
//import java.awt.Container;
//import java.awt.Image;
//import java.awt.MediaTracker;
//import java.awt.Toolkit;
//import java.awt.image.BufferedImage;
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.URL;
//import java.net.URLConnection;
//import java.nio.ByteBuffer;
//import java.nio.channels.FileChannel;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.ResourceBundle;
//
//import javax.imageio.ImageIO;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.nork.common.model.FileModel;
//import com.nork.common.model.LoginUser;
//import com.nork.system.model.ResFile;
//import com.nork.system.model.ResModel;
//import com.nork.system.model.ResPic;
//import com.nork.system.service.ResFileService;
//import com.nork.system.service.ResModelService;
//import com.nork.system.service.ResPicService;
//
///**
// * @ClassName: NewFileUploadUtils
// * @Description:TODO(这里用一句话描述这个类的作用)
// * @author: xxxx
// * @date: 2017年3月13日 下午4:07:54
// * 
// * @Copyright: 2017 www.xxxx.com Inc. All rights reserved.
// *             注意：本内容仅限于xxxx内部传阅，禁止外泄以及用于其他的商业目
// */
//public class NewFileUploadUtils {
//
//	private static Logger logger = LoggerFactory.getLogger(NewFileUploadUtils.class);
//
//	private final static ResourceBundle app = ResourceBundle.getBundle("app");
//	/** 上传通用常量 **/
//	public final static String UPLOADPATHTKEY = "uploadPathKey";
//	public final static String FILE = "file";
//	public final static String DB_FILE_PATH = "dbFilePath";
//	public final static String SERVER_FILE_PATH = "serverFilePath";
//
//	public final static String FTP_UPLOAD_METHOD = Utils.getValue("app.upload.method", "1").trim();
//
//	/*** 获取路径开始部分 */
//	public final static String FTP_UPLOAD_ROOT = Utils.getValue("app.ftp.upload.root", "D:\\app").trim();
//	public final static String SYSTEM_FORMAT = Utils.getValue("app.system.format", "linux").trim();
//	public final static String RESOURCES_URL = Utils.getValue("app.resources.url", "http://localhost/").trim();
//
//	/**
//	 * 
//	 * @Title: saveFile @Description: 文件上传 </p> @param map @return:
//	 * boolean @throws
//	 */
//	public static boolean saveFile(Map<String, Object> map) {
//		boolean flag = false;
//		if (map == null) {
//			return flag;
//		}
//		// 业务对应的文件存储目录key值
//		String uploadPathKey = (String) map.get(UPLOADPATHTKEY);
//		// 获取文件句柄
//		MultipartFile file = (MultipartFile) map.get(FILE);
//		logger.info("file====================>" + file);
//		if (file != null) {
//			// 获取上传文件的文件名
//			String fileName = file.getOriginalFilename();
//			String finalFlieName = "";
//			if ("nameKeep".equals(map.get("opType"))) {
//				finalFlieName = fileName.substring(0, fileName.indexOf(".")) + "_"
//						+ Utils.getCurrentDateTime(Utils.DATETIMESSS) + fileName.substring(fileName.indexOf("."));
//			} else {
//				finalFlieName = Utils.generateRandomDigitString(6) + "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
//						+ fileName.substring(fileName.indexOf("."));
//			}
//			// 获取配置的业务文件目录
//			String filePath = app.getString(uploadPathKey);
//			/* 如果有[code],替换code */
//			if (map.containsKey("code") && StringUtils.isNotBlank((String) map.get("code"))) {
//				filePath = filePath.replace("[code]", (String) map.get("code"));
//			}
//			// 数据库存储目录
//			String dbFilePath = filePath + finalFlieName;
//			// 文件服务器的存储路径
//			String realPath = "";
//			if ("linux".equals(SYSTEM_FORMAT)) {
//				realPath = Tools.getRootPath(dbFilePath, "D:\\app") + dbFilePath;
//			} else {
//				realPath = Tools.getRootPath(dbFilePath, "D:\\app")  + dbFilePath.replace("/", "\\");
//			}
//
//			File sFile = new File(realPath);
//			try {
//
//				if (!sFile.exists()) {
//					sFile.mkdirs();
//				}
//				file.transferTo(sFile);
//				String fname = sFile.getName();
//				long fileSize = sFile.length();
//				String suffix = fname.substring(fname.lastIndexOf("."));
//				String filename = fileName.substring(0, fileName.lastIndexOf("."));
//				if (!StringUtils.isEmpty(uploadPathKey)) {
//					map.put(FileModel.FILE_KEY, uploadPathKey.replace(".upload.path", ""));
//				}
//				if (!StringUtils.isEmpty(filename)) {
//					map.put(FileModel.FILE_NAME, filename);
//				}
//				String fileorgname = fname.substring(0, fname.lastIndexOf("."));
//				map.put("filePath", filePath);
//				map.put("finalFlieName", finalFlieName);
//				map.put("FileOriginalName", fileorgname);// 物理文件名称
//				map.put(FileModel.FILE_ORIGINAL_NAME, fileorgname);
//				map.put("FileSuffix", suffix);// 文件后缀
//				map.put(FileModel.FILE_SUFFIX, suffix);// 文件后缀附加(防止按FileModel.FILE_SUFFIX来取属性)
//				map.put("FileSize", new Long(fileSize).toString());// 文件大小
//				map.put(FileModel.FILE_SIZE, new Long(fileSize).toString());// 文件大小(防止按FileModel.FILE_SIZE来取属性)
//				if (".png".equals(suffix.toLowerCase()) || ".jpg".equals(suffix.toLowerCase())
//						|| ".jpge".equals(suffix.toLowerCase()) || ".jpge".equals(suffix.toLowerCase())
//						|| ".gif".equals(suffix.toLowerCase())) {
//					/* 应对CMYK模式图片上传报错的情况 */
//					BufferedImage bufferedImage = null;
//					try {
//						bufferedImage = ImageIO.read(sFile);
//					} catch (Exception e) {
//						try {
//							ThumbnailConvert tc = new ThumbnailConvert();
//							tc.setCMYK_COMMAND(sFile.getPath());
//							bufferedImage = null;
//							Image image = Toolkit.getDefaultToolkit().getImage(sFile.getPath());
//							MediaTracker mediaTracker = new MediaTracker(new Container());
//							mediaTracker.addImage(bufferedImage, 0);
//							mediaTracker.waitForID(0);
//							bufferedImage = ThumbnailConvert.toBufferedImage(image);
//						} catch (Exception e1) {
//							e1.printStackTrace();
//						}
//					}
//					/* 应对CMYK模式图片上传报错的情况END */
//					int width = bufferedImage.getWidth(); // 图片宽度
//					int height = bufferedImage.getHeight();// 图片高度
//					String format = suffix.replace(".", "");
//					map.put(FileModel.PIC_WEIGHT, new Integer(width).toString());
//					map.put(FileModel.PIC_HEIGHT, new Integer(height).toString());
//					map.put(FileModel.FORMAT, format);
//				}
//
//				map.put(SERVER_FILE_PATH, realPath);
//				map.put(DB_FILE_PATH, dbFilePath);
//
//			} catch (Exception e) {
//				logger.error("文件上传操作失败", e);
//				e.printStackTrace();
//				flag = false;
//			}
//			return true;
//		}
//		return flag;
//	}
//
//	/**
//	 * 
//	 * @Title: saveFile2 @Description: 文件上传 </p> @param map @return:
//	 * boolean @throws
//	 */
//	public static boolean saveFile2(Map<String, Object> map) {
//		boolean flag = false;
//		if (map == null) {
//			return flag;
//		}
//
//		// 业务对应的文件存储目录key值
//		String uploadPathKey = (String) map.get(UPLOADPATHTKEY);
//		// 获取文件句柄
//		MultipartFile file = (MultipartFile) map.get(FILE);
//		logger.info("file=====================>" + file);
//		if (file != null) {
//			String fileName = file.getOriginalFilename();
//			String finalFlieName = "";
//
//			if ("nameKeep".equals(map.get("opType"))) {
//				if (StringUtils.equals("system.sysVersion.file.upload.path", uploadPathKey)) {
//					/* 处理版本完整包文件的命名,规则:SanDu_V{版本号} */
//					finalFlieName = "SanDu_V" + map.get("code") + fileName.substring(fileName.lastIndexOf("."));
//					/* 处理版本完整包文件的命名,规则:SanDu_V{版本号}->end */
//				} else {
//					finalFlieName = fileName.substring(0, fileName.indexOf(".")) + "_"
//							+ Utils.getCurrentDateTime(Utils.DATETIMESSS) + fileName.substring(fileName.indexOf("."));
//				}
//			} else {
//				finalFlieName = Utils.generateRandomDigitString(6) + "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
//						+ fileName.substring(fileName.indexOf("."));
//			}
//
//			// 获取配置的业务文件目录
//			String filePath = Utils.getValue(uploadPathKey, "");
//			/* 如果有[code],替换code */
//			if (map.containsKey("code") && StringUtils.isNotBlank((String) map.get("code"))) {
//				filePath = filePath.replace("[code]", (String) map.get("code"));
//			}
//			// 数据库存储目录
//			String dbFilePath = filePath + finalFlieName;
//			// 文件服务器的存储路径
//			String realPath = "";
//			if ("linux".equals(SYSTEM_FORMAT)) {
//				realPath = Tools.getRootPath(dbFilePath, "D:\\app")  + dbFilePath;
//			} else {
//				realPath = Tools.getRootPath(dbFilePath, "D:\\app")  + dbFilePath.replace("/", "\\");
//			}
//
//			File sFile = new File(realPath);
//			try {
//				if (!sFile.exists()) {
//					sFile.mkdirs();
//				}
//				file.transferTo(sFile);
//				String fname = sFile.getName();
//				long fileSize = sFile.length();
//				String suffix = fname.substring(fname.lastIndexOf("."));
//				String filename = fileName.substring(0, fileName.lastIndexOf("."));
//				if (!StringUtils.isEmpty(uploadPathKey)) {
//					map.put(FileModel.FILE_KEY, uploadPathKey.replace(".upload.path", ""));
//				}
//				if (!StringUtils.isEmpty(filename)) {
//					map.put(FileModel.FILE_NAME, filename);
//				}
//
//				String fileorgname = fname.substring(0, fname.lastIndexOf("."));
//				map.put("filePath", filePath);
//				map.put("finalFlieName", finalFlieName);
//				map.put("FileOriginalName", fileorgname);// 物理文件名称
//				map.put(FileModel.FILE_ORIGINAL_NAME, fileorgname);
//				map.put("FileSuffix", suffix);// 文件后缀
//				map.put(FileModel.FILE_SUFFIX, suffix);// 文件后缀附加(防止按FileModel.FILE_SUFFIX来取属性)
//				map.put("FileSize", new Long(fileSize).toString());// 文件大小
//				map.put(FileModel.FILE_SIZE, new Long(fileSize).toString());// 文件大小(防止按FileModel.FILE_SIZE来取属性)
//				if (".png".equals(suffix.toLowerCase()) || ".jpg".equals(suffix.toLowerCase())
//						|| ".jpge".equals(suffix.toLowerCase()) || ".jpge".equals(suffix.toLowerCase())
//						|| ".gif".equals(suffix.toLowerCase())) {
//					/* 应对CMYK模式图片上传报错的情况 */
//					BufferedImage bufferedImage = null;
//					try {
//						bufferedImage = ImageIO.read(sFile);
//					} catch (Exception e) {
//						try {
//							ThumbnailConvert tc = new ThumbnailConvert();
//							tc.setCMYK_COMMAND(sFile.getPath());
//							bufferedImage = null;
//							Image image = Toolkit.getDefaultToolkit().getImage(sFile.getPath());
//							MediaTracker mediaTracker = new MediaTracker(new Container());
//							mediaTracker.addImage(bufferedImage, 0);
//							mediaTracker.waitForID(0);
//							bufferedImage = ThumbnailConvert.toBufferedImage(image);
//						} catch (Exception e1) {
//							// e1.printStackTrace();
//						}
//					}
//					/* 应对CMYK模式图片上传报错的情况END */
//					int width = bufferedImage.getWidth(); // 图片宽度
//					int height = bufferedImage.getHeight();// 图片高度
//					String format = suffix.replace(".", "");
//					map.put(FileModel.PIC_WEIGHT, new Integer(width).toString());
//					map.put(FileModel.PIC_HEIGHT, new Integer(height).toString());
//					map.put(FileModel.FORMAT, format);
//				}
//
//				map.put(SERVER_FILE_PATH, realPath);
//				map.put(DB_FILE_PATH, dbFilePath);
//
//			} catch (Exception e) {
//				logger.error("文件上传操作失败", e);
//				flag = false;
//				e.printStackTrace();
//			}
//			return true;
//		}
//
//		return flag;
//	}
//
//	/**
//	 * 
//	 * @Title: saveU3dModelFile @Description: 文件上传 </p> @param map @return:
//	 * boolean @throws
//	 */
//	public static boolean saveU3dModelFile(Map<String, Object> map) {
//		boolean flag = false;
//		if (map == null) {
//			return flag;
//		}
//
//		// 业务对应的文件存储目录key值
//		String uploadPathKey = (String) map.get(UPLOADPATHTKEY);
//		// 获取文件句柄
//		MultipartFile file = (MultipartFile) map.get(FILE);
//		logger.info("file===============>" + file);
//		if (file != null) {
//			String fileName = file.getOriginalFilename();
//			String finalFlieName = "";
//			if ("nameKeep".equals(map.get("opType"))) {
//				finalFlieName = fileName.substring(0, fileName.indexOf(".")) + "_"
//						+ Utils.getCurrentDateTime(Utils.DATETIMESSS) + fileName.substring(fileName.indexOf("."));
//			} else {
//				finalFlieName = Utils.generateRandomDigitString(6) + "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
//						+ fileName.substring(fileName.indexOf("."));
//			}
//			// 获取配置的业务文件目录
//			String filePath = app.getString(uploadPathKey);
//			/* 如果有[code],替换code */
//			if (map.containsKey("code") && StringUtils.isNotBlank((String) map.get("code"))) {
//				filePath = filePath.replace("[code]", (String) map.get("code"));
//			}
//			// 数据库存储目录
//			String dbFilePath = filePath + finalFlieName;
//			// 文件服务器的存储路径
//			String realPath = "";
//			if ("linux".equals(SYSTEM_FORMAT)) {
//				realPath = Tools.getRootPath(dbFilePath, "D:\\app")  + dbFilePath;
//			} else {
//				realPath = Tools.getRootPath(dbFilePath, "D:\\app")  + dbFilePath.replace("/", "\\");
//			}
//			File sFile = new File(realPath);
//			try {
//				if (!sFile.exists()) {
//					sFile.mkdirs();
//				}
//				file.transferTo(sFile);
//				String fname = sFile.getName();
//				long fileSize = sFile.length();
//				String suffix = fname.substring(fname.lastIndexOf("."));
//				String filename = fileName.substring(0, fileName.lastIndexOf("."));
//				if (!StringUtils.isEmpty(uploadPathKey)) {
//					map.put(FileModel.FILE_KEY, uploadPathKey.replace(".upload.path", ""));
//				}
//				if (!StringUtils.isEmpty(filename)) {
//					map.put(FileModel.FILE_NAME, filename);
//				}
//
//				String fileorgname = fname.substring(0, fname.lastIndexOf("."));
//				map.put("filePath", filePath);
//				map.put("finalFlieName", finalFlieName);
//				map.put("FileOriginalName", fileorgname);// 物理文件名称
//				map.put(FileModel.FILE_ORIGINAL_NAME, fileorgname);
//				map.put("FileSuffix", suffix);// 文件后缀
//				map.put(FileModel.FILE_SUFFIX, suffix);// 文件后缀附加(防止按FileModel.FILE_SUFFIX来取属性)
//				map.put("FileSize", new Long(fileSize).toString());// 文件大小
//				map.put(FileModel.FILE_SIZE, new Long(fileSize).toString());// 文件大小(防止按FileModel.FILE_SIZE来取属性)
//				if (".png".equals(suffix.toLowerCase()) || ".jpg".equals(suffix.toLowerCase())
//						|| ".jpge".equals(suffix.toLowerCase()) || ".jpge".equals(suffix.toLowerCase())
//						|| ".gif".equals(suffix.toLowerCase())) {
//					/* 应对CMYK模式图片上传报错的情况 */
//					BufferedImage bufferedImage = null;
//					try {
//						bufferedImage = ImageIO.read(sFile);
//					} catch (Exception e) {
//						try {
//							ThumbnailConvert tc = new ThumbnailConvert();
//							tc.setCMYK_COMMAND(sFile.getPath());
//							bufferedImage = null;
//							Image image = Toolkit.getDefaultToolkit().getImage(sFile.getPath());
//							MediaTracker mediaTracker = new MediaTracker(new Container());
//							mediaTracker.addImage(bufferedImage, 0);
//							mediaTracker.waitForID(0);
//							bufferedImage = ThumbnailConvert.toBufferedImage(image);
//						} catch (Exception e1) {
//							logger.error("文件上传操作失败", e1);
//							flag = false;
//							e1.printStackTrace();
//						}
//					}
//					/* 应对CMYK模式图片上传报错的情况END */
//					int width = bufferedImage.getWidth(); // 图片宽度
//					int height = bufferedImage.getHeight();// 图片高度
//					String format = suffix.replace(".", "");
//					map.put(FileModel.PIC_WEIGHT, new Integer(width).toString());
//					map.put(FileModel.PIC_HEIGHT, new Integer(height).toString());
//					map.put(FileModel.FORMAT, format);
//				}
//
//				map.put(SERVER_FILE_PATH, realPath);
//				map.put(DB_FILE_PATH, dbFilePath);
//
//				// 仅支持ftp服务器上传
//				if (Utils.getIntValue(NewFileUploadUtils.FTP_UPLOAD_METHOD) == 2) {
//					flag = FtpUploadUtils.uploadFile(finalFlieName, realPath, filePath);
//					if (flag) {
//						// 删除本地
//						NewFileUploadUtils.deleteFile(dbFilePath);
//					} else {
//						return false;
//					}
//				}
//				// 3 本地和ftp同时上传(默认是本地上传)
//				if (Utils.getIntValue(NewFileUploadUtils.FTP_UPLOAD_METHOD) == 3) {
//					flag = FtpUploadUtils.uploadFile(finalFlieName, realPath, filePath);
//					if (!flag) {
//						return false;
//					}
//				}
//			} catch (Exception e) {
//				logger.error("上传文件操作失败", e);
//				flag = false;
//				e.printStackTrace();
//			}
//			return true;
//		}
//		return flag;
//	}
//
//	/**
//	 * 
//	 * @Title: deleteDir @Description: 递归删除目录下的所有文件及子目录下所有文件 @param dir
//	 * 将要删除的文件目录 @return: boolean @throws
//	 */
//	public static boolean deleteDir(File dir) {
//		if (dir.isDirectory()) {
//			String[] children = dir.list();
//			for (int i = 0; i < children.length; i++) {
//				boolean success = deleteDir(new File(dir, children[i]));
//				if (!success) {
//					return false;
//				}
//			}
//		}
//		return dir.delete();
//	}
//
//	/**
//	 * 
//	 * @Title: getMap
//	 * @Description: 将某个文件或图片等转换为文件资源对象,自动读取资源大小等信息
//	 * @param sFile
//	 *            文件路径
//	 * @param uploadPathKey
//	 *            文件上传配置key
//	 * @param isExistTimestamp
//	 *            文件名称是否存在时间戳
//	 * @return: Map
//	 */
//	public static Map<String, Object> getMap(File sFile, String uploadPathKey, boolean isExistTimestamp) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		if (sFile.isDirectory() || !sFile.exists() || StringUtils.isEmpty(uploadPathKey)) {
//			return map;
//		}
//		String fname = sFile.getName();
//		long fileSize = sFile.length();
//		String suffix = fname.substring(fname.lastIndexOf("."));
//
//		String filename = fname;
//		if (isExistTimestamp) {
//			if (fname.contains("_"))
//				filename = fname.substring(0, fname.lastIndexOf("_"));
//		} else {
//			filename = fname.substring(0, fname.lastIndexOf("."));
//		}
//
//		if (!StringUtils.isEmpty(uploadPathKey)) {
//			map.put(FileModel.FILE_KEY, uploadPathKey.replace(".upload.path", ""));
//		}
//		if (!StringUtils.isEmpty(filename)) {
//			map.put(FileModel.FILE_NAME, filename);
//		}
//
//		String fileorgname = fname.substring(0, fname.lastIndexOf("."));
//		map.put(FileModel.FILE_ORIGINAL_NAME, fileorgname);// 物理文件名称
//		map.put(FileModel.FILE_SUFFIX, suffix);// 文件后缀
//		map.put(FileModel.FILE_SIZE, new Long(fileSize).toString());// 文件大小
//		if (".png".equals(suffix.toLowerCase()) || ".jpg".equals(suffix.toLowerCase())
//				|| ".jpge".equals(suffix.toLowerCase()) || ".jpge".equals(suffix.toLowerCase())
//				|| ".gif".equals(suffix.toLowerCase())) {
//			BufferedImage bufferedImage = null;
//			try {
//				bufferedImage = ImageIO.read(sFile);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			int width = bufferedImage == null ? -1 : bufferedImage.getWidth(); // 图片宽度
//			int height = bufferedImage == null ? -1 : bufferedImage.getHeight();// 图片高度
//			String format = suffix.replace(".", "");
//			map.put(FileModel.PIC_WEIGHT, new Integer(width).toString());
//			map.put(FileModel.PIC_HEIGHT, new Integer(height).toString());
//			map.put(FileModel.FORMAT, format);
//		}
//
//		map.put(SERVER_FILE_PATH, sFile.getAbsolutePath());
//		String dbFilePath = "";
//		if ("linux".equals(SYSTEM_FORMAT)) {
//			dbFilePath = sFile.getAbsolutePath().replace(Tools.getRootPath(dbFilePath, "D:\\app") , "");
//		} else {
//			dbFilePath = sFile.getAbsolutePath().replace(Tools.getRootPath(dbFilePath, "D:\\app") , "").replace("\\", "/");
//		}
//		map.put(DB_FILE_PATH, dbFilePath);
//		map.put(FileModel.FILE_PATH, dbFilePath);
//		return map;
//	}
//
//	public static void main(String[] args) {
//		String param = NewFileUploadUtils
//				.getFileContext("F:\\MaxRender\\zhangwj\\C07_0022_001_781406_20160613160612105228\\Data.txt");
//		RenderUtil.createDataFile(param, "F:\\Data.txt");
//	}
//
//	/**
//	 * 
//	 * @Title: download
//	 * @Description: 文件下载
//	 *               </p>
//	 * @param request
//	 * @param response
//	 * @param storeName
//	 * @param contentType
//	 * @param realName
//	 * @param: @throws
//	 *             Exception
//	 * @return: void
//	 */
//	public static void download(HttpServletRequest request, HttpServletResponse response, String storeName,
//			String contentType, String realName) throws Exception {
//		response.setContentType("text/html;charset=UTF-8;application/octet-stream");
//		if (Utils.getIntValue(FTP_UPLOAD_METHOD) == 1) {
//			request.setCharacterEncoding("UTF-8");
//			BufferedInputStream bis = null;
//			BufferedOutputStream bos = null;
//
//			String ctxPath = "";
//			if (Utils.getIntValue(FTP_UPLOAD_METHOD) == 1) {
//				ctxPath = Tools.getRootPath(storeName, "D:\\app") ;
//			} else {
//				ctxPath = FTP_UPLOAD_ROOT;
//			}
//			String downLoadPath = ctxPath + storeName;
//			logger.info("downLoadPath==================>", downLoadPath);
//			long fileLength = new File(downLoadPath).length();
//
//			response.setHeader("Content-disposition",
//					"attachment; filename=" + new String(realName.getBytes("GBK"), "ISO8859-1"));
//			response.setHeader("Content-Length", String.valueOf(fileLength));
//
//			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
//			bos = new BufferedOutputStream(response.getOutputStream());
//			byte[] buff = new byte[2048];
//			int bytesRead;
//			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
//				bos.write(buff, 0, bytesRead);
//			}
//			bis.close();
//			bos.close();
//		} else {
//			NewFtpUploadUtils.downloadFileFtp(storeName, response);
//		}
//	}
//
//	/**
//	 * 
//	 * @Title: fileCopy   
//	 * @Description: 文件复制 </p>   
//	 * @param resourcesPath
//	 * @param targetPath
//	 * @return: boolean      
//	 */
//	public static boolean fileCopy(File resourcesPath, File targetPath) {
//
//		boolean flag = false;
//		if (resourcesPath == null && targetPath == null) {
//			return flag;
//		}
//		if (!resourcesPath.exists()) {
//			logger.info("file is not exists = " + resourcesPath.getPath());
//			return false;
//		}
//		if (!targetPath.getParent().isEmpty()) {
//			try {
//				targetPath.getParentFile().mkdirs();
//			} catch (Exception e) {
//				logger.error("文件复制操作失败", e);
//				e.printStackTrace();
//			}
//		}
//		if (!targetPath.exists()) {
//			try {
//				targetPath.createNewFile();
//			} catch (Exception e) {
//				logger.error("文件复制操作失败", e);
//				e.printStackTrace();
//			}
//		}
//		FileInputStream fi = null;
//		FileOutputStream fo = null;
//		FileChannel in = null;
//		FileChannel out = null;
//		try {
//			fi = new FileInputStream(resourcesPath);
//			fo = new FileOutputStream(targetPath);
//			in = fi.getChannel();// 得到对应的文件通道
//			out = fo.getChannel();// 得到对应的文件通道
//			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
//		} catch (Exception e) {
//			logger.error("文件复制操作失败", e);
//			e.printStackTrace();
//			flag = false;
//		} finally {
//			try {
//				if (fi != null) {
//					fi.close();
//				}
//				if (in != null) {
//					in.close();
//				}
//				if (fo != null) {
//					fo.close();
//				}
//				if (out != null) {
//					out.close();
//				}
//
//			} catch (Exception e) {
//				logger.error("文件复制操作失败", e);
//				e.printStackTrace();
//			}
//		}
//		return true;
//	}
//
//	/**
//	 * 
//	 * @Title: fileCopy   
//	 * @Description: 文件复制   
//	 * @param resourcesPath
//	 * 			源文件
//	 * @param targetPath
//	 * 			复制到的新文件
//	 * @return: boolean      
//	 */
//	public static boolean fileCopy(String resourcesPath, String targetPath) {
//		File f1 = new File(resourcesPath);
//		File f2 = new File(targetPath);
//		return fileCopy(f1, f2);
//	}
//
//	/**
//	 * 
//	 * @Title: copyfile   
//	 * @Description: 文件复制   
//	 * @param resourcesPath
//	 * 			源文件
//	 * @param targetPath
//	 * 			复制到的新文件
//	 * @return: String      
//	 * @throws
//	 */
//	public static String copyfile(String resourcesPath, String targetPath) {
//		File f1 = new File(resourcesPath);
//		File f2 = new File(targetPath);
//		if (fileCopy(f1, f2)) {
//			return targetPath;
//		}
//		return "";
//	}
//
//	/**
//	 * 
//	 * @Title: copyFile   
//	 * @Description: 从某个资源中拷贝生成一个新的资源，并生成新的id   
//	 * @param resId
//	 * 			源资源主健
//	 * @param resType
//	 * 			资源类型(file，model，pic)
//	 * @param fileKey
//	 * 			新生成文件系统key标示
//	 * @param bussniess
//	 * 			该文件关联的业务id
//	 * @param request
//	 * @param baseService
//	 * @return: Integer      
//	 */
//	public static Integer copyFile(String resId, String resType, String fileKey, Integer bussniess,
//			HttpServletRequest request, Object baseService) {
//		logger.info("resId=" + resId + ";resType=" + resType + ";fileKey=" + fileKey + ";bussniess=" + bussniess + ";");
//		String resFilePath = "";
//		Integer newResId = -1;
//
//		ResFile resFile = new ResFile();
//		ResModel resModel = new ResModel();
//		ResPic resPic = new ResPic();
//		if (!StringUtils.isEmpty(resId)) {
//			if ("file".equals(resType)) {
//				ResFile file = ((ResFileService) baseService).get(new Integer(resId));
//				if (file != null && !StringUtils.isEmpty(file.getFilePath())) {
//					resFilePath = file.getFilePath();
//					resFile = file.copy();
//				}
//			}
//			if ("model".equals(resType)) {
//				ResModel model = ((ResModelService) baseService).get(new Integer(resId));
//				if (model != null && !StringUtils.isEmpty(model.getModelPath())) {
//					resFilePath = model.getModelPath();
//					resModel = model.copy();
//				}
//
//			}
//			if ("pic".equals(resType)) {
//				ResPic pic = ((ResPicService) baseService).get(new Integer(resId));
//				if (pic != null && !StringUtils.isEmpty(pic.getPicPath())) {
//					resFilePath = pic.getPicPath();
//					resPic = pic.copy();
//				}
//
//			}
//			if (!StringUtils.isEmpty(resFilePath)) {
//
//				String srcPath = Tools.getRootPath(resFilePath, "D:\\app")  + resFilePath.replace("/", "\\");
//				if ("linux".equals(NewFileUploadUtils.SYSTEM_FORMAT)) {
//					srcPath =Tools.getRootPath(resFilePath, "D:\\app")  + resFilePath;
//				}
//
//				File resourcesFile = new File(srcPath);
//				String resourcesName = resFilePath.substring(resFilePath.replace("/", "\\").lastIndexOf("\\") + 1,
//						resFilePath.length());
//				if ("linux".equals(NewFileUploadUtils.SYSTEM_FORMAT)) {
//					resourcesName = resFilePath.substring(resFilePath.lastIndexOf("/") + 1, resFilePath.length());
//				}
//
//				String newPath = app.getString(fileKey + ".upload.path");
//				String targetName = newPath
//						+ resourcesName.substring(resourcesName.lastIndexOf("/") + 1, resourcesName.lastIndexOf("_"))
//						+ "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
//						+ resourcesName.substring(resourcesName.indexOf("."));
//
//				String targetPath = Tools.getRootPath(resFilePath, "D:\\app") + targetName.replace("/", "\\");
//				if ("linux".equals(NewFileUploadUtils.SYSTEM_FORMAT)) {
//					targetPath =Tools.getRootPath(resFilePath, "D:\\app")  + targetName;
//				}
//
//				File targetFile = new File(targetPath);
//
//				if (!targetFile.getParentFile().exists()) {
//					targetFile.getParentFile().mkdirs();
//				}
//
//				if (resourcesFile.isFile() && resourcesFile.exists()) { // 判断文件是否存在
//					boolean flag = false;
//					flag = NewFileUploadUtils.fileCopy(resourcesFile, targetFile);
//					if (flag) {
//						if ("file".equals(resType)) {
//							resFile.setSysCode(null);
//							resFile.setFilePath(targetName);
//							resFile.setFileKey(fileKey);
//							resFile.setBusinessId(bussniess);
//							sysSave(resFile, request);
//							newResId = ((ResFileService) baseService).add(resFile);
//						}
//						if ("model".equals(resType)) {
//							resModel.setSysCode(null);
//							resModel.setModelPath(targetName);
//							resModel.setFileKey(fileKey);
//							resModel.setBusinessId(bussniess);
//							sysSave(resModel, request);
//							newResId = ((ResModelService) baseService).add(resModel);
//						}
//						if ("pic".equals(resType)) {
//							resPic.setPicPath(targetName);
//							resPic.setFileKey(fileKey);
//							resPic.setSysCode(null);
//							resPic.setBusinessId(bussniess);
//							sysSave(resPic, request);
//							newResId = ((ResPicService) baseService).add(resPic);
//						}
//					}
//				}
//			}
//		}
//		return newResId;
//	}
//
//	/**
//	 * 
//	 * @Title: sysSave   
//	 * @Description: ResFile自动存储系统字段   
//	 * @param model
//	 * @param request      
//	 * @return: void      
//	 */
//	private static void sysSave(ResFile model, HttpServletRequest request) {
//		if (model != null) {
//			LoginUser loginUser = new LoginUser();
//			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
//				loginUser.setLoginName("nologin");
//			} else {
//				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
//			}
//
//			if (model.getId() == null) {
//				model.setGmtCreate(new Date());
//				model.setCreator(loginUser.getLoginName());
//				model.setIsDeleted(0);
//				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
//					model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
//				}
//			}
//
//			model.setGmtModified(new Date());
//			model.setModifier(loginUser.getLoginName());
//		}
//	}
//
//	/**
//	 * 
//	 * @Title: sysSave   
//	 * @Description: ResPic自动存储系统字段   
//	 * @param model
//	 * @param request      
//	 * @return: void      
//	 */
//	private static void sysSave(ResPic model, HttpServletRequest request) {
//		if (model != null) {
//			LoginUser loginUser = new LoginUser();
//			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
//				loginUser.setLoginName("nologin");
//			} else {
//				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
//			}
//			if (model.getId() == null) {
//				model.setGmtCreate(new Date());
//				model.setCreator(loginUser.getLoginName());
//				model.setIsDeleted(0);
//				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
//					model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
//				}
//			}
//			model.setGmtModified(new Date());
//			model.setModifier(loginUser.getLoginName());
//		}
//	}
//
//	/**
//	 * 
//	 * @Title: sysSave   
//	 * @Description: ResModel自动存储系统字段   
//	 * @param model
//	 * @param request      
//	 * @return: void      
//	 */
//	private static void sysSave(ResModel model, HttpServletRequest request) {
//		if (model != null) {
//			LoginUser loginUser = new LoginUser();
//			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
//				loginUser.setLoginName("nologin");
//			} else {
//				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
//			}
//			if (model.getId() == null) {
//				model.setGmtCreate(new Date());
//				model.setCreator(loginUser.getLoginName());
//				model.setIsDeleted(0);
//				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
//					model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
//				}
//			}
//
//			model.setGmtModified(new Date());
//			model.setModifier(loginUser.getLoginName());
//		}
//	}
//
//	/**
//	 * 
//	 * @Title: ZipCompressor   
//	 * @Description:  将一个文件或文件夹压缩成zip source 文件或文件夹物理路径 zipFilePath 压缩文件的目标文件路径   
//	 * @param source
//	 * @param zipFilePath
//	 * @return: boolean      
//	 */
//	public static boolean ZipCompressor(String source, String zipFilePath) {
//		try {
//			ZipCompressor zc = new ZipCompressor(zipFilePath);
//			File zipFile = new File(zipFilePath);
//			if (!zipFile.getParentFile().exists()) {
//				zipFile.getParentFile().mkdirs();
//			}
//			zc.compress(source);
//			return true;
//		} catch (Exception e) {
//			logger.error("将一个文件或文件夹压缩成zip source 文件或文件夹物理路径 zipFilePath 压缩文件的目标文件路径 操作失败", e);
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	/**
//	 * 
//	 * @Title: ZipDeCompress   
//	 * @Description: 将zip或rar解压到指定目录中 sourceFile zip物理文件路径 destDir 目标物理目录   
//	 * @param sourceFile
//	 * @param destDir
//	 * @return: boolean      
//	 */
//	public static boolean ZipDeCompress(String sourceFile, String destDir) {
//		try {
//			DeCompressUtil.deCompress(sourceFile, destDir, "");
//			return true;
//		} catch (Exception e) {
//			logger.error("将zip或rar解压到指定目录中 sourceFile zip物理文件路径 destDir 目标物理目录操作失败", e);
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	/**
//	 * 
//	 * @Title: copyFile2   
//	 * @Description: 拷贝文件   
//	 * @param url1
//	 * @param url2
//	 * @throws IOException      
//	 * @return: boolean      
//	 */
//	public static boolean copyFile2(String url1, String url2) {
//		File f1 = new File(url1);
//		File f2 = new File(url2);
//		File folder = new File(url2.substring(0, url2.lastIndexOf("/")));// 拷贝文件路径文件夹
//		if (folder.exists() && folder.isDirectory()) {
//			logger.info("------未找到文件,path:" + url1);
//		} else {
//			folder.mkdirs();
//		}
//		boolean j = true;
//		FileInputStream in = null;
//		FileOutputStream out = null;
//		FileChannel inC = null;
//		FileChannel outC = null;
//		try {
//			int length = 2097152;
//			in = new FileInputStream(f1);
//			out = new FileOutputStream(f2);
//			inC = in.getChannel();
//			outC = out.getChannel();
//			ByteBuffer b = null;
//			while (true) {
//				if (inC.position() == inC.size()) {
//					inC.close();
//					outC.close();
//					break;
//				}
//				if ((inC.size() - inC.position()) < length) {
//					length = (int) (inC.size() - inC.position());
//				} else
//					length = 2097152;
//				b = ByteBuffer.allocateDirect(length);
//				inC.read(b);
//				b.flip();
//				outC.write(b);
//				outC.force(false);
//			}
//		} catch (Exception e) {
//			logger.error("拷贝文件 操作失败", e);
//			e.printStackTrace();
//			j = false;
//		} finally {
//			try{
//				if (in != null) {
//					in.close();
//				}
//				if (out != null) {
//					out.close();
//				}
//				if (inC != null) {
//					inC.close();
//				}
//				if (outC != null) {
//					outC.close();
//				}
//			}catch(Exception e){
//				logger.error("拷贝文件 操作失败", e);
//				e.printStackTrace();
//			}
//		}
//		return j;
//	}
//
//	/**
//	 * 
//	 * @Title: deleteFile   
//	 * @Description: 删除物理文件   
//	 * @param path      
//	 * @return: void      
//	 * @throws
//	 */
//	public static void deleteFile(String path ) {
//		File file = new File(Tools.getRootPath(path,"") + path);
//		if (file.exists()) {
//			//file.delete();
//			FileUploadUtils.deleteFile(path);
//		}
//	}
//
//	public static String downloadFile(String serverFilePath, String filePath) throws Exception {
//		String localFilePath = null;
//		if (SYSTEM_FORMAT.equals("linux")) {
//			filePath = filePath.replace("\\", "/");
//			serverFilePath = serverFilePath.replace("\\", "/");
//		}
//		File dirFile = new File(filePath);
//
//		if (!dirFile.exists()) {// 文件路径不存在时，自动创建目录
//			dirFile.mkdirs();
//		}
//		// 本地文件地址
//		localFilePath = filePath + serverFilePath.substring(serverFilePath.lastIndexOf("/"));
//		File localFile = new File(localFilePath);
//		logger.info("--------" + localFilePath);
//		if (localFile.exists())
//			return localFilePath;
//		URL theURL = new URL(RESOURCES_URL + serverFilePath);
//		// 从服务器上获取图片并保存
//		URLConnection connection = theURL.openConnection();
//		InputStream in = connection.getInputStream();
//		FileOutputStream os = new FileOutputStream(localFilePath);
//		byte[] buffer = new byte[2 * 1024 * 1024];
//		int read;
//		while ((read = in.read(buffer)) > 0) {
//			os.write(buffer, 0, read);
//		}
//		os.close();
//		in.close();
//		return localFilePath;
//	}
//
//	/**
//	 * 
//	 * @Title: getFileContext   
//	 * @Description: 读取文本文件中的文本信息   
//	 * @param filePath
//	 * @return: String      
//	 * @throws
//	 */
//	public static String getFileContext(String filePath) {
//		StringBuffer sb = new StringBuffer();
//		try {
//			FileReader fr = new FileReader(filePath);
//			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
//			String str = "";
//			while ((str = br.readLine()) != null) {
//				sb.append(str);
//				sb.append("\r\n");
//			}
//			br.close();
//			fr.close();
//		} catch (Exception e) {
//			logger.error("读取文本文件中的文本信息操作失败", e);
//			e.printStackTrace();
//		}
//		return sb.toString();
//	}
//
//	/**
//	 * 
//	 * @Title: writeTxtFile   
//	 * @Description: 创建txt文件   
//	 * @param filePath
//	 * @param context
//	 * @return: boolean      
//	 */
//	public static boolean writeTxtFile(String filePath, String context) {
//		if (StringUtils.isBlank(filePath)) {
//			return false;
//		}
//		File file = new File(filePath);
//		if (!file.getParentFile().exists()) {
//			file.getParentFile().mkdirs();
//		}
//		if (!file.exists()) {
//			try {
//				file.createNewFile();
//			} catch (Exception e) {
//				logger.error("创建txt文件操作失败", e);
//				e.printStackTrace();
//			}
//		}
//		OutputStreamWriter ow = null;
//		try {
//			ow = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
//			ow.write(context);
//			ow.close();
//		} catch (Exception e) {
//			logger.error("创建txt文件操作失败", e);
//			e.printStackTrace();
//			try {
//				if (ow != null) {
//					ow.close();
//				}
//				return false;
//			} catch (Exception e1) {
//				logger.error("创建txt文件操作失败", e1);
//				e.printStackTrace();
//				return false;
//			}
//		}
//		return true;
//	}
//}
