package com.nork.common.util;

import java.awt.Container;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.nork.aes.util.FileEncrypt;
import com.nork.common.model.FileModel;
import com.nork.common.model.LoginUser;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;


public class FileUploadUtils {

	private static Logger logger = Logger.getLogger(FileUploadUtils.class);

	/*** 获取配置文件 tmg.properties */
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	private final static ResourceBundle res = ResourceBundle.getBundle("config/res");
	/** 上传通用常量 **/
	public final static String UPLOADPATHTKEY = "uploadPathKey";
	public final static String FILE = "file";
	public final static String DB_FILE_PATH = "dbFilePath";
	public final static String  SERVER_FILE_PATH = "serverFilePath";
	
	public final static String FTP_UPLOAD_METHOD = Utils.getValue("app.upload.method", "1").trim();

	/*** 获取路径开始部分 */
	public final static String UPLOAD_ROOT = Utils.getValue("app.upload.root", "D:\\app").trim();
	public final static String FTP_UPLOAD_ROOT = Utils.getValue("app.ftp.upload.root", "D:\\app").trim();
	public final static String SYSTEM_FORMAT = Utils.getValue("app.system.format", "linux").trim();
	public final static String RESOURCES_URL = Utils.getValue("app.resources.url", "http://localhost/").trim();
	
	/*** 文件上传 */
	public static boolean saveFile(Map map) {
		boolean flag = false;
		if (map == null) {
			return flag;
		}

		// 业务对应的文件存储目录key值
		String uploadPathKey = (String) map.get(UPLOADPATHTKEY);
		// 获取文件句柄
		MultipartFile file = (MultipartFile) map.get(FILE);
		
		logger.info("file=" + file);
		if (file != null) {
			// 获取上传文件的文件名
			String fileName = file.getOriginalFilename();
			if(StringUtils.isBlank(fileName)){
				logger.debug("------识别待上传的文件名为null,默认为.png");
				fileName = ".png";
			}
//			String finalFlieName = fileName.substring(0, fileName.indexOf("."))
//					+ "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
//					+ fileName.substring(fileName.indexOf("."));
			String finalFlieName = "";
			if( "nameKeep".equals(map.get("opType")) ){
				finalFlieName = fileName.substring(0, fileName.indexOf("."))
						+ "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
						+ "_" + fileName;
			}else{
				finalFlieName = Utils.generateRandomDigitString(6)
						+ "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
						+ "_" + fileName;
			}
			// 获取配置的业务文件目录
			//String filePath = app.getString(uploadPathKey);
			String filePath = res.getString(uploadPathKey);
			filePath = Utils.replaceDate(filePath);
			/*如果有[code],替换code*/
			if(map.containsKey("code")&&StringUtils.isNotBlank((String)map.get("code"))){
				filePath=filePath.replace("[code]", (String)map.get("code"));
			}
			// 数据库存储目录
			String dbFilePath = filePath + finalFlieName;
			logger.debug("------dbFilePath-----------------------------"+dbFilePath);
			// 文件服务器的存储路径
			String realPath = Utils.dealWithPath(Utils.getAbsolutePath(dbFilePath, null), null);
			/*if("linux".equals(SYSTEM_FORMAT)){
				 realPath = UPLOAD_ROOT + dbFilePath;
			}else{
				 realPath = UPLOAD_ROOT + dbFilePath.replace("/", "\\");
			}*/
			
//			String findHead = UPLOAD_ROOT.substring(0,UPLOAD_ROOT.indexOf(":"));
			File sFile = new File(realPath);
			// 写入上传文件
			try {
//				if(!"ftp".equals(findHead)){
//					// 创建服务器文件存储目录
//					if (!sFile.exists()) {
//						sFile.mkdirs();
//					}
//					file.transferTo(sFile);
//				}else{
//					flag = FtpUploadUtils.ftpUploadFile(filePath,file,finalFlieName); 
//					if(flag){
//						map.put(SERVER_FILE_PATH, realPath);
//						logger.info("-------------"+realPath);
//						map.put(DB_FILE_PATH, dbFilePath);
//						return true;
//					}
//				}
				
				// 创建服务器文件存储目录
				logger.info("sFile-------------------"+sFile.getPath()+";"+!sFile.exists()+";"+!sFile.getParentFile().exists());
				if( !sFile.exists() ){
					logger.info("sFile-------------------"+sFile);
					if( !sFile.getParentFile().exists() ){
						sFile.getParentFile().mkdirs();
					}
				}
//				if (!sFile.exists()) {
//					sFile.mkdirs();
//				}
				file.transferTo(sFile);
				String fname = sFile.getName();
				long fileSize = sFile.length();
				String suffix = fname.substring(fname.lastIndexOf("."));
				String filename = fileName.substring(0, fileName.lastIndexOf("."));
				if(!StringUtils.isEmpty(uploadPathKey)){
				    map.put(FileModel.FILE_KEY,uploadPathKey.replace(".upload.path", ""));
				}
				if (!StringUtils.isEmpty(filename)) {
					map.put(FileModel.FILE_NAME,filename);
				}
				String fileorgname = fname.substring(0,fname.lastIndexOf("."));
				map.put("filePath", filePath);
				map.put("finalFlieName", finalFlieName);
				map.put("FileOriginalName",fileorgname);// 物理文件名称
				map.put(FileModel.FILE_ORIGINAL_NAME,fileorgname);
				map.put("FileSuffix",suffix);// 文件后缀
				map.put(FileModel.FILE_SUFFIX, suffix);//文件后缀附加(防止按FileModel.FILE_SUFFIX来取属性)
				map.put("FileSize",new Long(fileSize).toString());// 文件大小
				map.put(FileModel.FILE_SIZE,new Long(fileSize).toString());//文件大小(防止按FileModel.FILE_SIZE来取属性)
				if (".png".equals(suffix.toLowerCase())
						|| ".jpg".equals(suffix.toLowerCase())
						|| ".jpge".equals(suffix.toLowerCase())
						|| ".jpge".equals(suffix.toLowerCase())
						|| ".gif".equals(suffix.toLowerCase())) {
//					BufferedImage bufferedImage=ImageIO.read(sFile);
//					int width = bufferedImage.getWidth(); // 图片宽度
//					int height = bufferedImage.getHeight();// 图片高度
					/*应对CMYK模式图片上传报错的情况*/
					BufferedImage bufferedImage=null;
					try{
						bufferedImage = ImageIO.read(sFile);
					}catch(Exception e){
						try {
							ThumbnailConvert tc = new ThumbnailConvert();
							tc.setCMYK_COMMAND(sFile.getPath());
							bufferedImage = null;
							Image image = Toolkit.getDefaultToolkit().getImage(sFile.getPath());
							MediaTracker mediaTracker = new MediaTracker(new Container());
							mediaTracker.addImage(bufferedImage, 0);
							mediaTracker.waitForID(0);
							bufferedImage=ThumbnailConvert.toBufferedImage(image);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					/*应对CMYK模式图片上传报错的情况END*/
					int width = bufferedImage.getWidth(); // 图片宽度
					int height = bufferedImage.getHeight();// 图片高度
					String format = suffix.replace(".", "");
					map.put(FileModel.PIC_WEIGHT,new Integer(width).toString());
					map.put(FileModel.PIC_HEIGHT,new Integer(height).toString());
					map.put(FileModel.FORMAT,format);
				}

				map.put(SERVER_FILE_PATH, realPath);
				map.put(DB_FILE_PATH, dbFilePath);
				
			} catch (Exception e) {
				flag = false;
				logger.error(e.getMessage());
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return flag;
	}
	
	/*** 文件上传 */
	public static boolean saveFile2(Map map) {
		boolean flag = false;
		if (map == null) {
			return flag;
		}

		// 业务对应的文件存储目录key值
		String uploadPathKey = (String) map.get(UPLOADPATHTKEY);
		// 获取文件句柄
		MultipartFile file = (MultipartFile) map.get(FILE);
		logger.info("file=" + file);
		if (file != null) {
			// 获取上传文件的文件名
			String fileName = file.getOriginalFilename();
//			String finalFlieName = fileName.substring(0, fileName.indexOf("."))
//					+ "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
//					+ fileName.substring(fileName.indexOf("."));
			String finalFlieName = "";
			
			if( "nameKeep".equals(map.get("opType")) ){
				if(StringUtils.equals("system.sysVersion.file.upload.path", uploadPathKey)){
					/*处理版本完整包文件的命名,规则:SanDu_V{版本号}*/
					finalFlieName="SanDu_V"+map.get("code")+fileName.substring(fileName.lastIndexOf("."));
					/*处理版本完整包文件的命名,规则:SanDu_V{版本号}->end*/
				}else{
					finalFlieName = fileName.substring(0, fileName.indexOf("."))
							+ "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
							+ fileName.substring(fileName.indexOf("."));
				}
			}else{
				finalFlieName = Utils.generateRandomDigitString(6)
				+ "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
				+ fileName.substring(fileName.indexOf("."));
			}

			// 获取配置的业务文件目录
			 
//			String filePath=Utils.getValue(uploadPathKey, "");
			/*如果有[code],替换code*/
//			if(map.containsKey("code")&&StringUtils.isNotBlank((String)map.get("code"))){
//				filePath=filePath.replace("[code]", (String)map.get("code"));
//			}
			String filePath = Utils.getPropertyName("config/res", uploadPathKey, "").trim();
			filePath = Utils.replaceDate(filePath);
			
			// 数据库存储目录
			String dbFilePath = filePath + finalFlieName;
			// 文件服务器的存储路径
			String realPath = Utils.dealWithPath(Utils.getAbsolutePath(dbFilePath, null), null);
			/*if("linux".equals(SYSTEM_FORMAT)){
				 realPath = UPLOAD_ROOT + dbFilePath;
			}else{
				 realPath = UPLOAD_ROOT + dbFilePath.replace("/", "\\");
			}*/
			
//			String findHead = UPLOAD_ROOT.substring(0,UPLOAD_ROOT.indexOf(":"));
//			logger.info("------------"+findHead);
			
			File sFile = new File(realPath);
			// 写入上传文件
			try {
//				if(!"ftp".equals(findHead)){
//					// 创建服务器文件存储目录
//					if (!sFile.exists()) {
//						sFile.mkdirs();
//					}
//					file.transferTo(sFile);
//				}else{
//					flag = FtpUploadUtils.ftpUploadFile(filePath,file,finalFlieName); 
//					if(flag){
//						map.put(SERVER_FILE_PATH, realPath);
//						logger.info("-------------"+realPath);
//						map.put(DB_FILE_PATH, dbFilePath);
//						return true;
//					}
//				}
				
				// 创建服务器文件存储目录
				if (!sFile.exists()) {
					sFile.mkdirs();
				}
				file.transferTo(sFile);
				String fname = sFile.getName();
				long fileSize = sFile.length();
				String suffix = fname.substring(fname.lastIndexOf("."));
				String filename = fileName.substring(0, fileName.lastIndexOf("."));
				if(!StringUtils.isEmpty(uploadPathKey)){
				    map.put(FileModel.FILE_KEY,uploadPathKey.replace(".upload.path", ""));
				}
				if (!StringUtils.isEmpty(filename)) {
					map.put(FileModel.FILE_NAME,filename);
				}
				
				String fileorgname = fname.substring(0,fname.lastIndexOf("."));
				map.put("filePath", filePath);
				map.put("finalFlieName", finalFlieName);
				map.put("FileOriginalName",fileorgname);// 物理文件名称
				map.put(FileModel.FILE_ORIGINAL_NAME,fileorgname);
				map.put("FileSuffix",suffix);// 文件后缀
				map.put(FileModel.FILE_SUFFIX, suffix);//文件后缀附加(防止按FileModel.FILE_SUFFIX来取属性)
				map.put("FileSize",new Long(fileSize).toString());// 文件大小
				map.put(FileModel.FILE_SIZE,new Long(fileSize).toString());//文件大小(防止按FileModel.FILE_SIZE来取属性)
				if (".png".equals(suffix.toLowerCase())
						|| ".jpg".equals(suffix.toLowerCase())
						|| ".jpge".equals(suffix.toLowerCase())
						|| ".jpge".equals(suffix.toLowerCase())
						|| ".gif".equals(suffix.toLowerCase())) {
//					BufferedImage bufferedImage=ImageIO.read(sFile);
//					int width = bufferedImage.getWidth(); // 图片宽度
//					int height = bufferedImage.getHeight();// 图片高度
					/*应对CMYK模式图片上传报错的情况*/
					BufferedImage bufferedImage=null;
					try{
						bufferedImage = ImageIO.read(sFile);
					}catch(Exception e){
						try {
							ThumbnailConvert tc = new ThumbnailConvert();
							tc.setCMYK_COMMAND(sFile.getPath());
							bufferedImage = null;
							Image image = Toolkit.getDefaultToolkit().getImage(sFile.getPath());
							MediaTracker mediaTracker = new MediaTracker(new Container());
							mediaTracker.addImage(bufferedImage, 0);
							mediaTracker.waitForID(0);
							bufferedImage=ThumbnailConvert.toBufferedImage(image);
						} catch (Exception e1) {
							//e1.printStackTrace();
						}
					}
					/*应对CMYK模式图片上传报错的情况END*/
					int width = bufferedImage.getWidth(); // 图片宽度
					int height = bufferedImage.getHeight();// 图片高度
					String format = suffix.replace(".", "");
					map.put(FileModel.PIC_WEIGHT,new Integer(width).toString());
					map.put(FileModel.PIC_HEIGHT,new Integer(height).toString());
					map.put(FileModel.FORMAT,format);
				}
				
				map.put(SERVER_FILE_PATH, realPath);
				map.put(DB_FILE_PATH, dbFilePath);
				
			} catch (Exception e) {
				flag = false;
				logger.error(e.getMessage());
				e.printStackTrace();
				return false;
			}
			return true;
		}

		return flag;
	}
	
	/*** 文件上传 */
	public static boolean saveU3dModelFile(Map map) {
		boolean flag = false;
		if (map == null) {
			return flag;
		}

		// 业务对应的文件存储目录key值
		String uploadPathKey = (String) map.get(UPLOADPATHTKEY);
		// 获取文件句柄
		MultipartFile file = (MultipartFile) map.get(FILE);
		logger.info("file=" + file);
		if (file != null) {
			// 获取上传文件的文件名
			String fileName = file.getOriginalFilename();
//			String finalFlieName = fileName.substring(0, fileName.indexOf("."))
//					+ "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
//					+ fileName.substring(fileName.indexOf("."));
			String finalFlieName = "";
			if( "nameKeep".equals(map.get("opType")) ){
				finalFlieName = fileName.substring(0, fileName.indexOf("."))
						+ "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
						+ fileName.substring(fileName.indexOf("."));
			}else{
				finalFlieName = Utils.generateRandomDigitString(6)
						+ "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
						+ fileName.substring(fileName.indexOf("."));
			}
			// 获取配置的业务文件目录
			//String filePath = app.getString(uploadPathKey);
			String filePath = res.getString(uploadPathKey);
			/*如果有[code],替换code*/
			if(map.containsKey("code")&&StringUtils.isNotBlank((String)map.get("code"))){
				filePath=filePath.replace("[code]", (String)map.get("code"));
			}
			// 数据库存储目录
			String dbFilePath = filePath + finalFlieName;
			// 文件服务器的存储路径
			String realPath = Utils.dealWithPath(Utils.getAbsolutePath(dbFilePath, null), null);
			/*if("linux".equals(SYSTEM_FORMAT)){
				 realPath = UPLOAD_ROOT + dbFilePath;
			}else{
				 realPath = UPLOAD_ROOT + dbFilePath.replace("/", "\\");
			}*/
			
//			String findHead = UPLOAD_ROOT.substring(0,UPLOAD_ROOT.indexOf(":"));
//			logger.info("------------"+findHead);
			
			File sFile = new File(realPath);
			// 写入上传文件
			try {
//				if(!"ftp".equals(findHead)){
//					// 创建服务器文件存储目录
//					if (!sFile.exists()) {
//						sFile.mkdirs();
//					}
//					file.transferTo(sFile);
//				}else{
//					flag = FtpUploadUtils.ftpUploadFile(filePath,file,finalFlieName); 
//					if(flag){
//						map.put(SERVER_FILE_PATH, realPath);
//						logger.info("-------------"+realPath);
//						map.put(DB_FILE_PATH, dbFilePath);
//						return true;
//					}
//				}
				
				// 创建服务器文件存储目录
				if (!sFile.exists()) {
					sFile.mkdirs();
				}
				file.transferTo(sFile);
				String fname = sFile.getName();
				long fileSize = sFile.length();
				String suffix = fname.substring(fname.lastIndexOf("."));
				String filename = fileName.substring(0, fileName.lastIndexOf("."));
				if(!StringUtils.isEmpty(uploadPathKey)){
				    map.put(FileModel.FILE_KEY,uploadPathKey.replace(".upload.path", ""));
				}
				if (!StringUtils.isEmpty(filename)) {
					map.put(FileModel.FILE_NAME,filename);
				}
				
				String fileorgname = fname.substring(0,fname.lastIndexOf("."));
				map.put("filePath", filePath);
				map.put("finalFlieName", finalFlieName);
				map.put("FileOriginalName",fileorgname);// 物理文件名称
				map.put(FileModel.FILE_ORIGINAL_NAME,fileorgname);
				map.put("FileSuffix",suffix);// 文件后缀
				map.put(FileModel.FILE_SUFFIX, suffix);//文件后缀附加(防止按FileModel.FILE_SUFFIX来取属性)
				map.put("FileSize",new Long(fileSize).toString());// 文件大小
				map.put(FileModel.FILE_SIZE,new Long(fileSize).toString());//文件大小(防止按FileModel.FILE_SIZE来取属性)
				if (".png".equals(suffix.toLowerCase())
						|| ".jpg".equals(suffix.toLowerCase())
						|| ".jpge".equals(suffix.toLowerCase())
						|| ".jpge".equals(suffix.toLowerCase())
						|| ".gif".equals(suffix.toLowerCase())) {
//					BufferedImage bufferedImage=ImageIO.read(sFile);
//					int width = bufferedImage.getWidth(); // 图片宽度
//					int height = bufferedImage.getHeight();// 图片高度
					/*应对CMYK模式图片上传报错的情况*/
					BufferedImage bufferedImage=null;
					try{
						bufferedImage = ImageIO.read(sFile);
					}catch(Exception e){
						try {
							ThumbnailConvert tc = new ThumbnailConvert();
							tc.setCMYK_COMMAND(sFile.getPath());
							bufferedImage = null;
							Image image = Toolkit.getDefaultToolkit().getImage(sFile.getPath());
							MediaTracker mediaTracker = new MediaTracker(new Container());
							mediaTracker.addImage(bufferedImage, 0);
							mediaTracker.waitForID(0);
							bufferedImage=ThumbnailConvert.toBufferedImage(image);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					/*应对CMYK模式图片上传报错的情况END*/
					int width = bufferedImage.getWidth(); // 图片宽度
					int height = bufferedImage.getHeight();// 图片高度
					String format = suffix.replace(".", "");
					map.put(FileModel.PIC_WEIGHT,new Integer(width).toString());
					map.put(FileModel.PIC_HEIGHT,new Integer(height).toString());
					map.put(FileModel.FORMAT,format);
				}
				
				map.put(SERVER_FILE_PATH, realPath);
				map.put(DB_FILE_PATH, dbFilePath);
				
				//仅支持ftp服务器上传
				if(Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD)==2){
					flag = FtpUploadUtils.uploadFile(finalFlieName,realPath,filePath);
					if(flag){
						//删除本地
						FileUploadUtils.deleteFile(dbFilePath);
					}else{
						return false;
					}
				}
				//3 本地和ftp同时上传(默认是本地上传)
				if(Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD)==3){
					flag = FtpUploadUtils.uploadFile(finalFlieName,realPath,filePath);
					if(!flag){
						return false;
					}
				}
			} catch (Exception e) {
				flag = false;
				logger.error(e.getMessage());
				e.printStackTrace();
				return false;
			}
			return true;
		}

		return flag;
	}

   /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
    
 	/*
 	 * 将某个文件或图片等转换为文件资源对象，自动读取资源大小等信息
 	 * sFile 文件路径
 	 * uploadPathKey 文件上传配置key
 	 * isExistTimestamp 文件名称是否存在时间戳
 	 * 
 	 */
	public static Map<String, String> getMap(File sFile,String uploadPathKey,boolean isExistTimestamp){
		Map <String, String>map = new HashMap<String, String>();
		logger.info(sFile.isDirectory()+"-----"+!sFile.exists()+"----"+StringUtils.isEmpty(uploadPathKey));
		if(sFile.isDirectory() || !sFile.exists() || StringUtils.isEmpty(uploadPathKey)){
			return map;
		}
		String fname = sFile.getName();
		long fileSize = sFile.length();
		String suffix = fname.substring(fname.lastIndexOf("."));
		
		String filename = fname;
		if(isExistTimestamp){
			if(fname.contains("_"))
			   filename =fname.substring(0, fname.lastIndexOf("_"));
		}else{
			filename =fname.substring(0, fname.lastIndexOf("."));
		}
	
			
		if(!StringUtils.isEmpty(uploadPathKey)){
		    map.put(FileModel.FILE_KEY,uploadPathKey.replace(".upload.path", ""));
		}
		if (!StringUtils.isEmpty(filename)) {
			map.put(FileModel.FILE_NAME,filename);
		}
		
		String fileorgname = fname.substring(0,fname.lastIndexOf("."));
		map.put(FileModel.FILE_ORIGINAL_NAME,fileorgname);// 物理文件名称
		map.put(FileModel.FILE_SUFFIX,suffix);// 文件后缀
		map.put(FileModel.FILE_SIZE,new Long(fileSize).toString());// 文件大小
		if (".png".equals(suffix.toLowerCase())
				|| ".jpg".equals(suffix.toLowerCase())
				|| ".jpge".equals(suffix.toLowerCase())
				|| ".jpge".equals(suffix.toLowerCase())
				|| ".gif".equals(suffix.toLowerCase())) {
			BufferedImage bufferedImage = null;
			try{
				bufferedImage = ImageIO.read(sFile);
			}catch(Exception e){
				e.printStackTrace();
			}
			int width = bufferedImage==null?-1:bufferedImage.getWidth(); // 图片宽度
			int height =  bufferedImage==null?-1:bufferedImage.getHeight();// 图片高度
			String format = suffix.replace(".", "");
			map.put(FileModel.PIC_WEIGHT,new Integer(width).toString());
			map.put(FileModel.PIC_HEIGHT,new Integer(height).toString());
			map.put(FileModel.FORMAT,format);
		}
		
		map.put(SERVER_FILE_PATH, sFile.getAbsolutePath());
		String dbFilePath = Utils.getRelativeUrlByAbsolutePath(sFile.getAbsolutePath());
		/*if("linux".equals(SYSTEM_FORMAT)){
			dbFilePath = sFile.getAbsolutePath().replace(UPLOAD_ROOT, "");
		}else{
			dbFilePath = sFile.getAbsolutePath().replace(UPLOAD_ROOT, "").replace("\\", "/");
		}*/
		map.put(DB_FILE_PATH, dbFilePath);
		map.put(FileModel.FILE_PATH, dbFilePath);
		
		return map;
	}
	public static void main(String[] args) {
//		File sFile = new File("D:\\test\\test.txt");
//		FileUploadUtils.getMap(sFile, "home.spacecommon.pic.zip.upload.path",false);
//		//////System.out.println(FileUploadUtils.downloadFile("/product/baseProduct/model/530381_20151231152835603.zip","F:\\renderTest"));

//		try {
//			FileUploadUtils.downloadFile("//design/designTemplet/E01_0003_001/3dmodel/559463_20160523152754025.zip","f://");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		File file = new File("f:\\temp");
//		FileUploadUtils.deleteDir(file);
//
//		try {
//			FileUploadUtils.downloadFile("//design/designTemplet/E01_0003_001/3dmodel/559463_20160523152754025.zip","f://");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		String param = FileUploadUtils.getFileContext("F:\\MaxRender\\zhangwj\\C07_0022_001_781406_20160613160612105228\\Data.txt");
		RenderUtil.createDataFile(param,"F:\\Data.txt");
	}
	
	/*** 文件下载 
	 * 
	 * */
	public static void download(HttpServletRequest request,
			HttpServletResponse response, String storeName, String contentType,
			String realName) throws Exception {
		response.setContentType("text/html;charset=UTF-8;application/octet-stream");
		if( Utils.getIntValue(FTP_UPLOAD_METHOD)==1 ){
			request.setCharacterEncoding("UTF-8");
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;

			/*String ctxPath = "";
			if(Utils.getIntValue(FTP_UPLOAD_METHOD)==1){
				ctxPath = UPLOAD_ROOT;
			}else{
				ctxPath = FTP_UPLOAD_ROOT;
			}*/
			/*String downLoadPath = ctxPath + storeName;*/
			String downLoadPath = Utils.getAbsolutePath(storeName, null);
			logger.info(downLoadPath);
			long fileLength = new File(downLoadPath).length();

			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(realName.getBytes("GBK"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));

			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bis.close();
			bos.close();
		}else {
			FtpUploadUtils.downloadFileFtp(storeName,response);
		}
	}

	/**
	    * 
	    * @param resourcesPath
	    *            源文件
	    * @param targetPath
	    *            复制到的新文件
	    */

	    public static boolean fileCopy(File resourcesPath, File targetPath) {
	    
	    	boolean flag = false;
	    	if(resourcesPath==null && targetPath==null){
	    		return flag;
	    	}
	    	if(!resourcesPath.exists()){
	    		logger.info("file is not exists = " + resourcesPath.getPath());
	    		return false;
	    	}
	    	if(!targetPath.getParent().isEmpty()){    
	    		try {    
	    			targetPath.getParentFile().mkdirs();
	    		} catch (Exception e) {
	    			
	    		} 
	    	}
	    	if(!targetPath.exists()){    
	    		try {    
	    			targetPath.createNewFile();    
	    		} catch (IOException e) {
	    			
	    		} 
	    	}
	        FileInputStream fi = null;
	        FileOutputStream fo = null;
	        FileChannel in = null;
	        FileChannel out = null;
	        try {
	            fi = new FileInputStream(resourcesPath);
	            fo = new FileOutputStream(targetPath);
	            in = fi.getChannel();//得到对应的文件通道
	            out = fo.getChannel();//得到对应的文件通道
	            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道

	         } catch (IOException e) {
	        	flag = false;
	            e.printStackTrace();
	        } finally {
	            try {
	            	if(fi != null){
	            		fi.close();
	            	}
	            	if(in != null){
	                    in.close();
	            	}
	                if(fo != null){
	                   fo.close();
	                }
	                if(out != null){
	                   out.close();
	                }

	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return true;
	    }
	    
	    /**
		    * 
		    * @param resourcesPath
		    *            源文件
		    * @param targetPath
		    *            复制到的新文件
		    */

		    public static boolean fileCopy(String resourcesPath, String targetPath) {
		        File f1 = new File(resourcesPath);
		        File f2 = new File(targetPath);
		    	return fileCopy(f1,f2);
		    }
		    
		    public static String copyfile(String resourcesPath, String targetPath) {
		        File f1 = new File(resourcesPath);
		        File f2 = new File(targetPath);
		        if(fileCopy(f1,f2)){
		        	return targetPath;
		        }
		    	return "";
		    }
		    
	    /*
	     * 从某个资源中拷贝生成一个新的资源，并生成新的id
	     * @param resId ，源资源主健
	     * @param resType，资源类型file，model，pic
	     * @param fileKey，新生成文件系统key标示
	     * @param bussniess,该文件关联的业务id
	     */
	    public  static Integer copyFile(String resId, String resType, String fileKey, Integer bussniess, HttpServletRequest request,Object baseService) {
	    	logger.info("resId=" + resId+ ";resType=" +resType+";fileKey=" + fileKey +";bussniess="+bussniess+";");
	        String resFilePath = "";
	        Integer newResId = -1;

	        ResFile resFile = new ResFile();
	        ResModel resModel = new ResModel();
	        ResPic resPic = new ResPic();
	        if (!StringUtils.isEmpty(resId)) {
	            if ("file".equals(resType)) {
	                ResFile file = ((ResFileService)baseService).get(new Integer(resId));;//resFileService.get(new Integer(resId));
	                if (file != null && !StringUtils.isEmpty(file.getFilePath())) {
	                    resFilePath = file.getFilePath();
	                    resFile = file.copy();
	                }
	            }
	            if ("model".equals(resType)) {
	                ResModel model = ((ResModelService)baseService).get(new Integer(resId));
	                if (model != null && !StringUtils.isEmpty(model.getModelPath())) {
	                    resFilePath = model.getModelPath();
	                    resModel = model.copy();
	                }

	            }
	            if ("pic".equals(resType)) {
	                ResPic pic = ((ResPicService)baseService).get(new Integer(resId));
	                if (pic != null && !StringUtils.isEmpty(pic.getPicPath())) {
	                    resFilePath = pic.getPicPath();
	                    resPic = pic.copy();
	                }

	            }
	            if (!StringUtils.isEmpty(resFilePath)) {
	            	
	            	/*String srcPath = FileUploadUtils.UPLOAD_ROOT + resFilePath.replace("/", "\\");
	            	if("linux".equals(FileUploadUtils.SYSTEM_FORMAT)){
	            	   srcPath =FileUploadUtils.UPLOAD_ROOT + resFilePath;
	            	}*/
	            	String srcPath = Utils.dealWithPath(Utils.getAbsolutePath(resFilePath, null), null);
	               
	                File resourcesFile = new File(srcPath);
	                String resourcesName = resFilePath.substring(resFilePath.replace("/", "\\").lastIndexOf("\\") + 1, resFilePath.length());
	                if("linux".equals(FileUploadUtils.SYSTEM_FORMAT)){
	                	resourcesName =  resFilePath.substring(resFilePath.lastIndexOf("/") + 1, resFilePath.length());
	            	}
	                
	                //String newPath = app.getString(fileKey + ".upload.path");
	                String newPath = res.getString(fileKey + ".upload.path");
	                String targetName = newPath + resourcesName.substring(resourcesName.lastIndexOf("/") + 1, resourcesName.lastIndexOf("_"))
	                        + "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
	                        + resourcesName.substring(resourcesName.indexOf("."));

	                /*String targetPath = FileUploadUtils.UPLOAD_ROOT + targetName.replace("/", "\\");
	                if("linux".equals(FileUploadUtils.SYSTEM_FORMAT)){
	                	targetPath =  FileUploadUtils.UPLOAD_ROOT + targetName;
	            	}*/
	                String targetPath = Utils.dealWithPath(Utils.getAbsolutePath(targetName, null), null);
	                
	                File targetFile = new File(targetPath);

	                if (!targetFile.getParentFile().exists()) {
	                    targetFile.getParentFile().mkdirs();
	                }

	                if (resourcesFile.isFile() && resourcesFile.exists()) { // 判断文件是否存在
	                    boolean flag = false;
	                    flag = FileUploadUtils.fileCopy(resourcesFile, targetFile);
	                    if (flag) {
	                        if ("file".equals(resType)) {
	                            resFile.setSysCode(null);
	                            resFile.setFilePath(targetName);
	                            resFile.setFileKey(fileKey);
	                            resFile.setBusinessId(bussniess);
	                            sysSave(resFile, request);
	                            newResId =((ResFileService)baseService).add(resFile);
	                        }
	                        if ("model".equals(resType)) {
	                            resModel.setSysCode(null);
	                            resModel.setModelPath(targetName);
	                            resModel.setFileKey(fileKey);
	                            resModel.setBusinessId(bussniess);
	                            sysSave(resModel, request);
	                            newResId =((ResModelService)baseService).add(resModel);
	                        }
	                        if ("pic".equals(resType)) {
	                            resPic.setPicPath(targetName);
	                            resPic.setFileKey(fileKey);
	                            resPic.setSysCode(null);
	                            resPic.setBusinessId(bussniess);
	                            sysSave(resPic, request);
	                            newResId =((ResPicService)baseService) .add(resPic);
	                        }
	                    }
	                }
	            }
	        }
	        return newResId;
	    }
	    
	    /**
		 * ResFile自动存储系统字段
		 */
		private static void sysSave(ResFile model, HttpServletRequest request) {
			if (model != null) {
				LoginUser loginUser = new LoginUser();
				if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
						|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
					loginUser.setLoginName("nologin");
				} else {
					loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
				}

				if (model.getId() == null) {
					model.setGmtCreate(new Date());
					model.setCreator(loginUser.getLoginName());
					model.setIsDeleted(0);
					if (model.getSysCode() == null || "".equals(model.getSysCode())) {
						model.setSysCode(Utils
								.getCurrentDateTime(Utils.DATETIMESSS)
								+ "_"
								+ Utils.generateRandomDigitString(6));
					}
				}

				model.setGmtModified(new Date());
				model.setModifier(loginUser.getLoginName());
			}
		}

		/**
		 * ResPic自动存储系统字段
		 */
		private static void sysSave(ResPic model, HttpServletRequest request) {
			if (model != null) {
				LoginUser loginUser = new LoginUser();
				if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
						|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
					loginUser.setLoginName("nologin");
				} else {
					loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
				}

				if (model.getId() == null) {
					model.setGmtCreate(new Date());
					model.setCreator(loginUser.getLoginName());
					model.setIsDeleted(0);
					if (model.getSysCode() == null || "".equals(model.getSysCode())) {
						model.setSysCode(Utils
								.getCurrentDateTime(Utils.DATETIMESSS)
								+ "_"
								+ Utils.generateRandomDigitString(6));
					}
				}

				model.setGmtModified(new Date());
				model.setModifier(loginUser.getLoginName());
			}
		}

		/**
		 * ResModel自动存储系统字段
		 */
		private static void sysSave(ResModel model, HttpServletRequest request) {
			if (model != null) {
				LoginUser loginUser = new LoginUser();
				if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
						|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
					loginUser.setLoginName("nologin");
				} else {
					loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
				}

				if (model.getId() == null) {
					model.setGmtCreate(new Date());
					model.setCreator(loginUser.getLoginName());
					model.setIsDeleted(0);
					if (model.getSysCode() == null || "".equals(model.getSysCode())) {
						model.setSysCode(Utils
								.getCurrentDateTime(Utils.DATETIMESSS)
								+ "_"
								+ Utils.generateRandomDigitString(6));
					}
				}

				model.setGmtModified(new Date());
				model.setModifier(loginUser.getLoginName());
			}
		}
		
		/*
		 * 将一个文件或文件夹压缩成zip
		 * source 文件或文件夹物理路径
		 * zipFilePath 压缩文件的目标文件路径
		 */
		public static boolean ZipCompressor(String source ,String zipFilePath){
			try{
					ZipCompressor zc = new ZipCompressor(zipFilePath);
					File zipFile = new File(zipFilePath);
					if(!zipFile.getParentFile().exists() ){
						zipFile.getParentFile().mkdirs();
					}
					//压缩
					zc.compress(source);
					return true;
			}catch(Exception e){
			  e.printStackTrace();	
			}
			return false;
		}
		
		/*
		 * 将zip或rar解压到指定目录中
		 * sourceFile zip物理文件路径
		 * destDir 目标物理目录
		 */
		public static boolean ZipDeCompress(String sourceFile, String destDir){
			try{
				DeCompressUtil.deCompress(sourceFile, destDir, "");
				return true;
			}catch(Exception e){
				  e.printStackTrace();	
				}
				return false;
		}
		
		/**拷贝文件
		 * @throws IOException */
	    public static boolean copyFile2(String url1,String url2) throws IOException{
	    	File f1=new File(url1);
	    	File f2=new File(url2);
	    	File folder=new File(url2.substring(0,url2.lastIndexOf("/")));//拷贝文件路径文件夹
			if (folder.exists() && folder.isDirectory()) {
				logger.info("------未找到文件,path:"+url1);
			}else{
				/*文件夹不存在,创建文件夹*/
				folder.mkdirs();
			}
	    	boolean j=true;
	    	FileInputStream in=null;
	    	FileOutputStream out=null;
	    	FileChannel inC=null;
	    	FileChannel outC=null;
	        try{
	            int length=2097152;
	            in=new FileInputStream(f1);
	            out=new FileOutputStream(f2);
	            inC=in.getChannel();
	            outC=out.getChannel();
	            ByteBuffer b=null;
	            while(true){
	                if(inC.position()==inC.size()){
	                    inC.close();
	                    outC.close();
	                    break;
	                }
	                if((inC.size()-inC.position())<length){
	                    length=(int)(inC.size()-inC.position());
	                }else
	                    length=2097152;
	                b=ByteBuffer.allocateDirect(length);
	                inC.read(b);
	                b.flip();
	                outC.write(b);
	                outC.force(false);
	            }
	        }catch(Exception e){
	        	e.printStackTrace();
	        	j=false;
	        }finally{
	        	if(in!=null){
	        		in.close();
	        	}
	        	if(out!=null){
	        		out.close();
	        	}
	        	if(inC!=null){
	        		inC.close();
	        	}
	        	if(outC!=null){
	        		outC.close();
	        	}
	        }
	        return j;
	    }
	    
	    /**删除物理文件*/
		public static void deleteFile(String path){
			FileEncrypt.deleteAllFile(path);
		}

	public static String downloadFile(String serverFilePath, String filePath) throws IOException{
		String localFilePath = null;
//		try {
			if( SYSTEM_FORMAT.equals("linux") ){
				filePath = filePath.replace("\\", "/");
				serverFilePath = serverFilePath.replace("\\", "/");
			}
			File dirFile = new File(filePath);

			if (!dirFile.exists()) {//文件路径不存在时，自动创建目录
				dirFile.mkdirs();
			}
			//本地文件地址
			localFilePath = filePath + serverFilePath.substring(serverFilePath.lastIndexOf("/"));
			File localFile=new File(localFilePath);
			logger.info("--------"+localFilePath);
			if(localFile.exists())
				return localFilePath;
			/*URL theURL = new URL(RESOURCES_URL + serverFilePath);*/
			URL theURL = new URL(Utils.getAbsoluteUrlByRelativeUrl(serverFilePath));
			//从服务器上获取图片并保存
			URLConnection connection = theURL.openConnection();
			InputStream in = connection.getInputStream();
			FileOutputStream os = new FileOutputStream(localFilePath);
			byte[] buffer = new byte[4 * 1024];
			int read;
			while ((read = in.read(buffer)) > 0) {
				os.write(buffer, 0, read);
			}
			os.close();
			in.close();
//		}catch (IOException e){
//			e.printStackTrace();
//		}
		return localFilePath;
	}

	/**
	 * 读取文本文件中的文本信息
	 * @param filePath
	 * @return
	 */
	public static String getFileContext(String filePath){
		StringBuffer sb = new StringBuffer();
		try {
			if( !new File(filePath).exists() ){
				return sb.toString();
			}
			FileReader fr = new FileReader(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"UTF-8"));
			String str = "";
			while( ( str = br.readLine() ) != null ){
				sb.append(str);
				sb.append("\r\n");
			}
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 创建txt文件
	 * @param filePath
	 * @param context
	 * @return
	 */
	public static boolean writeTxtFile(String filePath,String context){
		if( StringUtils.isBlank(filePath) ){
			return false;
		}
		File file = new File(filePath);
		if( !file.getParentFile().exists() ){
			file.getParentFile().mkdirs();
		}
		if( !file.exists() ){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			file.delete();
		}
		OutputStreamWriter ow = null;
		FileWriter fw = null;
		try {
			ow = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			ow.write(context);
			ow.close();
//			fw = new FileWriter(file);
//			fw.write(context);
//			fw.close();
		}catch(Exception e){
			logger.error(e.getMessage());
			try {
				if( ow != null ){
					ow.close();
				}
				return false;
			} catch (IOException e1) {
				e1.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	
	/*** 视频文件上传 */
	public static boolean saveVideoFile(Map map) {
		boolean flag = false;
		if (map == null) {
			return flag;
		}

		// 业务对应的文件存储目录key值
		String uploadPathKey = (String) map.get(UPLOADPATHTKEY);
		logger.error("uploadPathKey======>"+uploadPathKey);
		// 获取文件句柄
		MultipartFile file = (MultipartFile) map.get(FILE);
		
		if (file != null) {
			// 获取上传文件的文件名
			String fileName = file.getOriginalFilename();
			if(StringUtils.isBlank(fileName)){
				logger.debug("------识别待上传的文件名为null,默认为.mp4");
				fileName = ".mp4";
			}
//			String finalFlieName = fileName.substring(0, fileName.indexOf("."))
//					+ "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
//					+ fileName.substring(fileName.indexOf("."));
			String finalFlieName = "";
			if( "nameKeep".equals(map.get("opType")) ){
//				finalFlieName = fileName.substring(0, fileName.indexOf("."))
//						+ "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
//						+ fileName.substring(fileName.indexOf("."));
				finalFlieName = fileName.substring(0, fileName.indexOf("."))
						+ "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
						+ "_" + fileName;
			}else{
//				finalFlieName = Utils.generateRandomDigitString(6)
//						+ "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
//						+ fileName.substring(fileName.indexOf("."));
				finalFlieName = Utils.generateRandomDigitString(6)
						+ "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
						+ "_" + fileName;
			}
			// 获取配置的业务文件目录
			//String filePath = app.getString(uploadPathKey);
			
			String filePath = res.getString(uploadPathKey);
			//替换[yyyy]/[MM]/[dd]/[HH]
			filePath = Utils.replaceDate(filePath);
			/*如果有[code],替换code*/
			if(map.containsKey("code")&&StringUtils.isNotBlank((String)map.get("code"))){
				filePath=filePath.replace("[code]", (String)map.get("code"));
			}
			// 数据库存储目录
			String dbFilePath = filePath + finalFlieName;
			// 文件服务器的存储路径
			String realPath = "";
			/*if("linux".equals(SYSTEM_FORMAT)){
				 realPath = UPLOAD_ROOT + dbFilePath;
			}else{
				 realPath = UPLOAD_ROOT + dbFilePath.replace("/", "\\");
			}*/
			realPath = Utils.dealWithPath(Utils.getAbsolutePath(dbFilePath, null), null);
			logger.debug("--------realPath=========================="+realPath);
//			String findHead = UPLOAD_ROOT.substring(0,UPLOAD_ROOT.indexOf(":"));
//			logger.info("------------"+findHead);
			File sFile = new File(realPath);
			// 写入上传文件
			try {
//				if(!"ftp".equals(findHead)){
//					// 创建服务器文件存储目录
//					if (!sFile.exists()) {
//						sFile.mkdirs();
//					}
//					file.transferTo(sFile);
//				}else{
//					flag = FtpUploadUtils.ftpUploadFile(filePath,file,finalFlieName); 
//					if(flag){
//						map.put(SERVER_FILE_PATH, realPath);
//						logger.info("-------------"+realPath);
//						map.put(DB_FILE_PATH, dbFilePath);
//						return true;
//					}
//				}
				
				// 创建服务器文件存储目录
				if( !sFile.exists() ){
					if( !sFile.getParentFile().exists() ){
						sFile.getParentFile().mkdirs();
					}
				}
//				if (!sFile.exists()) {
//					sFile.mkdirs();
//				}
				file.transferTo(sFile);
				String fname = sFile.getName();
				long fileSize = sFile.length();
				String suffix = fname.substring(fname.lastIndexOf("."));
				String filename = fileName.substring(0, fileName.lastIndexOf("."));
				if(!StringUtils.isEmpty(uploadPathKey)){
				    map.put(FileModel.FILE_KEY,uploadPathKey.replace(".upload.path", ""));
				}
				if (!StringUtils.isEmpty(filename)) {
					map.put(FileModel.FILE_NAME,filename);
				}
				String fileorgname = fname.substring(0,fname.lastIndexOf("."));
				map.put("filePath", filePath);
				map.put("finalFlieName", finalFlieName);
				map.put("FileOriginalName",fileorgname);// 物理文件名称
				map.put(FileModel.FILE_ORIGINAL_NAME,fileorgname);
				map.put("FileSuffix",suffix);// 文件后缀
				map.put(FileModel.FILE_SUFFIX, suffix);//文件后缀附加(防止按FileModel.FILE_SUFFIX来取属性)
				map.put("FileSize",new Long(fileSize).toString());// 文件大小
				map.put(FileModel.FILE_SIZE,new Long(fileSize).toString());//文件大小(防止按FileModel.FILE_SIZE来取属性)
				if (".mp4".equals(suffix.toLowerCase())
						|| ".mpg".equals(suffix.toLowerCase())
						|| ".mov".equals(suffix.toLowerCase())
						|| ".mpeg".equals(suffix.toLowerCase())
						|| ".avi".equals(suffix.toLowerCase())) {
//					BufferedImage bufferedImage=ImageIO.read(sFile);
//					int width = bufferedImage.getWidth(); // 图片宽度
//					int height = bufferedImage.getHeight();// 图片高度
					/*应对CMYK模式图片上传报错的情况*/
					BufferedImage bufferedImage=null;
					try{
						bufferedImage = ImageIO.read(sFile);
					}catch(Exception e){
						try {
							ThumbnailConvert tc = new ThumbnailConvert();
							tc.setCMYK_COMMAND(sFile.getPath());
							bufferedImage = null;
							Image image = Toolkit.getDefaultToolkit().getImage(sFile.getPath());
							MediaTracker mediaTracker = new MediaTracker(new Container());
							mediaTracker.addImage(bufferedImage, 0);
							mediaTracker.waitForID(0);
							bufferedImage=ThumbnailConvert.toBufferedImage(image);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					/*应对CMYK模式图片上传报错的情况END*/
					//int width = bufferedImage.getWidth(); // 图片宽度
					//int height = bufferedImage.getHeight();// 图片高度
					String format = suffix.replace(".", "");
					//map.put(FileModel.PIC_WEIGHT,new Integer(width).toString());
					//map.put(FileModel.PIC_HEIGHT,new Integer(height).toString());
					map.put(FileModel.FORMAT,format);
				}

				map.put(SERVER_FILE_PATH, realPath);
				map.put(DB_FILE_PATH, dbFilePath);
				
			} catch (Exception e) {
				flag = false;
				logger.error(e.getMessage());
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return flag;
	}
}
