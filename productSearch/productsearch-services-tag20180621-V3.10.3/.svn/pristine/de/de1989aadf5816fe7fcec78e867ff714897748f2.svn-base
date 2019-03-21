package com.nork.product.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.util.Utils;
import com.nork.product.dao.ProductAttributeMapper;
import com.nork.product.model.ProductAttribute;
import com.nork.product.model.ProductCategoryRel;
import com.nork.product.model.search.BaseProductSearch;
import com.nork.product.model.search.GroupProductSearch;
import com.nork.product.model.search.ProductAttributeSearch;
import com.nork.product.service.ProductAttributeService;
import com.nork.productprops.model.ProductProps;
import com.nork.productprops.service.ProductPropsService;

/**   
 * @Title: ProductAttributeServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:产品模块-产品属性ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-09-01 13:17:36
 * @version V1.0   
 */
@Service("productAttributeService")
@Transactional
public class ProductAttributeServiceImpl implements ProductAttributeService {
	

	@Autowired
	private ProductPropsService productPropsService;
	
	private ProductAttributeMapper productAttributeMapper;

	@Autowired
	public void setProductAttributeMapper(
			ProductAttributeMapper productAttributeMapper) {
		this.productAttributeMapper = productAttributeMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param productAttribute
	 * @return  int 
	 */
	@Override
	public int add(ProductAttribute productAttribute) {
		productAttributeMapper.insertSelective(productAttribute);
		return productAttribute.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param productAttribute
	 * @return  int 
	 */
	@Override
	public int update(ProductAttribute productAttribute) {
		return productAttributeMapper
				.updateByPrimaryKeySelective(productAttribute);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return productAttributeMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  ProductAttribute 
	 */
	@Override
	public ProductAttribute get(Integer id) {
		return productAttributeMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  productAttribute
	 * @return   List<ProductAttribute>
	 */
	@Override
	public List<ProductAttribute> getList(ProductAttribute productAttribute) {
	    return productAttributeMapper.selectList(productAttribute);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  productAttributeSearch
	 * @return   int
	 */
	@Override
	public int getCount(ProductAttributeSearch productAttributeSearch){
		return  productAttributeMapper.selectCount(productAttributeSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @return   List<ProductAttribute>
	 */
	@Override
	public List<ProductAttribute> getPaginatedList(
			ProductAttributeSearch productAttributeSearch) {
		return productAttributeMapper.selectPaginatedList(productAttributeSearch);
	}

	@Override
	public List<ProductAttribute> getMergeAttribute(
			ProductAttribute productAttributet) {
		return productAttributeMapper.getMergeAttribute(productAttributet);
	}

	/**
	 * getPropertyMap
	 * 
	 * 通过产品id  获取属性值 的map   map的结构（ 键是product_attribute表中attribute_key  值是product_props表中的prop_value）
	 * @param productId
	 * @return
	 */
	@Override
	public Map<String, String> getPropertyMap(Integer productId) {
		/*
		ProductAttribute productAttribute_p = new ProductAttribute();
		productAttribute_p.setProductId(productId);
		productAttribute_p.setIsDeleted(0);
		List<ProductAttribute> AttributeList = null;
		String cacheEnable = Utils.getValue("redisCacheEnable", "0");
		if("1".equals(cacheEnable)){
		     AttributeList = ProductAttributeCacher.getProductAttributeList(productAttribute_p);
		}else{
			 AttributeList = productAttributeMapper.selectList(productAttribute_p);
		}
		if(AttributeList!=null&&AttributeList.size()>0){
			for (ProductAttribute productAttribute : AttributeList) {
				ProductProps productProps = null;
				String propValue = null;
				if(productAttribute.getAttributeValueId()!=null&&!"".equals(productAttribute.getAttributeValueId())){
					if("1".equals(cacheEnable)){
						productProps =ProductPropsCacher.get(productAttribute.getAttributeValueId());
					}else{
						productProps =productPropsService.get(productAttribute.getAttributeValueId());
					}
				}
				
				if(productProps!=null){
					propValue=productProps.getPropValue();
				}
				map.put(productAttribute.getAttributeKey(),propValue);
			}
		}
		*/
		Map<String,String> map=new HashMap<String,String>();
		ProductAttributeSearch productAttributeSearch= new ProductAttributeSearch();
		productAttributeSearch.setProductId(productId);
		productAttributeSearch.setIsDeleted(0);
		int Count=productAttributeMapper.selectCount(productAttributeSearch);
		if(Count>0){
			List<ProductAttribute> AttributeList =productAttributeMapper.selectPaginatedList(productAttributeSearch);
			if(AttributeList!=null&&AttributeList.size()>0){
				for (ProductAttribute productAttribute : AttributeList) {
					ProductProps productProps = null;
					String propValue = null;
					String propCode = null;
					String productPropsParentCode = null;
					if(productAttribute.getAttributeValueId()!=null&&!"".equals(productAttribute.getAttributeValueId())){
						productProps =productPropsService.get(productAttribute.getAttributeValueId());
						if (null != productProps && productProps.getPid() != null) {
							ProductProps productPropsParent = productPropsService.get(productProps.getPid());
							if(productPropsParent != null) {
								productPropsParentCode = productPropsParent.getCode();
							}
						}
					}
					if(productProps!=null){
						propValue=productProps.getPropValue();
						propCode = productProps.getCode();
					}
					if(StringUtils.isNotBlank(productPropsParentCode)) {
						map.put(productPropsParentCode, propValue);
						//如果是结构产品标志 传Code值
						if("structureSign".equals(productPropsParentCode)){
							map.put(productPropsParentCode, propCode);
						}
					}
				}
			}
		}
		return map;
	}
	
	public Map<String, String> getPropertyMapV2(Integer productId) {
		Map<String, String> map = new HashMap<String, String>();
		List<ProductProps> productPropList = productPropsService.getAttributeKeyAndValueByProductId(productId);
		if (productPropList != null && productPropList.size() > 0) {
			for (ProductProps productProp : productPropList) {
				map.put(productProp.getCode(), productProp.getPropValue());
			}
		}
		return map;
	}
	
	/**
	 * 查询产品多个属性的条件
	 * @param productId
	 * @return
	 */
	@Override
	public ProductCategoryRel getAttributeCondition(ProductCategoryRel productCategoryRel,Integer productId) {
		List<String> propsFilterList = new ArrayList<String>();
		List<ProductProps> propsOrderList = new ArrayList<ProductProps>();
		ProductAttribute productAttribute = new ProductAttribute();
		productAttribute.setProductId(productId);
		productAttribute.setIsDeleted(0);
		List<ProductAttribute> attriList = productAttributeMapper.selectList(productAttribute);
		StringBuffer conditionStr = new StringBuffer(); 
		for (int i=0;i<attriList.size();i++) {
			conditionStr = new StringBuffer();
			ProductAttribute pa = attriList.get(i);
			ProductProps props = productPropsService.get(pa.getAttributeValueId());
			ProductProps parentProps = null;
			if( props != null ){
				parentProps = productPropsService.get(props.getPid());
			}
			if( parentProps != null ){
				if( StringUtils.isNotBlank(parentProps.getFilterOrder()) && "filter".equals(parentProps.getFilterOrder()) ){
//					conditionStr.append("pa.attribute_key = '" + pa.getAttributeKey() /*+ "' AND pa.attribute_type_value = '" + Utils.getTypeValueKey(pa.getAttributeTypeValue())*/+ "' AND pp.prop_value = " + props.getPropValue());
					//加一个非空判断，add by yangz 2018年1月18日11:20:40
					if(pa.getAttributeKey() != null && !"".equals(pa.getAttributeKey())) {
						conditionStr.append("pa.attribute_key = '" + pa.getAttributeKey() + "'");
						if(props.getPropValue() != null && !"".equals(props.getPropValue())) {
							conditionStr.append("AND pp.prop_value = " + props.getPropValue());
						}
					}
					propsFilterList.add(conditionStr.toString());
				}else{
					//分类搜索排序属性匹配度用到
					parentProps.setParentCode(pa.getAttributeKey());
					parentProps.setProductId(pa.getProductId());
					parentProps.setPropValue(props.getPropValue());
					propsOrderList.add(parentProps);
				}
/*				if( StringUtils.isNotBlank(parentProps.getFilterOrder()) && "order".equals(parentProps.getFilterOrder()) ){
					//分类搜索排序属性匹配度用到
					parentProps.setParentCode(pa.getAttributeKey());
					parentProps.setProductId(pa.getProductId());
					parentProps.setPropValue(props.getPropValue());
					propsOrderList.add(parentProps);
				}*/
			}
		}
		productCategoryRel.setPropsOrderList(propsOrderList);
		productCategoryRel.setAttributeConditionList(propsFilterList);
		productCategoryRel.setAttributeConditionSize(propsFilterList.size());
		
		return productCategoryRel;
	}

	/**
	 * 其他
	 * 
	 */


	/**
	 * 查询产品属性，包含属性定义内容
	 * @param productAttribute
	 * @return
	 */
	@Override
	public List<ProductAttribute> getAttributeUnionPropsList(ProductAttribute productAttribute){
		return productAttributeMapper.getAttributeUnionPropsList(productAttribute);
	}

	/**
	 * 查询出满足多个属性的产品
	 * @param map
	 * @return
	 */
	@Override
	public List<ProductAttribute> selectIntersectProductAttribute(Map<String,Object> map){
		return productAttributeMapper.selectIntersectProductAttribute(map);
	}

	@Override
	public Map<String, String> getPropertyOrderMap(Integer productId) {
		Map<String,String>map=new HashMap<String,String>();
		ProductAttributeSearch productAttributeSearch= new ProductAttributeSearch();
		productAttributeSearch.setProductId(productId);
		productAttributeSearch.setIsDeleted(0);
		int Count = productAttributeMapper.selectCount(productAttributeSearch); 
		if( Count > 0 ){
			List<ProductAttribute>AttributeList =productAttributeMapper.selectPaginatedList(productAttributeSearch);
			if (AttributeList!=null&&AttributeList.size()>0) {
				for (ProductAttribute productAttribute : AttributeList) {
					ProductProps productProps = null;
					ProductProps parentProductProps = null;
					if(productAttribute.getAttributeValueId()!=null&&!"".equals(productAttribute.getAttributeValueId())){
						productProps = productPropsService.get(productAttribute.getAttributeValueId());
						if(productProps != null){
							parentProductProps = productPropsService.get(productProps.getPid());
						}
					}
					if(parentProductProps!=null && StringUtils.isNotBlank(parentProductProps.getFilterOrder()) && "order".equals(parentProductProps.getFilterOrder())){
						map.put(productAttribute.getAttributeKey(),String.valueOf(productAttribute.getAttributeValueId()));
					}
				}
			}
		}
		return map;
	}
	
	/**
	 * 设置主产品属性过滤查询条件
	 * @param groupSearch
	 * @param productId
	 */
	public void setProductAttributeQueryCondition(GroupProductSearch groupSearch, Integer productId) {
		List<String> propsFilterList = new ArrayList<String>();
		ProductAttribute productAttribute = new ProductAttribute();
		productAttribute.setProductId(productId);
		productAttribute.setIsDeleted(0);
		List<ProductAttribute> attriList = productAttributeMapper.selectListMoreInfo(productAttribute);
		StringBuffer conditionStr = new StringBuffer(); 
		int listSize = attriList.size();
		for (int i=0;i<listSize;i++) {
			conditionStr = new StringBuffer();
			ProductAttribute pa = attriList.get(i);
			if( StringUtils.isNotBlank(pa.getParentFilterOrder()) && "filter".equals(pa.getParentFilterOrder()) ){
				conditionStr.append("pa.attribute_key = '" + pa.getAttributeKey() + "' AND pa.attribute_type_value = '" + Utils.getTypeValueKey(pa.getAttributeTypeValue()) + "' AND pp.prop_value = " + pa.getPropValue());
				propsFilterList.add(conditionStr.toString());
			}
		}
		groupSearch.setAttributeConditionList(propsFilterList);
		groupSearch.setAttributeConditionSize(propsFilterList.size());
	}
	
	/**
	 * 设置主产品属性过滤查询条件
	 * @param groupSearch
	 * @param productId
	 */
	public void setProductAttributeQueryConditionOld(GroupProductSearch groupSearch, Integer productId) {
		List<String> propsFilterList = new ArrayList<String>();
		List<ProductProps> propsOrderList = new ArrayList<ProductProps>();
		ProductAttribute productAttribute = new ProductAttribute();
		productAttribute.setProductId(productId);
		productAttribute.setIsDeleted(0);
		List<ProductAttribute> attriList = productAttributeMapper.selectList(productAttribute);
		StringBuffer conditionStr = new StringBuffer(); 
		int listSize = attriList.size();
		for (int i=0;i<listSize;i++) {
			conditionStr = new StringBuffer();
			ProductAttribute pa = attriList.get(i);
			ProductProps props = productPropsService.get(pa.getAttributeValueId());
			ProductProps parentProps = null;
			if( props != null ){
				parentProps = productPropsService.get(props.getPid());
			}
			
			if( parentProps != null ){
				if( StringUtils.isNotBlank(parentProps.getFilterOrder()) && "filter".equals(parentProps.getFilterOrder()) ){
					conditionStr.append("pa.attribute_key = '" + pa.getAttributeKey() + "' AND pa.attribute_type_value = '" + Utils.getTypeValueKey(pa.getAttributeTypeValue()) + "' AND pp.prop_value = " + props.getPropValue());
					propsFilterList.add(conditionStr.toString());
				}
				if( StringUtils.isNotBlank(parentProps.getFilterOrder()) && "order".equals(parentProps.getFilterOrder()) ){
					//分类搜索排序属性匹配度用到
					parentProps.setParentCode(pa.getAttributeKey());
					parentProps.setProductId(pa.getProductId());
					propsOrderList.add(parentProps);
				}
			}
		}
		groupSearch.setPropsOrderList(propsOrderList);
		groupSearch.setAttributeConditionList(propsFilterList);
		groupSearch.setAttributeConditionSize(propsFilterList.size());
	}


	/**
	 * 一键装修产品属性过滤条件
	 * @param productId
	 * @return
	 */
	@Override
	public void getOnekeyDecorateProductAttributeFilterCondition(BaseProductSearch baseProductSearch,Integer productId) {
		List<String> propsFilterList = new ArrayList<String>();
		ProductAttribute productAttribute = new ProductAttribute();
		productAttribute.setProductId(productId);
		productAttribute.setIsDeleted(0);
		List<ProductAttribute> attriList = productAttributeMapper.selectListMoreInfo(productAttribute);
		StringBuffer conditionStr = new StringBuffer();
		for (ProductAttribute pa : attriList) {
			conditionStr = new StringBuffer();
			if( StringUtils.isNotBlank(pa.getParentFilterOrder()) && "filter".equals(pa.getParentFilterOrder()) ){
				conditionStr.append("pa.attribute_key = '" + pa.getAttributeKey() + "' AND pa.attribute_type_value = '" + Utils.getTypeValueKey(pa.getAttributeTypeValue()) + "' AND pp.prop_value = " + pa.getPropValue());
				propsFilterList.add(conditionStr.toString());
			}
		}
		baseProductSearch.setAttributeConditionList(propsFilterList);
		baseProductSearch.setAttributeConditionSize(propsFilterList.size());

	}
	
}
