package com.sandu.service.house.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.house.bo.DrawBaseHouseBO;
import com.sandu.api.house.input.BaseHouseQuery;
import com.sandu.api.house.model.BaseHouse;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2018/1/2
 */

@Repository
public interface BaseHouseMapper {

	List<DrawBaseHouseBO> queryBaseHouse(BaseHouseQuery query);

	long countBaseHouse(BaseHouseQuery query);

	int updateHouseDealStatus(@Param("dealStatus") String dealStatus, @Param("id") Long id);

	int updateHouseDealStatusForLock(@Param("dealStatus") String dealStatus, @Param("userId") Long userId, @Param("id") Long id);

	BaseHouse selectByPrimaryKey(Long id);

	int updateHouseResouce(BaseHouse drawHouse);

	int restFailTask(@Param("status") String status, @Param("args") List<Long> args);

	int updateByPrimaryKeySelective(BaseHouse baseHouse);

    Integer fix2_3Task(@Param("failTasks") List<Map<String,Long>> failTasks);

    int countPutwayDesignTemplet(Long houseId);

    Integer getPutawayState(Long houseId);

    Integer resetDrawHouse(@Param("dealStatus") Integer dealStatus, @Param("houseIds") List<Integer> houseIds);
}
