package com.sandu.service.supply.dao;

import com.sandu.api.supply.model.BaseSupplyDemand;
import com.sandu.api.supply.model.BaseSupplyDemandExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BaseSupplyDemandMapper {
    int countByExample(BaseSupplyDemandExample example);

    int deleteByExample(BaseSupplyDemandExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BaseSupplyDemand record);

    int insertSelective(BaseSupplyDemand record);

    List<BaseSupplyDemand> selectByExample(BaseSupplyDemandExample example);

    BaseSupplyDemand selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BaseSupplyDemand record, @Param("example") BaseSupplyDemandExample example);

    int updateByExample(@Param("record") BaseSupplyDemand record, @Param("example") BaseSupplyDemandExample example);

    int updateByPrimaryKeySelective(BaseSupplyDemand record);

    int updateByPrimaryKey(BaseSupplyDemand record);
}