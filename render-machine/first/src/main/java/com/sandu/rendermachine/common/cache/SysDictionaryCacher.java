package com.sandu.rendermachine.common.cache;

import com.google.common.collect.Lists;
import com.sandu.rendermachine.common.util.SpringContextHolder;
import com.sandu.rendermachine.model.metadata.ModuleType;
import com.sandu.rendermachine.model.system.SysDicitonaryOptimize;
import com.sandu.rendermachine.model.system.SysDictionary;
import com.sandu.rendermachine.model.system.SysDictionarySearch;
import com.sandu.rendermachine.service.system.SysDictionaryService;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 字典数据缓存层
 * @author qiu.jun
 * @date 2016-05-10
 */
public class SysDictionaryCacher {

	private static SysDictionaryService sysDictionaryService = SpringContextHolder.getBean(SysDictionaryService.class);

    /***
     * 获取所有字典数据
     * @return
     */
    public static List<SysDictionary> getAllList(){
    	List<SysDictionary> lstDic= Lists.newArrayList();
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
				//TODO  APP端为了生成导航，需要产品小类来排序
				// 先获取所有大类
				List<SysDictionary> productTypeList = sysDictionaryService.findAllByType("productType");

				getAllSmallTypeByType(lstDic,productTypeList);

    			CacheManager.getInstance().getCacher().setObject(key, lstDic, -1);
    		}
    	}
    	return lstDic;
    }

    //获取大类下的所有小类
	public static void getAllSmallTypeByType(List<SysDicitonaryOptimize> list, List<SysDictionary> productTypeList) {
		if( productTypeList != null && productTypeList.size() > 0 ){
			// 获取大类下的所有小类
			List<SysDicitonaryOptimize> productSmallTypeList = null;
			SysDictionarySearch sysDicSearch = null;
			for( SysDictionary sysDictionary : productTypeList ){
				String productType = sysDictionary.getValuekey();
				if( StringUtils.isNotBlank(productType) ){
					sysDicSearch = new SysDictionarySearch();
					sysDicSearch.setType(productType);
					productSmallTypeList = sysDictionaryService.findAll(sysDicSearch);
					list.addAll(productSmallTypeList);
				}
			}
		}
	}

}
