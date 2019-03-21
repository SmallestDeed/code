package com.sandu.service.base.dao;

import com.sandu.api.base.input.GroupPurchaseActivitySearch;
import com.sandu.api.base.model.*;
import com.sandu.api.base.output.BaseCompanyVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version V1.0
 * @Title: BaseCompanyMapper.java
 * @Package com.sandu.product.dao
 * @Description:产品模块-企业表Mapper
 * @createAuthor pandajun
 * @CreateDate 2015-06-15 17:01:45
 */
@Repository
public interface BaseCompanyDao {

    BaseCompany selectByPrimaryKey(Integer id);

    List<BaseCompany> selectList(BaseCompany baseCompany);

    /**
     * 根据品牌ID查询公司信息
     *
     * @param brandId 品牌ID
     * @return
     */
    BaseCompany getCompanyByBrandId(@Param("brandId") Integer brandId);

    List<BaseCompany> selectListByCompanyAPPId(BaseCompany queryBaseCompany);

    MiniProgramDashboard getMiniProgramDashboardByAppId(String appId);


    CompanyBrandDesc getCompanyBrandDesc(Long companyId);

    RichContext getRichContext(RichContext context);

    CompanyMiniProgramConfig getCompanyMiniProgramConfigByAppId(String appId);

    List<MiniProgramActBargain> getMiniProgramActBargain(String appId);

    List<GroupPurchaseActivity> getGroupPurchaseActivityList(GroupPurchaseActivitySearch search);

    List<GoodsSpuPriceInfo> getGoodsSpuPriceInfoList(@Param("spuIds") List<Long> spuIds);

    List<CompanyMiniProgramConfig> getMiniProgramConfigByCompanyId(@Param("companyId") Integer companyId);

    List<Integer> getAllDealerCompanyId(@Param("companyId") Integer companyId);

    List<BaseCompanyVo> getAllDealerCompany(@Param("companyId") Integer companyId);
}
