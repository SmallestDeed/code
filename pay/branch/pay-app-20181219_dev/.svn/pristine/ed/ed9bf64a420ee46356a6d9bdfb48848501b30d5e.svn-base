package com.sandu.pay.controller;

import com.sandu.common.util.UtilDate;
import com.sandu.pay.order.metadata.PayState;
import com.sandu.pay.order.model.PayModelConfig;
import com.sandu.pay.order.model.PayOrder;
import com.sandu.pay.order.model.ResultMessage;
import com.sandu.pay.order.service.PayModelConfigService;
import com.sandu.pay.order.service.PayModelGroupRefService;
import com.sandu.pay.order.service.PayOrderService;
import com.sandu.pay.order.vo.PayModelConfigVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 监控controller
 * @Author yzw
 * @Date 2018/1/24 10:00
 */
@Controller
@RequestMapping("/running")
public class RunningController {

    private static Logger logger = LogManager.getLogger(RunningController.class);
    @Resource
    private PayModelGroupRefService payModelGroupRefService;
    @Resource
    private PayModelConfigService payModelConfigService;
    @Resource
    private PayOrderService payOrderService;
    /**
     * 监控接口
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/check")
    public String check(HttpServletRequest request, HttpServletResponse response,Integer userId, Integer payModelConfigId, String orderNo) {

        return "200";
    }

    public static void main(String[] args) {
        Date nowTime=new Date();
        System.out.println(nowTime);
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("时间是：" + time.format(nowTime));
    }
}
