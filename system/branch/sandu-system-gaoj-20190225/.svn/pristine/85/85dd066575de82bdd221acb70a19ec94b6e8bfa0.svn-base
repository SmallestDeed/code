package com.sandu.web.async;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.sandu.api.company.model.BaseCompanyMiniProgramTemplateMsg;
import com.sandu.api.notify.wx.TemplateMsgService;
import com.sandu.api.notify.wx.bo.TemplateMsgReqParam;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.web.act.WxActBargainRegistrationController;

@Component
public class AsyncCallTemplateMsgService {

	@Autowired
	private TemplateMsgService templateMsgService;
	 
	@Resource
	private SysUserService userService;
	
	private Logger logger = LoggerFactory.getLogger(AsyncCallTemplateMsgService.class);


    @Async
    public void sendTemplateMsg(SysUser miniUser,Integer msgType,Map templateData,Object... pageParams) {
    	if(miniUser==null) return;
    	TemplateMsgReqParam param = templateMsgService.buildTemplateReqParam(miniUser, msgType,templateData, pageParams);
    	logger.info("开始异步发送模板消息:{}"+param.toString());
    	templateMsgService.sendTemplateMsg(param);
    }


}
