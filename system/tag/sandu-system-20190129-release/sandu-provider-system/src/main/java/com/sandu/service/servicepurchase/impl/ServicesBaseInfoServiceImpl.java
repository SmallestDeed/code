package com.sandu.service.servicepurchase.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.servicepurchase.input.query.ServicesBaseInfoQuery;
import com.sandu.api.servicepurchase.model.ServicesBaseInfo;
import com.sandu.api.servicepurchase.model.bo.ServicesListBO;
import com.sandu.api.servicepurchase.model.bo.ServicesPurchaseLogListBO;
import com.sandu.api.servicepurchase.serivce.ServicesBaseInfoService;
import com.sandu.service.servicepurchase.dao.ServicesBaseInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service("servicesBaseInfoService")
@Slf4j
public class ServicesBaseInfoServiceImpl implements ServicesBaseInfoService {

    @Resource
    private ServicesBaseInfoMapper servicesBaseInfoMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final static String REDIS_KEY_PIX = "SERVICES_BASE_INFO_";

    @Override
    public PageInfo<ServicesListBO> getServicesListByUserScope(List<Integer> userTypes, String saleChannel, Integer page, Integer limit) {
        if (userTypes.isEmpty()) {
            return new PageInfo<>();
        }
        PageHelper.startPage(page, limit);

        PageInfo<ServicesListBO> result = new PageInfo<>(servicesBaseInfoMapper.getServicesListByUserScopeAndSaleChannel(userTypes, saleChannel));
//        List<ServicesListBO> list = result.getList().stream()
//                .filter(bo -> {
//                    AtomicBoolean ret = new AtomicBoolean(false);
//                    userTypes.forEach(type -> {
//                        if (bo.getUserScope().contains(String.valueOf(type))) {
//                            ret.set(true);
//                        }
//                    });
//                    return ret.get();
//                }).collect(Collectors.toList());
        return result;
    }

    @Override
    public ServicesBaseInfo getById(Long id) {
//        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
//        String redisKey = REDIS_KEY_PIX + "ID_" + id;
        ServicesBaseInfo servicesBaseInfo;
//        if (stringRedisTemplate.hasKey(redisKey)) {
//            log.info("套餐价格信息缓存开启=================");
//            //开启缓存
//            String json = ops.get(redisKey);
//            return (ServicesBaseInfo) JSON.parse(json);
//        }
//        log.info("套餐价格信息缓存不存在=================");
        servicesBaseInfo = servicesBaseInfoMapper.selectByPrimaryKey(id);
//        String jsonString = JSON.toJSONString(servicesBaseInfo);
//        ops.set(redisKey, jsonString, 3000, TimeUnit.MILLISECONDS);
        return servicesBaseInfo;
    }

    @Override
    public PageInfo<ServicesPurchaseLogListBO> getPurchasedServicesLogsByCompanyId(Integer companyId, Integer start, Integer limit) {
        PageHelper.startPage(start, limit);
        return new PageInfo<>(servicesBaseInfoMapper.getPurchasedServicesLogsByCompanyId(companyId));
    }

    @Override
    public List<ServicesBaseInfo> getServicesByType(String servicesType, String saleChannel) {
        return servicesBaseInfoMapper.getServicesByType(servicesType, saleChannel);
    }

    @Override
    public List<ServicesBaseInfo> queryByOption(ServicesBaseInfoQuery servicesBaseInfoQuery) {
        List<ServicesBaseInfo> servicesBaseInfoList;
//        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
//        String redisKey = REDIS_KEY_PIX + "QUERY_OPTION_" + JSON.toJSONString(servicesBaseInfoQuery);
//        if (stringRedisTemplate.hasKey(redisKey)) {
//            log.info("套餐价格信息缓存开启=================");
//            //开启缓存
//            String json = ops.get(redisKey);
//            return JSONArray.parseArray(json, ServicesBaseInfo.class);
//        }
//
//        log.info("套餐价格信息缓存不存在=================");
        servicesBaseInfoList = servicesBaseInfoMapper.queryByOption(servicesBaseInfoQuery);
//        String listJson = JSON.toJSONString(servicesBaseInfoList);
//        ops.set(redisKey, listJson, 3000, TimeUnit.MILLISECONDS);
        return servicesBaseInfoList;
    }

    @Override
    public List<ServicesBaseInfo> queryBuyServices(Long noId, String userScope, String saleChannel, String servicesStatus, Integer servicesType,
                                                   Integer isDeleted, Integer start, Integer limit) {
        ServicesBaseInfoQuery servicesBaseInfoQuery = new ServicesBaseInfoQuery();
        servicesBaseInfoQuery.setUserScope(userScope);
        servicesBaseInfoQuery.setSaleChannel(saleChannel);
        servicesBaseInfoQuery.setServicesStatus(servicesStatus);
        servicesBaseInfoQuery.setServicesType(servicesType);
        servicesBaseInfoQuery.setIsDeleted(isDeleted);
        servicesBaseInfoQuery.setStart(start);
        servicesBaseInfoQuery.setLimit(limit);
        servicesBaseInfoQuery.setNoId(noId);
        return this.queryByOption(servicesBaseInfoQuery);
    }

    @Override
    public ServicesBaseInfo queryReBuyServices(Long id, Set<String> userScopeSet, String saleChannel, String servicesStatus, Integer servicesType, Integer isDeleted) {
        if(CollectionUtils.isEmpty(userScopeSet)){
            return null;
        }
        ServicesBaseInfoQuery servicesBaseInfoQuery = new ServicesBaseInfoQuery();
        servicesBaseInfoQuery.setId(id);
        servicesBaseInfoQuery.setUserScopeSet(userScopeSet);
        servicesBaseInfoQuery.setSaleChannel(saleChannel);
        servicesBaseInfoQuery.setServicesStatus(servicesStatus);
        servicesBaseInfoQuery.setServicesType(servicesType);
        servicesBaseInfoQuery.setIsDeleted(isDeleted);
        List<ServicesBaseInfo> servicesBaseInfoList = this.queryByOption(servicesBaseInfoQuery);
        if (CollectionUtils.isEmpty(servicesBaseInfoList)) {
            return null;
        }
        return servicesBaseInfoList.get(0);
    }

    @Override
    public int countQueryBuyServices(Long noId, String userScope, String saleChannel, String servicesStatus, Integer servicesType, Integer isDeleted) {
        ServicesBaseInfoQuery servicesBaseInfoQuery = new ServicesBaseInfoQuery();
        servicesBaseInfoQuery.setUserScope(userScope);
        servicesBaseInfoQuery.setSaleChannel(saleChannel);
        servicesBaseInfoQuery.setServicesStatus(servicesStatus);
        servicesBaseInfoQuery.setServicesType(servicesType);
        servicesBaseInfoQuery.setIsDeleted(isDeleted);
        servicesBaseInfoQuery.setNoId(noId);
        return servicesBaseInfoMapper.countQueryByOption(servicesBaseInfoQuery);
    }

    @Override
    public List<ServicesBaseInfo> getByIds(Set<Long> collect) {
        if (collect != null && collect.isEmpty()) {
            return Collections.emptyList();
        }
        return servicesBaseInfoMapper.getByIds(collect);
    }

    @Override
    public List<ServicesBaseInfo> getServicesListByUserScopeAndTye(String servicesType, String saleChannel, String userScope) {
        return servicesBaseInfoMapper.selectServicesListByUserScopeAndTye(servicesType,saleChannel,userScope);
    }
}
