package com.sandu.config;

import com.sandu.quartz.UserCommissionTopJob;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;


@Configuration
public class QuartzConfiguration {

    // 配置定时任务1
    @Bean(name = "firstJobDetail")
    public MethodInvokingJobDetailFactoryBean firstJobDetail(UserCommissionTopJob  firstJob) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(false);
        // 为需要执行的实体类对应的对象
        jobDetail.setTargetObject(firstJob);
        // 需要执行的方法名称
        jobDetail.setTargetMethod("addUserCommissionTask");
        return jobDetail;
    }


    // 配置触发器1
    @Bean(name = "firstTrigger")
    public CronTriggerFactoryBean firstTrigger(JobDetail firstJobDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(firstJobDetail);
        // cron表达式, 每月1号凌晨1点执行一次
        trigger.setCronExpression("0 0 1 1 * ?");
        return trigger;
    }

    // 配置定时任务2
    @Bean(name = "secondJobDetail")
    public MethodInvokingJobDetailFactoryBean secondJobDetail(UserCommissionTopJob  secondJob) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(false);
        // 为需要执行的实体类对应的对象
        jobDetail.setTargetObject(secondJob);
        // 需要执行的方法名称
        jobDetail.setTargetMethod("addUserInviteTask");
        return jobDetail;
    }


    // 配置触发器2
    @Bean(name = "secondTrigger")
    public SimpleTriggerFactoryBean secondTrigger(JobDetail secondJobDetail) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(secondJobDetail);
        // 设置任务启动延迟
        trigger.setStartDelay(0);
        // 每2天执行一次
        trigger.setRepeatInterval(2*24*60*60*1000);
        return trigger;
    }


    // 配置定时任务3
    @Bean(name = "thirdJobDetail")
    public MethodInvokingJobDetailFactoryBean thirdJobDetail(UserCommissionTopJob  thirdJob) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(false);
        // 为需要执行的实体类对应的对象
        jobDetail.setTargetObject(thirdJob);
        // 需要执行的方法名称
        jobDetail.setTargetMethod("syncCacheToDB");
        return jobDetail;
    }


    // 配置触发器3
    @Bean(name = "thirdTrigger")
    public CronTriggerFactoryBean thirdTrigger(JobDetail thirdJobDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(thirdJobDetail);
        // 设置任务启动延迟
        trigger.setStartDelay(0);
        //
        trigger.setCronExpression("0 0/30 * * * ? ");
//        trigger.setCronExpression("0 0/1 * * * ?  ");
        return trigger;
    }





    // 配置Scheduler
    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory(Trigger firstTrigger, Trigger secondTrigger, Trigger thirdTrigger) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        // 延时启动，应用启动1秒后
        bean.setStartupDelay(1);
        // 注册触发器
        bean.setTriggers(firstTrigger,secondTrigger,thirdTrigger);
        return bean;
    }
} 