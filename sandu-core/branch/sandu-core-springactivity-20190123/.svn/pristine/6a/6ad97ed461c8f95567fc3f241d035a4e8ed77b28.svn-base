package com.sandu.job;

import com.sandu.api.notify.wx.TemplateMsgService;
import com.sandu.api.notify.wx.bo.TemplateMsgReqParam;
import com.sandu.api.springFestivalActivity.output.DateVo;
import com.sandu.api.springFestivalActivity.output.WxSpringActivityVo;
import com.sandu.api.springFestivalActivity.service.biz.FilmTicketActivityService;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@EnableScheduling
@Component
public class FilmTicketActivityJob {
    private static Long activityId = null;
    private final FilmTicketActivityService filmTicketActivityService;
    private final SysUserService sysUserService;
    private final TemplateMsgService templateMsgService;

    @Autowired
    public FilmTicketActivityJob(FilmTicketActivityService filmTicketActivityService,
                                 SysUserService sysUserService,
                                 TemplateMsgService templateMsgService) {
        this.filmTicketActivityService = filmTicketActivityService;
        this.sysUserService = sysUserService;
        this.templateMsgService = templateMsgService;
    }

    @Scheduled(cron = "0 0 9 * * ?")
    public void nineAmExecute() {
        if (activityId != null) {
            List<Long> userIdList = filmTicketActivityService.getUserListWhoDoingTask(activityId);
            WxSpringActivityVo detail = filmTicketActivityService.getSpringActivityDetail(activityId);
            if (userIdList != null && userIdList.size() > 0) {
                Map<String, Map<String, String>> templateData = filmTicketActivityService.buildData("您有新的任务可以领取",
                        "现在体验装修我家/产品替换功能，就可以获得一张拼图哦，点击继续领取免费电影票吧");
                for (Long userId : userIdList) {
                    SysUser user = sysUserService.get(userId.intValue());
                    this.sendTemplateMsg(user, 8, templateData, new Object[]{detail.getId(), detail.getLuckyWheelId()});
                }
            }
        }
    }

    @Scheduled(cron = "0 0 17 * * ?")
    public void fivePmExecute() {
        if (activityId != null) {
            Map<Long, Integer> userCardNumMap = filmTicketActivityService.getUserListWhoCardNumLessThanThree(activityId);
            DateVo dateVo = filmTicketActivityService.getActivityTime(activityId);
            WxSpringActivityVo detail = filmTicketActivityService.getSpringActivityDetail(activityId);
            if (userCardNumMap != null && userCardNumMap.size() > 0) {
                Set<Map.Entry<Long, Integer>> entrySet = userCardNumMap.entrySet();
                Iterator<Map.Entry<Long, Integer>> iterator = entrySet.iterator();
                while(iterator.hasNext()) {
                    Map.Entry<Long, Integer> entry = iterator.next();
                    Map<String, Map<String, String>> templateData = filmTicketActivityService.buildData("还有" +
                                    (int) Math.ceil((dateVo.getEndDate().getTime() - new Date().getTime()) / (1000.0 * 3600.0 * 24.0)) +
                                    "天随选网免费送电影票活动就结束啦",
                            "您今日已领取" + entry.getValue() + "张拼图，还可以继续领取拼图哦");
                    SysUser user = sysUserService.get(entry.getKey().intValue());
                    this.sendTemplateMsg(user, 9, templateData, new Object[]{detail.getId(), detail.getLuckyWheelId()});
                }
            }
        }
    }

    @Async
    public void sendTemplateMsg(SysUser miniUser, Integer msgType, Map templateData, Object[] pageParams) {
        if (miniUser == null) return;
        TemplateMsgReqParam param = templateMsgService.buildTemplateReqParam(miniUser, msgType, templateData, pageParams);
        templateMsgService.sendTemplateMsg(param);
    }

    public static void setActivityId(Long newActivityId) {
        activityId = newActivityId;
    }
}
