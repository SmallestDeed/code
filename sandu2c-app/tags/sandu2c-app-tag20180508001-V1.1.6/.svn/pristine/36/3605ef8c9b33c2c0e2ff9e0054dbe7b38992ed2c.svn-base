package com.sandu.supplydemand.service;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 6:00 2018/4/27 0027
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */


import com.sandu.supplydemand.input.BaseSupplyDemandAdd;
import com.sandu.supplydemand.model.BaseSupplyDemand;
import com.sandu.supplydemand.output.BaseSupplyDemandVo;

import java.util.List;

/**
 * @Title: 供求信息接口
 * @Package 供求信息
 * @Description:
 * @author weisheng
 * @date 2018/4/27 0027PM 6:00
 */
public interface SupplyDemandService {
    List<BaseSupplyDemand> getAllSupplyDemandInfo(BaseSupplyDemandAdd baseSupplyDemandAdd);

    Integer getAllSupplyDemandCount(BaseSupplyDemandAdd baseSupplyDemandAdd);

    Integer updateMySupplyDemandStatus(BaseSupplyDemandAdd baseSupplyDemandAdd);

    BaseSupplyDemand getSupplyDemandInfo(Integer supplyDemandId);

    Integer updateSupplyDemandInfo(BaseSupplyDemand baseSupplyDemand);

    Integer addSupplyDemandInfo(BaseSupplyDemand baseSupplyDemand);

    List<String> getAllSupplyDemandCategoryByOneCategory(Integer integer);

    Integer updateSupplyDemandInfoViewNum(BaseSupplyDemand newBaseSupplyDemand);
}
