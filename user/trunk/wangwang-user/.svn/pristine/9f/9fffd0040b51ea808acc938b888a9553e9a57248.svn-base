package com.sandu.common.sms;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sandu.config.SmsConfig;



public class Utils {
	private static Logger logger = LoggerFactory.getLogger(Utils.class);
	public static final String DATETIMESSS = "yyyyMMddHHmmssSSS";
    public static final String DATE = "yyyy-MM-dd";
	//public static ResourceBundle app = ResourceBundle.getBundle("sms");
	
	 
	public static int getIntValue(String v) {
		return getIntValue(v, -1);
	}
    public static String SIGN = "";
    public static String PASSWORD = "";
    public static String SEND_MESSAGE = "";
    public static String ALLOWED_TO_SEND = "";
    public static Integer VALID_TIME = null;
    public static String SERVICE_PHONE = "";
    public static String AUTHORIZED_CONFIG_VALID_TIME7 = "";
    public static String AUTHORIZED_CONFIG_VALID_TIME30 = "";

    static {
        if (StringUtils.isBlank(SIGN)) {
            SIGN = SmsConfig.SIGNKEY;
        }
        if (StringUtils.isBlank(PASSWORD)) {
            PASSWORD = SmsConfig.PASSWORD;
        }
        if (StringUtils.isBlank(SEND_MESSAGE)) {
            SEND_MESSAGE = SmsConfig.SEND_MESSAGE;
        }
        if (StringUtils.isBlank(ALLOWED_TO_SEND)) {
            ALLOWED_TO_SEND = SmsConfig.ALLOWED_TOS_END;
        }
        if (VALID_TIME == null) {
            String validTime = SmsConfig.VALID_TIME;
            if (StringUtils.isNotBlank(validTime) && StringUtils.isNumeric(validTime)) {
                VALID_TIME = 60000 * Integer.parseInt(validTime);
            } else {
                VALID_TIME = 60000 * 3;//默认3分钟
            }
        }
        if (StringUtils.isBlank(SERVICE_PHONE)) {
            SERVICE_PHONE = SmsConfig.SERVICE_PHONE;
        }
        if (StringUtils.isBlank(AUTHORIZED_CONFIG_VALID_TIME7)) {
            AUTHORIZED_CONFIG_VALID_TIME7 = SmsConfig.AUTHORIZED_CONFIG_VALID_TIME7;
        }
        if (StringUtils.isBlank(AUTHORIZED_CONFIG_VALID_TIME30)) {
            AUTHORIZED_CONFIG_VALID_TIME30 = SmsConfig.AUTHORIZED_CONFIG_VALID_TIME30;
        }
    }
	/***** 将给出的字符串v转换成整形值返回，如果例外则返回预给值def ************/
	public static int getIntValue(String v, int def) {
		try {
			return Integer.parseInt(v);
		} catch (Exception ex) {
			return def;
		}
	}

    public static String getDate() {
        SimpleDateFormat format = new SimpleDateFormat(DATE);
        return format.format(new Date());
    }

	public static String getCurrentDateTime(String _dtFormat) {
		String currentdatetime = "";
		try {
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat dtFormat = new SimpleDateFormat(_dtFormat);
			currentdatetime = dtFormat.format(date);
		} catch (Exception e) {
			////// System.out.println("时间格式不正确");
			e.printStackTrace();
		}
		return currentdatetime;
	}
	/**
	 * 产生指定长度的无规律数字字符串
	 * 
	 * @param aLength
	 *            生成的随机数的长度
	 * @return 生成的随机字符串 throws 卡号生成异常
	 */
	public static String generateRandomDigitString(int aLength) {
		SecureRandom tRandom = new SecureRandom();
		long tLong;
		String aString = "";

		tRandom.nextLong();
		tLong = Math.abs(tRandom.nextLong());
		aString = (String.valueOf(tLong)).trim();
		while (aString.length() < aLength) {
			tLong = Math.abs(tRandom.nextLong());
			aString += (String.valueOf(tLong)).trim();
		}
		aString = aString.substring(0, aLength);

		return aString;
	}
	
	
// 下发短信验证码
    public static String sendSMS(String url, String param) {
        String ret = "";
        if ("true".equals(ALLOWED_TO_SEND)) {
            url = url + "?cdkey=" + SIGN + "&password=" + PASSWORD;
            if (StringUtils.isNotBlank(param)) {
                url = url + "&" + param;
            }
            logger.info("[用户中心服务]:下发短信验证码:URL:{}", url);
            String responseString = HttpClientUtil.getInstance().doGetRequest(url);
            logger.info("[用户中心服务]:下发短信验证码完成:Response:{}", responseString);
            responseString = responseString.trim();
            if (null != responseString && !"".equals(responseString)) {
                ret = xmlMt(responseString);
            }
        } else {
            logger.warn("[用户中心服务]:下发短信验证码: Param ALLOWED_TO_SEND is false,Do no't allow send the sms check code!");
            ret = "0";
        }
        return ret;
    }
    // 解析下发response
    public static String xmlMt(String response) {
        String result = "0";
        Document document = null;
        try {
            document = DocumentHelper.parseText(response);
        } catch (DocumentException e) {
            e.printStackTrace();
            result = "-250";
        }
        Element root = document.getRootElement();
        result = root.elementText("error");
        if (null == result || "".equals(result)) {
            result = "-250";
        }
        return result;
    }
    /**
     * 添加到Map中
     *
     * @param key
     * @param value
     * @return
     */
   /* public boolean addMap(String mapName, String key, String value) {
        if (null == mapName || key == null || value == null) {
            return false;
        }

        Map<String, String> objectMap = new HashMap<>(1);
        objectMap.put(key, value);

        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.hmset(mapName, objectMap);
            return true;
        } catch (Exception ex) {
            logger.error("setMap error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }*/
}