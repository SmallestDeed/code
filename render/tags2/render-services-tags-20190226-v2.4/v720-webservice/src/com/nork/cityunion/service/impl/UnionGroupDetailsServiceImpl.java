package com.nork.cityunion.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.cityunion.dao.UnionGroupDetailsMapper;
import com.nork.cityunion.model.UnionGroupDetails;
import com.nork.cityunion.model.search.UnionGroupDetailsSearch;
import com.nork.cityunion.model.vo.UnionGroupDetailsVo;
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

	private UnionGroupDetailsMapper unionGroupDetailsMapper;

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
	


    @Override
    public List<UnionGroupDetailsVo> getUnionGroupDetailsLsByReleaseId(Integer releaseId) {
      List<UnionGroupDetailsVo> groupDetailsVos = new ArrayList<UnionGroupDetailsVo>();
      if(releaseId == null) {
        return groupDetailsVos;
      }
      List<UnionGroupDetails> detailsLs = unionGroupDetailsMapper.getUnionGroupDetailsLsByReleaseId(releaseId);
      if(detailsLs == null || detailsLs.size() < 0) {
        return groupDetailsVos;
      }
      UnionGroupDetailsVo detailsVo = new UnionGroupDetailsVo();
      for (UnionGroupDetails details : detailsLs) {
        detailsVo = new UnionGroupDetailsVo();
        detailsVo.setName(details.getName());
        detailsVo.setAddress(details.getAddress());
        detailsVo.setPhone(details.getPhone());
        groupDetailsVos.add(detailsVo);
      }
      
      return groupDetailsVos;
    }

	/**
	 * 其他
	 * 
	 */


}
