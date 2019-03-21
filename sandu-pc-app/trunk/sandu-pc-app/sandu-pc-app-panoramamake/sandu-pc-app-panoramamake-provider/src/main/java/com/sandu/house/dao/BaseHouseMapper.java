package com.sandu.house.dao;

import com.sandu.home.model.po.BaseHouseSpaceNumPO;
import com.sandu.house.model.input.BaseHouseSearch;
import com.sandu.house.model.output.BaseHouseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseHouseMapper {

    /**
     * 获取渲染过的户型数量
     * @param search
     * @return
     */
    int getMyUsedCount(BaseHouseSearch search);

    /**
     * 获取渲染过的户型
     * @param search
     * @return
     */
    List<BaseHouseVo> getMyUsed(BaseHouseSearch search);

    /**
     * 通过小区名称获取小区IDS TEMP
     * @param livingName
     * @return
     */
    List<Integer> getIdsByName(String livingName);

    /**
     * 通过户型Id集合查询户型下各房型对应空间数量
     * @param houseIds
     * @return
     */
    List<BaseHouseSpaceNumPO> getHouseSpaceNumInfo(@Param(value ="houseIds") List<Integer> houseIds);
}
