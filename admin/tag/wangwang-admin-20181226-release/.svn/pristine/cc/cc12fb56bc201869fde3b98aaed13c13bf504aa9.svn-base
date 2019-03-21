package com.sandu.config;

import com.sandu.api.resmodel.service.ResModelService;
import com.sandu.api.solution.service.biz.DesignPlanBizService;
import com.sandu.api.solution.service.biz.SolutionCopyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

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

    @Resource
    private DesignPlanBizService designPlanBizService;

    @Resource
    private SolutionCopyService solutionCopyService;

    @Resource
    private ResModelService resModelService;

    @Resource(name = "resPropertiesMap")
    private Map<String, String> fileKey2Path;

    /**
     * 自动上架任务
     */
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void test() {
        System.out.println("now::------------>" + LocalDateTime.now());
        designPlanBizService.initDesignPlanStatus();
    }

//    @Scheduled(cron = "0 0/3 * * * ? ")
    public void copySharePlan2Company() {
        designPlanBizService.copySharePlan2Company();

    }


    /**
     * 自动设置公司
     */
//    @Scheduled(cron = "0 0/5 * * * ? ")
    public void setDesignPlanCompanyId() {
        System.out.println("now::------------>" + LocalDateTime.now());
        designPlanBizService.setDesignPlanCompanyId();
    }

    /**
     * 初始化上架任务
     * Scheduled(cron = "0 0/1 * * * ? ")
     */
    public void init() {
        designPlanBizService.initDesignPlanStatus();
    }

    /**
     * 修复复制方案，未复制渲染资源的bug
     */
//    @Scheduled(cron = "0 0/5 * * * ? ")
//    public void fixedCopySolutionRenderResourceBug() {
//        solutionCopyService.fixedBugSolution();
//    }

    /**
     * 修复交付方案未打组bug
     */
    @Scheduled(fixedDelay = 100000L)
    public void fixedDeliverPlanCombo() {
        solutionCopyService.fixedDeliverPlan();
    }


    @Scheduled(cron = "0 25 13 4 12 ?")
    public void fixWithTransFile() {
        LocalDateTime begin = LocalDateTime.now();
        log.info("################start trans resource################");
        designPlanBizService.transResourceForPathError();
        log.info("################end trans resource################");
        Duration cost = Duration.between(begin, LocalDateTime.now());
        log.info("cost time :{} min", cost.toMinutes());
    }

    /**
     * 修复方案企业一对多问题
     */
    @Scheduled(initialDelay = 5 * 60 * 1000L, fixedDelay =3 * 60 * 60 * 1000L)
    public void fixPlanCompanyRelation() {
        log.info("############ 修复方案企业一对多开始{}", LocalDateTime.now());
        designPlanBizService.fixPlanCompanyRelation();
        log.info("############ EndTime: {}", LocalDateTime.now());
    }

    public static void main(String[] args) throws IOException {
    }
}
