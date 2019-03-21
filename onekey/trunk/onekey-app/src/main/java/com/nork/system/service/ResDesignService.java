package com.nork.system.service;

import com.nork.system.model.ResDesign;

 

public interface ResDesignService {

	public ResDesign get(Integer id);

	public long copySpellingFlowerFile(Integer spellingFlowerFileId, Integer id, String planCode);
	
}
