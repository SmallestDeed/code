package com.nork.repair.controller;

import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.product.service.ProductCategoryRelService;
import com.nork.repair.model.ProductCategoryInfo;
import com.nork.threadpool.ThreadPool;
import com.nork.threadpool.ThreadPoolManager;
import com.nork.repair.job.BgWallCategoryMountJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 修复把形象墙下面其他分类产品挂到各自分类下面去
 * @auth xiaoxc
 * @data 2018/12/8
 */
@Controller
@RequestMapping("/repair/bgwll/categor")
public class RepairBgWallCategoryMountController {

    Logger logger = LoggerFactory.getLogger(RepairBgWallCategoryMountController.class);
    private static String CLASS_LOG_PREFIX = "背景墙分类重新挂载：";

    @Autowired
    private ProductCategoryRelService productCategoryRelService;
    @Autowired
    private ThreadPoolManager threadPoolManager;



    @ResponseBody
    @RequestMapping("/mount")
    public Object disposeBgWallCategoryMount(Integer productId, Integer start, Integer limit) {

        List<ProductCategoryInfo> list = productCategoryRelService.getXingxCategoryProductInfo(productId, start, limit);
        if (null == list || list.size() == 0) {
            return  "找不到产品分类";
        }
        int count = list.size();
        logger.error(CLASS_LOG_PREFIX + "获取总条数：{}", count);

        //自定义线程数
        int jobNumber = 6;
        //分配任务数据
        int taskCount = count/jobNumber;
        // 余数大于0 线程数 + 1
        if(count % jobNumber > 0) {
            taskCount = taskCount + 1;
        }
        // 线程任务列表计算
        List<List<ProductCategoryInfo>> categoryList = SystemCommonUtil.getProductCategorList(list, taskCount, jobNumber);
        // 取得线程池实列
        ThreadPool threadPool = threadPoolManager.getThreadPool();
        for (int i = 0; i < jobNumber; i ++) {
            List<ProductCategoryInfo> categoryJobList = new ArrayList<>();
            if (i < categoryList.size()) {
                categoryJobList = categoryList.get(i);
            }
            //线程名称
            String jobName = "JobName_" + i;
            if (categoryJobList.size() != 0) {
                BgWallCategoryMountJob categoryJob = new BgWallCategoryMountJob(categoryJobList, productCategoryRelService, jobName);
                threadPool.submit(categoryJob);
            } else {
                logger.error(CLASS_LOG_PREFIX + "{} 任务无数据", jobName);
            }
        }

        return "OK！" ;

    }



}
