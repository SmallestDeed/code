package com.sandu.pay.wexin.common;

import com.sandu.pay.wexin.protocol.UnifiedOrderReqData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class XMLParser {
	public static Map<String, Object> getMapFromXML(String xmlString)
			throws ParserConfigurationException, IOException, SAXException {

		// 这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputStream is = Util.getStringStream(xmlString);
		Document document = builder.parse(is);

		// 获取到document里面的全部结点
		NodeList allNodes = document.getFirstChild().getChildNodes();
		Node node;
		Map<String, Object> map = new HashMap<String, Object>();
		int i = 0;
		while (i < allNodes.getLength()) {
			node = allNodes.item(i);
			if (node instanceof Element) {
				map.put(node.getNodeName(), node.getTextContent());
			}
			i++;
		}
		return map;

	}

	public static String getResponseWeixin(boolean success, String message) {
		/*
		 * <xml> <return_code><![CDATA[SUCCESS]]></return_code>
		 * <return_msg><![CDATA[OK]]></return_msg> </xml>
		 */
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append("<return_code><![CDATA[");
		if (success) {
			sb.append("SUCCESS");
		} else {
			sb.append("FAIL");
		}
		sb.append("]]></return_code>");
		sb.append("<return_msg><![CDATA[");
		sb.append(message);
		sb.append("]]></return_msg>");
		sb.append("</xml>");
		//////System.out.println("getResponseWeixin:"+sb.toString());
		return sb.toString();
	}

	public static String getOrderXml(UnifiedOrderReqData unifiedorder) {
		// 构造xml参数的时候，至少又是个必传参数
		/*
		 * <xml> <appid>wx2421b1c4370ec43b</appid> <attach>支付测试</attach>
		 * <body>JSAPI支付测试</body> <mch_id>10000100</mch_id>
		 * <nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>
		 * <notify_url>http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php</
		 * notify_url> <openid>oUpF8uMuAJO_M2pxb1Q9zNjWeS6o</openid>
		 * <out_trade_no>1415659990</out_trade_no>
		 * <spbill_create_ip>14.23.150.211</spbill_create_ip>
		 * <total_fee>1</total_fee> <trade_type>JSAPI</trade_type>
		 * <sign>0CB01533B8C1EF103065174F50BCA001</sign> </xml>
		 */

		if (unifiedorder != null) {
			StringBuffer bf = new StringBuffer();
			bf.append("<xml>");

			bf.append("<appid><![CDATA[");
			bf.append(unifiedorder.getAppid());
			bf.append("]]></appid>");

			bf.append("<mch_id><![CDATA[");
			bf.append(unifiedorder.getMch_id());
			bf.append("]]></mch_id>");

			bf.append("<nonce_str><![CDATA[");
			bf.append(unifiedorder.getNonce_str());
			bf.append("]]></nonce_str>");

			bf.append("<sign><![CDATA[");
			bf.append(unifiedorder.getSign());
			bf.append("]]></sign>");

			bf.append("<body><![CDATA[");
			bf.append(unifiedorder.getBody());
			bf.append("]]></body>");

			bf.append("<out_trade_no><![CDATA[");
			bf.append(unifiedorder.getOut_trade_no());
			bf.append("]]></out_trade_no>");

			bf.append("<total_fee><![CDATA[");
			bf.append(unifiedorder.getTotal_fee());
			bf.append("]]></total_fee>");

			bf.append("<spbill_create_ip><![CDATA[");
			bf.append(unifiedorder.getSpbill_create_ip());
			bf.append("]]></spbill_create_ip>");

			bf.append("<notify_url><![CDATA[");
			bf.append(unifiedorder.getNotify_url());
			bf.append("]]></notify_url>");

			bf.append("<trade_type><![CDATA[");
			bf.append(unifiedorder.getTrade_type());
			bf.append("]]></trade_type>");

			bf.append("</xml>");
			return bf.toString();
		}

		return "";
	}
}
