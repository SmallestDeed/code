package com.sandu.home.dao;

import com.sandu.home.model.HouseSpaceNew;
import com.sandu.home.model.search.HouseSpaceNewSearch;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @version V1.0
 * @Title: HouseSpaceNewMapper.java
 * @Package com.sandu.business.dao
 * @Description:户型空间-户型空间U3DMapper
 * @createAuthor pandajun
 * @CreateDate 2017-05-18 15:19:17
 */
@Repository
public interface HouseSpaceNewMapper {
    int insertSelective(HouseSpaceNew record);

    int updateByPrimaryKeySelective(HouseSpaceNew record);

    int deleteByPrimaryKey(Integer id);

    HouseSpaceNew selectByPrimaryKey(Integer id);

    int selectCount(HouseSpaceNewSearch houseSpaceNewSearch);

    List<HouseSpaceNew> selectPaginatedList(
            HouseSpaceNewSearch houseSpaceNewSearch);

    List<HouseSpaceNew> selectList(HouseSpaceNew houseSpaceNew);

    List<String> getSpaceTypeListByHouseId(Integer houseId);
    /**
     * 通过户型ID获取该户型下有哪些空间类型 
     * @param houseId
     * @return
     * add by yangzhun
     * 2018年1月17日15:09:55
     */
    List<String> getSpaceTypeByHouseId(Integer houseId);
}
