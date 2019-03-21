package com.sandu.product.dao;

import com.sandu.goods.output.BaseCompanyMiniProgramConfig;
import com.sandu.goods.output.CompanyIntroduceVO;
import com.sandu.product.model.BaseCompany;
import com.sandu.product.model.search.BaseCompanySearch;
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
public interface BaseCompanyMapper {
    int insertSelective(BaseCompany record);

    int updateByPrimaryKeySelective(BaseCompany record);

    int deleteByPrimaryKey(Integer id);

    BaseCompany selectByPrimaryKey(Integer id);

    int selectCount(BaseCompanySearch baseCompanySearch);

    List<BaseCompany> selectPaginatedList(BaseCompanySearch baseCompanySearch);

    List<BaseCompany> selectList(BaseCompany baseCompany);

    /**
     * 根据品牌ID查询公司信息
     * @param brandId 品牌ID
     * @return
     */
    BaseCompany getCompanyByBrandId(@Param("brandId") Integer brandId);

    List<BaseCompany> selectListByCompanyAPPId(BaseCompany queryBaseCompany);

    CompanyIntroduceVO getIntroduce(Integer id);

    List<BaseCompanyMiniProgramConfig> queryBaseCompanyMiniProgramConfigMetaData();

    BaseCompanyMiniProgramConfig queryBaseCompanyMiniProgramConfigMetaDataByAppId(@Param("appId")String appId);
}
