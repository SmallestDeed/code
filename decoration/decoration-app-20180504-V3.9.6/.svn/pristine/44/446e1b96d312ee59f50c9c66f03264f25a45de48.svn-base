package com.nork.product.dao2;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.GroupProduct;
import com.nork.product.model.GroupProductDetails;
import com.nork.product.model.GroupProductResult;
import com.nork.product.model.search.BaseBrandSearch;
import com.nork.product.model.search.GroupProductSearch;

/**
 * @Title: GroupProductMapper.java
 * @Package com.nork.product.dao2
 * @Description:产品模块-产品组合主表Mapper
 * @createAuthor yangzhun
 * @CreateDate 2017-6-21 13:48:38
 */
public interface GroupProductMapper extends BaseMapper<GroupProduct> {

	/**
	 * @Description 获取组合列表 </p>
	 * @param groupProduct
	 * @return
	 */
	List<GroupProductResult> selectCommonList(GroupProduct groupProduct);

	/**
	 * 其他
	 * 
	 */

	int getGroupCountByProduct(GroupProductSearch groupSearch);

	/**
	 * 根据产品ID，通过产品你ID查询产品组信息
	 * 
	 * @param groupSearch
	 * @return
	 */
	List<GroupProduct> getGroupListByProduct(GroupProductSearch groupSearch);

	/**
	 * 根据品牌查询组合（组合中包括关联产品）
	 * 
	 * @param brandName
	 * @return GroupProduct
	 */
	List<GroupProductDetails> queryBrandGroupList(BaseBrandSearch ResBrand);

	int queryBrandGroupCount(BaseBrandSearch ResBrand);

	/**
	 * 计算组合中所有产品的价格之和
	 * 
	 * @author huangsongbo
	 * @param groupId
	 * @return
	 */
	Double getPriceFromDetails(Integer groupId);

	// int getGroupCountByStructureId(GroupProductSearch groupSearch);
	//
	// List<GroupProduct> getGroupListByStructureId(GroupProductSearch
	// groupSearch);

	int getGroupCountByStructureId(Integer structureId);

	List<GroupProduct> getGroupListByStructureId(Integer structureId);

	GroupProduct getStructureByGroupId(Integer groupId);

	Integer getIsMainProductV2(Integer productId);

	int getGroupCountByStructureIdAndStatus(
			@Param("structureId") Integer structureId,
			@Param("userType") Integer usertype,
			@Param("brandIdList") String[] brandIdList,
			@Param("versionType") String versionType);

	/**
	 * 通过id查找组合(关联查找更多信息,比如:品牌名,风格名)
	 * 
	 * @author huangsongbo
	 * @param groupId
	 * @return
	 */
	GroupProduct getMoreInfoById(@Param("groupId") Integer groupId);

	void updateStatus(@Param("oldStatus") Integer oldStatus,
			@Param("newStatus") Integer newStatus);

}
