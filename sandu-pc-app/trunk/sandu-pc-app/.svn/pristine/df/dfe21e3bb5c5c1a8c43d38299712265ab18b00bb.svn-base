package com.sandu.panorama.service;

import java.util.List;

import com.sandu.cityunion.model.UnionSpecialOffer;
import com.sandu.panorama.model.input.UnionSpecialOfferSearch;
import com.sandu.panorama.model.output.UnionSpecialOfferVo;

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
	 * @param  unionSpecialOfferSearch
	 * @return   int
	 */
	public int getCount(UnionSpecialOfferSearch unionSpecialOfferSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  unionSpecialOffer
	 * @return   List<UnionSpecialOffer>
	 */
/*
	public List<UnionSpecialOffer> getPaginatedList(
            UnionSpecialOfferSearch unionSpecialOffertSearch);
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
	int findSpecialOfferCount(Integer userId, Integer start, Integer limit);

	/**
	 * 查询个人用户优惠活动集合
	 * @param userId
	 * @param start
	 * @param limit
	 * @return
	 */
	List<UnionSpecialOffer> findSpecialOfferList(Integer userId, Integer start, Integer limit);

	List<UnionSpecialOfferVo> selectUnionSpecialOfferVoListBySearch(UnionSpecialOfferSearch unionSpecialOfferSearch);

	/**
	 * 根据720制作分享uuid得到优惠信息
	 * @param uuid
	 * @return
	 */
	UnionSpecialOfferVo selectUnionSpecialOfferVoByUuid(String uuid);
}
