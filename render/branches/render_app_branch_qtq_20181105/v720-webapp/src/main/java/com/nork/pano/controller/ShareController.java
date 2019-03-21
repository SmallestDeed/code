package com.nork.pano.controller;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.nork.common.cache.CacheManager;
import com.nork.common.cache.Cacher;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.pano.share.Sign;
import com.nork.common.util.HttpClient;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.pano.model.constant.SceneTypeConstant;
import com.nork.pano.model.qrcode.QrInfo;
import com.nork.pano.model.qrcode.SceneQrCodeInfo;
import com.nork.pano.model.qrcode.SceneQrCodeResult;
import com.nork.pano.service.SceneQrCodeInfoService;
import com.nork.system.service.SysUserService;
import com.sandu.common.LoginContext;
import net.sf.json.JSONObject;
import org.apache.commons.net.util.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SceneQrCodeInfoService sceneQrCodeInfoService;

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
            if (jsonObject != null && StringUtils.isNotBlank(jsonObject.getString("access_token"))) {
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
     * @return
     * @date 2018/10/8 0008 10:00
     */
    @RequestMapping("getWxacodeun")
    @ResponseBody
    public Object getWxacodeun(HttpServletRequest request, HttpServletResponse response,
                               String shareDesc,String sceneType,String renderId,String planSourceType,String msgId) throws IOException{
        planSourceType = StringUtils.isBlank(planSourceType) ? "2" : planSourceType;
        logger.error("参数:shareDesc="+shareDesc+"|sceneType="+sceneType+"|renderId="+renderId+"|planSourceType="+planSourceType);

        /**------参数获取与校验------*/
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
//        LoginUser loginUser = new LoginUser();
//        loginUser.setId(1479);
//        loginUser.setLoginName("chenqiang");
        if(null == loginUser){
            return new ResponseEnvelope(false,"请重新登录",msgId);
        }
        if(!checkIsNum(sceneType)) {
            return  new ResponseEnvelope(false, "场景类型参数有误", msgId);
        }
        if(!checkIsNum(renderId)) {
            return new ResponseEnvelope(false, "渲染信息参数有误", msgId);
        }
        if(!checkIsNum(planSourceType)) {
            return  new ResponseEnvelope(false, "方案类型信息参数有误", msgId);
        }
        if(StringUtils.isNotBlank(shareDesc) && shareDesc.length() > 30) {
            return new ResponseEnvelope(false, "分享文案长度过长", msgId);
        }
        String access_token =  getaccess_token();
        if(StringUtils.isBlank(access_token)) {
            return new ResponseEnvelope(false, "access_token参数有误", msgId);
        }


        /**------声明变量------*/
        InputStream inputStream = null;
        String defaultInformation = "2".equals(planSourceType) ? WX_SMALL_INFORMATION_R : WX_SMALL_INFORMATION_D;         //默认文案
        Integer i = 0;
        Integer t = 0;
        Integer b = 0;
        Integer s = 0;
        String companyName = sysUserService.getCompanyNameByUserId(loginUser.getId());          //获取用户企业名称
        defaultInformation = defaultInformation.replace("企业名称",companyName);          //替换真正的企业名称


        /**------二维码信息保存------*/
        i = Integer.parseInt(renderId);
        t = Integer.parseInt(sceneType);
        s = Integer.parseInt(planSourceType)+1;//U3D传递参数为0,1，与定义类型不符，所以加1
        boolean bool = true;
        //文案信息为空、如果类型为3/4/5,需要查看是否存在已修改文案的二维码信息
        if(StringUtils.isBlank(shareDesc)
                && (t.intValue() == SceneTypeConstant.GROUP_SCENE
                || t.intValue() == SceneTypeConstant.UNION_STORE_SINGLE_SCENE
                || t.intValue() == SceneTypeConstant.STORE_RELEASE_SCENE)){

            //查询是否存在已分享的二维码信息
            SceneQrCodeInfo search = new SceneQrCodeInfo();
            search.setRenderId(renderId);
            search.setSceneType(t);
            SceneQrCodeInfo sceneQrCodeInfo = sceneQrCodeInfoService.getSceneQrCodeInfo(search);
            if(null != sceneQrCodeInfo){
                bool = false;
                i = sceneQrCodeInfo.getId();
                b = 1;
                defaultInformation = sceneQrCodeInfo.getCopywritingInformation();
            }

        }
        //默认文案是否改变
        if(bool && StringUtils.isNotBlank(shareDesc) && !shareDesc.equals(defaultInformation)){
            SceneQrCodeInfo sceneQrCodeInfo = getSceneQrCodeInfo(shareDesc,sceneType,renderId,loginUser);
            i = sceneQrCodeInfoService.insertSelective(sceneQrCodeInfo);
            b = 1;
            //返回文案信息改变
            defaultInformation = shareDesc;
        }


        /**------生成二维码------*/
        String contentType = "";
        try {

            //参数设置
            Map<String, Object> params = new HashMap<>();
            String scene = "?i="+i+"&t="+t+"&b="+b+"&s="+s;
            params.put("scene", scene);
//            params.put("page", "pages/home/home");
            params.put("page", "pages/plan/house-case/house-case");
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

//            saveToImgByInputStream(inputStream,"E:\\data001\\",System.currentTimeMillis()+".png");

        }catch (Exception e){
            logger.error("获取小程序二维码出错{}",e);
            return  new ResponseEnvelope(false,"获取小程序二维码出错",msgId);
        }


        //判断是否获取成功
        if(StringUtils.isBlank(contentType) || !contentType.contains("image"))
            return  new ResponseEnvelope(false,inputStream,"调用微信小程序二维码获取失败",msgId);

        //返回数据
        if(StringUtils.isBlank(msgId)){

            String imgBase64 = this.getBase64FromInputStream(inputStream);
            SceneQrCodeResult sceneQrCodeResult = new SceneQrCodeResult();
            sceneQrCodeResult.setText(defaultInformation);
            sceneQrCodeResult.setImgBase64(imgBase64);
            return  new ResponseEnvelope(true,sceneQrCodeResult,"获取小程序二维码成功",msgId);

        }else{

            response.setContentType(contentType);
            response.setHeader("MSGID",msgId);
            response.setHeader("SHAREDESC",URLEncoder.encode(defaultInformation,"UTF-8"));
            OutputStream outputStream = response.getOutputStream();
            byte[] data = new byte[1024];
            int nRead = 0;
            while ((nRead = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, nRead);
            }
            outputStream.flush();
            outputStream.close();
            return  new ResponseEnvelope(true,"获取小程序二维码成功",msgId);

        }

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

    private SceneQrCodeInfo getSceneQrCodeInfo(String copywritingInformation,String sceneType,String renderId,LoginUser loginUser){
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


    public static void main(String[] args) {
        String access_token =  new ShareController().getaccess_token();
        if(StringUtils.isNotBlank(access_token)){

            System.out.println("access_token=" + access_token);

            InputStream inputStream = null;
            try {

                //参数设置
                QrInfo qrInfo = new QrInfo(123456789,8,1);
                String scene = new Gson().toJson(qrInfo);
//                String scene = "i=123456789,t=8,b=1";
                System.out.println("scene=" + scene);
                Map<String, Object> params = new HashMap<>();
                params.put("scene", scene);
                params.put("page", "pages/home/home");
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

//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                byte[] bs = new byte[1024];//缓冲数组
//                int len = -1;
//                while ((len = inputStream.read(bs)) != -1) {
//                    byteArrayOutputStream.write(bs, 0, len);
//                }
//                System.out.println("byteArrayOutputStream="+byteArrayOutputStream);

//                FileOutputStream fileOutputStream = new FileOutputStream( new File( "E:\\data001\\cc.png" ) ) ;
//
//                byteArrayOutputStream.writeTo(fileOutputStream);
//                byteArrayOutputStream.close();
//                inputStream.close();
//                fileOutputStream.flush();
                //保存图片
              new ShareController().saveToImgByInputStream(inputStream,"E:\\data001\\",System.currentTimeMillis()+".png");

            }catch (Exception e){
                System.out.println("获取小程序二维码出错"+e.getMessage());
                e.printStackTrace();
            }

        }
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
////                /**
////                 * line_color生效
////                 * paramJson.put("auto_color", false);
////                 * JSONObject lineColor = new JSONObject();
////                 * lineColor.put("r", 0);
////                 * lineColor.put("g", 0);
////                 * lineColor.put("b", 0);
////                 * paramJson.put("line_color", lineColor);
////                 * */
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
