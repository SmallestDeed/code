package com.nork.designconfig.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.ModuleType;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.service.DesignRulesService;

/***
 * 设计规则缓存层
 * @author qiu.jun
 * @date 2016-05-15
 */
public class DesignRulesCacher {
    private static DesignRulesService designRulesService=SpringContextHolder.getBean(DesignRulesService.class);
	
	 public static List<DesignRules> getList(DesignRules designRules){
		List<DesignRules> lstRules=Lists.newArrayList();
		Map<String,String> map=new HashMap<String,String>(); 
		if(StringUtils.isNotBlank(designRules.getRulesType())){
			map.put("rulesType", designRules.getRulesType());
		}
		if(StringUtils.isNotBlank(designRules.getRulesLevel())){
			map.put("rulesLevel", designRules.getRulesLevel());
		}
		if(StringUtils.isNotBlank(designRules.getRulesBusiness())){
			map.put("rulesBusiness", designRules.getRulesBusiness());
		}
		if(StringUtils.isNoneBlank(designRules.getRulesMainValue())){
			map.put("rulesMainValue", designRules.getRulesMainValue());
		}
		if(StringUtils.isNoneBlank(designRules.getRulesMainObj())){
			map.put("rulesMainObj", designRules.getRulesMainObj());
		}
		String key=KeyGenerator.getAllListKeyWithMap(ModuleType.DesignRules, map);
		lstRules=CacheManager.getInstance().getCacher().getList(DesignRules.class, key);
		if(CustomerListUtils.isEmpty(lstRules)){
			lstRules=designRulesService.getList(designRules);
			if(CustomerListUtils.isNotEmpty(lstRules)){
				CacheManager.getInstance().getCacher().setObject(key, lstRules, 0);
			}
		}
		return lstRules;
	} 
}
