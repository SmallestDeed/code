package com.nork.pano.service;

import com.nork.design.model.ProductsCostType;
import com.nork.pano.model.input.SceneDataSearch;
import com.nork.pano.model.output.RoamSceneDataVo;
import com.nork.pano.model.output.UnionStoreSingleDataVo;
import com.nork.pano.model.scene.*;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
public interface PanoramaService {

    /**
     * 单场景加载（主函数）
     * @param sysCode
     * @return
     */
    SingleSceneVo vr720Single(String sysCode);
    
    
    SingleSceneVo vr720SingleMobileForAuto(String sysCode);

    /**
     * 720漫游场景加载（主函数）
     * @param sysCode
     * @return
     */
    RoamSceneVo vr720Roam(String sysCode);

    /**
     * 组装720页面基本信息
     * 
     * 2017.9.8 update by huangsongbo
     * 由于现在去设计方案信息,不一定是从designPlan中取,渲染图方案从designPlanrenderScene中取,推荐方案从designPlanRecommended中取
     * 所以多加了Integer designPlanRenderSceneId, Integer designPlanRecommendedId这两个参数
     * 
     * @param planId
     * @return
     */
    PanoramaVo assemblyPanoramaVo(Scene scene);
    
    
    /**
     * 组装720页面基本信息
     * @param planId
     * @return
     */
    PanoramaVo assemblyPanoramaVoForAuto(Integer planId);

    /**
     * 通过sysCode得到一个场景
     * @param sysCode
     * @return
     */
    Scene getSceneBySysCode(String sysCode, String type);
    
    /**
     * 移动端
     * @param sysCode
     * @return
     */
    Scene getMobileSceneBySysCodeForAuto(String sysCode);

    /**
     * 获取设计方案产品费用清单
     * 
     * 2017.9.8 update by huangsongbo
     * 消费清单不一定是从设计方案产品列表中取,也可能是从副本产品列表/推荐方案产品列表中取
     * 
     * @param planId
     * @param userId
     * @param userType
     * @return
     */
    /*List<ProductsCostType> getProductsCost(Integer planId, Integer userId, Integer userType);*/
    List<ProductsCostType> getProductsCost(PanoramaVo panoramaVo);

    /**
     * 720组合场景加载（主函数）
     * @param code 组合UUID
     * @return
     */
    GroupSceneVo vr720Group(String code);

    /***
     * 获取设计方案副本的产品费用清单
     * @return
     */
    List<ProductsCostType> getDesignRenderGroupCost(Integer planId, Integer userId, Integer userType);

    /**
     * 组装打组720页面基本信息
     * @param planId
     * @return
     */
    PanoramaVo assemblyGroupPanoramaVo(Integer planId);

    /**
     * 添加一个方法测试移动端的720场景去掉限制大小
     * @param code
     * @return
     */
	SingleSceneVo vr720MobileSingle(String code);
    
	/**
	 * 加载720单点联盟方案场景
	 * @param sysCode
	 * @param releaseId
	 * @return
	 */
	List<SingleSceneVo> vr720UnionStoreSingle(Integer releaseId);
 
	/**
     * 获取同城联盟720单点分享中场景的产品清单
     * @param releaseId
     * @param resPicId
     * @return
     */
	UnionStoreProductsCostVo getUnionStoreProductsCostList(Integer releaseId,Integer resPicId);
	
	/**
     * 获取同城联盟720单点分享中方案切换所需数据
     * @param releaseId
     * @return
     */
    List<UnionStoreSingleSenceInfoVo> getUnionStoreSingleSenceInfoList(Integer releaseId);


    /**
     *  单点720场景返回数据
     * @param search
     * @return
     */
    RoamSceneDataVo vr720SingleSenceData(SceneDataSearch search);

    /**
     * 多点720场景返回数据
     * @param search
     * @return
     */
    RoamSceneDataVo vr720RoamSenceData(SceneDataSearch search);

    /**
     * 720分享组合场景返回数据
     * @param search
     * @return
     */
    RoamSceneDataVo vr720GroupSenceData(SceneDataSearch search);

    /**
     * 同城联盟720分享场景返回数据
     * @param search
     * @return
     */
    UnionStoreSingleDataVo vr720UnionStoreSingleData(SceneDataSearch search);

    /**
     * 根据用户类型和场景信息查找该场景的分享描述文案
     * @param search
     * @param userId
     * @return
     */
    String getCustomizedDescBySceneInfo(SceneDataSearch search,Integer userId);
}
