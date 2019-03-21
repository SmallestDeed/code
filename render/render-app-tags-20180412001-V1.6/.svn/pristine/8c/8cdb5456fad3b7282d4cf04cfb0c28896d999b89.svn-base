package com.nork.design.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.properties.ResProperties;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.product.model.BaseBrand;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.ResRenderVideo;
import com.nork.system.service.ResRenderPicService;
import com.nork.system.service.ResRenderVideoService;
import com.sandu.common.LoginContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.nork.common.util.Utils;


/**
 * 设计方案推荐
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/{style}/web/design/designPlanRecommendedV2")
public class WebDesignPlanRecommendedController {
	private final static Logger logger = LogManager.getLogger(WebDesignPlanRecommendedController.class);
	@Autowired
	DesignPlanRecommendedServiceV2  designPlanRecommendedServiceV2;
	
	
 
	/**
	 * 详情查看
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/detailsSee")
	@ResponseBody
	public ResponseEnvelope<ResRenderPic> detailsSee(HttpServletRequest request,
			HttpServletResponse response){
		String detailsSeeType = request.getParameter("detailsSeeType");
		String picId = request.getParameter("smallPicId");
		String msgId = request.getParameter("msgId");
		boolean valid = this.validPlanReleaseParam(msgId,picId);
		if(!valid) {
			return new ResponseEnvelope<ResRenderPic>(false,"参数不完整",msgId);
		}
		if(StringUtils.isEmpty(detailsSeeType)){
			detailsSeeType = "";
		}
//		LoginUser loginUser = SystemCommonUtil.getLoginUserInfoByAuthData(request);
		Map map =LoginContext.getTokenData();
		if (null == map) {
			return new ResponseEnvelope<>(false,"请重新登录！");
		}
		LoginUser loginUser= new LoginUser();
		String appKey = map.get("appKey").toString();
		String mediaType = request.getHeader("MediaType");
		Long uTypeValue = (Long)map.get("utype");
		String loginName = (String) map.get("uname");
		int uType = uTypeValue.intValue();
		long id =(long) map.get("uid");
		loginUser.setId(new Long(id).intValue());
		loginUser.setLoginName(loginName);
		loginUser.setUserType(Integer.valueOf(uType));
		loginUser.setAppKey(appKey);
		loginUser.setMediaType(mediaType == null ? SystemCommonConstant.MEDIA_TYPE_PC : mediaType);
		String apprenderUrl= Utils.getPropertyName("app","app.render.server.url","");
		logger.error("===================定位服务获取配置文件地址apprenderUrl=="+apprenderUrl);
		return designPlanRecommendedServiceV2.detailsSee(msgId,picId,detailsSeeType,loginUser);
	}

	/**
	 * 参数完整性判断
	 * @param args
	 * @return
	 */
	private boolean validPlanReleaseParam(String... args) {
		boolean result = true;
		for(String arg :args) {
			if(StringUtils.isEmpty(arg)) {
				result = false;
				return result;
			}
		}
		return result;
	}
	
	
}
