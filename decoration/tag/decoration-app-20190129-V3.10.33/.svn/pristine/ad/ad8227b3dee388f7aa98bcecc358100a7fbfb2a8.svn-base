package com.nork.pano.controller;

import com.nork.common.util.JAXBUtil;
import com.nork.common.util.Utils;
import com.nork.design.model.ProductsCostType;
import com.nork.pano.model.scene.*;
import com.nork.pano.service.PanoramaService;
import com.nork.system.controller.SysUserGroupController;
import com.nork.system.service.ResRenderPicService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
@Controller
@RequestMapping(value = "/pages/vr720")
public class PanoramaController {
    private static Logger logger = Logger.getLogger(PanoramaController.class);
    @Autowired
    private ResRenderPicService resRenderPicService;
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
    public Object vr720Single(HttpServletRequest request, HttpServletResponse response, String code){
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("success",false);
        if( StringUtils.isBlank(code) ){
            jsonObject.remove("success");
            jsonObject.accumulate("success",false);
            jsonObject.accumulate("message","错误的请求!");
            return jsonObject;
        }
        // 获取单场景信息
        SingleSceneVo singleSceneVo = panoramaService.vr720Single(code);
        request.setAttribute("vo",singleSceneVo);
        request.setAttribute("code",code);
        return "vr720/vr720";
    }
    
    
    @RequestMapping(value = "/vr720MobileSingle")
    public Object vr720MobileSingle(HttpServletRequest request, HttpServletResponse response, String code){
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("success",false);
        if( StringUtils.isBlank(code) ){
            jsonObject.remove("success");
            jsonObject.accumulate("success",false);
            jsonObject.accumulate("message","错误的请求!");
            return jsonObject;
        }
        // 获取单场景信息
        SingleSceneVo singleSceneVo = panoramaService.vr720MobileSingle(code);
        request.setAttribute("vo",singleSceneVo);
        request.setAttribute("code",code);
        return "vr720/vr720Mobile";
    }
    
    /**
     * 获取单场景
     * @param code
     * @return
     */
    @RequestMapping(value = "/getScene")
    @ResponseBody
    public Object getScene(String code){
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("success",false);
        if( StringUtils.isBlank(code) ){
            jsonObject.remove("success");
            jsonObject.accumulate("success",false);
            jsonObject.accumulate("message","错误的请求!");
            return jsonObject;
        }

        // 获取单个场景对象
        Scene scene = panoramaService.getSceneBySysCode(code);
        if( scene != null ){
            jsonObject.remove("success");
            jsonObject.accumulate("success",true);
            jsonObject.accumulate("message", JAXBUtil.beanToXml(scene,Scene.class));
        }
        return jsonObject;
    }
    
    
    @RequestMapping(value = "/vr720MobileSingleForAuto")
    public Object vr720MobileSingleForAuto(HttpServletRequest request, HttpServletResponse response, String code){
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("success",false);
        if( StringUtils.isBlank(code) ){
            jsonObject.remove("success");
            jsonObject.accumulate("success",false);
            jsonObject.accumulate("message","错误的请求!");
            return jsonObject;
        }
        // 从自动渲染资源表里获取单场景信息
        SingleSceneVo singleSceneVo = panoramaService.vr720SingleMobileForAuto(code);
        request.setAttribute("vo",singleSceneVo);
        request.setAttribute("code",code);
        return "vr720/vr720MobileForAuto";
    }
    
    @RequestMapping(value = "/getMobileSceneForAuto")
    @ResponseBody
    public Object getMobileScene(String code){
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("success",false);
        if( StringUtils.isBlank(code) ){
            jsonObject.remove("success");
            jsonObject.accumulate("success",false);
            jsonObject.accumulate("message","错误的请求!");
            return jsonObject;
        }

        // 获自动渲染资源表取单个场景对象
        Scene scene = panoramaService.getMobileSceneBySysCodeForAuto(code);
        if( scene != null ){
            jsonObject.remove("success");
            jsonObject.accumulate("success",true);
            jsonObject.accumulate("message", JAXBUtil.beanToXml(scene,Scene.class));
        }
        return jsonObject;
    }

    /**
     * 进入720漫游页面
     * @param request
     * @param response
     * @param code
     * @return
     */
    @RequestMapping(value = "/vr720Roam")
    public Object vr720Roam(HttpServletRequest request, HttpServletResponse response, String code){
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("success",false);
        if( StringUtils.isBlank(code) ){
            jsonObject.remove("success");
            jsonObject.accumulate("success",false);
            jsonObject.accumulate("message","错误的请求!");
            return jsonObject;
        }
        // 获取720漫游场景
        RoamSceneVo roamSceneVo = panoramaService.vr720Roam(code);
        request.setAttribute("vo",roamSceneVo);
        return "vr720/vr720Roam";
    }

    /**
     * 进入720分组访问页面
     * @param request
     * @param response
     * @param code
     * @return
     */
    @RequestMapping(value = "/vr720Group")
    public Object vr720Group(HttpServletRequest request, HttpServletResponse response, String code){
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("success",false);
        if( StringUtils.isBlank(code) ){
            jsonObject.remove("success");
            jsonObject.accumulate("success",false);
            jsonObject.accumulate("message","错误的请求!");
            return jsonObject;
        }
        logger.error(code+"_vr720Group_"+request.getRequestedSessionId()+" _ "+Utils.getRequestIp(request)+"_"+(String) request.getHeader("User-Agent"));
        // 获取720组合场景
        GroupSceneVo groupSceneVo = panoramaService.vr720Group(code);
        request.setAttribute("vo",groupSceneVo);
        return "vr720/vr720Group";
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
        JSONObject responseJson = new JSONObject();
        if( planId == null ){
            responseJson.accumulate("success",false);
            responseJson.accumulate("message","错误的请求!");
        }
        // 组装页面需要显示的业务信息（用户昵称、企业LOGO、企业名称、产品费用清单）
        /*PanoramaVo panoramaVo = panoramaService.assemblyPanoramaVo(planId, null, null);*/
        Scene scene = new Scene();
        scene.setPlanId(planId);
        PanoramaVo panoramaVo = panoramaService.assemblyPanoramaVo(scene);
        responseJson.accumulate("success",true);
        responseJson.accumulate("panoramaVo",panoramaVo);

        return responseJson;
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
    public Object getProductsCost(HttpServletRequest request, HttpServletResponse response, Integer planId, Integer userId, Integer userType){
        // 获取设计方案产品费用清单
        List<ProductsCostType> list = panoramaService.getDesignRenderGroupCost(planId, userId, userType);
        if( list != null && list.size() > 0 ){
            GroupSceneVo groupSceneVo = new GroupSceneVo();
            BigDecimal totalPrice = new BigDecimal(0.0);
            for( ProductsCostType cost : list ){
                totalPrice = totalPrice.add(cost.getTotalPrice());
            }
            groupSceneVo.setTotalPrice(totalPrice.toString());
            groupSceneVo.setProductsCostTypeList(list);
            request.setAttribute("vo",groupSceneVo);
        }
        return "vr720/productCost";
    }
    
    /**
     * 进入单场景720页面   移动端有按钮的
     * @param request
     * @param response
     * @param code
     * @return
     */
    @RequestMapping(value = "/vr720MobileOrginal")
    public Object vr720SingleOfMobile(HttpServletRequest request, HttpServletResponse response, String code){
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("success",false);
        if( StringUtils.isBlank(code) ){
            jsonObject.remove("success");
            jsonObject.accumulate("success",false);
            jsonObject.accumulate("message","错误的请求!");
            return jsonObject;
        }
        // 获取单场景信息
        SingleSceneVo singleSceneVo = panoramaService.vr720SingleMobileForAuto(code);
        request.setAttribute("vo",singleSceneVo);
        request.setAttribute("code",code);
        return "vr720/vr720MobileOrginal";
    }
    
    /**
     * 进入720漫游页面
     * @param request
     * @param response
     * @param code
     * @return
     */
    @RequestMapping(value = "/vr720RoamOfMobile")
    public Object vr720RoamOfMobile(HttpServletRequest request, HttpServletResponse response, String code){
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("success",false);
        if( StringUtils.isBlank(code) ){
            jsonObject.remove("success");
            jsonObject.accumulate("success",false);
            jsonObject.accumulate("message","错误的请求!");
            return jsonObject;
        }
        // 获取720漫游场景
        RoamSceneVo roamSceneVo = panoramaService.vr720Roam(code);
        request.setAttribute("vo",roamSceneVo);
        return "vr720/vr720RoamOfMobile";
    }
}
