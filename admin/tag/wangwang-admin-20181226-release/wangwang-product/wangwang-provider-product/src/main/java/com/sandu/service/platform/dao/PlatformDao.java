package com.sandu.service.platform.dao;

import com.sandu.api.platform.model.Platform;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Sandu
 * @version V1.0
 * @Title: Platform Mapper.java
 * @Package com.nork.platform.dao
 * @Description:基础-平台表Mapper
 * @createAuthor pandajun
 * @CreateDate 2017-12-29 10:16:41
 */
@Repository
public interface PlatformDao {
    int insertSelective(Platform record);

    int updateByPrimaryKeySelective(Platform record);

    int deleteByPrimaryKey(Integer id);

    Platform selectByPrimaryKey(Integer id);

    List<Platform> selectList(Platform platform);

    int deleteById(@Param("id") Integer id);

    Platform getByPlatformCode(@Param("platformCode") String platformCode);

    List<Integer> groupPlatformWithBussinessType(@Param("list") List<Integer> usedPlatformIds,
                                                 @Param("bussinessType") String bussinessType);

    List<Integer> getPlatformIdsByBussinessTypes(@Param("list") List<String> bussinessTypes);

    Integer getOnePlatformIdByBussinessType(@Param("type") String type);

    List<Map<String, Object>> getAllPlatformIdAndName();

    List<Platform> getPlatformIdAndNameByBussinessType(String type);

	List<Map<String, Object>> getUpDownPlatformIdAndName();
}
