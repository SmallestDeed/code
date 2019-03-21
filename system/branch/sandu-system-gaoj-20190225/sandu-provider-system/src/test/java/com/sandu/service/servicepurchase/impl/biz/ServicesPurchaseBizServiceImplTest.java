package com.sandu.service.servicepurchase.impl.biz;

import com.alibaba.druid.sql.visitor.functions.Now;
import com.sandu.api.user.service.SysUserService;
import com.sandu.api.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tools.ant.taskdefs.optional.pvcs.Pvcs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.math.BigDecimal.ROUND_DOWN;
import static java.math.BigDecimal.ROUND_UP;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ServicesPurchaseBizServiceImplTest {
    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal(0.01);
        BigDecimal multiply = bigDecimal.subtract(new BigDecimal(0)).setScale(2, ROUND_DOWN);
        if (multiply.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("---");
        }
        System.out.println(bigDecimal.longValue()+"---------"+multiply);
    }
    @Autowired
    private SysUserService sysUserService;

    @Value("${msg.services.renewals.new.template}")
    private String msgRenewalsNewTemplate;
    @Value("${msg.services.renewals.template}")
    private String msgRenewalsTemplate;
    @Value("${msg.services.renewals.try.template}")
    private String msgRenewalsTryTemplate;


    @Test
    public void testMessage(){
        System.out.println("send start");
        String msg = MessageFormat.format(msgRenewalsNewTemplate, 3, new Date());
        String endDateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        try {
            msg = MessageFormat.format(msgRenewalsTryTemplate, "test_cur",endDateString);
            msg = URLEncoder.encode(msg, "UTF-8");
            sysUserService.sendMessage("16620895251", msg);
            //续费
            msg = MessageFormat.format(msgRenewalsTemplate, "test_old",  endDateString);
            msg = URLEncoder.encode(msg, "UTF-8");
            sysUserService.sendMessage("16620895251", msg);
            //升级套餐
            msg = MessageFormat.format(msgRenewalsNewTemplate,
                    "test_old",
                    "test_cur",
                    endDateString);
            msg = URLEncoder.encode(msg, "UTF-8");
            sysUserService.sendMessage("16620895251", msg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.info("短信提示Start===>:用户手机号:{},msg:{}", "16620895251", msg);
//        sysUserService.sendMessage("16620895251", msg);
        System.out.println("send end");
    }
}