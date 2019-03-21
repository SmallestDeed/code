package com.nork.system.controller.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.system.cache.ResourceCacher;
import com.nork.system.cache.SysUserCacher;
import com.nork.system.cache.SysUserFansCacher;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysUser;
import com.nork.system.model.SysUserFans;
import com.nork.system.model.search.SysUserFansSearch;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysUserFansService;
import com.nork.system.service.SysUserService;

/**
 * @Title: SysUserController.java
 * @Package com.nork.system.controller
 * @Description:系统-用户Controller
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 12:30:46
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/web/system/sysUserFans")
public class WebSysUserFansController {
	private static Logger logger = Logger.getLogger(WebSysUserFansController.class);
	private final JsonDataServiceImpl<SysUser> JsonUtil = new JsonDataServiceImpl<SysUser>();
	private final String STYLE = "jsp";
	private final String JSPSTYLE = "online";
	private final String JSPMAIN = "/" + JSPSTYLE + "/user";

	//private final String PIC_UPLOAD_PATH = "system.sysUser.pic.upload.path";
	@Autowired
	private SysUserFansService sysUserFansService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ResPicService resPicService;
	/**
	 * 取消关注 接口
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(Integer userId, String msgId, HttpServletRequest request, HttpServletResponse response) {

		if (msgId == null) {
			return new ResponseEnvelope<SysUser>(false, "参数msgId不能为空！", msgId);
		}
		if (userId == null) {
			return new ResponseEnvelope<SysUser>(false, "参数userId不能为空！", msgId);
		}
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		try {
			SysUserFans userFans = new SysUserFans();
			userFans.setUserId(userId);
			userFans.setFansUserId(loginUser.getId());
			List<SysUserFans> sysUserFans=null;
			if(Utils.enableRedisCache()){
				sysUserFans = SysUserFansCacher.getList(userFans);
			}else{
				sysUserFans = sysUserFansService.getList(userFans);
			}
			
			if (sysUserFans != null && sysUserFans.size() > 0) {
				Integer userFansId = sysUserFans.get(0).getId();
				sysUserFansService.delete(userFansId);
				SysUserFansCacher.remove(userFansId);
				SysUserFansCacher.remove(userFans);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUserFans>(false, " 删除失败！ ", msgId);
		}
		return new ResponseEnvelope<SysUserFans>(true, msgId, true);
	}

	/**
	 * 用户粉丝表列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(@ModelAttribute("sysUserFansSearch") SysUserFansSearch sysUserFansSearch,String msgId
				,HttpServletRequest request, HttpServletResponse response) {
		//
		if( sysUserFansSearch.getUserId() == null && sysUserFansSearch.getFansUserId() == null ){
			return new ResponseEnvelope<SysUserFans>(false, "参数userId和fansUserId必须传入一个!",msgId);
		}
		List<SysUserFans> userFansList = new ArrayList<>();
		int total = 0;
		try{
			if(Utils.enableRedisCache()){
				total = SysUserFansCacher.getCount(sysUserFansSearch);
			}else{
				total = sysUserFansService.getCount(sysUserFansSearch);
			}
			
			if( total > 0 ){
				if(Utils.enableRedisCache()){
					userFansList = SysUserFansCacher.getList(sysUserFansSearch);
				}else{
					userFansList = sysUserFansService.getPaginatedList(sysUserFansSearch);
				}
				
				for(SysUserFans sysUserFans:userFansList){
					SysUser user = new SysUser();
					if(sysUserFansSearch.getUserId() == null ){
						if(Utils.enableRedisCache()){
							user = SysUserCacher.get(sysUserFans.getUserId());
						}else{
							user = sysUserService.get(sysUserFans.getUserId());
						}
						
					}else if( sysUserFansSearch.getFansUserId() == null ){
						if(Utils.enableRedisCache()){
							user = SysUserCacher.get(sysUserFans.getFansUserId());
						}else{
							user = sysUserService.get(sysUserFans.getFansUserId());
						}
						
					}

					if( user == null ){
						continue;
					}
					sysUserFans.setNickName(user.getNickName());
                    sysUserFans.setUserName(user.getUserName());
                    ResPic resPic=null;
                    if(Utils.enableRedisCache()){
                    	resPic = ResourceCacher.getPic(user.getPicId());
                    }else{
                    	resPic = resPicService.get(user.getPicId());
                    }
                    
					if( resPic != null ){
						sysUserFans.setPic(resPic.getPicPath());
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			return new ResponseEnvelope<SysUserFans>(false, "查询异常!",msgId);
		}
		return new ResponseEnvelope<SysUserFans>(total, userFansList,msgId);

//		 LoginUser loginUser = new LoginUser();
//		 if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
//			loginUser.setLoginName("nologin");
//		 }else{
//		    loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
//		    sysUserFansSearch.setFansUserId(loginUser.getId());
//		 }
//
// 		String jsonStr = Utils.getJsonStr(request);
//		if (jsonStr != null && jsonStr.trim().length() > 0){
//			sysUserFansSearch = (SysUserFansSearch)JsonUtil.getJsonToBean(jsonStr, SysUserFansSearch.class);
//			if(sysUserFansSearch == null){
//			   return new ResponseEnvelope<SysUserFans>(false, "传参异常!","none");
//			}
//		}
		
//		List<SysUserFans> list = new ArrayList<SysUserFans> ();
//		int total = 0;
//		try {
//			total = sysUserFansService.getCount(sysUserFansSearch);
//
//			if (total > 0) {
//				list = sysUserFansService.getPaginatedList(sysUserFansSearch);
//				for(SysUserFans sysUserFans:list){
//
//					SysUser user = sysUserService.get(sysUserFans.getUserId());
//					sysUserFans.setNickName(user==null?"":user.getUserName());
//					//sysUserFans.setPic(user==null?"":user.getPicId());
//				}
//			}
//
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEnvelope<SysUserFans>(false, "数据异常!",sysUserFansSearch.getMsgId());
//		}
//		return new ResponseEnvelope<SysUserFans>(total, list,sysUserFansSearch.getMsgId());
	}
	
	
	/**
	 * 关注用户(移动端引用)
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(String userId, String msgId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (msgId == null) {
			return new ResponseEnvelope<SysUser>(false, "参数msgId不能为空!", msgId);
		}
		if (userId == null) {
			return new ResponseEnvelope<SysUser>(false, "参数userId不能为空!", msgId);
		}
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		SysUserFans userFans = new SysUserFans();
		userFans.setUserId(Integer.valueOf(userId));
		userFans.setFansUserId(loginUser.getId());
		List<SysUserFans> list=null;
		if(Utils.enableRedisCache()){
			list = SysUserFansCacher.getList(userFans);
		}else{
			list = sysUserFansService.getList(userFans);
		}
		
		if (CustomerListUtils.isNotEmpty(list)) {
			return new ResponseEnvelope<SysUser>(false, "该用户已关注过，请查看关注列表!", msgId);
		}
		SysUserFans sysUserFans = new SysUserFans();
		try {
			sysUserFans.setUserId(Integer.valueOf(userId));
			sysUserFans.setFansUserId(loginUser.getId());
			sysSave(sysUserFans, request);
			sysUserFansService.add(sysUserFans); 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUserFans>(false, "数据异常!", msgId);
		}
		return new ResponseEnvelope<SysUserFans>(true, msgId, true);
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(SysUserFans model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(
							Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}
}
