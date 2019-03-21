package com.nork.render.controller;

import com.google.gson.Gson;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.collections.Lists;
import com.nork.design.model.AutoRenderTask;
import com.nork.design.service.DesignPlanAutoRenderService;
import com.nork.render.model.ProductType;
import com.nork.render.model.RenderTypeCode;
import com.sandu.common.LoginContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 *
 * @createTime 2017年10月21日11:42:05
 */
@Controller
@RequestMapping("/{style}/autorender")
public class AutoRenderTaskController {

    private final static Gson GSON = new Gson();
    private final static String CLASS_LOG_PREFIX = "[自动渲染的任务服务]:";
    private final static Logger logger = LogManager.getLogger(AutoRenderTaskController.class);

    @Autowired
    private DesignPlanAutoRenderService designPlanAutoRenderService;

   

    /**
     * 检查当前方案是否被渲染过,渲染过和渲染中的方案不能再次渲染
     *
     * @date 20171107
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
    @RequestMapping(value = "/checkdesignplanisrender")
    public ResponseEnvelope checkDesignPlanIsRender(@ModelAttribute AutoRenderTask autoRenderTask, HttpServletRequest request) {
        if (null == autoRenderTask.getPlanId() || null == autoRenderTask.getTemplateId()) {
            return new ResponseEnvelope(false, "方案ID或样板房ID为空!");
        }
        //获取登录用户信息
    	//LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
        Map userMap =LoginContext.getTokenData();
		if (null == userMap) {
			return new ResponseEnvelope<>(false,"请重新登录！");
		}
		LoginUser loginUser= new LoginUser();
		String appKey = userMap.get("appKey").toString();
		String mediaType = request.getHeader("MediaType");
		Long uTypeValue = (Long)userMap.get("utype");
		String loginName = (String) userMap.get("uname");
		int uType = uTypeValue.intValue();
		long id =(long) userMap.get("uid");
		loginUser.setId(new Long(id).intValue());
		loginUser.setLoginName(loginName);
		loginUser.setUserType(Integer.valueOf(uType));
		loginUser.setAppKey(appKey);
		loginUser.setMediaType(mediaType == null ? SystemCommonConstant.MEDIA_TYPE_PC : mediaType);
//		LoginUser loginUser = SystemCommonUtil.getLoginUserInfoByAuthData(request);
        logger.info(CLASS_LOG_PREFIX + "获取登录用户信息:getUserFromCache:{}.", null == loginUser ? null : loginUser.toString());
        autoRenderTask.setOperationUserId(loginUser.getId());

        Map<Integer, Integer> map = designPlanAutoRenderService.getAllRenderTypesStr(autoRenderTask);

        //移动端 add by yangz 2018年1月19日15:22:45
        autoRenderTask.setIsDeleted(new Integer(0));
        autoRenderTask.setLimit(10);
        autoRenderTask.setStart(0);
        // 查询该用户的所有替换记录和方案名称的list 这里只会查出刚刚插入的任务，未开始渲染的，开始了的会被删除
        List<AutoRenderTask> list = designPlanAutoRenderService.getALLTaskByUserId(autoRenderTask);
        List<Integer> stateList = new ArrayList<>();
        stateList.add(new Integer(1));//渲染完成
        stateList.add(new Integer(2));//渲染中
        autoRenderTask.setStateList(stateList);//设置查询条件，只查询渲染成功的作为判断是否渲染过的依据
        List<AutoRenderTask> list2 = designPlanAutoRenderService.getAllTaskStateByUserId(autoRenderTask);
        // 合并两个list
        list.addAll(list2);
        if (Lists.isNotEmpty(list)) {
            for (AutoRenderTask state : list) {
                if (ProductType.PHOTO.equals(state.getRenderTypesStr())) {
                    state.setRenderingType(RenderTypeCode.COMMON_PICTURE_LEVEL);
                    state.setTaskType(state.getRenderPic());
                } else if (ProductType.ROAM720.equals(state.getRenderTypesStr())) {
                    state.setRenderingType(RenderTypeCode.COMMON_720_LEVEL);
                    state.setTaskType(state.getRender720());
                } else if (ProductType.VIDEO.equals(state.getRenderTypesStr())) {
                    state.setRenderingType(RenderTypeCode.COMMON_VIDEO);
                    state.setTaskType(state.getRenderVideo());
                } else if (ProductType.ROAMN720.equals(state.getRenderTypesStr())) {
                    state.setRenderingType(RenderTypeCode.ROAM_720_LEVEL);
                    state.setTaskType(state.getRenderN720());
                }
            }
        }
        return new ResponseEnvelope(true,"success","success",map,list.size(),list);
    }

    /**
     * 删除我的方案
     * @date 20171109
     */
    @ResponseBody
    @RequestMapping(value = "/delrenderdesignplan")
    public ResponseEnvelope delRederDesignPlan(@ModelAttribute AutoRenderTask autoRenderTask, HttpServletRequest request) {
        if (null == autoRenderTask.getId()) {
            return new ResponseEnvelope(false, "渲染ID缺失!");
        }
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
//		LoginUser loginUser = SystemCommonUtil.getLoginUserInfoByAuthData(request);
        //LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
        logger.info(CLASS_LOG_PREFIX + "获取登录用户信息:getUserFromCache:{}.", null == loginUser ? null : loginUser.toString());
        autoRenderTask.setOperationUserId(loginUser.getId());
        boolean status = designPlanAutoRenderService.delRederTaskByDesignPlanId(autoRenderTask);
        return new ResponseEnvelope(status, status ? "删除我的方案成功" : "删除我的方案失败");
    }


    /**
     * 查询用户已渲染的户型列表
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/alearyrenderhouse")
    public ResponseEnvelope delRederDesignPlan(HttpServletRequest request) {
        HttpSession session = request.getSession();
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
//        LoginUser loginUser = SystemCommonUtil.getLoginUserInfoByAuthData(request);
        logger.info(CLASS_LOG_PREFIX + "获取登录用户信息:getUserFromCache:{}.", null == loginUser ? null : loginUser.toString());
        logger.info(CLASS_LOG_PREFIX + "查询用户已渲染的户型列表--UserId:{}", loginUser.getId());
        Map<Integer, String> renderHouseMap = designPlanAutoRenderService.queryUsedHouseInMyRenderPlan(loginUser.getId());
        logger.info(CLASS_LOG_PREFIX + "查询用户已渲染的户型列表完成--renderHouseMap:{}", GSON.toJson(renderHouseMap));

        return new ResponseEnvelope(true, renderHouseMap,"");
    }



   
    
}
