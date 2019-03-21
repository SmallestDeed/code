package com.sandu.service.base.dao;

import com.sandu.api.base.model.BasePlatform;
import com.sandu.api.user.output.BasePlatformVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BasePlatformDao {
	
	BasePlatform selectByPlatformCode(@Param("platformCode") String platformCode);

    List<BasePlatform> queryList();

    List<BasePlatformVO> listPlatform(String bussinessType);

    Integer getUsedPlatformQty(@Param("businessAdministrationId") Long businessAdministrationId,
                               @Param("platformCode") String platformCode);

    List<BasePlatform> queryByIds(@Param("platformIds")Set<Long> platformIds);

    List<BasePlatform> getPC2BAndMobile2BPlatform(List<Long> platforms);
}
