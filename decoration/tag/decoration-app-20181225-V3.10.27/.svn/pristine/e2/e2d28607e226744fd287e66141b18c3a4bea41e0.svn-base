package com.nork.product.service2;

import java.util.List;
import java.util.Map;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.product.model.GroupProduct;
import com.nork.product.model.GroupProductCollect;
import com.nork.product.model.search.GroupProductCollectSearch;

/**
 * @Title: GroupProductCollectService2.java
 * @Package com.nork.product.service2
 * @Description:产品模块-组合收藏表Service
 * @createAuthor yangzhun
 * @CreateDate 2017-6-19 16:57:37
 */
@SuppressWarnings("rawtypes")
public interface GroupProductCollectService2 {

	/**
	 * updateDetails方法验证参数
	 * 
	 * @author huangsongbo
	 * @param groupId
	 * @param productId
	 * @param type
	 * @param msgId
	 * @return
	 */
	public Map<String, Object> updateDetailsVerifyParams(Integer groupId,
			Integer productId, Integer type, String msgId);

	/**
	 * 取得收藏的封面图并保存
	 * 
	 * @author huangsongbo
	 * @param groupProductCollect
	 */
	public void savePicUrl(GroupProductCollect groupProductCollect);

	/**
	 * 收藏组合
	 * 
	 * @author huangsongbo
	 * @param groupProduct
	 * @param integer
	 * @return
	 */
	public int saveCollectByGroup(GroupProduct groupProduct, LoginUser loginUser);

	/**
	 * 删除组合收藏,通过参数groupId和userId
	 * 
	 * @author huangsongbo
	 * @param groupId
	 * @param id
	 * @return
	 */
	public int deleteByGroupIdAndUserId(Integer groupId, Integer id);

	/**
	 * 获取用户数据数量
	 * 
	 * @param groupProductCollect
	 * @return int
	 */
	public int getGroupProductCollectCount(
			GroupProductCollectSearch groupProductCollectSearch);

	/**
	 * 分页获取用户收藏数据
	 * 
	 * @param groupProductCollect
	 * @return List<GroupProductCollect>
	 */
	public List<GroupProductCollect> getGroupProductCollectList(
			GroupProductCollectSearch groupProductCollecttSearch);

	/**
	 * 编辑组合收藏接口(未完成,备用)
	 * 
	 * @author huangsongbo
	 * @param groupId 组合id
	 * @param productId 产品id
	 * @param type 0:新增
	 * @param msgId
	 * @return
	 */
	public ResponseEnvelope updateDetails(Integer groupId, Integer productId,
			Integer type, String msgId);

	/**
	 * 组合收藏列表
	 * 
	 * @param groupProductCollectSearch
	 * @param spaceCommonId
	 * @param loginUser
	 * @param mediaType
	 * @return
	 */
	public ResponseEnvelope list(GroupProductCollectSearch groupProductCollectSearch,
			Integer spaceCommonId, LoginUser loginUser, String mediaType);

	/**
	 * 得到组合收藏详情接口
	 * @param collectId
	 * @param msgId
	 * @return
	 */
	public ResponseEnvelope getDetails(Integer collectId,String msgId);
	
	/**
	 * 收藏组合接口
	 * @param groupId
	 * @param type
	 * @param msgId
	 * @return
	 */
	public ResponseEnvelope collectGroup(Integer groupId,String type,String msgId, LoginUser loginUser);
}
