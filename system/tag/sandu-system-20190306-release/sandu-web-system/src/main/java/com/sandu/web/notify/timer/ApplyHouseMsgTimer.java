package com.sandu.web.notify.timer;

import com.sandu.api.applyHouse.model.BaseHouseApply;
import com.sandu.api.applyHouse.service.BaseHouseApplyService;
import com.sandu.api.company.model.BaseCompanyMiniProgramTemplateMsg;
import com.sandu.api.notify.wx.TemplateMsgService;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.web.async.AsyncCallTemplateMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
public class ApplyHouseMsgTimer {

    @Autowired
    private BaseHouseApplyService baseHouseApplyService;

    @Autowired
    private TemplateMsgService templateMsgService;

    @Autowired
    private AsyncCallTemplateMsgService asyncCallTemplateMsgService;

    @Autowired
    private SysUserService sysUserService;

    private final String XZW_APPID_FLAG = "wx42e6b214e6cdaed3";

    private Logger logger = LoggerFactory.getLogger(ApplyHouseMsgTimer.class);

    @Scheduled(cron = "0 0 * * * ?")//每小时执行一次
    public void sendApplyHouseOverSixHourTemplateMsg() {
        logger.info("通知用户申请户型录入中 =>{} 定时器  ===========start===========");
        //过滤同一个用户相同户型的校验
        List<BaseHouseApply> baseHouses = getAdviceApplyHouse();
        if (Objects.nonNull(baseHouses) && !baseHouses.isEmpty()) {
            baseHouses.stream().forEach(house -> {
                SysUser sysUser = sysUserService.get(house.getUserId());
                if (sysUser != null){
                    if (XZW_APPID_FLAG.equals(sysUser.getAppId())){ //随选网的才通知
                        if (Objects.equals(house.getEnteringAdviceMsgFlag(), 1)) {
                            //处理发送消息
                            if (baseHouseApplyService.updateEnteringAdviceMsgFlag(house.getId())) {
                                logger.info("需要发送通知的申请户型 =>{}"+house.getId());
                                Map<String, Object> objMap = templateMsgService.sendHandlerApplyHouseMsg(house.getId());
                                asyncCallTemplateMsgService.sendTemplateMsg((SysUser) objMap.get("sysUser"), BaseCompanyMiniProgramTemplateMsg.TEMPLATE_TYPE_APPLY_BASE_HOUSE_ADVICE, (Map) objMap.get("msgData"),"index","","");
                            }
                        }
                    }
                }
            });
        }
        logger.info("通知用户申请户型录入中 =>{} 定时器  ===========end===========");
    }

    private  List<BaseHouseApply> getAdviceApplyHouse() {
        List<BaseHouseApply> baseHouses = baseHouseApplyService.selectApplyHouseOverSixHour();
        logger.info("需要发送模板消息通知的条数 =>{}" + (Objects.isNull(baseHouses) ? 0 : baseHouses.size()));
        List<Integer> resultHouseIds = new ArrayList<>();
        //过滤某个用户相同时间内提交的户型
        if (Objects.nonNull(baseHouses) && !baseHouses.isEmpty()){
            //根据用户id分组
            Map<Integer, List<BaseHouseApply>> userBaseHouse = baseHouses.stream().collect(Collectors.groupingBy(BaseHouseApply::getUserId));
            for (Map.Entry<Integer, List<BaseHouseApply>> entry : userBaseHouse.entrySet()) {
                //Map<String, Integer> adviceHouseMap = entry.getValue().stream().collect(Collectors.toMap(BaseHouseApply::getLivingInfo, BaseHouseApply::getId));
                Map<String, Integer> adviceHouseMap = new HashMap<>();
                List<BaseHouseApply> houses = entry.getValue();
                houses.stream().forEach(h ->{
                    adviceHouseMap.put(h.getLivingInfo(),h.getId());
                });
                List<Integer> ids = adviceHouseMap.values().stream().collect(Collectors.toList());
                resultHouseIds.addAll(ids);
            }
            //提取需要发送模板消息的户型
            List<Integer> houseIds = baseHouses.stream().map(house -> {
                return house.getId();
            }).collect(Collectors.toList());

            //提取每个用户重复的户型id
            houseIds.removeAll(resultHouseIds);

            if (!houseIds.isEmpty()){
                logger.info("同一时间内重复提交的数据 =>{}"+houseIds);
                //更新重复的户型部需要发送模板消息
                baseHouseApplyService.batchUpdateEnteringAdviceMsgFlag(houseIds);
            }
           return baseHouses.stream().filter(house ->{return !houseIds.contains(house.getId());}).collect(Collectors.toList());
        }
        return baseHouses;
    }

}
