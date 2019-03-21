package com.sandu.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.sandu.exception.BizException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sandu.api.house.bo.DrawUploadBO;
import com.sandu.api.house.output.UploadFileVO;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.common.constant.SystemConfigEnum;
import com.sandu.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

/**
 * Description:
 * <p>
 * Company:Sandu Copyright:Copyright(c)2017
 * 
 * @author 何文
 * @version 1.0
 * @date 2017/12/29
 */

@Slf4j
public class UploadUtils {
	
	private static UploadFileVO uploadHousePic(MultipartFile file, String rootDir, String path) throws BusinessException {
		String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String fileName = Utils.getFileName(fileSuffix);
		
		File imageFile;
		try {
			imageFile = new File(rootDir + path, fileName);
			FileUtils.copyInputStreamToFile(new BufferedInputStream(file.getInputStream()), imageFile);
			log.debug("上传图片 imageFile ==> {}", imageFile.getPath());
		} catch (IOException e) {
			throw new BusinessException(false, ResponseEnum.HOUSE_PIC_UPLOAD_FAIL, e);
		}

		UploadFileVO pic = new UploadFileVO();
		pic.setFileFormat(fileSuffix.replace(".", ""));
		pic.setFileName(file.getOriginalFilename());
		pic.setFileOriginalName(file.getOriginalFilename());
		pic.setFilePath(path + fileName);
		pic.setFileSize(String.valueOf(file.getSize()));
		pic.setFileSuffix(fileSuffix);
		pic.setWidth(String.valueOf(-1));
		pic.setHeight(String.valueOf(-1));
		
		//下面代码会导致内存泄漏,故注释掉,后台如果需要图片的长度和宽度,可以考虑让u3d那边传过来,而不在这里获取
		/*
		 * if (imageFile.exists()) {
			BufferedImage bufferedImg;
			try {
				// v1.0.0 解决图片出现红色阴影bug
				Image image = Toolkit.getDefaultToolkit().getImage(imageFile.getPath());
				bufferedImg = ThumbnailConvert.toBufferedImage(image);
			} catch (Exception ex) {
				log.error("上传户型图异常 ==> {}", ex.getMessage(), ex);
				throw new BusinessException(false, ResponseEnum.HOUSE_PIC_UPLOAD_FAIL);
			}
			
			int imgWidth = bufferedImg.getWidth();
			int imgHeight = bufferedImg.getHeight();
			pic.setWidth(String.valueOf(imgWidth));
			pic.setHeight(String.valueOf(imgHeight));
		} else {
			throw new BusinessException(false, ResponseEnum.HOUSE_PIC_UPLOAD_FAIL);
		}*/
		
		return pic;
	}

	private static UploadFileVO uploadFile(MultipartFile myfile, String rootDir, String path) throws BusinessException {
		File uploadFile;
		String suffix = myfile.getOriginalFilename().substring(myfile.getOriginalFilename().lastIndexOf("."));
		String fileName = Utils.getFileName(suffix);
		
		try {
			uploadFile = new File(rootDir + path, fileName);
			FileUtils.copyInputStreamToFile(new BufferedInputStream(myfile.getInputStream()), uploadFile);
			log.debug("上传文件 uploadFile ==> {}", uploadFile.getPath());
		} catch (IOException e) {
			throw new BusinessException(false, ResponseEnum.HOUSE_PIC_UPLOAD_FAIL, e);
		}

		UploadFileVO file = new UploadFileVO();
		file.setFileFormat(suffix.replace(".", ""));
		file.setFileName(myfile.getOriginalFilename());
		file.setFileOriginalName(myfile.getOriginalFilename());
		file.setFilePath(path + fileName);
		file.setFileSize(String.valueOf(myfile.getSize()));
		file.setFileSuffix(suffix);
		if (!uploadFile.exists()) {
			throw new BusinessException(false, ResponseEnum.HOUSE_PIC_UPLOAD_FAIL);
		}

		return file;
	}
	
	public static UploadFileVO upload(MultipartFile file, Map<String, DrawUploadBO> uploadMap) throws BusinessException {
		UploadFileVO fileVO;
		if (file.isEmpty()) {
			throw new BusinessException(false, ResponseEnum.UPLOAD_FILE_IS_EMPTY);
		} else {
			SystemConfigEnum config;
			String fileOriginName = file.getOriginalFilename();
			try {
				config = SystemConfigEnum.valueOf(uploadMap.get(fileOriginName).getType());
			} catch (Exception e) {
				throw new BizException("参数fileName与文件名不一致！");
			}

			String rootDir = Utils.getValue(SystemConfigEnum.UPLOAD_ROOT_DIR.getKey());
			String rootPath = Utils.getRealStoreDir(null, config.getKey());

			if (fileOriginName.matches(Regex.IMAGE.getValue())) {
				fileVO = UploadUtils.uploadHousePic(file, rootDir, rootPath);
			} else {
				fileVO = UploadUtils.uploadFile(file, rootDir, rootPath);
			}

			fileVO.setFileType(uploadMap.get(fileOriginName).getType());
			fileVO.setFileUniqueId(uploadMap.get(fileOriginName).getFileUniqueId());
		}

		return fileVO;
	}

	public static boolean deleteFile(List<String> files) {

		for (String filePath : files) {
			File file = new File(filePath);
			if (!file.exists()) {
				log.error("文件不存在");
				return false;
			} else {
				if (file.isFile()) {
					if (file.delete()) {
						return true;
					} else {
						return false;
					}
				} else {
					log.error("删除非文件类型");
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * copy from 通用版本
	 * 
	 * @author huangsongbo
	 * @param source
	 * @param target
	 * @return
	 */
	public static boolean copyfile(String source, String target) {
		return fileCopy(new File(source), new File(target));
	}
	
	/**
	 * copy from 通用版本
	 * 
	 * @author huangsongbo
	 * @param source
	 * @param target
	 * @return
	 */
	public static boolean fileCopy(File source, File target) {
		boolean flag = false;
		
		if (source == null && target == null) {
			log.warn("参数source, target 是空");
			return flag;
		}

		if (!source.exists()) {
			log.error("资源文件不存在, resourcesPath ==> {}", source.getPath());
			return flag;
		}

		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;

		try {
			// parent 创建
			if (!target.getParent().isEmpty()) {
				target.getParentFile().mkdirs();
			}
			
			// 创建文件
			if (!target.exists()) {
				target.createNewFile();
			}
			
			fi = new FileInputStream(source);
			fo = new FileOutputStream(target);
			// 得到对应的文件通道
			in = fi.getChannel();
			// 得到对应的文件通道
			out = fo.getChannel();
			// 连接两个通道，并且从in通道读取，然后写入out通道
			in.transferTo(0, in.size(), out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fi != null) {
					fi.close();
				}
				if (in != null) {
					in.close();
				}
				if (fo != null) {
					fo.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return true;
	}
	
	/**
	 * 缩略图处理
	 * 
	 * @param source
	 * @param target
	 * @return
	 * @throws IOException
	 */
	public static String thumbnail(String source, String target, Integer[] wh) {
		Objects.requireNonNull(source, "参数 source 不能为空");
		Objects.requireNonNull(target, "参数 target 不能为空");

		if (wh == null || wh.length < 2) {
			wh = UploadUtils.NORMAL_W_H;
		}

		try {
			File file = new File(getFileDir(target));
			if (!file.exists()) {
				file.mkdirs();
			}
			// 缩略处理
			Thumbnails.of(source).size(wh[0], wh[1]).toFile(target);
		} catch (Exception e) {
			throw new BizException(e);
		}

		return target;
	}
	
	/**
	 * 获取缩略图的路径
	 * 
	 * @param filePath
	 * @param args
	 * @return
	 */
	public static String getThumbnailPath(String filePath, String args) {
		Objects.requireNonNull(filePath, "参数 filePath 不能为空");
		int lastIndexOf = lastIndexOf(filePath, Utils._LIUNX_FILE_SEPARATOR);
		String fileDir = filePath.substring(0, lastIndexOf + Utils.ONE_VALUE);
		String fileName = filePath.substring(lastIndexOf + Utils.ONE_VALUE);
		return fileDir + args + fileName;
	}
	
	/**
	 * 获取文件目录
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileDir(String filePath) {
		if (!StringUtils.isNotBlank(filePath)) {
			return Utils.VOID_VALUE;
		}
		return filePath.substring(0, lastIndexOf(filePath, Utils._LIUNX_FILE_SEPARATOR) + Utils.ONE_VALUE);
	}
	
	/**
	 * 获取文件名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		if (!StringUtils.isNotBlank(filePath)) {
			return Utils.VOID_VALUE;
		}
		return filePath.substring(lastIndexOf(filePath, Utils._LIUNX_FILE_SEPARATOR) + Utils.ONE_VALUE);
	}
	
	/**
	 * 获取文件名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileSuffix(String filePath) {
		if (!StringUtils.isNotBlank(filePath)) {
			return Utils.VOID_VALUE;
		}
		return filePath.substring(lastIndexOf(filePath, Utils._DOT) + Utils.ONE_VALUE);
	}
	
	/**
	 * 
	 * @param filePath
	 * @param regex
	 * @return
	 */
	public static int lastIndexOf(String filePath, String regex) {
		if (!StringUtils.isNotBlank(filePath)) {
			return -1;
		}
		int lastIndexOf = filePath.lastIndexOf(StringUtils.isNotBlank(regex) ? regex : Utils._LIUNX_FILE_SEPARATOR);
		// 兼容windows
		return (lastIndexOf <= 0) ? filePath.lastIndexOf(Utils._WINDOWS_FILE_SEPARATOR) : lastIndexOf;
	}

	public static class HouseImageStaff {
		public static final Integer[] SMALL_W_H = { 1024, 768 };
		public static final Integer[] THUMBNAIL_W_H = { 280, 210 };
		public static final Integer[] LARGE_THUMBNAIL_W_H = { 1120, 840 };
	}

	public static class SpaceImageStaff {
		public static final Integer[] WEB_W_H = { 296, 180 };
		public static final Integer[] IPAD_W_H = { 576, 348 };
		public static final Integer[] SMALL_W_H = { 150, 100 };
	}

	public static final Integer[] NORMAL_W_H = { 576, 348 };
	
	public static final String SMALL_MARK = "thumbnail/small_";
	public static final String THUMBNAIL_MARK = "thumbnail/thumbnail_";
	public static final String LARGE_THUMBNAIL_MARK = "largeThumbnail/largeThumbnail_";
}
