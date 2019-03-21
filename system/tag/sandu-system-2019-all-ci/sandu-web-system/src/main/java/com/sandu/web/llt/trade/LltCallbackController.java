package com.sandu.web.llt.trade;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sandu.api.llt.trade.model.LltRechargeLog;
import com.sandu.api.llt.trade.service.LltRechargeLogService;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.gson.GsonUtil;
import com.sandu.web.BaseController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/llt/trade")
@Slf4j
public class LltCallbackController extends BaseController {

	@Autowired
	private LltRechargeLogService lltRechargeLogService;
	 
	@ApiOperation(value = "流量通充值回调", response = ResponseEnvelope.class)
    @PostMapping("/callback")
    public int callback(HttpServletRequest request, HttpServletResponse response) {
    	try {
			String jsonData = this.readData(request);
			log.info("流量通回调..."+jsonData);
			Map<String,String> dataMap = GsonUtil.fromJson(jsonData, Map.class);
			if(this.modifyPushInfo(dataMap)>0) {
				return 1;
			}
			log.warn("流量通回调订单不存在:"+jsonData);
    		return 0;
		}catch(Exception ex) {
    		log.error("流量通回调系统异常:",ex);
			return 0;
		}
    }
	
	private int modifyPushInfo(Map<String,String> dataMap) {
		LltRechargeLog entity = new LltRechargeLog();
		entity.setRetOrderId(dataMap.get("orderId"));
		entity.setPushTime(dataMap.get("timestamp"));
		entity.setPushOrderStatus(dataMap.get("orderStatus"));
		entity.setPushOrderDesc(dataMap.get("orderDesc"));
		return lltRechargeLogService.modifyByOrderId(entity);
	}
	
	
	private String readData(HttpServletRequest request) {
		BufferedReader br = null;
		try {
			StringBuilder result = new StringBuilder();
			br = request.getReader();
			for (String line; (line=br.readLine())!=null;) {
				if (result.length() > 0) {
					result.append("\n");
				}
				result.append(line);
			}

			return result.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (br != null)
				try {br.close();} catch (IOException e) {e.printStackTrace();}
		}
	}
	
	
	
}
