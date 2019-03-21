package com.sandu.system.dao;

import com.sandu.system.model.ResRenderPic;
import org.springframework.stereotype.Repository;

@Repository
public interface ResRenderPicMapper {

	int insertSelective(ResRenderPic record);

    int updateByPrimaryKeySelective(ResRenderPic record);

    int deleteByPrimaryKey(Integer id);

    ResRenderPic selectByPrimaryKey(Integer id);
	
}
