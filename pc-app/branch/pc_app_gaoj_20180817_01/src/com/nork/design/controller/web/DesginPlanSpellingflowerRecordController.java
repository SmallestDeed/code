package com.nork.design.controller.web;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.jwt.Jwt;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.design.model.DesginPlanSpellingflowerRecord;
import com.nork.design.model.search.DesginPlanSpellingflowerRecordSearch;
import com.nork.design.service.DesginPlanSpellingflowerRecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import com.nork.common.jwt.Jwt;

/**   
 * @Title: DesginPlanSpellingflowerRecordController.java 
 * @Package com.nork.desgin.controller
 * @Description:拼花-设计方案瓷砖拼花记录Controller
 * @createAuthor roc
 * @CreateDate 2017-11-23 14:41:02
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/design/desginPlanSpellingflowerRecord")
public class DesginPlanSpellingflowerRecordController {
	private static Logger logger = Logger.getLogger(DesginPlanSpellingflowerRecordController.class);
	
	@Autowired
	private DesginPlanSpellingflowerRecordService desginPlanSpellingflowerRecordService;

	/**
	 * 查询当前设计方案已使用瓷砖拼花集合接口
	 * @param recordSearch
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public ResponseEnvelope list(@ModelAttribute("recordSearch") DesginPlanSpellingflowerRecordSearch recordSearch,
					   HttpServletRequest request){
		logger.info("查询当前设计方案已使用瓷砖拼花集合接口 Controller开始:参数="+ JSON.toJSONString(recordSearch));
		String msgId = recordSearch.getMsgId();

		/**  登录校验 */
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if(loginUser == null || loginUser.getId().intValue() < 1) {
			return new ResponseEnvelope(false,"请重新登录",msgId);
		}

		/**  参数校验 */
		String desginPlanId  = recordSearch.getDesginPlanId();
		if(null==desginPlanId||""==desginPlanId){
			return new ResponseEnvelope(false,"方案id不能为空",msgId);
		}
		try {
			recordSearch.setPlanId(Integer.parseInt(recordSearch.getDesginPlanId()));
		}catch (Exception e){
			return new ResponseEnvelope(false,"参数有误",msgId);
		}


		/**  调用service返回  */
		return desginPlanSpellingflowerRecordService.getPaginatedList(recordSearch,loginUser);

	}

	/**
	 * 保存当前设计方案已使用瓷砖拼花接口
	 * @param record
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public ResponseEnvelope save(@ModelAttribute("recordSearch") DesginPlanSpellingflowerRecord record,
								 @RequestParam MultipartFile picFile, HttpServletRequest request){
		logger.info("保存当前设计方案已使用瓷砖拼花接口 Controller开始:参数="+ JSON.toJSONString(record));
		String msgId = record.getMsgId();

		/**  登录校验 */
		Map<String, Object> authorizationMap = Jwt.validToken(record.getAuthorization());
		request.setAttribute("AuthorizationData", authorizationMap.get("data"));
		LoginUser loginUser = SystemCommonUtil.getLoginUserInfoByAuthData(request);
		if(loginUser == null || loginUser.getId().intValue() < 1) {
			return new ResponseEnvelope(false,"请重新登录",msgId);
		}

		/** 参数校验 */
		if(null==picFile){
			return new ResponseEnvelope(false,"上传文件picFile不能为空",msgId);
		}
		if(StringUtils.isBlank(record.getSpellingFlower())){
			return new ResponseEnvelope(false,"拼花信息不能为空",msgId);
		}

		/** 设值拼花已使用信息 */
		try {
			record.setPlanId(Integer.parseInt(record.getDesginPlanId()));
			this.sysSave(record,request);
		}catch (Exception e){
			return new ResponseEnvelope(false,"参数有误",msgId);
		}


		/** 调用service保存信息 并 返回 */
		return desginPlanSpellingflowerRecordService.add(record,loginUser,picFile);
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(DesginPlanSpellingflowerRecord model,HttpServletRequest request){
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
