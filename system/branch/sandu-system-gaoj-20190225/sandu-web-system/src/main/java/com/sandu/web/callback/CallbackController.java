package com.sandu.web.callback;

import com.sandu.api.callback.service.CallbackService;
import com.sandu.api.callback.toutiao.TouTiaoReportClick;
import com.sandu.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-system
 *
 * @author A.ddy (yocome@gmail.com)
 * @datetime 10/23/2018 2:52 PM
 */
@RestController
@Slf4j
@RequestMapping("/v1/callback")
public class CallbackController extends BaseController {

    @Autowired
    private CallbackService callbackService;


    /**
     * 头条广告服务器上报客户点击数据
     *
     * @return
     */
    @GetMapping(value = "/toutiao/report/click")
    public String reportTouTiaoClick(TouTiaoReportClick click) {
        log.debug("TouTiao Report: {}", click);

        int result = callbackService.recordTouTiaoAdClick(click);
        if (result > 0) {
            return "success";
        }

        return "failure";
    }


}
