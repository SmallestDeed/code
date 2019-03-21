package com.sandu.service.fullhouse.dao;


import com.sandu.api.fullhouse.model.FullHouseDesignPlanDetail;
import com.sandu.api.fullhouse.output.DesignPlanVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FullHouseDesignPlanDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FullHouseDesignPlanDetail record);

    int insertSelective(FullHouseDesignPlanDetail record);

    FullHouseDesignPlanDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FullHouseDesignPlanDetail record);

    int updateByPrimaryKey(FullHouseDesignPlanDetail record);

    int insertList(List<FullHouseDesignPlanDetail> detailList);

    int logicDeleteByFullHouseDesignPlanId(Integer id);

    List<DesignPlanVO> selectSingleSpaceDesignPlanList(Integer fullHouseId);

    int logicDeletedByFullHouseDesignPlanId(Integer fullHouseDesignPlanId);

    int updateDetail(FullHouseDesignPlanDetail updateData);
}