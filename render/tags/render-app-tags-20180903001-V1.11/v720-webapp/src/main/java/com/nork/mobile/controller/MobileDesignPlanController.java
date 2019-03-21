package com.nork.mobile.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.ResRenderVideo;
import com.nork.system.service.ResRenderPicService;
import com.nork.system.service.ResRenderVideoService;
import com.nork.mobile.model.ProductGroupReplaceTaskDetail;
import com.nork.mobile.model.ProductReplaceTaskDetail;
import com.nork.mobile.model.search.MobileRenderingModel;
import com.nork.mobile.service.MobileDesignPlanService;
import com.nork.mobile.service.MobilePayRenderService;
import com.nork.design.model.DesignRenderRoam;;

@Controller
@RequestMapping("/{style}/mobile/design/designPlan")
public class MobileDesignPlanController {
	private static Logger logger = Logger
			.getLogger(MobileDesignPlanController.class);
	@Autowired
    private MobileDesignPlanService mobileDesignPlanService;
	@Autowired
    private MobilePayRenderService mobilePayRenderService;

	

	/**
	 * 获取720图片  ，，没有按钮的
	 * 
	 * @param designPlan
	 * @return
	 */
	@RequestMapping(value = "/getPanoPicture")
	@ResponseBody
	public Object getPanoPicture(@RequestBody MobileRenderingModel designPlan, HttpServletRequest request) {
		if (designPlan.getThumbId() == null) {
			return new ResponseEnvelope<ResRenderPic>(false, "请求参数为空");
		}
		Integer resRenderPicId = designPlan.getThumbId();
		return mobileDesignPlanService.getPanoPicture(resRenderPicId);
	}

	
	/**
	 * 逻辑删除我的设计、我的任务、我的消息
	 * @param model
	 * @return
	 */
	@RequestMapping("/deleteMyDesignPlanAndTask")
	@ResponseBody
	public Object deleteMyDesignPlanAndTask(@RequestBody MobileRenderingModel model) {
		Integer planId = model.getPlanId();
		if (planId == null || planId == 0) {
		    return new ResponseEnvelope(false, "方案id为空");
        }
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		if (loginUser == null) {
		    return new ResponseEnvelope(false, "请登录");
        }

        return mobileDesignPlanService.deleteMyDesignPlanAndTask(planId,loginUser.getId(),model.getPlanHouseType());
	}

	 /**
     * 产品替换
     *
     * @param taskId
     * @param msgId
     * @return
     */
    @RequestMapping("/productReplaceList")
    @ResponseBody
    public ResponseEnvelope<ProductReplaceTaskDetail> productReplaceList(Integer taskId, String msgId) {
        List<ProductReplaceTaskDetail> replaceProductList = mobilePayRenderService.selectListByTaskId(taskId, msgId);
        ResponseEnvelope<ProductReplaceTaskDetail> result = new ResponseEnvelope<ProductReplaceTaskDetail>(replaceProductList, msgId);
        return result;
    }

    /**
     * 产品移除
     *
     * @param taskId
     * @param msgId
     * @return
     */
    @RequestMapping("/productDelList")
    @ResponseBody
    public ResponseEnvelope<ProductReplaceTaskDetail> productDelList(Integer taskId, String msgId) {
        List<ProductReplaceTaskDetail> replaceProductList = mobilePayRenderService.selectDelListByTaskId(taskId, msgId);
        ResponseEnvelope<ProductReplaceTaskDetail> result = new ResponseEnvelope<ProductReplaceTaskDetail>(replaceProductList, msgId);
        return result;
    }

    /**
     * 组合替换
     *
     * @param taskId
     * @param msgId
     * @return
     */
    @RequestMapping("/groupProductReplaceList")
    @ResponseBody
    public ResponseEnvelope<ProductGroupReplaceTaskDetail> groupProductReplaceList(Integer taskId, String msgId) {
        List<ProductGroupReplaceTaskDetail> groupReplaceList = mobilePayRenderService.selectGroupReplaceListByTaskId(taskId, msgId);
        ResponseEnvelope<ProductGroupReplaceTaskDetail> result = new ResponseEnvelope<ProductGroupReplaceTaskDetail>(groupReplaceList, msgId);
        return result;
    }

    /**
     * 材质替换
     *
     * @param taskId
     * @param msgId
     * @return
     */
    @RequestMapping("/textureReplaceList")
    @ResponseBody
    public ResponseEnvelope<ProductReplaceTaskDetail> textureReplaceList(Integer taskId, String msgId) {
        List<ProductReplaceTaskDetail> selectTextureReplaceList = mobilePayRenderService.selectTextureReplaceListByTaskId(taskId, msgId);
        ResponseEnvelope<ProductReplaceTaskDetail> result = new ResponseEnvelope<ProductReplaceTaskDetail>(selectTextureReplaceList, msgId);
        return result;
    }
}
