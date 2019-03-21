package com.nork.resource.dao;


import com.nork.resource.model.ResFullHouseFile;
import org.springframework.stereotype.Repository;

/**
 * 全屋户型文件
 */
@Repository
public interface ResFullHouseFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ResFullHouseFile record);

    int insertSelective(ResFullHouseFile record);

    ResFullHouseFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ResFullHouseFile record);

    int updateByPrimaryKey(ResFullHouseFile record);
}