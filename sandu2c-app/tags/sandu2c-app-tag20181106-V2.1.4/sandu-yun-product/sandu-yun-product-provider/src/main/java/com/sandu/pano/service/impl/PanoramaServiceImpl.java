package com.sandu.pano.service.impl;


import com.sandu.design.model.DesignPlanProduct;
import com.sandu.design.model.ProductsCostType;
import com.sandu.designplan.service.*;
import com.sandu.designplan.service.DesignPlanProductService.costListEnum;
import com.sandu.pano.model.scene.*;
import com.sandu.pano.service.PanoramaService;
import com.sandu.user.model.UserSO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
@Service("panoramaService")
public class PanoramaServiceImpl implements PanoramaService{
	private static Logger logger = Logger.getLogger(PanoramaServiceImpl.class);


    @Autowired
    private DesignPlanProductService designPlanProductService;


    /***
     * 获取设计方案的产品费用清单
     * @return
     */	
    @Override
    public List<ProductsCostType> getProductsCost(PanoramaVo panoramaVo,UserSO userSo) {
        Integer designPlanRecommendedId = panoramaVo.getDesignPlanRecommendedId();
        Integer designPlanRenderSceneId = panoramaVo.getDesignPlanRenderSceneId();
        List<ProductsCostType> list = null;
        if (designPlanRecommendedId == null && designPlanRenderSceneId == null) {
            return null;
        }
        
     
        DesignPlanProduct designPlanProduct = new DesignPlanProduct();
        //通过品牌过滤
//        if(panoramaVo.getBrandList()!=null&&panoramaVo.getBrandList().size()>0) {
//        	designPlanProduct.setBrandList(panoramaVo.getBrandList());
//        }
//        designPlanProduct.setOwnBrandIdList(panoramaVo.getOwnBrandIdList());
        //通过平台过滤
        designPlanProduct.setPlatformId(panoramaVo.getPlatformId());
//        designPlanProduct.setCompanyId(panoramaVo.getCompanyId());
        //大小分类
        designPlanProduct.setSysDictionaryBOList(panoramaVo.getSysDictionaryBOList());

        //通过推荐方案ID查询产品清单
        if (designPlanRecommendedId != null && designPlanRecommendedId.intValue() > 0) {
            designPlanProduct.setPlanId(designPlanRecommendedId);
            list = designPlanProductService.costList(designPlanProduct, costListEnum.designPlanRecommended, userSo);
        //通过副本设计ID查询产品清单
        } else if (designPlanRenderSceneId != null && designPlanRenderSceneId > 0) {
            designPlanProduct.setPlanId(designPlanRenderSceneId);
            list = designPlanProductService.costList(designPlanProduct, costListEnum.designPlanRenderScene,userSo);
        } 

        return list;
    }
}
