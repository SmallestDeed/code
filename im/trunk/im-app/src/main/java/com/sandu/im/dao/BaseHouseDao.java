package com.sandu.im.dao;

import com.sandu.im.model.BaseHouse;
import com.sandu.im.model.ResHousePic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BaseHouseDao {
    List<BaseHouse> selectBaseHouseInfo(@Param("houseIds") Set<Integer> houseIds);

    ResHousePic getHousePicPath(Integer picId);

    String getLivingName(String code);

    List<String> getSpaceTypeListByHouseId(Integer id);
}
