package com.sandu.product.dao;

import java.util.List;

import com.sandu.product.model.BaseCompany;
import com.sandu.product.model.input.BaseCompanySearch;
import com.sandu.product.model.output.BaseCompanyVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.ws.rs.ext.ParamConverter;

/**
 * @Title: BaseCompanyMapper.java
 * @Package com.sandu.product.dao
 * @Description:产品模块-企业表Mapper
 */
@Repository
public interface BaseCompanyMapper {
    int insertSelective(BaseCompany record);

    int updateByPrimaryKeySelective(BaseCompany record);

    int deleteByPrimaryKey(Integer id);

    BaseCompany selectByPrimaryKey(Integer id);
//
//    int selectCount(BaseCompanySearch baseCompanySearch);
//
//	List<BaseCompany> selectPaginatedList(
//            BaseCompanySearch baseCompanySearch);
//
    List<BaseCompany> selectList(BaseCompany baseCompany);

//	List<Integer> getCompanyIdListDifferentIndustry(@Param("categoryIdList") List<String> categoryIdList);

    List<BaseCompany> selectCompanyIdByUserId(Integer userId);

    List<BaseCompany> selectDealerCompanyBuUserId(Integer userId);

    List<BaseCompanyVo> selectBaseCompanyVoList(BaseCompanySearch baseCompanySearch);

}
