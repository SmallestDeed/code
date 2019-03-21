package com.sandu.company.service;

import com.sandu.company.model.ProprietorInfo;
import org.springframework.stereotype.Component;

/**
 * 店铺预约服务 Service 层接口
 * @author WangHaiLin
 * @date 2018/9/28  13:58
 */
@Component
public interface ProprietorInfoService {
    /**
     * 新增预约服务
     * @param services 预约服务
     * @return
     */
    int insert(ProprietorInfo services);


}
