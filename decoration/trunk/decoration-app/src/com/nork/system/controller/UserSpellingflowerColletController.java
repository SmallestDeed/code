package com.nork.system.controller;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.design.model.DesginPlanSpellingflowerRecord;
import com.nork.system.model.UserSpellingflowerCollet;
import com.nork.system.model.search.UserSpellingflowerColletSearch;
import com.nork.system.service.UserSpellingflowerColletService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Title: UserSpellingflowerColletController.java 
 * @Package com.nork.system.controller
 * @Description:拼花-我的瓷砖拼花收藏Controller
 * @createAuthor roc
 * @CreateDate 2017-11-23 14:38:44
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/system/userSpellingflowerCollet")
public class UserSpellingflowerColletController {
	private static Logger logger = Logger.getLogger(UserSpellingflowerColletController.class);
	
	@Autowired
	private UserSpellingflowerColletService userSpellingflowerColletService;

	/**
	 * 查询用户收藏瓷砖拼花集合接口
	 * @param colletSearch
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public ResponseEnvelope list(@ModelAttribute("colletSearch") UserSpellingflowerColletSearch colletSearch, HttpServletRequest request){
		logger.info("查询用户收藏瓷砖拼花集合接口 Controller 开始：参数="+ JSON.toJSONString(colletSearch));
		String msgId = colletSearch.getMsgId();

		/**  登录校验 */
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if(loginUser == null || loginUser.getId().intValue() < 1) {
			return new ResponseEnvelope(false,"请重新登录",msgId);
		}

		/** 参数校验 */
		String colletName = colletSearch.getColletName();
		if(StringUtils.isNotEmpty(colletName)){
			if(colletName.length() > 10){
				return new ResponseEnvelope(false,"收藏名长度不能大于10",msgId);
			}
		}

		/** 设值查询参数 */
		colletSearch.setUserId(loginUser.getId());

		/**  需要调用service 返回数据 */
		return userSpellingflowerColletService.getPaginatedList(colletSearch);
	}

	/**
	 * 保存用户收藏瓷砖拼花接口
	 * @param userSpellingflowerCollet
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public ResponseEnvelope save(@ModelAttribute("userSpellingflowerCollet") UserSpellingflowerCollet userSpellingflowerCollet,
								 HttpServletRequest  request){
		logger.info("保存用户收藏瓷砖拼花接口 Controller 开始：参数="+ JSON.toJSONString(userSpellingflowerCollet));
		String msgId = userSpellingflowerCollet.getMsgId();
		ResponseEnvelope<DesginPlanSpellingflowerRecord> result = null;

		/**  登录校验 */
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if(loginUser == null || loginUser.getId().intValue() < 1) {
			return new ResponseEnvelope(false,"请重新登录",msgId);
		}

		/** 参数校验 */
		String colletName = userSpellingflowerCollet.getColletName();
		if(StringUtils.isNotBlank(colletName)){
			if(colletName.length()>10){
				return new ResponseEnvelope(false,"拼花收藏名长度不能大于10",msgId);
			}
		}else{
			return new ResponseEnvelope(false,"拼花收藏名不能为空",msgId);
		}
		String recordId = userSpellingflowerCollet.getRecordId();
		if(StringUtils.isNotBlank(recordId)){
			try {
				Integer.parseInt(recordId);
			}catch (Exception e){
				return new ResponseEnvelope(false,"拼花记录表id参数有误",msgId);
			}
		}else{
			return new ResponseEnvelope(false,"拼花记录表id不能为空",msgId);
		}

		/** 调用service 返回信息 */
		this.sysSave(userSpellingflowerCollet,request);
		return userSpellingflowerColletService.add(userSpellingflowerCollet,loginUser);
	}


	/**
	 * 删除用户收藏瓷砖拼花接口
	 * @param msgId
	 * @param colletId 收藏表id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public ResponseEnvelope del(String  msgId,String  colletId,String authorization,HttpServletRequest  request){
		logger.info("删除用户收藏瓷砖拼花接口 Controller 开始：参数colletId="+ colletId);
		/**  登录校验 */
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if(loginUser == null || loginUser.getId().intValue() < 1) {
			return new ResponseEnvelope(false,"请重新登录",msgId);
		}

		/** 参数校验 */
		if(StringUtils.isEmpty(colletId)||StringUtils.isBlank(colletId)){
			return new ResponseEnvelope(false,"收藏id不能为空",msgId);
		}else{
			try {
				Integer.parseInt(colletId);
			}catch (Exception e){
				return new ResponseEnvelope(false,"收藏id参数有误",msgId);
			}
		}

		/** 调用service删除数据,返回信息 */
		return  userSpellingflowerColletService.delete(Integer.parseInt(colletId),loginUser.getId(),msgId);

	}

	/**
	 * 修改用户收藏瓷砖拼花名称接口
	 * @param msgId
	 * @param colletId 收藏表id
	 * @param colletName 收藏表名称
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public ResponseEnvelope update(String  msgId,String  colletId,String  colletName,String authorization,HttpServletRequest  request){
		logger.info("修改用户收藏瓷砖拼花名称接口 Controller 开始：参数colletId="+ colletId+"colletName="+colletName);
		/**  登录校验 */
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if(loginUser == null || loginUser.getId().intValue() < 1) {
			return new ResponseEnvelope(false,"请重新登录",msgId);
		}

		/** 参数校验 */
		if(StringUtils.isNotBlank(colletId)&&
				StringUtils.isNotBlank(colletName)){
			if(colletName.length() > 10){
				return new ResponseEnvelope(false,"拼花收藏名长度不能大于10",msgId);
			}
		}else{
			return new ResponseEnvelope(false,"收藏id与收藏名参数不能为空",msgId);
		}

		/** 调用service删除数据,返回信息 */
		//1.设值修改参数
		UserSpellingflowerCollet collet = new UserSpellingflowerCollet();
		collet.setUserId(loginUser.getId());
		collet.setId(Integer.parseInt(colletId));
		collet.setColletName(colletName);
		collet.setMsgId(msgId);
		//2.调用，返回
		return userSpellingflowerColletService.update(collet);
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(UserSpellingflowerCollet model,HttpServletRequest request){
		if(model != null){
			LoginUser loginUser = new LoginUser();
			if(request.getSession()==null||request.getSession().getAttribute("loginUser")==null){
				loginUser.setLoginName("nologin");
			}else{
				loginUser = (LoginUser)request.getSession().getAttribute("loginUser");
			}

			if(model.getId() == null){
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if(model.getSysCode()==null || "".equals(model.getSysCode())){
					model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}
}
