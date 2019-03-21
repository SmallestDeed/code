package com.sandu.service.file.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.api.house.model.DrawResFile;
import com.sandu.api.house.model.DrawResHousePic;
import com.sandu.api.house.output.UploadFileVO;
import com.sandu.api.house.service.DrawResFileService;
import com.sandu.api.house.service.DrawResHousePicService;
import com.sandu.api.house.service.UploadService;
import com.sandu.common.constant.SystemConfigEnum;
import com.sandu.util.Regex;
import com.sandu.util.UploadUtils;
import com.sandu.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("uploadService")
public class UploadServiceImpl implements UploadService {

	@Autowired
	private DrawResFileService drawResFileService;

	@Autowired
	private DrawResHousePicService drawResHousePicService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long createFileDataByUploadFileVO(UploadFileVO fileVO) {
		if (fileVO == null) {
			return null;
		}

		Long fileId;
		if (SystemConfigEnum.HOUSE_PIC_UPLOAD.equals(SystemConfigEnum.valueOf(fileVO.getFileType())) 
				|| SystemConfigEnum.SPACE_PIC_UPLOAD.equals(SystemConfigEnum.valueOf(fileVO.getFileType()))) {
			// 存draw_res_house_pic
			fileId = drawResHousePicService.createByUploadFileVO(fileVO);
		} else {
			// 存draw_res_file
			fileId = drawResFileService.createByUploadFileVO(fileVO);
		}

		return fileId;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveUploadFile(List<UploadFileVO> uploadFiles) {
		if (uploadFiles == null || uploadFiles.isEmpty()) {
			log.warn("参数 uploadFiles 为空，不能执行保存！");
			return;
		}

		// 图片资源
		List<UploadFileVO> pics = new ArrayList<>();
		// 文件资源
		List<UploadFileVO> files = new ArrayList<>();

		for (UploadFileVO uploadFile : uploadFiles) {
			if (SystemConfigEnum.HOUSE_PIC_UPLOAD.equals(SystemConfigEnum.valueOf(uploadFile.getFileType())) 
					|| SystemConfigEnum.SPACE_PIC_UPLOAD.equals(SystemConfigEnum.valueOf(uploadFile.getFileType())) 
					|| Objects.toString(uploadFile.getFileName(), "").matches(Regex.IMAGE.getValue())) {
				pics.add(uploadFile);
			} else {
				files.add(uploadFile);
			}
		}

		// 保存图片资源
		List<DrawResHousePic> drawPics = drawResHousePicService.addBatchPic(pics);

		// 保存文件资源
		List<DrawResFile> drawFiles = drawResFileService.addBatchFile(files);

		loop: for (UploadFileVO uploadFile : uploadFiles) {
			for (DrawResHousePic drawPic : drawPics) {
				if (Objects.toString(drawPic.getPicName(), "").equals(uploadFile.getFileName())) {
					uploadFile.setFileId(drawPic.getId().longValue());

					// 处理空间的缩略图
					this.handlerThumbnail(drawPic, uploadFile.getFileType());

					continue loop;
				}
			}

			for (DrawResFile drawFile : drawFiles) {
				if (Objects.toString(drawFile.getFileName(), "").equals(uploadFile.getFileName())) {
					uploadFile.setFileId(drawFile.getId());
					continue loop;
				}
			}
		}
	}
	
	/**
	 * 处理空间的缩略图
	 * 
	 * @param drawPic
	 */
	private void handlerThumbnail(DrawResHousePic drawPic, String type) throws RuntimeException {
		try {
			if (SystemConfigEnum.SPACE_PIC_UPLOAD.toString().equals(type)) {
				
				log.debug("处理空间的缩略图, picId ==> {}", drawPic.getId());
				
				final String uploadRootDir = Utils.getValue(SystemConfigEnum.UPLOAD_ROOT_DIR.getKey());
				// 处理缩略图
				String filePath = uploadRootDir + drawPic.getPicPath();
				String thumbnailPath = UploadUtils.getThumbnailPath(filePath, UploadUtils.SMALL_MARK);
				UploadUtils.thumbnail(uploadRootDir + drawPic.getPicPath(), thumbnailPath, UploadUtils.SpaceImageStaff.IPAD_W_H);

				// save pic
				UploadFileVO uploadFileVO = new UploadFileVO();
				String fileName = UploadUtils.getFileName(thumbnailPath);
				uploadFileVO.setFileName(fileName);
				uploadFileVO.setFilePath(thumbnailPath);
				uploadFileVO.setFileOriginalName(fileName);
				Long picId = drawResHousePicService.createByUploadFileVO(uploadFileVO);

				// 更新smallPicInfo
				drawPic.setSmallPicInfo("ipad:" + picId + ";");
				drawResHousePicService.update(drawPic);
			}
		} catch (Exception e) {
			log.error("处理空间的缩略图异常", e);
			throw e;
		}
	}
}
