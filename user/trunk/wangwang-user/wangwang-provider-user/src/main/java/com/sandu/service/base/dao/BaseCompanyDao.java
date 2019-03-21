package com.sandu.service.base.dao;

import java.util.List;
import java.util.Set;

import com.sandu.api.base.model.BasePlatform;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.base.model.BaseCompany;
import com.sandu.api.user.model.bo.CompanyInfoBO;

@Repository
public interface BaseCompanyDao {

	BaseCompany selectById(@Param("id") Long id);

	List<CompanyInfoBO> selectFranchiserCompanys(@Param("mobile") String mobile,@Param("password") String password,@Param("platformId")Long platformId,@Param("isForLogin")int isForLogin);

	BaseCompany selectByAppId(@Param("appId") String appId);

	String createCompanyCode(@Param("commanyCodePrefix") String commanyCodePrefix,
							 @Param("businessTypeList")List<Integer> businessTypeList,
							 @Param("businessTypeNotList")List<Integer> businessTypeNotList);

	int insertSelective(BaseCompany baseCompany);

    int delByPrimaryKey(Long id);

    int updatePid(@Param("id") Long id, @Param("companyId") Long companyId);

    List<CompanyInfoBO> selectFCompanys(@Param("mobile") String mobile,@Param("password") String password,@Param("platformId")Long platformId);

	BaseCompany selectByCode(@Param("companyCode") String companyCode);

    List<CompanyInfoBO> queryCompany(@Param("companyName")String companyName);

    List<CompanyInfoBO> getFranchiserCompany(Long companyId);

}
