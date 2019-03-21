package com.sandu.service.address.impl;

import com.sandu.api.address.input.MallUserAddressAdd;
import com.sandu.api.address.model.MallUserAddress;
import com.sandu.api.address.service.UserAddressService;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.util.Utils;
import com.sandu.service.address.dao.UserAddressDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户地址服务实现类
 * @author WangHaiLin
 * @date 2018/6/21  9:48
 */
@Service("userAddressService")
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    private UserAddressDao userAddressDao;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Long createUserAddress(MallUserAddressAdd addressAdd) {
        if (addressAdd==null) {
            return null;
        }
        //数据转换，构造用户地址
        MallUserAddress userAddress=new MallUserAddress();
        userAddress.setUserId(addressAdd.getUserId());
        userAddress.setAddressName(addressAdd.getAddressName());
        userAddress.setConsignee(addressAdd.getConsignee());
        userAddress.setProvince(addressAdd.getProvince());
        userAddress.setCity(addressAdd.getCity());
        userAddress.setDistrict(addressAdd.getDistrict());
        userAddress.setAddress(addressAdd.getAddress());
        userAddress.setZipcode(addressAdd.getZipcode());
        userAddress.setMobile(addressAdd.getMobile());
        SysUser sysUser = sysUserService.get(addressAdd.getUserId().intValue());
        userAddress.setCreator(sysUser.getNickName());
        userAddress.setGmtCreate(new Date());
        userAddress.setModifier(sysUser.getNickName());
        userAddress.setIsDeleted(0);
        userAddress.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
        //添加用户地址
        int result = userAddressDao.insert(userAddress);
        if (result>0){
            return userAddress.getId();
        }
        return null;
    }

    @Override
    public boolean removeUserAddress(Long addressId) {
        if (addressId==null) {
            return false;
        }
        return userAddressDao.delete(addressId)>0?true:false;
    }

    @Override
    public MallUserAddress getUserAddress(Long addressId) {
        if (addressId==null) {
            return null;
        }
        return userAddressDao.selectById(addressId);
    }

    @Override
    public List<MallUserAddress> getUserAddressList(Long userId) {
        if (userId==null) {
            return null;
        }
        return userAddressDao.selectList(userId);
    }
}
