package com.nork.design.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.design.service.DesignPlanRenderSceneService;
import com.nork.product.model.BaseBrand;
import com.nork.system.service.ResRenderPicService;

/**
 * 设计方案副本controller
 * @author huangsongbo
 *
 */
@Controller
@RequestMapping("/{style}/web/design/designPlanRenderScene")
public class WebDesignPlanRenderSceneController {

	private Logger logger = LoggerFactory.getLogger(WebDesignPlanRenderSceneController.class);
	
	@Autowired
	private DesignPlanRenderSceneService designPlanRenderSceneService;
	
	@Autowired
	private ResRenderPicService resRenderPicService;
	
	/**
	 * 通过副本id删除副本数据及关联的渲染图(逻辑删除,is_deleted = 1)
	 * @author huangsongbo
	 * @param planId
	 * @param msgId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public Object deleteById(
			Integer planId,
			String msgId,
			HttpServletRequest request,
			HttpServletResponse response
			){
		
		if(planId == null){
			return new ResponseEnvelope<>(false, "参数planId不能为空", msgId);
		}
		
		DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneService.get(planId);
		
		if(designPlanRenderScene == null){
			logger.error("------副本数据不存在,id:" + planId);
			return new ResponseEnvelope<>(false, "找到对应副本", msgId);
		}
		
		// 检测该副本是不是该登录用户的副本,如果不是,不允许删除
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		
		if(loginUser == null || loginUser.getId() == null || loginUser.getId() < 1){
			return new ResponseEnvelope<>(false, "未检测到登录信息,请重新登录", msgId);
		}
		
		if(!loginUser.getId().equals(designPlanRenderScene.getUserId())){
			return new ResponseEnvelope<>(false, "删除失败,不能删除别人的设计方案", msgId);
		}
		
		// 删除副本及关联数据(物理删除)
		designPlanRenderSceneService.deleteAllDataById(planId);
		
		// 删除副本对应的渲染图(逻辑删除)
		resRenderPicService.deletePicBySceneId(planId);
		
		return new ResponseEnvelope<>(true, "删除成功", msgId);
	}
	
	
	/**
	 * 通过厂商看到 经销商下 所有用到 该厂商品牌的设计方案
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping( value="/vendorPlanList")
    @ResponseBody
	public ResponseEnvelope<DesignPlanRenderScene> vendorPlanList(HttpServletRequest request,HttpServletResponse response){
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		String msgId = request.getParameter("msgId");
		String brandId = request.getParameter("brandId");
		String planName = request.getParameter("planName");
		String limit = request.getParameter("limit");
		String start = request.getParameter("start");
		if(StringUtils.isEmpty(msgId)){
			return new ResponseEnvelope<DesignPlanRenderScene>(false," parame not found!",msgId);
		}
        if (loginUser==null) {
            return new ResponseEnvelope<DesignPlanRenderScene>(false,SystemCommonConstant.PLEASE_LOGIN,msgId);
        }
        return designPlanRenderSceneService.vendorPlanList(msgId,brandId,planName,loginUser,limit,start);
	}
	
	
	
	/**
	 * 得到该厂商的所有品牌
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping( value="/manufacturerBrandList")
    @ResponseBody
	public ResponseEnvelope<BaseBrand>  manufacturerBrandList(HttpServletRequest request,HttpServletResponse response){
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		String msgId = request.getParameter("msgId");
		if(StringUtils.isEmpty(msgId)){
			return new ResponseEnvelope<BaseBrand>(false," parame not found!",msgId);
		}
        if (loginUser.getId() < 0) {
            return new ResponseEnvelope<BaseBrand>(false,SystemCommonConstant.PLEASE_LOGIN,msgId);
        }
        return designPlanRenderSceneService.manufacturerBrandList(msgId,loginUser);
	}
	
}
