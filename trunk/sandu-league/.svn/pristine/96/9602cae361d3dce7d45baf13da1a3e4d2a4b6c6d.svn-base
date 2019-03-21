package com.sandu.service.supply.dao;

import com.sandu.api.supply.model.SupplyDemandPic;
import com.sandu.api.supply.model.SupplyDemandPicExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SupplyDemandPicMapper {
    int countByExample(SupplyDemandPicExample example);

    int deleteByExample(SupplyDemandPicExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SupplyDemandPic record);

    int insertSelective(SupplyDemandPic record);

    List<SupplyDemandPic> selectByExample(SupplyDemandPicExample example);

    SupplyDemandPic selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SupplyDemandPic record, @Param("example") SupplyDemandPicExample example);

    int updateByExample(@Param("record") SupplyDemandPic record, @Param("example") SupplyDemandPicExample example);

    int updateByPrimaryKeySelective(SupplyDemandPic record);

    int updateByPrimaryKey(SupplyDemandPic record);
}