package com.nork.product.service;

import java.util.List;

import java.util.Map;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.StructureProductDetails;
import com.nork.product.model.search.StructureProductDetailsSearch;

/**   
 * @Title: StructureProductDetailsService.java 
 * @Package com.nork.product.service
 * @Description:产品模块-结构明细表Service
 * @createAuthor pandajun 
 * @CreateDate 2016-12-02 15:42:46
 * @version V1.0   
 */
public interface StructureProductDetailsService {
	/**
	 * 新增数据
	 *
	 * @param structureProductDetails
	 * @return  int 
	 */
	public int add(StructureProductDetails structureProductDetails);

	/**
	 *    更新数据
	 *
	 * @param structureProductDetails
	 * @return  int 
	 */
	public int update(StructureProductDetails structureProductDetails);

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
	 * @return  StructureProductDetails 
	 */
	public StructureProductDetails get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  structureProductDetails
	 * @return   List<StructureProductDetails>
	 */
	public List<StructureProductDetails> getList(StructureProductDetails structureProductDetails);

	/**
	 *    获取数据数量
	 *
	 * @param  structureProductDetails
	 * @return   int
	 */
	public int getCount(StructureProductDetailsSearch structureProductDetailsSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  structureProductDetails
	 * @return   List<StructureProductDetails>
	 */
	public List<StructureProductDetails> getPaginatedList(
				StructureProductDetailsSearch structureProductDetailstSearch);

	/**
	 * 删除结构明细(By 结构id)
	 * @author huangsongbo
	 * @param id
	 */
	public void deleteByStructureId(Integer id);

	/**
	 * 创建结构明细(用过结构id和产品id)
	 * @author huangsongbo
	 * @param structureId
	 * @param productId
	 * @return
	 */
	public StructureProductDetails createByStructureIdAndProductId(Integer structureId, Integer productId);

	/**
	 * 通过structureId查找StructureProductDetails
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	public List<StructureProductDetails> findAllByStructureId(Integer structureId);

	/**
	 * 获取modeCode:StructureProductDetails的map(为了自动匹配cameraLook和cameraView属性)
	 * @author huangsongbo
	 * @param productCodeList
	 * @return
	 */
	public Map<String, StructureProductDetails> getStructureProductDetailsInfoMap(Integer id);
}
