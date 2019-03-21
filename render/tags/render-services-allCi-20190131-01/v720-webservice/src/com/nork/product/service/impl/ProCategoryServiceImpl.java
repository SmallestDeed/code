package com.nork.product.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.util.Utils;
import com.nork.product.cache.ProCategoryCacher;
import com.nork.product.dao.ProCategoryMapper;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.ProCategory;
import com.nork.product.model.ProductCategoryRel;
import com.nork.product.model.search.ProCategorySearch;
import com.nork.product.model.small.SearchProCategorySmall;
import com.nork.product.service.ProCategoryService;

/**   
 * @Title: ProCategoryServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:产品模块-产品类目ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-06-17 14:46:36
 * @version V1.0   
 */
@Service("proCategoryService")
@Transactional
public class ProCategoryServiceImpl implements ProCategoryService {
	private ProCategoryMapper proCategoryMapper;
	

	@Autowired
	public void setProCategoryMapper(
			ProCategoryMapper proCategoryMapper) {
		this.proCategoryMapper = proCategoryMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param proCategory
	 * @return  int 
	 */
	@Override
	public int add(ProCategory proCategory) {
		proCategoryMapper.insertSelective(proCategory);
		return proCategory.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param proCategory
	 * @return  int 
	 */
	@Override
	public int update(ProCategory proCategory) {
		return proCategoryMapper
				.updateByPrimaryKeySelective(proCategory);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return proCategoryMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  ProCategory 
	 */
	@Override
	public ProCategory get(Integer id) {
		return proCategoryMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  proCategory
	 * @return   List<ProCategory>
	 */
	@Override
	public List<ProCategory> getList(ProCategory proCategory) {
	    return proCategoryMapper.selectList(proCategory);
	}
	
	
	
	/**
	 * 所有数据
	 * 
	 * @param  proCategory
	 * @return   List<ProCategory>
	 */
	@Override
	public List<ProCategory> getListV2(ProCategory proCategory) {
	    return proCategoryMapper.selectListV2(proCategory);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  proCategory
	 * @return   int
	 */
	@Override
	public int getCount(ProCategorySearch proCategorySearch){
		return  proCategoryMapper.selectCount(proCategorySearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  proCategory
	 * @return   List<ProCategory>
	 */
	@Override
	public List<ProCategory> getPaginatedList(
			ProCategorySearch proCategorySearch) {
		return proCategoryMapper.selectPaginatedList(proCategorySearch);
	}

	/**
	 * 其他
	 * 
	 */

	/**
	 * 根据分类Code查询
	 * @return
	 */
	@Override
	public List<CategoryProductResult> getProCategoryListByCode(ProductCategoryRel productCategoryRelSearch){
		return proCategoryMapper.getProCategoryListByCode(productCategoryRelSearch);
	}

	/**
	 * 根据类目ID查询子类目
	 * @param pid
	 * @return
	 */
	@Override
	public List<ProCategory> asyncLoadTree(Integer pid){
		return proCategoryMapper.asyncLoadTree(pid);
	}

	/**
	 * 根据分类Code查询(前台使用)
	 * @return
	 */
	@Override
	public List<CategoryProductResult> getProCategoryListByCodeForWeb(ProductCategoryRel productCategoryRelSearch){
		return proCategoryMapper.getProCategoryListByCodeForWeb(productCategoryRelSearch);
	}

	/**
	 * 修改分类.如果code变动过，则需要修改分类下所有子节点的code
	 * @return
	 */
	@Override
	public int updateRecursion(ProCategory proCategory){
		ProCategory oldProCategory = proCategoryMapper.selectByPrimaryKey(proCategory.getId());
		if( !oldProCategory.getCode().equals(proCategory.getCode()) ){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("oldCode","."+oldProCategory.getCode()+".");
			params.put("newCode","."+proCategory.getCode()+".");
			proCategoryMapper.recursionUpdate(params);
		}
		return proCategoryMapper.updateByPrimaryKeySelective(proCategory);
	}

	@Override
	public int getProCategoryListCountByCode(
			ProductCategoryRel productCategoryRelSearch) {
		return proCategoryMapper.getProCategoryListCountByCode(productCategoryRelSearch);
	}
	
	@Override
	public ProCategory getAllCategory() {
		List<SearchProCategorySmall> categoryList = new ArrayList<SearchProCategorySmall>();
		ProCategory category = null;
		ProCategory pc = new ProCategory();
		pc.setState("0");
		pc.setOrder(" ordering ");
		
		List<ProCategory> categoryAllList = null;
		
		if(Utils.enableRedisCache()){
			categoryAllList = ProCategoryCacher.getAllList(pc);
		}else{
			/*categoryAllList = this.getList(pc);*/
			categoryAllList = this.getListV2(pc);
		}
		
		
		if (categoryAllList != null && categoryAllList.size() >= 0) {
			for (ProCategory ppc : categoryAllList) {
				if (ppc.getId().intValue() == 1) {
					category = ppc;
					break;
				}
			}
		}
		//
		// categoryList = recursionCategory(category);
		categoryList = recursionCategory2(category, categoryAllList);
		category.setChildrenNodes(categoryList);
		return category;
	}
 

	public List<SearchProCategorySmall> recursionCategory2(
			ProCategory category, List<ProCategory> categoryAllList) {
		List<SearchProCategorySmall> childrenNodes = category
				.getChildrenNodes();
		/*ProCategorySearch search = new ProCategorySearch();
		search.setPid(category.getId());
		search.setLongCode(category.getLongCode());*/
		
		ProCategory procate = new ProCategory();
		procate.setPid(category.getId());
		procate.setLongCode(category.getLongCode());
		
		List<ProCategory> list = new ArrayList<ProCategory>();
		if (categoryAllList != null && categoryAllList.size() > 0) {
			for (ProCategory pc : categoryAllList) {
				if (pc.getPid().intValue() == procate.getPid().intValue()
						&& pc.getLongCode().indexOf(category.getLongCode()) != -1) {
					list.add(pc);
				}
			}
		} else {
			
			//list = this.getList(procate);
			if(Utils.enableRedisCache()){
        		list = ProCategoryCacher.getAllList(procate);
        	}else{
        		list = this.getList(procate);
        	}
		}
		if (list != null && list.size() > 0) {
			if (childrenNodes == null) {
				childrenNodes = new ArrayList<SearchProCategorySmall>();
			}
		}
		SearchProCategorySmall newCategory = null;
		for (ProCategory childrenNode : list) {
			newCategory = new SearchProCategorySmall();
			newCategory.setAa(childrenNode.getId());
			newCategory.setCc(childrenNode.getPid());
			newCategory.setDd(childrenNode.getName());
			newCategory.setBb(childrenNode.getCode());
			newCategory.setEe(childrenNode.getOrdering());
			newCategory.setFf(recursionCategory2(childrenNode,
					categoryAllList));
			newCategory.setGg(childrenNode.getNameFirstLetter());
			childrenNodes.add(newCategory);
		}
		category.setChildrenNodes(childrenNodes);

		return childrenNodes;
	}

	/**
	 * 通过code查找ProCategory
	 * @author huangsongbo
	 * @param categoryCode
	 * @return
	 */
	public ProCategory findOneByCode(String code) {
		return proCategoryMapper.findOneByCode(code);
	}
	
	/**
	 * 根据可见范围查询到所有可见的分类编码
	 */
	@Override
	public List<String> getCodeListByIdList(List<Integer> visibilityRangeIdList) {
		// TODO Auto-generated method stub
		return proCategoryMapper.getCodeListByIdList(visibilityRangeIdList);
	}
}
