package com.sandu.web.act;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import com.sandu.api.act.input.WxActBargainRegistrationQuery;
import com.sandu.api.act.model.WxActBargainRegistration;
import com.sandu.api.act.output.RegistrationStatusVO;
import com.sandu.api.act.output.WxActBargainRegCutResultVO;
import com.sandu.api.act.output.WxActBargainRegDashBoardResultVO;
import com.sandu.api.act.output.WxActBargainRegShipmentInfoVO;
import com.sandu.api.act.output.WxActBargainRegistrationAnalyseResultVO;
import com.sandu.api.act.service.WxActBargainInviteRecordService;
import com.sandu.api.act.service.WxActBargainRegistrationService;
import com.sandu.api.common.exception.SystemException;
import com.sandu.api.company.model.BaseCompanyMiniProgramTemplateMsg;
import com.sandu.api.notify.wx.TemplateMsgService;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.LoginContext;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.commons.gson.GsonUtil;
import com.sandu.web.async.AsyncCallTemplateMsgService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "ActBargain", description = "砍价活动")
@RestController
@RequestMapping("/v1/act/bargain/reg")
public class WxActBargainRegistrationController {
	
	private Logger logger = LoggerFactory.getLogger(WxActBargainRegistrationController.class);
	
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private WxActBargainRegistrationService wxActBargainRegistrationService;
    
    @Autowired
    private WxActBargainInviteRecordService wxActBargainInviteRecordService;

    @Autowired
    private AsyncCallTemplateMsgService asyncCallService;
    
    @Resource
    private SysUserService userService;
    
    
    @ApiOperation(value = "获取报名参加的活动状态", response = ResponseEnvelope.class)
    @GetMapping("/getRegStatus")
    public ResponseEnvelope getWxActBargainRegistrationStatus(String actId) {
    	
		try {
			if(StringUtils.isBlank(actId)) {
				return new ResponseEnvelope<>(false, "活动id不能为空!"); 
			}
			LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
			SysUser user = userService.get(loginUser.getId());
			if(user==null) {
				return new ResponseEnvelope<>(false, "用户不存在!"); 
			}
			RegistrationStatusVO retVo = wxActBargainRegistrationService.getWxActBargainRegistrationStatus(actId, user);
			return new ResponseEnvelope<>(true, retVo);
			
		}catch(SystemException ex) {
    		logger.warn("业务异常:"+ex.getMessage());
    		if("ACT_NOT_EXIST".equals(ex.getErrorCode())) {
    			ResponseEnvelope rsp = new ResponseEnvelope<>(false, ex.getMessage());
    			rsp.setExceptionCode(404);
    			return rsp;
    		}
    		return new ResponseEnvelope<>(false, ex.getMessage());
    	}catch(Exception ex) {
			logger.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }
    
    
    
    @ApiOperation(value = "砍价接口(自己砍价)", response = ResponseEnvelope.class)
    @PostMapping("/cutPriceByMyself")
    public ResponseEnvelope cutPriceByMyself(String actId,String formId, String forwardPage) {
    	try {
			if(StringUtils.isBlank(actId)) {
				return new ResponseEnvelope<>(false, "活动id不能为空!"); 
			}
			//返回砍价金额
			LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
			SysUser user = userService.get(loginUser.getId());
			if(user==null) {
				return new ResponseEnvelope<>(false, "用户不存在!"); 
			}
			WxActBargainRegCutResultVO retVo = wxActBargainRegistrationService.cutPriceByMyself(actId,user);
			if(retVo.isComplete()) {
				String cutFriends = wxActBargainInviteRecordService.getCutFriends(retVo.getRegId());
				logger.info("砍价任务完成(自己砍价):openId:{},productName:{},cutFriends:{}",user.getOpenId(), retVo.getProductName(), cutFriends);
				this.sendActBarginRegCompleteTemplateMsg(user, retVo.getProductName(), cutFriends,actId,retVo.getRegId());
			}
			return new ResponseEnvelope<>(true, retVo.getCutPrice());
    	}catch(SystemException ex) {
    		logger.warn("业务异常:"+ex.getMessage());
    		return new ResponseEnvelope<>(false, ex.getMessage());
    	}catch(Exception ex) {
			logger.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }
    
    private void sendActBarginRegCompleteTemplateMsg(SysUser user, String productName,String cutFriends,Object...pageParams) {
    	//获取模析消息数据
		Map tempalteData = this.buildActBarginRegCompleteTempalteData(productName,cutFriends);
		asyncCallService.sendTemplateMsg(user, BaseCompanyMiniProgramTemplateMsg.TEMPLATE_TYPE_ACT_BARGAIN_REG_COMPLETE,tempalteData, pageParams);
	}

	/**
	 * 获取模板数据,替换模板里面的配置参数
	 * @return
	 */
	private Map buildActBarginRegCompleteTempalteData(String productName,String cutFriends) {
		Map<String, Map> keywordsMap = new LinkedHashMap<String, Map>();
		Map<String, String> keywordTempMap = null;
		
		keywordTempMap = new HashMap<String, String>();
		keywordTempMap.put("value", productName);
		keywordsMap.put("keyword1", keywordTempMap);
		
		keywordTempMap = new HashMap<String, String>();
		keywordTempMap.put("value", cutFriends);
		keywordsMap.put("keyword2", keywordTempMap);
		
		return keywordsMap;
	}
    
    
    @ApiOperation(value = "砍价接口(装进我家)", response = ResponseEnvelope.class)
    @PostMapping("/cutPriceByDecorate")
    public ResponseEnvelope cutPriceByDecorate(String actId,Long houseId,String houseName,String formId, String forwardPage) {
		try {
			if(StringUtils.isBlank(actId)) {
				return new ResponseEnvelope<>(false, "活动id不能为空!"); 
			}
			//返回砍价金额
			LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
			SysUser user = userService.get(loginUser.getId());
			if(user==null) {
				return new ResponseEnvelope<>(false, "用户不存在!"); 
			}
			WxActBargainRegCutResultVO retVo = wxActBargainRegistrationService.cutPriceByDecorate(actId,user,houseId,houseName);
			if(retVo.isComplete()) {
				String cutFriends = wxActBargainInviteRecordService.getCutFriends(retVo.getRegId());
				logger.info("砍价任务完成(装进我家):openId:{},productName:{},cutFriends:{}",user.getOpenId(), retVo.getProductName(), cutFriends);
				this.sendActBarginRegCompleteTemplateMsg(user, retVo.getProductName(), cutFriends,actId,retVo.getRegId());
			}
			return new ResponseEnvelope<>(true, retVo.getCutPrice());
    	}catch(Exception ex) {
			logger.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }
    
    
    @ApiOperation(value = "是否已帮好友砍价", response = ResponseEnvelope.class)
    @GetMapping("/getInviteCutStatus")
    public ResponseEnvelope getWxActBargainInviteRecordCutStatus(String actId,String regId) {
    	try {
			if(StringUtils.isBlank(actId)) {
				return new ResponseEnvelope<>(false, "活动id不能为空!"); 
			}
			if(StringUtils.isBlank(regId)) {
				return new ResponseEnvelope<>(false, "任务id不能为空!"); 
			}
			//返回砍价金额
			LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
			SysUser user = userService.get(loginUser.getId());
			if(user==null) {
				return new ResponseEnvelope<>(false, "用户不存在!"); 
			}
			String statusCode = wxActBargainRegistrationService.getWxActBargainInviteRecordCutStatus(actId,user.getOpenId(),regId);
			return new ResponseEnvelope<>(true, (Object)statusCode);
    	}catch(SystemException ex) {
    		logger.warn("业务异常:"+ex.getMessage());
    		if("ACT_NOT_EXIST".equals(ex.getErrorCode())) {
    			ResponseEnvelope rsp = new ResponseEnvelope<>(false, ex.getMessage());
    			rsp.setExceptionCode(404);
    			return rsp;
    		}
    		return new ResponseEnvelope<>(false, ex.getMessage());
    	}catch(Exception ex) {
			logger.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }
    
    @ApiOperation(value = "砍价接口(帮好友砍价)", response = ResponseEnvelope.class)
    @PostMapping("/cutPriceByInvite")
    public ResponseEnvelope cutPriceByInvite(String actId,String regId) {
    	try {
    		if(StringUtils.isBlank(actId)) {
				return new ResponseEnvelope<>(false, "活动id不能为空!"); 
			}
			if(StringUtils.isBlank(regId)) {
				return new ResponseEnvelope<>(false, "任务id不能为空!"); 
			}
			//返回砍价金额
			LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
			SysUser user = userService.get(loginUser.getId());
			if(user==null) {
				return new ResponseEnvelope<>(false, "用户不存在!"); 
			}
			WxActBargainRegCutResultVO retVo = wxActBargainRegistrationService.cutPriceByInvite(actId,regId,user);
			if(retVo.isComplete()) {
				String cutFriends = wxActBargainInviteRecordService.getCutFriends(retVo.getRegId());
//				SysUser regOwner = userService.getMiniUser(retVo.getRegOwnerOpenId());
//				logger.info("砍价任务完成(好友):OnwerOpenId:{},productName:{},cutFriends:{}",regOwner.getOpenId(), retVo.getProductName(), cutFriends);
//				this.sendActBarginRegCompleteTemplateMsg(regOwner, retVo.getProductName(), cutFriends,actId,retVo.getRegId());
			}
			return new ResponseEnvelope<>(true, retVo.getCutPrice());
    	}catch(SystemException ex) {
    		logger.warn("业务异常:"+ex.getMessage());
    		if("REG_COMPLETE".equals(ex.getErrorCode())) {
    			ResponseEnvelope rsp = new ResponseEnvelope<>(false, ex.getMessage());
    			rsp.setExceptionCode(200);
    			return rsp;
    		}
    		return new ResponseEnvelope<>(false, ex.getMessage());
    	}catch(Exception ex) {
			logger.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }
    
    
    @ApiOperation(value = "活动报名统计列表", response = ResponseEnvelope.class)
    @GetMapping("/getRegAnalyseResultList")
    public ResponseEnvelope getWxActBargainRegResultList(WxActBargainRegistrationQuery query) {
    	try {
			PageInfo<WxActBargainRegistrationAnalyseResultVO> pageList = wxActBargainRegistrationService.getWxActBargainRegAnalyseResultList(query);
			return new ResponseEnvelope<>(true, pageList.getTotal(),pageList.getList());
    	}catch(Exception ex) {
			logger.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }
    
    
    @ApiOperation(value = "获取物流信息", response = ResponseEnvelope.class)
    @GetMapping("/getShipmentInfo")
    public ResponseEnvelope getShipmentInfo(String regId) {
    	try {
			if(StringUtils.isBlank(regId)) {
				return new ResponseEnvelope<>(false, "任务id不能为空!"); 
			}
			WxActBargainRegShipmentInfoVO retVo = wxActBargainRegistrationService.getShipmentInfo(regId);
			return new ResponseEnvelope<>(true, retVo);
    	}catch(Exception ex) {
			logger.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }
    
    
    @ApiOperation(value = "编辑运单号", response = ResponseEnvelope.class)
    @PostMapping("/modifyShipmentNo")
    public ResponseEnvelope modifyShipmentNo(String regId,String carrier, String shipmentNo) {
    	try {
    		
			if(StringUtils.isBlank(regId)) {
				return new ResponseEnvelope<>(false, "任务id不能为空!"); 
			}
			if(StringUtils.isBlank(carrier)) {
				return new ResponseEnvelope<>(false, "快递公司不能为空!"); 
			}
			
			if(StringUtils.isBlank(shipmentNo)) {
				return new ResponseEnvelope<>(false, "运单号不能为空!"); 
			}
			//返回砍价金额
			LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
			SysUser user = userService.get(loginUser.getId());
			if(user==null) {
				return new ResponseEnvelope<>(false, "用户不存在!"); 
			}
			WxActBargainRegistration regEntity = wxActBargainRegistrationService.getWxActBargainRegistration(regId);
			if(regEntity==null) {
				return new ResponseEnvelope<>(false, "任务不存在!");
			}
			if(regEntity.getCompleteStatus()!=WxActBargainRegistration.COMPLETE_STATUS_FINISH) {
				return new ResponseEnvelope<>(false, "任务未完成!");
			}
			
			if(regEntity.getExceptionStatus()==WxActBargainRegistration.EXCEPTION_STATUS_NO_STOCK) {
				return new ResponseEnvelope<>(false,"无库存!");
			}
			
			wxActBargainRegistrationService.modifyShipmentNo(regId,carrier,shipmentNo,user);
			return new ResponseEnvelope<>(true, "保存成功");
    	}catch(Exception ex) {
			logger.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }
    
    
    @ApiOperation(value = "图形统计数据列表", response = ResponseEnvelope.class)
    @GetMapping("/getRegDashBoardResultList")
    public ResponseEnvelope getWxActBargainRegDashBoardResultList(String actId,String beginTime,String endTime) {
    	try {
    		if(StringUtils.isBlank(actId)) {
				return new ResponseEnvelope<>(false, "活动id不能为空!"); 
			}
    		if(StringUtils.isBlank(beginTime)) {
    			return new ResponseEnvelope<>(false, "开始时间不能为空!"); 
    		}
    		
    		if(StringUtils.isBlank(endTime)) {
    			return new ResponseEnvelope<>(false, "结束时间不能为空!"); 
    		}
    		Date beginTime2 = null;
    		Date endTime2 = null;
    		try {
    			beginTime2 = dateFormat.parse(beginTime);
    		}catch(Exception ex) {
    			return new ResponseEnvelope<>(false, "开始时间格式不正确!"); 
    		}
    		try {
    			endTime2 = dateFormat.parse(endTime);
    		}catch(Exception ex) {
    			return new ResponseEnvelope<>(false, "结束时间格式不正确!"); 
    		}
    		if(beginTime2.compareTo(endTime2)>0) {
    			return new ResponseEnvelope<>(false, "开始时间不能小于结束时间!"); 
    		}
    		
    		WxActBargainRegDashBoardResultVO resultVo = wxActBargainRegistrationService.getWxActBargainRegDashBoardResultList(actId,beginTime2,endTime2);
			return new ResponseEnvelope<>(true, resultVo);
    	}catch(Exception ex) {
			logger.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }
    
  
    

}
