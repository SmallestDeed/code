package com.nork.product.service2.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.product.dao2.GroupCollectDetailsMapper;
import com.nork.product.model.GroupCollectDetails;
import com.nork.product.model.GroupProductDetails;
import com.nork.product.service2.GroupCollectDetailsService2;

/**
 * @Title: GroupCollectDetailsServiceImpl2.java
 * @Package com.nork.product.service2.impl
 * @Description:产品模块-组合收藏明细表ServiceImpl
 * @createAuthor yangzhun
 * @CreateDate 2017-6-15 17:34:51
 */
@Service("groupCollectDetailsService2")
public class GroupCollectDetailsServiceImpl2 implements
		GroupCollectDetailsService2 {

	@Autowired
	private GroupCollectDetailsMapper groupCollectDetailsMapper2;

	/**
	 * 通过组合收藏id查找该收藏下的产品信息
	 * 
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	@Override
	public List<GroupCollectDetails> findAllByGroupId(Integer id) {
		return groupCollectDetailsMapper2.findAllByGroupId(id);
	}

	/**
	 * 保存收藏明细
	 * 
	 * @author huangsongbo
	 * @param groupProductDetails 组合明细
	 * @param id 组合收藏id
	 */
	@Override
	public void saveByGroupDetails(GroupProductDetails groupProductDetails,
			int id, LoginUser loginUser) {
		GroupCollectDetails groupCollectDetails = new GroupCollectDetails();
		groupCollectDetails.setGroupCollectId(id);
		groupCollectDetails.setProductId(groupProductDetails.getProductId());
		groupCollectDetails.setIsMain(groupProductDetails.getIsMain());
		sysSave(groupCollectDetails, loginUser);
		groupCollectDetailsMapper2.insertSelective(groupCollectDetails);
	}

	/**
	 * 自动储存字段
	 * 
	 * @param model
	 * @param loginUser
	 */
	private void sysSave(GroupCollectDetails model, LoginUser loginUser) {
		if (model != null) {
			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(Utils
							.getCurrentDateTime(Utils.DATETIMESSS)
							+ "_"
							+ Utils.generateRandomDigitString(6));
				}
			}
			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	@Override
	public void deleteByGroupCollectId(Integer groupCollectId) {
		groupCollectDetailsMapper2.deleteByGroupCollectId(groupCollectId);
	}

	/**
	 * 删除组合收藏明细接口
	 * 
	 */
	@Override
	public ResponseEnvelope delete(Integer id, String msgId) {
		if (StringUtils.isBlank(msgId))
			return new ResponseEnvelope<>(false,
					SystemCommonConstant.MSGID_NOT_NULL, msgId);
		if (id == null)
			return new ResponseEnvelope<>(false,
					SystemCommonConstant.ID_NOT_NULL, msgId);
		int num = groupCollectDetailsMapper2.deleteByPrimaryKey(id);
		if (num < 1)
			return new ResponseEnvelope<>(false,
					SystemCommonConstant.COLLECTION_DETAIL_NOT_EXIST, msgId);
		return new ResponseEnvelope<>(true,
				SystemCommonConstant.DELETE_SUCCESS, msgId);
	}

}
