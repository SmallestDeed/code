package com.nork.system.cache;

import java.util.HashMap;
import java.util.Map;

import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.ModuleType;
import com.nork.common.util.SpringContextHolder;
import com.nork.system.model.SysVersion;
import com.nork.system.service.SysVersionService;

public class SysVersionCacher {
	private static SysVersionService sysVersionService = SpringContextHolder.getBean(SysVersionService.class);

	/**
	 * 最新版本检测接口
	 * 
	 * @param systemType
	 * @return sysVersion
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static SysVersion getLatestVersion(Integer systemType,String verType) {
		SysVersion sysVersion = new SysVersion();
		Map map = new HashMap();
		map.put("systemType", systemType);
		String key = KeyGenerator.getKeyWithMap(ModuleType.SysUser, map);
		if (CacheManager.getInstance().getCacher() != null) {
			sysVersion = (SysVersion) CacheManager.getInstance().getCacher().getObject(key);
			if (sysVersion == null) {
				sysVersion = sysVersionService.getLatestVersion(systemType,verType);
				if (sysVersion != null) {
					CacheManager.getInstance().getCacher().setObject(key, sysVersion, 0);
				}
			}
		}
		return sysVersion;
	}
}
