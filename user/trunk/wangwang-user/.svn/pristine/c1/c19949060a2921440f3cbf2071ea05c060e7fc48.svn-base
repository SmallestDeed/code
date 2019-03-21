package com.sandu.api.base.service;


import java.util.List;

import com.sandu.api.base.model.BaseCompany;
import com.sandu.api.user.model.bo.CompanyInfoBO;

public interface BaseCompanyService {

    /**
     * 根据id查找企业信息/经销商
     *
     * @param id 
     * @return
     */
	BaseCompany queryById(Long id);
	
	/**
	 * 查询经销商已开通平台权限的企业信息
	 * @param mobile
	 * @param password
	 * @return
	 */
	List<CompanyInfoBO> queryFranchiserCompanys(String mobile,String password,String platformCode,int isDefaultLogin);

	List<CompanyInfoBO> queryFranchiserCompanys(String mobile,String password,String platformCode);

	BaseCompany queryByAppId(String appId);

    String getcompanyCode();

	int save(BaseCompany baseCompany);

    int delByPrimaryKey(Long id);

    int updatePid(Long id, Long companyId);

	String createCompanyCode(String commanyCodePrefix,List<Integer> businessTypeList,List<Integer> businessTypeNotList);

	BaseCompany getCompanyByCode(String companyCode);

    List<CompanyInfoBO> queryCompany(String companyName);

    List<CompanyInfoBO> getFranchiserCompany(Long companyId);
}
