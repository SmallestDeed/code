package com.sandu.supplydemand.dao;


import com.sandu.designplan.model.DesignPlanRecommendedResult;
import com.sandu.supplydemand.input.BaseSupplyDemandAdd;
import com.sandu.supplydemand.model.BaseSupplyDemand;
import com.sandu.supplydemand.model.BaseSupplyDemandExample;
import com.sandu.supplydemand.model.DemandInfoRel;
import com.sandu.supplydemand.output.BaseSupplyDemandVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<BaseSupplyDemand> selectList(BaseSupplyDemandAdd baseSupplyDemandAdd);

    Integer selectListCount(BaseSupplyDemandAdd baseSupplyDemandAdd);

    BaseSupplyDemand selectSupplyDemandInfoByid(Integer supplyDemandId);

    int insertDemandInfoRel(DemandInfoRel demandInfoRel);

    DesignPlanRecommendedResult getDesignPlanRecommendedInfo(Integer planId);

    List<Integer> selectDesignPlanIdsFromRecord(Integer userId);

    List<DemandInfoRel> selectDemandInfoRelBySupplyDemandId(Integer id);

    int updateDemandInfoRel(DemandInfoRel demandInfoRel);
}