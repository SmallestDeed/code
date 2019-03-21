package com.nork.pano.controller;

import com.nork.cityunion.model.input.UnionGroupSearch;
import com.nork.cityunion.model.vo.UnionGroupVo;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nork.cityunion.model.vo.UnionGroupDetailsVo;
import com.nork.cityunion.model.vo.UnionSpecialOfferVo;
import com.nork.cityunion.service.UnionGroupDetailsService;
import com.nork.cityunion.service.UnionSpecialOfferService;
import com.nork.common.exception.BizException;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.JAXBUtil;
import com.nork.common.util.Utils;
import com.nork.design.model.ProductsCostType;
import com.nork.pano.model.scene.GroupSceneVo;
import com.nork.pano.model.scene.PanoramaVo;
import com.nork.pano.model.scene.RoamSceneVo;
import com.nork.pano.model.scene.Scene;
import com.nork.pano.model.scene.SingleSceneVo;
import com.nork.pano.model.scene.UnionStoreProductsCostModel;
import com.nork.pano.model.scene.UnionStoreProductsCostVo;
import com.nork.pano.model.scene.UnionStoreSingleSenceInfoVo;
import com.nork.pano.service.PanoramaService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
@Controller
@RequestMapping(value = "/pages/vr720")
public class PanoramaViewController {
    private static Logger logger = Logger.getLogger(PanoramaViewController.class);
    
    private static final String LOGPREFIX = "[720渲染图模块]:";
    
    private static final String REDIRECTURL = "https://www.sanduspace.com/";
    
    @Autowired
    private PanoramaService panoramaService;

    @Autowired
    private UnionGroupDetailsService unionGroupDetailsService;
    
    @Autowired
    private UnionSpecialOfferService unionSpecialOfferService;
    
    /**
     * 进入单场景720页面
     * @param request
     * @param response
     * @param code
     * @return
     */
    @RequestMapping(value = "/vr720Single")
    public Object vr720Single(
    		HttpServletRequest request, 
    		HttpServletResponse response, 
    		String code,
    		/**
			 * add by huangsongbo 2018.8.13
			 * 有效日期截止至...
			 * 格式:2018-8-15
			 */
			String dateDueStr){
    	// 验证 dateDueStr ->start
    	if(StringUtils.isNotEmpty(dateDueStr)) {
    		Date dateDue;
    		try {
    			dateDue = Utils.dealWithParamDateDueStr(dateDueStr, 0);
    		} catch (BizException e) {
    			logger.error(LOGPREFIX + e.getMessage());
    			return new ResponseEnvelope<>(false, e.getMessage(), null);
    		}
        	if(new Date().getTime() - dateDue.getTime() > 0) {
        		try {
    				response.sendRedirect(REDIRECTURL);
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
        	}
    	}
    	// 验证 dateDueStr ->start
    	
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
        Scene scene = panoramaService.getSceneBySysCode(code, "");
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
    public Object vr720Roam(
    		HttpServletRequest request, 
    		HttpServletResponse response, 
    		String code,
    		/**
			 * add by huangsongbo 2018.8.13
			 * 有效日期截止至...
			 * 格式:2018-8-15
			 */
			String dateDueStr
    		){
    	
    	// 验证 dateDueStr ->start
    	if(StringUtils.isNotEmpty(dateDueStr)) {
    		Date dateDue;
    		try {
    			dateDue = Utils.dealWithParamDateDueStr(dateDueStr, 0);
    		} catch (BizException e) {
    			logger.error(LOGPREFIX + e.getMessage());
    			return new ResponseEnvelope<>(false, e.getMessage(), null);
    		}
        	if(new Date().getTime() - dateDue.getTime() > 0) {
        		try {
    				response.sendRedirect(REDIRECTURL);
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
        	}
    	}
    	// 验证 dateDueStr ->start
    	
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
//        logger.error(code+"_vr720Group_"+request.getRequestedSessionId()+" _ "+Utils.getRequestIp(request)+"_"+(String) request.getHeader("User-Agent"));
        // 获取720组合场景
        GroupSceneVo groupSceneVo = panoramaService.vr720Group(code);
        request.setAttribute("vo",groupSceneVo);
        request.setAttribute("code" , code);
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
    
    /**
     * 进入同城联盟分享单场景720页面
     * @param request
     * @param response
     * @param code
     * @param releaseId 联盟方案发布ID 
     * @return
     */
    @RequestMapping(value = "/vr720UnionStoreSingle")
    @ResponseBody
    public Object vr720UnionStoreSingle(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "releaseId")Integer releaseId){
        if( releaseId == null){
            return new ResponseEnvelope<SingleSceneVo>(false, "参数错误！");
        }
        // 获取单场景信息
        List<SingleSceneVo> singleSceneVoLs = panoramaService.vr720UnionStoreSingle(releaseId);
        if (singleSceneVoLs == null || singleSceneVoLs.size() < 0) {
            return new ResponseEnvelope<SingleSceneVo>(false, "该同城联盟方案不存在！");
        }
        return new ResponseEnvelope<SingleSceneVo>(singleSceneVoLs == null ? 0 : singleSceneVoLs.size(),singleSceneVoLs);
    }
    
    
    
    /**
     * 获得同城联盟分享单场景720页面 门店分组信息
     * @param releaseId 联盟方案发布Id
     * @return
     */
    @RequestMapping(value = "/getUnionGroupDetailsList")
    @ResponseBody
    public Object getUnionGroupDetailsList(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "releaseId")Integer releaseId) {
      List<UnionGroupDetailsVo> detailsVoLs = new ArrayList<UnionGroupDetailsVo>();
      if(releaseId == null) {
        return new ResponseEnvelope<UnionSpecialOfferVo>(false,"参数异常！");
      }
      detailsVoLs = unionGroupDetailsService.getUnionGroupDetailsLsByReleaseId(releaseId);


      logger.info("getUnionGroupDetailsList");
      return new ResponseEnvelope<UnionGroupDetailsVo>(detailsVoLs == null ? 0:detailsVoLs.size(),detailsVoLs);
    }

    /**
     * 获取同城联盟分享单场景720页面 优惠活动信息
     * @param releaseId
     * @return
     */
    @RequestMapping(value = "/getUnionSpecialOfferVo")
    @ResponseBody
    public Object getUnionSpecialOfferVo(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "releaseId")Integer releaseId) {
      UnionSpecialOfferVo specialOfferVo = new UnionSpecialOfferVo();
      if(releaseId == null) {
        return new ResponseEnvelope<UnionSpecialOfferVo>(false,"参数异常！");
      }
      specialOfferVo = unionSpecialOfferService.getUnionSpecialOfferVoByReleaseId(releaseId);
      
      return new ResponseEnvelope<UnionSpecialOfferVo>(true,specialOfferVo);
    }
    
    /**
     * 获取同城联盟分享单场景720页面 商品清单
     * @param request
     * @param response
     * @param releaseId
     * @param picId
     * @return
     */
    @RequestMapping(value = "/getUnionStoreProductsCostList")
    @ResponseBody
    public Object getUnionStoreProductsCostList(HttpServletRequest request, HttpServletResponse response,
        UnionStoreProductsCostModel productsCostDto) {
      UnionStoreProductsCostVo productsCostVo = new UnionStoreProductsCostVo(); 
      //验证参数
      if(productsCostDto == null || productsCostDto.getReleaseId() == null || productsCostDto.getRenderPicId() == null) {
        return new ResponseEnvelope<UnionStoreProductsCostVo>(false,"参数异常！");
      }
      //得到数据
      productsCostVo = panoramaService.getUnionStoreProductsCostList(productsCostDto.getReleaseId(), productsCostDto.getRenderPicId());
      return new ResponseEnvelope<ProductsCostType>(true,productsCostVo);
    }
    
    /**
     * 同城联盟720单点分享页方案切换 获取数据接口
     * @param request
     * @param response
     * @param releaseId 发布
     * @return
     */
    @RequestMapping(value = "/getUnionStoreSingleSenceList")
    @ResponseBody
    public Object getUnionStoreSingleSenceList(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "releaseId")Integer releaseId) {
      List<UnionStoreSingleSenceInfoVo> singleSenceInfoVo = new ArrayList<UnionStoreSingleSenceInfoVo>();
      singleSenceInfoVo = panoramaService.getUnionStoreSingleSenceInfoList(releaseId);
      return new ResponseEnvelope<UnionStoreSingleSenceInfoVo>(singleSenceInfoVo == null ? 0 : singleSenceInfoVo.size(),singleSenceInfoVo);
    }
    
    
}
