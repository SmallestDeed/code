package com.sandu.api.house.service;

import java.util.List;

import com.sandu.api.house.bo.SystemDictionaryBO;
import com.sandu.api.house.input.SystemDictionarySearch;
import com.sandu.api.house.model.SystemDictionary;

/**
 * Description:
 * 数据字典类
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/29
 */
public interface SystemDictionaryService {
	
    /**
     * 获取数据字典数据
     * @param search
     * @return
     */
    List<SystemDictionary> listSysDictionary(SystemDictionarySearch search);
    
    List<SystemDictionary> listHardBaimoType(SystemDictionarySearch search);
    
    List<SystemDictionary> listHardBaimoSamllType(SystemDictionarySearch search);
    
    /**
     * 软装白膜分类 
     * @param search
     * @return
     */
    List<SystemDictionaryBO> listSoftBiamoType(SystemDictionarySearch search);

	SystemDictionary findOneByValueKey(String spaceCommonFunctionValueKey);

	SystemDictionary findOneByTypeAndArea(String valuekey, Double valueOf);
}
