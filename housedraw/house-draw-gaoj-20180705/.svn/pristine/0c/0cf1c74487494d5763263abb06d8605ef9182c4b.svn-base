package com.sandu.service.living.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.house.bo.BaseAreaBO;
import com.sandu.api.house.bo.LivingCodeBO;
import com.sandu.api.house.model.BaseLiving;

/**
 * Description:
 * 小区地理区域底层操作类
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/29
 */
@Repository
public interface BaseLivingMapper {

    List<BaseAreaBO> listAreas();

    List<BaseLiving> listLiving(@Param("areaCode") String areaCode,@Param("livingName")String livingName);

    BaseLiving get(Long livingId);
    
    List<BaseAreaBO> getDetailAddress(@Param("code1")String code1, @Param("code2")String code2, @Param("code3")String code3);
    
    int insertSelective(BaseLiving living);
    
    int updateByPrimaryKeySelective(BaseLiving living);
    
    LivingCodeBO getLivingCode(String areaId);
    
	List<BaseLiving> getLivingForSave(@Param("areaCode") String areaCode, @Param("livingName") String livingName);

    BaseLiving getLivingByName(@Param("areaId") String areaId, @Param("livingName")String livingName);
}
