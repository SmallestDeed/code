package com.nork.cityunion.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.nork.cityunion.dao.UnionDesignPlanStoreReleaseMapper;
import com.nork.cityunion.dao.UnionGroupDetailsMapper;
import com.nork.cityunion.model.UnionDesignPlanStoreRelease;
import com.nork.cityunion.model.UnionGroupDetails;
import com.nork.cityunion.model.vo.UnionGroupDetailsVo;
import com.nork.cityunion.model.vo.UnionGroupVo;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.pano.dao.DesignPlanStoreReleaseMapper;
import com.nork.pano.model.DesignPlanStoreRelease;
import com.nork.pano.model.output.DesignPlanStoreReleaseVo;
import com.nork.product.dao.BaseCompanyMapper;
import com.nork.product.model.BaseCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.cityunion.dao.UnionGroupMapper;
import com.nork.cityunion.model.UnionGroup;
import com.nork.cityunion.model.search.UnionGroupSearch;
import com.nork.cityunion.service.UnionGroupService;

/**   
 * @Title: UnionGroupServiceImpl.java 
 * @Package com.nork.cityunion.service.impl
 * @Description:同城联盟-联盟分组表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:23:22
 * @version V1.0   
 */
@Service("unionGroupService")
public class UnionGroupServiceImpl implements UnionGroupService {

	private UnionGroupMapper unionGroupMapper;

	@Autowired
	public void setUnionGroupMapper(
			UnionGroupMapper unionGroupMapper) {
		this.unionGroupMapper = unionGroupMapper;
	}
	@Autowired
	private UnionGroupDetailsMapper unionGroupDetailsMapper;
	@Autowired
	private DesignPlanStoreReleaseMapper designPlanStoreReleaseMapper;
	@Autowired
	private UnionDesignPlanStoreReleaseMapper unionDesignPlanStoreReleaseMapper;
	@Autowired
	private BaseCompanyMapper baseCompanyMapper;

	/**
	 * 新增数据
	 *
	 * @param unionGroup
	 * @return  int 
	 */
	@Override
	public int add(UnionGroup unionGroup) {
		unionGroupMapper.insertSelective(unionGroup);
		return unionGroup.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param unionGroup
	 * @return  int 
	 */
	@Override
	public int update(UnionGroup unionGroup) {
		return unionGroupMapper
				.updateByPrimaryKeySelective(unionGroup);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return unionGroupMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UnionGroup 
	 */
	@Override
	public UnionGroup get(Integer id) {
		return unionGroupMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  unionGroup
	 * @return   List<UnionGroup>
	 */
	@Override
	public List<UnionGroup> getList(UnionGroup unionGroup) {
	    return unionGroupMapper.selectList(unionGroup);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  unionGroupSearch
	 * @return   int
	 */
	@Override
	public int getCount(UnionGroupSearch unionGroupSearch){
		return  unionGroupMapper.selectCount(unionGroupSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  unionGroupSearch
	 * @return   List<UnionGroup>
	 */
	@Override
	public List<UnionGroup> getPaginatedList(
			UnionGroupSearch unionGroupSearch) {
		return unionGroupMapper.selectPaginatedList(unionGroupSearch);
	}

	@Override
	public UnionGroupVo getUnionGroupByStoreRelease(Integer id) {
		UnionGroupVo unionGroupVo = null;
		if(id == null){
			return null;
		}
		UnionGroup unionGroup = this.get(id);
		if(unionGroup == null){
			return null;
		}
		unionGroupVo = this.transformUnionGroupToVo(unionGroup);
		UnionGroupDetails detailSearch = new UnionGroupDetails();
		detailSearch.setGroupId(id);
		detailSearch.setIsDeleted(0);
		List<UnionGroupDetails> groupDetailList = unionGroupDetailsMapper.selectList(detailSearch);
		if(CustomerListUtils.isNotEmpty(groupDetailList)){
			List<UnionGroupDetailsVo> detailsVoList = new ArrayList<>();
			for (UnionGroupDetails details : groupDetailList){
				UnionGroupDetailsVo detailsVo = new UnionGroupDetailsVo();
				Integer companyId = details.getCompanyId();
			/*	if(companyId == null || companyId <= 0){
					continue;
				}
				BaseCompany baseCompany = baseCompanyMapper.selectByPrimaryKey(companyId);*/
				detailsVo.setName(details.getName());
				detailsVo.setPhone(details.getPhone());
				detailsVo.setAddress(details.getAddress());
				detailsVoList.add(detailsVo);
			}
			unionGroupVo.setDetailsVos(detailsVoList);
		}

		return unionGroupVo;
	}

	@Override
	public UnionGroupVo getUnionGroupByUnionStoreRelease(Integer id) {
		UnionGroupVo unionGroupVo = null;
		if(id == null){
			return null;
		}
		UnionGroup unionGroup = this.get(id);
		if(unionGroup == null){
			return null;
		}
		unionGroupVo = this.transformUnionGroupToVo(unionGroup);
		UnionGroupDetails detailSearch = new UnionGroupDetails();
		detailSearch.setGroupId(id);
		detailSearch.setIsDeleted(0);
		List<UnionGroupDetails> groupDetailList = unionGroupDetailsMapper.selectList(detailSearch);
		if(CustomerListUtils.isNotEmpty(groupDetailList)){
			List<UnionGroupDetailsVo> detailsVoList = new ArrayList<>();
			for (UnionGroupDetails details : groupDetailList){
				UnionGroupDetailsVo detailsVo = new UnionGroupDetailsVo();
				detailsVo = this.transformUnionGroupDetailsToVo(details);
				detailsVoList.add(detailsVo);
			}
			unionGroupVo.setDetailsVos(detailsVoList);
		}
		return unionGroupVo;
	}

	/**
	 * 其他
	 * 
	 */

	public UnionGroupVo transformUnionGroupToVo(UnionGroup unionGroup){
		if(unionGroup == null){
			return null;
		}
		UnionGroupVo unionGroupVo = new UnionGroupVo();
		unionGroupVo.setId(unionGroup.getId());
		unionGroupVo.setGroupName(unionGroup.getGroupName());
		return unionGroupVo;
	}

	public UnionGroupDetailsVo transformUnionGroupDetailsToVo(UnionGroupDetails groupDetails){
		if(groupDetails == null){
			return null;
		}
		UnionGroupDetailsVo groupDetailsVo = new UnionGroupDetailsVo();
		groupDetailsVo.setName(groupDetails.getName());
		groupDetailsVo.setPhone(groupDetails.getPhone());
		groupDetailsVo.setAddress(groupDetails.getAddress());
		return groupDetailsVo;
	}
}
