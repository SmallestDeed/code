package com.sandu.api.res.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sandu.api.house.model.DrawResFile;
import com.sandu.api.res.model.ResFile;

public interface ResFileService {

	Long createByDrawResFile(DrawResFile drawResFileConfig,
			String fileKey, boolean needEncrypt);

	void updateBusinessId(List<Integer> resFileIdList, Integer businessId);

	Long add(ResFile resFile);

	Map<String, ResFile> getResFileMap(Set<Long> args);

}