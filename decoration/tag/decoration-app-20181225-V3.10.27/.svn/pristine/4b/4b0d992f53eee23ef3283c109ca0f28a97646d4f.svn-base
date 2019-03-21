package com.nork.system.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.CacherPrefix;
import com.nork.common.metadata.ModuleType;
import com.nork.common.metadata.PageParameter;
import com.nork.common.metadata.QueryParameter;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.StringUtils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.system.model.BaseArea;
import com.nork.system.model.search.BaseAreaSearch;
import com.nork.system.model.web.WBaseArea;
import com.nork.system.service.BaseAreaService;

/***
 * 基本区域缓存层
 * @author qiu.jun
 * @date 2016-05-09
 */
public class BaseAreaCacher {
	private static BaseAreaService baseAreaService = SpringContextHolder.getBean(BaseAreaService.class);
	private static PageParameter getParameter(BaseAreaSearch search) {
		PageParameter parameter = new PageParameter();
		List<QueryParameter> lstParameter = Lists.newArrayList();
		QueryParameter qp = null;
		parameter.setPageIndex(search.getStart());
		parameter.setPageSize(search.getLimit());
		if (search.getLevelId() != null) {
			qp = new QueryParameter();
			qp.setName("levelId");
			qp.setValue(String.valueOf(search.getLevelId()));
			lstParameter.add(qp);
		}
		if(StringUtils.isNotBlank(search.getPid())){
			qp = new QueryParameter();
			qp.setName("pid");
			qp.setValue(search.getPid());
			lstParameter.add(qp);
		}
		if(search.getOrdering() != null){
			qp = new QueryParameter();
			qp.setName("ordering");
			qp.setValue(String.valueOf(search.getOrdering()));
			lstParameter.add(qp);
		}
		if(search.getIsDeleted() != null){
			qp = new QueryParameter();
			qp.setName("isDeleted");
			qp.setValue(String.valueOf(search.getIsDeleted()));
			lstParameter.add(qp);
		}
		parameter.setLstParameter(lstParameter);
		return parameter;
	}
	
	  /***
     * 获取区域总记录数
     * @param search
     * @return
     */
	public static int getCount(BaseAreaSearch search) {
		int total = 0;
		PageParameter parameter = getParameter(search);
		String key = KeyGenerator.getTotalWithParameter(ModuleType.BaseArea, parameter);
		String temp = CacheManager.getInstance().getCacher().get(key);
		if (StringUtils.isBlank(temp)) {
			total = baseAreaService.getCount(search);
			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
		} else {
			total = Integer.parseInt(temp);
			//////System.out.println("get from cacher,key:" + key);
		}
		return total;
	}
	
	/***
	 * 获取所有区域列表
	 * @param search
	 * @return
	 */
	public static List<BaseArea> getAllList(BaseAreaSearch search) {
		List<BaseArea> lstMessage = Lists.newArrayList();
		PageParameter parameter = getParameter(search);
		String key = KeyGenerator.getAllListKeyWithParameter(ModuleType.BaseArea, parameter);
		lstMessage = CacheManager.getInstance().getCacher().getList(BaseArea.class, key);
		if (CustomerListUtils.isEmpty(lstMessage)) {
			lstMessage = baseAreaService.getList(search);
			CacheManager.getInstance().getCacher().setObject(key, lstMessage, 0);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstMessage;
	}
	
	public static List<BaseArea> getAllList() {
		List<BaseArea> lstMessage = Lists.newArrayList();
		String key = "_allArea";
		lstMessage = CacheManager.getInstance().getCacher().getList(BaseArea.class, key);
		if (CustomerListUtils.isEmpty(lstMessage)) {
			lstMessage = baseAreaService.getList(new BaseArea());
			CacheManager.getInstance().getCacher().setObject(key, lstMessage, -1);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstMessage;
	}
	
	/***
	 * 获取城市列表数据
	 * @return
	 */
	public static List<BaseArea> getCityList(){
		List<BaseArea> lstArea=null;
		String key=KeyGenerator.getAllListKey(ModuleType.BaseArea);
		if(CacheManager.getInstance().getCacher()!=null){
			lstArea=CacheManager.getInstance().getCacher().getList(BaseArea.class, key);
			if(lstArea==null){
				lstArea=baseAreaService.getCityList();
				CacheManager.getInstance().getCacher().setObject(key, lstArea, 0);
			}
		}
		return lstArea;
	}
	
	/***
	 * 根据区域编码获取区域详情
	 * @param areaCode
	 * @return
	 */
	public static WBaseArea getAreaByCode(String areaCode){
		WBaseArea area=null;
		String key=CacherPrefix.AREA_PREFIX+"code_"+areaCode;
		if(CacheManager.getInstance().getCacher()!=null){
			area=(WBaseArea)CacheManager.getInstance().getCacher().getObject(key);
			if(area==null){
				BaseAreaSearch baseAreaSearch = new BaseAreaSearch();
				baseAreaSearch.setAreaCode(areaCode);
				area=baseAreaService.getByCode(baseAreaSearch);
				if(area!=null){
					CacheManager.getInstance().getCacher().setObject(key, area, 0);
				}
			}
		}
		return area;
	}
	
	/***
	 * 获取设计方案列表
	 * 
	 * @param plan
	 * @return
	 */
	public static List<BaseArea> getList(BaseArea baseArea) {
		List<BaseArea> lstArea = Lists.newArrayList();
		Map<String, String> map=new HashMap<String, String>();
		map.put("areaCode", baseArea.getAreaCode());
		String key = KeyGenerator.getAllListKeyWithMap(ModuleType.BaseArea, map);
		if(CacheManager.getInstance().getCacher()!=null){
			lstArea = CacheManager.getInstance().getCacher().getList(BaseArea.class, key);
			if (CustomerListUtils.isEmpty(lstArea)) {
				lstArea = baseAreaService.getList(baseArea);
				CacheManager.getInstance().getCacher().setObject(key, lstArea, 0);
			} else {
				//////System.out.println("get from cacher,key:" + key);
			}
		}
		return lstArea;
	}
	
	
	
	
	/***
	 * 获取地区基础总条数
	 * 
	 * @param plan
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static int getBaseAreaCount(BaseAreaSearch baseAreaSearch){
		int total = 0;
		Map map=new HashMap();
		map.put("Count", "Count");
		String key = KeyGenerator.getKeyWithMap(ModuleType.BaseAreaSearch, map);
		if(CacheManager.getInstance().getCacher()!=null){
			String totalString = CacheManager.getInstance().getCacher().get(key);
			if(totalString!=null&&!"".equals(totalString)){
				total=Integer.parseInt(totalString);
			}else{
				total = baseAreaService.getCount(baseAreaSearch);
				if(total>0){
					CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
				}
			}
		}
		//////System.out.println("********WebBaseAreaController-listAll-getBaseAreaCount-key:"+key+"********");
		return total;
	}
	
	
	
	
	/***
	 * 获取地区基础 
	 * 
	 * @param plan
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<BaseArea> getBaseAreaList(BaseAreaSearch baseAreaSearch){
		List<BaseArea> list = new ArrayList<BaseArea>();
		Map map=new HashMap();
		String key = KeyGenerator.getKeyWithMap(ModuleType.BaseAreaSearch, map);
		if(CacheManager.getInstance().getCacher()!=null){
			//////System.out.println("HC");
			list= CacheManager.getInstance().getCacher().getList(BaseArea.class, key);
			if (CustomerListUtils.isEmpty(list)) {
				//////System.out.println("DB");
				list = baseAreaService.getList(baseAreaSearch);
				if (!CustomerListUtils.isEmpty(list)) {
					//////System.out.println("DB2");
					CacheManager.getInstance().getCacher().setObject(key, list, 0);
				}
			}
		}
		//////System.out.println("********WebBaseAreaController-listAll-getBaseAreaList-key:"+key+"********");
		return list;
	}
}
