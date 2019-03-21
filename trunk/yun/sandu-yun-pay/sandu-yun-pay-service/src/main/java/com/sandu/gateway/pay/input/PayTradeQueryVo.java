package com.sandu.gateway.pay.input;

import lombok.Data;

@Data
public class PayTradeQueryVo {

	private String payTradeNo; 
	private String intenalTradeNo;
	//private String tradeDesc;
	//private Long total_fee;
	/**
	 *  '交易状态:10.开始,20.回调处理中,30.成功,40.失败',
	 */
	//private Integer status;
	//private String platformCode;
	//private Integer source;
	//private Long operator;
}
