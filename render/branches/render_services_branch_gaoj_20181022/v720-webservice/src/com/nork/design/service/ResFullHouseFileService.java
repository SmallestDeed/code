package com.nork.design.service;

import com.nork.resource.model.ResFullHouseFile;

/**
 * 全屋obj文件
 * Created by chenm on 2018/10/8.
 */
public interface ResFullHouseFileService {

    int deleteByPrimaryKey(Integer id);

    int insert(ResFullHouseFile record);

    int insertSelective(ResFullHouseFile record);

    ResFullHouseFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ResFullHouseFile record);

    int updateByPrimaryKey(ResFullHouseFile record);

}
