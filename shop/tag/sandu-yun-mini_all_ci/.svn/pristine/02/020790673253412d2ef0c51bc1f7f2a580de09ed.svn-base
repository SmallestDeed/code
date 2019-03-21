package com.sandu.activity.service;

import com.sandu.activity.model.Coupon;
import com.sandu.activity.model.CouponUser;
import com.sandu.activity.model.query.CouponCanBeUsedQuery;
import com.sandu.activity.model.query.CouponProductQuery;
import com.sandu.activity.model.query.CouponQuery;
import com.sandu.activity.model.query.CouponUserQuery;
import com.sandu.activity.model.vo.*;
import com.sandu.matadata.Page;
import com.sandu.matadata.ResultMessage;

import java.util.List;

/***
 * 优惠券服务接口
 * 
 * @author Administrator
 *
 */
public interface CouponService {

	/***
	 * 逻辑删除优惠券
	 * 
	 * @param couponId
	 * @return
	 */
	boolean deleteCoupon(long couponId);

	/***
	 * 根据ID物理删除优惠券关联商品信息
	 * 
	 * @param id
	 * @return
	 */
	boolean deleteCouponProduct(long id);

	/***
	 * 获取优惠券信息
	 * 
	 * @param couponId
	 * @return
	 */
	CouponVo getCoupon(long couponId);

	/***
	 * 根据优惠券ID分页获取关联的商品列表
	 * 
	 * @param query
	 * @return
	 */
	Page<CouponProductVo> getCouponProductList(CouponProductQuery query);

	/***
	 * 分页获取优惠券列表信息
	 * 
	 * @param query
	 * @return
	 */
	Page<CouponVo> getCouponList(CouponQuery query);

	/***
	 * 根据用户ID获取已过期的优惠券列表
	 * 
	 * @param query
	 * @return
	 */
	Page<CouponUserVo> getExpiredList(CouponUserQuery query);

	/***
	 * 根据用户ID获取未使用的优惠券列表
	 * 
	 * @param query
	 * @return
	 */
	Page<CouponUserVo> getUnUsedList(CouponUserQuery query);

	/***
	 * 根据用户ID获取已使用的优惠券列表
	 * 
	 * @param query
	 * @return
	 */
	Page<CouponUserVo> getUsedList(CouponUserQuery query);
	
	/***
	 * 根据用户ID获取待领取的优惠券列表
	 * 
	 * @param query
	 * @return
	 */
	Page<CouponVo> getWaitingReceiveList(CouponUserQuery query);

	/***
	 * 会员领取优惠券
	 * 
	 * @param couponId
	 * @param userId
	 * @return
	 */
	ResultMessage receive(long couponId, long userId);

	/***
	 * 保存优惠券信息
	 * 
	 * @param coupon
	 * @return
	 */
	boolean save(Coupon coupon);

	/***
	 * 更改优惠券状态 同时要更改会员领取优惠券的状态(除了用户已经使用的优惠券不能被联动更改状态)
	 * 
	 * @param couponId
	 * @param state
	 * @return
	 */
	ResultMessage updateState(long couponId, int state);


	/**
	* @description 通过商品Id查询商品可用优惠卷列表
	* @author : WangHaiLin
	* @date 2018/7/29 15:16
	* @param goodsId 商品Id
	* @param userId 用户Id
	* @return java.util.List<com.sandu.activity.model.vo.GoodsCouponVO>
	*
	*/
	List<GoodsCouponVO> getGoodsCouponList(Long goodsId,Long userId,Long companyId,Double totalFree);


	/**
	* @description 获取用户可使用优惠卷列表
	* @author : WangHaiLin
	* @date : 2018/7/30 16:21
	* @param query 查询入参
	* @return: java.util.List<com.sandu.activity.model.vo.CouponCanBeUsedVO>
	*
	*/
	List<CouponCanBeUsedVO> getUserCanBeUsedCouponList(CouponCanBeUsedQuery query);

	/**
	 * wangHL
	 * 通过优惠卷Id查询优惠卷详情
	 * @param couponId 优惠卷Id
	 * @return
	 */
	Coupon getCouponByCouponId(Long couponId);



	/**
	 * wangHl
	 * 修改用户优惠卷状态(优惠卷使用或取消使用)
	 * @param usedState 使用状态
	 * @param couponId 优惠卷Id
	 * @param couponId 用户Id
	 * @return boolean true 修改成功 false 修改失败
	 */
	boolean updateCouponUser(Integer usedState,Long couponId,Long userId);


	/**
	 * wangHl
	 * 通过Id查询用户优惠卷信息
	 * @param id 主键
	 * @return CouponUser
	 */
	CouponUser getCouponUserById(Integer userId,Long id,Integer usedState);

	int updateByUsedStateAPrimaryKey(int usedState,Long id);

	CouponUser getCouponByCouponNo(Long receiveNo,Integer usedState);

    List<Coupon> getIndexCoupons(Long companyId, Long userId);
}
