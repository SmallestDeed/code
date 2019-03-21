package com.nork.product.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.model.LoginUser;
import com.nork.common.util.Utils;
import com.nork.product.dao.GroupCollectDetailsMapper;
import com.nork.product.model.GroupCollectDetails;
import com.nork.product.model.GroupProductDetails;
import com.nork.product.model.search.GroupCollectDetailsSearch;
import com.nork.product.service.GroupCollectDetailsService;

/**   
 * @Title: GroupCollectDetailsServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:产品模块-组合收藏明细表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-06-22 20:41:47
 * @version V1.0   
 */
@Service("groupCollectDetailsService")
public class GroupCollectDetailsServiceImpl implements GroupCollectDetailsService {

	private GroupCollectDetailsMapper groupCollectDetailsMapper;

	@Autowired
	public void setGroupCollectDetailsMapper(
			GroupCollectDetailsMapper groupCollectDetailsMapper) {
		this.groupCollectDetailsMapper = groupCollectDetailsMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param groupCollectDetails
	 * @return  int 
	 */
	@Override
	public int add(GroupCollectDetails groupCollectDetails) {
		groupCollectDetailsMapper.insertSelective(groupCollectDetails);
		return groupCollectDetails.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param groupCollectDetails
	 * @return  int 
	 */
	@Override
	public int update(GroupCollectDetails groupCollectDetails) {
		return groupCollectDetailsMapper
				.updateByPrimaryKeySelective(groupCollectDetails);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return groupCollectDetailsMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  GroupCollectDetails 
	 */
	@Override
	public GroupCollectDetails get(Integer id) {
		return groupCollectDetailsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  groupCollectDetails
	 * @return   List<GroupCollectDetails>
	 */
	@Override
	public List<GroupCollectDetails> getList(GroupCollectDetails groupCollectDetails) {
	    return groupCollectDetailsMapper.selectList(groupCollectDetails);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  groupCollectDetails
	 * @return   int
	 */
	@Override
	public int getCount(GroupCollectDetailsSearch groupCollectDetailsSearch){
		return  groupCollectDetailsMapper.selectCount(groupCollectDetailsSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  groupCollectDetails
	 * @return   List<GroupCollectDetails>
	 */
	@Override
	public List<GroupCollectDetails> getPaginatedList(
			GroupCollectDetailsSearch groupCollectDetailsSearch) {
		return groupCollectDetailsMapper.selectPaginatedList(groupCollectDetailsSearch);
	}

	/**
	 * 通过组合收藏id查找该收藏下的产品信息
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	public List<GroupCollectDetails> findAllByGroupId(Integer id) {
		return groupCollectDetailsMapper.findAllByGroupId(id);
	}

	/**
	 * 保存收藏明细
	 * @author huangsongbo
	 * @param groupProductDetails 组合明细
	 * @param id 组合收藏id
	 */
	public void saveByGroupDetails(GroupProductDetails groupProductDetails, int id, LoginUser loginUser) {
		GroupCollectDetails groupCollectDetails=new GroupCollectDetails();
		groupCollectDetails.setGroupCollectId(id);
		groupCollectDetails.setProductId(groupProductDetails.getProductId());
		groupCollectDetails.setIsMain(groupProductDetails.getIsMain());
		sysSave(groupCollectDetails, loginUser);
		add(groupCollectDetails);
	}
	
	private void sysSave(GroupCollectDetails model, LoginUser loginUser){
		if(model != null){
			if(model.getId() == null){
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
			    if(model.getSysCode()==null || "".equals(model.getSysCode())){
				   model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
			   }
			}
			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	@Override
	public void deleteByGroupCollectId(Integer groupCollectId) {
		groupCollectDetailsMapper.deleteByGroupCollectId(groupCollectId);
	}
	
}
