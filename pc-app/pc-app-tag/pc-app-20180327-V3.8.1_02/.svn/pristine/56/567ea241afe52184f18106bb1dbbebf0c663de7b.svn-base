package com.nork.product.controller.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.util.Utils;
import com.nork.design.model.AutoCarryBaimoProducts;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.ProductAttribute;
import com.nork.product.model.ProductCategoryRel;
import com.nork.product.service.ProductAttributeService;

/**
 * Created by Administrator on 2017/1/11.
 */
@Controller
@RequestMapping("/{style}/web/product/productCategoryRel_zwj")
public class WebProductCategoryRelControllerZWJ {

    Logger logger = Logger.getLogger(WebProductCategoryRelControllerZWJ.class);

    @Resource
    private ProductAttributeService productAttributeService;
     
    
    
    
    
    
    
    
    /**
     * 根据分类查询产品接口
     *
     * @param request
     * @param productCategoryRel
     * @return Object
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/searchProduct")
    @ResponseBody
    public Object searchProduct(@PathVariable String style, @ModelAttribute("productCategoryRel") ProductCategoryRel productCategoryRel
            , HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "houseType",required = false)String houseType,
                                @RequestParam(value = "designPlanId",required = false)Integer designPlanId,
                                @RequestParam(value = "planProductId",required = false)Integer planProductId,
                                @RequestParam(value = "spaceCommonId",required = false)Integer spaceCommonId,
                                @RequestParam(value = "templateProductId",required = false)String templateProductId,
                                @RequestParam(value = "productTypeValue",required = false)String  productTypeValue,
                                @RequestParam(value = "smallTypeValue",required = false)String  smallTypeValue,
                                @RequestParam(value = "queryType",required = false)String queryType,
                                @RequestParam(value = "productModelNumber",required = false)String productModelNumber) {

    	
        return null;
    }

    /**
     * 获取需要自动携带白模产品得配置信息
     * @return
     */
    public Map<String,AutoCarryBaimoProducts> getAutoCarryBaimoProductConfigMap(){
        Map<String,AutoCarryBaimoProducts> autoCarryMap = new HashMap<>();
        String autoCarryBaimoProducrsConfig = Utils.getPropertyName("app","design.designPlan.autoCarryBaimoProducts","");
        if( StringUtils.isNotBlank(autoCarryBaimoProducrsConfig) ){
            JSONArray autoCarryBaimoProducrsObject = JSONArray.fromObject(autoCarryBaimoProducrsConfig);
            try {
                List<AutoCarryBaimoProducts> autoCarryBaimoProductsConfigs = (List<AutoCarryBaimoProducts>) JSONArray.toCollection(autoCarryBaimoProducrsObject, AutoCarryBaimoProducts.class);
                if( autoCarryBaimoProductsConfigs != null && autoCarryBaimoProductsConfigs.size() > 0 ) {
                    for( AutoCarryBaimoProducts autoCarryBaimoProductsConfig : autoCarryBaimoProductsConfigs ){
                        autoCarryMap.put(autoCarryBaimoProductsConfig.getSmallTypeCode(),autoCarryBaimoProductsConfig);
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
                logger.info("获取自动携带白模产品配置异常！");
            }
        }
        return autoCarryMap;
    }

    /**
     * 获取需要携带的白模产品信息
     * @param autoCarryMap
     * @param productResult
     * @return
     */
    public Map<String,CategoryProductResult> getAutoCarryBaimoProductMap(Map<String,AutoCarryBaimoProducts> autoCarryMap,CategoryProductResult productResult){
        if( autoCarryMap == null ){
            return null;
        }
        if( productResult == null ){
            return null;
        }
        if( productResult.getPropertyMap() == null ){
            return null;
        }
        Map<String,CategoryProductResult> basicModelMap = new HashMap<>();
        Map<String,String> map = productResult.getPropertyMap();
        if( autoCarryMap != null && autoCarryMap.containsKey(productResult.getProductSmallTypeCode()) ){
            logger.info("当前产品分类："+productResult.getProductSmallTypeCode()+"需要自动携带白模信息。");
            // 获取当前产品得配置
            AutoCarryBaimoProducts autoCarryBaimoProducts = autoCarryMap.get(productResult.getProductSmallTypeCode());
            logger.info("当前产品要对比的属性：" + autoCarryBaimoProducts.getContrastAttributeKey());
            if( autoCarryBaimoProducts != null ){
                // 获取需要携带得白模有哪些
                JSONArray baimoProductsConfigArray = autoCarryBaimoProducts.getBaimoProducts();
                logger.info("需要携带的白模：" + baimoProductsConfigArray);
                List<AutoCarryBaimoProducts> baimoProductsConfigs = (List<AutoCarryBaimoProducts>) JSONArray.toCollection(baimoProductsConfigArray,AutoCarryBaimoProducts.class);
                // 根据产品的属性，查询同属性的白模产品
                if( baimoProductsConfigs != null && baimoProductsConfigs.size() > 0 ){
                    ProductAttribute pa1 = null;
                    List<ProductAttribute> tempList = null;
                    // 产品属性(需要对比的属性)
                    JSONArray productPropertyArray = autoCarryBaimoProducts.getContrastAttributeKey();
                    // 白模产品属性（需要对比的属性）
                    JSONArray baimoProductPropertyArray = null;
                    Map<String,String> baimoPropertyMap = new HashMap<>();
                    for( AutoCarryBaimoProducts baimoProductConfig : baimoProductsConfigs ){
                        // 白模产品的属性
                        baimoProductPropertyArray = baimoProductConfig.getContrastAttributeKey();
                        if( baimoProductPropertyArray == null ){
                            continue;
                        }
                        if( baimoProductPropertyArray.size() != productPropertyArray.size() ){
                            continue; 
                        }
                        for( Object object : baimoProductPropertyArray ){
                            String[] objStr = object.toString().split("#");
                            baimoPropertyMap.put(objStr[0],objStr[1]);
                        }
                        // 对比产品和白模产品的每个属性
                        String productPropertyStr = "";
                        String baimoProductPropertyStr = "";
                        int i = 0;
                        Map<String,Object> conditionMap = new HashMap<>();
                        List<String> conditionList = new ArrayList<>();
                        StringBuffer conditionStr = new StringBuffer();
                        for( Object object : productPropertyArray ){
                            productPropertyStr = object.toString();
                            baimoProductPropertyStr = baimoPropertyMap.get(productPropertyStr);
                            if( StringUtils.isNotBlank(baimoProductPropertyStr) ){
                                logger.info("product property map : " + map);
                                logger.info("产品属性key："+productPropertyStr);
                                Integer propValue = map.get(productPropertyStr) != null ? Integer.valueOf(map.get(productPropertyStr)):0;
                                conditionStr.append("pa.attribute_key = '" + baimoProductPropertyStr + "' AND pa.attribute_type_value = '" + baimoProductConfig.getSmallTypeCode() + "' AND pp.prop_value = " + propValue);
                                if( i < productPropertyArray.size() - 1 ){
                                    conditionStr.append(" union all ");
                                }
                            }
                            i++;   
                            conditionList.add(conditionStr.toString());
                        }
                        conditionMap.put("conditionCount",conditionList.size());
                        conditionMap.put("conditionList",conditionList);

                        tempList = productAttributeService.selectIntersectProductAttribute(conditionMap);
                        if( tempList != null && tempList.size() > 0 ) {
                            logger.info("共查到"+tempList.size()+"条满足要求的白模数据！！！");
                            Integer baimoProductId = tempList.get(0).getProductId();
                            CategoryProductResult baimoProducts = null;//assemblyUnityPanProduct(baimoProductId, spaceCommonId, designPlan, loginUser.getId(), request);
                            basicModelMap.put(baimoProductConfig.getSmallTypeCode(), baimoProducts);
                        }
                    }
                    productResult.setBasicModelMap(basicModelMap);
                }
            }
        }
        return basicModelMap;
    }
}
