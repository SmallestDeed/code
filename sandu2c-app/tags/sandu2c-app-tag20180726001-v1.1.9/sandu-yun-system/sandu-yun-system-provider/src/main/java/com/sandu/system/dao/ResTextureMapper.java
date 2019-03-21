package com.sandu.system.dao;

import com.sandu.system.model.ResTexture;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version V1.0
 * @Title: ResTextureMapper.java
 * @Package com.sandu.system.dao
 * @Description:系统模块-材质库Mapper
 * @createAuthor pandajun
 * @CreateDate 2016-06-30 14:10:42
 */
@Repository
public interface ResTextureMapper {
    int insertSelective(ResTexture record);

    int updateByPrimaryKeySelective(ResTexture record);

    int deleteByPrimaryKey(Integer id);

    ResTexture selectByPrimaryKey(Integer id);

	List<ResTexture> getBatchGet(ResTexture resTexture);
}
