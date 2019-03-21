package com.nork.product.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nork.product.model.BaseCompany;
import com.nork.product.model.search.BaseCompanySearch;

/**   
 * @Title: BaseCompanyService.java 
 * @Package com.nork.product.service
 * @Description:产品模块-企业表Service
 * @createAuthor pandajun 
 * @CreateDate 2015-06-15 17:01:45
 * @version V1.0   
 */
public interface BaseCompanyService {
	/**
	 * 新增数据
	 *
	 * @param baseCompany
	 * @return  int 
	 */
	public int add(BaseCompany baseCompany);

	/**
	 *    更新数据
	 *
	 * @param baseCompany
	 * @return  int 
	 */
	public int update(BaseCompany baseCompany);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	public int delete(Integer id);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  BaseCompany 
	 */
	public BaseCompany get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  baseCompany
	 * @return   List<BaseCompany>
	 */
	public List<BaseCompany> getList(BaseCompany baseCompany);

	/**
	 *    获取数据数量
	 *
	 * @param  baseCompany
	 * @return   int
	 */
	public int getCount(BaseCompanySearch baseCompanySearch);

	/**
	 *    分页获取数据
	 *
	 * @param  baseCompany
	 * @return   List<BaseCompany>
	 */
	public List<BaseCompany> getPaginatedList(
				BaseCompanySearch baseCompanytSearch);

	/**
	 * 获得用户类型:(序列号->公司->小类)
	 * @param id
	 * @return
	 */
	public Set<String> getTypeSetByUserId(Integer id);

	/**
	 * 根据公司id获取其对应的大类(公司id->公司明细行业->数据字典att1->大类)
	 * @author huangsongbo
	 * @param companyId
	 * @return
	 */
	Map<String, Object> getCategoryList(Integer companyId);

	/**
	 * 获取用户企业LOGO
	 * @param userId
	 * @return
	 */
	String getCompanyLogoByAuthorizedConfig(Integer userId);
}
