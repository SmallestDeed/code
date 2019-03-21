package com.sandu.useraddress.dao;


import com.sandu.useraddress.MallUserAddress;

import java.util.List;

public interface MallUserAddressMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(MallUserAddress record);

    int insertSelective(MallUserAddress record);


    MallUserAddress selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(MallUserAddress record);

    int updateByPrimaryKey(MallUserAddress record);
    
    List<MallUserAddress> getAddressByUserId(Integer userId);

    MallUserAddress getAddressById(Integer addressId);
}