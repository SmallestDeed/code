package com.sandu.web.activity.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.sandu.activity.model.query.CouponCanBeUsedQuery;
import com.sandu.activity.model.vo.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.sandu.activity.model.Coupon;
import com.sandu.activity.model.query.CouponProductQuery;
import com.sandu.activity.model.query.CouponQuery;
import com.sandu.activity.model.query.CouponUserQuery;
import com.sandu.activity.service.CouponService;
import com.sandu.common.utils.DateUtil;
import com.sandu.matadata.Page;
import com.sandu.matadata.ResultCode;
import com.sandu.matadata.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "优惠券信息", tags = "Activity")
@RestController
@RequestMapping(value = "/v1/sandu/mini/activity")
public class CouponController {
	private static final Logger logger = LoggerFactory.getLogger(CouponController.class.getName());
	@Autowired
	private CouponService couponService;
	
	@ApiOperation(value = "删除优惠券(后台调用)", response = ResultMessage.class)
	@RequestMapping(value = "/deleteCoupon", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public ResultMessage deleteCoupon(long couponId) {
		ResultMessage result = new ResultMessage();
		couponService.deleteCoupon(couponId);
		result.setCode(ResultCode.Success);
		return result;
	}

	@ApiOperation(value = "删除优惠券关联商品信息(后台调用)", response = ResultMessage.class)
	@RequestMapping(value = "/deleteCouponProduct", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public ResultMessage deleteCouponProduct(long id) {
		ResultMessage result = new ResultMessage();
		couponService.deleteCouponProduct(id);
		result.setCode(ResultCode.Success);
		return result;
	}

	@ApiOperation(value = "获取优惠券信息(后台调用)", response = ResultMessage.class)
	@RequestMapping(value = "/getCoupon", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public ResultMessage getCoupon(long couponId) {
		ResultMessage result = new ResultMessage();
		result.setCode(ResultCode.Success);
		CouponVo coupon= couponService.getCoupon(couponId);
		if(coupon!=null) {
			result.setData(coupon);
		}
		return result;
	}

	@ApiOperation(value = "获取关联的商品列表(后台调用)", response = ResultMessage.class)
	@RequestMapping(value = "/getCouponProductList", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public ResultMessage getCouponProductList(CouponProductQuery query) {
		ResultMessage result = new ResultMessage();
		result.setCode(ResultCode.Success);
		if(query.getCouponId()==null) {
			result.setMessage("优惠券ID不能为空");
		}
		else {
			Page<CouponProductVo> page= couponService.getCouponProductList(query);
			if(page!=null) {
				result.setData(page);
			}
		}
		return result;
	}

	@ApiOperation(value = "获取优惠券列表信息(后台调用)", response = ResultMessage.class)
	@RequestMapping(value = "/getCouponList", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public ResultMessage getCouponList(CouponQuery query) {
		ResultMessage result = new ResultMessage();
		result.setCode(ResultCode.Success);
		Page<CouponVo> page= couponService.getCouponList(query);
		if(page!=null ) {
			result.setData(page);
		}
		return result;
	}

	@ApiOperation(value = "获取待领取的优惠券列表(小程序调用)", response = ResultMessage.class)
	@RequestMapping(value = "/getWaitingReceiveList", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public ResultMessage getWaitingReceiveList(CouponUserQuery query) {
		ResultMessage result = new ResultMessage();
		query.setPageNo(1);
		query.setPageSize(300);
		Page<CouponVo> page= couponService.getWaitingReceiveList(query);
		if(page!=null ) {
			result.setData(page);
			result.setCode(ResultCode.Success);
		}
		return result;
	}
	@ApiOperation(value = "获取已过期的优惠券列表(小程序调用)", response = ResultMessage.class)
	@RequestMapping(value = "/getExpiredList", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public ResultMessage getExpiredList(CouponUserQuery query) {
		ResultMessage result = new ResultMessage();
		Page<CouponUserVo> page= couponService.getExpiredList(query);
		if(page!=null ) {
			result.setData(page);
			result.setCode(ResultCode.Success);
		}
		return result;
	}

	@ApiOperation(value = "获取未使用的优惠券列表(小程序调用)", response = ResultMessage.class)
	@RequestMapping(value = "/getUnUsedList", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public ResultMessage getUnUsedList(CouponUserQuery query) {
		ResultMessage result = new ResultMessage();
		Page<CouponUserVo> page= couponService.getUnUsedList(query);
		if(page!=null ) {
			result.setData(page);
			result.setCode(ResultCode.Success);
		}
		return result;
	}

	@ApiOperation(value = "获取已使用的优惠券列表(小程序调用)", response = ResultMessage.class)
	@RequestMapping(value = "/getUsedList", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public ResultMessage getUsedList(CouponUserQuery query) {
		ResultMessage result = new ResultMessage();
		Page<CouponUserVo> page= couponService.getUsedList(query);
		if(page!=null ) {
			result.setData(page);
			result.setCode(ResultCode.Success);
		}
		return result;
	}

	@ApiOperation(value = "会员领取优惠券(小程序调用)", response = ResultMessage.class)
	@RequestMapping(value = "/receive", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public ResultMessage receive(long couponId, long userId) {
		ResultMessage result = new ResultMessage();
		result = couponService.receive(couponId, userId);
		return result;
	}

	@ApiOperation(value = "保存优惠券信息(后台调用)", response = ResultMessage.class)
	@RequestMapping(value = "/save", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public ResultMessage save(@RequestBody Coupon coupon) {
		ResultMessage result = new ResultMessage();
		couponService.save(coupon);
		result.setCode(ResultCode.Success);
		return result;
	}

	@ApiOperation(value = "更改优惠券状态(后台调用)", response = ResultMessage.class)
	@RequestMapping(value = "/updateState", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public ResultMessage updateState(long couponId, int state) {
		ResultMessage result = couponService.updateState(couponId, state);
		return result;
	}


	/**
	 * 获取某个商品可用优惠卷
	 * @author wanghailin
	 * @param goodsId 商品Id
	 * @param userId 用户Id
	 * @param companyId 企业Id
	 * @return ResultMessage
	 */
	@ApiOperation(value = "获取某个商品可用优惠卷(小程序调用)", response = ResultMessage.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "goodsId",value = "商品Id",paramType = "query",dataType = "Long",required = true),
			@ApiImplicitParam(name = "userId",value = "用户Id",paramType = "query",dataType = "Long",required = true),
			@ApiImplicitParam(name = "companyId",value = "企业Id",paramType = "query",dataType = "Long",required = true)
	})
	@RequestMapping(value = "/getGoodsCouponList", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public ResultMessage getGoodsCouponList(@RequestParam("productId") Long productId,@RequestParam("userId")Long userId,
											@RequestParam("companyId")Long companyId,@RequestParam("totalFree")Double totalFree) {
		ResultMessage result = new ResultMessage();
		//1.参数校验
		if (null==productId){
			result.setMessage("商品Id为空");
			result.setCode(ResultCode.ParameterError);
			return result;
		}
 		if (null==userId){
			result.setMessage("用户Id为空");
			result.setCode(ResultCode.ParameterError);
			return result;
		}
		if (null==companyId){
			result.setMessage("企业Id为空");
			result.setCode(ResultCode.ParameterError);
			return result;
		}
		//2.从数据库查询商品可用优惠卷列表
		List<GoodsCouponVO> goodsCouponList = couponService.getGoodsCouponList(productId, userId,companyId,totalFree);
		if(goodsCouponList!=null ) {
			result.setData(goodsCouponList);
			result.setCode(ResultCode.Success);
		}
		return result;
	}



	@ApiOperation(value = "获取用户可使用的优惠卷(确认订单使用)", response = ResultMessage.class)
	@RequestMapping(value = "/getCanBeUsedCouponList", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public ResultMessage getCanBeUsedCouponList(@RequestBody CouponCanBeUsedQuery query) {
		ResultMessage result = new ResultMessage();
		//校验参数
		if (null==query){
			logger.info("控制层：获取用户可使用的优惠卷：参数{}",query);
			result.setMessage("参数为空");
			result.setCode(ResultCode.ParameterError);
			return result;
		}
		//获取可使用优惠卷
		List<CouponCanBeUsedVO> userCanBeUsedCouponList = couponService.getUserCanBeUsedCouponList(query);
		logger.info("获取可使用优惠卷结果",userCanBeUsedCouponList);
        result.setData(userCanBeUsedCouponList);
		result.setCode(ResultCode.Success);
		result.setMessage("获取可使用优惠卷结果");
		return result;
	}

	@GetMapping(value = "/getIndexCoupon")
	@ApiOperation(value = "下程序获取首页优惠券列表", response = ResultMessage.class)
	public ResultMessage getIndexCoupon(@RequestParam(value = "companyId",required = true) Long companyId,
										@RequestParam(value = "userId",required = true) Long userId
										){
		ResultMessage result = new ResultMessage();
		List<Coupon> coupons = null;
		try {
			coupons = couponService.getIndexCoupons(companyId,userId);
			logger.info("获取首页优惠券结果: =>{}"+coupons);
		} catch (Exception e) {
			logger.error("系统错误",e);
			result.setCode(500);
			result.setMessage("系统错误");
		}
		result.setData(coupons);
		result.setCode(ResultCode.Success);
		result.setMessage("获取首页可使用优惠卷结果");
		return result;
	}

}
