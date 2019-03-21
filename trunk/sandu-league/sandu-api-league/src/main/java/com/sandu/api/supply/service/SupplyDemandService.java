package com.sandu.api.supply.service;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 6:00 2018/4/27 0027
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.api.supply.input.SupplyDemandAdd;
import com.sandu.api.supply.model.user.LoginUser;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Title: 供求信息接口
 * @Package 供求信息
 * @Description:
 * @author weisheng
 * @date 2018/4/27 0027PM 6:00
 */
public interface SupplyDemandService {

    int add(SupplyDemandAdd supplyDemandAdd, LoginUser loginUser, MultipartFile[] files);
}
