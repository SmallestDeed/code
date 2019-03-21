package com.nork.system.controller.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.cache.utils.JedisUtils;
import com.nork.common.model.ResponseEnvelope;
import com.nork.product.cache.ProCategoryCacher;
import com.nork.system.cache.SysDictionaryCacher;
import com.nork.system.model.SysCache;

@Controller
@RequestMapping("/{style}/web/system/sysCache")
public class WebSysCacheController {
	
	@RequestMapping("/clearCache")
	@ResponseBody
	public Object clearCache(HttpServletRequest request,HttpServletResponse response,String msgId){
		
		String keyStart = request.getParameter("keyStart") == null ? "" : request.getParameter("keyStart");
		if(StringUtils.isBlank(keyStart)){
			return false;
		}
		if(StringUtils.isNotBlank(keyStart) && StringUtils.isNotEmpty(keyStart)){
			if(!keyStart.equals("loginUser_") && !keyStart.equals("sys_user_") && !keyStart.equals("dictionary_optimize_") && !keyStart.equals("dic_")){
				JedisUtils.removeWithKeyPrefix(keyStart);
			}
		}
		return true;
	}
	
	/**
	 * 查询缓存列表
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object list(String msgId){
		Set<String> keys=JedisUtils.getAllKey();
		List<SysCache> list=new ArrayList<SysCache>();
		for(String key:keys){
			Object object=JedisUtils.getObject(key);
			if(object!=null){
				list.add(new SysCache(key, object.toString()));
			}
		}
		return new ResponseEnvelope<>(list, msgId, true);
	}
	
	/**
	 * 刷新数据字典缓存
	 * @author huangsongbo
	 */
	@RequestMapping("/updateSysDictionaryCache")
	@ResponseBody
	public void updateSysDictionaryCache(){
		SysDictionaryCacher.updateCache();
	}
	
	/**
	 * 刷新产品分类缓存
	 * @author huangsongbo
	 */
	@RequestMapping("/updateProCategoryCache")
	@ResponseBody
	public void updateProCategoryCache(){
		ProCategoryCacher.updateCache();
	}
	
}
