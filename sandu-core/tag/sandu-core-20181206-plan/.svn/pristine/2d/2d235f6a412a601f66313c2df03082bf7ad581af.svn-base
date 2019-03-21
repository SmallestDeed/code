package com.sandu.service.base.dao;

import com.sandu.api.base.model.BaseCompany;
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
     * @param brandId 品牌ID
     * @return
     */
    BaseCompany getCompanyByBrandId(@Param("brandId") Integer brandId);

    List<BaseCompany> selectListByCompanyAPPId(BaseCompany queryBaseCompany);
}
