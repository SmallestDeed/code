package com.sandu.gateway.pay.callback;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jpay.ext.kit.HttpKit;
import com.jpay.ext.kit.PaymentKit;
import com.sandu.common.util.SpringContextHolder;
import com.sandu.gateway.pay.callback.service.RefundCallbackService;
import com.sandu.gateway.pay.common.exception.BizException;


@Controller
@RequestMapping("/v1/gateway/refund/callback")
public class GatewayRefundCallbackController {

    private static Logger logger = LogManager.getLogger(GatewayRefundCallbackController.class);

  
    @RequestMapping(value = "/wx/notify")
    public void wxPayCallback(HttpServletRequest request, HttpServletResponse response) {
    	logger.info("wx退款回调");
        //获取xml回调报文
    	String xmlResult = HttpKit.readData(request);
//    	String xmlResult = "<xml><return_code>SUCCESS</return_code><appid><![CDATA[wxe24ed743feb9c17f]]></appid><mch_id><![CDATA[1378786102]]></mch_id><nonce_str><![CDATA[8e5e40f99858ffbb95578049f69d4cc9]]></nonce_str><req_info><![CDATA[9LXgjpa1UXD9Ii7FNGKV3ijHXDEOtLtEOE8bpVbqq1KqtI/0VBM2/aBBTfn+aGDB+UwDaZsNR9tNfnEwEZIXkSCfYpKVTbuibklkjm0VXC5yE4OcknQrn8jkUXFLrvEK03yYj4H7ysEX24dtNPXVttOln69Qf/6rkiWxPvT63wL5NYLxuURep7rMkxrCPBTxnBRwGMkM6pI5IJGWoEenhqkAHudnfMdBR/dOdWJPViHuCqBzct3o9qv0bh9c8xJlYL+5XbX/yU94zin3AMW6zMVxQZDmDDpRXslL5+LR7RGPUoHgupBediFD3C2GgOf1nc8jgMlehh1UZberzQmptlGrpf5WJSmRQ8/6efENIsWMNCFGC4acaZTklbrnPH5u9WWteNVHvJjpaZQ2u2y3E9R6otDgjoIKGLQWWNJrFV/VpFZyjHwjpPkYchSVFsAUoQjjL1rb5WX8eMMg0aYvR6ptOfUD2JYBnxHTqwpzu8tshmlLGd7pxb9Wtc+FdlA/AZdFwwJ+KDoNrpsO8Xq2FDy221IXPAGEONVS5RS7N4ISu9VS/y5gVkjIRA+OVYhQoY2gaubqfiP2gbmR4HI1vLbllXY4yfhinnvzGtDSjf3uV2Il886Jq5CufONwfe0CR8ZnEn+iWCFRFTJwYARsu3rtmdKYVb64bh+IVqs+ZGabT+Ol+Sim1vFi2EjbHFPtrj5tXN2RfxEWRyld2acUOTxQstG7Wv4nVgfmYzHTKI+U7a5w3RiX/AnuOuj6SqWMq7MRrcxWs0S+IWSdrOH7bsrdPeTlktAXe3d1cTyKcEXd12nVUB62cr2zgfFgUqOzxpOZRcRzMWJio408TUbz6jDz3Xp73iTDIxqGmWPf09cQ12lIN7DPnIYh6FD/tRDiaCTeThQDJt71d+AVg1E2FTrDa+6CIglQ1jxua/9V443go29lYmqAexyR5a+yoIs/3Z55eJsZ/tq8+IGNDMoT+mIY04nFIxBQhpkpJzQkvX2xkxiBFHC9G/30qkUKG71CAOQoD2wDKDsz9xKi1A/E2m5Opke9DgIklRzUObmALcV/Y9VfsFiqhPHlmsG045PL]]></req_info></xml>";
    	boolean success = false;
    	String errMsg = "";
    	try {
    		RefundCallbackService refundCallbackService = SpringContextHolder.getBean("wxRefundCallback");
    		logger.info("wx退款回调结果为 =>{}"+xmlResult);
    		success = refundCallbackService.callback(xmlResult);
    	}catch(BizException ex) {
    		errMsg = ex.getMessage();
    	}catch(Exception ex) {
    		logger.error("系统错误:", ex);
    		errMsg = "未知错误";
    	}
    	//响应微信服务器
    	responseToWxServer(success,errMsg,response);
    }
    
    private	void responseToWxServer(boolean success,String errMsg,HttpServletResponse response) {
    	Map<String, String> xml = new HashMap<String, String>();
    	if(success) {
    		xml.put("return_code","SUCCESS");
    		xml.put("return_msg", "OK");
    	}else {
    		xml.put("return_code","FAIL");
    		xml.put("return_msg", errMsg);
    	}
    	try {
    		logger.info("退款后,开始通知微信服务器:{}",xml);
    		PrintWriter writer = response.getWriter();
    		writer.write(PaymentKit.toXml(xml));
			writer.flush();
		} catch (IOException e) {
			logger.error("网络异常:", e);
		}
    }

   
}
