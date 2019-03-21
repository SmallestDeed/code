package com.sandu.activity.dao;

import java.util.List;

import com.sandu.activity.model.vo.*;
import org.apache.ibatis.annotations.Param;

import com.sandu.activity.model.Coupon;
import com.sandu.activity.model.CouponProduct;
import com.sandu.activity.model.CouponUser;
import com.sandu.activity.model.query.CouponProductQuery;
import com.sandu.activity.model.query.CouponQuery;
import com.sandu.activity.model.query.CouponUserQuery;

public interface CouponDao {
	int batchDeleteCouponProduct(CouponProductQuery query);
	int deleteCoupon(long id);
	int deleteCouponProduct(long id);
	int deleteCouponProductWithCouponId(long couponId);
	CouponVo getCoupon(long couponId);
	List<CouponProductVo> getCouponProductList(CouponProductQuery query);
	long findCouponProductCount(CouponProductQuery query);
	int findUserReceiveCount(CouponUserQuery query);
	List<CouponVo> getCouponList(CouponQuery query);
	long findCouponCount(CouponQuery query);
	List<CouponUserVo> getExpiredList(CouponUserQuery query);
	long findExpiredCount(CouponUserQuery query);
	List<CouponUserVo> getUnUsedList(CouponUserQuery query);
	long findUnUsedCount(CouponUserQuery query);
	List<CouponUserVo> getUsedList(CouponUserQuery query);
	long findUsedCount(CouponUserQuery query); 
	int receive(CouponUser couponuser);
	int insert(Coupon coupon);
	int update(Coupon coupon);
	/***
	 * 更新领取数量
	 * @param coupon
	 * @return
	 */
	int updateReceiveQty(Coupon coupon);
	int updateCouponState(@Param("id")long couponId, @Param("coupon_state")int state);
	int updateCouponUserState(@Param("coupon_id")long couponId, @Param("coupon_state")int state);
	List<CouponVo> getWaitingReceiveList(CouponUserQuery query);
	long findWaitingReceiveCount(CouponUserQuery query);
	int insertProductBatch(List<CouponProduct> list);
	int updateProduct(CouponProduct couponproduct);
	int deleteProductByCouponId(long couponId);
	int getCouponCountByUseId(@Param("couponId")long couponId, @Param("userId")long userId);

	/**
	 * wangHl
	 * 通过商品Id获取商品可使用优惠卷列表
	 * @param goodsId 商品Id
	 * @return List<GoodsCouponVO> 可使用优惠卷列表
	 */
	List<GoodsCouponVO> selectCouponByGoodsId(@Param("goodsId")Long goodsId,@Param("companyId")Long companyId);

	/**
	 * wangHl
	 * 通过优惠卷Id和用户Id查询用户领取的优惠卷
	 * @param couponId 优惠卷Id
	 * @param userId 用户Id
	 * @return
	 */
	int countCouponUser(@Param("couponId") Long couponId,@Param("userId") Long userId);

	/**
	 * wangHl
	 * 查询用户可使用优惠卷列表
	 * @param companyId 企业Id
     * @param userId 用户Id
     * @return
	 */
	List<CouponUser> selectUserCanBeUsedCoupons(@Param("companyId") Long companyId, @Param("userId") Long userId);


	/**
	 * wangHl
	 * 通过优惠卷Id获取优惠卷信息
	 * @param id 优惠卷Id
	 * @return Coupon优惠卷实体
	 */
	Coupon selectCouponById(@Param("id") Long id);


	/**
	 * wangHl
	 * 通过优惠卷Id获取优惠卷商品信息
	 * @param couponId 优惠卷Id
	 * @return Coupon优惠卷实体
	 */
	CouponProduct selectCouponProduct(@Param("couponId")Long couponId);


	/**
	 * 修改用户优惠卷状态(优惠卷使用或取消使用)
	 * @param usedState 使用状态
	 * @param couponId 优惠卷Id
	 * @param couponId 用户Id
	 * @return int
	 */
	int updateCouponUser(@Param("usedState") Integer usedState,
						 @Param("couponId") Long couponId,@Param("userId") Long userId);



	/**
	 * wangHl
	 * 通过Id查询用户优惠卷信息
	 * @param id 主键
	 * @return CouponUser
	 */
	CouponUser getCouponUserById(@Param("userId") Integer userId,@Param("couponId") Long couponId,@Param("usedState")Integer usedState);


    List<GoodsCouponVO> selectCommonCouponByCompanyId(Long companyId);

	List<CouponCanBeUsedVO> selectCouponByIds(@Param("couponIds")List<Long> couponIds,@Param("goodsIds")List<Long> goodsIds);

    List<Coupon> getCouponByCompanyId(Long companyId);

	List<Coupon> selectCouponByProductScopeTypeAndIds(@Param("couponIds") List<Long> couponIds, @Param("productScopeType") int productScopeType, @Param("userId") Long userId);

    int updateCouponUserByPrimaryKey(@Param("usedState")int usedState, @Param("id")Long id);

    int selectCountCouponByid(long receiveId);

    Long selectBaseProduct(Long productId);

	List<Long> selectGoodSpuIds(@Param("productIds") List<Long> productIds);

    CouponUser getCouponUserCouponNo(@Param("receiveNo") Long receiveNo,@Param("usedState") Integer usedState);
}
