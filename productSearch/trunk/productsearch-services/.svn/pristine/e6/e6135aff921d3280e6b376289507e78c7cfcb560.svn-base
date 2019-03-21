package com.nork.product.service;

import java.util.List;
import java.util.Set;

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
	 * 查询公司所拥有的品牌
	 * @param companyId 公司ID
	 * @return      公司品牌LIST
	 */
	List<Integer> queryBrandListByCompanyId(Integer companyId);
	/**
	 * 查询与该产品品牌联盟的所有品牌
	 * @param
	 */
	List<Integer> getBrandIdsByProductId(Integer productId);

	/**
	 * 获取产品所属的品牌
	 * @param productId
	 * @return
	 */
	List<Integer> getProductOwnBrandIds(Integer productId);

	/**
	 * 根据公司id获取品牌idsStr
	 * @param companyId
	 * @return
	 */
	String getBrandIdsStrByCompanyId(Integer companyId);
}
