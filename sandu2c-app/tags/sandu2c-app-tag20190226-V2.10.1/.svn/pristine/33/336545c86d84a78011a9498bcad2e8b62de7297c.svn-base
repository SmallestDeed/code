package com.sandu.system.sms.httpclient;

import com.sandu.system.sms.bean.Mo;
import com.sandu.system.sms.bean.StatusReport;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SmsClient {

    private final static String CLASS_LOG_PREFIX = "[发送验证码服务]";
    private final static Logger logger = LoggerFactory.getLogger(SmsClient.class);

    public static ResourceBundle app = ResourceBundle.getBundle("config/sms");
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
            SIGN = app.getString("signKey");
        }
        if (StringUtils.isBlank(PASSWORD)) {
            PASSWORD = app.getString("password");
        }
        if (StringUtils.isBlank(SEND_MESSAGE)) {
            SEND_MESSAGE = app.getString("sendMessage");
        }
        if (StringUtils.isBlank(ALLOWED_TO_SEND)) {
            ALLOWED_TO_SEND = app.getString("allowedToSend");
        }
        if (VALID_TIME == null) {
            String validTime = app.getString("validTime");
            if (StringUtils.isNotBlank(validTime) && StringUtils.isNumeric(validTime)) {
                VALID_TIME = 60000 * Integer.parseInt(validTime);
            } else {
                VALID_TIME = 60000 * 3;//默认3分钟
            }
        }
        if (StringUtils.isBlank(SERVICE_PHONE)) {
            SERVICE_PHONE = app.getString("ServicePhone");
        }
        if (StringUtils.isBlank(AUTHORIZED_CONFIG_VALID_TIME7)) {
            AUTHORIZED_CONFIG_VALID_TIME7 = app.getString("authorizedConfigValidTime7");
        }
        if (StringUtils.isBlank(AUTHORIZED_CONFIG_VALID_TIME30)) {
            AUTHORIZED_CONFIG_VALID_TIME30 = app.getString("authorizedConfigValidTime30");
        }
    }

    public static SmsClient getInstance() {
        return new SmsClient();
    }

    // 注册、注销
    public static String registAndLogout(String url, String param) {
        String ret = "失败";
        url = url + "?cdkey=" + SIGN + "&password=" + PASSWORD;
        if (StringUtils.isNotBlank(param)) {
            url = url + "&" + param;
        }
        String responseString = HttpClientUtil.getInstance().doGetRequest(url);
        responseString = responseString.trim();
        if (null != responseString && !"".equals(responseString)) {
            ret = xmlResponseForRegist(responseString);
        }
        return ret;
    }

    // 解析注册注销响应
    public static String xmlResponseForRegist(String response) {
        String result = "失败";
        Document document = null;
        try {
            document = DocumentHelper.parseText(response);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();
        result = root.elementText("error");
        return result;
    }

    // 获取mo
    public static List<Mo> getMos(String url) {
        List<Mo> _Mos = new ArrayList<Mo>();

        if ("".equals(url)) {
            return _Mos;
        }
        String param = "cdkey=" + SIGN + "&password=" + PASSWORD;
        url = url + "?" + param;
        HttpClientUtil.getInstance();
        String responseString = HttpClientUtil.getInstance().doGetRequest(url);

        responseString = responseString.trim();
        if (null != responseString && !"".equals(responseString)) {
            List<Element> elements = xmlDoc(responseString);
            for (Element element : elements) {
                if (null != element) {
                    logger.info("【SmsClient】上行请求->" + responseString);
                    Mo mo = new Mo();
                    mo.setMobileNumber(element.elementText("srctermid"));
                    mo.setSmsContent(element.elementText("msgcontent"));
                    mo.setAddSerial(element.elementText("addSerial"));
                    mo.setAddSerialRev(element.elementText("addSerialRev"));
                    mo.setSentTime(element.elementText("sendTime"));
                    _Mos.add(mo);
                }
            }
        }
        return _Mos;
    }

    // 获取report
    public static List<StatusReport> getReports(String url) {
        List<StatusReport> _Reports = new ArrayList<StatusReport>();
        if ("".equals(url)) {
            return _Reports;
        }
        String param = "cdkey=" + SIGN + "&password=" + PASSWORD;
        url = url + "?" + param;
        logger.info("【SmsClient】Request-Url:" + url);
        String responseString = HttpClientUtil.getInstance().doGetRequest(url);
        responseString = responseString.trim();
        if (null != responseString && !"".equals(responseString)) {
            List<Element> elements = xmlDoc(responseString);
            for (Element element : elements) {
                if (null != element) {
                    logger.info("【SmsClient】REPORT->" + element.elementText("seqid"));
                    StatusReport report = new StatusReport();
                    report.setMobile(element.elementText("srctermid"));
                    report.setErrorCode(element.elementText("state"));
                    report.setSeqID(Long.parseLong(element.elementText("seqid")));
                    report.setReceiveDate(element.elementText("receiveDate"));
                    report.setSubmitDate(element.elementText("submitDate"));
                    report.setServiceCodeAdd(element.elementText("addSerialRev"));
                    _Reports.add(report);
                }
            }

        }
        return _Reports;
    }

    // 下发短信验证码
    public static String sendSMS(String url, String param) {
        String ret = "";
        if ("true".equals(ALLOWED_TO_SEND)) {
            url = url + "?cdkey=" + SIGN + "&password=" + PASSWORD;
            if (StringUtils.isNotBlank(param)) {
                url = url + "&" + param;
            }
            logger.info(CLASS_LOG_PREFIX + "下发短信验证码:URL:{}", url);
            String responseString = HttpClientUtil.getInstance().doGetRequest(url);
            logger.info(CLASS_LOG_PREFIX + "下发短信验证码完成:Response:{}", responseString);
            responseString = responseString.trim();
            if (null != responseString && !"".equals(responseString)) {
                ret = xmlMt(responseString);
            }
        } else {
            logger.warn(CLASS_LOG_PREFIX + "下发短信验证码: Param ALLOWED_TO_SEND is false,Do no't allow send the sms check code!");
            ret = "0";
        }
        return ret;
    }

    // 获取余额
    public static String getBalance(String url) {
        String ret = "失败";
        url = url + "?cdkey=" + SIGN + "&password=" + PASSWORD;
        logger.info("【SmsClient】Balance->" + url);
        String responseString = HttpClientUtil.getInstance().doGetRequest(url);
        responseString = responseString.trim();
        if (null != responseString && !"".equals(responseString)) {
            ret = xmlResponse(responseString);
        }
        return ret;
    }

    // 统一解析格式
    public static String xmlResponse(String response) {
        String result = "失败";
        Document document = null;
        try {
            document = DocumentHelper.parseText(response);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();
        result = root.elementText("message");
        return result;
    }

    // 解析状态、上行
    private static List<Element> xmlDoc(String response) {
        boolean result = false;
        Document document = null;
        try {
            document = DocumentHelper.parseText(response);
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
        Element root = document.getRootElement();
        List<Element> list = root.elements();
        List<Element> elemets = new ArrayList();
        // 增强for循环来遍历所有的U8ArrivalVouch节点
        for (Element element : list) {
            String message = element.getName();
            if ("message".equalsIgnoreCase(message)) {
                if (element.elements().size() > 0) {
                    // //////System.out.println("--------------------"+element.elements().size());
                    elemets.add(element);
                }
            }

        }
        return elemets;
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

    public static void main(String[] args) {
        String result = "";
//		String url = "http://sdk4report.eucp.b2m.cn:8080/sdkproxy/querybalance.action";
//		result = SmsClient.getBalance(url);
        String mobile = "13631525876";
        String message = "你收到了一条测试短信！";
        long seqId = System.currentTimeMillis();
        String params = "phone=" + mobile + "&message=" + message + "&addserial=&seqid=" + seqId;
        result = SmsClient.sendSMS(SmsClient.SEND_MESSAGE, params);
        //System.out.println(result);
    }
}
