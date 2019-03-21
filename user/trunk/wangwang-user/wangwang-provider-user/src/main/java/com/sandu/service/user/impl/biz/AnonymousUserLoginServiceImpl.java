package com.sandu.service.user.impl.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.base.model.BasePlatform;
import com.sandu.api.base.service.BasePlatformService;
import com.sandu.api.user.input.UserLogin;
import com.sandu.api.user.model.bo.UserInfoBO;
import com.sandu.api.user.output.AnonymousUserLoginVO;
import com.sandu.api.user.service.biz.UserLoginService;
import com.sandu.common.exception.BizException;
import com.sandu.common.exception.ExceptionCodes;

@Service("anonymousUserLoginService")
public class AnonymousUserLoginServiceImpl extends BaseUserLoginServiceImpl implements UserLoginService {

    @Autowired
    private BasePlatformService basePlatformService;

    public Object login(UserLogin userLogin) {
        BasePlatform platform = basePlatformService.queryByPlatformCode(userLogin.getPlatformCode());
        if (platform == null) {
            throw new BizException(ExceptionCodes.CODE_10010001, "平台类型不正确");
        }
        UserInfoBO userInfoBO = new UserInfoBO();
        userInfoBO.setId(-1L);
        
        //设置用户权限(菜单、功能、字段).
        setUserPermissions(userInfoBO, platform.getId(),null);
        
        //生成菜单树
        buildMenuTree(userInfoBO);
        
        AnonymousUserLoginVO vo = new AnonymousUserLoginVO();
        vo.setMenuTree(userInfoBO.getMenuTree());
        return vo;
    }


	@Override
	public Object cacheUserInfoToRedis(UserInfoBO userInfoBO) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void checkEquipment(UserInfoBO userInfoBO, UserLogin userLogin) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void saveLoginLog(UserInfoBO userInfoBO, UserLogin userLogin) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setServerConfigInfo(UserInfoBO userInfoBO, UserLogin userLogin) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void createUserJwtToken(UserInfoBO userInfoBO) {
		// TODO Auto-generated method stub
		
	}

}
