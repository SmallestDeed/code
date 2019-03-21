package com.sandu.pay.wexin.service;

import com.sandu.common.util.StringUtils;
import com.sandu.pay.wexin.common.Util;
import com.sandu.pay.wexin.common.WxConfigure;
import com.sandu.pay.wexin.protocol.UnifiedOrderReqData;
import com.sandu.pay.wexin.protocol.UnifiedOrderResData;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.log4j.Logger;

public class UnifiedOrderService extends BaseService{

    Logger logger = Logger.getLogger(UnifiedOrderService.class);

	public UnifiedOrderService()
			throws ClassNotFoundException, IllegalAccessException, InstantiationException {
		super(WxConfigure.UNIFIED_ORDER_API,false);
		// TODO Auto-generated constructor stub
	}

    /**
     * 请求支付服务
     * @param unifiedOrderReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public UnifiedOrderResData request(UnifiedOrderReqData unifiedOrderReqData) throws Exception {
        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        XStream xStreamForRequestPostData = new XStream(
                new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        // 将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = xStreamForRequestPostData.toXML(unifiedOrderReqData);
        //logger.error("---------------------Request Data:"+postDataXML);
        
        String responseString = sendPost(unifiedOrderReqData);
        //logger.error("---------------------responseString:"+responseString);
        //将从API返回的XML数据映射到Java对象
        UnifiedOrderResData scanPayResData=null;
        //////System.out.println("responseString:"+responseString);
        if(StringUtils.isNotEmpty(responseString)){
             scanPayResData = (UnifiedOrderResData) Util.getObjectFromXML(responseString, UnifiedOrderResData.class);
        }
        return scanPayResData;
    }
}
