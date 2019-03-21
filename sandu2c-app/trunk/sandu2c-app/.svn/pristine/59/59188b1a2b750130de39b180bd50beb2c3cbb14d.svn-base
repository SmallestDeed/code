package com.sandu.user.service;

import com.sandu.common.model.ResponseBo;
import com.sandu.user.model.UserPO;
import com.sandu.user.model.UserSO;
import com.sandu.user.model.view.LoginUserVO;

public interface IUserService {

    LoginUserVO login(LoginUserVO loginUser);

    UserPO getUserByAccount(LoginUserVO loginUser);
    
    /*发送短信验证码*/
    Boolean sendMessage(String mobile, String type);
    
    /*用户找回或修改密码*/
    ResponseBo updatePassword(String mobile, String newPassword, String code);

    ResponseBo addUser(String mobile, String password, String code,UserSO userSo, Integer integer);
}
