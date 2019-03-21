package com.sandu.pay.alipay.util;

import com.sandu.pay.alipay.common.AlipayConfig;
import com.sandu.pay.alipay.sign.RSA;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/* *
 *类名：AlipayFunction
 *功能：支付宝接口公用函数类
 *详细：该类是请求、通知返回两个文件所调用的公用函数核心处理文件，不需要修改
 *版本：1.0
 */
public class AlipayCore {
	
	/***
	 * 获取订单信息
	 * @param orderNo
	 * @param subject
	 * @param body
	 * @param total_fee
	 * @return
	 */
	public static String getOrder(String orderNo,String subject,String body,int total_fee){
		String orderInfo="";
		Map<String, String> params=new HashMap<String, String>();
		params.put("service", "\""+AlipayConfig.getService()+"\"");
		params.put("partner", "\""+AlipayConfig.getPartner()+"\"");
		params.put("_input_charset", "\""+AlipayConfig.getInput_charset()+"\"");
		params.put("sign_type", "\""+AlipayConfig.getSign_type()+"\"");
		params.put("notify_url", "\""+AlipayConfig.getNotify_url()+"\"");
		params.put("out_trade_no", "\""+orderNo+"\"");
		params.put("subject", "\""+subject+"\"");
		params.put("payment_type", "\"1\"");
		params.put("seller_id", "\""+AlipayConfig.getSeller_id()+"\"");
		params.put("total_fee", "\""+total_fee*0.01+"\"");
		params.put("body", "\""+body+"\"");
    	//过滤空值、sign与sign_type参数
    	Map<String, String> sParaNew = AlipayCore.paraFilter(params);
        //获取待签名字符串
        String preSignStr = AlipayCore.createLinkString(sParaNew);
        String sign = RSA.sign(preSignStr, AlipayConfig.getPrivate_key(), AlipayConfig.getInput_charset());
        String payInfo=orderInfo + "&sign=\"" + sign + "\"&sign_type=\"RSA\"";
		return payInfo;
	}
	
    /** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /** 
     * 把数组所有元素，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要参与字符拼接的参数组
     * @param sorts   是否需要排序 true 或者 false
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord,String filename) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(AlipayConfig.getLog_path() + "alipay_log_" + System.currentTimeMillis()+filename+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
