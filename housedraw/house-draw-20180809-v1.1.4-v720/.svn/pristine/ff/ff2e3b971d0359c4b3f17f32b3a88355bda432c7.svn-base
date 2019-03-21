package com.sandu.service.pic.dao;

import java.util.List;

import com.sandu.api.space.bo.DrawSpaceFileBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.house.model.DrawResHousePic;

/**
 * Description:
 * 户型图片数据库操作类
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/28
 */
@Repository
public interface DrawResHousePicMapper {

    Integer deleteDrawResHousePic(@Param("args")Integer[] args);

    void saveDrawResHousePic(DrawResHousePic pic);

    DrawResHousePic getDrawResHousePic(Long id);

    /**
     * 保存数据至 res_house_pic
     * @param drawResHousePic
     */
    Long saveResHousePic(DrawResHousePic drawResHousePic);

	void addBatchPic(List<DrawResHousePic> list);

	int update(DrawResHousePic drawPic);

	DrawResHousePic selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(DrawResHousePic pic);

    int insertSelective(DrawResHousePic pic);

    List<DrawSpaceFileBO> listDrawResHousePicByIds(@Param("picIds") List<Long> picIds);

    List<DrawResHousePic> listDrawHousePicByIds(@Param("picIds") List<Long> picIds);
}
