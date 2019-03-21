package com.nork.task.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.ModuleType;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.task.model.SysTask;
import com.nork.task.model.search.SysTaskSearch;
import com.nork.task.service.SysTaskService;

/***
 * 调度任务缓存层
 * @author qiu.jun
 * @date 2016-05-23
 */
public class SysTaskCacher {
	private static SysTaskService sysTaskService = SpringContextHolder.getBean(SysTaskService.class);

	/***
	 * 获取调度任务总记录数
	 * 
	 * @param search
	 * @return
	 */
	public static int getCount(SysTaskSearch search) {
		int total = 0;
		Map<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(search.getAttribute())) {
			map.put("attribute", search.getAttribute());
		}
		if (search.getBusinessId() != null) {
			map.put("businessId", String.valueOf(search.getBusinessId()));
		}
		if (search.getState() != null) {
			map.put("state", String.valueOf(search.getState()));
		}
		if (CustomerListUtils.isNotEmpty(search.getStateList())) {
			map.put("stateList", Utils.getString(search.getStateList()));
		}

		String key = KeyGenerator.getKeyWithMap(ModuleType.SysTask, map);
		String temp = CacheManager.getInstance().getCacher().get(key);
		if (StringUtils.isBlank(temp)) {
			total = sysTaskService.getCount(search);
			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
		} else {
			total = Integer.parseInt(temp);
			//////System.out.println("get from cacher,key:" + key);
		}
		return total;
	}

	/***
	 * 系统任务
	 * 
	 * @param ConfigFileId
	 * @author Administrator
	 * @return ResFile
	 */
	public static List<SysTask> getList(SysTask sysTask) {
		List<SysTask> taskList = new ArrayList<SysTask>();
		Map<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(sysTask.getAttribute())) {
			map.put("attribute", sysTask.getAttribute());
		}
		if (sysTask.getBusinessId() != null) {
			map.put("businessId", String.valueOf(sysTask.getBusinessId()));
		}
		if (sysTask.getState() != null) {
			map.put("state", String.valueOf(sysTask.getState()));
		}
		if (CustomerListUtils.isNotEmpty(sysTask.getStateList())) {
			map.put("stateList", Utils.getString(sysTask.getStateList()));
		}
		if (StringUtils.isNotBlank(sysTask.getRenderServer())) {
			map.put("renderServer", sysTask.getRenderServer());
		}

		String key = KeyGenerator.getKeyWithMap(ModuleType.SysTask, map);
		if (CacheManager.getInstance().getCacher() != null) {
			taskList = CacheManager.getInstance().getCacher().getList(SysTask.class, key);
			if (CustomerListUtils.isEmpty(taskList)) {
				taskList = sysTaskService.getList(sysTask);
				if (!CustomerListUtils.isEmpty(taskList)) {
					CacheManager.getInstance().getCacher().setObject(key, taskList, 0);
				}
			}
		}
		//////System.out.println("********WebDesignPlanController-prepareRender-getSysTask-key" + key + "********");
		return taskList;
	}

}
