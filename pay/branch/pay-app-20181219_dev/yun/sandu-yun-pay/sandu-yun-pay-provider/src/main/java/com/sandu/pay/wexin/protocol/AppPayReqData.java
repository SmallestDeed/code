package com.sandu.pay.wexin.protocol;

import com.sandu.pay.wexin.common.RandomStringGenerator;
import com.sandu.pay.wexin.common.Signature;
import com.sandu.pay.wexin.common.Util;
import com.sandu.pay.wexin.common.WxConfigure;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class AppPayReqData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String appid;
	private String partnerid;
	private String prepayid;
	private String packageName="Sign=WXPay";
	private String noncestr;
	private String timestamp;
	private String code_url;
	private String sign="";
	
	public AppPayReqData(String prepayid){
		setPrepayid(prepayid);
		setAppid(WxConfigure.getAppid());
		setNoncestr(RandomStringGenerator.getRandomStringByLength(32));
		setPartnerid(WxConfigure.getMchid());
		setTimestamp(String.valueOf(Util.getTimeStamp()));
		 //根据API给的签名规则进行签名
        String sign = Signature.getSign(toMap());
        
        setSign(sign);//把签名数据设置到Sign这个属性中
	}
	
	public AppPayReqData(String prepayid,String codeUrl){
		setPrepayid(prepayid);
		setCode_url(codeUrl);
		setAppid(WxConfigure.getAppid());
		setNoncestr(RandomStringGenerator.getRandomStringByLength(32));
		setPartnerid(WxConfigure.getMchid());
		setTimestamp(String.valueOf(Util.getTimeStamp()));
		 //根据API给的签名规则进行签名
        String sign = Signature.getSign(toMap());
        
        setSign(sign);//把签名数据设置到Sign这个属性中
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}

	public String getPrepayid() {
		return prepayid;
	}

	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
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

	public String getCode_url() {
		return code_url;
	}

	public void setCode_url(String code_url) {
		this.code_url = code_url;
	}
}
