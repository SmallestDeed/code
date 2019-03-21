package com.nork.cityunion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.cityunion.dao.UnionSpecialOfferMapper;
import com.nork.cityunion.model.UnionSpecialOffer;
import com.nork.cityunion.model.search.UnionSpecialOfferSearch;
import com.nork.cityunion.model.vo.UnionSpecialOfferVo;
import com.nork.cityunion.service.UnionSpecialOfferService;

/**   
 * @Title: UnionSpecialOfferServiceImpl.java 
 * @Package com.nork.cityunion.service.impl
 * @Description:同城联盟-联盟优惠活动表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:24:34
 * @version V1.0   
 */
@Service("unionSpecialOfferService")
public class UnionSpecialOfferServiceImpl implements UnionSpecialOfferService {

	private UnionSpecialOfferMapper unionSpecialOfferMapper;

	@Autowired
	public void setUnionSpecialOfferMapper(
			UnionSpecialOfferMapper unionSpecialOfferMapper) {
		this.unionSpecialOfferMapper = unionSpecialOfferMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param unionSpecialOffer
	 * @return  int 
	 */
	@Override
	public int add(UnionSpecialOffer unionSpecialOffer) {
		unionSpecialOfferMapper.insertSelective(unionSpecialOffer);
		return unionSpecialOffer.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param unionSpecialOffer
	 * @return  int 
	 */
	@Override
	public int update(UnionSpecialOffer unionSpecialOffer) {
		return unionSpecialOfferMapper
				.updateByPrimaryKeySelective(unionSpecialOffer);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return unionSpecialOfferMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UnionSpecialOffer 
	 */
	@Override
	public UnionSpecialOffer get(Integer id) {
		return unionSpecialOfferMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  unionSpecialOffer
	 * @return   List<UnionSpecialOffer>
	 */
	@Override
	public List<UnionSpecialOffer> getList(UnionSpecialOffer unionSpecialOffer) {
	    return unionSpecialOfferMapper.selectList(unionSpecialOffer);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  unionSpecialOffer
	 * @return   int
	 */
	@Override
	public int getCount(UnionSpecialOfferSearch unionSpecialOfferSearch){
		return  unionSpecialOfferMapper.selectCount(unionSpecialOfferSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  unionSpecialOffer
	 * @return   List<UnionSpecialOffer>
	 */
	@Override
	public List<UnionSpecialOffer> getPaginatedList(
			UnionSpecialOfferSearch unionSpecialOfferSearch) {
		return unionSpecialOfferMapper.selectPaginatedList(unionSpecialOfferSearch);
	}

    @Override
    public UnionSpecialOfferVo getUnionSpecialOfferVoByReleaseId(Integer releaseId) {
      UnionSpecialOfferVo unionSpecialOfferVo = new UnionSpecialOfferVo();
      if(releaseId == null) {
        return unionSpecialOfferVo;
      }
      UnionSpecialOffer unionSpecialOffer = unionSpecialOfferMapper.getUnionSpecialOfferByReleaseId(releaseId);
      if(unionSpecialOffer == null) {
        return unionSpecialOfferVo;
      }
      unionSpecialOfferVo.setSpecialOfferName(unionSpecialOffer.getSpecialOfferName());
      unionSpecialOfferVo.setSpecialOfferContent(unionSpecialOffer.getSpecialOfferContent());
      unionSpecialOfferVo.setIsDisplayed(unionSpecialOffer.getIsDisplayed());
      return unionSpecialOfferVo;
    }

	/**
	 * 其他
	 * 
	 */


}
