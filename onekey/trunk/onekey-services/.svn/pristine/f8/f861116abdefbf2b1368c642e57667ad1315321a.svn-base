package com.nork.productprops.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.util.collections.Lists;
import com.nork.productprops.dao.ProductPropsMapper;
import com.nork.productprops.model.ProductProps;
import com.nork.productprops.model.search.ProductPropsSearch;
import com.nork.productprops.service.ProductPropsService;

/**   
 * @Title: ProductPropsServiceImpl.java 
 * @Package com.nork.productprops.service.impl
 * @Description:产品属性-产品属性表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-09-01 10:40:03
 * @version V1.0   
 */
@Service("productPropsService")
@Transactional
public class ProductPropsServiceImpl implements ProductPropsService {

	private ProductPropsMapper productPropsMapper;

	@Autowired
	public void setProductPropsMapper(
			ProductPropsMapper productPropsMapper) {
		this.productPropsMapper = productPropsMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param productProps
	 * @return  int 
	 */
	@Override
	public int add(ProductProps productProps) {
		productPropsMapper.insertSelective(productProps);
		return productProps.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param productProps
	 * @return  int 
	 */
	@Override
	public int update(ProductProps productProps) {
		return productPropsMapper
				.updateByPrimaryKeySelective(productProps);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return productPropsMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  ProductProps 
	 */
	@Override
	public ProductProps get(Integer id) {
		return productPropsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  productProps
	 * @return   List<ProductProps>
	 */
	@Override
	public List<ProductProps> getList(ProductProps productProps) {
	    return productPropsMapper.selectList(productProps);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  productProps
	 * @return   int
	 */
	@Override
	public int getCount(ProductPropsSearch productPropsSearch){
		return  productPropsMapper.selectCount(productPropsSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  productProps
	 * @return   List<ProductProps>
	 */
	@Override
	public List<ProductProps> getPaginatedList(
			ProductPropsSearch productPropsSearch) {
		return productPropsMapper.selectPaginatedList(productPropsSearch);
	}

	/**
	 *    分页获取数据
	 *
	 * @param  productProps
	 * @return   List<ProductProps  >
	 */
	@Override
	public List<ProductProps> getPaginatedPropValsList(ProductPropsSearch productPropsSearch) {
//		List<ProductProps> newList = new ArrayList<>();
		List<ProductProps> list = productPropsMapper.selectPaginatedList(productPropsSearch);
		for( ProductProps productProps : list ){
			ProductProps props = productPropsMapper.selectByPrimaryKey(productProps.getPid());
			productProps.setParentProps(props);
//			newList.add(productProps);
		}
		return list;
	}

	/**
	 * 其他
	 * 
	 */


	/**
	 * 异步加载产品属性
	 * @param pid
	 * @return
	 */
	@Override
	public List<ProductProps> asyncLoadTree(Integer pid){
		return productPropsMapper.asyncLoadTree(pid);
	}

	@Override
	public List<ProductProps> getAttributeKeyAndValueByProductId(Integer productId) {
		return productPropsMapper.getAttributeKeyAndValueByProductId(productId);
	}

	@Override
	public String getStructureProductPropValueByProductId(Integer productId, String productAttrCode) {
		// 参数验证 ->start
		if(productId == null) {
			return null;
		}
		if(StringUtils.isEmpty(productAttrCode)) {
			productAttrCode = "structureSign";
		}
		// 参数验证 ->end
		List<String> propValueList = productPropsMapper.getStructureProductPropValueByProductId(productId, productAttrCode);
		if(Lists.isNotEmpty(propValueList)) {
			return propValueList.get(0);
		}else {
			return null;
		}
	}
	
	@Override
	public String getStructureProductPropCodeByProductId(Integer productId, String productAttrCode) {
		// 参数验证 ->start
		if(productId == null) {
			return null;
		}
		if(StringUtils.isEmpty(productAttrCode)) {
			productAttrCode = "structureSign";
		}
		// 参数验证 ->end
		List<String> propValueList = productPropsMapper.getStructureProductPropCodeByProductId(productId, productAttrCode);
		if(Lists.isNotEmpty(propValueList)) {
			return propValueList.get(0);
		}else {
			return null;
		}
	}
}
