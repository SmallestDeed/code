package com.sandu.service.area.dao;

import com.sandu.api.area.model.BaseMobileArea;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author WangHaiLin
 * @date 2018/7/10  16:12
 */
@Repository
public interface BaseMobileAreaMapper {
    /**
     * 通过电话号码前七位查询电话号码归属地
     * @param mobilePrefix 电话号码前七位
     * @return BaseMobileArea 电话号码归属地
     */
    BaseMobileArea getAreaByMobile(@Param("mobilePrefix") String mobilePrefix);
}
