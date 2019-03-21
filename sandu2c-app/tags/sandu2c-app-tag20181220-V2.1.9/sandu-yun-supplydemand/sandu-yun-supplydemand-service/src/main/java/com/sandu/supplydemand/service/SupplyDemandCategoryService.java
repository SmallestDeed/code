package com.sandu.supplydemand.service;


import com.sandu.supplydemand.model.SupplyDemandCategory;
import com.sandu.supplydemand.output.SupplyDemandCategoryVo;

import java.util.List;

/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in AM 10:10 2018/5/7 0007
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */
public interface SupplyDemandCategoryService {
    List<SupplyDemandCategoryVo> getAllSupplyDemandCategory( SupplyDemandCategory supplyDemandCategory);

    List<SupplyDemandCategoryVo> selectCategoryName(List<Integer> categoryIdList);
}
