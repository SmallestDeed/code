package com.sandu.config;

import com.sandu.api.grouppurchase.service.biz.GroupPurchaseBizService;
import com.sandu.api.servicepurchase.model.ServicesAccountRel;
import com.sandu.api.servicepurchase.serivce.ServicesAccountRelService;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.service.servicepurchase.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
@Slf4j
public class ServiceScheduleConfig {

    @Resource
    private ServicesAccountRelService servicesAccountRelService;

    @Resource
    private SysUserService sysUserService;


    @Autowired
    private GroupPurchaseBizService groupPurchaseBizService;
    @Value("${msg.service.remind.message.template}")
    private String messageTemplate;

    @Value("${remind.message.switch}")
    private boolean sendMessageSwitch;

    private final static Integer TOP_DAYS = 5;

    /**
     * 每天十点执行,短信通知临期用户
     */
    @Scheduled(cron = "0 0 10 * * ? ")
    public void remindReamingMessage() {
        if (!sendMessageSwitch) {
            return;
        }
        log.info("短信通知临期用户Start");
        //获取临期用户
        List<ServicesAccountRel> accountInfos = servicesAccountRelService.listRemainingAccounts(TOP_DAYS);
        //获取用户信息
        List<SysUser> users = accountInfos.stream().map(item -> sysUserService.get(item.getId().intValue())).collect(Collectors.toList());
        log.info("被通知的用户:{}", users.stream().map(SysUser::getId).collect(Collectors.toList()));
        //发送短信
        users.forEach(
                user -> accountInfos.stream().filter(account -> account.getAccount().equals(user.getNickName()))
                        .findFirst().ifPresent(account -> this.sendMessage(user, account))
        );
        log.info("短信通知临期用户End");
    }

    private void sendMessage(SysUser user, ServicesAccountRel account) {
        long days = (System.currentTimeMillis() - account.getEffectiveEnd().getTime()) / DateUtil.millionSecondsOfDay;
        String message = MessageFormat.format(messageTemplate, user.getMobile(), days);
        try {
            message = URLEncoder.encode(message, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.info("send message:{}", message);
        sysUserService.sendMessage(user.getMobile(), message);
        log.info("send done");
    }


}
