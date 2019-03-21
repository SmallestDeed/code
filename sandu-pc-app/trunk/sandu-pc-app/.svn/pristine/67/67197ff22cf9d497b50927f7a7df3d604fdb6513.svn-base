package com.sandu.product.service;

import com.sandu.product.model.CompanyShop;
import com.sandu.product.model.input.CompanyShopSearch;
import com.sandu.product.model.output.CompanyShopVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**   
 * @Title: CompanyShopService.java 
 * @Package com.sandu.product.service
 *
 * @Description:企业商铺-企业店铺Service
 * @version V1.0
 */
@Component
public interface CompanyShopService {
	/**
	 * 新增数据
	 *
	 * @param companyShop
	 * @return  int 
	 */
	public int add(CompanyShop companyShop);

	/**
	 *    更新数据
	 *
	 * @param companyShop
	 * @return  int 
	 */
	public int update(CompanyShop companyShop);

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
	 * @return  CompanyShop 
	 */
	public CompanyShop get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  companyShop
	 * @return   List<CompanyShop>
	 */
	public List<CompanyShop> getList(CompanyShop companyShop);

	/**
	 * 根据企业Id更新店铺分类
	 *
	 * @param companyShop
	 * @return
	 */
	int updateCategoryByCompanyId(CompanyShop companyShop);

	/**
	 * 通过企业ID查询店铺主键列表
	 * @param companyId
	 * @return
	 */
	List<Integer> findIdListByCompanyId(Integer companyId);

	/**
	 * 通过用户id查询用户所属店铺列表
	 * @param userId 用户Id
	 * @return list
	 */
	List<CompanyShop> getShopListByUserId(Long userId);

	/**
	 * 通过用户Id删除属于用户的店铺
	 * @param userId 用户Id
	 * @return
	 */
	Integer deleteShopByUserId(Long userId, String loginUserName);


	/**
	 * 通过企业Id查询企业店铺
	 * @param companyId 企业Id
	 * @return list
	 */
	List<CompanyShop> getShopListByCompanyId(Long companyId);

	/**
	 * 通过企业Id删除企业店铺
	 * @param companyId 企业Id
	 * @return int
	 */
	int deleteShopByCompanyId(Long companyId, String loginUserName);

	/**
	 * 根据公司id获取品牌馆
	 * 有多个则获取最早的
	 * @param companyId
	 * @return
	 */
	CompanyShop getCompanyShopByCompanyId(Integer companyId);


	/**
	 * 查询店铺总数
	 * @param search
	 * @return
	 */
	int getCountBySearch(CompanyShopSearch search);

	/**
	 * 按条件查询店铺信息
	 * @param search
	 * @return
	 */
	List<CompanyShopVo> getCompanyShopListBySearch(CompanyShopSearch search);
}
