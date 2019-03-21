package com.sandu.service.servicepurchase.impl;

import com.sandu.api.servicepurchase.input.query.ServicesAccountRelQuery;
import com.sandu.api.servicepurchase.model.ServicesAccountRel;
import com.sandu.api.servicepurchase.serivce.ServicesAccountRelService;
import com.sandu.service.servicepurchase.dao.ServicesAccountRelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("servicesAccountRelService")
@Slf4j
public class ServicesAccountRelServiceImpl implements ServicesAccountRelService {

    @Resource
    private ServicesAccountRelMapper servicesAccountRelMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    private final static String REDIS_KEY_PIX = "SERVICES_ACCOUNT_REL_";

    @Override
    public List<ServicesAccountRel> getAccountListByAccount(String account) {
        return servicesAccountRelMapper.selectAccountListByAccount(account);
    }

    @Override
    public int updateByPrimaryKeySelective(ServicesAccountRel rel) {
        return servicesAccountRelMapper.updateByPrimaryKeySelective(rel);
    }

    @Override
    public void batchInsertServiceAccount(List<ServicesAccountRel> accountList) {
        servicesAccountRelMapper.batchInsertServiceAccount(accountList);
    }

    @Override
    public List<ServicesAccountRel> queryByOption(ServicesAccountRelQuery servicesAccountRelQuery) {
        List<ServicesAccountRel> servicesAccountRelList;
//        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
//        String redisKey = REDIS_KEY_PIX + "QUERY_OPTION_" + JSON.toJSONString(servicesAccountRelQuery);
//        if (stringRedisTemplate.hasKey(redisKey)) {
//            log.info("套餐价格信息缓存开启=================");
//            //开启缓存
//            String json = ops.get(redisKey);
//            return JSONArray.parseArray(json, ServicesAccountRel.class);
//        }

        log.info("套餐价格信息缓存不存在=================");
        servicesAccountRelList = servicesAccountRelMapper.queryByOption(servicesAccountRelQuery);
//        String listJson = JSON.toJSONString(servicesAccountRelList);
//        ops.set(redisKey, listJson, 3000, TimeUnit.MILLISECONDS);
        return servicesAccountRelList;
    }


    @Override
    public ServicesAccountRel getAccountByUserId(Integer userId) {
        return servicesAccountRelMapper.getAccountByUserId(userId);
    }

    @Override
    public List<ServicesAccountRel> queryByUserId(Integer userId, Integer isDelete) {
        ServicesAccountRelQuery servicesAccountRelQuery = new ServicesAccountRelQuery();
        servicesAccountRelQuery.setIsDeleted(isDelete);
        servicesAccountRelQuery.setUserId(userId);
        return this.queryByOption(servicesAccountRelQuery);
    }

    @Override
    public List<ServicesAccountRel> listRemainingAccounts(Integer topDays) {
        return servicesAccountRelMapper.listRemainingAccounts(topDays);
    }

}
