package com.sandu.authz;

import java.util.*;

import com.sandu.gson.GsonUtil;
public class UserInfo{
	
	private Set<String> permissions;
	private Map<String,Set<String>> queryFields;
	
	public boolean isPermit(String[] perms) {
		if(permissions==null || permissions.size()<=0) {
			return false;
		}
		if(perms!=null && perms.length>0) {
//			for(String perm:perms) {
//				if(!permissions.contains(perm)) {
//					return false;
//				}
//			}
//			return true;

			// Modified by songjianming@sanduspace.cn on 2019/1/16
			// 修复多个权限字段时不匹配问题
			for (String item : perms) {
				if (permissions.contains(item)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public Object doFiledsFilter(String filterCode,String filterPath,Object retObj) {
		String jsonRetVal = GsonUtil.toJson(retObj);
		Map<String,Object> retMap = GsonUtil.fromJson(jsonRetVal, Map.class);
		if(queryFields==null) queryFields = new HashMap<String,Set<String>>();
		Set<String> fields = queryFields.get(filterCode);
		if(fields==null) fields = new HashSet<String>();
		//当前对象字段过滤
		if(filterPath.equals("/")) {
			processRecord(fields,retMap);
		}
		//当前对象属性字段过滤
		else if(filterPath.startsWith("/")) {
			String propertyName = filterPath.split("/")[1];
			Object targetObj = retMap.get(propertyName);
			//当前对象属性是实体对象
			if(targetObj instanceof Map) {
				Map map = (Map)targetObj;
				processRecord(fields,map);
			}
			//当前对象属性是实体列表
			else if(targetObj instanceof List) {
				 List list = (List)targetObj;
				 for(int i=0;i<list.size();i++) {
					 Object obj = list.get(i);
					 //列表中的实体对象
					 if(obj instanceof Map) {
						 Map map = (Map)obj;
						 processRecord(fields,map);
					 }
				 }
			 }
		}
		String jsonResult = GsonUtil.toJson(retMap); 
		return GsonUtil.fromJson(jsonResult, retObj.getClass());
	}
	
	/**
	 * 实体对象字段过滤 
	 * @param fields 用户拥有的字段权限集合
	 * @param record 实体对象
	 */
	private void processRecord(Set<String> fields,Map<String,Object> record) {
		if(record == null) return;
		for (Map.Entry<String, Object> entry : record.entrySet()) {
			if(!fields.contains(entry.getKey())){
				record.put(entry.getKey(), null);
			}
		}
	}
		

}
