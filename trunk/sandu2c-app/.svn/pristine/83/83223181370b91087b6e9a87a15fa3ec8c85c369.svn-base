package com.sandu.web.user;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 5:33 2018/5/29 0029
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nork.common.model.LoginUser;
import com.sandu.base.QRCode;
import com.sandu.common.LoginContext;
import com.sandu.common.file.util.file.FilePathUtil;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.StringUtils;
import com.sandu.constant.Punctuation;
import com.sandu.pay.alipay.util.WXChatRequest;
import com.sandu.pay.alipay.util.httpClient.AccessTokenSuccess;
import com.sandu.user.model.SysUser;
import com.sandu.user.model.UserInvite;
import com.sandu.user.model.input.UserInviteAdd;
import com.sandu.user.model.view.UserInviteInfoVo;
import com.sandu.user.model.view.UserInviteTopListVo;
import com.sandu.user.service.SysUserService;
import com.sandu.user.service.UserInviteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author weisheng
 * @Title: 用户邀请
 * @Package
 * @Description:
 * @date 2018/5/29 0029PM 5:33
 */
@Api(tags = "UserInviteController", description = "用户分享")
@Slf4j
@RestController
@RequestMapping("/v1/union/invite")
public class UserInviteController {

    @Autowired
    private UserInviteService userInviteService;
    @Autowired
    private SysUserService sysUserService;

    private static Gson gson = new Gson();

    private static  final  String SHARE_TYPE_REGEX ="^(1|2)$";

    private static  final  int DEFAULT_DESHARE_TYPE =0;

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


    @InitBinder
    public void initBinder(WebDataBinder binder) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, "inviteTime", new CustomDateEditor(
                dateFormat, true));
    }

    /**
     * @Title: 用户发起分享XZ微信小程序
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/4/27 0027PM 6:02
     */
    @RequestMapping(value = "/sharexzchat", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "用户发起分享XZ微信小程序", response = ResponseEnvelope.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shareType", value = "分享类型:'1:微信朋友圈,2:微信好友,3:QQ好友,4:QQ空间,5:微博'", required = true, paramType = "query", dataType = "int"),
    })
    public ResponseEnvelope getUserPrivateMessageList(@RequestParam(value = "shareType", required = false) Integer shareType, HttpServletRequest request) {
       LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
       if (loginUser == null) {
            return new ResponseEnvelope(false, "请先登录");
        }

        String sceneStr ;
        /*如果分享标识为1,分享到朋友圈.为2,分享到微信好友,其他为主动扫码*/
        if( null != shareType && 0 != shareType && Pattern.compile(SHARE_TYPE_REGEX).matcher(shareType+"").find()){
             sceneStr = loginUser.getId() + "," + shareType+","+System.currentTimeMillis();
        }else {
             sceneStr = loginUser.getId()+","+DEFAULT_DESHARE_TYPE+","+System.currentTimeMillis();
        }
        log.info("生成微信分享参数:"+sceneStr);

        //获取微信accessToken
        String accessTokenJson = WXChatRequest.httpsRequest(accessTokenUrl, "GET", null);
        String accessToken = null;
        if (accessTokenJson.contains("access_token")) {
            Type type = new TypeToken<AccessTokenSuccess>() {
            }.getType();
            AccessTokenSuccess accessTokenSuccess = gson.fromJson(accessTokenJson, type);
            accessToken = accessTokenSuccess.getAccess_token();
            log.info("获取微信accessToken:"+accessToken);
        } else {
            return new ResponseEnvelope(false, "获取微信accessToken失败", accessTokenJson);
        }
        //生成二维码保存路径
        String absolutePath = FilePathUtil.absolutePath(storagePath, "xzmini", "shareSign", "image", LocalDateTime.now(), FilePathUtil.filename( LocalDateTime.now())+".png");
        log.info("二维码图片路径:"+absolutePath);
        //封装微信图片参数
        QRCode qrCode = new QRCode();
        qrCode.setQRUrl(QRCodeUrl);
        qrCode.setScene(sceneStr);
        qrCode.setPage(page);
        qrCode.setWidth(Integer.parseInt(width));
        qrCode.setAutoColor(Boolean.parseBoolean(autoColor));
        qrCode.setLineColor(lineColor);
        qrCode.setIsHyaline(Boolean.parseBoolean(isHyaline));
        log.info("封装微信图片参数:"+gson.toJson(qrCode));
        //调用微信API获取二维码
        String returnPath;
        try {
            String miniqrQrPicPath = WXChatRequest.getminiqrQr(accessToken, absolutePath, qrCode);
            String relativePath = FilePathUtil.relativePath(storagePath, absolutePath);
            if(!relativePath.startsWith(Punctuation.SLASH)){
                returnPath = Punctuation.SLASH+relativePath;
            }else {
                returnPath = relativePath;
            }
            log.info("微信二维码地址:"+returnPath);
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
     * @Title: 邀请人注册后回调
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/4/27 0027PM 6:02
     */
    @RequestMapping(value = "/invitecallback", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "邀请人注册后回调", response = ResponseEnvelope.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shareType", value = "分享类型:'1:微信朋友圈,2:微信好友,3:QQ好友,4:QQ空间,5:微博'", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "shareSign", value = "分享标识'", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "inviteId", value = "邀请者ID'", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "inviteTime", value = "邀请时间'", required = true, paramType = "query", dataType = "Date"),
    })
    public ResponseEnvelope inviteCallBack(
            @RequestBody UserInviteAdd userInviteAdd,
            HttpServletRequest request) {
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            return new ResponseEnvelope(false, "请先登录");
        }
        //获取当前用户注册时间
        SysUser sysUser = sysUserService.get(loginUser.getId());
        if(null ==sysUser){
            return new ResponseEnvelope(false, "token失效");
        }

        //判断该用户是否被邀请注册过
        UserInvite userInvite = userInviteService.getUserInviteInfoByFid(loginUser.getId());
        if (userInvite != null) {
            return new ResponseEnvelope(false, "该用户已经被邀请注册");
        }


        userInviteAdd.setRegisterTime(sysUser.getGmtCreate());
        userInviteAdd.setFid(loginUser.getId());


        if (StringUtils.isEmpty(userInviteAdd.getShareSign())) {
            return new ResponseEnvelope(false, "缺失参数shareSign");
        }
        if (userInviteAdd.getShareType() == null || userInviteAdd.getShareType() == 0) {
            return new ResponseEnvelope(false, "缺失参数shareType");
        }
        if (userInviteAdd.getInviteTime() == null) {
            return new ResponseEnvelope(false, "缺失参数inviteTime");
        }
        if (userInviteAdd.getInviteId() == null) {
            return new ResponseEnvelope(false, "缺失参数inviteId");
        }


        Integer keyId = userInviteService.saveUserInviteInfo(userInviteAdd);
        if (keyId == null || keyId == 0) {
            return new ResponseEnvelope(false, "邀请失败");
        }

        return new ResponseEnvelope(true, "", keyId);

    }


    /**
     * @Title: 获取邀请排行榜信息
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/4/27 0027PM 6:02
     */
    @RequestMapping(value = "/invitetoplist", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取邀请排行榜信息", response = ResponseEnvelope.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "curPage", value = "当前页码", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页多少条", required = false, paramType = "query", dataType = "int")
    })
    public ResponseEnvelope getInviteTopList(@ModelAttribute UserInviteAdd userInviteAdd, HttpServletRequest request) {
        List<UserInviteTopListVo> userInviteVoList = null;
        int total = userInviteService.getAllInviteCount();
        if (total > 0) {
            userInviteVoList = userInviteService.getAllInviteList(userInviteAdd);
        }

        if (userInviteVoList == null || userInviteVoList.size() == 0) {
            return new ResponseEnvelope(false, "没有获取到排行榜信息");
        }

        return new ResponseEnvelope(true, "", userInviteVoList,total);

    }


    /**
     * @Title: 获取被邀请人信息
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/4/27 0027PM 6:02
     */
    @RequestMapping(value = "/userinvitelist", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取被邀请人信息", response = ResponseEnvelope.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "curPage", value = "当前页码", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页多少条", required = false, paramType = "query", dataType = "int")
    })
    public ResponseEnvelope getUserInviteList(@ModelAttribute UserInviteAdd userInviteAdd,HttpServletRequest request) {
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            return new ResponseEnvelope(false, "请先登录");
        }
        userInviteAdd.setInviteId(loginUser.getId());
        List<UserInviteInfoVo> userInviteInfoVoList = null;
        int total = userInviteService.getUserInviteInfoCountByInviteId(userInviteAdd);
        if(total>0){
            userInviteInfoVoList = userInviteService.getUserInviteInfoByInviteId(userInviteAdd);
        }

        if (userInviteInfoVoList == null || userInviteInfoVoList.size() == 0) {
            return new ResponseEnvelope(false, "您暂时还没有邀请哦");
        }

        return new ResponseEnvelope(true, "", userInviteInfoVoList,total);

    }


}
