package com.sandu.web.act2;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.sandu.api.act2.model.RedPacketRegistration;
import com.sandu.api.act2.output.InviteResultVO;
import com.sandu.api.act2.output.ReceiveRedPacketResultVO;
import com.sandu.api.act2.output.RedPacketJoinVO;
import com.sandu.api.act2.service.RedPacketRegistrationService;
import com.sandu.api.common.exception.SystemException;
import com.sandu.api.company.model.BaseCompanyMiniProgramTemplateMsg;
import com.sandu.api.llt.trade.model.LltRechargeLog;
import com.sandu.api.llt.trade.service.LltRechargeLogService;
import com.sandu.api.llt.trade.service.LltTradeService;
import com.sandu.api.user.model.ResponseBo;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.LoginContext;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.gateway.pay.common.gson.GsonUtil;
import com.sandu.util.UUIDUtil;
import com.sandu.web.async.AsyncCallTemplateMsgService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/act2/redpacket/reg")
@Slf4j
public class RedPacketRegistrationController {
	
    @Autowired
    private RedPacketRegistrationService redPacketRegistrationService;
    
    @Autowired
    private LltRechargeLogService lltRechargeLogService;
    
    @Autowired
    private LltTradeService lltTradeService;
    
    @Resource
    private SysUserService userService;
    
    @Autowired
    private AsyncCallTemplateMsgService asyncCallService;
    
    @ApiOperation(value = "加入红包活动", response = ResponseEnvelope.class)
    @PostMapping("/joinRedPacketAct")
    public ResponseEnvelope joinRedPacketAct(String actId) {
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
			RedPacketJoinVO retVo = redPacketRegistrationService.join(actId,user);
			/*if(retVo.isComplete()) {
				String cutFriends = wxActBargainInviteRecordService.getCutFriends(retVo.getRegId());
				logger.info("砍价任务完成(自己砍价):openId:{},productName:{},cutFriends:{}",user.getOpenId(), retVo.getProductName(), cutFriends);
				this.sendActBarginRegCompleteTemplateMsg(user, retVo.getProductName(), cutFriends,actId,retVo.getRegId());
			}*/
			return new ResponseEnvelope<>(true,(Object)retVo.getRegId());
    	}catch(SystemException ex) {
    		log.warn("业务异常:"+ex.getMessage());
    		return new ResponseEnvelope<>(false, ex.getMessage());
    	}catch(Exception ex) {
    		log.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }
    
    @ApiOperation(value = "好友通过分享链接进入", response = ResponseEnvelope.class)
    @PostMapping("/recordInvitation")
    public ResponseEnvelope recordInvitation(String regId) {
    	try {
			if(StringUtils.isBlank(regId)) {
				return new ResponseEnvelope<>(false, "任务id不能为空!"); 
			}
			//返回砍价金额
			LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
			SysUser user = userService.get(loginUser.getId());
			if(user==null) {
				return new ResponseEnvelope<>(false, "用户不存在!"); 
			}
			InviteResultVO resultVO = redPacketRegistrationService.recordInvitation(regId,user);
			//发送领取红包模板消息
			if(resultVO!=null) {
				log.info("开始发送领取红包模板消息:{}",regId);
				//获取任务创建人
				RedPacketRegistration regEntity = redPacketRegistrationService.get(regId);
				//找到相应的小程序用户
				SysUser miniUser = userService.getMiniUser(regEntity.getOpenId());
				String msg = "您目前已成功邀请"+resultVO.getCurrentInviteNum()+"人，请尽快领取话费奖励";
				Map tempalteData = this.buildReceiveRedPacketTempalteData(resultVO.getAvailableAmount(),msg);
				asyncCallService.sendTemplateMsg(miniUser, BaseCompanyMiniProgramTemplateMsg.TEMPLATE_TYPE_ACT_RED_PACKET_RECEIVE,tempalteData, resultVO.getActId());
			}
			return new ResponseEnvelope<>(true, "邀请成功");
    	}catch(SystemException ex) {
    		log.warn("业务异常:"+ex.getMessage());
    		return new ResponseEnvelope<>(false, ex.getMessage());
    	}catch(Exception ex) {
    		log.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }
    
    /**
	 * 构建模板数据,替换模板里面的配置参数
	 * @return
	 */
	private Map buildReceiveRedPacketTempalteData(String availableAmount,String msg) {
		Map<String, Map> keywordsMap = new LinkedHashMap<String, Map>();
		Map<String, String> keywordTempMap = null;
		
		keywordTempMap = new HashMap<String, String>();
		keywordTempMap.put("value", availableAmount);
		keywordsMap.put("keyword1", keywordTempMap);
		
		keywordTempMap = new HashMap<String, String>();
		keywordTempMap.put("value", msg);
		keywordsMap.put("keyword2", keywordTempMap);
		
		return keywordsMap;
	}
    
    @ApiOperation(value = "是否已绑定充值手机", response = ResponseEnvelope.class)
    @GetMapping("/isRechargeMobileBind")
    public ResponseEnvelope isRechargeMobileBind(String regId) {
    	try {
			if(StringUtils.isBlank(regId)) {
				return new ResponseEnvelope<>(false, "任务id不能为空!"); 
			}
			//返回砍价金额
			LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
			SysUser user = userService.get(loginUser.getId());
			if(user==null) {
				return new ResponseEnvelope<>(false, "用户不存在!"); 
			}
			boolean isBind = redPacketRegistrationService.isRechargeMobileBind(regId,user);
			return new ResponseEnvelope<>(true, isBind);
    	}catch(SystemException ex) {
    		log.warn("业务异常:"+ex.getMessage());
    		return new ResponseEnvelope<>(false, ex.getMessage());
    	}catch(Exception ex) {
    		log.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }
    
    @ApiOperation(value = "绑定充值手机", response = ResponseEnvelope.class)
    @PostMapping("/bindRechargeMobile")
    public ResponseEnvelope bindRechargeMobile(String regId,String mobile,String validationCode) {
    	try {
			if (StringUtils.isBlank(mobile)) {
				return new ResponseEnvelope<>(false, "手机号不能为空!");
			}
			if (StringUtils.isBlank(validationCode)) {
				return new ResponseEnvelope<>(false, "验证码不能为空!");
			}
			if (!this.isMobile(mobile)) {
				return new ResponseEnvelope<>(false, "手机号格式有误!");
			}
			LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
			SysUser user = userService.get(loginUser.getId());
			if(user==null) {
				return new ResponseEnvelope<>(false, "用户不存在!");
			}
			ResponseBo flag = userService.checkPhone(mobile, validationCode);
			if (flag.getStatus()) {
	        	redPacketRegistrationService.bindRechargeMobile(regId,mobile,user);
			}else {
				return new ResponseEnvelope<>(false, flag.getMsg());
			}
			return new ResponseEnvelope<>(true, "绑定成功");
			
    	}catch(SystemException ex) {
    		log.warn("业务异常:"+ex.getMessage());
    		return new ResponseEnvelope<>(false, ex.getMessage());
    	}catch(Exception ex) {
    		log.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }
    
    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    private boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^((17[0-9])|(2[0-9][0-9])|(13[0-9])|(15[012356789])|(18[0-9])|(14[57])|(16[0-9])|(19[0-9]))[0-9]{8}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
    
    @ApiOperation(value = "领取红包", response = ResponseEnvelope.class)
    @PostMapping("/receiveRedPacket")
    public ResponseEnvelope receiveRedPacket(String regId,Integer redPacketSeq) {
    	try {
			if(StringUtils.isBlank(regId)) {
				return new ResponseEnvelope<>(false, "任务id不能为空!"); 
			}
			if(redPacketSeq == null) {
				return new ResponseEnvelope<>(false, "红包索引不能为空."); 
			}
			//返回砍价金额
			LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
			SysUser user = userService.get(loginUser.getId());
			if(user==null) {
				return new ResponseEnvelope<>(false, "用户不存在!"); 
			}
			ReceiveRedPacketResultVO vo = redPacketRegistrationService.receiveRedPacket(regId,redPacketSeq,user);
			//调用外部接口充话费
			if(Double.valueOf(vo.getPacketAmount())>0) {
				doRecharge(vo.getRechargeMobile(),Double.valueOf(vo.getPacketAmount()),vo.getAwardId());
			}
			return new ResponseEnvelope<>(true, vo);
    	}catch(SystemException ex) {
    		log.warn("业务异常:"+ex.getMessage());
    		if("ACT_END".equals(ex.getErrorCode())) {
    			ResponseEnvelope rsp = new ResponseEnvelope<>(false, ex.getMessage());
    			rsp.setExceptionCode(403);
    			return rsp;
    		}
    		return new ResponseEnvelope<>(false, ex.getMessage());
    	}catch(Exception ex) {
    		log.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }
   
   /* @PostMapping("/test")
    public void test(String mobile,Double packetAmount,String awardId) {
    	doRecharge(mobile,packetAmount,awardId);
    }*/

	private void doRecharge(String mobile,Double packetAmount,String awardId) {
		//如果已经充值则,不能重复调用
		if(!isRecharge(awardId)) {
			//记录请求流水
			String rechargeLogId = saveRechargeLog(mobile,packetAmount,awardId);
			//调用充值接口
			String rspMsg = lltTradeService.pushOrder(mobile, packetAmount.intValue());
			//更新响应信息
			modifyResponseInfo(rechargeLogId,rspMsg);
		}
		
	}
	
	private void modifyResponseInfo(String rechargeLogId,String rspMsg) {
		Map<String,String> rspMap = GsonUtil.fromJson(rspMsg, Map.class);
		LltRechargeLog entity = new LltRechargeLog();
		entity.setId(rechargeLogId);
		entity.setRetCode(rspMap.get("code"));
		entity.setRetMsg(rspMap.get("msg"));
		entity.setRetOrderId(rspMap.get("orderId"));
		entity.setResponse(rspMsg);
		lltRechargeLogService.modifyById(entity);
	}

	private String saveRechargeLog(String mobile, Double packetAmount, String awardId) {
		Date now = new Date();
		LltRechargeLog entity = new LltRechargeLog();
		entity.setId(UUIDUtil.getUUID());
		entity.setBizId(awardId);
		entity.setBizType(1);
		entity.setFluxpackage(lltTradeService.getFluxpackage(packetAmount.intValue()));
		entity.setMobile(mobile);
		entity.setRechargeAmount(packetAmount);
		entity.setRechargeTime(now);
		//entity.setRetCode();
		//entity.setRetMsg(retMsg);
		//entity.setRetOrderId(retOrderId);
		//entity.setPushTime(pushTime);
		//entity.setPushOrderStatus(pushOrderStatus);
		//entity.setPushOrderDesc(pushOrderDesc);
		entity.setRequest(lltTradeService.getJsonReqParam(mobile,packetAmount.intValue()));
		//entity.setResponse(response);
		entity.setGmtCreate(now);
		entity.setIsDeleted(0);
		lltRechargeLogService.create(entity);
		return entity.getId();
	}

	private boolean isRecharge(String awardId) {
		LltRechargeLog entity = new LltRechargeLog();
		entity.setBizId(awardId);
		entity.setBizType(1);
		List<LltRechargeLog> list = lltRechargeLogService.getList(entity);
		if(list!=null && list.size()>0) {
			return true;
		}
		return false;
	}
    

   

}
