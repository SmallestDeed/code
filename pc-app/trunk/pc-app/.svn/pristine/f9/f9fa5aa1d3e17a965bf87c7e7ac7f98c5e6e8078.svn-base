package com.nork.product.controller.web;

import com.google.gson.Gson;
import com.nork.base.controller.BaseController;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.objectconvert.baseseries.BaseSeriesObject;
import com.nork.common.param.ParamCommonVerification;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.design.service.DesignPlanProductService;
import com.nork.product.model.BaseSeries;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.result.DesignProductResult;
import com.nork.product.model.vo.BaseSeriesVO;
import com.nork.product.service.*;
import com.nork.system.model.ResPic;
import com.nork.system.service.ResPicService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2017/11/7 0007.
 */

@Controller
@RequestMapping("/{style}/web/product/baseSeries")
public class WebBaseSeriesController extends BaseController {

    //Json转换类
    private final static Gson gson = new Gson();
    private static Logger logger = Logger.getLogger(WebBaseProductController.class);
    private final static String CLASS_LOG_PREFIX = "[系列模块]:";

    @Autowired
    private BaseSeriesService baseSeriesService;
    @Autowired
    private DesignPlanProductService designPlanProductService;
    @Autowired
    private BaseProductService baseProductService;
    @Autowired
    private ProductCategoryRelService productCategoryRelService;
    @Autowired
    private ProductAttributeService productAttributeService;
    @Autowired
    private ResPicService resPicService;
    @Autowired
    private BaseProductCountertopsService baseProductCountertopsService;

    /**
     * 系列列表接口
     * @param msgId
     * @param request
     * @return
     */
    @RequestMapping(value = "/getSeriesList")
    @ResponseBody
    public Object getSeriesList(String msgId, HttpServletRequest request){

        //参数验证
        if (StringUtils.isEmpty(msgId)) {
            return new ResponseEnvelope(false,"param is null",msgId);
        }
        //用户验证
        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser == null) {
            return new ResponseEnvelope(false,"user is null",msgId);
        }
//        LoginUser loginUser = new LoginUser();
//        loginUser.setId(48);

        logger.info(CLASS_LOG_PREFIX + "获取用户授权码品牌系列userId = " + loginUser.getId());
        //可能一个用户绑定多个品牌
        List<BaseSeries> brandSeriesIdList = baseSeriesService.getSeriesByUserAuthorizedBrandCode(loginUser.getId());
        logger.info(CLASS_LOG_PREFIX + "获取用户授权码品牌系列完成brandSeriesIdList = " + gson.toJson(brandSeriesIdList));
        int total = 0;
        List<BaseSeries> seriesList = null;
        //1、序列号绑定橱柜品牌查询该品牌系列 2、序列号绑定非橱柜品牌查询所有品牌系列
        if (Lists.isNotEmpty(brandSeriesIdList)) {
            List<Integer> idsList = new ArrayList<>();
            for (BaseSeries baseSeries : brandSeriesIdList) {
                idsList.add(baseSeries.getId());
            }
            logger.info(CLASS_LOG_PREFIX + "获取品牌系列数量参数idsList = " + gson.toJson(idsList));
            total = baseSeriesService.getSeriesCount(idsList,null);
            logger.info(CLASS_LOG_PREFIX + "获取品牌系列数量完成total = " + total);
            if (total > 0) {
                logger.info(CLASS_LOG_PREFIX + "获取品牌系列参数idsList = " + gson.toJson(idsList));
                seriesList = baseSeriesService.getSeriesList(idsList,null);
                logger.info(CLASS_LOG_PREFIX + "获取品牌系列数量seriesList = " + gson.toJson(seriesList));
            }
        } else {
            logger.info(CLASS_LOG_PREFIX + "获取品牌系列参数level = " + BaseSeries.LEVEL_THREE);
            total = baseSeriesService.getSeriesCount(null,BaseSeries.LEVEL_THREE);
            logger.info(CLASS_LOG_PREFIX + "获取品牌系列数量完成total = " + total);
            if (total > 0) {
                logger.info(CLASS_LOG_PREFIX + "获取品牌系列列表参数level = " + BaseSeries.LEVEL_THREE);
                seriesList = baseSeriesService.getSeriesList(null,BaseSeries.LEVEL_THREE);
                logger.info(CLASS_LOG_PREFIX + "获取品牌系列列表完成seriesList = " + gson.toJson(seriesList));
            }
        }
        List<BaseSeriesVO> resultList = new ArrayList<>();
        for (BaseSeries baseSeries : seriesList) {
            BaseSeriesVO baseSeriesVO = BaseSeriesObject.parseToBaseSeriesVOByBaseServies(baseSeries);
            //处理缩略图[sql不好处理格式(web:id,ipad:id;)]
            if (StringUtils.isNotEmpty(baseSeries.getSmallPicInfo())) {
                Map<String,String> map = Utils.readFileDesc(baseSeries.getSmallPicInfo());
                if (StringUtils.isNotBlank(map.get("web"))) {
                    ResPic resPic = resPicService.get(Integer.parseInt(map.get("web")));
                    baseSeriesVO.setPicSmallPath(null == resPic?"":resPic.getPicPath());
                }
            }
            resultList.add(baseSeriesVO);
        }
        logger.info(CLASS_LOG_PREFIX + "获取品牌系列列表resultList = " + gson.toJson(resultList));

        return new ResponseEnvelope<>(total,resultList,msgId);
    }

    /**
     * 匹配系列产品,进行替换
     * @param seriesId 系列ID
     * @param planProductIds 需要匹配的系列产品ID (id1,id2,id3)
     * @param msgId
     * @param request
     * @return
     */
    @RequestMapping(value = "/matchingProductSeries")
    @ResponseBody
    public Object matchingProductSeries(Integer seriesId, String planProductIds, String msgId, HttpServletRequest request){

        //参数验证
        boolean falg = ParamCommonVerification.checkTheParamIsValid(msgId,planProductIds);
        if (!falg) {
            return new ResponseEnvelope(false,"param is null",msgId);
        }
        //用户验证
        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser == null) {
            return new ResponseEnvelope(false,"loginUser is null",msgId);
        }
        //转化为list
        List<String> planProductIdList = Utils.getListFromStr(planProductIds);
        //获取方案所有系列白膜产品
        logger.info(CLASS_LOG_PREFIX + "获取方案系列产品参数planProductIds="+planProductIds);
        List<DesignProductResult> designProductResultList = designPlanProductService.getPlanSeriesProductByPlanProuctIds(planProductIdList);
        logger.info(CLASS_LOG_PREFIX + "获取方案系列产品完成designProductResultList=" + gson.toJson(designProductResultList));

        //方案白膜产品匹配某系列产品
        List<CategoryProductResult> matcheSeriesProductResultList = new ArrayList<>();
        if (Lists.isNotEmpty(designProductResultList)) {
            for (DesignProductResult planProductResult : designProductResultList) {

                logger.info(CLASS_LOG_PREFIX + "获取匹配方案系列产品参数planProductResult="
                        + gson.toJson(planProductResult)+";loginUser="+gson.toJson(loginUser)+";seriesId=" + seriesId);

                CategoryProductResult productInfo = baseProductService.getSeriesProductData(planProductResult,loginUser,seriesId);
                if (productInfo == null)
                    productInfo = new CategoryProductResult();
                logger.info(CLASS_LOG_PREFIX + "获取匹配方案系列产品完成productInfo=" + gson.toJson(productInfo));
                productInfo.setPosName(planProductResult.getPosName());
                productInfo.setFullPaveLength(Utils.getIntValue(planProductResult.getFullPaveLength()));

                //设置产品多维材质
                productCategoryRelService.dealWithTextureInfo(productInfo);

                /** 处理产品 橱柜 信息*/
                baseProductCountertopsService.setCountertopsDetails(productInfo);

                // 软硬装:0:软装;1:硬装
                String smallTypeAtt1 = productInfo.getSmallTypeAtt1();
                productInfo.setRootType(StringUtils.isEmpty(smallTypeAtt1) ? "2" : smallTypeAtt1.trim());
                Map<String, String> map = productAttributeService.getPropertyMapV2(productInfo.getProductId());
                productInfo.setPropertyMap(map);
                logger.info(CLASS_LOG_PREFIX + "获取方案系列产品转换对象result=" + productInfo.toString());
                matcheSeriesProductResultList.add(productInfo);
            }
        } else {
            return new ResponseEnvelope(false,"no series product!",msgId);
        }
        logger.info(CLASS_LOG_PREFIX + "结果matcheSeriesProductResultList"+ gson.toJson(matcheSeriesProductResultList));

        return new ResponseEnvelope<>(true,matcheSeriesProductResultList,msgId);
    }

    /**
     * 匹配把手产品,进行替换
     * @param seriesId 系列ID
     * @param placePointCodes 放置点codes (code1,code2,code3)
     * @param msgId
     * @param request
     * @return
     */
    @RequestMapping(value = "/matchingHandleProducts")
    @ResponseBody
    public Object matchingHandleProducts(Integer seriesId, String placePointCodes, String msgId, HttpServletRequest request){

        //参数验证
        boolean falg = ParamCommonVerification.checkTheParamIsValid(msgId,placePointCodes);
        if (!falg) {
            return new ResponseEnvelope(false,"The param is null",msgId);
        }
        //用户验证
        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser == null) {
            return new ResponseEnvelope(false,"The LoginUser is null",msgId);
        }
        //转化为list
        List<String> placePointCodeList = Utils.getListFromStr(placePointCodes);
        List<Integer> putwawyStateList = baseProductService.getPutawayList(loginUser);
        List<CategoryProductResult> matcheHandleProductResultList = new ArrayList<>();
        if (Lists.isNotEmpty(placePointCodeList)) {
            for (String attributeKey : placePointCodeList) {
                logger.info(CLASS_LOG_PREFIX + "获取匹配把手产品参数attributeKey="
                        + attributeKey+";seriesId=" + seriesId+";putwawyStateList="+gson.toJson(putwawyStateList));
                List<CategoryProductResult> productInfoList = baseProductService.getHandleProductData(putwawyStateList,seriesId,null,attributeKey);
                CategoryProductResult productInfo = null;
                if (productInfoList != null && productInfoList.size() > 0) {
                    productInfo = productInfoList.get(0);
                }else{
                    productInfo = new CategoryProductResult();
                }
                //当前放置点返回给前端
                productInfo.setPlacePointCode(attributeKey);
                //设置产品多维材质
                productCategoryRelService.dealWithTextureInfo(productInfo);
                // 软硬装:0:软装;1:硬装
                String smallTypeAtt1 = productInfo.getSmallTypeAtt1();
                productInfo.setRootType(StringUtils.isEmpty(smallTypeAtt1) ? "2" : smallTypeAtt1.trim());
                Map<String, String> map = productAttributeService.getPropertyMapV2(productInfo.getProductId());
                productInfo.setPropertyMap(map);
                logger.info(CLASS_LOG_PREFIX + "获取方案把手产品转换对象result=" + productInfo.toString());
                matcheHandleProductResultList.add(productInfo);
            }
        } else {
            return new ResponseEnvelope(false,"no placePointCodes product!",msgId);
        }
        logger.info(CLASS_LOG_PREFIX + "结果matcheHandleProductResultList"+ gson.toJson(matcheHandleProductResultList));

        return new ResponseEnvelope<>(true,matcheHandleProductResultList,msgId);
    }

}
