package com.nork.pano.controller;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.nork.common.cache.CacheManager;
import com.nork.common.cache.Cacher;
import com.nork.common.exception.BizException;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.pano.share.Sign;
import com.nork.common.util.HttpClient;
import com.nork.common.util.QRCode.MatrixToImageWriter;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.pano.model.constant.SceneTypeConstant;
import com.nork.pano.model.input.SceneDataSearch;
import com.nork.pano.model.qrcode.SceneQrCodeInfo;
import com.nork.pano.model.qrcode.SceneQrCodeResult;
import com.nork.pano.service.SceneQrCodeInfoService;
import com.nork.product.model.BaseCompanyMiniProgramConfig;
import com.nork.product.service.BaseCompanyService;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysUserService;
import com.sandu.common.LoginContext;
import com.sandu.user.model.UserTypeCode;
import net.sf.json.JSONObject;
import org.apache.commons.net.util.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Created by Administrator on 2017/7/17.
 */
@Controller
@RequestMapping(value = "/{style}/share")
public class ShareController {
    Logger logger = LoggerFactory.getLogger(ShareController.class);

    private HttpClient httpClient = null;
    private Cacher cacher = null;
    private static final String appid = Utils.getPropertyName("config/share","wx.share.appid","");
    private static final String app_secret = Utils.getPropertyName("config/share","wx.share.app_secret","");
    private static final String WX_SMALLl_APPID = Utils.getPropertyName("config/share","wx.small.share.appid","");
    private static final String WX_SMALL_APPSECRET = Utils.getPropertyName("config/share","wx.small.share.secret","");
    private static final String WX_SMALL_ACODEUN_URL = Utils.getPropertyName("config/share","wx.small.share.wxacodeun_url","");
    private static final String ACCESS_TOKEN_URL = Utils.getPropertyName("config/share","wx.share.access_token_url","");
    private static final String WX_SMALL_INFORMATION_D = Utils.getPropertyName("config/share","wx.small.share.copywriting.informationD","");
    private static final String WX_SMALL_INFORMATION_R = Utils.getPropertyName("config/share","wx.small.share.copywriting.informationR","");
    // 720 H5场景分享访问的前端地址
    private static final String SCENE_SHARE_H5_QRCODE_URL = Utils.getPropertyName("app","scene.share.H5.qrcode.url","https://720.sanduspace.com/v-seven-grass.html#/");
    //720 PC端浏览场景访问的前端地址
    private static final String SCENE_SHARE_PC_QRCODE_URL = Utils.getPropertyName("app","scene.share.PC.qrcode.url","https://720.sanduspace.com/v-seven-pc.html#/");
    private static final Integer SANDU_COMPANY_ID = 2501;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SceneQrCodeInfoService sceneQrCodeInfoService;
    @Autowired
    private BaseCompanyService baseCompanyService;

    /**
     * 获取微信配置信息
     * @return
     */
    @RequestMapping(value = "/getWXConfig")
    @ResponseBody
    public Object getWXConfig(HttpServletRequest request, HttpServletResponse response, String url){
        String access_token = "";
        String jspia_ticket = "";
        String access_token_url = Utils.getPropertyName("config/share","wx.share.access_token_url","");
        String jsapi_ticket_url = Utils.getPropertyName("config/share", "wx.share.jsapi_ticket_url", "");
        JSONObject jsonObject = new JSONObject();
        String responseBody = "";
        Object cacheObj = null;
        if( httpClient == null ) {
            httpClient = HttpClient.getInstance();
        }
        if( cacher == null ){
            cacher = CacheManager.getInstance().getCacher();
        }
        // 获取access_token
        cacheObj = cacher.get("ACCESS_TOKEN");
        if( cacheObj != null ){
            access_token = cacheObj.toString();
            //logger.debug("=========access_token from redis:"+access_token);
        }else {
            access_token_url = access_token_url + "&appid=" + appid + "&secret=" + app_secret;
            logger.info("=========access_token_url:"+access_token_url);
            responseBody = httpClient.sendHttpGet(access_token_url);
            if( StringUtils.isBlank(responseBody) ){
                return null;
            }
            jsonObject = JSONObject.fromObject(responseBody);
            if (jsonObject != null && jsonObject.get("access_token") != null) {
                access_token = jsonObject.getString("access_token");
                //logger.info("=========access_token from httpclient:"+access_token);
                // 放到redis缓存中
                cacher.set("ACCESS_TOKEN", access_token, 7000);
            }
        }

        // 获取jsapi_ticket
        if( StringUtils.isNotBlank(access_token) ){
            cacheObj = cacher.get("JSAPI_TICKET");
            //logger.debug("=========cacher.get(\"JSAPI_TICKET\"):"+cacher.get("JSAPI_TICKET"));
            if( cacheObj != null ){
                jspia_ticket = cacheObj.toString();
            }else {
                jsapi_ticket_url = jsapi_ticket_url + "&access_token=" + access_token;
                //logger.info("=========jsapi_ticket_url:"+jsapi_ticket_url);
                responseBody = httpClient.sendHttpGet(jsapi_ticket_url);
                if( StringUtils.isBlank(responseBody) ){
                    return null;
                }
                jsonObject = JSONObject.fromObject(responseBody);
                jspia_ticket = jsonObject.getString("ticket");
                // 放到redis中
                cacher.set("JSAPI_TICKET", jspia_ticket, 7000);
            }
        }

        // 生成签名相关信息
        if( StringUtils.isNotBlank(jspia_ticket) ){
            return Sign.sign(jspia_ticket, url);
        }
        return null;
    }


    /**
     * 获取二维码
     * @author chenqiang
     * @param request
     * @param response
     * @param shareDesc                 分享文案信息
     * @param sceneType                 场景类型
     * @param renderId                  渲染图片id
     * @param planSourceType            方案类型：1：草图、2：效果图
     * @param shareTitle                分享标题
     * @qrCodeType                      二维码类型  0(PC端浏览);1(PC端分享的普通二维码);2(PC端分享的随选网二维码);3(PC端分享的企业小程序二维码)
     * @return
     * @date 2018/10/8 0008 10:00
     */
    @RequestMapping("getWxacodeun")
    @ResponseBody
    public Object getWxacodeun(HttpServletRequest request, HttpServletResponse response,
                               String shareDesc,String sceneType,String renderId,String planSourceType,String msgId
                                ,String shareTitle,Integer qrCodeType
    ) throws IOException{
        //不传planSourceType,请求单点/多点单场景分享二维码时默认为是来源于效果图方案?
        planSourceType = StringUtils.isBlank(planSourceType) ? "2" : planSourceType;
        logger.info("参数:shareDesc="+shareDesc+"|sceneType="+sceneType+"|renderId="+renderId+"|planSourceType="+planSourceType);
        /**------参数获取与校验------*/
        LoginUser loginUser = null;
        loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(null == loginUser){
            return new ResponseEnvelope(false,"请重新登录",msgId);
        }
        if(!checkIsNum(sceneType)) {
            return  new ResponseEnvelope(false, "场景类型参数有误", msgId);
        }
        if(!checkIsNum(renderId) || Integer.parseInt(renderId) < 1) {
            return new ResponseEnvelope(false, "渲染信息参数有误", msgId);
        }
        if(!checkIsNum(planSourceType)) {
            return  new ResponseEnvelope(false, "方案类型信息参数有误", msgId);
        }
        if(StringUtils.isNotBlank(shareDesc) && shareDesc.length() > 30) {
            return new ResponseEnvelope(false, "分享文案长度过长", msgId);
        }
        if(StringUtils.isNotBlank(shareTitle) && shareTitle.length() > 30) {
            return new ResponseEnvelope(false, "分享标题长度过长", msgId);
        }
        InputStream inputStream = null;
        Integer i = null;
        Integer t = null;
        Integer b = null;
        Integer s = null;
        Integer c = null;

        try {
            /**------声明变量------*/
            inputStream = null;
            String defaultInformation = "2".equals(planSourceType) ? WX_SMALL_INFORMATION_R : WX_SMALL_INFORMATION_D;         //默认文案
            //默认分享标题
            String defaultShareTitle = "3D全景";
            i = 0;
            t = 0;
            b = 0;
            s = 0;
            //二维码类型
            c = 0;
            String companyName = sysUserService.getCompanyNameByUserId(loginUser.getId());          //获取用户企业名称
            defaultInformation = defaultInformation.replace("企业名称",companyName);          //替换真正的企业名称
            //限制分享文案长度
            if(defaultInformation.length() > 30){
                defaultInformation = defaultInformation.substring(0,27) +"...";
            }

            /**------二维码信息保存------*/
            i = Integer.parseInt(renderId);
            t = Integer.parseInt(sceneType);
       /* s = Integer.parseInt(planSourceType)+1;//U3D传递参数为0,1，与定义类型不符，所以加1*/
            s = Integer.parseInt(planSourceType);
            c = qrCodeType;
            //是否是默认生成的二维码
            boolean isDefaultQrCode = true;
            //标题或描述是否有改变
            boolean hasChanged = false;

            //如果类型为3/4/5,需要查看是否存在已修改文案的二维码信息
            SceneQrCodeInfo sceneQrCodeInfo = new SceneQrCodeInfo();
            if((t.intValue() == SceneTypeConstant.GROUP_SCENE
                    || t.intValue() == SceneTypeConstant.UNION_STORE_SINGLE_SCENE
                    || t.intValue() == SceneTypeConstant.STORE_RELEASE_SCENE)){
                SceneQrCodeInfo search = new SceneQrCodeInfo();
                search.setRenderId(renderId);
                search.setSceneType(t);
                search.setQrCodeType(qrCodeType);
                sceneQrCodeInfo = sceneQrCodeInfoService.getSceneQrCodeInfo(search);
                if(null != sceneQrCodeInfo){
                    isDefaultQrCode = false;
                    i = sceneQrCodeInfo.getId();
                    b = 1;
                    if(Utils.isBlank(shareDesc) && Utils.isBlank(shareTitle)){
                        shareDesc = sceneQrCodeInfo.getCopywritingInformation();
                        shareTitle = sceneQrCodeInfo.getShareTitle();
                        hasChanged = false;
                    }else{
                        if(!Objects.equals(shareDesc,sceneQrCodeInfo.getCopywritingInformation()) || !Objects.equals(shareTitle,sceneQrCodeInfo.getShareTitle())){
                            hasChanged = true;
                        }else{
                            hasChanged = false;
                        }
                    }
                }else{
                    //新生成二维码时
                    if(Utils.isBlank(shareDesc) && Utils.isBlank(shareTitle)){
                        hasChanged = true;
                    }else if(!Objects.equals(defaultInformation,shareDesc) || !Objects.equals(defaultShareTitle,shareTitle)){
                        hasChanged = true;
                    }
                }
            }else{
                if(Utils.isBlank(shareDesc) && Utils.isBlank(shareTitle)){
                    hasChanged = false;
                }else{
                    if(!Objects.equals(defaultInformation,shareDesc) || !Objects.equals(defaultShareTitle,shareTitle)){
                        hasChanged = true;
                    }
                }
            }

            if(Utils.isBlank(shareDesc)){
                shareDesc = defaultInformation;
            }
            if(Utils.isBlank(shareTitle)){
                shareTitle = defaultShareTitle;
            }

            //默认文案或标题是否改变
            if(hasChanged){
                    SceneQrCodeInfo codeInfo = getSceneQrCodeInfo(shareDesc,shareTitle,sceneType,renderId,loginUser,qrCodeType);
                    i = sceneQrCodeInfoService.insertSelective(codeInfo);
                    b = 1;
                    //返回文案信息改变
               /* defaultInformation = shareDesc;*/
            }
        } catch (Exception e) {
            logger.error("getWxacodeun出现异常",e);
            return new ResponseEnvelope(false,"出现异常,生成二维码失败！",msgId);
        }


        /**------生成二维码------*/
        String imgBase64 = "";
        String pathParameters = null;
        try {
            if(Objects.equals(SceneDataSearch.QRCodeType.QRCODE_TYPE_BROWSE,qrCodeType)){
                pathParameters = "?renderId="+ i +"&sceneType=" + sceneType + "&planSourceType=" + planSourceType + "&hasChanged=" + b + "&qrCodeType=" + qrCodeType + "&platformNewCode=pc2b";
                //PC端浏览
                String url = SCENE_SHARE_PC_QRCODE_URL + pathParameters;
                SceneQrCodeResult sceneQrCodeResult = new SceneQrCodeResult();
                sceneQrCodeResult.setQrUrl(url);
                return  new ResponseEnvelope(true,sceneQrCodeResult,msgId);
            }else if(Objects.equals(SceneDataSearch.QRCodeType.QRCODE_TYPE_GENERAL,qrCodeType) || qrCodeType == null){
                pathParameters = "?renderId="+ i +"&sceneType=" + sceneType + "&planSourceType=" + planSourceType + "&hasChanged=" + b + "&qrCodeType=" + qrCodeType + "&platformNewCode=pc2b" ;
                //生成PC端普通二维码
                String url = SCENE_SHARE_H5_QRCODE_URL + pathParameters;
                try {
                    imgBase64 = MatrixToImageWriter.getQRCodeBase64(url,null,null);
                } catch (Exception e) {
                    logger.error("生成PC端二维码时出现异常:",e);
                    return new ResponseEnvelope(false,"出现异常,生成二维码失败！",msgId);
                }

            }else{
                //生成随选网/小程序二维码
                pathParameters = "?i="+i+"&t="+t+"&b="+b+"&s="+s+"&c="+c;
                String access_token = null;
                if(Objects.equals(SceneDataSearch.QRCodeType.QRCODE_TYPE_SELECTDECORATION,qrCodeType)){
                    access_token =  getaccess_token();
                }else{
                    //生成企业小程序二维码
                    try {
                        access_token = getaccess_token(loginUser.getId());
                    } catch (BizException e1){
                        logger.error("获取access_token出现异常：",e1);
                        return new ResponseEnvelope(false, e1.getMessage(),"获取access_token出现异常，生成二维码失败!", msgId);
                    }catch (Exception e) {
                        e.printStackTrace();
                        logger.error("获取access_token出现异常：",e);
                        return new ResponseEnvelope(false, "获取access_token出现异常，生成二维码失败!", msgId);
                    }
                }
                if(StringUtils.isBlank(access_token)) {
                    return new ResponseEnvelope(false, "access_token参数有误", msgId);
                }
                String contentType = "";
                try {

                    //参数设置
                    Map<String, Object> params = new HashMap<>();
                    String scene = pathParameters;
                    params.put("scene", scene);
                    //地址
                    if(Objects.equals(SceneDataSearch.QRCodeType.QRCODE_TYPE_SELECTDECORATION,qrCodeType)){
                        params.put("page", "pages/plan/house-case/house-case");
//                        params.put("page", "pages/web-720/web-720");
                    }else{
                        params.put("page", "pages/index/index");
                    }

                    params.put("width", 430);

                    //路径拼接
                    String url = WX_SMALL_ACODEUN_URL + access_token;

                    //参数转换
                    StringEntity entity = new StringEntity(JSON.toJSONString(params));
                    entity.setContentType("image/png");
                    //获取二维码流
                    CloseableHttpClient  httpClient = HttpClientBuilder.create().build();
                    HttpPost httpPost = new HttpPost(url);

                    httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
                    httpPost.setEntity(entity);
                    HttpResponse responseImg = httpClient.execute(httpPost);
                    inputStream = responseImg.getEntity().getContent();
                    contentType = responseImg.getEntity().getContentType().toString().split(":")[1];
                }catch (Exception e){
                    logger.error("获取小程序二维码出错{}",e);
                    return  new ResponseEnvelope(false,"获取小程序二维码出错",msgId);
                }
                //判断是否获取成功
                if(StringUtils.isBlank(contentType) || !contentType.contains("image")) {
                    BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    StringBuilder responseStrBuilder = new StringBuilder();

                    String inputStr;
                    while ((inputStr = streamReader.readLine()) != null)
                        responseStrBuilder.append(inputStr);

                    JSONObject jsonObject = JSONObject.fromObject(responseStrBuilder.toString());
                    return new ResponseEnvelope(false, jsonObject, "调用微信小程序二维码获取失败", msgId);
                }else{
                    //返回数据
                    imgBase64 = getBase64FromInputStream(inputStream);
                }

            }
        } catch (Exception e) {
            logger.error("getWxacodeun错误",e);
            return new ResponseEnvelope(false,"出现异常,生成二维码失败！",msgId);
        }

        SceneQrCodeResult sceneQrCodeResult = new SceneQrCodeResult();
        sceneQrCodeResult.setText(shareDesc);
        sceneQrCodeResult.setImgBase64(imgBase64);
        sceneQrCodeResult.setTitle(shareTitle);
        return  new ResponseEnvelope(true,sceneQrCodeResult,"获取小程序二维码成功",msgId);

    }


    /**
     * 获取二维码应显示数量
     * 如果用户关联企业有企业小程序，则也显示企业小程序二维码
     * 没有则只显示 网页版二维码和随选网二维码
     * @param request
     * @param response
     * @param msgId
     * @return
     */
    @RequestMapping("getQRCodeTypeCount")
    @ResponseBody
    public Object getQRCodeTypeCount(HttpServletRequest request, HttpServletResponse response,String msgId){
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null){
            return new ResponseEnvelope(false,"登录信息失效，请重新登录!",msgId);
        }
        //二维码的数量
        int count = 2;
        Integer businessCompanyId = loginUser.getBusinessAdministrationId();
        //三度云享家企业只显示随选网二维码
        if(!Objects.equals(SANDU_COMPANY_ID,businessCompanyId)){
            BaseCompanyMiniProgramConfig programConfig = null;
            try {
                programConfig = baseCompanyService.getBaseCompanyMiniProgramConfigByUserId(loginUser.getId());
            } catch (BizException e) {
                logger.warn("查询企业小程序时出现异常:",e);
                count = 2;
            }
            if(programConfig != null) {
                count = 3;
            }
        }
        return new ResponseEnvelope<>(true,count,msgId);
    }

    private String getaccess_token(){
        String access_token = "";
        if(httpClient == null )
            httpClient = HttpClient.getInstance();
        if(cacher == null)
            cacher = CacheManager.getInstance().getCacher();

//        Object access_token_object = cacher.get("SAMLL_ACCESS_TOKEN");
        Object access_token_object = null;
        if(null == access_token_object){
            String access_token_url = ACCESS_TOKEN_URL + "&appid=" + WX_SMALLl_APPID + "&secret=" + WX_SMALL_APPSECRET;
            logger.info("=========access_token_url:" + access_token_url);
            String responseBody = httpClient.sendHttpGet(access_token_url);
            if( StringUtils.isBlank(responseBody) ){
                return null;
            }

            JSONObject jsonObject = JSONObject.fromObject(responseBody);
            try {
                if (jsonObject != null && StringUtils.isNotBlank(jsonObject.getString("access_token"))) {
                    access_token = jsonObject.getString("access_token");
                    // 放到redis缓存中
                    cacher.set("SAMLL_ACCESS_TOKEN", access_token, 7000);
                }
            }catch (Exception e){
                throw new RuntimeException(new Gson().toJson(jsonObject));
            }

        }else{
            access_token = access_token_object.toString();
        }

        return access_token;
    }

    public static String getBase64FromInputStream(InputStream in) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = in.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
                data = swapStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new String(Base64.encodeBase64(data));
    }

    private SceneQrCodeInfo getSceneQrCodeInfo(String copywritingInformation,String shareTitle,String sceneType,String renderId,LoginUser loginUser,Integer qrCodeType){
        SceneQrCodeInfo sceneQrCodeInfo = new SceneQrCodeInfo();
        sceneQrCodeInfo.setCopywritingInformation(copywritingInformation);
        sceneQrCodeInfo.setSceneType(Integer.parseInt(sceneType));
        sceneQrCodeInfo.setRenderId(renderId);
        sceneQrCodeInfo.setUserId(loginUser.getId());
        sceneQrCodeInfo.setSysCode(System.currentTimeMillis()+"");
        sceneQrCodeInfo.setCreator(loginUser.getLoginName());
        sceneQrCodeInfo.setGmtCreate(new Date());
        sceneQrCodeInfo.setModifier(loginUser.getLoginName());
        sceneQrCodeInfo.setGmtModified(new Date());
        sceneQrCodeInfo.setIsDeleted(0);
        sceneQrCodeInfo.setShareTitle(shareTitle);
        sceneQrCodeInfo.setQrCodeType(qrCodeType);
        return sceneQrCodeInfo;
    }

    private boolean checkIsNum(String str){
        boolean bool = false;

        if(StringUtils.isNotBlank(str)){
            try {
                Integer.parseInt(str);
                bool = true;
            }catch (Exception e){
                bool = false;
            }
        }

        return bool;
    }


    /**
     * 将二进制转换成文件保存
     * @param instreams     二进制流
     * @param imgPath       图片的保存路径
     * @param imgName       图片的名称
     * @return
     *      1：保存正常
     *      0：保存失败
     */
    private int saveToImgByInputStream(InputStream instreams,String imgPath,String imgName){
        int stateInt = 1;
        if(instreams != null){
            try {
                File file = new File(imgPath,imgName);                //可以是任何图片格式.jpg,.png等
                FileOutputStream fos = new FileOutputStream(file);
                byte[] b = new byte[1024];
                int nRead = 0;
                while ((nRead = instreams.read(b)) != -1) {
                    fos.write(b, 0, nRead);
                }
                fos.flush();
                fos.close();
            } catch (Exception e) {
                stateInt = 0;
                e.printStackTrace();
            } finally {
            }
        }
        return stateInt;
    }


    private String getaccess_token(Integer userId) throws  BizException{
        if(userId == null){
            throw new BizException("当前用户登录信息失效!");
        }
        //查找用户企业对应企业小程序
        BaseCompanyMiniProgramConfig config = null;
        try {
            config = baseCompanyService.getBaseCompanyMiniProgramConfigByUserId(userId);
        } catch (BizException e) {
            logger.error("生成企业小程序时出现异常:",e);
            throw new BizException(e.getMessage());
        }
        if(config == null){
            throw new BizException("该企业没有企业小程序!");
        }
        if(Utils.isBlank(config.getAppId()) || Utils.isBlank(config.getAppSecret())){
            logger.error("用户企业相关企业小程序数据缺失AppId或AppSecret:" + config.toString());
            throw new BizException("该企业小程序数据缺失!" );
        }
        //得到 token
        String access_token = "";

        Object access_token_object = null;
      /*  access_token_object = cacher.get("SAMLL_ACCESS_TOKEN_" + companyId  );*/
        if(null == access_token_object){
            String access_token_url = ACCESS_TOKEN_URL + "&appid=" + config.getAppId() + "&secret=" + config.getAppSecret();
            logger.info("=========access_token_url:" + access_token_url);
            String responseBody = httpClient.sendHttpGet(access_token_url);
            if( StringUtils.isBlank(responseBody) ){
                return null;
            }

            JSONObject jsonObject = JSONObject.fromObject(responseBody);
            try {
                if (jsonObject != null && StringUtils.isNotBlank(jsonObject.getString("access_token"))) {
                    access_token = jsonObject.getString("access_token");
                // 放到redis缓存中
                    /*cacher.set("SAMLL_ACCESS_TOKEN_" + companyId  , access_token, 7000);*/
            }
            }catch (Exception e){
                throw new RuntimeException(new Gson().toJson(jsonObject));
            }

        }else{
            access_token = access_token_object.toString();
        }

        return access_token;
    }


//    public static void main(String[] args) throws Exception {
//        (String access_token,String page,String scene){
//        String page = "pages/index/index";
//        String scene = "id/"+1+"*userIdId/"+2;
//
//        String access_token =  new ShareController().getaccess_token();
//        new ShareController().createBCode(access_token,page,scene);
//        //获取token
////        String result1 = HttpTool.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+Constant.NATIVE_APP_ID+"&secret="+Constant.NATIVE_APP_SECRET);
////        String access_token = JSONObject.parseObject(result1).getString("access_token");
//        String access_token =  new ShareController().getaccess_token();
//        if(StringUtils.isNotBlank(access_token)) {
//            System.out.println("token为");
//            System.out.println(access_token);
//            Map<String, Object> params = new HashMap<>();
//            params.put("scene", "test");
//            params.put("page", "pages/index/index");
//            params.put("width", 430);
//
//            CloseableHttpClient  httpClient = HttpClientBuilder.create().build();
//
//            String url = wxacodeun_url + access_token;
//            HttpPost httpPost = new HttpPost(url);
//            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
//            StringEntity entity = new StringEntity(JSON.toJSONString(params));
//            entity.setContentType("image/png");
//
//            httpPost.setEntity(entity);
//            HttpResponse response;
//
//            response = httpClient.execute(httpPost);
//            InputStream inputStream = response.getEntity().getContent();
//
//
//            //保存图片
//            new ShareController().saveToImgByInputStream(inputStream,"E:\\data001\\",System.currentTimeMillis()+"1.png");

//            File targetFile = new File("E:\\upload");
//            if(!targetFile.exists()){
//                targetFile.mkdirs();
//            }
//            FileOutputStream out = new FileOutputStream("E:\\upload\\6.png");
//
//            byte[] buffer = new byte[8192];
//            int bytesRead = 0;
//            while((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
//                out.write(buffer, 0, bytesRead);
//            }
//
//            out.flush();
//            out.close();
//        } else {
//            System.out.println("获取access_token错误");
//        }
}

//    public static void main(String[] args) throws Exception{
////        String access_token = new ShareController().getaccess_token();
////        if(StringUtils.isNotBlank(access_token)){
////            try {
////                URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+access_token);
////                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
////                httpURLConnection.setRequestMethod("POST");// 提交模式
////                // conn.setConnectTimeout(10000);//连接超时 单位毫秒
////                // conn.setReadTimeout(2000);//读取超时 单位毫秒
////                // 发送POST请求必须设置如下两行
////                httpURLConnection.setDoOutput(true);
////                httpURLConnection.setDoInput(true);
////                // 获取URLConnection对象对应的输出流
////                PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
////                // 发送请求参数
////                JSONObject paramJson = new JSONObject();
////                paramJson.put("scene", "a=1234567890000000");
////                paramJson.put("page", "pages/index/index");
////                paramJson.put("width", 430);
////                paramJson.put("auto_color", true);
////                *
////                 * line_color生效
////                 * paramJson.put("auto_color", false);
////                 * JSONObject lineColor = new JSONObject();
////                 * lineColor.put("r", 0);
////                 * lineColor.put("g", 0);
////                 * lineColor.put("b", 0);
////                 * paramJson.put("line_color", lineColor);
////                 *
////                printWriter.write(paramJson.toString());
////                // flush输出流的缓冲
////                printWriter.flush();
////                //开始获取数据
////                BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
////                OutputStream os = new FileOutputStream(new File("E:\\data001\\aaa.png"));
////                int len;
////                byte[] arr = new byte[1024];
////                while ((len = bis.read(arr)) != -1)
////                {
////                    os.write(arr, 0, len);
////                    os.flush();
////                }
////                os.close();
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        }else{
////            System.out.println("为空");
////        }
//
//
//        String token = new ShareController().getaccess_token();   // 得到token
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("scene", "a=1234567890000000");  //参数
//        params.put("page", "page/msg_waist/msg_waist"); //位置
//        params.put("width", 430);
//
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//
//        HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+token);  // 接口
//        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
//        String body = new Gson().toJson(params);           //必须是json模式的 post
//        StringEntity entity;
//        entity = new StringEntity(body);
//        entity.setContentType("image/png");
//
//        httpPost.setEntity(entity);
//        HttpResponse response;
//
//        response = httpClient.execute(httpPost);
//        InputStream inputStream = response.getEntity().getContent();
//        String name = "867186032552993.png";
//        saveToImgByInputStream(inputStream,"E:\\data001\\",name);  //保存图片
//    }


//    /**
//     * B接口生成小程序码
//     * @param access_token
//     * @param page
//     * @param scene
//     */
//    public String createBCode(String access_token,String page,String scene){
//        String url = wxacodeun_url + access_token;
//        Map<String,Object> param = new HashMap<>();
//        param.put("page", page);
//        param.put("scene", scene);
//        param.put("width", "100");
//        param.put("auto_color", false);
//        Map<String,Object> line_color = new HashMap<>();
//        line_color.put("r", 0);
//        line_color.put("g", 0);
//        line_color.put("b", 0);
//        param.put("line_color", line_color);
//        JSONObject json = JSONObject.fromObject(param);
//        try {
//            String imageUrl = httpPostWithJSON2(url, json.toString(), "xxx.png");
//            return imageUrl;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    //返回图片地址
//    public String httpPostWithJSON2(String url, String json,String imagePath)
//            throws Exception {
//        String result = null;
//        DefaultHttpClient httpClient = new DefaultHttpClient();
//        HttpPost httpPost = new HttpPost(url);
//        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
//
//        StringEntity se = new StringEntity(json);
//        se.setContentType("application/json");
//        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"UTF-8"));
//        httpPost.setEntity(se);
//        HttpResponse response = httpClient.execute(httpPost);
//        if (response != null) {
//            HttpEntity resEntity = response.getEntity();
//            if (resEntity != null) {
//                InputStream instreams = resEntity.getContent();
//                //上传至资源服务器生成url
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                byte[] bs = new byte[1024];//缓冲数组
//                int len = -1;
//                while ((len = instreams.read(bs)) != -1) {
//                    byteArrayOutputStream.write(bs, 0, len);
//                }
//                byte b[] = byteArrayOutputStream.toByteArray();
//
//                //将byte字节数组上传至资源服务器返回图片地址
//                // ......
//                System.out.println("ddd"+byteArrayOutputStream);
//                FileOutputStream fileOutputStream = new FileOutputStream( new File( "E:\\data001\\cc.png" ) ) ;
//
//                byteArrayOutputStream.writeTo( fileOutputStream ) ;
//
//                byteArrayOutputStream.close();
//                instreams.close();
//                fileOutputStream.flush();
//            }
//        }
//        httpPost.abort();
//        return result;
//    }

//}
