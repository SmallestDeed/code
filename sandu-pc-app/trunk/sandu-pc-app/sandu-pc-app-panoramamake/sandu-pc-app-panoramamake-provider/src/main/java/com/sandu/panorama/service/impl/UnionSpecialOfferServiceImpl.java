package com.sandu.panorama.service.impl;

import java.util.Date;
import java.util.List;

import com.sandu.cityunion.model.UnionSpecialOffer;
import com.sandu.common.util.Utils;
import com.sandu.panorama.dao.DesignPlanStoreReleaseMapper;
import com.sandu.panorama.dao.UnionSpecialOfferMapper;
import com.sandu.panorama.model.input.UnionSpecialOfferSearch;
import com.sandu.panorama.model.output.DesignPlanStoreReleaseVo;
import com.sandu.panorama.model.output.UnionSpecialOfferVo;
import com.sandu.panorama.service.UnionSpecialOfferService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Title: UnionSpecialOfferServiceImpl.java 
 * @Package com.nork.cityunion.service.impl
 * @Description:同城联盟-联盟优惠活动表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:24:34
 * @version V1.0   
 */
@Service("unionSpecialOfferService")
@Transactional
public class UnionSpecialOfferServiceImpl implements UnionSpecialOfferService {

	private UnionSpecialOfferMapper unionSpecialOfferMapper;

	@Autowired
	public void setUnionSpecialOfferMapper(
			UnionSpecialOfferMapper unionSpecialOfferMapper) {
		this.unionSpecialOfferMapper = unionSpecialOfferMapper;
	}
	@Autowired
	private DesignPlanStoreReleaseMapper designPlanStoreReleaseMapper;

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
	 * @param
	 * @return   int
	 */
	@Override
	public int getCount(UnionSpecialOfferSearch unionSpecialOfferSearch){
		return  unionSpecialOfferMapper.selectCount(unionSpecialOfferSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param
	 * @return   List<UnionSpecialOffer>
	 */
/*
	@Override
	public List<UnionSpecialOffer> getPaginatedList(
			UnionSpecialOfferSearch unionSpecialOfferSearch) {
		return unionSpecialOfferMapper.selectPaginatedList(unionSpecialOfferSearch);
	}
*/

	/**
	 * 其他
	 * 
	 */

	/**
	 * 其他
	 *
	 */

	/**
	 * 查询个人用户优惠活动数量
	 * @param userId
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public int findSpecialOfferCount(Integer userId,Integer start,Integer limit) {
		return unionSpecialOfferMapper.findSpecialOfferCount(userId,start,limit);
	}

	/**
	 * 查询个人用户优惠活动集合
	 * @param userId
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public List<UnionSpecialOffer> findSpecialOfferList(Integer userId,Integer start,Integer limit) {
		return unionSpecialOfferMapper.findSpecialOfferList(userId,start,limit);
	}

	@Override
	public List<UnionSpecialOfferVo> selectUnionSpecialOfferVoListBySearch(UnionSpecialOfferSearch unionSpecialOfferSearch) {

		Integer unionOfferId = null;//720制作关联联盟分组Id
		//在列表第一页置顶720制作关联的联盟分组
		if(StringUtils.isNotBlank(unionSpecialOfferSearch.getUuid()) && unionSpecialOfferSearch.getStart() != null && unionSpecialOfferSearch.getStart() == 0){
			DesignPlanStoreReleaseVo planStoreReleaseVo = new DesignPlanStoreReleaseVo();
			planStoreReleaseVo = designPlanStoreReleaseMapper.getDesignPlanStoreRelease(unionSpecialOfferSearch.getUuid());
			if(planStoreReleaseVo.getUnionContactId() != null){
				unionOfferId = planStoreReleaseVo.getUnionSpecialOfferId();
			}
			unionSpecialOfferSearch.setUnionOfferId(unionOfferId);
		}

		return unionSpecialOfferMapper.selectUnionSpecialOfferVoListBySearch(unionSpecialOfferSearch);
	}

	@Override
	public UnionSpecialOfferVo selectUnionSpecialOfferVoByUuid(String uuid) {
		if(StringUtils.isBlank(uuid)){
			return null;
		}
		DesignPlanStoreReleaseVo planStoreReleaseVo = new DesignPlanStoreReleaseVo();
		planStoreReleaseVo = designPlanStoreReleaseMapper.getDesignPlanStoreRelease(uuid);
		if(planStoreReleaseVo == null || planStoreReleaseVo.getUnionSpecialOfferId() == null){
			return null;
		}
		UnionSpecialOfferVo unionSpecialOfferVo = new UnionSpecialOfferVo();
		UnionSpecialOffer unionSpecialOffer = this.get(planStoreReleaseVo.getUnionSpecialOfferId());
		if(unionSpecialOffer == null || unionSpecialOffer.getId() == null){
			return null;
		}
		//TODO:抽出方法
		unionSpecialOfferVo.setId(unionSpecialOffer.getId());
		unionSpecialOfferVo.setName(unionSpecialOffer.getSpecialOfferName());
		unionSpecialOfferVo.setContent(unionSpecialOffer.getSpecialOfferContent());
		//转换时间
		if(unionSpecialOffer.getEffectiveBegin() != null){
			Date beginDate = unionSpecialOffer.getEffectiveBegin();
			String beginDateStr = Utils.getDateStr(beginDate,Utils.DATE);
			unionSpecialOfferVo.setEffectiveBegin(beginDateStr);
		}
		if(unionSpecialOffer.getEffectiveEnd() != null){
			Date endDate = unionSpecialOffer.getEffectiveEnd();
			String endDateStr = Utils.getDateStr(endDate,Utils.DATE);
			unionSpecialOfferVo.setEffectiveEnd(endDateStr);
		}

		return unionSpecialOfferVo;
	}
}
