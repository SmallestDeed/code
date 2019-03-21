package com.sandu.user.dao;

import com.sandu.user.model.UserPO;
import com.sandu.user.model.view.LoginUserVO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDaoMapper {

    UserPO getUserByLoginUser(LoginUserVO loginUser);

}
