package com.sandu.web.user;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in AM 10:33 2018/8/27 0027
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sandu.base.QRCode;
import com.sandu.common.file.util.file.FilePathUtil;
import com.sandu.common.file.util.formater.FormatterUtil;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.StringUtils;
import com.sandu.constant.Punctuation;
import com.sandu.pay.alipay.util.WXChatRequest;
import com.sandu.pay.alipay.util.httpClient.AccessTokenSuccess;
import com.sandu.pay.alipay.util.httpClient.TicketSuccess;
import com.sandu.user.model.QRCodeShareTypeConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author weisheng
 * @Title: 二维码生成
 * @Package
 * @Description:
 * @date 2018/8/27 0027AM 10:33
 */
@RestController
@Slf4j
@RequestMapping("/v1/union/sxw")
public class QRCodeController {

    private static Gson gson = new Gson();


    private static final String SECRET_KEY = "mDUsnPokhdcTgjip67sS0n1QaCg8rseWhEDKsChvkyX9hTRUmSwV8Tio43tNgPFLUGlLqOT0d8E8iw85ZtZS";

    private static final String ACESSCODEURRL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx6867dc7fcf7eac50&secret=509ed82a5f4e8289c5e6005c43882004";

    private static final String TICKET_URL="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";

    private static final String CODEURL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";

    @Value("${file.storage.path}")
    private String storagePath;

    @Value("${company.access.token.url}")
    private String accessTokenUrl;

    @Value("${QRCode.url}")
    private String QRCodeUrl;

    @Value("${page}")
    private String page;

    @Value("${width}")
    private String width;

    @Value("${auto.color}")
    private String autoColor;

    @Value("${line.color}")
    private String lineColor;

    @Value("${is.hyaline}")
    private String isHyaline;

    @Value("${share.Sign}")
    private String shareSign;


    @Value("${app.server.url}")
    private String appServerUrl;


    @InitBinder
    public void initBinder(WebDataBinder binder) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, "inviteTime", new CustomDateEditor(
                dateFormat, true));
    }

    /**
     * @Title: 生成分享到贴吧, 豆瓣, 知乎, 微头条, 简书的小程序码
     * 分享类型:'1:微信朋友圈,2:微信好友,3:QQ好友,4:QQ空间,5:微博,6:贴吧,7:豆瓣,8:知乎,9:微头条,10:简书
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/4/27 0027PM 6:02
     */
    @RequestMapping(value = "/minicode", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope getMiniCode(@RequestParam(value = "key", required = true) String key,
                                                      @RequestParam(value = "shareType", required = true) Integer shareType, HttpServletRequest request) {
        if (StringUtils.isBlank(key) || !key.equals(SECRET_KEY)) {
            log.warn("秘钥参数::" + key);
            return new ResponseEnvelope(false, "无法获取小程序码,请先授权!!!");
        }
        String QRPicName;

        switch (shareType) {
            case 1:
                QRPicName = QRCodeShareTypeConstant.WEI_XIN_P_Y_QUAN;
                break;
            case 2:
                QRPicName = QRCodeShareTypeConstant.WEI_XIN_HAOYOU;
                break;
            case 3:
                QRPicName = QRCodeShareTypeConstant.QQ_HAO_YOU;
                break;
            case 4:
                QRPicName = QRCodeShareTypeConstant.QQ_KONG_JIAN;
                break;
            case 5:
                QRPicName = QRCodeShareTypeConstant.WEI_BO;
                break;
            case 6:
                QRPicName = QRCodeShareTypeConstant.TIE_BA;
                break;
            case 7:
                QRPicName = QRCodeShareTypeConstant.DOU_BAN;
                break;
            case 8:
                QRPicName = QRCodeShareTypeConstant.ZHI_HU;
                break;
            case 9:
                QRPicName = QRCodeShareTypeConstant.WEI_TOU_TIAO;
                break;
            case 10:
                QRPicName = QRCodeShareTypeConstant.JIAN_SHU;
                break;

            default:
                return new ResponseEnvelope(false, "获取微信二维码失败,分享类型无法识别");
        }


        String sceneStr = 7049 + "," + shareType + "," + System.currentTimeMillis()+","+"mini";
        log.info("生成微信分享参数:" + sceneStr);

        //获取微信accessToken
        String accessTokenJson = WXChatRequest.httpsRequest(accessTokenUrl, "GET", null);
        String accessToken = null;
        if (accessTokenJson.contains("access_token")) {
            Type type = new TypeToken<AccessTokenSuccess>() {
            }.getType();
            AccessTokenSuccess accessTokenSuccess = gson.fromJson(accessTokenJson, type);
            accessToken = accessTokenSuccess.getAccess_token();
            log.info("获取微信accessToken:" + accessToken);
        } else {
            return new ResponseEnvelope(false, "获取微信accessToken失败", accessTokenJson);
        }
        //生成二维码保存路径
        String absolutePath = FilePathUtil.absolutePath(storagePath, "xzmini", "shareSign", "image", LocalDateTime.now(), QRPicName+"_"+LocalDateTime.now().format(FormatterUtil.nameFormatter) + ".png");
        log.info("二维码图片路径:" + absolutePath);
        //封装微信图片参数
        QRCode qrCode = new QRCode();
        qrCode.setQRUrl(QRCodeUrl);
        qrCode.setScene(sceneStr);
        qrCode.setPage(page);
        qrCode.setWidth(Integer.parseInt(width));
        qrCode.setAutoColor(Boolean.parseBoolean(autoColor));
        qrCode.setLineColor(lineColor);
        qrCode.setIsHyaline(Boolean.parseBoolean(isHyaline));
        log.info("封装微信图片参数:" + gson.toJson(qrCode));
        //调用微信API获取二维码
        String returnPath;
        try {
            String miniqrQrPicPath = WXChatRequest.getminiqrQr(accessToken, absolutePath, qrCode);
            String relativePath = FilePathUtil.relativePath(storagePath, absolutePath);
            if (!relativePath.startsWith(Punctuation.SLASH)) {
                returnPath = appServerUrl+Punctuation.SLASH + relativePath;
            } else {
                returnPath = appServerUrl+relativePath;
            }
            log.info("微信二维码地址:" + returnPath);
            if (StringUtils.isEmpty(miniqrQrPicPath)) {
                return new ResponseEnvelope(false, "生成二维码失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEnvelope(false, "微信参数配置错误,无法获取二维码");
        }


        return new ResponseEnvelope(true, "", returnPath);
    }












    /**
     * @Title: 生成分享到贴吧, 豆瓣, 知乎, 微头条, 简书的公众号二维码
     * 分享类型:'1:微信朋友圈,2:微信好友,3:QQ好友,4:QQ空间,5:微博,6:贴吧,7:豆瓣,8:知乎,9:微头条,10:简书
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/8/27 0027PM 9:02
     */
    @RequestMapping(value = "/qrcode", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope getQRCode(@RequestParam(value = "key", required = true) String key,
                                                      @RequestParam(value = "shareType", required = true) Integer shareType, HttpServletRequest request) {
        if (StringUtils.isBlank(key) || !key.equals(SECRET_KEY)) {
            log.warn("秘钥参数::" + key);
            return new ResponseEnvelope(false, "无法获取公众号二维码,请先授权!!!");
        }
        String QRPicName;

        switch (shareType) {
            case 1:
                QRPicName = QRCodeShareTypeConstant.WEI_XIN_P_Y_QUAN;
                break;
            case 2:
                QRPicName = QRCodeShareTypeConstant.WEI_XIN_HAOYOU;
                break;
            case 3:
                QRPicName = QRCodeShareTypeConstant.QQ_HAO_YOU;
                break;
            case 4:
                QRPicName = QRCodeShareTypeConstant.QQ_KONG_JIAN;
                break;
            case 5:
                QRPicName = QRCodeShareTypeConstant.WEI_BO;
                break;
            case 6:
                QRPicName = QRCodeShareTypeConstant.TIE_BA;
                break;
            case 7:
                QRPicName = QRCodeShareTypeConstant.DOU_BAN;
                break;
            case 8:
                QRPicName = QRCodeShareTypeConstant.ZHI_HU;
                break;
            case 9:
                QRPicName = QRCodeShareTypeConstant.WEI_TOU_TIAO;
                break;
            case 10:
                QRPicName = QRCodeShareTypeConstant.JIAN_SHU;
                break;

            default:
                return new ResponseEnvelope(false, "获取微信二维码失败,分享类型无法识别");
        }


        String sceneStr = 7049 + "," + shareType + "," + System.currentTimeMillis();
        log.info("生成微信分享参数:" + sceneStr);


        //获取微信accessToken
        String accessTokenJson = WXChatRequest.httpsRequest(ACESSCODEURRL, "GET", null);
        String accessToken = null;
        if (accessTokenJson.contains("access_token")) {
            Type type = new TypeToken<AccessTokenSuccess>() {
            }.getType();
            AccessTokenSuccess accessTokenSuccess = gson.fromJson(accessTokenJson, type);
            accessToken = accessTokenSuccess.getAccess_token();
            log.info("获取微信accessToken:" + accessToken);
        } else {
            return new ResponseEnvelope(false, "获取微信accessToken失败", accessTokenJson);
        }


        //获取带参的tiket
        //封装二维码请求参数
        Map<String,String> intMap = new HashMap<String,String>();
        intMap.put("scene_str",sceneStr);
        Map<String,Map<String,String>> mapMap = new HashMap<String,Map<String,String>>();
        mapMap.put("scene", intMap);
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        paramsMap.put("action_name", "QR_LIMIT_STR_SCENE");
        paramsMap.put("action_info", mapMap);
        String code = new Gson().toJson(paramsMap);

        String ticketJson = WXChatRequest.httpsRequest(CODEURL+accessToken, "POST", code);

        TicketSuccess ticketSuccess;
        if (ticketJson.contains("ticket") && ticketJson.contains("url") ) {
            Type type = new TypeToken<TicketSuccess>() {
            }.getType();
            ticketSuccess = gson.fromJson(ticketJson, type);
            if(null==ticketSuccess){
                return new ResponseEnvelope(false, "获取微信ticketSuccess数据异常", gson.toJson(ticketSuccess));
            }
            log.info("获取微信ticketSuccess:" + ticketSuccess);
        } else {
            return new ResponseEnvelope(false, "获取微信ticketSuccess失败", ticketJson);
        }

        return new ResponseEnvelope(true, QRPicName+"二维码", TICKET_URL+ticketSuccess.getTicket());
    }
















}
