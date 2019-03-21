package com.nork.product.service;

import java.util.List;
import java.util.Map;

import com.nork.common.model.LoginUser;
import com.nork.product.model.GroupProduct;
import com.nork.product.model.GroupProductCollect;
import com.nork.product.model.search.GroupProductCollectSearch;

/**   
 * @Title: GroupProductCollectService.java 
 * @Package com.nork.product.service
 * @Description:产品模块-组合收藏表Service
 * @createAuthor pandajun 
 * @CreateDate 2016-06-22 19:46:06
 * @version V1.0   
 */
public interface GroupProductCollectService {
	/**
	 * 新增数据
	 *
	 * @param groupProductCollect
	 * @return  int 
	 */
	public int add(GroupProductCollect groupProductCollect);

	/**
	 *    更新数据
	 *
	 * @param groupProductCollect
	 * @return  int 
	 */
	public int update(GroupProductCollect groupProductCollect);

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
	 * @return  GroupProductCollect 
	 */
	public GroupProductCollect get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  groupProductCollect
	 * @return   List<GroupProductCollect>
	 */
	public List<GroupProductCollect> getList(GroupProductCollect groupProductCollect);

	/**
	 *    获取数据数量
	 *
	 * @param  groupProductCollect
	 * @return   int
	 */
	public int getCount(GroupProductCollectSearch groupProductCollectSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  groupProductCollect
	 * @return   List<GroupProductCollect>
	 */
	public List<GroupProductCollect> getPaginatedList(
				GroupProductCollectSearch groupProductCollecttSearch);

	/**
	 * updateDetails方法验证参数
	 * @author huangsongbo
	 * @param groupId
	 * @param productId
	 * @param type
	 * @param msgId 
	 * @return
	 */
	public Map<String, Object> updateDetailsVerifyParams(Integer groupId, Integer productId, Integer type, String msgId);

	/**
	 * 取得收藏的封面图并保存
	 * @author huangsongbo
	 * @param groupProductCollect
	 */
	public void savePicUrl(GroupProductCollect groupProductCollect);

	/**
	 * 收藏组合
	 * @author huangsongbo
	 * @param groupProduct
	 * @param integer 
	 * @return
	 */
	public int saveCollectByGroup(GroupProduct groupProduct, LoginUser loginUser);

	/**
	 * 删除组合收藏,通过参数groupId和userId
	 * @author huangsongbo
	 * @param groupId
	 * @param id
	 * @return
	 */
	public int deleteByGroupIdAndUserId(Integer groupId, Integer id);
	
	/**
	 *    获取用户数据数量
	 *
	 * @param  groupProductCollect
	 * @return   int
	 */
	public int getGroupProductCollectCount(GroupProductCollectSearch groupProductCollectSearch);

	/**
	 *    分页获取用户收藏数据
	 *
	 * @param  groupProductCollect
	 * @return   List<GroupProductCollect>
	 */
	public List<GroupProductCollect> getGroupProductCollectList(
				GroupProductCollectSearch groupProductCollecttSearch);
	

}
