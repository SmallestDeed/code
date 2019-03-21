package com.sandu.api.house.service;

import com.sandu.api.house.model.House;

import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * house
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Jul-31 14:14
 */
public interface HouseService {

    /**
     * 插入
     *
     * @param house
     * @return
     */
    int insert(House house);

    /**
     * 更新
     *
     * @param house
     * @return
     */
    int update(House house);

    /**
     * 删除
     *
     * @param houseIds
     * @return
     */
    int delete(Set<Integer> houseIds);

    /**
     * 通过ID获取详情
     *
     * @param houseId
     * @return
     */
     House getById(int houseId);

    /**
     * 通过UserId获取详情
     *
     * @param UserId
     * @return
     */
    List<House> getByUserId(int UserId);


}
