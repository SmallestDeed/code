package com.sandu.search.service.metadata;

import com.sandu.search.entity.designplan.po.DesignPlanProductPo;
import com.sandu.search.entity.elasticsearch.po.*;
import com.sandu.search.entity.elasticsearch.po.house.HouseLivingPo;
import com.sandu.search.entity.elasticsearch.po.metadate.*;
import com.sandu.search.exception.MetaDataException;

import java.util.List;

/**
 * 元数据服务
 *
 * @date 20171213
 * @auth pengxuangang
 */
public interface MetaDataService {

    /**
     * 查询产品分类元数据
     *
     * @return
     */
    List<ProductCategoryPo> queryProductCategoryMetaData() throws MetaDataException;

    /**
     * 查询产品风格元数据
     *
     * @return
     */
    List<ProductStylePo> queryProductStyleMetaData() throws MetaDataException;

    /**
     * 查询产品材质元数据
     *
     * @return
     */
    List<ProductTexturePo> queryProductTextureMetaData() throws MetaDataException;

    /**
     * 查询品牌元数据
     *
     * @return
     */
    List<BrandPo> queryBrandMetaData() throws MetaDataException;

    /**
     * 查询产品分类关联信息
     *
     * @return
     */
    List<ProductCategoryRelPo> queryProductCategoryRelMetaData() throws MetaDataException;

    /**
     * 查询图片资源元数据
     *
     * @return
     */
    List<ResPicPo> queryResPicMetaData() throws MetaDataException;

    /**
     * 根据ID查询图片资源元数据
     *
     * @return
     */
    ResPicPo getResPicMetaDataById(int id) throws MetaDataException;

    /**
     * 查询产品组合关联元数据
     *
     * @return
     */
    List<ProductGroupRelPo> queryProductGroupRelMetaData() throws MetaDataException;

    /**
     * 查询产品组合元数据
     *
     * @return
     */
    List<ProductGroupPo> queryProductGroupMetaData() throws MetaDataException;

    /**
     * 查询草稿设计方案产品元数据
     *
     * @return
     */
    List<DesignPlanProductPo> queryTempDesignPlanProductMetaData() throws MetaDataException;

    /**
     * 查询推荐设计方案产品元数据
     *
     * @return
     */
    List<DesignPlanProductPo> queryRecommendDesignPlanProductMetaData() throws MetaDataException;

    /**
     * 查询自定义设计方案产品元数据
     *
     * @return
     */
    List<DesignPlanProductPo> queryDiyDesignPlanProductMetaData() throws MetaDataException;

    /**
     * 查询系统字典元数据
     *
     * @return
     * @throws MetaDataException
     */
    List<SystemDictionaryPo> querySystemDictionaryMetaData() throws MetaDataException;

    /**
     * 查询户型小区元数据
     *
     * @return
     */
    List<HouseLivingPo> queryHouseLivingMetaData() throws MetaDataException;

    /**
     * 查询区域元数据
     *
     * @return
     */
    List<AreaPo> queryAreaMetaData() throws MetaDataException;

    /**
     * 查询公司元数据
     *
     * @return
     */
    List<CompanyPo> queryCompanyMetaData() throws MetaDataException;

    /**
     * 查询公司分类关联元数据
     *
     * @return
     */
    List<CompanyCategoryRelPo> queryCompanyCategoryRelMetaData() throws MetaDataException;

    /**
     * 查询设计方案样板房元数据
     *
     * @return
     * @throws MetaDataException
     */
    List<DesignTemplatePo> queryDesignTemplatePoMetaData() throws MetaDataException;

    /**
     * 查询所有平台产品数据(平台数据过滤)
     *
     * @return
     */
    List<ProductPlatformRelPo> queryAllPlatformProductMetaData() throws MetaDataException;

    /**
     * 查询产品属性元信息
     *
     * @return
     */
    List<ProductAttributePo> queryProductAttrMetaData() throws MetaDataException;

    /**
     * 查询产品使用次数统计信息
     *
     * @return
     */
    List<ProductUsagePo> queryProductUsageCountSatatistics() throws MetaDataException;

    /**
     * 查询联盟品牌元数据
     *
     * @return
     */
    List<UnionBrandPo> queryUnionBrandMetaData() throws MetaDataException;

    /**
     * 查询渲染图片资源元数据
     *
     * @return
     */
    List<ResPicPo> queryRenderPicMetaData() throws MetaDataException;

    /**
     * 查询设计方案品牌元数据
     *
     * @return
     */
    List<DesignPlanBrandPo> queryDesignPlanBrandMetaData() throws MetaDataException;

    /**
     * 查询空间元数据
     *
     * @return
     */
    List<SpaceCommonPo> querySpaceCommonMetaData() throws MetaDataException;
}
