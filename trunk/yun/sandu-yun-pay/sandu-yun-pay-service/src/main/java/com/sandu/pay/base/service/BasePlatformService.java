package com.sandu.pay.base.service;

import com.sandu.pay.base.model.BasePlatform;

/**
 * 平台
 *
 * @Author yzw
 * @Date 2018/1/8 15:31
 */
public interface BasePlatformService {

    /**
     * 添加
     *
     * @param record
     * @return
     */
    BasePlatform add(BasePlatform record);

    /**
     * 删除
     *
     * @param id 平台id
     * @return
     */
    boolean delete(Integer id);

    /**
     * 更新
     *
     * @param record 平台id
     * @return
     */
    BasePlatform update(BasePlatform record);

    /**
     * 获取
     *
     * @param id 平台id
     * @return
     */
    BasePlatform get(Integer id);

    /**
     * 获取平台信息
     *
     * @param platformCode 平台编码
     * @return
     */
    public BasePlatform getBasePlatformByCode(String platformCode);
}
