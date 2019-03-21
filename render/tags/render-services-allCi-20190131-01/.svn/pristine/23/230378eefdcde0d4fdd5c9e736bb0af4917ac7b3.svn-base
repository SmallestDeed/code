package com.nork.customerservice.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.ModuleType;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.customerservice.model.SysFaq;
import com.nork.customerservice.model.search.SysFaqSearch;
import com.nork.customerservice.service.SysFaqService;

public class SysFaqCacher {

	private static SysFaqService sysFaqService=SpringContextHolder.getBean(SysFaqService.class);
	
	
	/**
	 * 获取 常见问题详情
	 * @param id
	 * @return SysFaq
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static SysFaq getSysFaqById(Integer id){
		SysFaq sysFaq = new SysFaq();
		Map map=new HashMap();
		map.put("id", id);
		String key=KeyGenerator.getKeyWithMap(ModuleType.SysFaq, map);
		if (CacheManager.getInstance().getCacher() != null) {
			sysFaq=(SysFaq) CacheManager.getInstance().getCacher().getObject(key);
			if(sysFaq==null){
				sysFaq = sysFaqService.get(id);
				if(sysFaq!=null){
					CacheManager.getInstance().getCacher().setObject(key, sysFaq, 0);
				}
			}
		}
		return sysFaq;
	}
	
	
	/**
	 * 常见问题总条数
	 * @param sysFaqSearch
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int getSysFaqCount(SysFaqSearch sysFaqSearch){
		int Count=0;
		Map map = new HashMap();
		map.put("Count", "Count");
		String key=KeyGenerator.getKeyWithMap(ModuleType.SysFaq, map);
		if (CacheManager.getInstance().getCacher() != null) {
			String CountString=CacheManager.getInstance().getCacher().get(key);
			if(CountString!=null&&!"".equals(CountString)){
				Count=Integer.parseInt(CountString);
			}else{
				Count = sysFaqService.getCount(sysFaqSearch);
				if(Count>0){
					CacheManager.getInstance().getCacher().set(key, String.valueOf(Count), 0);
				}
			}
		}
		
		return Count;
	}
	
	
	/**
	 * 常见问题列表
	 * @param sysFaqSearch
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<SysFaq> getSysFaqList(SysFaqSearch sysFaqSearch){
		List<SysFaq> list = new ArrayList<SysFaq> ();
		Map map = new HashMap();
		String key=KeyGenerator.getKeyWithMap(ModuleType.SysFaq, map);
		if (CacheManager.getInstance().getCacher() != null) {
			list=CacheManager.getInstance().getCacher().getList(SysFaq.class, key);
			if (CustomerListUtils.isEmpty(list)) {
				list = sysFaqService.getPaginatedList(sysFaqSearch);
				if (!CustomerListUtils.isEmpty(list)) {
					CacheManager.getInstance().getCacher().setObject(key, list, 0);
				}
			}
		}
		return list;
	}
	
	
	/**
	 * 清除常见问题缓存
	 * @name removeSysFaq 
	 * @param null
	 * @return
	 */
	public static void removeSysFaq(){
		String key="";
		CacheManager.getInstance().getCacher().removeObject(ModuleType.SysFaq, key);
	}
}
