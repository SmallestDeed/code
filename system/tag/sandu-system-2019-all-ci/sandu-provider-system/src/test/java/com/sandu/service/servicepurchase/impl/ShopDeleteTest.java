package com.sandu.service.servicepurchase.impl;

import com.sandu.SampleProvider;
import com.sandu.api.notify.wx.TemplateMsgService;
import com.sandu.api.shop.service.biz.ShopBizService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author WangHaiLin
 * @date 2018/8/31  13:38
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SampleProvider.class)
public class ShopDeleteTest {
    @Autowired
    private ShopBizService shopBizService;

    @Autowired
    private TemplateMsgService templateMsgService;

    @Test
    public void testDeleteShop(){
        int whl = shopBizService.delUsersShop(11604L, "WHL");
        System.out.println(whl);
    }

    @Test
    public void testMsg(){
        templateMsgService.sendHandlerApplyHouseMsg(3502);
    }
}
