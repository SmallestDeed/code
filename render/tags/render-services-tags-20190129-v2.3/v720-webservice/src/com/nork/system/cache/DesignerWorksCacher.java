package com.nork.system.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.ModuleType;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.system.model.DesignerWorks;
import com.nork.system.model.DesignerWorksUser;
import com.nork.system.model.ResPic;
import com.nork.system.model.search.DesignerWorksSearch;
import com.nork.system.service.DesignerWorksService;
import com.nork.system.service.ResPicService;

public class DesignerWorksCacher {

	@SuppressWarnings("unused")
	private static DesignerWorksService designerWorksService = SpringContextHolder.getBean(DesignerWorksService.class);
	private static ResPicService resPicService = SpringContextHolder.getBean(ResPicService.class);

	/***
	 * 暂时没谁用到这个方法000
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int getCount(DesignerWorksSearch designerWorksSearch) {
		int total = 0;

		Map map = new HashMap();

		Integer userId = designerWorksSearch.getUserId();
		String title = designerWorksSearch.getTitle();

		map.put("userId", userId);
		map.put("title", title);

		String key = KeyGenerator.getKeyWithMap(ModuleType.DesignerWorks, map);
		if (CacheManager.getInstance().getCacher() != null) {
			String totalString = CacheManager.getInstance().getCacher().get(key);
			if (null != totalString && !"".equals(totalString)) {
				total = Integer.parseInt(totalString);
			} else {
				total = designerWorksService.getCount(designerWorksSearch);
				if (total > 0) {
					CacheManager.getInstance().getCacher().setObject(key, String.valueOf(total), 0);
				}
			}
		}

		return total;
	}

	/***
	 * 设计师作品角色?
	 * 
	 * @name getdesignerWorkRendered
	 * @param MsgId
	 * @return DesignerWorksUser
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static DesignerWorksUser getdesignerWorkRendered(DesignerWorksUser designerWorksUser) {
		DesignerWorksUser designerWorksUserDb = new DesignerWorksUser();

		Map map = new HashMap();
		String msgId = designerWorksUser.getMsgId();
		map.put("msgId", msgId);

		String key = KeyGenerator.getKeyWithMap(ModuleType.DesignerWorksUser, map);
		if (CacheManager.getInstance().getCacher() != null) {
			designerWorksUserDb = (DesignerWorksUser) CacheManager.getInstance().getCacher().getObject(key);
			if (designerWorksUserDb == null) {
				designerWorksUserDb = designerWorksService.getdesignerWorkRendered(designerWorksUser);
				if (designerWorksUserDb != null) {
					CacheManager.getInstance().getCacher().setObject(key, designerWorksUserDb, 0);
				}
			}
		}
		//////System.out.println("********WebDesignerWorksController-modifyDesignerWork-getdesignerWorkRendered-key" + key + "********");
		return designerWorksUserDb;
	}

	/***
	 * 根据id获得图片资源
	 * 
	 * @name getResPic
	 * @param PicId
	 * @return resPic
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ResPic getResPic(Integer PicId) {
		ResPic resPic = new ResPic();
		Map map = new HashMap();
		map.put("PicId", PicId);
		String key = KeyGenerator.getKeyWithMap(ModuleType.ResPic, map);
		if (CacheManager.getInstance().getCacher() != null) {
			resPic = (ResPic) CacheManager.getInstance().getCacher().getObject(key);
			if (resPic == null) {
				resPic = resPicService.get(PicId);
				if (resPic != null) {
					CacheManager.getInstance().getCacher().setObject(key, resPic, 0);
				}
			}
		}
		//////System.out.println("********WebDesignerWorksController-modifyDesignerWork-getResPic-key" + key + "********");
		return resPic;
	}

	/**
	 * 清除缓存DesignerWorks表格缓存
	 * 
	 * @param designerWorks
	 */
	public static void removeDesignerWork(DesignerWorks designerWorks) {
		String key = "";
		CacheManager.getInstance().getCacher().removeObject(ModuleType.DesignerWorks, key);
	}

	/**
	 * 获取设计师作品总条数
	 * 
	 * @param designerWorksSearch
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int getDesignerWorksCount(DesignerWorksSearch designerWorksSearch) {

		int total = 0;
		Integer userId = designerWorksSearch.getUserId();
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("Count", "Count");
		String key = KeyGenerator.getKeyWithMap(ModuleType.DesignerWorksSearch, map);
		if (CacheManager.getInstance().getCacher() != null) {
			String totalString = CacheManager.getInstance().getCacher().get(key);
			if (StringUtils.isNoneBlank(totalString)) {
				total = Integer.parseInt(totalString);
			} else {
				total = designerWorksService.getCount(designerWorksSearch);
				if (total > 0) {
					CacheManager.getInstance().getCacher().setObject(key, total, 0);
				}
			}
		}
		//////System.out.println("********WebDesignerWorksController-designerWorksList-getDesignerWorksCount-key" + key + "********");
		return total;
	}

	/**
	 * 获取设计师作品
	 * 
	 * @param designerWorksSearch
	 */
	@SuppressWarnings({ "rawtypes" })
	public static List<DesignerWorksUser> getdesignerWorkDetail(DesignerWorksUser designerWorksUser) {

		List<DesignerWorksUser> list = new ArrayList<DesignerWorksUser>();
		Map map = new HashMap();

		String key = KeyGenerator.getKeyWithMap(ModuleType.DesignerWorksUser, map);
		if (CacheManager.getInstance().getCacher() != null) {
			list = CacheManager.getInstance().getCacher().getList(DesignerWorksUser.class, key);
			if (CustomerListUtils.isEmpty(list)) {
				list = designerWorksService.getdesignerWorkDetail(designerWorksUser);
				if (!CustomerListUtils.isEmpty(list)) {
					CacheManager.getInstance().getCacher().setObject(key, list, 0);
				}
			}
		}
		//////System.out.println("********WebDesignerWorksController-designerWorksList-getdesignerWorkDetail-key" + key + "********");
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void removeDesignerWorkById(Integer id) {
		Map map = new HashMap();
		map.put("id", id);
		String key = KeyGenerator.getKeyWithMap(ModuleType.DesignerWorks, map);
		CacheManager.getInstance().getCacher().remove(ModuleType.DesignerWorks, key);

	}
}
