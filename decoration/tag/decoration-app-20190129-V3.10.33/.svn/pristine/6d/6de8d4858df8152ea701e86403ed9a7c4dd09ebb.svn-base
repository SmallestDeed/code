package com.nork.product.service;

import java.util.List;

import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.ProCategory;
import com.nork.product.model.ProductCategoryRel;
import com.nork.product.model.search.ProCategorySearch;

/**   
 * @Title: ProCategoryService.java 
 * @Package com.nork.product.service
 * @Description:产品模块-产品类目Service
 * @createAuthor pandajun 
 * @CreateDate 2015-06-17 14:46:36
 * @version V1.0   
 */
public interface ProCategoryService {
	/**
	 * 新增数据
	 *
	 * @param proCategory
	 * @return  int 
	 */
	public int add(ProCategory proCategory);

	/**
	 *    更新数据
	 *
	 * @param proCategory
	 * @return  int 
	 */
	public int update(ProCategory proCategory);

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
	 * @return  ProCategory 
	 */
	public ProCategory get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  proCategory
	 * @return   List<ProCategory>
	 */
	public List<ProCategory> getList(ProCategory proCategory);

	/**
	 *    获取数据数量
	 *
	 * @param  proCategory
	 * @return   int
	 */
	public int getCount(ProCategorySearch proCategorySearch);

	/**
	 *    分页获取数据
	 *
	 * @param  proCategory
	 * @return   List<ProCategory>
	 */
	public List<ProCategory> getPaginatedList(
				ProCategorySearch proCategorytSearch);

	/**
	 * 其他
	 * 
	 */

	/**
	 * 根据分类Code查询
	 * @return
	 */
	List<CategoryProductResult> getProCategoryListByCode(ProductCategoryRel productCategoryRelSearch);

	/**
	 * 根据类目ID查询子类目
	 * @param pid
	 * @return
	 */
	List<ProCategory> asyncLoadTree(Integer pid);

	/**
	 * 根据分类Code查询(前台使用)
	 * @return
	 */
	List<CategoryProductResult> getProCategoryListByCodeForWeb(ProductCategoryRel productCategoryRelSearch);

	/**
	 * 修改分类.如果code变动过，则需要修改分类下所有子节点的code
	 * @return
	 */
	int updateRecursion(ProCategory proCategory);

	public int getProCategoryListCountByCode(
			ProductCategoryRel productCategoryRelSearch);
	/**
	 * 获取所有的大类
	 * @return
	 */
	public ProCategory  getAllCategory();
	
	/**
	 * 通过code查找ProCategory
	 * @author huangsongbo
	 * @param categoryCode
	 * @return
	 */
	public ProCategory findOneByCode(String categoryCode);

	public List<ProCategory> getListV2(ProCategory proCategory);
}
