package com.nork.decorateOrder.constant;

public class DecoratePayConstants {

	/**
	 * DecoratePayController.pay方法, 参数orderType = 0: 支付的是限时快抢订单
	 */
	public final static Integer DECORATEPAY_PAY_ORDERTYPE_DECORATESECKILL = 0;
	
	/**
	 * DecoratePayController.pay方法, 参数orderType = 1: 支付的是平台派单订单
	 */
	public final static Integer DECORATEPAY_PAY_ORDERTYPE_DECORATEPRICE = 1;
	
	/**
	 * DecoratePayController.pay方法, 参数orderType = 2: 从"我的客户"列表支付
	 */
	public final static Integer DECORATEPAY_PAY_ORDERTYPE_DECORATEORDER = 2;
	
	/**
	 * DecoratePayService.pay方法的返回: payStatus = 0: 支付成功
	 */
	/*public final static Integer DECORATEPAY_PAY_PAYSTATUS_SUCCESS = 0;*/
	
	/**
	 * DecoratePayService.pay方法的返回: payStatus = 1: 支付失败, 系统异常
	 */
	/*public final static Integer DECORATEPAY_PAY_PAYSTATUS_FAILED = 1;*/
	
	/**
	 * DecoratePayService.pay方法的返回: payStatus = 2: 支付失败, 余额不足
	 */
	/*public final static Integer DECORATEPAY_PAY_PAYSTATUS_FAILED_NOT_SUFFICIENT_FUNDS = 2;*/
	
	/**
	 * DecoratePayDTO.payStatus = 0: 订单未支付
	 */
	public final static Integer DECORATEPAYDTO_PAYSTATUS_NO_PAY = 0;
	
	/**
	 * DecoratePayDTO.payStatus = 0: 订单未支付, 且支付超时
	 */
	public final static Integer DECORATEPAYDTO_PAYSTATUS_PAY_OVERTIME = 2;
	
	/**
	 * DecoratePayDTO.payStatus = 0: 订单已支付
	 */
	public final static Integer DECORATEPAYDTO_PAYSTATUS_HAVE_PAY = 1;
	
	/**
	 * 支付返回状态enum
	 */
	public static enum DECORATEPAY_PAY_PAYSTATUS_ENUM{
		success(0, "支付成功"), 
		failed(1, "支付失败, 系统异常"), 
		failed_notSufficientFunds(2, "余额不足, 请充值"), 
		failed_alreadyPaid(3, "该订单已被支付, 无需重复支付"), 
		failed_overtime(5, "支付失败, 订单已超时");
		
		private Integer value;
		
		private String message;
		
		public Integer getValue() {
			return value;
		}

		public String getMessage() {
			return message;
		}

		private DECORATEPAY_PAY_PAYSTATUS_ENUM(Integer value, String message) {
			this.message = message;
			this.value = value;
		}
		
		public static DECORATEPAY_PAY_PAYSTATUS_ENUM get(Integer value) {
			if(value == null) {
				return null;
			}
			
			for(DECORATEPAY_PAY_PAYSTATUS_ENUM item : DECORATEPAY_PAY_PAYSTATUS_ENUM.values()) {
				if(item.getValue().intValue() == value.intValue()) {
					return item;
				}
			}
			return null;
		}
		
	}
	
	/**
	 * DecoratePayDTO.orderType
	 * 
	 * @author huangsongbo
	 *
	 */
	public static enum DECORATEPAYDTO_ORDERTYPE_ENUM{
		decorateSeckill(0, "限时快抢"),
		decoratePrice(1, "平台派单"),
		innerRecommend(2, "内部推荐")
		;
		
		private Integer value;
		
		private String message;
		
		public Integer getValue() {
			return value;
		}

		public String getMessage() {
			return message;
		}

		private DECORATEPAYDTO_ORDERTYPE_ENUM(Integer value, String message) {
			this.message = message;
			this.value = value;
		}
		
	}
	
}
