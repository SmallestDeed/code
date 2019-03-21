package com.sandu.service.house.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.house.bo.DrawBaseHouseBO;
import com.sandu.api.house.bo.DrawHouseResouceBO;
import com.sandu.api.house.input.DrawBaseHouseSearch;
import com.sandu.api.house.model.DrawBaseHouse;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2017/12/28
 */
@Repository
public interface DrawBaseHouseMapper {

	Long saveDrawBaseHouse(DrawBaseHouse drawBaseHouse);

	int updateDrawBaseHouse(DrawBaseHouse drawBaseHouse);

	int deleteById(Long id);

	DrawBaseHouse getBaseHouseById(Long id);

	DrawBaseHouse get(Long id);

	List<DrawBaseHouseBO> listDrawHouse(DrawBaseHouseSearch baseHouseQuery);

	Long countDrawHouse(DrawBaseHouseSearch query);

	int updateDrawHouseStatus(@Param("checkStatus") Integer checkStatus, @Param("rejectReason") String rejectReason, @Param("houseId") Long houseId);
	int updateDrawHouseStatus2(@Param("checkStatus") Integer checkStatus, @Param("rejectReason") String rejectReason,
							   @Param("houseId") Long houseId, @Param("houseStatus")Integer houseStatus);

	int updateDrawHouseStatus3(@Param("checkHouse") DrawBaseHouse checkHouse, @Param("checkStatus") Integer checkStatus);

	int updateStatusByHouseId(Long houseId, Integer status);

	/*List<DrawBaseHouseBO> selectHousesByLivingId(DrawBaseHouseSearch query);

	int selectCountHouseByLivingId(DrawBaseHouseSearch query);*/

	DrawBaseHouseBO selectHouseAndPicPathById(@Param("houseId") Long houseId);

	DrawBaseHouseBO selectHouseAndRestoreFile(@Param("houseId") Long houseId);

	// List<DrawBaseHouseBO> listDrawBaseHouse(BaseHouseQuery query);

	int selectConutHouses(DrawBaseHouseSearch query);

	List<DrawBaseHouseBO> getHouses(DrawBaseHouseSearch query);

	DrawBaseHouse selectByPrimaryKey(Long id);

	/**
	 * DrawBaseHouse ->BaseHouse
	 * 
	 * @author huangsongbo
	 * @param drawBaseHouse
	 */
	void transformToBaseHouse(DrawBaseHouse drawBaseHouse);

	int updateByPrimaryKeySelective(DrawBaseHouse drawBaseHouse);

	DrawBaseHouseBO selectDrawHouseRestoreFile(@Param("houseId") Long houseId);

	DrawHouseResouceBO getHouseFileResource(Long houseId);
	
	Integer insertSelective(DrawBaseHouse drawHouse);

	int lockPerfectHouse(@Param("houseId")Long houseId, @Param("userId")Long userId, @Param("lockStatus")Integer lockStatus, @Param("limitStatus")Integer limitStatus);
	
	List<DrawBaseHouseBO> listDrawHouseResource(@Param("isSnapPic")Integer isSnapPic, @Param("drawHouse")List<DrawBaseHouseBO> drawHouse);

	int selectCountAllHouses();

	Integer getDataType(Long houseId);

    String getSnapPicPathById(Long id);

	int restFailTask(@Param("status") Integer status, @Param("args") List<Map<String, Long>> args);

	List<Long> listBaseHouseByDrawHouseId(@Param("args") List<Map<String, Long>> args);
	
	Map<String, Object> getBaseHouseInfo(Long houseId);
	
	Map<String, Object> getUserByPhone(String phone);

	DrawBaseHouse getDrawHouseByBaseHouseId(Long baseHouseId);

    Integer fix2_3Task(@Param("failTasks") List<Map<String,Long>> failTasks);
}
