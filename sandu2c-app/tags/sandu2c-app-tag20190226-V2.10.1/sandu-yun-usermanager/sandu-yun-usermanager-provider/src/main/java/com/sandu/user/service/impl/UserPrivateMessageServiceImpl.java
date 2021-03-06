package com.sandu.user.service.impl;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in AM 10:08 2018/5/14 0014
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.common.util.StringUtils;
import com.sandu.common.util.Utils;
import com.sandu.product.model.BaseCompany;
import com.sandu.user.dao.UserPrivateMessageMapper;
import com.sandu.user.model.CompanyShopVo;
import com.sandu.user.model.UserPrivateMessage;
import com.sandu.user.model.UserRoleContants;
import com.sandu.user.model.input.UserPrivateMessageAdd;
import com.sandu.user.model.view.UserPrivateMessageVo;
import com.sandu.user.service.SysUserService;
import com.sandu.user.service.UserPrivateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author weisheng
 * @Title: 用户留言
 * @Package
 * @Description:
 * @date 2018/5/14 0014AM 10:08
 */
@Service("userPrivateMessageService")
public class UserPrivateMessageServiceImpl implements UserPrivateMessageService {

    private static final String DEFAULT_MOBILE = "";

    @Autowired
    private UserPrivateMessageMapper userPrivateMessageMapper;
    @Autowired
    private SysUserService sysUserService;


    @Override
    public List<UserPrivateMessageVo> getUserPrivateMessageList(UserPrivateMessageAdd userPrivateMessageAdd) {
        List<UserPrivateMessageVo> userPrivateMessageVos = userPrivateMessageMapper.selectUserPrivateMessageList(userPrivateMessageAdd);
        if (userPrivateMessageVos != null && userPrivateMessageVos.size() > 0) {
            for (UserPrivateMessageVo userPrivateMessageVo : userPrivateMessageVos) {
                String userName = userPrivateMessageVo.getFriendName();
                String nickName = userPrivateMessageVo.getNickName();
                if(StringUtils.isEmpty(userName) && StringUtils.isNotBlank(nickName) && Utils.isMobile(nickName)){
                    userPrivateMessageVo.setFriendName(nickName.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
                }

                if(StringUtils.isEmpty(userName) && StringUtils.isNotBlank(nickName) && !Utils.isMobile(nickName)){
                    userPrivateMessageVo.setFriendName(nickName);
                }

                if(StringUtils.isNotBlank(userName) && Utils.isMobile(userName)){
                    userPrivateMessageVo.setFriendName(userName.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
                }


                if(StringUtils.isEmpty(userPrivateMessageVo.getFriendPicPath())) {

                    if(userPrivateMessageVo.getSex()==null || userPrivateMessageVo.getSex()== 0 || userPrivateMessageVo.getSex() == 1){
                        String senderPic = sysUserService.getUserDefaultPic(1);
                        userPrivateMessageVo.setFriendPicPath(senderPic);
                    }else if (userPrivateMessageVo.getSex()!=null  && userPrivateMessageVo.getSex() == 2){
                        String senderPic = sysUserService.getUserDefaultPic(2);
                        userPrivateMessageVo.setFriendPicPath(senderPic);
                    }

                }


            }
        }


       /* //根据不同的入口和不同的角色显示的信息不同
        if (userPrivateMessageVos != null && userPrivateMessageVos.size() > 0) {
            if (null != userPrivateMessageAdd.getType() && userPrivateMessageAdd.getType() == 1) {
                for (UserPrivateMessageVo userPrivateMessageVo : userPrivateMessageVos) {

                    if (userPrivateMessageVo.getUserType() != null && userPrivateMessageVo.getUserType() != 0) {

                        switch (userPrivateMessageVo.getUserType().intValue()) {
                            //装修公司,门店,设计公司采用公司名称和图片
                            case UserRoleContants.DECORATE_COMPANY:
                            case UserRoleContants.DEALERS:
                            case UserRoleContants.DESIGNER_COMPANY:
                                BaseCompany companyInfo = sysUserService.getCompanyInfo(userPrivateMessageVo.getFriendId());
                                if(companyInfo!=null){
                                    userPrivateMessageVo.setFriendName(companyInfo.getCompanyName());
                                    userPrivateMessageVo.setFriendPicPath(companyInfo.getCompanyLogoPicPath());
                                }

                                break;
                            default:
                                ;
                        }

                    }
                }


            } else if (null != userPrivateMessageAdd.getType() && userPrivateMessageAdd.getType() == 2) {
                for (UserPrivateMessageVo userPrivateMessageVo : userPrivateMessageVos) {
                    if (userPrivateMessageVo.getUserType() != null && userPrivateMessageVo.getUserType() != 0) {
                        switch (userPrivateMessageVo.getUserType().intValue()) {
                            //装修公司,工长,门店,设计公司采用店铺名称和图片
                            case UserRoleContants.DECORATE_COMPANY:
                            case UserRoleContants.FOREMAN:
                            case UserRoleContants.DEALERS:
                            case UserRoleContants.DESIGNER_COMPANY:
                                List<CompanyShopVo> companyShopList = sysUserService.getCompanyShop(userPrivateMessageVo.getFriendId());
                                CompanyShopVo companyShopVo = null;
                                if(companyShopList!=null && companyShopList.size()>0){
                                    companyShopVo = companyShopList.get(0);
                                }

                                if(companyShopVo!=null){
                                    userPrivateMessageVo.setFriendName(companyShopVo.getCompanyShopName());
                                    userPrivateMessageVo.setFriendPicPath(companyShopVo.getCompanyShopPicPath());
                                }

                                break;
                            default:
                                ;
                        }
                    }


                }
            }

        }
*/

        return userPrivateMessageVos;
    }

    @Override
    public List<UserPrivateMessageVo> getUserPrivateMessageInfoList(UserPrivateMessageAdd userPrivateMessageAdd) {
        return userPrivateMessageMapper.selectUserPrivateMessageInfoList(userPrivateMessageAdd);
    }

    @Override
    public Integer delUserPrivateMessage(UserPrivateMessageAdd userPrivateMessageAdd) {
        return userPrivateMessageMapper.delUserPrivateMessage(userPrivateMessageAdd);
    }

    @Override
    public Integer addUserPrivateMessage(UserPrivateMessageAdd userPrivateMessageAdd) {
        UserPrivateMessage userPrivateMessage = new UserPrivateMessage();
        userPrivateMessage.setUserId(userPrivateMessageAdd.getUserId());
        userPrivateMessage.setFriendId(userPrivateMessageAdd.getFriendId());
        userPrivateMessage.setIsDeleted(0);
        userPrivateMessage.setMessageContent(userPrivateMessageAdd.getMessageContent());
        userPrivateMessage.setMessageType(userPrivateMessageAdd.getMessageType());
        userPrivateMessage.setStatus(new Integer(1).byteValue());
        userPrivateMessage.setReceiverId(userPrivateMessageAdd.getFriendId());
        userPrivateMessage.setSenderId(userPrivateMessageAdd.getUserId());
        userPrivateMessage.setSendTime(new Date());
        int count = userPrivateMessageMapper.insertSelective(userPrivateMessage);
        if (count > 0) {
            UserPrivateMessage userPrivateMessage2 = new UserPrivateMessage();
            userPrivateMessage2.setUserId(userPrivateMessageAdd.getFriendId());
            userPrivateMessage2.setFriendId(userPrivateMessageAdd.getUserId());
            userPrivateMessage2.setIsDeleted(0);
            userPrivateMessage2.setMessageContent(userPrivateMessageAdd.getMessageContent());
            userPrivateMessage2.setMessageType(userPrivateMessageAdd.getMessageType());
            userPrivateMessage2.setStatus(new Integer(1).byteValue());
            userPrivateMessage2.setReceiverId(userPrivateMessageAdd.getFriendId());
            userPrivateMessage2.setSenderId(userPrivateMessageAdd.getUserId());
            userPrivateMessage2.setSendTime(new Date());
            int count2 = userPrivateMessageMapper.insertSelective(userPrivateMessage2);
            if (count2 == 0) {
                return 0;
            }
        } else {
            return 0;
        }
        return userPrivateMessage.getId();
    }
}
