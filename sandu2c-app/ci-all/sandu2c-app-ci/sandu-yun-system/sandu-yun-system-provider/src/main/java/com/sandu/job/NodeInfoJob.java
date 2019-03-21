package com.sandu.job;

import com.sandu.system.service.NodeInfoBizService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * created by zhangchengda
 * 2018/11/12 17:00
 */
@Log4j2
@Component
public class NodeInfoJob {
    @Autowired
    private NodeInfoBizService nodeInfoBizService;

    private static final String LOG_PREFIX_NODE_INFO_JOB = "[节点信息定时任务]:";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:sss");

    public void syncToDatabase(){
        log.info(LOG_PREFIX_NODE_INFO_JOB + "开始增量同步节点数据到数据库! 当前时间:{}", DATE_FORMAT.format(new Date()));
        long startTime = System.currentTimeMillis();
        nodeInfoBizService.incrementDataToDatabase();
        long endTime = System.currentTimeMillis();
        log.info(LOG_PREFIX_NODE_INFO_JOB + "增量同步节点数据到数据库完成! 耗时:{}ms，当前时间:{}", endTime - startTime, DATE_FORMAT.format(new Date()));
    }
}
