package com.sandu.system.dao;

import com.sandu.system.model.ResHousePic;
import org.springframework.stereotype.Repository;

/**
 * @version V1.0
 * @Title: ResHousePicMapper.java
 * @Package com.sandu.system.dao
 * @Description:系统模块-户型、空间图片资源表Mapper
 * @createAuthor pandajun
 * @CreateDate 2016-08-13 16:34:09
 */
@Repository
public interface ResHousePicMapper {
    int insertSelective(ResHousePic record);

    int updateByPrimaryKeySelective(ResHousePic record);

    int deleteByPrimaryKey(Integer id);

    ResHousePic selectByPrimaryKey(Integer id);
}
