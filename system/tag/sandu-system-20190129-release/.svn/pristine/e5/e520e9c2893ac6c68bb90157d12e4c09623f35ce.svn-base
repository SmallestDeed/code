package com.sandu.service.callback;

import com.sandu.api.callback.service.CallbackService;
import com.sandu.api.callback.toutiao.TouTiaoReportClick;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-system
 *
 * @author A.ddy (yocome@gmail.com)
 * @datetime 10/23/2018 9:14 PM
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class CallbackServiceTest {

    @Autowired
    private CallbackService callbackService;

    @Test
    public void testRecordAdClick(){
        TouTiaoReportClick click = TouTiaoReportClick.builder()
                .adid("1615447982177316")
                .cid("1615447982177332")
                .csite("1")
                .ctype("4")
                .imei("ded876c398e039f8b46cf7a014f68328")
                .mac("0f607264fc6318a92b9e13c65db7cd3c")
                .ip("192.168.1.2")
                .os(1)
                .ua("News+6.9.2+rv%3A6.9.2.12+%28iPhone%3B+iOS+11.4%3B+zh_CN%29+Cronet")
                .timestamp(1540611491000L)
                .idfa("1CEFCAFA-CF58-4F1A-8D44-1A554753D186")
                .udid("56099122dd24efd753ad9b6ab16ead02")
                .openudid("86bb867ec60c9ba89bf53b4dce9e0974a1157f31")

                .androidid("86bb867ec60c9ba89bf53b4dce9e0974a1157f31")
                .uuid("99001026982201")
                .convert_id("1615447891148856")
                .callback("CKT4hdncp-8CELT4hdncp-8CGLLjrM39AiDrqcif2gEouIDSrdyn7wIwDDgBQiEyMDE4MTAyNzExMzgwMzAxMDAwODA1OTAzMzI0MzhCNjBIAVAA")
                .build();

        int result = callbackService.recordTouTiaoAdClick(click);

        Assert.state(result == 1, "Don't record ad click!");
    }


}
