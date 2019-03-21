package com.sandu.web;

import com.sandu.api.product.service.biz.ProductBizService;
import com.sandu.api.solution.model.po.CopyShareDesignPlanPO;
import com.sandu.api.solution.service.biz.DesignPlanBizService;
import com.sandu.api.solution.service.biz.SolutionCopyService;
import com.sandu.common.ReturnData;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Enumeration;

import static com.sandu.constant.ResponseEnum.SUCCESS;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2018/3/7 16:12
 */
@RestController
@Slf4j
public class RunningCheck {

    @Autowired
    private SolutionCopyService solutionCopyService;

    @Autowired
    private DesignPlanBizService designPlanBizService;

    @Autowired
    private ProductBizService productBizService;

    //    @GetMapping(value = "/fixed-solution")
//    public int fixedSolution() {
//        solutionCopyService.fixedBugSolution();
//        return 1;
//    }
//
    @GetMapping(value = "/fixed/{originId}/to/{targetId}")
    public int fixedOneSolution(@PathVariable("originId") Integer originId, @PathVariable("targetId") Integer targetId) {
        if (originId == null || targetId == null) {
            return 0;
        }
        solutionCopyService.fixedOne(originId, targetId);
        return 1;
    }

    @GetMapping(value = "/running/check")
    public int check() {
        return 200;
    }

    @PostMapping("/share/copy")
    @ApiOperation("拷贝制定方案到指定公司")
    public ReturnData copy(@Valid @RequestBody CopyShareDesignPlanPO copyShareDesignPlanPO) {
        designPlanBizService.copyDesignPlanToCompany(copyShareDesignPlanPO);
        return ReturnData.builder().code(SUCCESS);
    }

    @PostMapping("/pic/transfer")
    @ApiOperation("图片地址迁移")
    public ReturnData transfer() {
        productBizService.picTransfer();
        return ReturnData.builder().code(SUCCESS);
    }


    @GetMapping(value = "/get-logger")
    public void getLogger() {
        System.out.println("Current Loggers:");

        Enumeration currentLoggers = LogManager.getCurrentLoggers();
        while (currentLoggers.hasMoreElements()) {
            Logger logger = (Logger) currentLoggers.nextElement();
            System.out.println(logger.getName());
            System.out.println(logger.getEffectiveLevel());
        }
    }
}
