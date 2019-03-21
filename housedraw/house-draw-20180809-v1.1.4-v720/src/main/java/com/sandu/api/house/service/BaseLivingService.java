package com.sandu.api.house.service;

import java.util.List;

import com.sandu.api.house.bo.BaseAreaBO;
import com.sandu.api.house.bo.LivingCodeBO;
import com.sandu.api.house.model.BaseLiving;

/**
 * Description:
 * 小区地理区域逻辑类
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/29
 */
public interface BaseLivingService {

    /**
     * 小区列表 省市区 + 小区
     * @return
     */
    List<BaseAreaBO> listBaseLiving();

    /**
     * 获取小区信息
     * @param livingId
     * @return
     */
    BaseLiving getLiving(Long livingId);

    /**
     * 获取小区列表 只有小区信息
     * @param areaCode
     * @return
     */
    List<BaseLiving> listLivings(String areaCode,String livingName);
    
    String getDetailAddress(String...args);
    
    int insertSelective(BaseLiving living);
    
    int updateByPrimaryKeySelective(BaseLiving living);
    
    LivingCodeBO getLivingCode(String areaId);
    
    List<BaseLiving> getLivingForSave(String areaCode, String livingName);

    BaseLiving getLivingByName(String areaId, String livingName);
}
