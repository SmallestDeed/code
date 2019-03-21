package com.sandu.sys.service;

import java.util.List;

import com.sandu.sys.model.vo.ProCategoryVO;
import com.sandu.sys.model.vo.ProductCategoryNodeVo;
import com.sandu.sys.model.vo.ProductCategoryVo;

/***
 * 产品分类服务接口
 * 
 * @author Administrator
 *
 */
public interface ProductCategoryService {
	/***
	 * 获取一二级产品分类节点信息
	 * 
	 * @return
	 */
	List<ProductCategoryNodeVo> getNodeList();

	/**
	 * 获取一级产品分类信息
	 * 
	 * @return
	 */
	List<ProductCategoryVo> getFirstList();

	/**
	 * 通过分类ids获取分类名称
	 * 
	 * @param companyId
	 *            公司id
	 * @param categoryIds
	 *            分类ids
	 * @return
	 */
	List<ProductCategoryVo> getCategoryListByIds(Integer companyId, String categoryIds);

	/**
	 * 通过分类等级，查询分类信息
	 * @param level 分类等级
	 *
	 * @return list 分类集合
	 */
	List<ProCategoryVO> getListByLevel(Integer level,Integer start,Integer pageSize);
}
