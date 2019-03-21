package com.sandu.service.space.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.house.bo.DrawSpaceCommonBO;
import com.sandu.api.house.model.DrawSpaceCommon;
import com.sandu.api.house.model.HouseSpaceNew;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/29
 */
@Repository
public interface DrawSpaceCommonMapper {

    List<DrawSpaceCommonBO> listSpaceCommon(Long houseId);

    Integer countSpaceCommonBySpaceCode(@Param("spaceCodeSuffix") String spaceCodeSuffix);

    Long saveSpaceCommon(DrawSpaceCommon spaceCommon);

    void saveHouseSpaceNew(HouseSpaceNew relation);

    /**
     *  维护空间灯光模型文件关系
     * @param resModelId 模型文件id
     * @param spaceId 空间id
     * @param modelType 类型
     */
    void updateSpaceCommon(@Param("resModelId") Long resModelId, @Param("spaceId") Long spaceId, @Param("modelType") String modelType);

    DrawSpaceCommon get(Long id);
    DrawSpaceCommon get2(Long id);

    Long saveDrawSpaceCommon(DrawSpaceCommon drawSpaceCommon);

    void saveDrawHouseAndSpaceRelation(HouseSpaceNew relation);

    void delete(@Param("spaceIds") List<Long> spaceIds);

    void deleteDrawHouseSpaceRelation(Long houseId);
    
    int updateByPrimaryKeySelective(DrawSpaceCommon drawSpaceCommon);

	DrawSpaceCommon selectByPrimaryKey(Long id);

	void transformToSpaceCommon(DrawSpaceCommon drawSpaceCommon);
	
	List<Long> getDealDrawSpaceCommon(@Param("isDeleted") Integer isDeleted, @Param("houseId")Long houseId);

	int deleteDrawSpaceCommon(@Param("status")Integer status, @Param("emptySpaces")List<Long> emptySpaces);

	List<Long> getDealSpaceCommon(Long spaceId);

	List<Long> getEmptyDealSpaceCommon(Long houseId);

	Integer batchSaveDrawSpaceCommon(List<DrawSpaceCommon> drawSpaces) ;

    List<Long> getClearCupboardSpaceBySubmit(Long houseId);

    List<Long> getClearCupboardSpaceByCallback(Long baseHouseId);

    List<DrawSpaceCommonBO> listSpaceCommonByIds(@Param("drawSpaces") List<DrawSpaceCommon> drawSpaces);

    DrawSpaceCommon getBakeSpaceCommon(Long drawSpaceId);
}
