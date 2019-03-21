package com.sandu.user.service.impl;

import com.sandu.cache.service.RedisService;
import com.sandu.common.util.StringUtils;
import com.sandu.user.dao.UserBehaviorMapper;
import com.sandu.user.dao.UserGuideStepMiniMapper;
import com.sandu.user.model.UserBehavior;
import com.sandu.user.model.UserGuideStepMini;
import com.sandu.user.model.view.UserBehaviorVO;
import com.sandu.user.service.UserBehaviorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Supplier;

@Service("userBehaviorService")
@Slf4j
public class UserBehaviorServiceImpl implements UserBehaviorService {

	@Resource
	private RedisService redisService;

	@Autowired
	private UserBehaviorMapper userBehaviorMapper;

	@Autowired
	private UserGuideStepMiniMapper userGuideStepMiniMapper;


	private final String key = "user_behavior_map";

	private final String lockKey = "user_behavior_map:lockKey_uuid";

	@Override
	public boolean cacheCount(String type) {
		log.info("start cache");

		//更新cache
		this.syncCacheOperation(() -> {
			String s = redisService.getMap(key, type);
			if (StringUtils.isBlank(s)) {
				redisService.addMap(key, UserBehaviorVO.keyMap);
				s = UserBehaviorVO.keyMap.get(type);
			}
			String cur = Integer.valueOf(s) + 1 + "";
			log.info("key:{}------>value:{}",type,cur);
			return redisService.addMap(key, type, cur);
		});
		log.info("end cache");
		return true;
	}

	@Override
	public boolean syncToDB() {
		log.info("start  sync db");
		String id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
		List<Integer> typeList = Arrays.asList(UserBehavior.MEDIATION_USER, UserBehavior.NORMAL_USER);
		List<UserBehavior> behaviorList = userBehaviorMapper.selectLikePrimaryKey(id);
		if (behaviorList == null || behaviorList.isEmpty()) {
			Date date = new Date();
			for (Integer typeValue : typeList) {
				UserBehavior userBehavior = new UserBehavior();
				userBehavior.setId(id + "_" + typeValue);
				userBehavior.setHour_time_str(id);
				userBehavior.setBehavior_type(typeValue.byteValue());
				userBehavior.setGmt_create(date);
				userBehaviorMapper.insertSelective(userBehavior);
			}
			behaviorList = userBehaviorMapper.selectLikePrimaryKey(id);
		}
		Map<String, String> map = new HashMap<>(UserBehaviorVO.keyMap.size());
		this.syncCacheOperation(() -> {
			//get cache
			map.putAll(redisService.getMap(key));
			//reset cache
			return redisService.addMap(key, UserBehaviorVO.keyMap);
		});


		//save db
		Field[] fields = UserBehavior.class.getDeclaredFields();
		int alterCount = 0;

		for (UserBehavior userBehavior : behaviorList) {
			for (Field field : fields) {
				String mapKey = field.getName() + "_" + userBehavior.getBehavior_type();
				if (!UserBehaviorVO.keyMap.containsKey(mapKey)) {
					continue;
				}
				field.setAccessible(true);
				String count = map.get(mapKey);
				try {
					if (field.getType().equals(Integer.class)) {
						int o = field.get(userBehavior) == null ? 0 : (Integer) field.get(userBehavior);
						field.set(userBehavior, o + Integer.valueOf(count));
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			alterCount += userBehaviorMapper.updateByPrimaryKey(userBehavior);
		}

		log.info("end  sync db");
		return alterCount > 0;
	}

	@Override
	public UserGuideStepMini getUserGuideStepByUserId(Long userId) {
		UserGuideStepMini userGuideStepMini = userGuideStepMiniMapper.selectByUserId(userId.intValue());
		if (userGuideStepMini == null) {
			userGuideStepMini = new UserGuideStepMini();
			userGuideStepMini.setUserId(userId.intValue());
			userGuideStepMini.setGmtCreated(new Date());
			userGuideStepMini.setOneKeyDesign((byte) 0);
			userGuideStepMini.setChooseHouseType((byte) 0);
			userGuideStepMiniMapper.insertSelective(userGuideStepMini);
		}
		return userGuideStepMini;
	}

	@Override
	public int updateUserGuideStep(UserGuideStepMini guideStepMini) {
		Date now = new Date();
		UserGuideStepMini userGuideStepMini = userGuideStepMiniMapper.selectByPrimaryKey(guideStepMini.getId());
		if (userGuideStepMini == null) {
			throw new RuntimeException("获取UserGuideStepMini信息失败,Id:" + guideStepMini.getId());
		}
		if (!userGuideStepMini.getChooseHouseType().equals(guideStepMini.getChooseHouseType())) {
			userGuideStepMini.setModifiedChooseHouseType(now);
		}
		if (!userGuideStepMini.getOneKeyDesign().equals(guideStepMini.getOneKeyDesign())) {
			userGuideStepMini.setModifiedOneKeyDesign(now);
		}
		return userGuideStepMiniMapper.updateByPrimaryKeySelective(guideStepMini);
	}

	private void syncCacheOperation(Supplier<Boolean> supplier) {
		UUID uuid = UUID.randomUUID();
		long expire = 1;
		while (!redisService.tryLock(lockKey, uuid.toString(), expire)) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		supplier.get();
		redisService.releaseLock(lockKey, uuid.toString());


		//get lock
//            String lock = redisService.get(lockKey);
//            while (lock != null && lock.equalsIgnoreCase("locked")) {
//                try {
//                    Thread.sleep(50);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                lock = redisService.get(lockKey);
//            }
//            redisService.set(lockKey, "locked");
//            supplier.get();
		//release lock
//        redisService.set(lockKey, "unlock");
	}
}
