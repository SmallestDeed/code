package com.sandu.service.address.dao;

import com.sandu.api.address.model.MallUserAddress;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户服务Dao层接口
 * @author WangHaiLin
 * @date 2018/6/21  9:50
 */
@Repository
public interface UserAddressDao {
    /**
     * 新增用户地址
     * @param address 新增入参
     * @return int 操作结果
     */
    int insert(MallUserAddress address);

    /**
     * 删除用户地址
     * @param addressId 地址Id
     * @return 删除操作结果
     */
    int delete(@Param("addressId") Long addressId);

    /**
     * 获取用户地址详情
     * @param addressId 用户地址Id
     * @return 用户地址
     */
    MallUserAddress selectById(@Param("addressId") Long addressId);

    /**
     * 获取用户地址列表
     * @param userId 用户Id
     * @return list 用户地址列表
     */
    List<MallUserAddress> selectList(@Param("userId") Long userId);
}
