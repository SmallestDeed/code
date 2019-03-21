package com.sandu.home.service;


import com.sandu.home.model.BaseHouseApply;
import com.sandu.user.model.SysUser;

/**
 * @version V1.0
 * @Title: BaseHouseApplyService.java
 * @Package com.sandu.home.service
 * @Description:户型房型-户型申请表Service
 * @createAuthor pandajun
 * @CreateDate 2016-10-13 11:45:31
 */
public interface BaseHouseApplyService {
    /**
     * 新增数据
     *
     * @param baseHouseApply
     * @return int
     */
    int add(BaseHouseApply baseHouseApply);

    /**
     * 更新数据
     *
     * @param baseHouseApply
     * @return int
     */
    int update(BaseHouseApply baseHouseApply);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    int delete(Integer id);

    /**
     * 获取数据详情
     *
     * @param id
     * @return BaseHouseApply
     */
    BaseHouseApply get(Integer id);

    BaseHouseApply sysSave(BaseHouseApply baseHouseApply, SysUser sysUser);

}
