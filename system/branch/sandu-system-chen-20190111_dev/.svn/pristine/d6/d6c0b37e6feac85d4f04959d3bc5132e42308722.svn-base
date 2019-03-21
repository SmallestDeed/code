package com.sandu.web;

import com.google.common.collect.Lists;
import com.sandu.WebApplication;
import com.sandu.api.callback.toutiao.TouTiaoReportClick;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-system
 *
 * @author A.ddy (yocome@gmail.com)
 * @datetime 10/24/2018 3:22 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WebApplication.class)
@AutoConfigureMockMvc
public class CallbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRecordTouTiaoAdClick() throws Exception {
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

        mockMvc.perform(get("/v1/callback/toutiao/report/click")
                .contentType(MediaType.APPLICATION_JSON_UTF8).params(beanToMultiValueMap(click)))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));
    }


    private static MultiValueMap<String, String> beanToMultiValueMap(Object obj) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        if (obj != null) {
            BeanMap beanMap = BeanMap.create(obj);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", Lists.newArrayList(String.valueOf(beanMap.get(key))));
            }
        }

        return map;
    }


}
