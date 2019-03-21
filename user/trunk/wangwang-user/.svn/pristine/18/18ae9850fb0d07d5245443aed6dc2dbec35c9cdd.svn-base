package com.sandu.api.address.service;

import com.sandu.api.address.input.MallUserAddressAdd;
import com.sandu.api.address.model.MallUserAddress;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户地址服务
 * @author WangHaiLin
 * @date 2018/6/21  9:45
 */
@Component
public interface UserAddressService {

    /**
     * 新增用户地址
     * @param addressAdd 新增入参
     * @return long 新增数据Id
     */
    Long createUserAddress(MallUserAddressAdd addressAdd);

    /**
     * 删除用户地址
     * @param addressId 地址Id
     * @return 删除操作结果：true 删除成功；false 删除失败
     */
    boolean removeUserAddress(Long addressId);

    /**
     * 获取用户地址详情
     * @param addressId 用户地址Id
     * @return 用户地址
     */
    MallUserAddress getUserAddress(Long addressId);

    /**
     * 获取用户地址列表
     * @param userId 用户Id
     * @return list 用户地址列表
     */
    List<MallUserAddress> getUserAddressList(Long userId);

}
