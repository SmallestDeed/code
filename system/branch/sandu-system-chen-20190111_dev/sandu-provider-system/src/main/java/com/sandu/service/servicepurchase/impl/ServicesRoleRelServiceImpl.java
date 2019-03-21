package com.sandu.service.servicepurchase.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.servicepurchase.input.query.ServicesRoleRelQuery;
import com.sandu.api.servicepurchase.model.ServicesRoleRel;
import com.sandu.api.servicepurchase.model.bo.ServicesPurchaseListBO;
import com.sandu.api.servicepurchase.serivce.ServicesRoleRelService;
import com.sandu.service.servicepurchase.dao.ServicesRoleRelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


@Service("servicesRoleRelService")
@Slf4j
public class ServicesRoleRelServiceImpl implements ServicesRoleRelService {
    @Resource
    private ServicesRoleRelMapper servicesRoleRelMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    private final static String REDIS_KEY_PIX = "SERVICES_PURCHASE_RECORD_";

    @Override
    public List<ServicesRoleRel> getByServiceId(Long id) {
        return servicesRoleRelMapper.getByServiceId(id);
    }

    @Override
    public PageInfo<ServicesPurchaseListBO> getBePurchasedServices(Integer companyId, Integer start, Integer limit) {
        PageHelper.startPage(start, limit);
        return new PageInfo<>(servicesRoleRelMapper.getBePurchasedServices(companyId));
    }

    @Override
    public List<ServicesRoleRel> queryByOption(ServicesRoleRelQuery servicesRoleRelQuery) {
        List<ServicesRoleRel> servicesRoleRelList;
//        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
//        String redisKey = REDIS_KEY_PIX + "QUERY_OPTION_" + JSON.toJSONString(servicesRoleRelQuery);
//        if (stringRedisTemplate.hasKey(redisKey)) {
//            log.info("套餐价格信息缓存开启=================");
//            //开启缓存
//            String json = ops.get(redisKey);
//            return JSONArray.parseArray(json, ServicesRoleRel.class);
//        }
//        log.info("套餐价格信息缓存不存在=================");
        servicesRoleRelList = servicesRoleRelMapper.queryByOption(servicesRoleRelQuery);
//        String listJson = JSON.toJSONString(servicesRoleRelList);
//        ops.set(redisKey, listJson, 3000, TimeUnit.MILLISECONDS);
        return servicesRoleRelList;
    }

    @Override
    public List<ServicesRoleRel> queryByIds(Set<Long> ids, Integer isDelete) {
        ServicesRoleRelQuery servicesRoleRelQuery = new ServicesRoleRelQuery();
        servicesRoleRelQuery.setServicesIds(ids);
        servicesRoleRelQuery.setIsDeleted(isDelete);
        return this.queryByOption(servicesRoleRelQuery);
    }

    @Override
    public List<ServicesRoleRel> queryByServiceId(Long serviceId, Integer isDelete) {
        ServicesRoleRelQuery servicesRoleRelQuery = new ServicesRoleRelQuery();
        servicesRoleRelQuery.setServicesId(serviceId);
        servicesRoleRelQuery.setIsDeleted(isDelete);
        return this.queryByOption(servicesRoleRelQuery);
    }
}
