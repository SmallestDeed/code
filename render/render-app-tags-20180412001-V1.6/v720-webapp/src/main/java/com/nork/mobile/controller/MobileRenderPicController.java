package com.nork.mobile.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.designplan.model.DesignPlan;
import com.nork.mobile.model.search.MobileRenderRecord;
import com.nork.mobile.service.MobileResRenderPicService;
import com.nork.system.model.ResRenderPic;
import com.sandu.common.LoginContext;

/**
 * 移动端获取渲染图的controller
 *
 */
@Controller
@RequestMapping("/{style}/mobile/renderPic")
public class MobileRenderPicController {

	private static Logger logger = Logger
			.getLogger(MobileRenderPicController.class);
	
	@Autowired
	private MobileResRenderPicService mobileResRenderPicService;
	



	/**
	 * 从我的设计里获取一个设计方案的照片集
	 */
	@RequestMapping(value = "/getPictureList")
	@ResponseBody
	public Object getPictureList(@RequestBody DesignPlan designPlan) {

		String remark = designPlan.getRemark();

		if (designPlan.getId() == null) {
			return new ResponseEnvelope<ResRenderPic>(false, "请求参数为空");
		}
		return mobileResRenderPicService.getPictureList(designPlan.getId(),remark);
	}

	// 移动端获取二维码
	@SuppressWarnings("unused")
	@RequestMapping("/getQRCodePicUrl")
	@ResponseBody
	public Object getQRCodePicUrl(@RequestBody MobileRenderRecord model, HttpServletRequest request) {
		Integer picId = model.getPicId();
		String renderingType = model.getRenderingType();
		String msgId = model.getMsgId();
		String remark = model.getRemark();
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
//		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
//		LoginUser loginUser = SystemCommonUtil.getLoginUserInfoByAuthData(request);
		/* 参数验证 */
		if (picId == null || picId < 1) {
			return new ResponseEnvelope<>(false, "参数picId不能为空并且不能小于1", msgId);
		}
		/* 参数验证->end */

		return mobileResRenderPicService.getQRCodePicUrl(picId, msgId, loginUser,remark);
	}

	
}
