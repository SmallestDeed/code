package com.sandu.service.springFestivalActivity.impl;

import com.sandu.api.springFestivalActivity.output.UserSignInRecordVo;
import com.sandu.api.springFestivalActivity.service.WxUserSigninService;
import com.sandu.service.springFestivalActivity.dao.WxUserSigninMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName: WxUserSignInServiceImplTest
 * @Auther: gaoj
 * @Date: 2019/1/29 16:03
 * @Description:
 * @Version 1.0
 */
public class WxUserSignInServiceImplTest {

    private WxUserSigninService wxUserSigninService = new WxUserSigninServiceImpl();

    @Test
    public void testGetUserSignInRecordList() {
        List<UserSignInRecordVo> userSignInRecordList = wxUserSigninService.getUserSignInRecordList(5, 32829);
        System.out.println(userSignInRecordList.size());
    }
}
