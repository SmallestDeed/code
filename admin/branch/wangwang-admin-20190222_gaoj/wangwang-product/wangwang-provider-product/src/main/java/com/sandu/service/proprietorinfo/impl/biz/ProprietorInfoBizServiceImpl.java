package com.sandu.service.proprietorinfo.impl.biz;

import com.sandu.api.area.model.BaseArea;
import com.sandu.api.area.output.BaseAreaListVO;
import com.sandu.api.company.model.Company;
import com.sandu.api.company.service.CompanyService;
import com.sandu.api.company.service.CompanyShopService;
import com.sandu.api.decoratecustomer.model.DecorateCustomer;
import com.sandu.api.decoratecustomer.service.DecorateCustomerService;
import com.sandu.api.decoratecustomer.service.biz.DecorateCustomerBizService;
import com.sandu.api.proprietorinfo.input.ProprietorInfoListQuery;
import com.sandu.api.proprietorinfo.input.ProprietorInfoUpdate;
import com.sandu.api.proprietorinfo.model.ProprietorInfo;
import com.sandu.api.proprietorinfo.output.*;
import com.sandu.api.proprietorinfo.service.ProprietorInfoService;
import com.sandu.api.proprietorinfo.service.biz.ProprietorInfoBizService;
import com.sandu.api.shop.model.CompanyShop;
import com.sandu.api.user.model.SysUser;
import com.sandu.system.model.SysDictionary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static com.sandu.api.proprietorinfo.common.constant.ProprietorInfoConstant.*;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * <p>DecorateOrder
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-18 14:00
 */
@Slf4j
@Service("proprietorInfoBizService")
public class ProprietorInfoBizServiceImpl implements ProprietorInfoBizService {
    private final ProprietorInfoService proprietorInfoService;
    private final DecorateCustomerService decorateCustomerService;
    private final CompanyService companyService;
    private final DecorateCustomerBizService decorateCustomerBizService;

    private final CompanyShopService companyShopService;

    @Autowired
    public ProprietorInfoBizServiceImpl(
            ProprietorInfoService proprietorInfoService,
            DecorateCustomerService decorateCustomerService,
            CompanyService companyService,
            DecorateCustomerBizService decorateCustomerBizService,
            CompanyShopService companyShopService) {
        this.proprietorInfoService = proprietorInfoService;
        this.decorateCustomerService = decorateCustomerService;
        this.companyService = companyService;
        this.decorateCustomerBizService = decorateCustomerBizService;
        this.companyShopService = companyShopService;
    }

    @Override
    public ProprietorInfo getById(int id) {
        return proprietorInfoService.getById(id);
    }

    @Override
    public Integer getVoListCount(ProprietorInfoListQuery proprietorInfoListQuery) {
        // 设置查询参数
        ProprietorInfo proprietorInfoQuery = new ProprietorInfo();
        BeanUtils.copyProperties(proprietorInfoListQuery, proprietorInfoQuery);
        proprietorInfoQuery.setType(proprietorInfoListQuery.getType());
        proprietorInfoQuery.setCityCode(proprietorInfoListQuery.getCityCode());
        proprietorInfoQuery.setCustomerType(proprietorInfoListQuery.getCustomerTypeValue());
        proprietorInfoQuery.setRevisitTime(proprietorInfoListQuery.getNextTime());
        proprietorInfoQuery.setBusinessType(new Byte("0"));

        proprietorInfoQuery.setDecorateBudget(proprietorInfoListQuery.getDecorateBudget());
        proprietorInfoQuery.setDecorateHouseType(proprietorInfoListQuery.getDecorateHouseType());
        proprietorInfoQuery.setDecorateStyle(proprietorInfoListQuery.getDecorateStyle());
        proprietorInfoQuery.setDecorateType(proprietorInfoListQuery.getDecorateType());
        proprietorInfoQuery.setHasDesign(proprietorInfoListQuery.getHasDesign());
        proprietorInfoQuery.setIsDeleted(0);

        return proprietorInfoService.getListCount(proprietorInfoQuery);
    }

    @Override
    public List<ProprietorInfoDetailVo> getVoList(ProprietorInfoListQuery proprietorInfoListQuery) {
        // 设置查询参数
        ProprietorInfo proprietorInfoQuery = new ProprietorInfo();
        BeanUtils.copyProperties(proprietorInfoListQuery, proprietorInfoQuery);
        proprietorInfoQuery.setType(proprietorInfoListQuery.getType());
        proprietorInfoQuery.setCityCode(proprietorInfoListQuery.getCityCode());
        proprietorInfoQuery.setCustomerType(proprietorInfoListQuery.getCustomerTypeValue());
        proprietorInfoQuery.setRevisitTime(proprietorInfoListQuery.getNextTime());
        proprietorInfoQuery.setBusinessType(new Byte("0"));

        proprietorInfoQuery.setDecorateBudget(proprietorInfoListQuery.getDecorateBudget());
        proprietorInfoQuery.setDecorateHouseType(proprietorInfoListQuery.getDecorateHouseType());
        proprietorInfoQuery.setDecorateStyle(proprietorInfoListQuery.getDecorateStyle());
        proprietorInfoQuery.setDecorateType(proprietorInfoListQuery.getDecorateType());
        proprietorInfoQuery.setHasDesign(proprietorInfoListQuery.getHasDesign());
        proprietorInfoQuery.setIsDeleted(0);

        // 查询业主信息列表
        int start = (proprietorInfoListQuery.getPage() - 1) * proprietorInfoListQuery.getPageSize();
        int limit = proprietorInfoListQuery.getPageSize();
        List<ProprietorInfo> proprietorInfoList =
                proprietorInfoService.getList(proprietorInfoQuery, start, limit);
        // 获取客户
        List<DecorateCustomer> beCustomers =
                decorateCustomerService.getCustomerByProprietorIds(
                        proprietorInfoList.stream().map(ProprietorInfo::getId).collect(Collectors.toList()));
        // 获取店铺信息
        Map<String, String> shopId2CompanyName =
                companyShopService.shopId2CompanyName(
                        proprietorInfoList
                                .stream()
                                .map(ProprietorInfo::getShopId)
                                .filter(Objects::nonNull)
                                .map(Long::intValue)
                                .collect(Collectors.toList()));
        // 封装返回对象
        if (proprietorInfoList.size() > 0) {
            List<ProprietorInfoDetailVo> voList = new ArrayList<>(proprietorInfoList.size());
            for (ProprietorInfo proprietorInfo : proprietorInfoList) {
                ProprietorInfoDetailVo vo = this.convertToProprietorInfoDetailVo(proprietorInfo);
                vo.setInitTime(proprietorInfo.getGmtCreated());
                vo.setDeleteFunc(true);
                vo.setShopName(shopId2CompanyName.get(proprietorInfo.getShopId() + ""));
                beCustomers
                        .stream()
                        .filter(it -> it.getProprietorInfoId().equals(vo.getId()))
                        .findFirst()
                        .ifPresent(it -> vo.setDeleteFunc(false));
                voList.add(vo);
            }
            return voList;
        }
        return Collections.emptyList();
    }

    @Override
    public ProprietorInfoEditVo getVoById(Integer id) {
        try {
            ProprietorInfo proprietorInfo = this.getById(id);
            if (proprietorInfo == null) {
                return null;
            }
            ProprietorInfoDetailVo proprietorInfoDetailVo =
                    this.convertToProprietorInfoDetailVo(proprietorInfo);
            ProprietorInfoEditVo proprietorInfoEditVo = new ProprietorInfoEditVo();
            proprietorInfoEditVo.setProprietorInfoDetailVo(proprietorInfoDetailVo);
            proprietorInfoEditVo.setProvinceList(getProvinceVoList());
            proprietorInfoEditVo.setDecorateTypeList(
                    this.getListFromSysDictionary(PROPRIETOR_INFO_DECORATE_TYPE, DecorateTypeVo.class));
            proprietorInfoEditVo.setDecorateBudgetList(
                    this.getListFromSysDictionary(PROPRIETOR_INFO_DECORATE_BUDGET, DecorateBudgetVo.class));
            proprietorInfoEditVo.setGoodStyleList(
                    this.getListFromSysDictionary(PROPRIETOR_INFO_DECORATE_STYLE, GoodStyleVo.class));
            proprietorInfoEditVo.setDecorateHouseTypeList(this.getDecorateHouseTypeVoList());
            proprietorInfoEditVo.setCustomerTypeList(
                    this.getListFromSysDictionary(PROPRIETOR_INFO_CUSTOMER_TYPE, CustomerTypeVo.class));
            return proprietorInfoEditVo;
        } catch (Exception e) {
            log.error("获取业主基础信息详情异常", e);
            throw new RuntimeException("获取业主基础信息详情异常");
        }
    }

    @Transactional
    @Override
    public Integer updateProprietorInfo(ProprietorInfoUpdate proprietorInfoUpdate) {
        // 更新业主基础信息
        int successNum = proprietorInfoService.updateProprietorInfo(proprietorInfoUpdate);
        if (successNum == 0) {
            throw new RuntimeException("更新业主基础信息失败");
        }
        // 如果是1类和2类客户，往decorate_customer表中插入数据
        if (proprietorInfoUpdate.getCustomerTypeValue() != null) {
            if (proprietorInfoUpdate.getCustomerTypeValue() == 1
                    || proprietorInfoUpdate.getCustomerTypeValue() == 2) {
                DecorateCustomer decorateCustomer =
                        decorateCustomerService.getByProprietorInfoId(proprietorInfoUpdate.getId());
                if (decorateCustomer == null) {
                    ProprietorInfo proprietorInfo =
                            proprietorInfoService.getById(proprietorInfoUpdate.getId());
                    // 填入装修客户的基本信息
                    decorateCustomer = this.getDecorateCustomerFromProprietorInfo(proprietorInfo);
                    // 客户类型：
                    //    0：店铺预约（通过店铺预约来的）
                    //    1：抢单（没有店铺和方案）
                    //    2：平台派单（有方案没店铺）
                    if (proprietorInfo.getType() == 3 || proprietorInfo.getShopId() != null) {
                        decorateCustomer.setCustomerType(0);
                        CompanyShop shop =
                                proprietorInfoService.getShopById(proprietorInfo.getShopId().intValue());
                        if (shop.getBusinessType() != null && shop.getBusinessType() == 5) {
                            decorateCustomer.setCompanyId(shop.getCompanyId());
                            decorateCustomerBizService.initOrderForCompanyCustomer();
                        }
                    } else if (proprietorInfo.getShopId() == null
                            && proprietorInfo.getDesignplanId() == null) {
                        decorateCustomer.setCustomerType(1);
                    } else if (proprietorInfo.getShopId() == null
                            && proprietorInfo.getDesignplanId() != null) {
                        decorateCustomer.setCustomerType(2);
                    } else {
                        throw new RuntimeException("无此类型客户");
                    }
                    successNum = decorateCustomerService.insert(decorateCustomer);
                    if (successNum == 0) {
                        throw new RuntimeException("插入装修客户信息失败");
                    }
                } else {
                    decorateCustomer.setModifier(proprietorInfoUpdate.getModifier());
                    decorateCustomer.setGmtModified(new Date());
                    decorateCustomer.setDecorateBudget(proprietorInfoUpdate.getDecorateBudgetValue());
                    decorateCustomerService.update(decorateCustomer);
                }
            }
        }
        return successNum;
    }

    @Override
    public List<ProvinceVo> getProvinceVoList() {
        List<BaseArea> provinceList =
                proprietorInfoService.getBaseAreaListByLevelId(BASE_AREA_LEVEL_ID_PROVINCE);
        List<BaseArea> cityList =
                proprietorInfoService.getBaseAreaListByLevelId(BASE_AREA_LEVEL_ID_CITY);
        if (provinceList == null
                || provinceList.size() <= 0
                || cityList == null
                || cityList.size() <= 0) {
            return null;
        }
        List<ProvinceVo> provinceVoList = new ArrayList<>(provinceList.size());
        for (BaseArea province : provinceList) {
            ProvinceVo provinceVo = new ProvinceVo();
            List<CityVo> cityVoList = new ArrayList<>();
            provinceVoList.add(provinceVo);
            provinceVo.setProvince(province.getAreaName());
            provinceVo.setProvinceCode(province.getAreaCode());
            provinceVo.setCityList(cityVoList);
            cityList
                    .stream()
                    .filter(item -> item.getPid().equals(province.getAreaCode()))
                    .forEach(
                            city -> {
                                CityVo cityVo = new CityVo();
                                cityVo.setCity(city.getAreaName());
                                cityVo.setCityCode(city.getAreaCode());
                                cityVoList.add(cityVo);
                            });
        }
        return provinceVoList;
    }

    @Override
    public List<CustomerTypeVo> getCustomerTypeList() {
        try {
            return this.getListFromSysDictionary(PROPRIETOR_INFO_CUSTOMER_TYPE, CustomerTypeVo.class);
        } catch (Exception e) {
            log.error("查询用户类型列表异常", e);
            throw new RuntimeException("查询用户类型列表异常");
        }
    }

    @Override
    public Map<Integer, ProprietorInfo> getByIds(List<Integer> ids) {
        List<ProprietorInfo> infoList = proprietorInfoService.getById(ids);
        Map<Integer, ProprietorInfo> dataMaps = new HashMap<>();
        if (infoList != null && infoList.size() > 0) {
            infoList.forEach(
                    s -> {
                        dataMaps.put(s.getId(), s);
                    });
        }
        return dataMaps;
    }

    @Override
    public SysUser getSysUserById(Integer id) {
        return proprietorInfoService.getSysUserById(id);
    }

    @Override
    public Integer deleteByIds(List<Integer> ids) {
        // 校验数据
        List<DecorateCustomer> customers = decorateCustomerService.getCustomerByProprietorIds(ids);
        ids.removeAll(
                customers.stream().map(DecorateCustomer::getProprietorInfoId).collect(Collectors.toList()));
        // 删除
        return proprietorInfoService.deleteByIds(ids);
    }

    private DecorateCustomer getDecorateCustomerFromProprietorInfo(ProprietorInfo proprietorInfo) {
        DecorateCustomer decorateCustomer = DecorateCustomer.builder().build();
        decorateCustomer.setProprietorInfoId(proprietorInfo.getId());
        decorateCustomer.setRemark(proprietorInfo.getRemark());
        decorateCustomer.setCreator(proprietorInfo.getModifier());
        decorateCustomer.setModifier(proprietorInfo.getModifier());
        decorateCustomer.setGmtCreate(proprietorInfo.getGmtModified());
        decorateCustomer.setGmtModified(proprietorInfo.getGmtModified());
        if (proprietorInfo.getDecorateBudget() != null) {
            decorateCustomer.setDecorateBudget(proprietorInfo.getDecorateBudget().intValue());
        }
        if (proprietorInfo.getDesignplanId() != null) {
            decorateCustomer.setDesignPlanId(proprietorInfo.getDesignplanId().intValue());
        }
        if (proprietorInfo.getRevisitTime() != null) {
            decorateCustomer.setRevisitTime(proprietorInfo.getRevisitTime());
        }
        decorateCustomer.setIsDeleted(0);
        return decorateCustomer;
    }

    private List<DecorateHouseTypeVo> getDecorateHouseTypeVoList() {
        List<DecorateHouseTypeVo> list = new ArrayList<>(2);
        DecorateHouseTypeVo decorateHouseTypeVoZero = new DecorateHouseTypeVo();
        decorateHouseTypeVoZero.setDecorateHouseTypeKey(0);
        decorateHouseTypeVoZero.setDecorateHouseType("新房装修");
        list.add(decorateHouseTypeVoZero);
        DecorateHouseTypeVo decorateHouseTypeVoOne = new DecorateHouseTypeVo();
        decorateHouseTypeVoOne.setDecorateHouseTypeKey(1);
        decorateHouseTypeVoOne.setDecorateHouseType("旧房改造");
        list.add(decorateHouseTypeVoOne);
        return list;
    }

    private <T> List<T> getListFromSysDictionary(String type, Class<T> clz) throws Exception {
        List<SysDictionary> sysDictionaryList = proprietorInfoService.findAllByType(type);
        if (sysDictionaryList == null || sysDictionaryList.size() <= 0) {
            return null;
        }
        List<T> list = new ArrayList<>(sysDictionaryList.size());
        for (SysDictionary sysDictionary : sysDictionaryList) {
            T t = clz.newInstance();
            StringBuilder baseFieldName = new StringBuilder();
            baseFieldName.append(type.substring(0, 1).toUpperCase()).append(type.substring(1));
            Method method = clz.getMethod("set" + baseFieldName.toString(), String.class);
            Method valueMethod = clz.getMethod("set" + baseFieldName.toString() + "Value", Integer.class);
            method.invoke(t, sysDictionary.getName());
            valueMethod.invoke(t, sysDictionary.getValue());
            list.add(t);
        }
        return list;
    }

    private ProprietorInfoDetailVo convertToProprietorInfoDetailVo(ProprietorInfo proprietorInfo) {
        ProprietorInfoDetailVo vo = new ProprietorInfoDetailVo();
        // 主键
        vo.setId(proprietorInfo.getId());
        // 需求类型
        if (proprietorInfo.getType() != null) {
            switch (proprietorInfo.getType()) {
                case PROPRIETOR_INFO_TYPE_ZERO:
                    vo.setRequirementType(PROPRIETOR_INFO_TYPE_ZERO_VALUE);
                    break;
                case PROPRIETOR_INFO_TYPE_ONE:
                    vo.setRequirementType(PROPRIETOR_INFO_TYPE_ONE_VALUE);
                    break;
                case PROPRIETOR_INFO_TYPE_THREE:
                    vo.setRequirementType(PROPRIETOR_INFO_TYPE_THREE_VALUE);
                    break;
                default:
                    break;
            }
        }

        // 上次回访时间
        vo.setLastTime(proprietorInfo.getGmtModified());
        // 下次回访时间
        vo.setNextTime(proprietorInfo.getRevisitTime());
        // 客户类型
        if (proprietorInfo.getCustomerType() != null) {
            SysDictionary sysDictionary =
                    proprietorInfoService.findOneByTypeAndValue(
                            PROPRIETOR_INFO_CUSTOMER_TYPE, proprietorInfo.getCustomerType().intValue());
            vo.setCustomerType(sysDictionary.getName());
            vo.setCustomerTypeValue(proprietorInfo.getCustomerType().intValue());
        }
        // 客户名称
        vo.setUserName(proprietorInfo.getUserName());
        // 省市，例：广东省深圳市
        if (proprietorInfo.getProvinceCode() != null) {
            BaseAreaListVO province =
                    proprietorInfoService.queryAreaByCode(proprietorInfo.getProvinceCode());
            if (province != null) {
                vo.setProvince(province.getAreaName());
                vo.setProvinceCode(province.getAreaCode());
                vo.setProvinceCity(province.getAreaName());
            }
        }
        if (proprietorInfo.getCityCode() != null) {
            BaseAreaListVO city = proprietorInfoService.queryAreaByCode(proprietorInfo.getCityCode());
            if (city != null) {
                vo.setCity(city.getAreaName());
                vo.setCityCode(city.getAreaCode());
                vo.setProvinceCity(vo.getProvinceCity() + city.getAreaName());
            }
        }
        // 小区名称
        vo.setHouseEstate(proprietorInfo.getAreaName());
        // 详细地址
        vo.setAddress(proprietorInfo.getAddress());
        // 户型面积
        vo.setHouseAcreage(proprietorInfo.getHouseAcreage());
        // 户型：N室N厅N卫
        vo.setHouse(
                getHouse(
                        proprietorInfo.getLivingRoomNum(),
                        proprietorInfo.getBedroomNum(),
                        proprietorInfo.getToiletNum()));
        vo.setLivingRoomNum(proprietorInfo.getLivingRoomNum());
        vo.setBedroomNum(proprietorInfo.getBedroomNum());
        vo.setToiletNum(proprietorInfo.getToiletNum());
        // 移动电话
        vo.setMobile(proprietorInfo.getMobile());
        // 装修类型
        if (proprietorInfo.getDecorateHouseType() != null) {
            vo.setDecorateHouseTypeKey(proprietorInfo.getDecorateHouseType().intValue());
            switch (proprietorInfo.getDecorateHouseType()) {
                case PROPRIETOR_INFO_DECORATE_HOUSE_TYPE_ZERO:
                    vo.setDecorateHouseType(PROPRIETOR_INFO_DECORATE_HOUSE_TYPE_ZERO_VALUE);
                    break;
                case PROPRIETOR_INFO_DECORATE_HOUSE_TYPE_ONE:
                    vo.setDecorateHouseType(PROPRIETOR_INFO_DECORATE_HOUSE_TYPE_ONE_VALUE);
                    break;
                default:
                    break;
            }
        }
        // 装修方式
        if (proprietorInfo.getDecorateType() != null) {
            SysDictionary sysDictionary =
                    proprietorInfoService.findOneByTypeAndValue(
                            PROPRIETOR_INFO_DECORATE_TYPE, proprietorInfo.getDecorateType().intValue());
            if (sysDictionary != null) {
                vo.setDecorateTypeValue(proprietorInfo.getDecorateType().intValue());
                vo.setDecorateType(sysDictionary.getName());
            }
        }
        // 装修风格
        if (proprietorInfo.getDecorateStyle() != null) {
            SysDictionary sysDictionary =
                    proprietorInfoService.findOneByTypeAndValue(
                            PROPRIETOR_INFO_DECORATE_STYLE, proprietorInfo.getDecorateStyle().intValue());
            if (sysDictionary != null) {
                vo.setGoodStyle(sysDictionary.getName());
                vo.setGoodStyleValue(sysDictionary.getValue());
            }
        }
        // 装修预算
        if (proprietorInfo.getDecorateBudget() != null) {
            SysDictionary sysDictionary =
                    proprietorInfoService.findOneByTypeAndValue(
                            PROPRIETOR_INFO_DECORATE_BUDGET, proprietorInfo.getDecorateBudget().intValue());
            if (sysDictionary != null) {
                vo.setDecorateBudget(sysDictionary.getName());
                vo.setDecorateBudgetValue(proprietorInfo.getDecorateBudget().intValue());
            }
        }
        // 装修公司
        if (proprietorInfo.getShopId() != null) {
            CompanyShop shop = proprietorInfoService.getShopById(proprietorInfo.getShopId().intValue());
            if (shop != null && shop.getBusinessType() != null && shop.getBusinessType() == 5) {
                if (shop.getCompanyId() != null) {
                    Company company = companyService.getCompanyNameById(shop.getCompanyId());
                    if (company != null) {
                        vo.setCompanyName(company.getCompanyName());
                    }
                }
            }
        }
        // 方案
        if (proprietorInfo.getDesignplanId() != null && proprietorInfo.getDesignplanType() != null) {
            vo.setDesignPlanId(proprietorInfo.getDesignplanId().intValue());
            int designPlanType = proprietorInfo.getDesignplanType();
            if (designPlanType == 0) { // 效果图方案
                String pic =
                        proprietorInfoService.getRenderScenePlanPic(
                                proprietorInfo.getDesignplanId().intValue());
                vo.setDesignPlanPic(pic);
            } else if (designPlanType == 1) { // 全屋方案
                String pic =
                        proprietorInfoService.getFullHousePlanPic(proprietorInfo.getDesignplanId().intValue());
                vo.setDesignPlanPic(pic);
            }
        }
        // 备注
        vo.setRemark(proprietorInfo.getRemark());
        return vo;
    }

    private String getHouse(Byte livingRoom, Byte bedRoom, Byte toilet) {
        if (livingRoom == null) {
            livingRoom = 0;
        }
        if (bedRoom == null) {
            bedRoom = 0;
        }
        if (toilet == null) {
            toilet = 0;
        }
        return bedRoom + "室" + livingRoom + "厅" + toilet + "卫";
    }
}
