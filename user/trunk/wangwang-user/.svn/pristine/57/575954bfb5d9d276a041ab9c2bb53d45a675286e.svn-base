package com.sandu.timer;

import com.sandu.api.user.output.UserWillExpireVO;
import com.sandu.api.user.service.SysUserService;
import com.sandu.config.SmsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 快到期账号发短信提醒定时任务
 * @author WangHaiLin
 * @date 2018/8/16  9:40
 */

@Configuration
@EnableScheduling
public class Timer {


    @Autowired
    private SysUserService sysUserService;

    //@Scheduled(cron = "0/30 * * * * *")//每30s触发一次
    @Scheduled(cron = "0 0 12 * * ?")//每天中午12点
    public void timer() {

         Logger logger = LoggerFactory.getLogger("快到期账号短信提醒定时任务");
        logger.info("到期账号提醒，定时任务启动");
        //查询B端用户，账号5天内将会过期的账号信息(days:账号剩余天数；platformType:平台类型(1:C端用户；2B端用户))
        List<UserWillExpireVO> userList = sysUserService.getUserWillExpire(5, 2);
        if (null!=userList&&userList.size()>0){
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //循环集合，取出账号的电话号码和剩余天数
            for (UserWillExpireVO user:userList) {
                //将过期账号的电话号码
                String mobile = user.getMobile();
                logger.info("手机号码:{}",mobile);
                //将过期账号剩余天数
                Integer remainDays = user.getRemainDays();
                logger.info("剩余天数:{}",remainDays);
                //具体到期时间
                String failureTime=dateFormat.format(user.getFailureTime());
                logger.info("账号到期时间:{}",remainDays);
                //短信内容
                String message=MessageFormat.format(SmsConfig.REMIND,mobile,remainDays,failureTime,SmsConfig.SERVICEPHONE);
                logger.info("提醒短信内容:{}",message);
                Boolean aBoolean = sysUserService.sendMessage(mobile, message);
                logger.info("发送短信结果:{}",aBoolean);
                //获取当前时间
                Date date=new Date();
                String dateTime = dateFormat.format(date);
                logger.info("短信发送时间:{}",dateTime);
            }
        }

        /*String mobile="18772847049";
        String remainDays="5";
        //短信内容
        String message=MessageFormat.format(SmsConfig.REMIND,mobile,remainDays,SmsConfig.SERVICEPHONE);

        Boolean aBoolean = sysUserService.sendMessage(mobile,message);
        System.out.println(aBoolean);
        //获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));*/
    }

}
