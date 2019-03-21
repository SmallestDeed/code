package com.sandu.wx.handler;

import com.sandu.user.service.UserInviteService;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class UnsubscribeHandler extends AbstractHandler {

    @Autowired
    private UserInviteService userInviteService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        String openId = wxMessage.getFromUser();
        this.logger.info("取消关注用户 OPENID: " + openId);
        // TODO 取消关注状态更新邀请记录
        int inviteId = userInviteService.delUserInviteByRemark(openId);
        this.logger.info("取消关注状态更新邀请记录: " + inviteId);

        return null;
    }

}
