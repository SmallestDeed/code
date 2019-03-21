package com.nork.product.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.BrandIndustryModel;
import com.nork.product.model.search.BaseBrandSearch;

/**   
 * @Title: BaseBrandService.java 
 * @Package com.nork.product.service
 * @Description:产品-品牌表Service
 * @createAuthor pandajun 
 * @CreateDate 2015-06-16 10:03:47
 * @version V1.0   
 */
public interface BaseBrandService {
	/**
	 * 新增数据
	 *
	 * @param baseBrand
	 * @return  int 
	 */
	public int add(BaseBrand baseBrand);

	/**
	 *    更新数据
	 *
	 * @param baseBrand
	 * @return  int 
	 */
	public int update(BaseBrand baseBrand);

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
	 * @return  BaseBrand 
	 */
	public BaseBrand get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  baseBrand
	 * @return   List<BaseBrand>
	 */
	public List<BaseBrand> getList(BaseBrand baseBrand);

	/**
	 *    获取数据数量
	 *
	 * @param  baseBrand
	 * @return   int
	 */
	public int getCount(BaseBrandSearch baseBrandSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  baseBrand
	 * @return   List<BaseBrand>
	 */
	public List<BaseBrand> getPaginatedList(
				BaseBrandSearch baseBrandtSearch);

	/**
	 * 根据用户得到其序列号对应的品牌type
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	public Set<String> getBrandTypeSet(Integer userId);

	/**
	 * 通过brandReferred查找一条BaseBrand数据
	 * @param string
	 * @return
	 */
	public BaseBrand findOneByBrandReferred(String brandReferred);

	public List<BaseBrand> getListByIds(List<Integer> brandIds);

	int getAllBrandInfoCount(BaseBrandSearch baseBrandSearch);

	public List<BaseBrand> listAllByPara(BaseBrandSearch baseBrandSearch);

	/**
	 * 获取品牌公司所属行业和细分行业值
	 * @param userId
	 * @return
	 */
	List<BaseBrand> getBrandIndustryValueByUserId(Integer userId);

	/**
	 * 获取授权码品牌过滤条件
	 * @param userId
	 * @return
	 */
	List<BrandIndustryModel> getAuthorizedBrandFilterCondition(Integer userId);

	/**
	 * 通过品牌名称查出id
	 * @param brandName
	 * @return
	 */
	int getIdByBrandName(String brandName);
	/**
	 * 通过用户id去查询品牌id
	 * 
	 * */
	BaseBrand findBrandIdByUserId(Integer id);
	/**
	 * 通过品牌ids去查询品牌信息
	 * 
	 * */
	BaseBrand findBrandInfoByUserId(String ids);
	/**
	 * 通过用户id去查询品牌id,并且用户类型为厂商
	 * 
	 * */
	BaseBrand findBrandIdByUserIdAndUserType(LoginUser loginUser);

	/**
	 * 通过品牌id与平台查询已上架方案id
	 * @param brandIds
	 * @return
	 */
	List<Integer> findPlanIdListByBrand(List<Integer> brandIds);
	/**
	 * 通过品牌id去查询用户id,并且用户类型为经销商
	 * 
	 * */
	List<LoginUser> selectBrandByUserId(Integer id);
	List<BaseBrand> findBrandIdByUserIdList(Integer id);
	BaseBrand findBrandIdByUserIdBase(LoginUser loginUser);
	BaseBrand findBrandIdByUserIdDuli(LoginUser loginUser);
	List<BaseBrand> findBrandIdByUserIdListB(Integer id);
	List<BaseBrand> findBrandIdByUserIdListBase(Integer id);
	BaseBrand selectUserIdByBrandIds(LoginUser loginUser);
	List<BaseBrand> selectCompanyUserIdByDealerId(Integer id);
	List<BaseBrand> selectCompanyByUserId(Map<String,Object>map);

	/**
	 * 通过企业ID查询品牌IDS
	 * @param companyId
	 * @return
	 */
	String selectBrandIdsByCompanyId(Integer companyId);
}
