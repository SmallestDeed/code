package com.nork.product.service2;

import java.util.List;

import com.nork.common.model.LoginUser;
import com.nork.product.model.GroupProduct;
import com.nork.product.model.GroupProductDetails;
import com.nork.product.model.GroupProductResult;
import com.nork.product.model.search.BaseBrandSearch;
import com.nork.product.model.search.GroupProductSearch;

/**   
 * @Title: GroupProductService2.java 
 * @Package com.nork.product.service2
 * @Description:产品模块-产品组合主表Service
 * @createAuthor yangzhun 
 * @CreateDate 2017-6-21 13:58:18
 */
public interface GroupProductService2 {
	
	/**
	 * @Description 获取组合列表 </p>
	 * @param groupProduct
	 * @return
	 * @throws Exception
	 */
	List<GroupProductResult> selectCommonList(GroupProduct groupProduct)throws Exception;

	/**
	 * 其他
	 * 
	 */

	int getGroupCountByProduct(GroupProductSearch groupSearch);

	List<GroupProduct> getGroupListByProduct(GroupProductSearch productSearch);
	
	
	/**
	 * 根据品牌查询组合（组合中包括关联产品）
	 *
	 * @param brandName
	 * @return  GroupProduct 
	 */
	public List<GroupProductDetails> queryBrandGroupList(BaseBrandSearch ResBrand);

	public int queryBrandGroupCount(BaseBrandSearch ResBrand);

	/**
	 * 得到组合的价格->组合表有价格信息就取价格信息,没有的话就把该组合的产品价格相加
	 * @author huangsongbo
	 * @param groupId
	 * @return
	 */
	public Double getSalePrice(Integer groupId);

//	public int getGroupCountByStructureId(GroupProductSearch groupSearch);
//
//	public List<GroupProduct> getGroupListByStructureId(GroupProductSearch groupSearch);

	public int getGroupCountByStructureId(Integer structureId);

	public List<GroupProduct> getGroupListByStructureId(Integer structureId);

	public GroupProduct getStructureByGroupId(Integer groupId);

	/**
	 * 判断该产品是不是否是主产品
	 * @param cacheEnable 
	 * @param productId 产品id
	 * @param cacheEnable 是否开启缓存
	 * @return
	 */
	public Integer getIsMainProduct(Integer productId, String cacheEnable);

	public Integer getIsMainProductV2(Integer id);
	
	/**
	 * 根据结构id和组合状态查找结构组合数量
	 * @author huangsongbo
	 * @param structureId
	 * @param usertype  用户类型   add by yanghz(根据用户类型查询不同状态数据)
	 * @return
	 */
	public int getGroupCountByStructureIdAndStatus(Integer structureId, Integer usertype,String [] brandIdList,String versionType);

	/**
	 * 通过id查找组合(关联查找更多信息,比如:品牌名,风格名)
	 * @author huangsongbo
	 * @param groupId
	 * @return
	 */
	public GroupProduct getMoreInfoById(Integer groupId);

	/**
	 * 批量修改组合状态
	 * @author huangsongbo
	 * @param oldStatus
	 * @param newStatus
	 */
	public void updateStatus(Integer oldStatus, Integer newStatus);
	
	/**
	 * 产品组合详情接口
	 * 
	 * @param msgId
	 * @param groupId
	 * @param loginUser
	 * @param mediaType
	 * @return
	 */
	public Object getGroupProductDetails(String msgId,Integer groupId,LoginUser loginUser,String mediaType);
	
	/**
	 * 根据品牌查询组合（组合中包括关联产品）
	 * @param baseBrandSearch
	 * @param msgId
	 * @param loginUser
	 * @return
	 */
	public Object queryGroupByBrand(BaseBrandSearch baseBrandSearch,String msgId,LoginUser loginUser);
	
	/**
	 * 一键替换组合
	 * @param templetId
	 * @param designTempletId
	 * @param msgId
	 * @return
	 */
	public Object getGroupProductData(Integer templetId,Integer designTempletId,String msgId);
}
