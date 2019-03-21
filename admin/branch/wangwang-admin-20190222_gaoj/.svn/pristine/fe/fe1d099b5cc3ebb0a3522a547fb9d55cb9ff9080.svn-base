package com.sandu.config;

import com.sandu.api.category.service.biz.CategoryBizService;
import com.sandu.api.customer.service.CustomerAlotZoneService;
import com.sandu.api.decoratecustomer.service.biz.DecorateCustomerBizService;
import com.sandu.api.product.service.biz.ProductBizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;


/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * wangwang-product
 *
 * @author Sandu
 * @datetime 2018/3/19 17:39
 */
@Configuration
@EnableScheduling
@Slf4j
public class SchedulingConfig {

    @Autowired
    private ProductBizService productBizService;
    @Autowired
    private CustomerAlotZoneService customerAlotZoneService;
    @Autowired
    private CategoryBizService categoryBizService;

    @Autowired
    private DecorateCustomerBizService decorateCustomerBizService;

    @Value("${app.schedule.use.flag}")
    private boolean scheduleFlag;

    /**
     * 自动上架任务
     * 每5分钟执行一次
     */
//    @Scheduled(cron = "0 0/5 * * * ? ")
//    @Scheduled(initialDelay = 2 * 60 * 1000L, fixedDelay = 5 * 60 * 1000L)
    public void autoPutway() {
        if (!scheduleFlag) {
            return;
        }
        log.info("#1 自动上架产品, StartTime: {}", LocalDateTime.now());
        productBizService.synProductToPlatform();
        log.info("#1 EndTime: {}", LocalDateTime.now());
    }

    /**
     * 同步产品运营分类缓存
     */
    @Scheduled(cron = "0 0 2 * * ? ")
    public void syncCategoryCache() {
        if (!scheduleFlag) {
            return;
        }
        log.info("#1 同步产品运营分类缓存, StartTime: {}", LocalDateTime.now());
        categoryBizService.syncCache();
        log.info("#1 EndTime: {}", LocalDateTime.now());
    }

    /**
     * 同步三度后台已上架产品状态为已发布
     * 程序启动后，延迟5分钟开始执行， 以后每6分钟执行一次
     */
    @Scheduled(initialDelay = 7 * 60 * 1000L, fixedDelay = 6 * 60 * 1000L)
    public void updatedProductStateToRelease() {
        if (!scheduleFlag) {
            return;
        }
        log.info("#2 同步三度后台已上架产品为已发布， StartTime: {}", LocalDateTime.now());
        productBizService.synProductPutawayState();
        log.info("#2 EndTime： {}", LocalDateTime.now());
    }

    /**
     * 修复无公司ID的产品
     * 程序启动后，延迟7分钟开始执行， 以后每9分钟执行一次
     */
    @Scheduled(initialDelay = 9 * 60 * 1000L, fixedDelay = 9 * 60 * 1000L)
    public void fixedCompanyIdByBrandId() {
        if (!scheduleFlag) {
            return;
        }
        log.info("#3 同步三度后台无企业产品, StartTime: {}", LocalDateTime.now());
        productBizService.synProductCompanyInfoWithBrandId();
        log.info("#3 EndTime: {}", LocalDateTime.now());
    }

    /**
     * 初始化上架任务
     */
//    @Scheduled(cron = "0 30 12 * 3 ?")
//    @Scheduled(cron = "0 0/3 * * * ?")
    public void init() {
        if (!scheduleFlag) {
            return;
        }
        log.info("#4 初始化上架任务, StartTime: {}", LocalDateTime.now());
        productBizService.initProductStatus();
        log.info("#4 EndTime: {}", LocalDateTime.now());
    }

    /**
     * 同步三度后台产品-SPU-SKU关系
     */
    /*@Scheduled(fixedDelay = 5 * 60 * 1000L)*/
    @Scheduled(cron = "0 0 1 * * ?")
    public void fixedProductToSpuToSku() {
        if (!scheduleFlag) {
            return;
        }
        log.info("#5 调用修复三度后台创建产品关联spu-sku存储过程, StartTime: {}", LocalDateTime.now());
        productBizService.fixedProductToSpu();
        log.info("#5 EndTime: {}", LocalDateTime.now());
    }

    /**
     * 为开通了小程序管理的厂商下面的经销商根据区域自动生成匹配规则
     * 每天上午10点，下午2点，4点
     */
//    @Scheduled(cron = "0 0 10,14,16 * * ?")
    @Scheduled(fixedDelay = 10 * 60 * 1000L)
    public void autoGeneraRule() {
        if (!scheduleFlag) {
            return;
        }
        log.info("#6 经销商生成规则开始{}", LocalDateTime.now());
        customerAlotZoneService.autoGeneraRule();
        log.info("#6 EndTime: {}", LocalDateTime.now());
    }

    /**
     * 根据客户所在区域与经销商企业所负责区域进行分配客户
     * 每天凌晨0点
     */
//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(cron = "0 0 0/1 * * ?")
//    @Scheduled(fixedDelay = 10 * 60 * 1000L)
    public void allotAreaRule() {
        if (!scheduleFlag) {
            return;
        }
        log.info("#7 经销商分配客户开始{}", LocalDateTime.now());
        customerAlotZoneService.allotAreaRule();
        log.info("#7 EndTime: {}", LocalDateTime.now());
    }

    /**
     * 将用户放到客户表s
     * 每天凌晨0点
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void allotCustomer() {
        if (!scheduleFlag) {
            return;
        }
        log.info("#8 将用户放到客户表开始{}", LocalDateTime.now());
        customerAlotZoneService.allotCustomer();
        log.info("#8 将用户放到客户表EndTime: {}", LocalDateTime.now());
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    public void dispatchDecorateOrder() {
        if (!scheduleFlag) {
            return;
        }
        log.info("#9 系统派单/抢单 start=====>{}", LocalDateTime.now());
        decorateCustomerBizService.dispatchDecoratePrice();
        log.info("#9 系统派单/抢单开始end=====>{}", LocalDateTime.now());
    }

}
