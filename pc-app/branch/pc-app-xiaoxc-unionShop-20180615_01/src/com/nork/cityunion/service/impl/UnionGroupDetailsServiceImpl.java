package com.nork.cityunion.service.impl;

import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nork.cityunion.model.UnionGroup;
import com.nork.cityunion.model.vo.UnionGroupDetailsVO;
import com.nork.cityunion.service.UnionGroupService;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.cityunion.dao.UnionGroupDetailsMapper;
import com.nork.cityunion.model.UnionGroupDetails;
import com.nork.cityunion.model.search.UnionGroupDetailsSearch;
import com.nork.cityunion.service.UnionGroupDetailsService;

/**   
 * @Title: UnionGroupDetailsServiceImpl.java 
 * @Package com.nork.cityunion.service.impl
 * @Description:同城联盟-联盟组明细表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:24:02
 * @version V1.0   
 */
@Service("unionGroupDetailsService")
public class UnionGroupDetailsServiceImpl implements UnionGroupDetailsService {

	private static Logger logger = Logger
			.getLogger(UnionGroupDetailsServiceImpl.class);

	private UnionGroupDetailsMapper unionGroupDetailsMapper;

	@Autowired
	private UnionGroupService unionGroupService;

	@Autowired
	public void setUnionGroupDetailsMapper(
			UnionGroupDetailsMapper unionGroupDetailsMapper) {
		this.unionGroupDetailsMapper = unionGroupDetailsMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param unionGroupDetails
	 * @return  int 
	 */
	@Override
	public int add(UnionGroupDetails unionGroupDetails) {
		unionGroupDetailsMapper.insertSelective(unionGroupDetails);
		return unionGroupDetails.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param unionGroupDetails
	 * @return  int 
	 */
	@Override
	public int update(UnionGroupDetails unionGroupDetails) {
		return unionGroupDetailsMapper
				.updateByPrimaryKeySelective(unionGroupDetails);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return unionGroupDetailsMapper.deleteByPrimaryKey(id);
	}
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int deleteById(Integer id) {
		return unionGroupDetailsMapper.deleteById(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UnionGroupDetails 
	 */
	@Override
	public UnionGroupDetails get(Integer id) {
		return unionGroupDetailsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  unionGroupDetails
	 * @return   List<UnionGroupDetails>
	 */
	@Override
	public List<UnionGroupDetails> getList(UnionGroupDetails unionGroupDetails) {
	    return unionGroupDetailsMapper.selectList(unionGroupDetails);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  unionGroupDetailsSearch
	 * @return   int
	 */
	@Override
	public int getCount(UnionGroupDetailsSearch unionGroupDetailsSearch){
		return  unionGroupDetailsMapper.selectCount(unionGroupDetailsSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  unionGroupDetailsSearch
	 * @return   List<UnionGroupDetails>
	 */
	@Override
	public List<UnionGroupDetails> getPaginatedList(
			UnionGroupDetailsSearch unionGroupDetailsSearch) {
		return unionGroupDetailsMapper.selectPaginatedList(unionGroupDetailsSearch);
	}

	@Override
	public int deleteByGroupId(Integer id) {
		// TODO Auto-generated method stub
		return unionGroupDetailsMapper.deleteByGroupId(id);
	}

	@Override
	public int batchInsertDataList(List<UnionGroupDetails> list) {
		// TODO Auto-generated method stub
		return unionGroupDetailsMapper.batchInsertDataList(list);
	}

	@Override
	public List<UnionGroupDetails> getListByGroupId(Integer groupId) {
		// TODO Auto-generated method stub
		return unionGroupDetailsMapper.getListByGroupId(groupId);
	}

	/**
	 * 其他
	 * 
	 */
	/**
	 * 保存联盟组信息
	 * @param groupDetailsList
	 * @param groupId
	 * @param groupName
	 * @param loginUser
	 * @return
	 */
	@Override
	public UnionGroupDetailsVO saveUnionGroupInfo(List<UnionGroupDetailsVO> groupDetailsList, Integer groupId, String groupName, LoginUser loginUser) {
		UnionGroupDetailsVO unionGroupDetailsVO = null;
		try {
			if (groupDetailsList == null || groupDetailsList.size() == 0) {
				logger.error("groupDetailsList = null or size = 0！");
				return null;
			}
			//TODO 同盟组信息保存或修改
			if (groupId == null || groupId == 0) {
				UnionGroup unionGroup = new UnionGroup();
				unionGroup.setGroupName(groupName);
				unionGroup.setUserId(loginUser.getId());
				unionGroupService.sysSave(unionGroup,loginUser);
				groupId = unionGroupService.add(unionGroup);
			} else {
				UnionGroup unionGroup = new UnionGroup();
				unionGroup.setId(groupId);
				unionGroup.setGroupName(groupName);
				unionGroup.setGmtModified(new Date());
				unionGroupService.update(unionGroup);
			}
			//TODO 同盟组成员信息新增或修改
			for (UnionGroupDetailsVO groupDetailsVO : groupDetailsList) {
				unionGroupDetailsVO = groupDetailsVO;
				if (groupDetailsVO.getGroupDetailsId() == null || groupDetailsVO.getGroupDetailsId() == 0) {
					UnionGroupDetails groupDetails = new UnionGroupDetails();
					groupDetails.setName(groupDetailsVO.getName());
					groupDetails.setBrandId(groupDetailsVO.getBrandId());
					groupDetails.setContact(groupDetailsVO.getContact());
					groupDetails.setPhone(groupDetailsVO.getPhone());
					groupDetails.setAddress(groupDetailsVO.getAddress());
					groupDetails.setGroupId(groupId);
					groupDetails.setUserId(loginUser.getId());
					sysSave(groupDetails,loginUser);
					int id = unionGroupDetailsMapper.insertSelective(groupDetails);
					unionGroupDetailsVO.setGroupDetailsId(groupDetails.getId());
				} else {
					UnionGroupDetails groupDetails = new UnionGroupDetails();
					groupDetails.setId(groupDetailsVO.getGroupDetailsId());
					groupDetails.setName(groupDetailsVO.getName());
					groupDetails.setBrandId(groupDetailsVO.getBrandId());
					groupDetails.setContact(groupDetailsVO.getContact());
					groupDetails.setPhone(groupDetailsVO.getPhone());
					groupDetails.setAddress(groupDetailsVO.getAddress());
					groupDetails.setGmtModified(new Date());
					groupDetails.setGroupId(groupId);
					unionGroupDetailsMapper.updateByPrimaryKeySelective(groupDetails);
				}
			}
			// 这里只传单个对象到前端，防止修改信息需要用到ID
			// 批量新增直接返回OK就行，因为前端批量保存之后直接跳到下个界面
			if (unionGroupDetailsVO != null) {
				unionGroupDetailsVO.setGroupId(groupId);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		return unionGroupDetailsVO;
	}


	/**
	 * 自动存储系统字段
	 */
	private void sysSave(UnionGroupDetails model,LoginUser loginUser){
		if (model != null) {
			if (loginUser == null) {
				loginUser = new LoginUser();
				loginUser.setLoginName("nologin");
			}
			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode()==null || "".equals(model.getSysCode())) {
					model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
				}
			}
			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}
}
