package com.sandu.service.system.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.system.model.SysDictionary;
import com.sandu.api.system.service.RAMCacheService;
import com.sandu.api.system.service.SysDictionaryService;

import lombok.extern.log4j.Log4j2;

@Service("ramCacheService")
@Log4j2
public class RAMCacheServiceImpl implements RAMCacheService {

	private final static String LOGPREFIX = "[内存查询模块]:";
	
	// key = type_value; eg : decorateBudget_1, value = SysDictionary
	private static Map<String, SysDictionary> sysDictionaryMap = new ConcurrentHashMap<String, SysDictionary>();
	
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	@Override
	public SysDictionary getSysDictionaryByTypeAndValue(String type, Integer value) {
		// ------参数验证 ->start
		if(StringUtils.isEmpty(type)) {
			log.error(LOGPREFIX + "StringUtils.isEmpty(type) = true");
			return null;
		}
		if(value == null) {
			log.error(LOGPREFIX + "value = null");
			return null;
		}
		// ------参数验证 ->end
		
		String key = type + "_" + value;
		
		if(sysDictionaryMap.containsKey(key) && sysDictionaryMap.get(key) != null) {
			return sysDictionaryMap.get(key);
		}else {
			SysDictionary sysDictionary = sysDictionaryService.getSysDictionary(type, value);
			if(sysDictionary != null) {
				sysDictionaryMap.put(key, sysDictionary);
			}
			return sysDictionary;
		}
	}

}
