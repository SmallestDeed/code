package com.sandu.service.base.dao;

import com.sandu.api.base.output.PicBean;
import com.sandu.supplydemand.model.SupplyDemandPic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyDemandPicMapper {
    List<SupplyDemandPic> selectByIds(@Param("ids") List<Integer> ids);

    PicBean getHousePicByHouseId(Integer houseId);

}