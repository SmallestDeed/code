package com.sandu.quartz;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in AM 9:23 2018/7/30 0030
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.user.model.UserBehavior;
import com.sandu.user.service.SysUserService;
import com.sandu.user.service.UserBehaviorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/**
 * @author weisheng
 * @Title: 用户佣金排行榜定时任务
 * @Package
 * @Description:
 * @date 2018/7/30 0030AM 9:23
 */
@Slf4j
@Component
public class UserCommissionTopJob {

    private static final String MEDIATION_TEST_SIGN = "cityUserTest";


    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserBehaviorService userBehaviorService;

    /*从100个中介假账号中,每月随机抽取2个账号新增4000-8000的佣金数*/
    public void addUserCommissionTask() {

        //获取100个中介假账号ID
 /*       List<Integer> idList =  sysUserService.getMediationTestId(MEDIATION_TEST_SIGN);
        if(null==idList || idList.size() == 0){
            log.info("未查询到中介假账号");
            return;
        }

        //随机抽取2个账号新增4000-8000的佣金数
        Integer firstId = idList.get(new Random().nextInt(idList.size()-1)) ;
        Integer secondId = idList.get(new Random().nextInt(idList.size()-1));
        Integer commission = new Random().nextInt(4001)+4000;
        Boolean flag = sysUserService.addUserCommssion(firstId,secondId,commission);
        if(flag){
            log.info("账号:"+firstId+"----"+secondId+"新增4000-8000的佣金数完成");
        }else {
            log.info("账号:"+firstId+"----"+secondId+"新增4000-8000的佣金数失败");
        }*/


    }


    /*从100个中介假账号中,每2天随机抽取2个假账号自增一个邀请数*/
    public void addUserInviteTask() {
        //获取100个中介假账号ID
/*        List<Integer> idList =  sysUserService.getMediationTestId(MEDIATION_TEST_SIGN);
        if(null==idList || idList.size() == 0){
            log.info("未查询到中介假账号");
            return;
        }

        //随机抽取2个账号自增一个邀请数
        Integer firstId = idList.get(new Random().nextInt(idList.size()-1)) ;
        Integer secondId = idList.get(new Random().nextInt(idList.size()-1));
        Boolean flag = sysUserService.addInviteCount(firstId,secondId);

        if(flag){
            log.info("账号:"+firstId+"----"+secondId+"自增邀请数完成");
        }else {
            log.info("账号:"+firstId+"----"+secondId+"自增邀请数失败");
        }*/


    }

    //同步用户操作埋点至数据库
    private void syncCacheToDB() {
        log.info("begin sync user_behavior to db");
        userBehaviorService.syncToDB();
        log.info("end sync user_behavior to db");
    }


}
