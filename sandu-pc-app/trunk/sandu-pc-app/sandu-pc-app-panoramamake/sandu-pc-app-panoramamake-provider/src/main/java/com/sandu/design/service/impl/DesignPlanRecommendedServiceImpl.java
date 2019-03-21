package com.sandu.design.service.impl;

import com.sandu.common.util.JsonUtil;
import com.sandu.common.util.StringUtils;
import com.sandu.common.util.Utils;
import com.sandu.design.dao.DesignPlanRecommendedMapper;
import com.sandu.design.model.DesignPlanRecommended;
import com.sandu.design.model.input.DesignPlanRecommendedSearch;
import com.sandu.design.model.output.DesignPlanRecommendedVo;
import com.sandu.design.model.output.DesignPlanStyleVo;
import com.sandu.design.service.DesignPlanRecommendedServiceV2;
import com.sandu.panorama.dao.DesignPlanStoreReleaseDetailsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Slf4j
@Transactional
@Service("designPlanRecommendedServiceV2")
public class DesignPlanRecommendedServiceImpl implements DesignPlanRecommendedServiceV2 {

    @Autowired
    private DesignPlanRecommendedMapper designPlanRecommendedMapper;
    @Autowired
    private DesignPlanStoreReleaseDetailsMapper designPlanStoreReleaseDetailsMapper;

    /**
     * 查询方案数量
     * @param designPlanRecommendedSearch
     * @return
     */
    @Override
    public int selectCount(DesignPlanRecommendedSearch designPlanRecommendedSearch) {
        if( designPlanRecommendedSearch == null || designPlanRecommendedSearch.getUserId() == null ){
            return 0;
        }
        String userType = designPlanStoreReleaseDetailsMapper.getColumnValueFromTableById("sys_user", "user_type", designPlanRecommendedSearch.getUserId());// TEMP
        String companyId = designPlanStoreReleaseDetailsMapper.getColumnValueFromTableById("sys_user", "business_administration_id", designPlanRecommendedSearch.getUserId());// TEMP
        designPlanRecommendedSearch.setCompanyId(Integer.valueOf(companyId));
        // 如果用户类型为经销商则需要去经销商所属企业ID
        if( "3".equals(userType) ){
            companyId = designPlanStoreReleaseDetailsMapper.getColumnValueFromTableById("base_company", "pid", Integer.valueOf(companyId));// TEMP
            designPlanRecommendedSearch.setCompanyId(Integer.valueOf(companyId));
        }
        // 品牌过滤，暂时不加

        designPlanRecommendedSearch.setRecommendedType(DesignPlanRecommended.RecommendedType.RECOMMENDED_TYPE_DECORATE);
        designPlanRecommendedSearch.setReleaseStatus(DesignPlanRecommended.ReleaseStatus.IS_RELEASEING);
        //适用面积过滤
        if(designPlanRecommendedSearch.getPlanId() != null){
            designPlanRecommendedSearch = this.getSpaceCommonInfoByPlanId(designPlanRecommendedSearch);
        }
        Integer count = designPlanRecommendedMapper.selectCount(designPlanRecommendedSearch);
        return count == null ? 0 : count;
    }

    /**
     * 查询方案列表
     * @param designPlanRecommendedSearch
     * @return
     */
    @Override
    public List<DesignPlanRecommendedVo> selectList(DesignPlanRecommendedSearch designPlanRecommendedSearch) {
        if( designPlanRecommendedSearch == null || designPlanRecommendedSearch.getUserId() == null ){
            return null;
        }
        String userType = designPlanStoreReleaseDetailsMapper.getColumnValueFromTableById("sys_user", "user_type", designPlanRecommendedSearch.getUserId());// TEMP
        String companyId = designPlanStoreReleaseDetailsMapper.getColumnValueFromTableById("sys_user", "business_administration_id", designPlanRecommendedSearch.getUserId());// TEMP
        designPlanRecommendedSearch.setCompanyId(Integer.valueOf(companyId));
        // 如果用户类型为经销商则需要去经销商所属企业ID
        if( "3".equals(userType) ){
            companyId = designPlanStoreReleaseDetailsMapper.getColumnValueFromTableById("base_company", "pid", Integer.valueOf(companyId));// TEMP
            designPlanRecommendedSearch.setCompanyId(Integer.valueOf(companyId));
        }
        // 品牌过滤，暂时不加

        designPlanRecommendedSearch.setRecommendedType(DesignPlanRecommended.RecommendedType.RECOMMENDED_TYPE_DECORATE);
        designPlanRecommendedSearch.setReleaseStatus(DesignPlanRecommended.ReleaseStatus.IS_RELEASEING);
        //适用面积过滤
        if(designPlanRecommendedSearch.getPlanId() != null){
            designPlanRecommendedSearch = this.getSpaceCommonInfoByPlanId(designPlanRecommendedSearch);
        }
        List<DesignPlanRecommendedVo> list = designPlanRecommendedMapper.selectList(designPlanRecommendedSearch);

        if( list != null && list.size() > 0 ) {
            for (DesignPlanRecommendedVo recommendedVo : list) {
                String picPath = designPlanRecommendedMapper.getCoverPicPath(recommendedVo.getPlanRecommendedId());// TEMP
                recommendedVo.setPicPath(picPath);
                String targetResourcePath = designPlanRecommendedMapper.getPlanRecommendedRenderPath(recommendedVo.getPlanRecommendedId());// TEMP
                // 空间类型 TEMP
                String spaceType = designPlanRecommendedMapper.getFunctionName(recommendedVo.getPlanRecommendedId(), "design_plan_recommended");
                recommendedVo.setSpaceType(spaceType);
                recommendedVo.setTargetResourcePath(targetResourcePath);
                if (!StringUtils.isEmpty(targetResourcePath) && targetResourcePath.indexOf(".") == -1) {
                    File file = new File(Utils.getAbsolutePath(targetResourcePath, null));
                    // 如果图片资源为目录，则表示为切片资源
                    if (file.exists() && file.isDirectory()) {
                        recommendedVo.setIsShear(DesignPlanRecommendedVo.IsShear.YES);
                    } else {
                        recommendedVo.setIsShear(DesignPlanRecommendedVo.IsShear.DIRNOEXISTS);
                    }
                }

            }
        }


//        return designPlanRecommendedMapper.selectList(designPlanRecommendedSearch); ???不知道为啥返回这个
        return list;
    }

    /**
     * 通过方案类型获取对应的方案风格 TEMP
     * @param designPlanType
     * @return
     */
    @Override
    public List<DesignPlanStyleVo> getPlanStyleList(Integer designPlanType) {
        if( designPlanType == null ){
            return null;
        }
        return designPlanRecommendedMapper.getPlanStyleList(designPlanType);
    }

    /**
     * 根据720分享的uuid，查找该分享副本方案关联空间的面积作为查找推荐方案的过滤条件
     * @param designPlanRecommendedSearch
     * @return
     */
    private DesignPlanRecommendedSearch getSpaceCommonInfoByPlanId(DesignPlanRecommendedSearch designPlanRecommendedSearch){
        if(designPlanRecommendedSearch.getPlanId() == null && designPlanRecommendedSearch.getPlanId() != 0){
            return designPlanRecommendedSearch;
        }
        /**
         * TODO：使用了临时的查询方法,下次改回来
         * 根据720制作详情里的效果图方案Id获取关联空间Id
         */
        String spaceCommonId = designPlanStoreReleaseDetailsMapper.getColumnValueFromTableById("design_plan_render_scene", "space_common_id",designPlanRecommendedSearch.getPlanId());
        if(StringUtils.isBlank(spaceCommonId)){
            return designPlanRecommendedSearch;
        }
        //空间面积
        String spaceAreas = designPlanStoreReleaseDetailsMapper.getColumnValueFromTableById("space_common" , " space_areas",Integer.parseInt(spaceCommonId));
        //房型
        String spaceFunctionId  = designPlanStoreReleaseDetailsMapper.getColumnValueFromTableById("space_common" , "space_function_id",Integer.parseInt(spaceCommonId));
        if(StringUtils.isNotBlank(spaceAreas)){
            designPlanRecommendedSearch.setSpaceAreas(spaceAreas);
        }
        if(StringUtils.isNotBlank(spaceFunctionId)){
            designPlanRecommendedSearch.setSpaceFunctionType(Integer.parseInt(spaceFunctionId));
        }
        return designPlanRecommendedSearch;
    }
}
