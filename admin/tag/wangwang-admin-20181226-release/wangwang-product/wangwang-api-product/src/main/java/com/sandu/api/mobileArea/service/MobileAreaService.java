package com.sandu.api.mobileArea.service;

import com.sandu.api.mobileArea.model.MobileArea;

import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * mobileArea
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Jul-31 14:14
 */
public interface MobileAreaService {

    /**
     * 插入
     *
     * @param mobileArea
     * @return
     */
    int insert(MobileArea mobileArea);

    /**
     * 更新
     *
     * @param mobileArea
     * @return
     */
    int update(MobileArea mobileArea);

    /**
     * 删除
     *
     * @param mobileAreaIds
     * @return
     */
    int delete(Set<Integer> mobileAreaIds);

    /**
     * 通过ID获取详情
     *
     * @param mobileAreaId
     * @return
     */
     MobileArea getById(int mobileAreaId);

    /**
     * 根据手机号前7位查询
     * @param mobilePrefix
     * @return
     */
    List<MobileArea> selectByMobilePrefix(String mobilePrefix);
}
