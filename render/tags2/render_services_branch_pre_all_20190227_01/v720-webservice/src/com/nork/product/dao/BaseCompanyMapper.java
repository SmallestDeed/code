package com.nork.product.dao;

import java.util.List;

import com.nork.product.model.CompanyShop;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.BaseCompany;
import com.nork.product.model.search.BaseCompanySearch;

/**   
 * @Title: BaseCompanyMapper.java 
 * @Package com.nork.product.dao
 * @Description:产品模块-企业表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-06-15 17:01:45
 * @version V1.0   
 */
@Repository
@Transactional
public interface BaseCompanyMapper {
    int insertSelective(BaseCompany record);

    int updateByPrimaryKeySelective(BaseCompany record);
  
    int deleteByPrimaryKey(Integer id);
        
    BaseCompany selectByPrimaryKey(Integer id);
    
    int selectCount(BaseCompanySearch baseCompanySearch);
    
	List<BaseCompany> selectPaginatedList(
			BaseCompanySearch baseCompanySearch);
			
    List<BaseCompany> selectList(BaseCompany baseCompany);

    BaseCompany getHeadMessageOfCompany(Integer companyId);

    CompanyShop getHeadMessageOfShop(Integer shopId);
}
