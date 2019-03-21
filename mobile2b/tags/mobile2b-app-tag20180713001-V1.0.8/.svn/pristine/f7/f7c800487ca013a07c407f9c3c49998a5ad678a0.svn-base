package com.nork.product.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.util.Utils;
import com.nork.product.dao.ProductUsageCountMapper;
import com.nork.product.model.ProductUsageCount;
import com.nork.product.model.search.ProductUsageCountSearch;
import com.nork.product.service.ProductUsageCountService;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysUserService;

/**   
 * @Title: ProductUsageCountServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:产品模块-使用次数记录表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-07-26 16:18:44
 * @version V1.0   
 */
@Service("productUsageCountService")
public class ProductUsageCountServiceImpl implements ProductUsageCountService {
	
	@Autowired
	private ProductUsageCountMapper productUsageCountMapper;
	@Autowired
	private SysUserService sysUserService;

	/**
	 * 新增数据
	 *
	 * @param productUsageCount
	 * @return  int 
	 */
	@Override
	public int add(ProductUsageCount productUsageCount) {
		productUsageCountMapper.insertSelective(productUsageCount);
		return productUsageCount.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param productUsageCount
	 * @return  int 
	 */
	@Override
	public int update(ProductUsageCount productUsageCount) {
		return productUsageCountMapper
				.updateByPrimaryKeySelective(productUsageCount);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return productUsageCountMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  ProductUsageCount 
	 */
	@Override
	public ProductUsageCount get(Integer id) {
		return productUsageCountMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  productUsageCount
	 * @return   List<ProductUsageCount>
	 */
	@Override
	public List<ProductUsageCount> getList(ProductUsageCount productUsageCount) {
	    return productUsageCountMapper.selectList(productUsageCount);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  productUsageCount
	 * @return   int
	 */
	@Override
	public int getCount(ProductUsageCountSearch productUsageCountSearch){
		return  productUsageCountMapper.selectCount(productUsageCountSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  productUsageCount
	 * @return   List<ProductUsageCount>
	 */
	@Override
	public List<ProductUsageCount> getPaginatedList(
			ProductUsageCountSearch productUsageCountSearch) {
		return productUsageCountMapper.selectPaginatedList(productUsageCountSearch);
	}

	/**
	 * 更新产品使用次数
	 * @author huangsongbo
	 * @param userId
	 * @param productId
	 */
	public void update(Integer userId, Integer productId, int length) {
		ProductUsageCountSearch productUsageCountSearch=new ProductUsageCountSearch();
		productUsageCountSearch.setStart(0);
		productUsageCountSearch.setLimit(1);
		productUsageCountSearch.setProductId(productId);
		productUsageCountSearch.setUserId(userId);
		List<ProductUsageCount> list=getPaginatedList(productUsageCountSearch);
		if(list!=null&&list.size()>0){
			/*更新*/
				ProductUsageCount productUsageCount=list.get(0);
				if( productUsageCount != null ){
					ProductUsageCount newProductUsageCount = new ProductUsageCount();
					newProductUsageCount.setId(productUsageCount.getId());
					newProductUsageCount.setCount(productUsageCount.getCount()+length);
					newProductUsageCount.setGmtModified(new Date());
					update(newProductUsageCount);
				}
		}else{
			/*新建*/
			ProductUsageCount productUsageCount=new ProductUsageCount();
			productUsageCount.setUserId(userId);
			productUsageCount.setProductId(productId);
			productUsageCount.setCount(new Integer(length));
			sysSave(productUsageCount);
			add(productUsageCount);
		}
	}
	
	private void sysSave(ProductUsageCount model){
		if(model != null){
			String nickname="unknow";
			if(model.getUserId()!=null&&model.getUserId()>0){
				SysUser sysUser=sysUserService.get(model.getUserId());
				if(sysUser!=null)
					nickname=sysUser.getNickName();
			}
			if(model.getId() == null){
				model.setGmtCreate(new Date());
				model.setCreator(nickname);
				model.setIsDeleted(0);
			    if(model.getSysCode()==null || "".equals(model.getSysCode())){
				   model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
			   }
			}
			model.setGmtModified(new Date());
			model.setModifier(nickname);
		}
	}
	
}
