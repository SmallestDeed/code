package com.nork.system.service;

import com.nork.system.model.SysUserLevelPrice;

import java.util.List;

/**
 * Created by yanghz on 2017-08-15.
 */
public interface SysUserLevelPriceService {
    /**
     *add by yanghz
     * addUserLevelLimit 方法描述：根据用户id获取所属组群类型的价格等级列表
     *
     * @param userId
     * @return
     *
     * @return List<SysUserLevelPrice> 返回类型
     *
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    List<SysUserLevelPrice> getListByUserId(Integer userId);
    SysUserLevelPrice getIdByUserType(int userType);
}
