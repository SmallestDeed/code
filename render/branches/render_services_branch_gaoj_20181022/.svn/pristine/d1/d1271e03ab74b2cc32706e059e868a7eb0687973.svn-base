package com.nork.cityunion.service;

import java.util.List;

import com.nork.cityunion.model.UnionSpecialOffer;
import com.nork.cityunion.model.search.UnionSpecialOfferSearch;
import com.nork.cityunion.model.vo.UnionSpecialOfferVo;

/**   
 * @Title: UnionSpecialOfferService.java 
 * @Package com.nork.cityunion.service
 * @Description:同城联盟-联盟优惠活动表Service
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:24:34
 * @version V1.0   
 */
public interface UnionSpecialOfferService {
	/**
	 * 新增数据
	 *
	 * @param unionSpecialOffer
	 * @return  int 
	 */
	public int add(UnionSpecialOffer unionSpecialOffer);

	/**
	 *    更新数据
	 *
	 * @param unionSpecialOffer
	 * @return  int 
	 */
	public int update(UnionSpecialOffer unionSpecialOffer);

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
	 * @return  UnionSpecialOffer 
	 */
	public UnionSpecialOffer get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  unionSpecialOffer
	 * @return   List<UnionSpecialOffer>
	 */
	public List<UnionSpecialOffer> getList(UnionSpecialOffer unionSpecialOffer);

	/**
	 *    获取数据数量
	 *
	 * @param  unionSpecialOffer
	 * @return   int
	 */
	public int getCount(UnionSpecialOfferSearch unionSpecialOfferSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  unionSpecialOffer
	 * @return   List<UnionSpecialOffer>
	 */
	public List<UnionSpecialOffer> getPaginatedList(
				UnionSpecialOfferSearch unionSpecialOffertSearch);

	/**
	 * 其他
	 * 
	 */

	/**
	 * 根据联盟方案发布id查找优惠活动信息
	 * @param releaseId
	 * @return
	 */
	public UnionSpecialOfferVo getUnionSpecialOfferVoByReleaseId(Integer releaseId);
	
}
