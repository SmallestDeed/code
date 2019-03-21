package com.sandu.service.basesupplydemand.dao;


import com.sandu.api.basesupplydemand.model.SupplyDemandCategory;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SupplyDemandCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SupplyDemandCategory record);

    int insertSelective(SupplyDemandCategory record);

    SupplyDemandCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SupplyDemandCategory record);

    int updateByPrimaryKey(SupplyDemandCategory record);

    List<SupplyDemandCategory> querySupplyDemandCategory();
}