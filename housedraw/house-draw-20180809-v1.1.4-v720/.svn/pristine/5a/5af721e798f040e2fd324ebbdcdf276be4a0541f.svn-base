package com.sandu.service.pic.dao;

import org.springframework.stereotype.Repository;

import com.sandu.api.res.model.ResHousePic;

@Repository
public interface ResHousePicMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ResHousePic record);

    int insertSelective(ResHousePic record);

    ResHousePic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ResHousePic record);

    int updateByPrimaryKey(ResHousePic record);

	int copyByPrimaryKey(Long snapPicId);
}