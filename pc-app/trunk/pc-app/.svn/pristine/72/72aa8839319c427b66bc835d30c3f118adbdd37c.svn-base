package com.nork.cityunion.service;

import java.util.List;

import com.nork.cityunion.model.SpecialOfferModel;
import com.nork.cityunion.model.UnionSpecialOffer;
import com.nork.cityunion.model.search.UnionSpecialOfferSearch;
import com.nork.cityunion.model.vo.UnionSpecialOfferVO;

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
	 * 查询个人用户优惠活动数量
	 * @param userId
	 * @param start
	 * @param limit
	 * @return
	 */
	int findSpecialOfferCount(Integer userId,Integer start,Integer limit);

	/**
	 * 查询个人用户优惠活动集合
	 * @return
	 */
	List<UnionSpecialOffer> findSpecialOfferList(SpecialOfferModel model);

	/**
	 * 根据id获取优惠活动对象信息
	 * @param id
	 * @return
	 */
	UnionSpecialOfferVO getUnionSpecialOfferVOById(Integer id);

	/**
	 * 处理数据以兼容旧同城联盟
	 * 新的活动内容为json格式存储,旧同城联盟前端读取的是String类型数据
	 * 所以要将json格式存储的活动内容转为String类型的数据
	 * @param unionSpecialOffer
	 * @return
	 */
	UnionSpecialOffer transformSpecialOfferWithContent(UnionSpecialOffer unionSpecialOffer);

	/**
	 * 处理接收到的活动内容
	 * 非json格式的内容需处理为json格式存储
	 * 如："优惠1\n优惠2" 转为 "{"1":"优惠活动","2":"优惠2"}"
	 * @param unionSpecialOffer
	 * @return
	 */
	UnionSpecialOffer transformSpecialOfferContentToJson(UnionSpecialOffer unionSpecialOffer);
}
