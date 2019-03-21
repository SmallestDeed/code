package com.nork.render.controller;

import com.google.gson.Gson;
import com.nork.common.cache.utils.JedisUtils;
import com.nork.common.cache.utils.JedisUtils2;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.JsonUtil;
import com.nork.common.util.Utils;
import com.nork.design.model.UserDesignPlanPurchaseRecord;
import com.nork.design.service.UserDesignPlanPurchaseRecordService;
import com.nork.home.model.UserHouseRecord;
import com.nork.home.service.UserHouseRecordService;
import com.nork.platform.model.BasePlatform;
import com.nork.platform.service.BasePlatformService;
import com.nork.render.model.OrderResponse;
import com.nork.render.model.PayDesignPlanFree;
import com.nork.render.service.IAutoRenderTaskApi;
import com.nork.system.model.ResRenderPic;
import com.sandu.common.LoginContext;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/12
 */
@Controller
@RequestMapping("/{style}/render/server")
public class AddRenderTaskController {
    private final static String CLASS_LOG_PREFIX = "[添加渲染任务服务]:";
    private final static Logger logger = LogManager.getLogger(AutoRenderTaskController.class);
    public final static Integer PLAN_SINGLE_HOUSE_TYPE = 1;
    public final static Integer PLAN_FULL_HOUSE_TYPE = 2;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private IAutoRenderTaskApi autoRenderTaskApi;
    @Autowired
    private UserHouseRecordService userHouseRecordService;
    @Autowired
    private BasePlatformService basePlatformService;
    @Autowired
    private UserDesignPlanPurchaseRecordService userDesignPlanPurchaseRecordService;

    private static final String RENDER_TASK_INFO_PREFIX = "render_task_info_prefix:";

    /**
     * 添加渲染任务
     *
     * @param resRenderPic
     * @return
     */
    @ResponseBody
    @RequestMapping("/addRenderTask")
    public ResponseEnvelope addRenderTask(HttpServletRequest request, @RequestBody ResRenderPic resRenderPic) {

        Map<String, String> headerMap = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            headerMap.put(key, value);
        }
        logger.error("addRenderTask-headerNames:{}.", JsonUtil.toJson(headerMap));

        String token = request.getHeader("Authorization");//获取token
        String platformCode = request.getHeader("Platform-Code");//获取平台编码
        String companyDomainName = request.getHeader("Referer"); // 获取域名
        //获取登录用户信息
        Map map = LoginContext.getTokenData();
        if (null == map) {
            return new ResponseEnvelope<>(false, "请重新登录！");
        }
//		if (null == companyDomainName) {
//			return new ResponseEnvelope<>(false,"获取域名失败！");
//		}

        LoginUser loginUser = new LoginUser();
        String loginName = (String) map.get("uname");
        long id = (long) map.get("uid");
        loginUser.setId(new Long(id).intValue());
        loginUser.setLoginName(loginName);
//		LoginUser loginUser = SystemCommonUtil.getLoginUserInfoByAuthData(request);
        Integer userId = loginUser.getId();
        Double totalFee = resRenderPic.getTotalFee();
        logger.error("addRenderTask------totalFee=" + resRenderPic.getTotalFee());
        String renderTaskType = resRenderPic.getRenderTaskType();
        String payType = resRenderPic.getPayType();
        Integer houseId = resRenderPic.getHouseId();
        //区分推荐方案ID 或副本方案ID
        Integer planId = null;
        //订单号
        String orderNo = null;
        String orderNoStr = null;
        if (resRenderPic.getTaskSource().intValue() == 1) {
            //移动端
            if (totalFee > 0) {
                logger.error(" 1   ======totalFee" + totalFee);
                logger.error("移动端getOrderNoMobile====userId=" + userId + "&totalFee=" + totalFee + "&payType=" + payType + "&renderTaskType=" + renderTaskType + "&token=" + token + "&platformCode=" + platformCode + "&companyDomainName=" + companyDomainName);
                orderNoStr = getOrderNoMobile(userId, totalFee, payType, renderTaskType, token, platformCode, companyDomainName);
            } else {
                logger.error(" 2   ======totalFee" + totalFee);
                logger.error("移动端getBaoNianOrderNo====renderTaskType=" + renderTaskType + "&token=" + token + "&platformCode=" + platformCode + "&companyDomainName=" + companyDomainName);
                //包年包月
                orderNoStr = getBaoNianOrderNo(userId, renderTaskType, planId, houseId, token, platformCode, companyDomainName);

            }
            if (orderNoStr.equals("false") || orderNoStr.equals("")) {
                return new ResponseEnvelope<>(false, "获取订单号失败！");
            }
            logger.info(CLASS_LOG_PREFIX + "addRenderTask 移动端===>orderNoStr" + orderNoStr);
            JSONObject jsonObject = JSONObject.fromObject(orderNoStr);
            String msg = (String) jsonObject.get("message");
            if (jsonObject.get("obj").equals("null")) {
                return new ResponseEnvelope<>(false, msg);
            } else {
                orderNo = (String) jsonObject.get("obj");
            }
        } else {
            //运营网站 C端
            if (resRenderPic.getOperationSource().intValue() == 0) {
                planId = resRenderPic.getDesignPlanSceneId();
            } else {
                planId = resRenderPic.getPlanRecommendedId();
            }
            if (null == totalFee || totalFee > 0) {
                if (PLAN_FULL_HOUSE_TYPE.equals(resRenderPic.getPlanHouseType())) {
                    renderTaskType = "full_house_render_name";
                    logger.error(" C端 收费渲染  ====== renderTaskType="+renderTaskType);
                }
                logger.error(" C端 收费渲染  ======totalFee:" + totalFee);
                logger.error(" C端 收费渲染getOrderNoWeb ======userId=" + userId + "&renderTaskType=" + renderTaskType + "&planId=" + planId + "&houseId=" + houseId + "&token=" + token + "&platformCode=" + platformCode + "&companyDomainName=" + companyDomainName);
                orderNoStr = getOrderNoWeb(userId, renderTaskType, planId, houseId, token, platformCode, companyDomainName);
            } else {
                logger.error(" C端 包年包月  ======totalFee" + totalFee);
                logger.error("C端getBaoNianOrderNo====userId=" + userId + "&renderTaskType=" + renderTaskType + "&planId=" + planId + "&houseId=" + houseId + "&token=" + token + "&platformCode=" + platformCode + "&companyDomainName=" + companyDomainName);
                //包年包月
                orderNoStr = getBaoNianOrderNo(userId, renderTaskType, planId, houseId, token, platformCode, companyDomainName);
            }
            if (orderNoStr.equals("false") || orderNoStr.equals("")) {
                return new ResponseEnvelope<>(false, "获取订单号失败！");
            }
            logger.error(CLASS_LOG_PREFIX + "addRenderTask 运营网站===>orderNoStr" + orderNoStr);

            OrderResponse orderResponse = JsonUtil.fromJson(orderNoStr, OrderResponse.class);
            if (null != orderResponse) {
                logger.error(CLASS_LOG_PREFIX + "addRenderTask 运营网站=========>jsonObject" + orderResponse.toString());
            }
            String msg = orderResponse.getMessage();
            Object obj = orderResponse.getObj();
            if (StringUtils.isEmpty(obj)) {
                return new ResponseEnvelope<>(false, msg);
            } else {
                logger.error("addRenderTask 运营网站=========>obj" + obj);
                orderNo = (String) obj;
            }
        }

        if (resRenderPic.getTaskSource().intValue() == 1 && null != houseId && houseId != 0) {
            try {
                BasePlatform basePlatform = basePlatformService.getByCode(platformCode);
                UserHouseRecord userHouseRecord = new UserHouseRecord();
                userHouseRecord.setUserId(userId);
                userHouseRecord.setHouseId(houseId);
                userHouseRecord.setPlatformId(basePlatform.getId());
                userHouseRecord.setRenderType(1);
                userHouseRecord.setHouseCommonCode(resRenderPic.getHouseCommonCode());
                userHouseRecord.setHouseName(resRenderPic.getHouseName());
                userHouseRecord.setLivingName(resRenderPic.getLivingName());
                userHouseRecord.setCreator(userId.toString());
                userHouseRecord.setModifier(userId.toString());
                userHouseRecordService.insertRecord(userHouseRecord);
            } catch (Exception e) {
                logger.error("addRenderTask ------- 保存户型失败 exception=" + e);
            }
        }

        resRenderPic.setUserId(userId);
        resRenderPic.setOrderNo(orderNo);
        resRenderPic.setToken(token);
        resRenderPic.setPlatformCode(platformCode);

        //如果不传方案空间类型，默认为单空间
        if (resRenderPic.getPlanHouseType() == null) {
            resRenderPic.setPlanHouseType(PLAN_SINGLE_HOUSE_TYPE);
        }
        ResponseEnvelope responseEnvelope;
        if (PLAN_FULL_HOUSE_TYPE.equals(resRenderPic.getPlanHouseType())) {
            responseEnvelope = autoRenderTaskApi.createFullHouseAutoRenderTask(resRenderPic);
        } else {
            responseEnvelope = autoRenderTaskApi.createAutoRenderTask(resRenderPic);
        }
        return responseEnvelope;
    }

    public ResponseEnvelope payCallBackaddRenderTask(ResRenderPic resRenderPic) {

        //从缓存中获取token等信息
        String infoMap = JedisUtils2.get(RENDER_TASK_INFO_PREFIX + resRenderPic.getUserId());

        Map<String, String> map = new Gson().fromJson(infoMap, Map.class);

        if (map == null || !map.isEmpty()) {
            logger.error("支付回调从缓存中获取token等信息失败");
            return new ResponseEnvelope(false, "添加渲染任务失败!");
        }

        String token = map.get("token");//获取token
        String platformCode = map.get("platformCode");//获取平台编码
        String companyDomainName = map.get("companyDomainName"); // 获取域名

        Integer userId = resRenderPic.getUserId();
        Double totalFee = resRenderPic.getTotalFee();
        logger.error("addRenderTask------totalFee=" + resRenderPic.getTotalFee());
        String renderTaskType = resRenderPic.getRenderTaskType();
        String payType = resRenderPic.getPayType();
        Integer houseId = resRenderPic.getHouseId();
        //区分推荐方案ID 或副本方案ID
        Integer planId = null;
        //订单号
        String orderNo = null;
        String orderNoStr = null;
        if (resRenderPic.getTaskSource().intValue() == 1) {
            //移动端
            if (totalFee > 0) {
                logger.error(" 1   ======totalFee" + totalFee);
                logger.error("移动端getOrderNoMobile====userId=" + userId + "&totalFee=" + totalFee + "&payType=" + payType + "&renderTaskType=" + renderTaskType + "&token=" + token + "&platformCode=" + platformCode + "&companyDomainName=" + companyDomainName);
                orderNoStr = getOrderNoMobile(userId, totalFee, payType, renderTaskType, token, platformCode, companyDomainName);
            } else {
                logger.error(" 2   ======totalFee" + totalFee);
                logger.error("移动端getBaoNianOrderNo====renderTaskType=" + renderTaskType + "&token=" + token + "&platformCode=" + platformCode + "&companyDomainName=" + companyDomainName);
                //包年包月
                orderNoStr = getBaoNianOrderNo(userId, renderTaskType, planId, houseId, token, platformCode, companyDomainName);

            }
            if (orderNoStr.equals("false") || orderNoStr.equals("")) {
                return new ResponseEnvelope<>(false, "获取订单号失败！");
            }
            logger.info(CLASS_LOG_PREFIX + "addRenderTask 移动端===>orderNoStr" + orderNoStr);
            JSONObject jsonObject = JSONObject.fromObject(orderNoStr);
            String msg = (String) jsonObject.get("message");
            if (jsonObject.get("obj").equals("null")) {
                return new ResponseEnvelope<>(false, msg);
            } else {
                orderNo = (String) jsonObject.get("obj");
            }
        } else {
            //运营网站 C端
            if (resRenderPic.getOperationSource().intValue() == 0) {
                planId = resRenderPic.getDesignPlanSceneId();
            } else {
                planId = resRenderPic.getPlanRecommendedId();
            }
            if (null == totalFee || totalFee > 0) {
                logger.error(" C端 收费渲染  ======totalFee:" + totalFee);
                logger.error(" C端 收费渲染getOrderNoWeb ======userId=" + userId + "&renderTaskType=" + renderTaskType + "&planId=" + planId + "&houseId=" + houseId + "&token=" + token + "&platformCode=" + platformCode + "&companyDomainName=" + companyDomainName);
                orderNoStr = getOrderNoWeb(userId, renderTaskType, planId, houseId, token, platformCode, companyDomainName);
            } else {
                logger.error(" C端 包年包月  ======totalFee" + totalFee);
                logger.error("C端getBaoNianOrderNo====userId=" + userId + "&renderTaskType=" + renderTaskType + "&planId=" + planId + "&houseId=" + houseId + "&token=" + token + "&platformCode=" + platformCode + "&companyDomainName=" + companyDomainName);
                //包年包月
                orderNoStr = getBaoNianOrderNo(userId, renderTaskType, planId, houseId, token, platformCode, companyDomainName);
            }
            if (orderNoStr.equals("false") || orderNoStr.equals("")) {
                return new ResponseEnvelope<>(false, "获取订单号失败！");
            }
            logger.error(CLASS_LOG_PREFIX + "addRenderTask 运营网站===>orderNoStr" + orderNoStr);

            OrderResponse orderResponse = JsonUtil.fromJson(orderNoStr, OrderResponse.class);
            if (null != orderResponse) {
                logger.error(CLASS_LOG_PREFIX + "addRenderTask 运营网站=========>jsonObject" + orderResponse.toString());
            }
            String msg = orderResponse.getMessage();
            Object obj = orderResponse.getObj();
            if (StringUtils.isEmpty(obj)) {
                return new ResponseEnvelope<>(false, msg);
            } else {
                logger.error("addRenderTask 运营网站=========>obj" + obj);
                orderNo = (String) obj;
            }
        }

        if (resRenderPic.getTaskSource().intValue() == 1 && null != houseId && houseId != 0) {
            try {
                BasePlatform basePlatform = basePlatformService.getByCode(platformCode);
                UserHouseRecord userHouseRecord = new UserHouseRecord();
                userHouseRecord.setUserId(userId);
                userHouseRecord.setHouseId(houseId);
                userHouseRecord.setPlatformId(basePlatform.getId());
                userHouseRecord.setRenderType(1);
                userHouseRecord.setHouseCommonCode(resRenderPic.getHouseCommonCode());
                userHouseRecord.setHouseName(resRenderPic.getHouseName());
                userHouseRecord.setLivingName(resRenderPic.getLivingName());
                userHouseRecord.setCreator(userId.toString());
                userHouseRecord.setModifier(userId.toString());
                userHouseRecordService.insertRecord(userHouseRecord);
            } catch (Exception e) {
                logger.error("addRenderTask ------- 保存户型失败 exception=" + e);
            }
        }

        resRenderPic.setUserId(userId);
        resRenderPic.setOrderNo(orderNo);
        resRenderPic.setToken(token);
        resRenderPic.setPlatformCode(platformCode);
        ResponseEnvelope responseEnvelope = autoRenderTaskApi.createAutoRenderTask(resRenderPic);
        return responseEnvelope;
    }

    @RequestMapping("/payDesignPlanCopyRight")
    @ResponseBody
    public Object payDesignPlanCopyRight(HttpServletRequest request, @RequestBody PayDesignPlanFree payDesignPlanFree) {
        if (payDesignPlanFree.getUserId() == null){
            Map map = LoginContext.getTokenData();
            Long userId = (Long) map.get("uid");
            payDesignPlanFree.setUserId(userId);
        }
        boolean userDesignIsExist = userDesignPlanPurchaseRecordService.checkUserDesignIsExist(payDesignPlanFree.getUserId().intValue(), payDesignPlanFree.getPlanRecommendedId().intValue());
        logger.info("current DesignPlan is pay? userDesignIsExist =>{}" + userDesignIsExist);
        if (!userDesignIsExist) {
            try {
                String token = request.getHeader("Authorization");
                String platformCode = request.getHeader("Platform-Code");
                payDesignPlanFree.setToken(token);
                payDesignPlanFree.setPlatformCode(platformCode);
                logger.info("处理支付信息");
                String payResult = userDesignPlanPurchaseRecordService.payDesignPlanCopyRight(payDesignPlanFree);
                logger.info("支付信息: =>{}"+payResult);
                Object obj = this.tranFormResult(payResult);
                return new ResponseEnvelope<>(true, obj, userDesignIsExist, "返回支付参数");
            } catch (Exception e) {
                logger.error("系统错误", e);
                return new ResponseEnvelope<>(false, "系统错误");
            }
        }
        return new ResponseEnvelope<>(false, null, userDesignIsExist,"该方案已经支付");
    }

    private Object tranFormResult(String payResult) {

        if (!StringUtils.isEmpty(payResult)) {
            Object obj = new Gson().fromJson(payResult, Object.class);
            return obj;
        }
        return Collections.emptyMap();
    }

    private void cacheCallBackInfo(HttpServletRequest request, ResRenderPic renderPic) {

        //调用支付,支付方案费用
        String token = request.getHeader("Authorization");
        String platformCode = request.getHeader("Platform-Code");
        String companyDomainName = request.getHeader("Referer"); // 获取域名

        Map<String, String> infoMap = new HashMap<>();
        infoMap.put("token", token);
        infoMap.put("platformCode", platformCode);
        infoMap.put("companyDomainName", companyDomainName);

        renderPic.setToken(token);
        renderPic.setPlatformCode(platformCode);
        String key = renderPic.getUserId() + "" + renderPic.getPlanRecommendedId();
        JedisUtils2.set(RENDER_TASK_INFO_PREFIX + key, new Gson().toJson(infoMap), 0);
    }

    @RequestMapping("/checkDesignCopyRight")
    @ResponseBody
    public Object checkDesignCopyRight(@RequestParam(value = "userId",required = false) Long userId,
                                       @RequestParam(value = "recommendedPlanId",required = true)Integer recommendedPlanId) {
        if (userId == null) {
            Map map = LoginContext.getTokenData();
            userId =(Long) map.get("uid");
        }

        if (recommendedPlanId == null) {
            return new ResponseEnvelope(false, "parameter recommendedPlanId is null");
        }

        logger.info("parameter:userId,recommendedPlanId =>{}" + userId + "{}" + recommendedPlanId);

        UserDesignPlanPurchaseRecord u;
        try {
            u = userDesignPlanPurchaseRecordService.getRecordByUserIdAndDesignPlanId(userId.intValue(), recommendedPlanId);
            if (u != null) {
                return new ResponseEnvelope<>(true, u);
            }
        } catch (Exception e) {
            return new ResponseEnvelope<>(false, "系统错误");
        }
        return new ResponseEnvelope<>(false, u);
    }

    @RequestMapping("/callBack")
    public void callBack(HttpServletRequest request) {
        logger.info("支付完成，接口回调。");
        logger.info("request param:{}", request.getParameterMap());
        int tradeStatus;
        String payTradeNo = "";
        try {
            if ("SUCCESS".equals(request.getParameter("resultCode"))) {
                tradeStatus = UserDesignPlanPurchaseRecord.TRADE_STATUS_SUCCESS;
            } else {
                tradeStatus = UserDesignPlanPurchaseRecord.TRADE_STATUS_FAIL;
            }
            String tradeNo = request.getParameter("intenalTradeNo");

            Integer useType = Integer.parseInt(request.getParameter("passbackParams"));
            //更新用户购买方案记录状态
            userDesignPlanPurchaseRecordService.updateCallBackStatus(tradeNo, tradeStatus);

            //获取用户购买记录
            UserDesignPlanPurchaseRecord userDesignPlanPurchaseRecord = userDesignPlanPurchaseRecordService.getByTradeNo(tradeNo, 2);

            //更新推荐方案使用次数
            userDesignPlanPurchaseRecordService.updateRecommendedPlanUseCount(userDesignPlanPurchaseRecord.getDesignPlanId().intValue());

            //插入企业设计方案推荐消费记录
            userDesignPlanPurchaseRecordService.addCompanyDesignPlanIncome(userDesignPlanPurchaseRecord, useType);

            //插入渲染任务
            //payCallBackaddRenderTask(resRenderPic);
        } catch (Exception e) {
            logger.error("{}", e);
        }
    }

    @RequestMapping(value = "/checkReplaceDesignPlanPay")
    @ResponseBody
    public Object replaceDesignPlanPay(@RequestParam(value = "recommendedPlanId",required = true) Integer recommendedPlanId){
        //获取方案详情
        Map map = LoginContext.getTokenData();
        Long userId =(Long) map.get("uid");

        boolean flag = false;
        Map<String,Object> resultMap = new HashMap<>();
        try {
            resultMap = userDesignPlanPurchaseRecordService.checkReplaceDesignPlanPay(userId.intValue(),recommendedPlanId);
            if (!(boolean)resultMap.get("flag")){
                //获取方案信息
                return new ResponseEnvelope<>(false,resultMap);
            }
        } catch (Exception e) {
            logger.error("系统错误",e);
            return new ResponseEnvelope<>(false,"系统错误");
        }
        return new ResponseEnvelope<>(true,resultMap);
    }

    private static String getBaoNianOrderNo(Integer userId, String productType, Integer planId, Integer houseId, String token, String platformCode, String companyDomainName) {
        String payPath = Utils.getPropertyName("app", "pay.server.url", "");
        String url = payPath + "/web/pay/payOrder/addRenderOrder"/*?platformCode=" + platformCode + "&productType=" + productType + "&companyDomainName=" + companyDomainName + "&userId=" + userId*/;

        if ("brand2c".equals(platformCode) || "miniProgram".equals(platformCode) || "mobile2c".equals(platformCode)) {
            url = payPath + "/web/pay/addRenderOrder2C";//?platformCode="+platformCode+"&productType="+productType+"&companyDomainName="+companyDomainName+"&userId="+userId+"&planId="+planId+"&houseId="+houseId;
        }
        logger.error("url=========================" + url);
        logger.info(CLASS_LOG_PREFIX + "addRenderTask===>移动端getBaoNianOrderNo==>url" + url);
        Map<String, String> params = new HashMap<String, String>();
        params.put("productType", productType);
        params.put("companyDomainName", companyDomainName);
        params.put("userId", userId + "");
        if (planId != null) {
            params.put("planId", planId + "");
        }
        if (houseId != null) {
            params.put("houseId", houseId + "");
        }

        try {
            String result = Utils.doPostMethod(url, params, token, platformCode);
            logger.error("移动端getBaoNianOrderNo支付返回==>result" + result);
            if (result != null && result != "") {
                return result;
            }
        } catch (Exception e) {
            logger.error("移动端getBaoNianOrderNo支付返回==>exception:" + e);
            return "false";
        }
        return "false";
    }

    /*    private static String getOrderNoMobile(Integer userId,Double totalFee,String payType,String productType,String token) {

            String payPath = Utils.getPropertyName("app","pay.server.url","");
            String url=payPath+"v1/web/pay/payOrder/addRenderOrder?platformCode="+code+"&productType="+productType;
            logger.info(CLASS_LOG_PREFIX+"addRenderTask===>移动端getOrderNoMobile==>url"+url);
            Map<String,String> params=new HashMap<String,String>();
            params.put("platformCode", code);
            params.put("productType", productType);
            try {
                String result = Utils.doPostMethod(url, params,token);
                if (result != null) {
                    return result;
                }
            } catch (Exception e) {
                return "false";
            }
            return "false";
        }
        */
    private static String getOrderNoMobile(Integer userId, Double totalFee, String payType, String productType, String token, String platformCode, String companyDomainName) {
        String payPath = Utils.getPropertyName("app", "pay.server.url", "");
        String url = payPath + "/web/pay/payOrder/replaceRecord"/*?userId=" + userId + "&totalFee=" + totalFee + "&payType=" + payType + "&productType=" + productType + "&companyDomainName=" + companyDomainName*/;
        logger.info(CLASS_LOG_PREFIX + "addRenderTask===>移动端getOrderNoMobile==>url" + url);
        String uId = userId.toString();
        String totalFe = totalFee.toString();
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", uId);
        params.put("totalFee", totalFe);
        params.put("payType", payType);
        params.put("productType", productType);
        params.put("companyDomainName", companyDomainName);
        try {
            String result = Utils.doPostMethod(url, params, token, platformCode);
            logger.error("移动端getOrderNoMobile支付返回==>result" + result);
            if (result != null && result != "") {
                return result;
            }
        } catch (Exception e) {
            logger.error("移动端getOrderNoMobile支付返回==>exception:" + e);
            return "false";
        }
        return "false";
    }

    public static void main(String[] args) {


//    	Integer userId =1480;
//    	String renderTaskType = "roam720";
//    	Integer planId =87575;
//    	String  token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzaWduZmxhdCI6Im1vYmlsZV8yYl91c2VyX3Rva2VuOiIsImV4dCI6MTUxODI1MjA0Nzg0NCwidWlkIjo1ODIsIm10eXBlIjpudWxsLCJ1bmFtZSI6IjE4NjgxNTIzMDMyIiwidXR5cGUiOjEsImFwcEtleSI6ImZjY2FmMjNhZDJlYTRmODBiYjMyMTY0OWY2NmNhMzY0IiwibWVkaWFUeXBlIjpudWxsLCJzZXNzaW9uVGltZW91dCI6MjU5MjAwLCJ1a2V5IjoiZmNjYWYyM2FkMmVhNGY4MGJiMzIxNjQ5ZjY2Y2EzNjQiLCJpYXQiOjE1MTc5OTI4NDc4NDR9.6EwV_XygPir58ot_zdcJw1ri8GkvytonfiZ_IbfuGzw";
//    	Integer houseId = 0;
//    	String platformCode = "brand2c";
//    	String companyDomainName = "mobile2C";
//
//    	String result = getOrderNoWeb(userId, renderTaskType, planId, houseId,token,platformCode,companyDomainName);
//    	System.out.println("result = = = = = = = ="+result);
        Integer userId = 1480;
        Double totalFee = 500.00;
        String payType = "1";
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzaWduZmxhdCI6Im1vYmlsZV8yYl91c2VyX3Rva2VuOiIsImV4dCI6MTUyMzQyOTI0OTgyNywidWlkIjoxNDgwLCJtdHlwZSI6NSwidW5hbWUiOiIxMzY2OTI0MzUxMyIsInV0eXBlIjoxLCJhcHBLZXkiOiJlZjViODhiNDE0N2Y0ZWZkOWZhYmYxYTM2NjFmOTRjNiIsIm1lZGlhVHlwZSI6NSwic2Vzc2lvblRpbWVvdXQiOjI1OTIwMCwidWtleSI6ImVmNWI4OGI0MTQ3ZjRlZmQ5ZmFiZjFhMzY2MWY5NGM2IiwiaWF0IjoxNTIzMTcwMDQ5ODI3fQ.GwPeiF3dQhbOd_f6kFykPs_XdKIaTMlU5p4cJBdbgSY";
        String productType = "panorama_render";
        String platformCode = "mobile2b";
        String companyDomainName = "https://m.ci.sanduspace.com/";
        String result = getOrderNoMobile(userId, totalFee, payType, productType, token, platformCode, companyDomainName);
        System.out.println("result======= " + result);
    }

    private static String getOrderNoWeb(Integer userId, String renderType, Integer planId, Integer houseId, String token, String platformCode, String companyDomainName) {
        String payPath = Utils.getPropertyName("app", "pay.server.url", "");
//    	String url=payPath+"/web/pay/payOrder/addIntegralPayRenderTask?userId="+userId+"&renderType="+renderType+"&planId="+planId+"&houseId="+houseId+"&companyDomainName="+companyDomainName;
        String url = payPath + "/web/pay/payOrder/addIntegralPayRenderTask";
        logger.info(CLASS_LOG_PREFIX + "addRenderTask===>运营网站getOrderNoWeb==>url:" + url);
        String uId = userId.toString();
        String pId = planId.toString();
        String hId = null;
        if (houseId != null) {
            hId = houseId.toString();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", uId);
        params.put("renderType", renderType);
        params.put("planId", pId);
        if (null != houseId) {
            params.put("houseId", hId);
        }
        params.put("companyDomainName", companyDomainName);
        try {
            String result = Utils.doPostMethod(url, params, token, platformCode);
            logger.error("运营网站getOrderNoWeb返回==>result" + result);
            if (result != null && result != "") {
                return result;
            }
        } catch (Exception e) {
            logger.error("运营网站getOrderNoWeb支付返回==>exception:" + e);
            return "false";
        }
        return "false";
    }
}