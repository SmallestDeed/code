package com.nork.onekeydesign.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.ModuleType;
import com.nork.common.metadata.PageParameter;
import com.nork.common.metadata.QueryParameter;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.StringUtils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.onekeydesign.model.DesignTemplet;
import com.nork.onekeydesign.model.search.DesignTempletSearch;
import com.nork.onekeydesign.service.DesignTempletService;
import com.nork.home.model.DesignProgramDiy;
import com.nork.home.model.SpaceCommon;
import com.nork.home.service.SpaceCommonService;
import com.nork.system.service.SysResLevelCfgService;

/***
 * 设计方案样本房缓存层
 * @author qiu.jun
 * @Date 2016-05-12
 */
public class DesignTempletCacher {
	private static DesignTempletService designTempletService = SpringContextHolder.getBean(DesignTempletService.class);
	private static SpaceCommonService spaceCommonService = SpringContextHolder.getBean(SpaceCommonService.class);
	private static SysResLevelCfgService sysResLevelCfgService=SpringContextHolder.getBean(SysResLevelCfgService.class);
	private static PageParameter getDiyParameter(DesignProgramDiy diy) {
		PageParameter parameter = new PageParameter();
		List<QueryParameter> lstParameter = Lists.newArrayList();
		QueryParameter qp = null;
		parameter.setPageIndex(diy.getStart());
		parameter.setPageSize(diy.getLimit());

		qp = new QueryParameter();
		qp.setName("houseTypeId");
		qp.setValue(String.valueOf(diy.getHouseTypeId()));
		lstParameter.add(qp);
		qp = new QueryParameter();
		qp.setName("designStyleId");
		qp.setValue(String.valueOf(diy.getDesignStyleId()));
		lstParameter.add(qp);

		parameter.setLstParameter(lstParameter);
		return parameter;
	}
	
    /***
	 * 获取单个设计方案详情
	 * 
	 * @param id
	 * @return
	 */
	public static DesignTemplet get(int id) {
		DesignTemplet templet = null;
		String key=KeyGenerator.getIdKey(ModuleType.DesignTemplet, id);
		templet = (DesignTemplet) CacheManager.getInstance().getCacher().getObject(key);
		if (templet == null) {
			templet = designTempletService.get(id);
			if (templet != null) {
				CacheManager.getInstance().getCacher().setObject(key, templet, 0);
			}
		}
		return templet;
	}
	
	 /***
     * 获取所有设计方案样板房
     * @return
     */
    public static List<DesignTemplet> getAllTempletList(){
    	List<DesignTemplet> lstTemplet=Lists.newArrayList();
    	String key=KeyGenerator.getAllListKey(ModuleType.DesignTemplet);
    	if(CacheManager.getInstance().getCacher()!=null){
    		lstTemplet=CacheManager.getInstance().getCacher().getList(DesignTemplet.class, key);
    		if(lstTemplet==null){
    			DesignTempletSearch search=new DesignTempletSearch();
    			lstTemplet=designTempletService.getList(search);
    			CacheManager.getInstance().getCacher().setObject(key, lstTemplet, 0);
    		}
    	}
    	return lstTemplet;
    }
    
	private static PageParameter getPageParameter(DesignTempletSearch search){
		PageParameter parameter=new PageParameter();
		List<QueryParameter> lstParameter=Lists.newArrayList();
		QueryParameter qp=null;
		parameter.setPageIndex(search.getStart());
		parameter.setPageSize(search.getLimit());
		
		qp=new QueryParameter();
		qp.setName("putawayState");
		qp.setValue(String.valueOf(search.getPutawayState()));
		lstParameter.add(qp);
		
		qp=new QueryParameter();
		qp.setName("spaceCommonId");
		qp.setValue(String.valueOf(search.getSpaceCommonId()));
		lstParameter.add(qp);
		
		parameter.setLstParameter(lstParameter);
		return parameter;
	}
    
	 /***
     * 根据SysCode获取所有设计方案样板房
     * @return
     */
    public static List<DesignTemplet> getAllTempletListWithSysCode(String sysCode){
    	List<DesignTemplet> lstTemplet=Lists.newArrayList();
    	Map<String, String> map=new HashMap<String, String>();
    	map.put("sysCode", sysCode);
    	String key=KeyGenerator.getAllListKeyWithMap(ModuleType.DesignTemplet,map);
    	//////System.out.println("key:"+key);
    	if(CacheManager.getInstance().getCacher()!=null){
    		lstTemplet=CacheManager.getInstance().getCacher().getList(DesignTemplet.class, key);
    		if(lstTemplet==null){
				DesignTemplet desTemplet = new DesignTemplet();
				desTemplet.setSysCode(sysCode);   
    			lstTemplet=designTempletService.getList(desTemplet);
    			if(CustomerListUtils.isNotEmpty(lstTemplet)){
    			    CacheManager.getInstance().getCacher().setObject(key, lstTemplet, 0);
    			}
    		}
    		else{
    			//////System.out.println("getfromcaher");
    		}
    	}
    	return lstTemplet;
    }
    
	/***
	 * 获取带查询参数的设计方案样板房总记录数
	 * @param spaceCommon
	 * @return
	 */
	public static int getTotalWithParameter(DesignTempletSearch search,int userId){
		int total=0;
		PageParameter parameter= getPageParameter(search);
		String key=KeyGenerator.getTotalWithParameter(ModuleType.DesignTemplet, parameter);
		String temp=CacheManager.getInstance().getCacher().get(key);
		if(StringUtils.isBlank(temp)){
			total=designTempletService.getCount(search,userId);
			if(total>0){
			   CacheManager.getInstance().getCacher().set(key , String.valueOf(total), 0);
			}
		}
		else{
			total=Integer.parseInt(temp);
			//////System.out.println("get from cacher,key:"+key);
		}
		return total;
	}
    
	/***
	 * 获取带查询参数的设计方案样板房总记录数
	 * @param spaceCommon
	 * @return
	 */
	public static int getTotalWithParameter2(DesignTempletSearch search,Integer userType,int userId){
		int total=0;
		PageParameter parameter= getPageParameter(search);
		String key=KeyGenerator.getTotalWithParameter(ModuleType.DesignTemplet, parameter);
		String otherkeyvalue = "_userType=" + userType;
		String temp=CacheManager.getInstance().getCacher().get(key + otherkeyvalue);
		if(StringUtils.isBlank(temp)){
			total=designTempletService.getCount(search,userId);
			if(total>0){
			   CacheManager.getInstance().getCacher().set(key + otherkeyvalue , String.valueOf(total), 0);
			}
		}
		else{
			total=Integer.parseInt(temp);
			//////System.out.println("get from cacher,key:"+key);
		}
		return total;
	}
	
	/***
	 * 获取带查询参数的设计方案样板房的分页记录
	 * @param spaceCommon
	 * @return
	 */
	public static List<DesignTemplet> getPageWithParameter(DesignTempletSearch search,int userId){
		List<DesignTemplet> lstCommon=Lists.newArrayList();
		PageParameter parameter= getPageParameter(search);
		String key=KeyGenerator.getPageQueryKeyParameter(ModuleType.DesignTemplet, parameter);
		lstCommon=CacheManager.getInstance().getCacher().getList(DesignTemplet.class, key);
		if(CustomerListUtils.isEmpty(lstCommon)){
			lstCommon= designTempletService.getPaginatedList(search,userId);
			CacheManager.getInstance().getCacher().setObject(key, lstCommon, 0);
		}
		else{
			//////System.out.println("get from cacher,key:"+key);
		}
		return lstCommon;
	}
	
	/***
	 * 获取带查询参数的设计方案样板房的分页记录
	 * @param spaceCommon
	 * @return
	 */
	public static List<DesignTemplet> getPageWithParameter2(DesignTempletSearch search,Integer userType,int userId){
		List<DesignTemplet> lstCommon=Lists.newArrayList();
		PageParameter parameter= getPageParameter(search);
		String key=KeyGenerator.getPageQueryKeyParameter(ModuleType.DesignTemplet, parameter);
		String otherkeyvalue = "_userType=" + userType;
		key = key + otherkeyvalue;
		lstCommon=CacheManager.getInstance().getCacher().getList(DesignTemplet.class, key);
		if(CustomerListUtils.isEmpty(lstCommon)){
			lstCommon= designTempletService.getPaginatedList(search,userId);
			CacheManager.getInstance().getCacher().setObject(key, lstCommon, 0);
		}
		else{
			//////System.out.println("get from cacher,key:"+key);
		}
		return lstCommon;
	}
	 
	/***
	 * 设计模板 总条数
	 * @param designTempletSearch
	 * @return total
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int getDesignTempletCount(DesignTempletSearch designTempletSearch,int userId){
		int total=0;
		Map map=new HashMap();
		Integer start=designTempletSearch.getStart();
		Integer limit=designTempletSearch.getLimit();
		map.put("start", start);
		map.put("limit", limit);
		
		String key=KeyGenerator.getKeyWithMap(ModuleType.DesignTemplet, map);
		if (CacheManager.getInstance().getCacher() != null) {
			String totalString=CacheManager.getInstance().getCacher().get(key);
			if(totalString!=null&&!"".equals(totalString)){
				total=Integer.parseInt(totalString);
			}else{
				total=designTempletService.getCount(designTempletSearch,userId);
				if(total>0){
					CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
				}
			}
		}
		//////System.out.println("********WebDesignTempletController-spaceDesignWeb-getDesignTempletCount-key" + key+"********");
		return 0;
	}
	
	/***
	 * 设计模板 列表
	 * @param designTempletSearch
	 * @return total
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<DesignTemplet>  getPaginatedList(DesignTempletSearch designTempletSearch,int userId){
		List<DesignTemplet> list = new ArrayList<DesignTemplet> ();
		Map map=new HashMap();
		Integer start=designTempletSearch.getStart();
		Integer limit=designTempletSearch.getLimit();
		map.put("start", start);
		map.put("limit", limit);
		
		String key=KeyGenerator.getKeyWithMap(ModuleType.DesignTemplet, map);
		if (CacheManager.getInstance().getCacher() != null) {
			list=CacheManager.getInstance().getCacher().getList(DesignTemplet.class, key);
			if (CustomerListUtils.isNotEmpty(list)) {
				list=designTempletService.getPaginatedList(designTempletSearch,userId);
				if (!CustomerListUtils.isNotEmpty(list)) {
					CacheManager.getInstance().getCacher().setObject(key, list, 0);
				}
			}
	    }
		//////System.out.println("********WebDesignTempletController-spaceDesignWeb-getPaginatedList-key" + key+"********");
		return list;
	}
	
	
	/***
	 * 公共空间
	 * @param spaceCommonId
	 * @return SpaceCommon
	 */
	@SuppressWarnings({ "rawtypes" })
	public static SpaceCommon getSpaceCommon(Integer spaceCommonId){
		SpaceCommon spacecommon= new SpaceCommon();
		Map map= new HashMap();
		map.put("spaceCommonId", spaceCommonId);
		String key=KeyGenerator.getKeyWithMap(ModuleType.DesignTemplet, map);
		if (CacheManager.getInstance().getCacher() != null) {
			spacecommon=(SpaceCommon) CacheManager.getInstance().getCacher().getObject(key);
			if(spacecommon==null){
				spacecommon=spaceCommonService.get(spaceCommonId);
				if(spacecommon!=null){
					CacheManager.getInstance().getCacher().setObject(key, spacecommon, 0);
				}
			}
		}
		
		//////System.out.println("********WebDesignTempletController-spaceDesignWeb-getSpaceCommon-key" + key+"********");
		return spacecommon;
		
	}
	
	
	
}
