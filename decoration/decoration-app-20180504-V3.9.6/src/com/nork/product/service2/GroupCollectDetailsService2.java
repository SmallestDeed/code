package com.nork.product.service2;

import java.util.List;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.product.model.GroupCollectDetails;
import com.nork.product.model.GroupProductDetails;

/**   
 * @Title: GroupCollectDetailsService.java 
 * @Package com.nork.product.service2
 * @Description:产品模块-组合收藏明细表Service
 * @createAuthor yangzhun
 * @CreateDate 2017-6-15 17:33:51
 */
public interface GroupCollectDetailsService2 {

	
	/**
	 * 通过组合收藏id查找该收藏下的产品信息
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	public List<GroupCollectDetails> findAllByGroupId(Integer id);

	/**
	 * 保存收藏明细
	 * @author huangsongbo
	 * @param groupProductDetails
	 * @param id
	 * @param loginUser 
	 */
	public void saveByGroupDetails(GroupProductDetails groupProductDetails, int id, LoginUser loginUser);

	/**
	 * 删除收藏明细表中关联的数据
	 * @author huangsongbo
	 * @param id
	 */
	public void deleteByGroupCollectId(Integer id);
	
	
	/**
	 * 删除组合收藏明细接口
	 * @param id
	 * @param msgId
	 * @return
	 */
	public ResponseEnvelope delete(Integer id,String msgId);
}
