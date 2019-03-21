package com.sandu.service.customer.imp;

import com.sandu.api.area.output.BaseAreaBo;
import com.sandu.api.area.service.BaseAreaService;
import com.sandu.api.customer.service.CustomerCommonService;
import com.sandu.api.house.model.House;
import com.sandu.api.house.service.HouseService;
import com.sandu.api.mallUserAddress.model.MallUserAddress;
import com.sandu.api.mallUserAddress.service.MallUserAddressService;
import com.sandu.api.mobileArea.model.MobileArea;
import com.sandu.api.mobileArea.service.MobileAreaService;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("customerCommonService")
@Slf4j
public class CustomerCommonServiceImpl implements CustomerCommonService {


    @Resource
    private MallUserAddressService mallUserAddressService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private HouseService           houseService;
    @Resource
    private MobileAreaService      mobileAreaService;
    @Resource
    private BaseAreaService        baseAreaService;

    @Override
    public BaseAreaBo queryAreaInfoByUserId(Integer userId) {
        BaseAreaBo baseAreaBo = new BaseAreaBo();

        Map<String, String> map = baseAreaService.codeAndNameMap();
        if(map.isEmpty()){
            log.error("area-map:,map为空");
        }
        //   SELECT area_long_code FROM base_house -- 小区地址;
        List<House> houseList = houseService.getByUserId(userId);
        if (!CollectionUtils.isEmpty(houseList)) {
            String code = houseList.get(0).getAreaLongCode();
            if (code != null) {
                String[] codes = code.split("\\.");
                switch (codes.length) {
                    case 2:
                        baseAreaBo.setProvinceCode(codes[1]);
                        baseAreaBo.setProvinceName(map.get(baseAreaBo.getProvinceCode()));
                        break;
                    case 3:
                        baseAreaBo.setProvinceCode(codes[1]);
                        baseAreaBo.setCityCode(codes[2]);
                        baseAreaBo.setProvinceName(map.get(baseAreaBo.getProvinceCode()));
                        baseAreaBo.setCityName(map.get(baseAreaBo.getCityCode()));
                        break;
                    case 4:
                        baseAreaBo.setProvinceCode(codes[1]);
                        baseAreaBo.setCityCode(codes[2]);
                        baseAreaBo.setAreaCode(codes[3]);
                        baseAreaBo.setProvinceName(map.get(baseAreaBo.getProvinceCode()));
                        baseAreaBo.setCityName(map.get(baseAreaBo.getCityCode()));
                        baseAreaBo.setAreaName(map.get(baseAreaBo.getAreaCode()));
                        break;
                    case 5:
                        baseAreaBo.setProvinceCode(codes[1]);
                        baseAreaBo.setCityCode(codes[2]);
                        baseAreaBo.setAreaCode(codes[3]);
                        baseAreaBo.setStreetCode(codes[4]);
                        baseAreaBo.setProvinceName(map.get(baseAreaBo.getProvinceCode()));
                        baseAreaBo.setCityName(map.get(baseAreaBo.getCityCode()));
                        baseAreaBo.setAreaName(map.get(baseAreaBo.getAreaCode()));
                        baseAreaBo.setStreetName(map.get(baseAreaBo.getStreetCode()));
                        break;
                    default:
                        break;
                }
                if(baseAreaBo !=null){
                    log.info("区域对象-小区地址，baseAreaBo：{},userId:{}",baseAreaBo,userId);
                }
                return baseAreaBo;
            }
        }
        //   SELECT * FROM mall_user_address -- 收货地址
        List<MallUserAddress> mallUserAddressList = mallUserAddressService.getAddressByUserId(userId);
        if (!CollectionUtils.isEmpty(mallUserAddressList)) {
            MallUserAddress mallUserAddress = mallUserAddressList.get(0);
            //这里只要有省，说明是有地址的
            if (mallUserAddress.getProvince() != null) {
                baseAreaBo.setProvinceCode(mallUserAddress.getProvince());
                baseAreaBo.setCityCode(mallUserAddress.getCity());
                baseAreaBo.setAreaCode(mallUserAddress.getDistrict());

                baseAreaBo.setProvinceName(map.get(mallUserAddress.getProvince()));
                baseAreaBo.setCityName(map.get(mallUserAddress.getCity()));
                baseAreaBo.setAreaName(map.get(mallUserAddress.getDistrict()));

                if(baseAreaBo !=null){
                    log.info("区域对象-收货地址，baseAreaBo：{},userId:{}",baseAreaBo,userId);
                }
                return baseAreaBo;
            }
        }
        //   SELECT * FROM sys_user  -- 微信定位地址;
        SysUser sysUser = sysUserService.getUserById(Long.valueOf(userId));
        if (sysUser != null) {
            //这里只要有省，说明是有地址的
            if (sysUser.getProvinceCode() != null) {
                baseAreaBo.setProvinceCode(sysUser.getProvinceCode());
                baseAreaBo.setCityCode(sysUser.getCityCode());
                baseAreaBo.setAreaCode(sysUser.getAreaCode());
                baseAreaBo.setStreetCode(sysUser.getStreetCode());

                baseAreaBo.setProvinceName(map.get(sysUser.getProvinceCode()));
                baseAreaBo.setCityName(map.get(sysUser.getCityCode()));
                baseAreaBo.setAreaName(map.get(sysUser.getAreaCode()));
                baseAreaBo.setStreetName(map.get(sysUser.getStreetCode()));
                if(baseAreaBo !=null){
                    log.info("区域对象-微信定位地址，baseAreaBo：{},userId:{}",baseAreaBo,userId);
                }
                return baseAreaBo;
            }

            log.info("手机号：{}",sysUser.getMobile());
            //   SELECT * FROM `base_mobile_area`; -- 手机定位地址，带手机前七位去查
            Integer mobilePrefix = Integer.valueOf(sysUser.getMobile().substring(0,7));
            List<MobileArea> mobileAreaList = mobileAreaService.selectByMobilePrefix(mobilePrefix.toString());
            if (!CollectionUtils.isEmpty(mobileAreaList)) {
                MobileArea mobileArea = mobileAreaList.get(0);
                log.info("手机号：{}，手机定位地址:{}",sysUser.getMobile(),mobileArea);
                baseAreaBo.setProvinceCode(mobileArea.getProvinceCode());
                baseAreaBo.setCityCode(mobileArea.getCityCode());

                baseAreaBo.setProvinceName(map.get(mobileArea.getProvinceCode()));
                baseAreaBo.setCityName(map.get(mobileArea.getCityCode()));
                if(baseAreaBo !=null){
                    log.info("区域对象-手机定位地址，baseAreaBo：{},userId:{}",baseAreaBo,userId);
                }
                return baseAreaBo;
            }else {
                log.info("手机号：{},没有地址",sysUser.getMobile());
            }
        }

        return baseAreaBo;
    }

}
