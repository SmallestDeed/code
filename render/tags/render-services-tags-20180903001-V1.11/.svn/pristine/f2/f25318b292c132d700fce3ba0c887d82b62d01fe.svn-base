package com.nork.product.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.util.Utils;
import com.nork.product.dao.StructureProductDetailsMapper;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.StructureProductDetails;
import com.nork.product.model.search.StructureProductDetailsSearch;
import com.nork.product.service.StructureProductDetailsService;
import com.nork.system.service.ResModelService;

/**   
 * @Title: StructureProductDetailsServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:产品模块-结构明细表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-12-02 15:42:46
 * @version V1.0   
 */
@Service("structureProductDetailsService")
public class StructureProductDetailsServiceImpl implements StructureProductDetailsService {

	@Autowired
	private StructureProductDetailsMapper structureProductDetailsMapper;
	
	@Autowired
	private ResModelService resModelService;

	/**
	 * 新增数据
	 *
	 * @param structureProductDetails
	 * @return  int 
	 */
	@Override
	public int add(StructureProductDetails structureProductDetails) {
		structureProductDetailsMapper.insertSelective(structureProductDetails);
		return structureProductDetails.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param structureProductDetails
	 * @return  int 
	 */
	@Override
	public int update(StructureProductDetails structureProductDetails) {
		return structureProductDetailsMapper
				.updateByPrimaryKeySelective(structureProductDetails);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return structureProductDetailsMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  StructureProductDetails 
	 */
	@Override
	public StructureProductDetails get(Integer id) {
		return structureProductDetailsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  structureProductDetails
	 * @return   List<StructureProductDetails>
	 */
	@Override
	public List<StructureProductDetails> getList(StructureProductDetails structureProductDetails) {
	    return structureProductDetailsMapper.selectList(structureProductDetails);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  structureProductDetails
	 * @return   int
	 */
	@Override
	public int getCount(StructureProductDetailsSearch structureProductDetailsSearch){
		return  structureProductDetailsMapper.selectCount(structureProductDetailsSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  structureProductDetails
	 * @return   List<StructureProductDetails>
	 */
	@Override
	public List<StructureProductDetails> getPaginatedList(
			StructureProductDetailsSearch structureProductDetailsSearch) {
		return structureProductDetailsMapper.selectPaginatedList(structureProductDetailsSearch);
	}

	/**
	 * 删除结构明细(By 结构id)
	 * @author huangsongbo
	 * @param id
	 */
	public void deleteByStructureId(Integer structureId) {
		structureProductDetailsMapper.deleteByStructureId(structureId);
	}

	/**
	 * 创建结构明细(用过结构id和产品id)
	 * @author huangsongbo
	 * @param structureId
	 * @param productId
	 * @return
	 */
	public StructureProductDetails createByStructureIdAndProductId(Integer structureId, Integer productId) {
		StructureProductDetails structureProductDetails=new StructureProductDetails();
		structureProductDetails.setStructureId(structureId);
		structureProductDetails.setProductId(productId);
		sysSave(structureProductDetails);
		structureProductDetails.setSorting(new Integer(0));
		return structureProductDetails;
	}

	/**
	 * 填充字段
	 * @author huangsongbo
	 * @param model
	 */
	private void sysSave(StructureProductDetails model) {
		if (model != null) {
			String loginUserName="nologin";
			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUserName);
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(
							Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				}
			}
			model.setGmtModified(new Date());
			model.setModifier(loginUserName);
		}
	}

	/**
	 * 通过structureId查找StructureProductDetails
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	public List<StructureProductDetails> findAllByStructureId(Integer structureId) {
		StructureProductDetailsSearch structureProductDetailsSearch=new StructureProductDetailsSearch();
		structureProductDetailsSearch.setStart(new Integer(-1));
		structureProductDetailsSearch.setLimit(new Integer(-1));
		structureProductDetailsSearch.setStructureId(structureId);
		return this.getPaginatedList(structureProductDetailsSearch);
	}

	/**
	 * 获取modeCode:StructureProductDetails的map(为了自动匹配cameraLook和cameraView属性)
	 * @author huangsongbo
	 * @param productCodeList
	 * @return
	 */
	public Map<String, StructureProductDetails> getStructureProductDetailsInfoMap(Integer structureId) {
		Map<String, StructureProductDetails> returnMap = new HashMap<String, StructureProductDetails>();
		List<StructureProductDetails> structureProductDetailList = this.findAllByStructureId(structureId);
		for(StructureProductDetails structureProductDetail : structureProductDetailList){
			String resModelCode = resModelService.getModelCodeByProductId(structureProductDetail.getProductId());
			if(StringUtils.isNotBlank(resModelCode))
				returnMap.put(resModelCode, structureProductDetail);
		}
		return returnMap;
	}
}
