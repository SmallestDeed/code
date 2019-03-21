package com.sandu.pay.wexin.protocol;

import com.sandu.pay.minipro.util.SignUtil;
import com.sandu.pay.wexin.common.RandomStringGenerator;
import com.sandu.pay.wexin.common.Signature;
import com.sandu.pay.wexin.common.WxConfigure;
import com.sandu.pay.wexin.metadata.WxTradeType;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class UnifiedOrderReqData {
	  //每个字段具体的意思请查看API文档
    private String appid = "";
    private String mch_id = "";
    private String nonce_str = "";
    private String sign = "";
    private String body = "";
    private String out_trade_no = "";
    private int total_fee = 0;
    private String spbill_create_ip = "";
    private String notify_url="";
    private String trade_type=WxTradeType.APP;
    private String time_expire="";
    private String openid = "";

    /**
     * 小程序支付
     *
     * @param appid      微信分配的小程序ID
     * @param mchId      微信支付分配的商户号
     * @param body       商品简单描述
     * @param outTradeNo 商户系统内部订单号
     * @param totalFee   订单总金额，单位为分
     * @param notifyUrl  回调地址
     * @param tradeType  小程序取值如下：JSAPI
     * @param openid     用户在商户appid下的唯一标识
     * @param key        商户支付密钥
     */
    public UnifiedOrderReqData(String appid, String mchId, String body,
                               String outTradeNo, int totalFee, String notifyUrl,
                               String tradeType, String openid, String key, String timeExpire) {
        setAppid(appid);
        setMch_id(mchId);
        setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
        setBody(body);
        setOut_trade_no(outTradeNo);
        setTotal_fee(totalFee);
        //订单生成的机器IP
        setSpbill_create_ip(WxConfigure.getIP());
        setNotify_url(notifyUrl);
        setTrade_type(tradeType);
        setOpenid(openid);
        setTime_expire(timeExpire);
        //根据API给的签名规则进行签名
        String sign = Signature.getSign(toMap(), key);
        setSign(sign);//把签名数据设置到Sign这个属性中
    }

    /**
     * @param authCode 这个是扫码终端设备从用户手机上扫取到的支付授权号，这个号是跟用户用来支付的银行卡绑定的，有效期是1分钟
     * @param body 要支付的商品的描述信息，用户会在支付成功页面里看到这个信息
     * @param attach 支付订单里面可以填的附加数据，API会将提交的这个附加数据原样返回
     * @param outTradeNo 商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
     * @param totalFee 订单总金额，单位为“分”，只能整数
     * @param deviceInfo 商户自己定义的扫码支付终端设备号，方便追溯这笔交易发生在哪台终端设备上
     * @param spBillCreateIP 订单生成的机器IP
     * @param timeStart 订单生成时间， 格式为yyyyMMddHHmmss，如2009年12 月25 日9 点10 分10 秒表示为20091225091010。时区为GMT+8 beijing。该时间取自商户服务器
     * @param timeExpire 订单失效时间，格式同上
     * @param goodsTag 商品标记，微信平台配置的商品标记，用于优惠券或者满减使用
     * @param openid trade_type=JSAPI，此参数必传，
     */
    public UnifiedOrderReqData(String body,String outTradeNo,int totalFee,String wxNotifyUrl){

        //微信分配的公众号ID（开通公众号之后可以获取到）
        setAppid(WxConfigure.getAppid());

        //微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
        setMch_id(WxConfigure.getMchid());

        //要支付的商品的描述信息，用户会在支付成功页面里看到这个信息
        setBody(body);

        //商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
        setOut_trade_no(outTradeNo);

        //订单总金额，单位为“分”，只能整数
        setTotal_fee(totalFee);

        //订单生成的机器IP
        setSpbill_create_ip(WxConfigure.getIP());

        setNotify_url(wxNotifyUrl);

        //随机字符串，不长于32 位
        setNonce_str(RandomStringGenerator.getRandomStringByLength(32));

        //根据API给的签名规则进行签名
        String sign = Signature.getSign(toMap());
        
        setSign(sign);//把签名数据设置到Sign这个属性中

    }
   

    
    public UnifiedOrderReqData(String body,String outTradeNo,int totalFee,String tradeType, String timeExpire,String wxNotifyUrl){

    	setTrade_type(tradeType);
        //微信分配的公众号ID（开通公众号之后可以获取到）
        setAppid(WxConfigure.getAppid());

        //微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
        setMch_id(WxConfigure.getMchid());

        //要支付的商品的描述信息，用户会在支付成功页面里看到这个信息
        setBody(body);

        //商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
        setOut_trade_no(outTradeNo);

        //订单总金额，单位为“分”，只能整数
        setTotal_fee(totalFee);

        //订单生成的机器IP
        setSpbill_create_ip(WxConfigure.getIP());

        setNotify_url(wxNotifyUrl);

        //随机字符串，不长于32 位
        setNonce_str(RandomStringGenerator.getRandomStringByLength(32));

        // 失效时间
        // 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则
        // 注意：最短失效时间间隔必须大于5分钟
        setTime_expire(timeExpire);
        
        //根据API给的签名规则进行签名
        String sign = Signature.getSign(toMap());
        
        //////System.out.println("sign:"+sign);
        setSign(sign);//把签名数据设置到Sign这个属性中
        
    }

     public UnifiedOrderReqData(String appId,String muchId,String key,String body,String outTradeNo,int totalFee,String tradeType, String timeExpire,String wxNotifyUrl){

    	setTrade_type(tradeType);
        //微信分配的公众号ID（开通公众号之后可以获取到）
        setAppid(appId);

        //微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
        setMch_id(muchId);

        //要支付的商品的描述信息，用户会在支付成功页面里看到这个信息
        setBody(body);

        //商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
        setOut_trade_no(outTradeNo);

        //订单总金额，单位为“分”，只能整数
        setTotal_fee(totalFee);

        //订单生成的机器IP
        setSpbill_create_ip(WxConfigure.getIP());

        setNotify_url(wxNotifyUrl);

        //随机字符串，不长于32 位
        setNonce_str(RandomStringGenerator.getRandomStringByLength(32));

        // 失效时间
        // 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则
        // 注意：最短失效时间间隔必须大于5分钟
        setTime_expire(timeExpire);
        
        //根据API给的签名规则进行签名
        String sign = Signature.getSign(toMap(),key);
        
        //////System.out.println("sign:"+sign);
        setSign(sign);//把签名数据设置到Sign这个属性中
        
    }


    /**
     * Mini program pay
     * @param appId
     * @param muchId
     * @param key
     * @param body
     * @param outTradeNo
     * @param totalFee
     * @param tradeType
     * @param timeExpire
     * @param wxNotifyUrl
     * @param openid
     */
    public UnifiedOrderReqData(String appId,String muchId,String key,String body,String outTradeNo,
                               int totalFee,String tradeType, String timeExpire,String wxNotifyUrl,
                               String openid){

        setTrade_type(tradeType);
        //微信分配的公众号ID（开通公众号之后可以获取到）
        setAppid(appId);

        //微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
        setMch_id(muchId);

        //要支付的商品的描述信息，用户会在支付成功页面里看到这个信息
        setBody(body);

        //商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
        setOut_trade_no(outTradeNo);

        //订单总金额，单位为“分”，只能整数
        setTotal_fee(totalFee);

        //订单生成的机器IP
        setSpbill_create_ip(WxConfigure.getIP());

        setNotify_url(wxNotifyUrl);

        //随机字符串，不长于32 位
        setNonce_str(RandomStringGenerator.getRandomStringByLength(32));

        // 失效时间
        // 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则
        // 注意：最短失效时间间隔必须大于5分钟
        setTime_expire(timeExpire);
        setOpenid(openid);
        //根据API给的签名规则进行签名,TODO :Changed it to MD5
        //String sign = Signature.getSign(toMap(),key);

       String sign = SignUtil.getSign(toMap(),key);
        //////System.out.println("sign:"+sign);
        setSign(sign);//把签名数据设置到Sign这个属性中

    }



    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }


    public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	public String getNotify_url() {
		return notify_url;
	}


	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}


	public String getTrade_type() {
		return trade_type;
	}


	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}


	public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
 
    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
                    map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    @Override
    public String toString() {
        return "UnifiedOrderReqData{" +
                "appid='" + appid + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                ", sign='" + sign + '\'' +
                ", body='" + body + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", total_fee=" + total_fee +
                ", spbill_create_ip='" + spbill_create_ip + '\'' +
                ", notify_url='" + notify_url + '\'' +
                ", trade_type='" + trade_type + '\'' +
                ", time_expire='" + time_expire + '\'' +
                ", openid='" + openid + '\'' +
                '}';
    }
}
