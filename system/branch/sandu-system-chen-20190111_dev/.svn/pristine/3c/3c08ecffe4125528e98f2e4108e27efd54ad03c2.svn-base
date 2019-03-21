package com.sandu.service.servicepurchase.impl;

import com.sandu.api.servicepurchase.input.query.ServicesPurchaseRecordQuery;
import com.sandu.api.servicepurchase.model.ServicesPurchaseRecord;
import com.sandu.api.servicepurchase.serivce.ServicesPurchaseRecordService;
import com.sandu.service.servicepurchase.dao.ServicesPurchaseRecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("servicesPurchaseRecordService")
@Slf4j
public class ServicesPurchaseRecordServiceImpl implements ServicesPurchaseRecordService {

	@Resource
	private ServicesPurchaseRecordMapper servicesPurchaseRecordMapper;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	private final static String REDIS_KEY_PIX = "SERVICES_PURCHASE_RECORD_";
	
	@Override
	public int addServicesPurchaseRecord(ServicesPurchaseRecord record) {
		return servicesPurchaseRecordMapper.insertSelective(record);
	}

	@Override
	public ServicesPurchaseRecord selectByPrimaryKey(Long purchaseRecordId) {
//		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
//		String redisKey = REDIS_KEY_PIX + "ID_" + purchaseRecordId;
		ServicesPurchaseRecord servicesPurchaseRecord;
//		if (stringRedisTemplate.hasKey(redisKey)) {
//			log.info("套餐价格信息缓存开启=================");
//			//开启缓存
//			String json = ops.get(redisKey);
//			return (ServicesPurchaseRecord) JSON.parse(json);
//		}
//		log.info("套餐价格信息缓存不存在=================");
		servicesPurchaseRecord = servicesPurchaseRecordMapper.selectByPrimaryKey(purchaseRecordId);
//		String jsonString = JSON.toJSONString(servicesPurchaseRecord);
//		ops.set(redisKey, jsonString, 3000, TimeUnit.MILLISECONDS);
		return servicesPurchaseRecord;
	}

	@Override
	public int updateByPrimaryKeySelective(ServicesPurchaseRecord record) {
		return servicesPurchaseRecordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<ServicesPurchaseRecord> queryByOption(ServicesPurchaseRecordQuery servicesPurchaseRecordQuery) {
		List<ServicesPurchaseRecord> servicesPurchaseRecordList;
//		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
//		String redisKey = REDIS_KEY_PIX + "QUERY_OPTION_" + JSON.toJSONString(servicesPurchaseRecordQuery);
//		if (stringRedisTemplate.hasKey(redisKey)) {
//			log.info("套餐价格信息缓存开启=================");
//			//开启缓存
//			String json = ops.get(redisKey);
//			return JSONArray.parseArray(json, ServicesPurchaseRecord.class);
//		}
//		log.info("套餐价格信息缓存不存在=================");
		servicesPurchaseRecordList = servicesPurchaseRecordMapper.queryByOption(servicesPurchaseRecordQuery);
//		String listJson = JSON.toJSONString(servicesPurchaseRecordList);
//		ops.set(redisKey, listJson, 3000, TimeUnit.MILLISECONDS);
		return servicesPurchaseRecordList;
	}

    @Override
    public ServicesPurchaseRecord getRecordByOrderNum(String orderNum) {
        return servicesPurchaseRecordMapper.getRecordByOrderNum(orderNum);
    }

	@Override
	public ServicesPurchaseRecord checkRecordWithUserId(Integer userId) {
		return servicesPurchaseRecordMapper.checkRecordWithUserId(userId);
	}

	@Override
	public int checkTelephoneExists(String telephone) {
		return servicesPurchaseRecordMapper.selectTelephoneExists(telephone);
	}

}
