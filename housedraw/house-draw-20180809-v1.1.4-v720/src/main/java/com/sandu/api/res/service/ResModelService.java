package com.sandu.api.res.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sandu.api.house.model.DrawBaseProduct;
import com.sandu.api.house.model.DrawResFile;
import com.sandu.api.res.model.ResModel;

public interface ResModelService {

	/**
	 * DrawResFile ->ResModle
	 * 
	 * @author huangsongbo
	 * @param drawResFile
	 * @param needEncrypt
	 * @return
	 */
	Long createByDrawResFile(DrawResFile drawResFile, String fileKey, boolean needEncrypt);
	ResModel createByDrawResFile(DrawResFile drawResFile, String fileKey, boolean isEncrypt, boolean isAADir);

	Long add(ResModel resModel);

	void updateBusinessId(List<Integer> resFileIdList, Integer businessId);

	Map<String, ResModel> getResModelMap(Set<Long> list);

	ResModel selectByPrimaryKey(Integer windowsU3dmodelId);

	Long copyModel(ResModel source, String fileKey, boolean needEncrypt);

	String getFileKey(String key);

    Integer addBatchModelFile(List<ResModel> resModels);

    Integer updateBatchBusinessId(List<DrawBaseProduct> drawProducts);
}
