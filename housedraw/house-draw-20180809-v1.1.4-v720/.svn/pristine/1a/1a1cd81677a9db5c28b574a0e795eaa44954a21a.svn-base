package com.sandu.api.house.service;

import java.util.List;

import com.sandu.api.house.model.DrawResHousePic;
import com.sandu.api.house.output.UploadFileVO;
import com.sandu.api.res.model.ResPic;

public interface DrawResHousePicService {

	Long createByUploadFileVO(UploadFileVO fileVO);

	List<DrawResHousePic> addBatchPic(List<UploadFileVO> pics);

	Integer update(DrawResHousePic drawPic);
	
	DrawResHousePic getDrawResHousePic(Long id);

	Long copyFile(DrawResHousePic source, String fileKey, boolean isSmall);
	ResPic copyFile(DrawResHousePic source, String fileKey, boolean isSmall, boolean isAADir);

    Integer clearDrawResHousePicResourceWithError(List<Long> picIds);

	Integer batchDeletedDrawResHousePic(List<Long> picIds);

	List<DrawResHousePic> listDrawHousePicByIds(List<Long> picIds);
}
