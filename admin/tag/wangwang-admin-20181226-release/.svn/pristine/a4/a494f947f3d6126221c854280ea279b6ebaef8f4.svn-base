package com.sandu.service.house.dao;

import com.sandu.api.house.input.HouseQuery;
import com.sandu.api.house.model.House;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * house
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Jul-31 14:14
 */
@Repository
public interface HouseMapper {
    int insert(House house);

    int updateByPrimaryKey(House house);

    int deleteByPrimaryKey(@Param("houseIds") Set<Integer> houseIds);

    House selectByPrimaryKey(@Param("houseId") int houseId);


    List<House> getByUserId(@Param("userId")Integer userId);
}
