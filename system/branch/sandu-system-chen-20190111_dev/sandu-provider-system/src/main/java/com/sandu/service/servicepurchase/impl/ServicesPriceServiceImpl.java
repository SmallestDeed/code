package com.sandu.service.servicepurchase.impl;

import com.sandu.api.servicepurchase.input.query.ServicesPriceQuery;
import com.sandu.api.servicepurchase.model.ServicesPrice;
import com.sandu.api.servicepurchase.output.ServicesPriceVO;
import com.sandu.api.servicepurchase.serivce.ServicesPriceService;
import com.sandu.service.servicepurchase.dao.ServicesPriceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author sandu-lipeiyuan
 */
@Service("servicesPriceService")
@Slf4j
public class ServicesPriceServiceImpl implements ServicesPriceService {

    @Resource
    private ServicesPriceMapper servicePriceMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final static String REDIS_KEY_PIX = "SERVICES_PRICE_";

    /**
     * 获取套餐的价格策略
     */
    @Override
    public List<ServicesPriceVO> findServicesPriceById(Long servicesId) {
        if (servicesId == null) {
            return Collections.emptyList();
        }
        return servicePriceMapper.selectByServicesId(servicesId);

    }

    /**
     * 获取当前套餐的所有价格信息
     */
    @Override
    public List<ServicesPrice> findServicesPriceList(Long servicesId) {
        if (servicesId == null) {
            return Collections.emptyList();
        }
        return servicePriceMapper.selectServicesPriceList(servicesId);
    }

    @Override
    public List<ServicesPrice> queryByOption(ServicesPriceQuery servicesPriceQuery) {
        List<ServicesPrice> servicesPriceList;
//        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
//        String redisKey = REDIS_KEY_PIX + "QUERY_OPTION_" + JSON.toJSONString(servicesPriceQuery);
//        if (stringRedisTemplate.hasKey(redisKey)) {
//            log.info("套餐价格信息缓存开启=================");
//            //开启缓存
//            String json = ops.get(redisKey);
//            return JSONArray.parseArray(json, ServicesPriceListVO.class);
//        }
//        log.info("套餐价格信息缓存不存在=================");
        servicesPriceList = servicePriceMapper.queryByOption(servicesPriceQuery);
//        String listJson = JSON.toJSONString(servicesPriceList);
//        ops.set(redisKey, listJson, 3000, TimeUnit.MILLISECONDS);
        return servicesPriceList;
    }

    @Override
    public List<ServicesPrice> queryByServicesIdAndCompanyId(Long servicesId, Integer companyId, Integer isDelete) {
        ServicesPriceQuery servicesPriceQuery = new ServicesPriceQuery();
        servicesPriceQuery.setServicesId(servicesId);
        servicesPriceQuery.setCompanyId(companyId);
        servicesPriceQuery.setIsDeleted(isDelete);
        return this.queryByOption(servicesPriceQuery);
    }


    @Override
    public ServicesPrice findPriceById(Long id) {
//        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
//        String redisKey = REDIS_KEY_PIX + "ID_" + id;
        ServicesPrice servicesPrice;
//        if (stringRedisTemplate.hasKey(redisKey)) {
//            log.info("套餐价格信息缓存开启=================");
//            //开启缓存
//            String json = ops.get(redisKey);
//            return (ServicesPriceListVO) JSON.parse(json);
//        }
//        log.info("套餐价格信息缓存不存在=================");
        servicesPrice = servicePriceMapper.selectByPrimaryKey(id);
//        String jsonString = JSON.toJSONString(servicesPrice);
//        ops.set(redisKey, jsonString, 3000, TimeUnit.MILLISECONDS);
        return servicesPrice;
    }
}
