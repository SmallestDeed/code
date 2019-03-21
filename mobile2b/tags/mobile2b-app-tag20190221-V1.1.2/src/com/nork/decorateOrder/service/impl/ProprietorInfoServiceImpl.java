package com.nork.decorateOrder.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.decorateOrder.model.ProprietorInfo;
import com.nork.decorateOrder.service.ProprietorInfoService;
import com.nork.ramCache.service.RAMCacheService;
import com.nork.system.model.BaseArea;
import com.nork.system.model.SysDictionary;

@Service("proprietorInfoService")
public class ProprietorInfoServiceImpl implements ProprietorInfoService {

	private final static Logger LOGGER = LoggerFactory.getLogger(ProprietorInfoServiceImpl.class);
	
	private final static String LOGPREFIX = "[业主信息模块]:";
	
	@Autowired
	private RAMCacheService ramCacheService;
	
	@Override
	public ProprietorInfo setMoreInfo(ProprietorInfo proprietorInfo) {
		// 参数验证 ->start
		if(proprietorInfo == null) {
			LOGGER.error(LOGPREFIX + "proprietorInfo = null");
			return null;
		}
		// 参数验证 ->end
		
		// ------省市信息 ->start
		BaseArea baseAreaProprietor = ramCacheService.getBaseAreaByAreaCode(proprietorInfo.getProvinceCode());
		proprietorInfo.setProvinceInfo(baseAreaProprietor == null ? null : baseAreaProprietor.getAreaName());
		
		BaseArea baseAreaCity = ramCacheService.getBaseAreaByAreaCode(proprietorInfo.getCityCode());
		proprietorInfo.setCityInfo(baseAreaCity == null ? null : baseAreaCity.getAreaName());
		// ------省市信息 ->end
		
		// ------装修风格 ->start
		/*BaseProductStyle baseProductStyle = ramCacheService.getBaseProductStyleById(proprietorInfo.getDecorateStyle());
		proprietorInfo.setDecorateStyleInfo(baseProductStyle == null ? null : baseProductStyle.getName());*/
		// 风格改为从数据字典取(type="goodStyle") update by huangsongbo 2018.11.06
		SysDictionary sysDictionaryStyle = ramCacheService.getSysDictionaryByTypeAndValue("goodStyle", proprietorInfo.getDecorateStyle());
		proprietorInfo.setDecorateStyleInfo(sysDictionaryStyle == null ? null : sysDictionaryStyle.getName());
		// ------装修风格 ->end
		
		// ------装修方式 ->start
		SysDictionary sysDictionary = ramCacheService.getSysDictionaryByTypeAndValue("decorateType", proprietorInfo.getDecorateType());
		proprietorInfo.setDecorateTypeInfo(sysDictionary == null ? null : sysDictionary.getName());
		// ------装修方式 ->end
		
		// ------用户预算 ->start
		SysDictionary sysDictionaryDecorateBudget = ramCacheService.getSysDictionaryByTypeAndValue("decorateBudget", proprietorInfo.getDecorateBudget());
		proprietorInfo.setDecorateBudgetInfo(sysDictionaryDecorateBudget == null ? null : sysDictionaryDecorateBudget.getName());
		// ------用户预算 ->end
		
		return proprietorInfo;
	}

}
