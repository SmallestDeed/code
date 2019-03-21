package com.sandu.api.house.service;

import java.util.List;
import java.util.Map;

import com.sandu.api.house.model.DrawResFile;
import com.sandu.api.house.output.UploadFileVO;
import com.sandu.api.res.model.ResModel;

public interface DrawResFileService {

	/**
	 * 用法如方法名
	 * 
	 * @author huangsongbo
	 * @param fileVO
	 * @return
	 */
	Long createByUploadFileVO(UploadFileVO fileVO);

	Long add(DrawResFile drawResFile);

	DrawResFile get(Long id);

	List<DrawResFile> addBatchFile(List<UploadFileVO> files);

    List<DrawResFile> listDrawResFileById(List<Integer> u3dOfWindows);

	Map<Long, ResModel> addBatchModelFile(List<DrawResFile> drawResFiles, String fileKey, boolean useEncrypt);

	Integer clearDrawResFileResource(List<Long> files);
}
