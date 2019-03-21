package com.sandu.service.supply.dao;

import com.sandu.api.supply.model.SupplyDemandCategory;
import com.sandu.api.supply.model.SupplyDemandCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SupplyDemandCategoryMapper {
    int countByExample(SupplyDemandCategoryExample example);

    int deleteByExample(SupplyDemandCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SupplyDemandCategory record);

    int insertSelective(SupplyDemandCategory record);

    List<SupplyDemandCategory> selectByExample(SupplyDemandCategoryExample example);

    SupplyDemandCategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SupplyDemandCategory record, @Param("example") SupplyDemandCategoryExample example);

    int updateByExample(@Param("record") SupplyDemandCategory record, @Param("example") SupplyDemandCategoryExample example);

    int updateByPrimaryKeySelective(SupplyDemandCategory record);

    int updateByPrimaryKey(SupplyDemandCategory record);
}