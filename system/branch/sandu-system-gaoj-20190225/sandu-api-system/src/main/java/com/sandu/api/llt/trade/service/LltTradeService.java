package com.sandu.api.llt.trade.service;

public interface LltTradeService {

	String getFluxpackage(Integer amount);
	
	public String pushOrder(String mobile,Integer amount);
	
	public String getJsonReqParam(String mobile,Integer amount);
}
