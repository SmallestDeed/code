package com.nork.system.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.ModuleType;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.StringUtils;
import com.nork.system.model.SysMessageRecord;
import com.nork.system.model.search.SysDictionarySearch;
import com.nork.system.model.search.SysMessageRecordSearch;
import com.nork.system.service.SysMessageRecordService;

/***
 * 聊天记录缓存层
 * 
 * @author qiu.jun
 * @date 2016-05-18
 */
public class SysMessageRecordCacher {
	private static SysMessageRecordService sysMessageRecordService = SpringContextHolder
			.getBean(SysMessageRecordService.class);

	/***
	 * 获取聊天总记录数
	 * 
	 * @param search
	 * @return
	 */
	public static int getCount(SysMessageRecordSearch search) {
		int total = 0;
		Map<String, String> map = new HashMap<String, String>();
		map.put("fromUser", String.valueOf(search.getFromUser()));
		map.put("targetUser", String.valueOf(search.getTargetUser()));
		map.put("isRead", String.valueOf(search.getIsRead()));
		String key = KeyGenerator.getAllCountKeyWithMap(ModuleType.SysMessageRecord, map);
		String temp = CacheManager.getInstance().getCacher().get(key);
		if (StringUtils.isBlank(temp)) {
			total = sysMessageRecordService.getCount(search);
			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
		} else {
			total = Integer.parseInt(temp);
			//////System.out.println("get from cacher,key:" + key);
		}
		return total;
	}

	/***
	 * 获取最近联系人名单
	 * 
	 * @return
	 */
	public static List<SysMessageRecord> getRecentContacts(SysMessageRecordSearch search) {
		List<SysMessageRecord> lstRecord = Lists.newArrayList();
		Map<String, String> map = new HashMap<String, String>();
		map.put("fromUser", String.valueOf(search.getFromUser()));
		String key = KeyGenerator.getAllListKeyWithMap(ModuleType.SysMessageRecord, map);
		if (CacheManager.getInstance().getCacher() != null) {
			lstRecord = CacheManager.getInstance().getCacher().getList(SysMessageRecord.class, key);
			if (lstRecord == null) {
				SysDictionarySearch sysDictionarySearch = new SysDictionarySearch();
				lstRecord = sysMessageRecordService.selectRecentContacts(search);
				CacheManager.getInstance().getCacher().setObject(key, lstRecord, -1);
			}
		}
		return lstRecord;
	}
}
