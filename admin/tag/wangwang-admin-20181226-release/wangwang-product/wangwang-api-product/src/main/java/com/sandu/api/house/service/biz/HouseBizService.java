package com.sandu.api.house.service.biz;

import com.github.pagehelper.PageInfo;
import com.sandu.api.house.input.HouseAdd;
import com.sandu.api.house.input.HouseQuery;
import com.sandu.api.house.input.HouseUpdate;
import com.sandu.api.house.model.House;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * house
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Jul-31 14:14
 */
public interface HouseBizService {

    /**
     * 创建
     *
     * @param input
     * @return
     */
    int create(HouseAdd input);

    /**
     * 更新
     *
     * @param input
     * @return
     */
    int update(HouseUpdate input);



    /**
     * 通过ID获取详情
     *
     * @param houseId
     * @return
     */
    House getById(int houseId);


}
