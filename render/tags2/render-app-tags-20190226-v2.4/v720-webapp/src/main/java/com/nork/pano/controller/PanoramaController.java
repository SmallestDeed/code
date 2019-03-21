package com.nork.pano.controller;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.ProductsCostType;
import com.nork.pano.model.scene.GroupSceneVo;
import com.nork.pano.model.scene.PanoramaVo;
import com.nork.pano.model.scene.RoamSceneVo;
import com.nork.pano.model.scene.Scene;
import com.nork.pano.model.scene.SingleSceneVo;
import com.nork.pano.service.PanoramaService;

/**
 * @version V1.0
 * @Title: ExpController.java
 * @Package com.sandu.demo.controller
 * @Description:演示模块-参考例子Controller
 * @createAuthor pandajun
 * @CreateDate 2015-05-17 20:11:49
 */
@Controller
@RequestMapping("/{style}/pages/vr720")
public class PanoramaController {
    private final static Logger logger = LoggerFactory.getLogger(PanoramaController.class);
    private final static String CLASS_LOG_PREFIX = "[720场景产品清单列表服务]";

    @Autowired
    private PanoramaService panoramaService;
    
   
    /**
     * 进入单场景720页面
     * @param request
     * @param response
     * @param code
     * @return
     */
    @RequestMapping(value = "/vr720Single")
    @ResponseBody
    public Object vr720Single(HttpServletRequest request, HttpServletResponse response, String code){
        if( StringUtils.isBlank(code) ){
        	logger.info(CLASS_LOG_PREFIX + " 获取720漫游场景:vr720RoamOfMobile：{}code="+code);
            return new ResponseEnvelope<SingleSceneVo>(false, "参数错误！");
        }
        // 获取单场景信息
        SingleSceneVo singleSceneVo = panoramaService.vr720Single(code);
        logger.info(CLASS_LOG_PREFIX + "获取单场景信息:vr720Single：{}", null == singleSceneVo ? null : singleSceneVo.toString());
        if (singleSceneVo == null) {
        	return new ResponseEnvelope<SingleSceneVo>(false, "数据异常！");
		}
        return new ResponseEnvelope<SingleSceneVo>(true,singleSceneVo);
    }
    
    
    @RequestMapping(value = "/vr720MobileSingle")
    @ResponseBody
    public Object vr720MobileSingle(HttpServletRequest request, HttpServletResponse response, String code){
    	if( StringUtils.isBlank(code) ){
    		logger.info(CLASS_LOG_PREFIX + " 获取720漫游场景:vr720RoamOfMobile：{}code="+code);
            return new ResponseEnvelope<SingleSceneVo>(false, "参数错误！");
        }
    	// 获取单场景信息
        SingleSceneVo singleSceneVo = panoramaService.vr720Single(code);
        logger.info(CLASS_LOG_PREFIX + "获取单场景信息:vr720MobileSingle：{}", null == singleSceneVo ? null : singleSceneVo.toString());
        if (singleSceneVo == null) {
        	return new ResponseEnvelope<SingleSceneVo>(false, "数据异常！");
		}
        return new ResponseEnvelope<SingleSceneVo>(true,singleSceneVo);
}
    
    /**
     * 获取单场景
     * @param code
     * @return
     */
    @RequestMapping(value = "/getScene")
    @ResponseBody
    public Object getScene(String code){
    	if( StringUtils.isBlank(code) ){
    		logger.info(CLASS_LOG_PREFIX + " 获取720漫游场景:vr720RoamOfMobile：{}code="+code);
            return new ResponseEnvelope<Scene>(false, "参数错误！");
        }

        // 获取单个场景对象
        Scene scene = panoramaService.getSceneBySysCode(code, "");
        logger.info(CLASS_LOG_PREFIX + "获取单场景信息:scene：{}", null == scene ? null : scene.toString());
        if (scene == null) {
        	return new ResponseEnvelope<Scene>(false, "数据异常！");
		}
        return new ResponseEnvelope<Scene>(true,scene);
    }
    
    
    @RequestMapping(value = "/vr720MobileSingleForAuto")
    @ResponseBody
    public Object vr720MobileSingleForAuto(HttpServletRequest request, HttpServletResponse response, String code){
    	if( StringUtils.isBlank(code) ){
    		logger.info(CLASS_LOG_PREFIX + " 获取720漫游场景:vr720RoamOfMobile：{}code="+code);
            return new ResponseEnvelope<SingleSceneVo>(false, "参数错误！");
        }
        // 从自动渲染资源表里获取单场景信息
        SingleSceneVo singleSceneVo = panoramaService.vr720SingleMobileForAuto(code);
        logger.info(CLASS_LOG_PREFIX + "自动渲染资源表里获取单场景信息:SingleSceneVo：{}", null == singleSceneVo ? null : singleSceneVo.toString());
        if (singleSceneVo == null) {
        	return new ResponseEnvelope<SingleSceneVo>(false, "数据异常！");
		}
        return new ResponseEnvelope<SingleSceneVo>(true,singleSceneVo);
    }
    
    @RequestMapping(value = "/getMobileSceneForAuto")
    @ResponseBody
    public Object getMobileScene(String code){
    	if( StringUtils.isBlank(code) ){
    		logger.info(CLASS_LOG_PREFIX + " 获取720漫游场景:vr720RoamOfMobile：{}code="+code);
            return new ResponseEnvelope<Scene>(false, "参数错误！");
        }

        // 获自动渲染资源表取单个场景对象
        Scene scene = panoramaService.getMobileSceneBySysCodeForAuto(code);
        logger.info(CLASS_LOG_PREFIX + " 获自动渲染资源表取单个场景对象:scene：{}", null == scene ? null : scene.toString());
        if (scene == null) {
        	return new ResponseEnvelope<Scene>(false, "数据异常！");
		}
        return new ResponseEnvelope<Scene>(true,scene);
    }

    /**
     * 进入720漫游页面
     * @param request
     * @param response
     * @param code
     * @return
     */
    @RequestMapping(value = "/vr720Roam")
    @ResponseBody
    public Object vr720Roam( String code){
    	if( StringUtils.isBlank(code) ){
    		logger.info(CLASS_LOG_PREFIX + " 获取720漫游场景:vr720RoamOfMobile：{}code="+code);
            return new ResponseEnvelope<RoamSceneVo>(false, "参数错误！");
        }
        // 获取720漫游场景
        RoamSceneVo roamSceneVo = panoramaService.vr720Roam(code);
        logger.info(CLASS_LOG_PREFIX + " 获取720漫游场景:roamSceneVo：{}", null == roamSceneVo ? null : roamSceneVo.toString());
        if (roamSceneVo == null) {
        	return new ResponseEnvelope<RoamSceneVo>(false, "数据异常！");
		}
        return new ResponseEnvelope<RoamSceneVo>(true,roamSceneVo);
    }

    /**
     * 进入720分组访问页面
     * @param request
     * @param response
     * @param code
     * @return
     */
    @RequestMapping(value = "/vr720Group")
    @ResponseBody
    public Object vr720Group(String code){
    	if( StringUtils.isBlank(code) ){
    		logger.info(CLASS_LOG_PREFIX + " 获取720漫游场景:vr720RoamOfMobile：{}code="+code);
            return new ResponseEnvelope<GroupSceneVo>(false, "参数错误！");
        }
        // 获取720组合场景
        GroupSceneVo groupSceneVo = panoramaService.vr720Group(code);
        logger.info(CLASS_LOG_PREFIX + " 获取720组合场景:groupSceneVo：{}", null == groupSceneVo ? null : groupSceneVo.toString());
        if (groupSceneVo == null) {
        	return new ResponseEnvelope<GroupSceneVo>(false, "数据异常！");
		}
        return new ResponseEnvelope<GroupSceneVo>(true,groupSceneVo);
    }

    


    /**
     * 获取用户昵称、企业名称、企业LOGO等信息
     * @param request
     * @param response
     * @param planId
     * @return
     */
    @RequestMapping(value = "/getPanoramaVo")
    @ResponseBody
    public Object getPanoramaVo(HttpServletRequest request, HttpServletResponse response, Integer planId){
        if(planId == null) {
        	logger.info(CLASS_LOG_PREFIX + " 获取720漫游场景:vr720RoamOfMobile：{}planId="+planId);
        	return new ResponseEnvelope<PanoramaVo>(false, "参数错误！");
        }
        // 组装页面需要显示的业务信息（用户昵称、企业LOGO、企业名称、产品费用清单）
        /*PanoramaVo panoramaVo = panoramaService.assemblyPanoramaVo(planId, null, null);*/
        Scene scene = new Scene();
        scene.setPlanId(planId);
        PanoramaVo panoramaVo = panoramaService.assemblyPanoramaVo(scene);
        logger.info(CLASS_LOG_PREFIX + " 组装页面需要显示的业务信息:groupSceneVo：{}", null == panoramaVo ? null : panoramaVo.toString());
        if (panoramaVo == null) {
        	return new ResponseEnvelope<PanoramaVo>(false, "数据异常！");
		}
        return new ResponseEnvelope<PanoramaVo>(true,panoramaVo);
    }

    /**
     * 获取设计方案副本产品费用清单
     * @param request
     * @param response
     * @param planId
     * @param userId
     * @param userType
     * @return
     */
    @RequestMapping(value = "/getProductsCost")
    @ResponseBody
    public Object getProductsCost(HttpServletRequest request, HttpServletResponse response, Integer planId, Integer userId, Integer userType){
    	if(planId == null) {
    		logger.info(CLASS_LOG_PREFIX + " 获取720漫游场景:vr720RoamOfMobile：{}planId="+planId);
        	return new ResponseEnvelope<GroupSceneVo>(false, "参数错误！");
        }
    	if(userId == null) {
    		logger.info(CLASS_LOG_PREFIX + " 获取720漫游场景:vr720RoamOfMobile：{}userId="+userId);
        	return new ResponseEnvelope<GroupSceneVo>(false, "参数错误！");
        }
    	if(userType == null) {
    		logger.info(CLASS_LOG_PREFIX + " 获取720漫游场景:vr720RoamOfMobile：{}userType="+userType);
        	return new ResponseEnvelope<GroupSceneVo>(false, "参数错误！");
        }
    	
    	// 获取设计方案产品费用清单
        List<ProductsCostType> list = panoramaService.getDesignRenderGroupCost(planId, userId, userType);
        GroupSceneVo groupSceneVo = new GroupSceneVo();
        if( list != null && list.size() > 0 ){
            BigDecimal totalPrice = new BigDecimal(0.0);
            for( ProductsCostType cost : list ){
                totalPrice = totalPrice.add(cost.getTotalPrice());
            }
            groupSceneVo.setTotalPrice(totalPrice.toString());
            groupSceneVo.setProductsCostTypeList(list);
            logger.info(CLASS_LOG_PREFIX + " 获取设计方案产品费用清单:groupSceneVo：{}", null == groupSceneVo ? null : groupSceneVo.toString());
        }
        return new ResponseEnvelope<GroupSceneVo>(true,groupSceneVo);
    }
    
    /**
     * 进入单场景720页面   移动端有按钮的
     * @param request
     * @param response
     * @param code
     * @return
     */
    @RequestMapping(value = "/vr720MobileOrginal")
    @ResponseBody
    public Object vr720SingleOfMobile(HttpServletRequest request, HttpServletResponse response, String code){
    	if( StringUtils.isBlank(code) ){
    		logger.info(CLASS_LOG_PREFIX + " 获取720漫游场景:vr720RoamOfMobile：{}code="+code);
            return new ResponseEnvelope<SingleSceneVo>(false, "参数错误！");
        }
        // 获取单场景信息
        SingleSceneVo singleSceneVo = panoramaService.vr720SingleMobileForAuto(code);
        logger.info(CLASS_LOG_PREFIX + " 进入单场景720页面   移动端有按钮的:singleSceneVo：{}", null == singleSceneVo ? null : singleSceneVo.toString());
        if (singleSceneVo == null) {
        	return new ResponseEnvelope<SingleSceneVo>(false, "数据异常！");
		}
        return new ResponseEnvelope<SingleSceneVo>(true,singleSceneVo);
    }
    
    /**
     * 进入720漫游页面
     * @param request
     * @param response
     * @param code
     * @return
     */
    @RequestMapping(value = "/vr720RoamOfMobile")
    @ResponseBody
    public Object vr720RoamOfMobile(HttpServletRequest request, HttpServletResponse response, String code){
    	if( StringUtils.isBlank(code) ){
    		logger.info(CLASS_LOG_PREFIX + " 获取720漫游场景:vr720RoamOfMobile：{}code="+code);
            return new ResponseEnvelope<RoamSceneVo>(false, "参数错误！");
        }
        // 获取720漫游场景
        RoamSceneVo roamSceneVo = panoramaService.vr720Roam(code);
        logger.info(CLASS_LOG_PREFIX + " 获取720漫游场景:roamSceneVo：{}", null == roamSceneVo ? null : roamSceneVo.toString());
        if (roamSceneVo == null) {
        	return new ResponseEnvelope<RoamSceneVo>(false, "数据异常！");
		}
        return new ResponseEnvelope<RoamSceneVo>(true,roamSceneVo);
    }

}
