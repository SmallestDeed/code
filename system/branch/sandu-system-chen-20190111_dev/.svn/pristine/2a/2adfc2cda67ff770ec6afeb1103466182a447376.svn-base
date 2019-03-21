package com.sandu.service.llt.trade.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.sandu.api.llt.trade.service.LltTradeService;
import com.sandu.commons.gson.GsonUtil;
import com.sandu.commons.http.HttpClientUtil;

@Service("lltTradeService")
public class LltTradeServiceImpl implements LltTradeService{

	private static final String PUSH_ORDER_URL = "http://121.40.18.106/srv/srv/order";
	private static final String APP_KEY = "513b65fd562773a8";
	private static final String APP_SERCET = "8cb2dca7467803f29822d9f724fc233d";
	
	
	private static Map<Integer,String> packages = new HashMap<Integer,String>();
	static {
		packages.put(1,"EXP11001");
		packages.put(2,"EXP11002");
		packages.put(3,"EXP11003");
		packages.put(5,"EXP11005");
		packages.put(6,"EXP11006");
		packages.put(10,"EXP11010");
		packages.put(20,"EXP11020");
		packages.put(30,"EXP11030");
		packages.put(50,"EXP11050");
		/*packages.put(100,"EXP110100");
		packages.put(200,"EXP110200");
		packages.put(300,"EXP110300");
		packages.put(500,"EXP110500");*/
	}
	
	@Override
	public String getFluxpackage(Integer amount) {
		return packages.get(amount);
	}
	
	@Override
	public String pushOrder(String mobile,Integer amount){
    	String paramsJson = this.getJsonReqParam(mobile,amount);
    	return HttpClientUtil.doPostJsonMethod(PUSH_ORDER_URL, paramsJson);
	}
	
	@Override
	public String getJsonReqParam(String mobile,Integer amount) {
		LinkedHashMap<String,Object> paramsMap = new LinkedHashMap<String,Object>();
    	paramsMap.put("appkey", APP_KEY);
    	paramsMap.put("datetime", System.currentTimeMillis());
    	paramsMap.put("type", 1); //0：流量，1：话费
    	//paramsMap.put("fluxpackage","YD1111");//this.getFluxpackage(amount));
    	paramsMap.put("fluxpackage",this.getFluxpackage(amount));//);
    	paramsMap.put("mobile", mobile);
    	String sign = getSign(paramsMap);
    	paramsMap.put("sign", sign);
    	String paramsJson = GsonUtil.toJson(paramsMap);
		return paramsJson;
	}
	
	private String getSign(LinkedHashMap<String,Object> map){
    	String context = "";
    	for (String key : map.keySet()) {
        	context += map.get(key)+"";
    	}
    	context += APP_SERCET;
    	return DigestUtils.md5Hex(context).toUpperCase();
	}
	
}
