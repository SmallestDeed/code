package com.nork.ramCache.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.nork.product.model.BaseCompany;
import com.nork.product.model.BaseProductStyle;
import com.nork.product.service.BaseCompanyService;
import com.nork.product.service.BaseProductStyleService;
import com.nork.ramCache.service.RAMCacheService;
import com.nork.system.model.BaseArea;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUser;
import com.nork.system.service.BaseAreaService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserService;

@Service("ramCacheService")
public class RAMCacheServiceImpl implements RAMCacheService {

	private final static Logger LOGGER = LoggerFactory.getLogger(RAMCacheServiceImpl.class);
	
	private final static String LOGPREFIX = "[内存查询模块]:";
	
	// key = baseArea.areaCode, value = BaseArea
	private static Map<String, BaseArea> baseAreaMap = new ConcurrentHashMap<String, BaseArea>();
	
	// key = baseProductStyle.id, value = BaseProductStyle
	private static Map<Integer, BaseProductStyle> baseProductStyleMap = new ConcurrentHashMap<Integer, BaseProductStyle>();
	
	// key = type_value; eg : decorateBudget_1, value = SysDictionary
	private static Map<String, SysDictionary> sysDictionaryMap = new ConcurrentHashMap<String, SysDictionary>();
	
	// key = sysUser.id value = SysUser
	private static Map<Long, SysUser> sysUserMap = new ConcurrentHashMap<Long, SysUser>();
	
	@Autowired
	private BaseAreaService baseAreaService;
	
	@Autowired
	private BaseProductStyleService baseProductStyleService;
	
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private BaseCompanyService baseCompanyService;
	
	@Override
	public BaseArea getBaseAreaByAreaCode(String areaCode) {
		// ------参数验证 ->start
		if(StringUtils.isEmpty(areaCode)) {
			LOGGER.error(LOGPREFIX + "StringUtils.isEmpty(provinceCode) = true");
			return null;
		}
		// ------参数验证 ->end
		
		if(baseAreaMap.containsKey(areaCode) && baseAreaMap.get(areaCode) != null) {
			return baseAreaMap.get(areaCode);
		}else {
			BaseArea baseArea = baseAreaService.findOneByAreaCode(areaCode);
			if(baseArea != null) {
				baseAreaMap.put(baseArea.getAreaCode(), baseArea);
			}
			return baseArea;
		}
	}
	
	@Override
	public BaseProductStyle getBaseProductStyleById(Integer id) {
		// ------参数验证 ->start
		if(id == null) {
			LOGGER.error(LOGPREFIX + "id = null");
			return null;
		}
		// ------参数验证 ->end
		
		if(baseProductStyleMap.containsKey(id) && baseProductStyleMap.get(id) != null) {
			return baseProductStyleMap.get(id);
		}else {
			BaseProductStyle baseProductStyle = baseProductStyleService.get(id);
			if(baseProductStyle != null) {
				baseProductStyleMap.put(id, baseProductStyle);
			}
			return baseProductStyle;
		}
	}

	@Override
	public SysDictionary getSysDictionaryByTypeAndValue(String type, Integer value) {
		// ------参数验证 ->start
		if(StringUtils.isEmpty(type)) {
			LOGGER.error(LOGPREFIX + "StringUtils.isEmpty(type) = true");
			return null;
		}
		if(value == null) {
			LOGGER.error(LOGPREFIX + "value = null");
			return null;
		}
		// ------参数验证 ->end
		
		String key = type + "_" + value;
		
		if(sysDictionaryMap.containsKey(key) && sysDictionaryMap.get(key) != null) {
			return sysDictionaryMap.get(key);
		}else {
			SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue(type, value);
			if(sysDictionary != null) {
				sysDictionaryMap.put(key, sysDictionary);
			}
			return sysDictionary;
		}
	}
	
	@Override
	public SysUser getSysUser(Long userId) {
		// ------参数验证 ->start
		if(userId == null) {
			LOGGER.error(LOGPREFIX + "userId = null");
			return null;
		}
		// ------参数验证 ->end
		
		if(sysUserMap.containsKey(userId) && sysUserMap.get(userId) != null) {
			return sysUserMap.get(userId);
		}else {
			SysUser sysUser = sysUserService.get(userId.intValue());
			if(sysUser != null && sysUser.getBusinessAdministrationId() != null) {
				BaseCompany baseCompany = baseCompanyService.get(sysUser.getBusinessAdministrationId());
				if(baseCompany != null) {
					sysUser.setpCompanyId(baseCompany.getPid() == null ? baseCompany.getId().longValue() : baseCompany.getPid().longValue());
					sysUserMap.put(userId, sysUser);
				}
			}
			return sysUser;
		}
	}
	
	@Override
	public void clearStaticMap() {
		LOGGER.info("{} clearStaticMap() begin", LOGPREFIX);
		baseAreaMap.clear();
		baseProductStyleMap.clear();
		sysDictionaryMap.clear();
		sysUserMap.clear();
		LOGGER.info("{} clearStaticMap() end", LOGPREFIX);
	}

}
