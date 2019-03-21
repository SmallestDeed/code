package com.nork.product.service.impl;

import com.nork.common.util.Utils;
import com.nork.design.model.DesignPlanProductResult;
import com.nork.design.model.DesignPlanRecommendedProductResult;
import com.nork.design.model.ProductCostDetail;
import com.nork.product.common.PlatformConstants;
import com.nork.product.dao.BaseProductMapper;
import com.nork.product.model.*;
import com.nork.product.model.result.SearchProductGroupResult;
import com.nork.product.service.BaseProductStyleService;
import com.nork.product.service.ProductPlatformService;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nork.product.dao.GroupProductMapper;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author chenqiang
 * @Description
 * @Date 2018/5/22 0022 14:02
 * @Modified By
 */
@Service("productPlatformService")
@Transactional
public class ProductPlatformServiceImpl implements ProductPlatformService{

    @Autowired
    private BaseProductMapper baseProductMapper;

    @Autowired
    private GroupProductMapper groupProductMapper;

    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Autowired
    private BaseProductStyleService baseProductStyleService;

    @Autowired
    private ResPicService resPicService;

    /**
     * 通过产品id获取平台个性数据信息
     * @author chenqiang
     * @param productId
     * @param platformType
     * @return
     * @date 2018/5/22 0022 11:25
     */
    public ProductPlatform getProductPlatformInfo(int productId,String platformType){
        //获取单品产品基础平台个性信息
        ProductPlatform productPlatform = baseProductMapper.selectProductPlatformInfo(productId,platformType);
        return null == productPlatform ? new ProductPlatform() : productPlatform;
    }

    /**
     * 获取组合产品平台个性化数据
     * @author chenqiang
     * @param groupId
     * @return
     */
    public ProductPlatform getGroupProductPlatformInfo(int groupId, String platformType){
        //获取组合产品基础平台个性信息
        ProductPlatform productPlatform = baseProductMapper.selectGroupProductPlatformInfo(groupId,platformType);
        return null == productPlatform ? new ProductPlatform() : productPlatform;
    }

    /**
     * 获取产品价格单位名称
     * @param advicePrice 产品建议售价
     * @return
     */
    public String getSalePriceValueName(String advicePrice){
        SysDictionary dictionary = null;
        //产品价格单位名称
        if(StringUtils.isNotEmpty(advicePrice)){
            dictionary = sysDictionaryService.getSysDictionaryByValue("productUnitPrice", new Integer(advicePrice));
        }
        return dictionary==null?"":dictionary.getName();
    }


    /**
     * 获取组合产品价格总价
     * @param groupId 组合产品
     * @return
     */
    public BigDecimal getGroupSalePrice(Integer groupId){
        BigDecimal salePrice = new BigDecimal("0");

        /** 获取组合产品平台个性化信息 */
        ProductPlatform productPlatform = this.getGroupProductPlatformInfo(groupId, PlatformConstants.PC_2B);

        //判断是否存在该组合的售价
        if (null != productPlatform.getSalePrice() && 0 != productPlatform.getSalePrice().compareTo(new BigDecimal(0))){
            salePrice = productPlatform.getSalePrice();
        }else{
            //不存在获取组合产品售价之和
            productPlatform =  groupProductMapper.getGroupProductSalePrice(groupId,PlatformConstants.PC_2B);
            if (null != productPlatform && null != productPlatform.getSalePriceSum()) {
                salePrice = productPlatform.getSalePriceSum();
            }
        }

        return salePrice;
    }

    /**
     * 获取产品图片、缩略图列表
     * @param picIds
     * @param picId
     * @param productId
     * @return 返回产品平台个性信息实体
     */
    public ProductPlatform getProductPicList(String picIds,Integer picId,Integer productId){
        ProductPlatform productPlatform = new ProductPlatform();
        List<String> smallPiclist = new ArrayList<>();
        List<ResPic> thumbnailList = new ArrayList<>();

        //判断是否获取平台图片信息
        if(StringUtils.isEmpty(picIds)){

            if(null != productId){

                BaseProduct baseProduct = baseProductMapper.selectByPrimaryKey(productId);
                if(null != baseProduct)
                    picIds = baseProduct.getPicIds();

            }
        }

        //判断图片ids是否存在
        if(StringUtils.isNotEmpty(picIds)){
            List<ResPic> smallPicList = new ArrayList<>();
            String[] picIdsStr = picIds.split(",");
            List<Integer> picIdsList = new ArrayList<>();


            //将主缩略图排至第一位
            if(null != picId){
                for (String s : picIdsStr) {
                    if(StringUtils.isNotEmpty(s )){
                        if(s.equals(picId+"")){
                            picIdsList.add(0,Integer.parseInt(s));
                        }else{
                            picIdsList.add(Integer.parseInt(s));
                        }
                    }
                }
            }

            for (Integer picid : picIdsList) {
                //获取信息
                ResPic resPic = resPicService.get(picid);
                smallPicList.add(resPic);
            }


            //循环产品图片集合
            for (ResPic resPic : smallPicList) {
                //写入small集合路径
                smallPiclist.add(resPic.getPicPath());

                //获取缩略图列表
                if (StringUtils.isNotBlank(resPic.getSmallPicInfo())) {
                    Map<String, String> map = Utils.getMapFromStr(resPic.getSmallPicInfo());
                    if (StringUtils.isNotBlank(map.get("ipad"))) {
                        ResPic ipadPic = resPicService.get(Utils.getIntValue(map.get("ipad")));
                        resPic.setIpadThumbnailPath(ipadPic == null ? "" : ipadPic.getPicPath());
                    }
                    if (StringUtils.isNotBlank(map.get("web"))) {
                        ResPic webPic = resPicService.get(Utils.getIntValue(map.get("web")));
                        resPic.setWebThumbnailPath(webPic == null ? "" : webPic.getPicPath());
                    }
                } else {
                    // 如果没有缩略图就显示原图
                    resPic.setIpadThumbnailPath(resPic == null ? "" : resPic.getPicPath());
                    resPic.setWebThumbnailPath(resPic == null ? "" : resPic.getPicPath());
                }

                thumbnailList.add(resPic);
            }

        }

        //赋值
        productPlatform.setSmallPiclist(smallPiclist);
        productPlatform.setThumbnailList(thumbnailList);

        return productPlatform;
    }

    /**
     * 获取产品风格名与列表
     * @param styleIds 平台风格ids
     * @param productId 单品产品id
     * @param groupId 组合产品id
     * @return 返回产品平台个性信息实体
     */
    public ProductPlatform getProductStyleInfo(String styleIds,Integer productId,Integer groupId){
        ProductPlatform productPlatform = new ProductPlatform();
        String productStyleIdInfo = null;
        List<Integer> idList = new ArrayList<>();
        List<String> productStyleInfoList = new ArrayList<>();
        StringBuffer productStyleSB = new StringBuffer();

        if(StringUtils.isNotEmpty(styleIds)){
            String[] styleIdsStr = styleIds.split(",");
            for (String s : styleIdsStr) {
                if(StringUtils.isNotEmpty(s)) {
                    idList.add(Integer.parseInt(s));
                }
            }
            productStyleInfoList = baseProductStyleService.getNameByIdList(idList);

        }else if(null != productId){

            BaseProduct baseProduct = baseProductMapper.selectByPrimaryKey(productId);
            productStyleIdInfo = baseProduct.getProductStyleIdInfo();

        }else if(null != groupId){

            GroupProduct groupProduct = groupProductMapper.selectByPrimaryKey(groupId);
            productStyleIdInfo = groupProduct.getProductStyleIdInfo();

        }

        //判断是否取基础信息
        if(StringUtils.isNotEmpty(productStyleIdInfo)){
            if(productStyleIdInfo.contains("isLeaf_1")){
                JSONObject styleJson = JSONObject.fromObject(productStyleIdInfo);
                productStyleInfoList = baseProductStyleService.getProductStyleInfo(styleJson);
            }else{
                List<String> idStrList = Arrays.asList(productStyleIdInfo.split(","));
                for (String s : idStrList) {
                    idList.add(Integer.parseInt(s));
                }
                productStyleInfoList = baseProductStyleService.getNameByIdList(idList);
            }
        }

        //组装风格信息
        for(String str : productStyleInfoList){
            productStyleSB.append(str + "  ");
        }

        //赋值
        productPlatform.setProductStyle(productStyleSB.toString());
        productPlatform.setProductStyleInfoList(productStyleInfoList);

        return productPlatform;
    }

//    private String  salePrice;//产品在此平台售价
//    private String  description;//产品在此平台描述
//    private String  advicePrice;//产品建议售价
//    private String  styleId;//产品风格
//    private Integer picId;//产品默认图片id
//    private String  picPath;//产品默认图片路径
//    private String  picIds;//产品所有图片ID

    /**
     * 草图方案/效果图方案 产品列表
     * @param planProduct
     * @return
     */
    public void getDesignPlanProductResultInfo(int productId,String platformType,DesignPlanProductResult planProduct){
        //调用方法获取产品平台个性数据信息
        ProductPlatform productPlatform = this.getProductPlatformInfo(productId,platformType);

        //赋值个性数据信息
        if (productPlatform != null) {
            BigDecimal salePrice = productPlatform.getSalePrice();
            BigDecimal advicePrice = productPlatform.getAdvicePrice();
            planProduct.setPicId(productPlatform.getPicId());
            planProduct.setPicPath(productPlatform.getPicPath());
            planProduct.setSalePrice(String.valueOf(salePrice.doubleValue()));
            planProduct.setProStyleValue(productPlatform.getStyleId());
            planProduct.setSalePriceValue(salePrice != null ? salePrice.intValue() : 0);
            planProduct.setSalePriceValueName(this.getSalePriceValueName(advicePrice != null ? String.valueOf(advicePrice.intValue()) : ""));
        }
    }

    /**
     * 推荐方案 产品列表
     * @param planProduct
     * @return DesignPlanRecommendedProductResult
     */
    public void getDesignPlanRecommendedProductResultInfo(int productId,String platformType,DesignPlanRecommendedProductResult planProduct){
        //调用方法获取产品平台个性数据信息
        ProductPlatform productPlatform = this.getProductPlatformInfo(productId,platformType);

        //赋值个性数据信息
        if (productPlatform != null) {
            BigDecimal salePrice = productPlatform.getSalePrice() == null ? new BigDecimal("0") : productPlatform.getSalePrice();
            BigDecimal advicePrice = productPlatform.getAdvicePrice() == null ? new BigDecimal("0") : productPlatform.getAdvicePrice();;
            planProduct.setPicId(productPlatform.getPicId());
            planProduct.setPicPath(productPlatform.getPicPath());
            planProduct.setSalePrice(String.valueOf(salePrice.doubleValue()));
            planProduct.setProStyleValue(productPlatform.getStyleId());
            planProduct.setSalePriceValue(salePrice.intValue());
            planProduct.setSalePriceValueName(this.getSalePriceValueName(String.valueOf(advicePrice.intValue())));
        }
    }

    /**
     * 单品产品详情
     * @param baseProduct
     * @return BaseProduct
     */
    public void getBaseProductInfo(int productId,String platformType,BaseProduct baseProduct){
        //调用方法获取产品平台个性数据信息
        ProductPlatform productPlatform = this.getProductPlatformInfo(productId,platformType);

        //赋值个性数据信息
        if (productPlatform != null) {
            BigDecimal salePrice = productPlatform.getSalePrice() == null ? new BigDecimal("0") : productPlatform.getSalePrice();
            BigDecimal advicePrice = productPlatform.getAdvicePrice() == null ? new BigDecimal("0") : productPlatform.getAdvicePrice();;
            baseProduct.setPicId(productPlatform.getPicId());
            baseProduct.setPicPath(productPlatform.getPicPath());
            baseProduct.setPicIds(productPlatform.getPicIds());
            baseProduct.setSalePrice(salePrice.doubleValue());
            baseProduct.setSalePriceValue(advicePrice.intValue());
            baseProduct.setSalePriceValueName(this.getSalePriceValueName(String.valueOf(advicePrice.intValue())));
            baseProduct.setProductStyleIdInfo(productPlatform.getStyleId());
            baseProduct.setProductDesc(productPlatform.getDescription());
        }

        //图片列表信息
        ProductPlatform resPicProduct = this.getProductPicList(productPlatform.getPicIds(),productPlatform.getPicId(),null);
        baseProduct.setSmallPiclist(resPicProduct.getSmallPiclist());
        baseProduct.setThumbnailList(resPicProduct.getThumbnailList());

        //风格信息
        ProductPlatform styleProductPlatform = this.getProductStyleInfo(productPlatform.getStyleId(),null,null);
        baseProduct.setProductStyle(styleProductPlatform.getProductStyle());
        baseProduct.setProductStyleInfoList(styleProductPlatform.getProductStyleInfoList());

    }

    /**
     * 组合产品详情
     * @param groupProductResult
     * @return BaseProduct
     */
    public void getGroupProductResultInfo(int groupId,String platformType,SearchProductGroupResult groupProductResult){
        //调用方法获取组合产品平台个性数据信息
        ProductPlatform productPlatform = this.getGroupProductPlatformInfo(groupId,platformType);

        //赋值个性数据信息
        groupProductResult.setPicPath(productPlatform.getPicPath());
        groupProductResult.setProductStyleIdInfo(productPlatform.getStyleId());
        groupProductResult.setRemark(productPlatform.getDescription());

        //基础风格
        ProductPlatform styleProduct = this.getProductStyleInfo(null,null,groupId);
        groupProductResult.setStyleName(styleProduct.getProductStyle());

        //平台个性风格信息
        ProductPlatform styleProductPlatform = this.getProductStyleInfo(productPlatform.getStyleId(),null,groupId);
        groupProductResult.setProductStyle(styleProductPlatform.getProductStyle());
        groupProductResult.setProductStyleInfoList(styleProductPlatform.getProductStyleInfoList());

        //组合总价
        BigDecimal totalPrice = this.getGroupSalePrice(groupId);
        groupProductResult.setGroupPrice(totalPrice != null ? totalPrice.doubleValue() : 0);
        groupProductResult.setSalePrice(totalPrice != null ? totalPrice.doubleValue() : 0);

    }

    /**
     * 方案已删除产品列表详情
     * @param productId
     * @param platformType
     * @param baseProduct
     */
    public void getDeleteBaseProductInfo(int productId,String platformType,BaseProduct baseProduct){
        //调用方法获取产品平台个性数据信息
        ProductPlatform productPlatform = this.getProductPlatformInfo(productId,platformType);

        //赋值个性数据信息
        if (productPlatform != null) {
            BigDecimal salePrice = productPlatform.getSalePrice() == null ? new BigDecimal("0") : productPlatform.getSalePrice();
            BigDecimal advicePrice = productPlatform.getAdvicePrice() == null ? new BigDecimal("0") : productPlatform.getAdvicePrice();;
            baseProduct.setPicId(productPlatform.getPicId());
            baseProduct.setPicPath(productPlatform.getPicPath());
            baseProduct.setPicIds(productPlatform.getPicIds());
            baseProduct.setSalePrice(salePrice.doubleValue());
            baseProduct.setSalePriceValue(advicePrice.intValue());
            baseProduct.setSalePriceValueName(this.getSalePriceValueName(String.valueOf(advicePrice.intValue())));
            baseProduct.setProductStyleIdInfo(productPlatform.getStyleId());
            baseProduct.setRemark(productPlatform.getDescription());
        }
    }

    /**
     * 方案已使用产品列表
     * @param productId
     * @param platformType
     * @param categoryProductResult
     */
    public void getUseCategoryProductResultInfo(int productId,String platformType,CategoryProductResult categoryProductResult){
        //调用方法获取产品平台个性数据信息
        ProductPlatform productPlatform = this.getProductPlatformInfo(productId,platformType);

        //赋值个性数据信息
        if (productPlatform != null) {
            BigDecimal salePrice = productPlatform.getSalePrice() == null ? new BigDecimal("0") : productPlatform.getSalePrice();
            BigDecimal advicePrice = productPlatform.getAdvicePrice() == null ? new BigDecimal("0") : productPlatform.getAdvicePrice();;
            categoryProductResult.setPicPath(productPlatform.getPicPath());
            categoryProductResult.setSalePrice(String.valueOf(salePrice.doubleValue()));
            categoryProductResult.setSalePriceValueName(this.getSalePriceValueName(String.valueOf(advicePrice.intValue())));
        }
    }

    /**
     * 方案已使用产品组合列表
     * @param groupId
     * @param platformType
     * @param searchProductGroupResult
     */
    public void getUseSearchProductGroupResultInfo(int groupId,String platformType,SearchProductGroupResult searchProductGroupResult){
        //调用方法获取组合产品平台个性数据信息
        ProductPlatform productPlatform = this.getGroupProductPlatformInfo(groupId,platformType);

        //赋值个性数据信息
        searchProductGroupResult.setPicPath(productPlatform != null ? productPlatform.getPicPath() : "");

        //组合总价
        BigDecimal totalPrice = this.getGroupSalePrice(groupId);
        searchProductGroupResult.setGroupPrice(totalPrice != null ? totalPrice.doubleValue() : 0);
        searchProductGroupResult.setSalePrice(totalPrice != null ? totalPrice.doubleValue() : 0);
    }

    /**
     * 收藏 单品列表
     * @param productId
     * @param platformType
     * @param userProductsConllect
     */
    public void getUserProductsConllectInfo(int productId,String platformType,UserProductsConllect userProductsConllect){
        //调用方法获取产品平台个性数据信息
        ProductPlatform productPlatform = this.getProductPlatformInfo(productId,platformType);

        //赋值个性数据信息
        if (productPlatform != null) {
            BigDecimal salePrice = productPlatform.getSalePrice();
            BigDecimal advicePrice = productPlatform.getAdvicePrice();
            userProductsConllect.setPicId(productPlatform.getPicId());
            userProductsConllect.setProductPath(productPlatform.getPicPath());
            userProductsConllect.setSalePrice(salePrice != null ? salePrice.doubleValue() : 0);
            userProductsConllect.setSalePriceValueName(this.getSalePriceValueName(advicePrice != null ? String.valueOf(advicePrice.intValue()) : "0"));
        }
    }

    /**
     * 收藏 组合列表
     * @param groupId
     * @param platformType
     * @param groupProductCollect 组合对象
     */
    public void getGroupProductCollectInfo(int groupId,String platformType,GroupProductCollect groupProductCollect){
        //调用方法获取平台个性数据信息
        ProductPlatform productPlatform = this.getGroupProductPlatformInfo(groupId,platformType);
        groupProductCollect.setProductPath(null != productPlatform ? productPlatform.getPicPath() : "");

        //组合总价
        BigDecimal salePrice = this.getGroupSalePrice(groupProductCollect.getGroupId());
        groupProductCollect.setSalePrice(salePrice != null ? salePrice.doubleValue() : 0);
    }

    /**
     * 结算清单
     * @param productId
     * @param platformType
     * @param productCostDetail
     */
    public void getProductCostDetailInfo(int productId,String platformType,ProductCostDetail productCostDetail){
        //调用方法获取产品平台个性数据信息
        ProductPlatform productPlatform = this.getProductPlatformInfo(productId,platformType);

        //赋值个性数据信息
        if (null != productPlatform) {
            BigDecimal salePrice = productPlatform.getSalePrice();
            Integer count = productCostDetail.getCount();
            productCostDetail.setProductPicPath(productPlatform.getPicPath());
            productCostDetail.setProductOriginalPicPath(productPlatform.getPicPath());
            productCostDetail.setUnitPrice(salePrice);
            productCostDetail.setTotalPrice(null != count ? salePrice.multiply(new BigDecimal(count)) : salePrice);
            productCostDetail.setProductDesc(productPlatform.getDescription());
        }

        //讲 地面 墙面 天花的 价格 从总价中  去除
        BaseProduct baseProduct = baseProductMapper.selectByPrimaryKey(productId);
        if("1".equals(baseProduct.getProductTypeValue())||"2".equals(baseProduct.getProductTypeValue())||"3".equals(baseProduct.getProductTypeValue())){
            if(baseProduct!=null){
                productCostDetail.setTotalPrice(null);
                productCostDetail.setUnitPrice(null);
            }
        }
    }
//    /** 产品ID **/
//    private int productId;
//    /** 产品名称 **/
//    private String productName;
//    /** 产品图片路径 **/
//    private String productPicPath;
//    /** 产品原图路径 **/
//    private String productOriginalPicPath;
//    /** 品牌名称 **/
//    private String brandName;
//    /** 产品单价 **/
//    private BigDecimal unitPrice;
//    /** 产品数量 **/
//    private Integer count;
//    /** 总价 **/
//    private BigDecimal totalPrice;
//    /** 产品型号 **/
//    private String productModelNumber;
//    /** 产品规格 **/
//    private String productSpec;
//    /** 产品描述 **/
//    private String productDesc;
//    /** 产品价格单位 **/
//    private String productUnit;
//    /** 产品编码 */
//    private String productCode;
//    /** 产品挂节点信息 */
//    private String posIndexPath;
}
