package com.sandu.wx.handler;

import com.sandu.common.util.GsonUtil;
import com.sandu.common.util.StringUtils;
import com.sandu.common.util.Utils;
import com.sandu.user.model.UserInvite;
import com.sandu.user.service.UserInviteService;
import com.sandu.wx.builder.TextBuilder;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author weis
 */
@Component
public class SubscribeHandler extends AbstractHandler {

    @Autowired
    private UserInviteService userInviteService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) throws WxErrorException {

        this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

        // 获取微信用户基本信息
        WxMpUser userWxInfo = weixinService.getUserService()
            .userInfo(wxMessage.getFromUser(), null);



        if (userWxInfo != null) {
            // TODO 可以添加关注用户到本地
        }

        WxMpXmlOutMessage responseResult = null;
        try {
            responseResult = handleSpecial(wxMessage);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        if (responseResult != null) {
            return responseResult;
        }

        try {
            return new TextBuilder().build("感谢关注", wxMessage, weixinService);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 处理特殊请求，比如如果是扫带参数二维 码进来的，可以做相应处理
     */
    private WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage)
        throws Exception {
        //TODO
        //获取二维码参数,并保存邀请信息到数据库
        String qrsceneStr = wxMessage.getEventKey().replaceAll("qrscene_", "");
        if(StringUtils.isNotBlank(qrsceneStr)){
            List<String> strList = Utils.getListFromStr(qrsceneStr, ",");
           if(null != strList && strList.size() > 0 ){
               int inviteId = userInviteService.saveWXUserInviteInfo(wxMessage.getFromUser(),strList);
               if(inviteId ==0){
                   this.logger.error("保存用户信息失败:"+ GsonUtil.bean2Json(wxMessage)+"----------"+GsonUtil.bean2Json(strList));
               }
           }

        }
        return null;
    }

}
