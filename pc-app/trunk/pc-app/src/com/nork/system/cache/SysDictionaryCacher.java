package com.nork.system.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.CacherPrefix;
import com.nork.common.metadata.ModuleType;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.system.model.SysDicitonaryOptimize;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.search.SysDictionarySearch;
import com.nork.system.service.SysDictionaryService;

/***
 * 字典数据缓存层
 * @author qiu.jun
 * @date 2016-05-10
 */
public class SysDictionaryCacher {
	private static SysDictionaryService sysDictionaryService=SpringContextHolder.getBean(SysDictionaryService.class);
	
	/***
	 * 获取字典总记录数
	 * @return
	 */
    public static int getDictionaryTotal(){
    	int total=0;
    	String key=CacherPrefix.DIC_PREFIX+"total_size";
		if(CacheManager.getInstance().getCacher()!=null){
			String temp=CacheManager.getInstance().getCacher().get(key);
			if(StringUtils.isNoneBlank(temp)){
				total=Integer.parseInt(temp);
			}
			else{
				SysDictionarySearch sysDictionarySearch=new SysDictionarySearch();
				total=sysDictionaryService.getCount(sysDictionarySearch);
				CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
			}
		}
    	return total;
    }
    
    /***
     * 获取所有字典数据
     * @return
     */
    public static List<SysDictionary> getAllList(){
    	List<SysDictionary> lstDic=Lists.newArrayList();
    	String key=KeyGenerator.getAllListKey(ModuleType.Dictionary);
    	if(CacheManager.getInstance().getCacher()!=null){
    		lstDic=CacheManager.getInstance().getCacher().getList(SysDictionary.class, key);
    		if(lstDic==null){
    			SysDictionary sysDictionary=new SysDictionary();
    			sysDictionary.setIsDeleted(0);
    			lstDic=sysDictionaryService.getList(sysDictionary);
    			CacheManager.getInstance().getCacher().setObject(key, lstDic, -1);
    		}
    	}
    	return lstDic;
    }
    
    public static SysDictionary get(int id){
    	SysDictionary dic=new SysDictionary();
    	List<SysDictionary> lst= getAllList();
    	for(SysDictionary d : lst){
    		if(id==d.getId()){
    			dic=d;
    			break;
    		}
    	}
    	return dic;
    }
    
    /***
     * 根据类别获取所有字典数据
     * @return
     */
    public static List<SysDictionary> getAllListWithType(String type){
    	List<SysDictionary> lstDic=Lists.newArrayList();
    	Map<String,String> map=new HashMap<String,String>();
    	map.put("is_deleted", "0");
    	map.put("type", type);
    	String key=KeyGenerator.getAllListKeyWithMap(ModuleType.Dictionary,map);
    	if(CacheManager.getInstance().getCacher()!=null){
    		lstDic=CacheManager.getInstance().getCacher().getList(SysDictionary.class, key);
    		if(lstDic==null){
    			SysDictionary search=new SysDictionary();
    			search.setIsDeleted(0);
    			search.setType(type);
    			lstDic=sysDictionaryService.getList(search);
    			if(CustomerListUtils.isNotEmpty(lstDic)){
     			   CacheManager.getInstance().getCacher().setObject(key, lstDic, 0);
     			}
    		}
    	}
    	return lstDic;
    }
    
    /***
     * 根据类别获取所有字典数据
     * @return
     */
    public static List<SysDictionary> getSysDictionary(String type,Integer value){
    	List<SysDictionary> lstDic=Lists.newArrayList();
    	Map<String,String> map=new HashMap<String,String>();
    	map.put("is_deleted", "0");
    	map.put("type", type);
    	map.put("value", value+"");
    	String key=KeyGenerator.getAllListKeyWithMap(ModuleType.Dictionary,map);
    	if(CacheManager.getInstance().getCacher()!=null){
    		lstDic=CacheManager.getInstance().getCacher().getList(SysDictionary.class, key);
    		if(lstDic==null){
    			SysDictionary search=new SysDictionary();
    			search.setIsDeleted(0);
    			search.setType(type);
    			search.setValue(value);
    			lstDic=sysDictionaryService.getList(search);
    			if(CustomerListUtils.isNotEmpty(lstDic)){
     			   CacheManager.getInstance().getCacher().setObject(key, lstDic, 0);
     			}
    		}
    	}
    	return lstDic;
    }
    
    /***
     * 根据条件分页获取字典数据
     * @return
     */
    public static List<SysDictionary> getPageList(SysDictionarySearch search){
    	List<SysDictionary> lstDic=Lists.newArrayList();
    	Map<String,String> map=new HashMap<String,String>();
    	map.put("list", "page");
		map.put("limit", String.valueOf(search.getLimit()));
		map.put("start", String.valueOf(search.getStart()));
    	map.put("type", search.getType());
    	map.put("att4",search.getAtt4());
    	map.put("att6",search.getAtt6());
    	if(search.getValue()!=null){
    	   map.put("value", String.valueOf(search.getValue()));
    	}
    	String key=KeyGenerator.getAllListKeyWithMap(ModuleType.Dictionary,map);
    	if(CacheManager.getInstance().getCacher()!=null){
    		lstDic=CacheManager.getInstance().getCacher().getList(SysDictionary.class, key);
    		if(lstDic==null){
    			lstDic=sysDictionaryService.getPaginatedList(search);
    			if(CustomerListUtils.isNotEmpty(lstDic)){
    			   CacheManager.getInstance().getCacher().setObject(key, lstDic, 0);
    			}
    		}
    	}
    	return lstDic;
    }
    
	/***
	 * 移除单个字典的缓存
	 * 
	 * @param id
	 */
	public static void remove(int id) {
		String key = KeyGenerator.getIdKey(ModuleType.Dictionary, id);
		CacheManager.getInstance().getCacher().removeObject(ModuleType.Dictionary, key);
	}
	
	/**
	 * 刷新数据字典缓存
	 * @author huangsongbo
	 */
	public static void updateCache() {
		SysDictionarySearch sysDictionarySearch=new SysDictionarySearch();
		sysDictionarySearch.setStart(-1);
		sysDictionarySearch.setLimit(-1);
		sysDictionarySearch.setIsDeleted(0);
		List<SysDictionary> sysDictionaries=sysDictionaryService.getList(sysDictionarySearch);
		String key=KeyGenerator.getAllListKey(ModuleType.Dictionary);
		CacheManager.getInstance().getCacher().setObject(key, sysDictionaries, -1);
		/*同时更新request*/
		/*request.getSession().setAttribute("sysDictionaryList", sysDictionaries);*/
	}

	
	/**
	 * 通过 type  和 value  查询数据字典
	 * @param type
	 * @param value
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static SysDictionary getSysDictionaryByValue(String type,Integer value){
		SysDictionary sysDictionary = null;
		Map map = new HashMap();
		map.put("type", type);
		map.put("value", value);
		String key = KeyGenerator.getKeyWithMap(ModuleType.Dictionary,map);
		sysDictionary = (SysDictionary) CacheManager.getInstance().getCacher().getObject(key);
		if(sysDictionary == null){
			sysDictionary = sysDictionaryService.getSysDictionaryByValue(type,value);
			if(sysDictionary !=null){
				CacheManager.getInstance().getCacher().setObject(key, sysDictionary, -1);
			}
		}
		return sysDictionary;
	}
	
	
	/**
	 * 优化后 数据字典接口 缓存list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List getListOptimize() {
    	List<SysDicitonaryOptimize> lstDic=new ArrayList<SysDicitonaryOptimize>();
    	String key=KeyGenerator.getAllListKey(ModuleType.DictionaryOptimize);
    	if(CacheManager.getInstance().getCacher()!=null){
    		lstDic=CacheManager.getInstance().getCacher().getList(SysDicitonaryOptimize.class, key);
    		if(lstDic==null){
    			SysDictionarySearch sysDictionarySearch=new SysDictionarySearch();
    			sysDictionarySearch.setOrder("ordering");
				sysDictionarySearch.setOrderNum("asc");
				sysDictionarySearch.setType("spaceShape");
    			sysDictionarySearch.setIsDeleted(0);
    			lstDic=sysDictionaryService.getListOptimize(sysDictionarySearch);
    			CacheManager.getInstance().getCacher().setObject(key, lstDic, -1);
    		}
    	}
    	return lstDic;
    }

	/**
	 * 优化后 数据字典接口 缓存总条数
	 * @return
	 */
	public static int getCountOptimize() {
		int total=0;
    	String key=CacherPrefix.DIC_PREFIX+"total_size_optimize";
		if(CacheManager.getInstance().getCacher()!=null){
			String temp=CacheManager.getInstance().getCacher().get(key);
			if(StringUtils.isNoneBlank(temp)){
				total=Integer.parseInt(temp);
			}
			else{
				SysDictionarySearch sysDictionarySearch=new SysDictionarySearch();
				total=sysDictionaryService.getCountOptimize(sysDictionarySearch);
				CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
			}
		}
    	return total;
    }


}
