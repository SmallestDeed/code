package com.sandu.api.house.service;

import java.util.List;

import com.sandu.api.house.output.UploadFileVO;

public interface UploadService {

	/**
	 * 根据UploadFileVO 创建资源数据
	 * 
	 * @author huangsongbo
	 * @param fileVO
	 * @return
	 */
	Long createFileDataByUploadFileVO(UploadFileVO fileVO);

	/**
	 * 批量保存资源到数据库
	 * 
	 * @param uploadFiles
	 */
	void saveUploadFile(List<UploadFileVO> uploadFiles);
}
